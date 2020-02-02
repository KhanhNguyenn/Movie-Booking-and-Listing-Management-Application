
import java.io.IOException;
import java.util.*;


/**
 * a MovieManager handles all movies existing in the system.
 *
 * @author SS3 Group 6
 *
 */
public class MovieManager {
	protected static List <Movie> catalogue;
	private final String MOVIE_FILENAME = "Movies";
	private static final MoblimaScanner sc = new MoblimaScanner();

	/**
	 * Constructor class which initializes list of movies using the DataStore class
	 */
	public MovieManager() {
		catalogue = new ArrayList <Movie>();
		try {
			ArrayList al = DataStore.readCatalogue(MOVIE_FILENAME);
			for (int i = 0; i<al.size(); i++) {
				catalogue.add((Movie)al.get(i));
			}
		} catch (Exception e) {
			System.out.println("IOException > " + e.getMessage());
		}
	}

	/**
	 * Get catalogue;
	 *
	 * @return catalogue
	 */
	public static List <Movie> getCatalogue(){
		return catalogue;
	}

	/**
	 * This method allows a Customer to choose the movie he wants, to
	 * view movie details, Customer ratings, and book the movie.
	 *
	 * In our implementation, depending on the Customer's choice, they can choose from
	 * all Movies, or just from Movies showing at a particular location
	 *
	 * It also saves all changes to the system.
	 *
	 * @param location, which represents the cineplexIndex of the location chosen by the Customer. If the search is not location specific, location = -1.
	 *
	 * @return String, which represents the title of the chosen Movie
	 */
	public String chooseMovie(int location){
		for (int i=0; i<catalogue.size(); i++) {
			if (catalogue.get(i).bookable()) {
				catalogue.get(i).showMovie();
				if (location != -1) {
					String title = catalogue.get(i).getTitle();
					for (int j=0; j<ListingManager.showtimes.size(); j++) {
						MovieListing curr = ListingManager.showtimes.get(j);
						if (curr.getTitle().contentEquals(title)&&curr.getCineplex()==location) {
							System.out.println("\n ["+curr.printableDateTime()+"]");
						}
					}
				}
			}
		}
		System.out.println("\n========================");

		int sel = sc.inputMovieCode();
		if (sel == -1) {return "-1";}

		Movie target = catalogue.get(sel);

		if (location != -1) {return target.getTitle();}
		else {

			target.displayMovieDetails();

			System.out.println("\n========================");
			System.out.println("\n(1) Book this movie");
			System.out.println("(2) View Reviews");

			int choice = sc.inputInt(1, 2);

			switch (choice) {
			case 1: return target.getTitle();
			case 2:
				target.showRatings();
				System.out.println("\n========================");
				System.out.println("\n(1) Book this movie");
				System.out.println("(2) Back");

				choice = sc.inputInt(1, 2);
				break;
			case -1: return chooseMovie(-1);
			default:
				break;
			}

			if (choice == 1) {return target.getTitle();}
			else {return chooseMovie(-1);}
		}
	}

	/**
	 * This method presents options to Staff looking to update the list of movies
	 */
	protected void update_movies(){
		int choice = 0;

		System.out.println("\nUpdate Movies\n");
		System.out.println("(1) Add Movie");
		System.out.println("(2) Delete Movie");
		System.out.println("(3) Update Movie Showing Status");

		choice = sc.inputInt(1, 3);

		switch(choice){
		case 1:
			add_movies();
			break;
		case 2:
			remove_movies();
			break;
		case 3:
			update_movie_status();
			break;
		case -1: return;
		default: break;
		}
	}

