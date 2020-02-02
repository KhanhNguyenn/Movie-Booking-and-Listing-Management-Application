
/**
 * Hold for row object of type couple with status of booking.
 *
 * @author SS3-grp6
 *
 */
public class CoupleRow extends Row{
	/**
	 * Construc a coupleRow with initial number of couple seats in that row
	 *
	 * @param numSeats
	 */
	public CoupleRow(int numSeats) {
		super(numSeats);
		for (int i = 0; i < seatlist.length; i+=2) {
			seatlist[i] = new CoupleSeat();
		}
	}

	/**
	 * Display a row with status of seats
	 */
	public void displayrow() {
		for (int i = 0; i < width; i+=2) {
			seatlist[i].displayseat();
		}
	}

	/**
	 * Reset status of bookings of all seats in a row
	 */
	public void reset() {
		for (int i = 0; i < seatlist.length; i+=2) {
			seatlist[i].reset();
		}
	}

	/**
	 * Assign a specific seat in row
	 *
	 * @param seatID
	 */
	public void assignSeat(int seatID) {
		if (seatID%2==0) {seatlist[seatID].assign();}
	}

	/**
	* Check whether the row is valid based on existence of empty single seat.
	*
	*@return true (always valid for coupleRow)
	*/
	public boolean checkNoSingleEmptySeat() {return true;}
}
