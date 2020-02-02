import java.util.Scanner;

/**
 * Main class for application.
 * Manage all commands or queries to application.
 *
 * @author SS3-grp6
 *
 */
public class Moblima {
	private CineplexManager Xcineplex;
	private MovieManager Xmovie;
	private ListingManager Xlisting;
	private BookingManager Xbooking;
	private StaffManager Xstaff;
	private RatingManager Xrating;
	private static final MoblimaScanner sc = new MoblimaScanner();

	/**
	 * Main method to run MOBLIMA app
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to MOBLIMA!");

		Moblima app = new Moblima();
		String listingCode = "";
		int choice = menu();

		while (choice != -1) {
			switch(choice) {
			case 1:
				listingCode = app.browseAllMovies();
				if (listingCode.contentEquals("-1")) {break;}
				else {app.booking(listingCode);}
				break;
			case 2:
				listingCode = app.browseByTheatres();
				if (listingCode.contentEquals("-1")) {break;}
				else {app.booking(listingCode);}
				break;
			case 3:
				app.topMovies();
				break;
			case 4:
				app.Xrating.rate(app.Xbooking);
				break;
			case 5:
				app.Xbooking.Xuser.customerCheckBooking();
				break;
			case 6:
				app.Xstaff.staffLogin();
				break;
			default:
				break;
			}

			choice = menu();
		}
		System.out.println("\nThank you!");
	}

	/**
	 * Constructor of MOBLIMA app
	 *
	 */
	public Moblima() {
		Xcineplex = new CineplexManager();
		Xmovie = new MovieManager();
		Xlisting = new ListingManager();
		Xbooking = new BookingManager();
		Xstaff = new StaffManager(Xmovie, Xlisting, Xbooking);
		Xrating= new RatingManager();
	}

	/**
	 * List of command choices for MOBLIMA app.
	 * <br>
	 * User choose requests from menu:
	 * <ul>
	 * 	<li> (1) Browse All Available Movies
	 * 	<li> (2) Searches Movies By Theatres
	 * 	<li> (3) Check Movie Rankings
	 * 	<li> (4) Rate Movies
	 * 	<li> (5) Check Booking History
	 * 	<li> (6) Staff login
	 * <ul>
	 *
	 * @return choice from menu
	 */
	public static int menu(){
		System.out.println("\n========================");
		System.out.println("\nHow may we help you today?\n");

		System.out.println("(1) Browse All Available Movies");
		System.out.println("(2) Search Movies by Theatres");
		System.out.println("(3) Check Movie Rankings");
		System.out.println("(4) Rate Movies");
		System.out.println("(5) Check Booking History");
		System.out.println("(6) Staff Login");

		return sc.inputInt(1, 6);
	}

	/**
	 * Proceed and save bookings of a specific moviegoer.
	 *
	 * @param listingCode
	 */
	public void booking(String listingCode) {
		Cinema x = this.Xcineplex.getCinema(listingCode);

		String seats = "-1";
		boolean valid = false;

		while(!valid) {
			Xbooking.prepareForBooking(listingCode,x);
			seats = Xcineplex.makeBooking(x);
			valid = Xcineplex.checkNoSingleEmptySeat(x);
			if (!valid) {
				System.out.println("Do you wish to select again?\n(1) YES\n(2) NO");
				int choice = sc.inputInt(1, 2);
				if (choice == -1 || choice == 2) {
					valid = true;
					seats = "-1";
				}
			}
		}

		if (seats == "-1") {return;}
		else {Xbooking.processBooking(listingCode,x,seats);}
	}

	/**
	 * Show list of movies along with show times for user to make bookings.
	 * <br>
	 * User choose in order:
	 * <ul>
	 * <li>title
	 * <li>show time
	 * <li>choose seat
	 * <li> make bookings
	 * <ul>
	 *
	 * @return listingCode for movie choosen
	 */
	public String browseAllMovies(){
		String title = Xmovie.chooseMovie(-1);
		if (title.contentEquals("-1")) {return title;}
		return (Xlisting.displayTimings(title, -1));
	}

	/**
	 * Show list of cineplexes to view movies at.
	 * <br>
	 * User choose in order:
	 * <ul>
	 * <li>cinelex
	 * <li>movie and title
	 * <li>make bookings
	 * <ul>
	 *
	 * @return listingCode for movie choosen
	 */
	public String browseByTheatres(){
		int cineplex = Xcineplex.chooseLocation();
		if (cineplex == -2) {return "-1";}
		String title = Xmovie.chooseMovie(cineplex);
		if (title.contentEquals("-1")) {return title;}

		return (Xlisting.displayTimings(title,cineplex));
	}

	/**
	 * List top movies by Sales or Ratings
	 *
	 */
	public void topMovies() {
		System.out.println("\n========================");
		System.out.println("\nList Top Movies By:\n");
		System.out.println("(1) Sales");
		System.out.println("(2) Ratings");

		int choice = sc.inputInt(1, 2);

		switch (choice) {
		case 1:
			Xbooking.listTop5BySales();
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
