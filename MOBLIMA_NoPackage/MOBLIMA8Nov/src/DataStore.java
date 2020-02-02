
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.File;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;


/**
* DataStore is supported to read and write to different files such as Transaction, Price,
* Catalogue, Date_time, Staff, Rating
*
* @author SS3-grp6
*/
public class DataStore {
	public static final String SEPARATOR = "|";

	/**
   * Read list of current transaction stored from a file.
   *
   * @param filename
   * @return list of current transaction
   * @throws IOException
   */
	public static ArrayList readTransactions(String filename) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList)read(filename);
		ArrayList alr = new ArrayList() ;// to store CustomerTransactions data

        for (int i = 0 ; i < stringArray.size() ; i++) {
				String st = (String)stringArray.get(i);
				// get individual 'fields' of the string separated by SEPARATOR
				StringTokenizer star = new StringTokenizer(st , SEPARATOR);	// pass in the string to the string tokenizer using delimiter ","

				String TID = (star.nextToken().trim()); // TID
				String  name = star.nextToken().trim();	// first token
				int age = Integer.parseInt(star.nextToken().trim()); //second token
				int  mobile = Integer.parseInt(star.nextToken().trim()); // third token
				String  email = star.nextToken().trim();  // fourth token
				String seats = star.nextToken().trim(); //fifth token

				CustomerTransaction curr = new CustomerTransaction(name, age, mobile, email, TID, seats);

				alr.add(curr) ;
			}
			return alr ;
	}

	/**
	 * Save list of new modified transactions back to file
	 *
	 * @param filename
	 * @param al
	 * @throws IOException
	 */
	public static void saveTransactions(String filename, List al) throws IOException {
		List alw = new ArrayList() ;// to store CustomerTransactions data

        for (int i = 0 ; i < al.size() ; i++) {
				CustomerTransaction cust = (CustomerTransaction)al.get(i);
				StringBuilder st =  new StringBuilder() ;

				st.append(cust.getTID().trim());
				st.append(SEPARATOR);

				st.append(cust.getName().trim());
				st.append(SEPARATOR);

				st.append(cust.getAge());
				st.append(SEPARATOR);

				st.append(cust.getMobile());
				st.append(SEPARATOR);

				st.append(cust.getEmail().trim());
				st.append(SEPARATOR);

				st.append(cust.getSeats().trim());
				alw.add(st.toString()) ;
			}
			write(filename,alw);
	}

	/**
	 * Read a file
	 *
	 * @param filename
	 * @return list of lines in file
	 * @throws IOException
	 */
	public static ArrayList readListing(String filename) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList)read(filename);
		ArrayList alr = new ArrayList() ;// to store MovieListing data

        for (int i = 0 ; i < stringArray.size() ; i++) {
				String st = (String)stringArray.get(i);
				// get individual 'fields' of the string separated by SEPARATOR
				StringTokenizer star = new StringTokenizer(st , SEPARATOR);

				String datetime = star.nextToken().trim(); // movie date
				String movie = star.nextToken().trim(); // movie name
				int cineplexIndex = Integer.parseInt(star.nextToken().trim());	// first token
				int cinemaIndex = Integer.parseInt(star.nextToken().trim()); //second token

				MovieListing curr = new MovieListing(datetime, movie, cineplexIndex, cinemaIndex);

				alr.add(curr);
			}
			return alr ;
	}

	/**
	 * Save list of MovieListing back to file
	 *
	 * @param filename
	 * @param al - list of MovieListing
	 * @throws IOException
	 */
	public static void saveListing(String filename, List al) throws IOException {
		List alw = new ArrayList() ;

        for (int i = 0 ; i < al.size() ; i++) {
				MovieListing curr = (MovieListing)al.get(i);
				StringBuilder st =  new StringBuilder() ;

				st.append(curr.getListingCode().substring(3).trim());
				st.append(SEPARATOR);

				st.append(curr.getTitle().trim());
				st.append(SEPARATOR);

				st.append(curr.getCineplex());
				st.append(SEPARATOR);

				st.append(curr.getCinema());

				alw.add(st.toString()) ;
			}
        Collections.sort(alw);
		write(filename,alw);
	}

	/**
	 * Read list of prices from a price file
	 *
	 * @param filename
	 * @return list of prices
	 * @throws Exception
	 */
	public static ArrayList readPrices(String filename) throws Exception{
		ArrayList stringArray = (ArrayList)read(filename);
		ArrayList <Double> alr = new ArrayList <Double> ();
		for (int i = 0; i < stringArray.size(); i++){
			alr.add(Double.valueOf((String)stringArray.get(i)));
		}
		return alr;
	}

	/**
	 * Save list of modified prices back to file
	 *
	 * @param filename
	 * @param pricelist
	 */
	public static void savePrices(String filename, ArrayList pricelist) {
		try{
			write(filename,pricelist);
		} catch(Exception e){
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Read list of movies from file.
	 *
	 * @param filename
	 * @return list of Movie
	 * @throws IOException
	 */
	public static ArrayList readCatalogue(String filename) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList)read(filename);
		ArrayList alr = new ArrayList() ;// to store MovieListing data

        for (int i = 0 ; i < stringArray.size() ; i++) {
				String st = (String)stringArray.get(i);
				// get individual 'fields' of the string separated by SEPARATOR
				StringTokenizer star = new StringTokenizer(st , SEPARATOR);

				char movieCode = star.nextToken().trim().charAt(0);
				String title = star.nextToken().trim(); // movie name
				String status = star.nextToken().trim();
				String rating = star.nextToken().trim();
				String synopsis = star.nextToken().trim();
				String cast = star.nextToken().trim();
				String director = star.nextToken().trim();

				Movie curr = new Movie(movieCode, title, status, rating, synopsis, cast, director);

				alr.add(curr);
			}
			return alr ;
	}

	/**
	 * Save list of modified Movie back to file.
	 *
	 * @param filename
	 * @param movies
	 * @throws IOException
	 */
	public static void saveCatalogue(String filename, List movies) throws IOException{
		List moviesw = new ArrayList() ;

		for (int i = 0 ; i < movies.size() ; i++) {
			Movie curr = (Movie)movies.get(i);
			StringBuilder st =  new StringBuilder() ;

			st.append(curr.getCode());
			st.append(SEPARATOR);

			st.append(curr.getTitle().trim());
			st.append(SEPARATOR);

			st.append(curr.getStatus().trim());
			st.append(SEPARATOR);

			st.append(curr.getAgeRating().trim());
			st.append(SEPARATOR);

			st.append(curr.getSynopsis().trim());
			st.append(SEPARATOR);

			st.append(curr.getCast().trim());
			st.append(SEPARATOR);

			st.append(curr.getDirector().trim());
			st.append(SEPARATOR);

			moviesw.add(st.toString()) ;
		}
		write(filename,moviesw);
	}

	/**
	 * Read list of ratings from file
	 *
	 * @param filename
	 * @param title
	 * @return
	 * @throws IOException
	 */
	public static double readRatings(String filename, String title) throws IOException {

		ArrayList stringArray = (ArrayList)read(filename);
		double sum = 0;
		double count = 0;
        for (int i = 0 ; i < stringArray.size() ; i++) {
				String st = (String)stringArray.get(i);
				// get individual 'fields' of the string separated by SEPARATOR
				StringTokenizer star = new StringTokenizer(st , SEPARATOR);

				String currTitle = star.nextToken().trim(); // movie name
				int rating = Integer.parseInt(star.nextToken().trim());

				if (currTitle.equalsIgnoreCase(title)) {
					count ++;
					sum += rating;
				}
			}
		return sum/count;
	}

	/**
	 * Get list of Dates from file
	 *
	 * @param filename
	 * @return list of dates
	 * @throws IOException
	 */
	public static ArrayList readDates(String filename) throws IOException{
		ArrayList stringArray = (ArrayList)read(filename);
		ArrayList dates = new ArrayList();
		for (int i = 0 ; i < stringArray.size() ; i++) {
			String date = (String)stringArray.get(i);
			dates.add(date);
		}
		return dates;
	}

	/**
	 * Save list of dates back to file
	 *
	 * @param filename
	 * @param dates
	 * @throws IOException
	 */
	public static void saveDates(String filename, ArrayList dates) throws IOException
	{
		write(filename,dates);
	}

	/**
	 * Read list of staffs from file
	 *
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static ArrayList readStaff(String filename) throws IOException{
		// read String from text file
		ArrayList stringArray = (ArrayList)read(filename);
		ArrayList staffs = new ArrayList() ;// to store staff data

		for (int i = 0 ; i < stringArray.size() ; i++) {
			String st = (String)stringArray.get(i);
			// get individual 'fields' of the string separated by SEPARATOR
			StringTokenizer star = new StringTokenizer(st , SEPARATOR);	// pass in the string to the string tokenizer using delimiter ","

			String username = star.nextToken().trim(); // username
			String password = star.nextToken().trim();	// password
			Staff curr = new Staff(username, password);
			staffs.add(curr) ;
		}
		return staffs ;
	}

	/**
	 * Save list of staffs back to file
	 *
	 * @param filename
	 * @param staffs
	 * @throws IOException
	 */
	public static void saveStaff(String filename, List staffs) throws IOException {
		List staffsw = new ArrayList() ;// to store staffs data

		for (int i = 0 ; i < staffs.size() ; i++) {
			Staff employee = (Staff)staffs.get(i);
			StringBuilder st =  new StringBuilder() ;

			st.append(employee.getUsername().trim());
			st.append(SEPARATOR);

			st.append(employee.getPassword().trim());
			staffsw.add(st.toString());
		}
		write(filename,staffsw);
	}

	/**
	 * Save list of Ratings back to file
	 *
	 * @param fileName
	 * @param al
	 * @throws IOException
	 */
	public static void saveRating(String fileName, List<Rating> al) throws IOException{
		List<String> alw= new ArrayList<>();
		for(int i=0;i<al.size();i++){
			Rating rate= al.get(i);
			StringBuilder st= new StringBuilder();

			st.append(rate.getName().trim());
			st.append(SEPARATOR);

			st.append(rate.getMobile()+"");
			st.append(SEPARATOR);

			st.append(rate.getEmail().trim());
			st.append(SEPARATOR);

			st.append(rate.getTitle().trim());
			st.append(SEPARATOR);

			st.append(rate.getRating()+"");
			st.append(SEPARATOR);

			st.append(rate.getReview().trim());
			st.append(SEPARATOR);

			st.append(rate.getDateTime().trim());
			alw.add(st.toString());
		}
		write(fileName,alw);
	}

	/**
	 * Save list of ratings back to file
	 *
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static List<Rating> readRating(String filename) throws IOException{
		List<String> stringArray= (ArrayList) read(filename);
		List<Rating> alr= new ArrayList<>();

		for(int i=0;i<stringArray.size();i++){
			String st= stringArray.get(i);

			StringTokenizer star= new StringTokenizer(st, SEPARATOR);

			String name= star.nextToken().trim();
			int mobile= Integer.parseInt(star.nextToken().trim());
			String email= star.nextToken().trim();
			String title= star.nextToken().trim();
			int rating= Integer.parseInt(star.nextToken().trim());
			String review= star.nextToken().trim();
			String date_time= star.nextToken().trim();

			Rating cur= new Rating(name,mobile,email,title,rating,review,date_time);

			alr.add(cur);

		}
		return alr;
	}

	/**
	 * Read list of lines from a specified file
	 *
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static List read(String fileName) throws IOException {
		List data = new ArrayList() ;
	    Scanner scanner = new Scanner(new FileInputStream(fileName));
	    try {
	    	while (scanner.hasNextLine()){
	    		data.add(scanner.nextLine());
	    	}
	    }
	    finally{
	      scanner.close();
	    }
	    return data;
	}

	/**
	 * Save list of lines to a file.
	 *
	 * @param fileName
	 * @param data
	 * @throws IOException
	 */
	public static void write(String fileName, List data) throws IOException  {
	    PrintWriter out = new PrintWriter(new FileWriter(fileName));

	    try {
			for (int i =0; i < data.size() ; i++) {
	      		out.println(data.get(i));
			}
	    }
	    finally {
	      out.close();
	    }
	}
}
