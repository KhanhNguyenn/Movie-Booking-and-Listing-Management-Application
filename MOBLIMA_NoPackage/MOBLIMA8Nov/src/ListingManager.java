
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * ListingManager hold list of MovieListing and manage the Listings
 * such as add, remove,displpay and so on.
 *
 * @author SS3-grp6
 *
 */
public class ListingManager {
	protected static List <MovieListing> showtimes;
	private final String MOVIELISTING_FILENAME = "MovieListing";
	private static final MoblimaScanner sc = new MoblimaScanner();

	/**
	 * Construct listingManager with initializing the list of (MovieListing) showtimes
	 */
	public ListingManager() {
		try{
			showtimes = DataStore.readListing(MOVIELISTING_FILENAME);
			Collections.sort(showtimes);
		} catch (Exception e) {
			System.out.println("Showtimes file could not be read.");
		}

	}

	/**
	 * Get showtimes.
	 *
	 * @return showtimes
	 */
	public static List <MovieListing> getShowtimes(){
		return showtimes;
	}

	/**
	 * Display list of show times for a specific movie at a cineplex for customer to choose
	 *
	 * @param movieTitle
	 * @param cineplex
	 * @return listingCode choosen
	 */
	public String displayTimings(String movieTitle, int cineplex) {
		List <MovieListing> result = new ArrayList <MovieListing>();
		int count = 0;
		for (int i=0; i<showtimes.size(); i++) {
			MovieListing curr = showtimes.get(i);
			if (curr.getTitle().contentEquals(movieTitle)) {
				if (cineplex != -1) {
					if (curr.getCineplex()!=cineplex) {continue;}
				}

				result.add(curr);
				count++;
				System.out.println("\n========================");
				System.out.println("("+(count)+")");
				curr.showListing();
			}
		}
		if (count == 0) {
			System.out.println("\n========================");
			System.out.println("\n===== SORRY! NO SHOWS CURRENTLY AVAILABLE =====");
			return "-1";
		}
		System.out.println("\n========================");

		int sel = sc.inputInt(1, count);
		if (sel == -1) {return "-1";}
		else {return result.get(sel-1).getListingCode();}
	}

	/**
	 * Update movie listing by Add listing or Remove Listing.<br>
	 */
	protected void updateListing(){
		System.out.println("\nUpdate Listings:\n");
		System.out.println("(1) Add Listing");
		System.out.println("(2) Remove Listing");

		int choice = sc.inputInt(1, 2);
		switch(choice) {
		case 1:
			addListing();
			break;
		case 2:
			removeListing();
			break;
		case -1:
			return;
		default:
			break;
		}
	}

	/**
	 * Add new MovieListing.
	 * List information of current movie listing.
	 * Prompt to input date_time, movie_title, cineplex, hall
	 */
	public void addListing(){
		List <Movie> movies = MovieManager.catalogue;

		System.out.println("\nMovies currently bookable:\n");
		for (int i = 0; i<movies.size(); i++) {
			if (movies.get(i).bookable()) {
				System.out.println(((char)('A'+(i))) + ". " + movies.get(i).getTitle());
			}
		}
		int choice = sc.inputMovieCode();
		if (choice == -1) {return;}
		String movie_title = movies.get(choice).getTitle();

		String dimensions;
		System.out.println("\nIs this movie in 3D: true/false(default)");
		sc.nextLine();
		Boolean threeD = Boolean.parseBoolean(sc.nextLine());
		if (threeD == true)
			dimensions = "3D";
		else
			dimensions = "2D";

		String date_time = null;
		while (true) {
			try {
				System.out.print("\nPlease enter the date & time in yyyyMMddHHmm format: ");
				date_time = sc.next();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
				dateFormat.setLenient(false);
				dateFormat.parse(date_time.trim());
				break;
			} catch (Exception e) {
				continue;
			}
		}

		System.out.println("\nAvailable Cineplexes");
		System.out.println("(1) Plaza Singapura");
		System.out.println("(2) Great World City");
		System.out.println("(3) Marina Square");

		int cineplexIndex = sc.inputInt(1, 3) - 1;
		String cineplex="";

		switch (cineplexIndex) {
		case 0:
			cineplex = "Plaza Singapura";
			break;
		case 1:
			cineplex = "Great World City";
			break;
		case 2:
			cineplex = "Marina Square";
			break;
		case -2:
			return;
		default:
			break;
		}

		System.out.print("Choose a hall. Available halls: [ ");
		for (int i = 0; i < CineplexManager.locations[cineplexIndex].theatres.length; i++) {
			System.out.print((i + 1) + " ");
		}
		System.out.println("]");

		int hall = sc.inputInt(1, CineplexManager.locations[cineplexIndex].theatres.length) - 1;

		for (int i = 0; i < showtimes.size(); i++) {
			if (showtimes.get(i).getCineplex() == cineplexIndex && showtimes.get(i).getCinema() == hall
					&& showtimes.get(i).getDateTime().equals(date_time)) {
				System.out.println("\n" + cineplex + " hall " + (hall + 1) + " is already booked on " + date_time);
				System.out.println("\nReturning to menu");
				return;
			}
		}

		MovieListing newlisting = new MovieListing(date_time+dimensions, movie_title, cineplexIndex, hall);
		showtimes.add(newlisting);
		Collections.sort(showtimes);
		try {
			DataStore.saveListing(MOVIELISTING_FILENAME, showtimes);
			System.out.println("New Listing successfully added");
			return;
		} catch (Exception e) {
			System.out.println("Exception occurred in adding new listing.");
			return;
		}
	}
	/**
	 * Remove MovieListing from current list.<br>
	 * List all current listings to choose MovieListing to remove.
	 */
	protected void removeListing()
	{
		String cineplex = null;
		for (int i = 0; i < showtimes.size(); i++){
			int cineplexindex = showtimes.get(i).getCineplex();
			if (cineplexindex == 0)
				cineplex = "Plaza Singapura";
			else if (cineplexindex == 1)
				cineplex = "Great World City";
			else if (cineplexindex == 2)
				cineplex = "Marina Square";
			System.out.println((i+1) + ". " + showtimes.get(i).getTitle() + " at " + cineplex + " hall " + showtimes.get(i).getCinema()
			+ " on " + showtimes.get(i).getDateTime());
		}

		System.out.println("Which listing do you wish to remove:");
		int choice = sc.inputInt(1, showtimes.size())-1;
		if (choice == -2) {return;}

		String listingCode = showtimes.get(choice).getListingCode();
		for (int i = 0; i<BookingManager.bookings.size(); i++) {
			if (BookingManager.bookings.get(i).getListingID().contentEquals(listingCode)) {
				System.out.println("\nCannot remove listings which have already been booked!");
				return;
			}
		}

		showtimes.remove(choice);
		try{
			DataStore.saveListing(MOVIELISTING_FILENAME, showtimes);
			System.out.println("Listing successfully removed");
		} catch(Exception e){
			System.out.println("Exception occurred in removing listing.");
		}

	}
}