	/**
	 * This method adds a new Movie to the system, with attributes defined by staff input.
	 */
	protected void add_movies() {
		System.out.println("\nCurrent Movies:\n");
		for (int i = 0; i<catalogue.size(); i++){
			System.out.println(catalogue.get(i).getCode() + ". " + catalogue.get(i).getTitle());
		}
		System.out.print("\nWhat is the Movie Code of the new movie: ");
		char movieCode = sc.nextChar();

		for (int i = 0; i<catalogue.size(); i++) {
			if (catalogue.get(i).getCode() == movieCode) {
				System.out.println("Movie Code already exists.");
				return;
			}
		}

		System.out.print("\nMovie Title of the new movie: ");
		sc.nextLine();
		String title = sc.nextLine();
		for (int i = 0; i<catalogue.size(); i++) {
			if (catalogue.get(i).getTitle().equals(title)) {
				System.out.println("Movie already exists.");
				return;
			}
		}

		System.out.println("\nWhat is the Status of the new movie");
		System.out.println("(1) Coming Soon \n(2) Preview \n(3) Now Showing \n(4) End of Showing");
		String status = "";

		int sel = sc.inputInt(1, 4);

		switch(sel) {
		case 1:
			status = "Coming Soon";
			break;
		case 2:
			status = "Preview";
			break;
		case 3:
			status = "Now Showing";
			break;
		case 4:
			status = "End of Showing";
			break;
		case -1: return;
		default: break;
		}

		System.out.println("\nWhat is the Age Rating of the new movie?");
		System.out.println("(1) PG \n(2) NC16 \n(3) M18 \n(4) R21");
		String rating = "";

		sel = sc.inputInt(1, 4);

		switch(sel) {
		case 1:
			status = "PG";
			break;
		case 2:
			status = "NC16";
			break;
		case 3:
			status = "M18";
			break;
		case 4:
			status = "R21";
			break;
		case -1: return;
		default: break;
		}

		System.out.println("\nWhat is the Synopsis of the new Movie");
		sc.nextLine();
		String synopsis = sc.nextLine();

		System.out.println("\nWhat is the Cast of the new movie?");
		String cast = sc.nextLine();

		System.out.println("\nWho is the Director of the new movie?");
		String director = sc.nextLine();

		Movie added = new Movie(movieCode, title, status, rating, synopsis, cast, director);
		catalogue.add(added);
		try{
			DataStore.saveCatalogue(MOVIE_FILENAME, catalogue);
			System.out.println("\nMovie added successfully");
		}catch (IOException e)
		{
			System.out.println("IOException > " + e.getMessage());
		}
	}

	/**
	 * This method is used for staff to remove existing movies.
	 * A movie can only be removed if it has no existing listings.
	 *
	 * It also saves all changes to the system.
	 */
	protected void remove_movies(){
		System.out.println("\nCurrent Movies:\n");
		for (int i = 0; i<catalogue.size(); i++){
			System.out.println(catalogue.get(i).getCode() + ". " + catalogue.get(i).getTitle());
		}
		System.out.print("\nWhich movie do you want to delete: ");
		char movieCode = sc.nextChar();

		Movie found = null;
		for (int i = 0; i<catalogue.size(); i++) {
			if (catalogue.get(i).getCode() == movieCode) {
				found = catalogue.get(i);
			}
		}
		if (found == null){
			System.out.println("\nMovie Code does not exist.");
			return;
		}

		String title = found.getTitle();
		for (int i = 0; i<ListingManager.showtimes.size(); i++) {
			if (ListingManager.showtimes.get(i).getTitle().contentEquals(title)) {
				System.out.println("\nCannot remove movie which has existing listings!");
				return;
			}
		}
		catalogue.remove(found);

		try{
			DataStore.saveCatalogue(MOVIE_FILENAME, catalogue);
			System.out.println("\nMovie deleted successfully");
		}catch (IOException e)
		{
			System.out.println("IOException > " + e.getMessage());
		}
	}

	/**
	 * This method updates the showing status of a movie chosen by the staff.
	 * It handles the logic which prevents the status of movies with existing listings to be changed to 'End of Showing'
	 *
	 * It also saves all changes to the system.
	 */
	protected void update_movie_status() {
		System.out.println("\nCurrent Movies:\n");
		for (int i = 0; i<catalogue.size(); i++){
			System.out.println(catalogue.get(i).getCode() + ". " + catalogue.get(i).getTitle());
		}
		System.out.println("\nWhich movie do you want to update?");
		char movieCode = sc.nextChar();
		Movie found = null;

		for (int i = 0; i<catalogue.size(); i++) {
			if (catalogue.get(i).getCode() == movieCode) {
				found = catalogue.get(i);
			}
		}
		if (found == null){
			System.out.println("Movie Code does not exist.");
			return;
		}

		String title = found.getTitle();
		boolean stillHasShows = false;

		for (int i = 0; i<ListingManager.showtimes.size(); i++) {
			if (ListingManager.showtimes.get(i).getTitle().contentEquals(title)) {
				stillHasShows = true;
				break;
			}
		}

		found.changeStatus(stillHasShows);

		try{
			DataStore.saveCatalogue(MOVIE_FILENAME, catalogue);
		}catch (IOException e)
		{
			System.out.println("IOException > " + e.getMessage());
		}
	}

	/**
	 * This method finds a Movie object (if it exists), based on the movie title
	 *
	 * @param title
	 * @return Movie object, if it exists. If it does not exist, null is returned
	 */
	public static Movie findMovie(String title) {
		for (int i = 0; i<catalogue.size(); i++) {
			if (catalogue.get(i).getTitle().contentEquals(title)) {
				return catalogue.get(i);
			}
		}
		return null;
	}
}
