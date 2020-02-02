
import java.util.ArrayList;


/**
 * Class hold list of staff and operations only for staffs.
 * Opeartions such as update status of movie, update list of MovieListing,
 * update list of special date and price system and provide authetication for other people.
 *
 * @author SS3-grp6
 *
 */
public class StaffManager {
	protected static ArrayList<Staff> Staffs;
	private static String filename = "staffs";
	private MovieManager Smovie;
	private ListingManager Slisting;
	private SpecialDatesManager Sdates;
	private BookingManager Sbooking;
	private static final MoblimaScanner sc = new MoblimaScanner();

	/**
	 * Constructor of staffManager with initial movieManage, listingManager bookingManager
	 * @param a-movieManager
	 * @param b-listingManager
	 * @param c-bookingManager
	 */
	public StaffManager(MovieManager a, ListingManager b, BookingManager c) {
		try {
			Staffs = DataStore.readStaff(filename);
		} catch (Exception e) {
			System.out.println("Staff file could not be read.");
		}
		Smovie = a;
		Slisting = b;
		Sdates = new SpecialDatesManager();
		Sbooking = c;
	}

	/**
	 * Login for staff.
	 */
	public void staffLogin(){
		Staff employee = authenticate();
		if (employee == null)
			return;
		makechoice(employee);
	}

	/**
	 * Check authentication of a login entity.
	 * Prompt users to enter username, password and then list of staffs to check for
	 * correct entity.
	 *
	 * @return Staff object if exists, otherwise null
	 */
	public Staff authenticate() {
		System.out.println("\n========================");
		System.out.print("Enter username: ");
		String username = sc.next();
		System.out.print("\nEnter password: ");
		String password = sc.next();
		System.out.println("\n========================");

		for (int i = 0; i < Staffs.size(); i++) {
			Staff cur = Staffs.get(i);
			switch(cur.authenticate(username, password)){
			case 1:
				System.out.println("\nAnthenticated");
				return cur;
			case 0:
				System.out.println("\nWrong password");
				return null;
			case -1: continue;
			}
		}
		System.out.println("\nUser does not exist!");
		return null;
	}

	/**
	 * Execute choice of command from staff.
	 *
	 * @param employee
	 */
	public void makechoice(Staff employee) {
		int choice = menu();
		while (choice != -1) {
			switch (choice) {
			case 1:
				Smovie.update_movies();
				break;
			case 2:
				Slisting.updateListing();
				break;
			case 3:
				Sbooking.getBookingPrice().updatePrice();
				break;
			case 4:
				Sdates.update_holiday();
				break;
			case 5:
				add_staff(employee);
				break;
			case 6:
				delete_staff(employee);
				break;
			case 7:
				topMovies();
				break;
			default:
				break;
			}
			choice = menu();
		}
		System.out.println("\nThank you!");
	}

	/**
	 * Display list of commands for staff to choose.
	 * <ul>
	 * <li>(1) Update Movies
	 * <li>(2) Update Listings
	 * <li>(3) Update Prices
	 * <li>(4) Update Special Dates
	 * <li>(5) Add User
	 * <li>(6) Remove User
	 * <li>(7) Check Top Movies
	 * <ul>
	 *
	 * @return commands choosen by staff
	 */
	public int menu() {
		System.out.println("\n========================");
		System.out.println("\nHow may we help you today?\n");

		System.out.println("(1) Update Movies");
		System.out.println("(2) Update Listings");
		System.out.println("(3) Update Prices");
		System.out.println("(4) Update Special Dates");
		System.out.println("(5) Add User");
		System.out.println("(6) Remove User");
		System.out.println("(7) Check Top Movies");

		return sc.inputInt(1, 7);
	}

	/**
	 * Add staff authentication to staff list.
	 *
	 * @param employee
	 */
	protected void add_staff(Staff employee) {
		System.out.println("Enter username");
		String username = sc.next();
		System.out.println("Enter password");
		String password = sc.next();
		Staff added = new Staff(username, password);

		for (int i = 0; i < Staffs.size(); i++) {
			Staff cur = Staffs.get(i);
			switch(cur.authenticate(username, password)){
			case 1:
				System.out.println("Staff already exists");
				return;
			case 0:
				System.out.println("Staff already exists");
				return;
			case -1: continue;
			}
		}

		try {
			Staffs.add(added);
			DataStore.saveStaff(filename, Staffs);
			System.out.println("Staff successfully added");
		} catch (Exception e) {
			System.out.println("Exception in adding users.");
		}
	}

	/**
	 * Remove staff entity from list of staff.
	 *
	 * @param employee
	 */
	protected void delete_staff(Staff employee) {
		System.out.println("Enter username");
		String username = sc.next();
		if (employee.getUsername().equals(username)) {
			System.out.println("You cannot delete yourself!");
			return;
		}

		Staff found = null;
		for (int i = 0; i < Staffs.size(); i++) {
			if (Staffs.get(i).getUsername().equals(username)) {
				found = Staffs.get(i);
				break;
			}
		}
		if (found == null) {
			System.out.println("Staff does not exist in system.");
			return;
		}

		try {
			Staffs.remove(found);
			DataStore.saveStaff(filename, Staffs);
			System.out.println("Staff successfully removed.");
		} catch (Exception e) {
			System.out.println("Exception in deleting staff.");
		}
	}

	/**
	 * List of movie by Sales or Ratings.
	 */
	public void topMovies() {
		System.out.println("\n========================");
		System.out.println("\nList Top Movies By:\n");
		System.out.println("(1) Sales");
		System.out.println("(2) Ratings");

		int choice = sc.inputInt(1, 2);

		switch (choice) {
		case 1:
			Sbooking.listTop5BySales();
			break;
		case 2:
			RatingManager.listByRatings();
			break;
		case -1:
			return;
		default:
			break;
		}
	}
}
