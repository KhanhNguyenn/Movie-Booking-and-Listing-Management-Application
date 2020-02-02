
/**
 * Hold for row object.
 * It supports display with status and reserve or assign a seat in the row.
 *
 * @author SS3-grp6
 *
 */
public abstract class Row {
	protected int width;
	protected Seat[] seatlist;

	/**
	 * Construct row with number of seats
	 *
	 * @param numOfSeats
	 */
	public Row(int numOfSeats) {
		width = numOfSeats;
		seatlist = new Seat[width];
	}
	/**
	 * Display row with status of each seat in that row,
	 * which will be overrided by child classes of rows.
	 */
	public abstract void displayrow();
	/**
	* Check whether a row is valid or not based on whether there exists a single empty seat.
	*
	*@return true if valid, otherwise false
	*/
	public abstract boolean checkNoSingleEmptySeat();

	/**
	 * Get width of the row
	 *
	 * @return width of row
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * Set the status of seat to reserved.
	 *
	 * @param seatID
	 * @return 1 if can reserve, otherwise 0
	 * @throws Exception
	 */
	public int reserveSeat(int seatID) throws Exception{
		return seatlist[seatID].reserve();
	}

	/**
	 * Set status of seat according to seatID to assigned
	 *
	 * @param seatID
	 */
	public void assignSeat(int seatID) {
		seatlist[seatID].assign();
	}

	/**
	 * Clear status reserved of seat according to seatID
	 *
	 * @param seatID
	 */
	public void unreserveSeat(int seatID) {
		seatlist[seatID].unreserve();
	}

	/**
	 * Empty all seats in the row.
	 */
	public void reset() {
		for (int i=0; i<width; i++) {
			seatlist[i].reset();
		}
	}
}
