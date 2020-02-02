
/**
 * Manages an cineplex with supporting of makeBooking and so on. 
 *
 * @author SS3-grp6
 *
 */
public class CineplexManager {
	protected static Cineplex[] locations;
	private static final MoblimaScanner sc = new MoblimaScanner();

	/**
	 * Construct cineplexManager with initializing 3 cineplexes.
	 */
	public CineplexManager() {
		locations = new Cineplex[3];

		//Plaza Singapura
		locations[0] = new Cineplex(1,1,2,0,0);

		//Great World City
		locations[1] = new Cineplex(1,0,2,1,0);

		//Marina Square
		locations[2] = new Cineplex(0,2,1,0,1);
	}

	/**
	 * Get cinema and cineplex from a specific listinCode.
	 *
	 * @param listingCode
	 * @return a specific cinema in a specfic cineplex
	 */
	public Cinema getCinema(String listingCode) {
		int cineplexIndex = Character.getNumericValue(listingCode.charAt(0));
		int cinemaIndex = Character.getNumericValue(listingCode.charAt(2));
		return locations[cineplexIndex].theatres[cinemaIndex];
	}

	/**
	 * Make bookings in a specific cinema.
	 * Display cinema with current status of existing status of seats to make urther bookings.
	 *
	 * @param x
	 * @return list of seats choosen
	 */
	public String makeBooking(Cinema x) {
		x.displaycinema();

		System.out.println("\nHow many seats would you like to book? (Max. 20)");
		int numSeats = sc.inputInt(0, 20);
		if (numSeats == -1) {return "-1";}

		int[][] seats = new int[numSeats][2];
		String seatString = "";
		boolean done;

		for(int i=0; i<numSeats; i++) {
			done = false;
			while(!done) {
				try {
					System.out.println("\nPlease choose a seat. Occupied seats are marked with XX. (ENTER '-1' to EXIT)");
					int[] result = sc.inputSeat();

					if (result[0] == -20 && result[1] == 1){
						for (int j=0;j<i;j++) {
							x.unreserveSeat(seats[j][0], seats[j][1]);
						}
						return "-1";
					} // Exit condition, input == "-1"

					if(x.getLayout()[result[0]][0] instanceof CoupleRow) {
						if (result[1]%2==0) {
							result[1] -= 1;
						}
					}

					if (x.reserveSeat(result[0], result[1])==1) {
						seats[i] = result;

						if(x.getLayout()[result[0]][0] instanceof CoupleRow) {
							if (i<=numSeats-2) {
								i++;
								seats[i][0] = result[0];
								seats[i][1] = result[1]+1;
							}
							else {
								x.unreserveSeat(result[0], result[1]);
								throw new Exception();
							}
						}

						System.out.println("Selection Successful!\n");
						System.out.print("Current selection: [");

						char row;
						for(int j=0; j<=i; j++) {
							row = 'A';
							row += seats[j][0];
							System.out.print(" "+row+(seats[j][1])+" ");
							if (i==numSeats-1) {
								seatString = seatString.concat(row+Integer.toString(seats[j][1])+' ');
							}
						}
						System.out.println("]");
						done = true;

					}
					else {
						System.out.println("Seat Occupied!");
					}
				}
				catch (Exception e) {
					System.out.println("\nInvalid choice!");
                    System.out.println("You have " + (numSeats-i) + " more seats to book.");
				}


			}
		}
		return seatString;
	}

	/**
	* Chcek whether a cinema is valid based on checking whether
	* exists a single empty seat or not.
	*
	* @param Cinema x
	* @return true if valid, otherwise false
	*/
	public boolean checkNoSingleEmptySeat(Cinema x) {
		boolean valid = x.checkNoSingleEmptySeat();
		x.displaycinema();

		if (valid) {return true;}
		else {
			System.out.println("\nInvalid seats selection! Cannot leave a single empty seat!\n");
			return false;
		}
	}

	/**
	 * List of cineplex for moviegoer to choose
	 *
	 * @return cineplex choosen
	 */
	public int chooseLocation(){
		System.out.println("========================");
		System.out.println("\nPlease choose your preferred location: ");

		System.out.println("(1) Plaza Singapura");
		System.out.println("(2) Great World City");
		System.out.println("(3) Marina Square");

		return sc.inputInt(1, 3)-1;
	}
}
