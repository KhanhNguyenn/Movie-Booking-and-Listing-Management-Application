
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Calendar;
import java.util.GregorianCalendar;



/**
 * SpecialDatesManager hold list of special dates and manages operations on the list.
 *
 * @author SS3-grp6
 *
 */
public class SpecialDatesManager
{
    protected static ArrayList <String> holidays = new ArrayList<String>();
    private final String filename = "special_dates";

    /**
     * Constructor of specialdatesManager with initializing list of holidays from file
     */
    public SpecialDatesManager(){
        DataStore txtdb = new DataStore();
        try{
            this.holidays = DataStore.readDates(filename);
        } catch (Exception e)
        {
            System.out.println("IOException > " + e.getMessage());
        }

    }

    /**
     * Check whether a date is holiday.
     *
     * @param date
     * @return true if it is a holiday, otherwise false
     */
    public static boolean if_holiday(String date) {
        if (isWeekend(date) == true)
            return true;
        else if (holidays.contains(date.substring(0,8)))
            return true;
        else
            return false;
    }

    /**
     * Check whether a date is weekend or not.
     *
     * @param date
     * @return true if a date is weekend, otherwise false
     */
    public static boolean isWeekend(String date)
    {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6, 8));
        Calendar cal = new GregorianCalendar(year, month - 1, day);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        return (Calendar.SUNDAY == dayOfWeek || Calendar.SATURDAY == dayOfWeek);
    }

    /**
     * Modify list of special dates.
     */
    public void update_holiday()
    {
        try
        {
            Scanner sc = new Scanner(System.in);
            int choice = 0;
            while (true)
            {
                System.out.println("\nUpdate Special Dates / Holidays");
                System.out.println("\n(1) Check Special Date");
                System.out.println("(2) Add Special Date");
                System.out.println("(3) Remove Special Date");
                System.out.println("(4) Quit");
                System.out.print("\nEnter choice: ");
                choice = sc.nextInt();
                if (choice == 4)
                    break;
                System.out.print("\nPlease enter date (Format: yyyyMMdd): ");
                String date = sc.next();
                switch(choice)
                {
                    case(1):
                    {
                        boolean isSpecial = if_holiday(date);
                        if (isSpecial){
                            System.out.println("\nIt is a special date.");
                        }
                        else
                            System.out.println("\nIt is not a special date.");
                        break;
                    }
                    case(2):
                    {
                        add_holiday(date);
                        break;
                    }
                    case(3):
                    {
                        remove_holiday(date);
                        break;
                    }
                }
            }
        } catch (Exception e){
            System.out.println("Invalid Input!");
            return;
        }

    }
    /**
     * Add a special date to list and save back to file
     * @param date
     * @throws IOException
     */
    public void add_holiday(String date) throws IOException
    {
        // Check 1: null values
        if (date == null) {
            System.out.println("\nPlease do not enter a null value");
            return;
        }

        // Check 2: wrong format
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            dateFormat.setLenient(false);
            dateFormat.parse(date.trim());
        }
        catch (ParseException e) {
            System.out.print("\nPlease enter date in yyyyMMdd format: ");
            return;
        }

        // Check 3: if date already exists in ArrayList
        if (holidays.contains(date)) {
            System.out.println("\nDate is already in record");
            return;
        }

        // All no problem, proceed to add
        holidays.add(date);
        DataStore txtdb = new DataStore();
        DataStore.saveDates(filename, holidays);
        System.out.println("\nDate successfully added");
    }

    /**
     * Remove a special date from file.
     *
     * @param date
     * @throws IOException
     */
    public void remove_holiday(String date) throws IOException
    {
        // Check 1: null values
        if (date == null) {
            System.out.println("\nPlease do not enter a null value");
            return;
        }

        // Check 2: wrong format
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            dateFormat.setLenient(false);
            dateFormat.parse(date.trim());
        }
        catch (ParseException e) {
            System.out.print("\nPlease enter date in yyyyMMdd format: ");
            return;
        }

        // Check 3: if date does not exist in ArrayList
        if (!holidays.contains(date)) {
            System.out.println("\nDate is not in record");
            return;
        }

        // All no problem, proceed to add
        holidays.remove(date);
        DataStore txtdb = new DataStore();
        DataStore.saveDates(filename, holidays);
        System.out.println("\nDate successfully removed");
    }


}
