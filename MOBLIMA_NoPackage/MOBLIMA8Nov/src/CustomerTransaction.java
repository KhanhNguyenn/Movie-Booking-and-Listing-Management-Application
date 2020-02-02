
/**
 * CustomerTransaction create a single record of bookings.
 * Each object contains name of transaction id, name, age,
 * mobile, email, seats.
 *
 * @author SS3-grp6
 */
public class CustomerTransaction {
	private String TID;
	private String name ;
	private int Age;
	private int mobile ;
	private String email ;
	private String seats;
	private static final String[] locations = {"Plaza Singapura", "Great World City", "Marina Square"};

	/**
	 * Construct CustomerTransaction with initial name, age, mobile, email,
	 * transaction id,seat.
	 *
	 * @param name
	 * @param Age
	 * @param mobile
	 * @param email
	 * @param TID
	 * @param seats
	 */
	public CustomerTransaction(String name, int Age, int mobile, String email, String TID, String seats) {
		this.TID = TID;
		this.name = name;
		this.Age = Age;
		this.mobile = mobile;
		this.email = email;
		this.seats = seats;
	}

	/**
	 * Get name of moviegoer.
	 *
	 * @return name of booker
	 */
	public String getName() {return name;}
	/**
	 * Get age of moviegoer.
	 *
	 * @return age of booker
	 */
	public int getAge() {return Age;}
	/**
	 * Get mobile number of moviegoer.
	 *
	 * @return  mobile number of booker
	 */
	public int getMobile() {return mobile;}
	/**
	 * Get email of moviegoer.
	 *
	 * @return email of booker
	 */
	public String getEmail() {return email;}

	/**
	 * Get listingCode of that transaction for a movie booked.
	 *
	 * @return listingCode
	 */
	public String getListingID() {return TID.substring(0,17);}
	/**
	 * Get transaction id
	 *
	 * @return transactionID
	 */
	public String getTID() {return TID;}

	/**
	* Get location of the show booked.
	*
	* @return string of cineplex and hall
	*/
	public String getShowLocation(){
		int cineplexIndex = Character.getNumericValue(TID.charAt(0));
		int cinemaIndex = Character.getNumericValue(TID.charAt(2)) + 1;
		return locations[cineplexIndex]+", Hall: "+cinemaIndex;
	}

	/**
	* Get date_time of the show
	*
	* @return showtime
	*/
	public String printableDateTime() {
		String dateTime = getMovieDate();
		return dateTime.substring(6,8)+'/'+dateTime.substring(4,6)+'/'+dateTime.substring(0,4)+ ", "+ dateTime.substring(8,12)+"H";
	}

	/**
	 * Get list of seats booked
	 *
	 * @return list of seat booked
	 */
	public String getSeats() {return seats;}
	/**
	 * Get number of seats booked
	 *
	 * @return number of booked seats
	 */
	public int getNumSeats() {return seats.split(" ").length;}

	/**
	 * Check whether current customer already booked for some movie
	 *
	 * @param name
	 * @param mobile
	 * @param email
	 * @return true if customer has booked a movie, otherwise false
	 */
	public String getMovieDate() {return TID.substring(3,15);}

	/**
  *Check whether the movie booked is #D or not.
  *
  *@return true if movie booked is 3D, otherwise false
  */
	public boolean getthreeD() {
		if (TID.contains("3D"))
			return true;
		else
			return false;
	}

	/**
	 * Get information of bookingss
	 *
	 * @param show
	 */
	public void showTransaction(MovieListing show) {
		System.out.println("\n==========================\n");
		String TID = this.getTID();
		System.out.println("Booking Date: "+TID.substring(21,23)+'/'+TID.substring(19,21)+'/'+TID.substring(15,19)+'\n');
		show.showListing();
		System.out.println("Seats: "+this.getSeats());
	}
}
