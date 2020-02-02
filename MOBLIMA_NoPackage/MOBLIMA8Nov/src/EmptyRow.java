
/**
 * EmptyRow to hold an row object with empty status
 *
 * @author SS3-grp6
 *
 */
public class EmptyRow extends Row{
	/**
	 * Construct an empty row with initial number of seats
	 *
	 * @param numSeats
	 */
	public EmptyRow(int numSeats) {
		super(numSeats);
	}

	/**
	 * Display row with empty seats
	 */
	public void displayrow() {
		for (int i = 0; i<this.width; i++) {
			System.out.print("    ");
		}
	}

	/**
	 * Reserve a seat according to seatID in a row
	 */
	public int reserveSeat(int seatID) throws Exception{
		throw new Exception();
	}

	/**
	 * Reset status of all seats in a row
	 */
	public void reset() {
	}

	/**
	* Check whether th row is valid (with no single empty seat)
	*
	*@return true (the empty row is always valid)
	*/
	public boolean checkNoSingleEmptySeat() {return true;}
}
