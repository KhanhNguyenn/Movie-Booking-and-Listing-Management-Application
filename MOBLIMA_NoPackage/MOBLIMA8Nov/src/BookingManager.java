import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * BookingManager hold list of CustomerTransaction and Price system.
 * It supports prepareForBooking, processBooking, calculates booking price,
 * processPayment, modify a CustomerTransaction and checkSale, listTop5BySales. 
 *
 * @author SS3-grp6
 *
 */
public class BookingManager {
	protected static List <CustomerTransaction> bookings;
	public CustomerManager Xuser;
	private final String BOOKING_FILENAME = "TransactionHistory";
	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
	private static Price bookingPrice;
	private static final MoblimaScanner sc = new MoblimaScanner();

	/**
	 * Construct bookingManager() with initialize list of current stored CustomerTransaction
	 */
	public BookingManager() {
		this.updateBookings(null);
		this.Xuser = new CustomerManager(bookings);
		bookingPrice = new Price();
	}

	/**
	* Get the price of bookings
	*
	* @return bookingPrice
	*/
	public Price getBookingPrice(){
		return bookingPrice;
	}

	/**
	 * Initialize the status of cinema x with current bookings of listingCode
	 * of a specific movie title.
	 * <br>
	 * It will check all bookings of a specific movie title according to listingCode
	 * to initialize the cinema for further bookings
	 *
	 * @param listingCode
	 * @param x
	 */
	public void prepareForBooking(String listingCode, Cinema x) {
		//Initialize cinema to original conditions (0 bookings)
		x.reset();

		//Restore past bookings
		for (int i=0; i<bookings.size(); i++) {
			CustomerTransaction cust = bookings.get(i);
			if (cust.getListingID().contentEquals(listingCode)) {
				String[] seats = cust.getSeats().split(" ");
				for (int j=0; j<seats.length; j++) {
					x.assignSeat(seats[j]);
				}
			}
		}
	}

	/**
	 * Proceed and save bookings of a specific moviegoer.
	 * <br>
	 * Require information of moviegoer and their bookings(listingCode, cinema and seats) to store
	 *
	 * @param listingCode
	 * @param x
	 * @param seats
	 * @return 1 if store successfully, otherwise -1
	 */
	public int processBooking(String listingCode, Cinema x, String seats) {
		System.out.println("\nYour Seats: "+seats);

		System.out.println("\nDo you wish to confirm your selection? \n(1) YES\n(2) NO");

		if (sc.next().contentEquals("1")) {
			System.out.print("\nPlease enter your name (Input -1 to exit): ");
			sc.nextLine();

			String inputName = sc.nextLine();
			if(inputName.contentEquals("-1")) {return -1;}

			String name = inputName.replaceAll("\\s+", "_");

			int age = 0;
			while (true){
				try{
					System.out.print("\nPlease enter your age (Input -1 to exit): ");
					age = sc.nextInt();
					if (age < -1){
						System.out.println("Please do not enter a negative age.");
						continue;
					}
					else {
						break;
					}
				} catch (Exception e){
					sc.next(); // clear scanner wrong input
					System.out.println("Invalid input for age input");
				}
			}
			if (age == -1) {return -1;}

			int mobilenum = sc.inputMobileNum();
			if (mobilenum == -1) {return -1;}

			System.out.print("\nPlease enter email address (Input -1 to exit): ");
			String email = sc.next();
			if (email.contentEquals("-1")) {return -1;}

			LocalDateTime now = LocalDateTime.now();
			String TID = listingCode.concat(dtf.format(now));
			CustomerTransaction curr = new CustomerTransaction(name, age, mobilenum, email, TID, seats);

			if (processPayment(curr, x)) {
				System.out.println("\n========================");
				System.out.println("\nBooking Successful!");
				this.updateBookings(curr);
				QR qr = new QR();
				qr.make_QR(curr);
				return 1;
			}
		}
		return -1;
	}

	/**
	 * Make payments according to CustomerTransaction and type of cinema.
	 * <br>
	 * Calculate the price based on show time and type of movie
	 *
	 * @param cust
	 * @param x
	 * @return true if payment is successful, otherwise false
	 */
	public static boolean processPayment(CustomerTransaction cust, Cinema x) {
		double price = bookingPrice.getBasePrice();
		// Normal: Weekday 10.5, Weekend/PH 13
		// Premium (imax): Weekday 14, Weekend/PH 16.5
		// Age <21 or >65 then any ticket will -3 dollars

		// Check if movie date is a special date/weekend
		if (x.isPremium()) {price += bookingPrice.getMarkUpIMAX();}
		if (cust.getthreeD()) {price += bookingPrice.getMarkUp3D();} // 3D markup
		if (SpecialDatesManager.if_holiday(cust.getMovieDate())){
			price += bookingPrice.getMarkUpSpecialDay();
		}
		else {
			if (cust.getAge()<=21 || cust.getAge()>=65) {price -= bookingPrice.getAgeDiscount();}
		}

		String totalPrice = String.format("%.2f", price*cust.getNumSeats());
		System.out.println("\nTotal Price: $"+totalPrice);

		System.out.print("\nPlease enter your credit card number: ");
		sc.next();

		//Assumes payment is always successful
		return true;
	}

	/**
	 * Add new CustomerTransaction to list of transactions
	 *
	 * @param cust
	 */
	public void updateBookings(CustomerTransaction cust) {
		bookings = new ArrayList <CustomerTransaction>();
		try {
			ArrayList<CustomerTransaction> al = DataStore.readTransactions(BOOKING_FILENAME);
			if (cust!=null) {
				al.add(cust);
				this.Xuser.update(cust);
			}
			for (int i = 0; i<al.size(); i++) {
				bookings.add((CustomerTransaction)al.get(i));
			}
			DataStore.saveTransactions(BOOKING_FILENAME, al);
		} catch (Exception e) {
			System.out.println("IOException > " + e.getMessage());
		}
	}

	/**
	 * List top 5 movies by number of bookings
	 */
	public void listTop5BySales() {
		int numMovies = MovieManager.catalogue.size();
		String[] result = new String[numMovies];
		for (int i = 0; i < numMovies; i++) {
			result[i] = MovieManager.catalogue.get(i).getTitle();
			for (int j = i; j > 0; j--) {
				if (checkSales(result[j]) > checkSales(result[j - 1])) {
					String temp = result[j];
					result[j] = result[j - 1];
					result[j - 1] = temp;
				}
			}
		}
		System.out.println("\n========================");
		System.out.println("\nTop 5 Movies by Sales:");
		for (int i = 0; i < 5; i++) {
			System.out.println(
					"\n(" + (i + 1) + ") " + result[i] + ": " + checkSales(result[i]) + " tickets");
		}
	}

	/**
	 * Calculate the number of seats booked for a specific movie
	 * @param title
	 * @return number of booked seats
	 */
	public int checkSales(String title) {
		List <MovieListing> shows = new ArrayList <MovieListing>();
		for (int i=0; i<ListingManager.showtimes.size(); i++) {
			if (ListingManager.showtimes.get(i).getTitle().contentEquals(title)) {
				shows.add(ListingManager.showtimes.get(i));
			}
		}
		int sales = 0;
		for (int i=0; i<bookings.size(); i++) {
			for (int j=0; j<shows.size(); j++) {
				if (bookings.get(i).getListingID().contentEquals(shows.get(j).getListingCode())) {
					sales += bookings.get(i).getNumSeats();
				}
			}
		}
		return sales;
	}
}
