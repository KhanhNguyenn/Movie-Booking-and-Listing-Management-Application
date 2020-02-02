
/**
 * A MovieListing represents a particular showtime of a Movie.
 * It holds attributes such as movie title, listingCode (unique identifier), location and time information.
 *
 * @author SS3 Group 6
 *
 */
public class MovieListing implements Comparable<MovieListing> {
	private String title;
	private String listingCode;
	private int cineplexIndex;
	private int cinemaIndex;
	private String dateTime;

	/**
	 * Constructor method which creates a new MovieListing object based on the input parameters.
	 *
	 * @param datetime
	 * @param title
	 * @param cineplex
	 * @param cinema
	 */
	public MovieListing(String datetime, String title, int cineplex, int cinema) {
		this.title = title;
		this.cineplexIndex = cineplex;
		this.cinemaIndex = cinema;
		this.listingCode = Integer.toString(cineplex)+"c"+Integer.toString(cinema)+datetime;
		this.dateTime = datetime;
	}

	/**
	 * Getter method for listing title
	 *
	 * @return String representing title
	 */
	public String getTitle() {return title;}

	/**
	 * Getter method for listing code
	 *
	 * @return String representing listing code
	 */
	public String getListingCode() {return listingCode;}

	/**
	 * Getter method for Cineplex information
	 *
	 * @return int representing Cineplex index
	 */
	public int getCineplex() {return cineplexIndex;}

	/**
	 * Getter method for Cinema information
	 *
	 * @return int representing Cinema index
	 */
	public int getCinema() {return cinemaIndex;}

	/**
	 * Getter method for listing date and time
	 *
	 * @return String representing listing date and time
	 */
	public String getDateTime() {return dateTime;}

	/**
	 * This method reads the movie listing code and determines if the show is 3D
	 *
	 * @return boolean indicating if listing is 3D
	 */
	public boolean getThreeD() {
		if (listingCode.contains("3D"))
			return true;
		else
			return false;
	}

	/**
	 * This method processes listing date and time, and returns a String which contains
	 * the same information in a user-friendly format.
	 *
	 * @return String representing printable date and time
	 */
	public String printableDateTime() {return dateTime.substring(6,8)+'/'+dateTime.substring(4,6)+'/'+dateTime.substring(0,4)
	+' '+dateTime.substring(8, 12)+'H';}


	/**
	 * This method displays details of the listing, such as title, location and dateTime.
	 */
	public void showListing() {
		System.out.print("Movie: "+title);
		if (getThreeD())
			System.out.print(" (3D)");
		if ((cineplexIndex==0 || cineplexIndex==1) && cinemaIndex==0) {System.out.print(" (IMAX)");}
		System.out.println("\nShow Time: "+dateTime.substring(6,8)+'/'+dateTime.substring(4,6)+'/'+dateTime.substring(0,4)
		+' '+dateTime.substring(8, 12)+'H');

		String cineplex="";

		switch(cineplexIndex) {
		case 0:
			cineplex = "Plaza Singapura";
			break;
		case 1:
			cineplex = "Great World City";
			break;
		case 2:
			cineplex = "Marina Square";
			break;
		}
		System.out.println("Location: "+cineplex+", Hall "+(cinemaIndex+1));
	}

	/**
	 * This method compares MovieListings based on location and dateTime,
	 * which allows us to sort them efficiently in the system.
	 */
	public int compareTo(MovieListing other) {
		if (this.getCineplex() > other.getCineplex()) {return 1;}
		else if (this.getCineplex() == other.getCineplex()){
			if (this.getDateTime().compareTo(other.getDateTime())>0) {return 1;}
			else {
				return -1;
			}
		}
		else {return -1;}
	}

	/**
	 * This method determines if the input listing code corresponds to the listing
	 *
	 * @param listingCode
	 * @return boolean indicating if this listing has the same listing code
	 */
	public boolean sameListing(String listingCode) {
		if (this.getListingCode().contentEquals(listingCode)) {
			return true;
		}
		else {return false;}
	}
}
