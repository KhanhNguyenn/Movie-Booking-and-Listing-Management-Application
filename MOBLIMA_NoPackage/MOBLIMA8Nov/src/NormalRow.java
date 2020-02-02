
/**
 * NormalRow hold object for normal row.
 * It can set status of seat, check valid seat and display it.
 *
 * @author SS3-grp6
 *
 */
public class NormalRow extends Row{
	/**
	 * Construct normal row with number of seats
	 *
	 * @param numSeats
	 */
	public NormalRow(int numSeats) {
		super(numSeats);
		for (int i = 0; i < seatlist.length; i++) {
			seatlist[i] = new Seat();
		}
	}

	/**
	 * Display row with status of each seat in that row
	 */
	public void displayrow() {
		for (int i = 0; i < width; i++) {
			seatlist[i].displayseat();
		}
	}

	/**
	* Check whether the row is valid or not based on checking whether exists single empty seat.
	*
	*@return true if valid, otherswise false
	*
	*/
	public boolean checkNoSingleEmptySeat() {
		int run = 0;
		boolean valid = true;

		for (int i=0; i<width; i++) {
			if (!(seatlist[i].isReserved()||seatlist[i].isOccupied())){
				run++;
			}
			else {
				if (run == 1) {
					seatlist[i-1].setWarning();
					valid = false;
					run = 0;
				}
				else {
					run=0;
				}
			}
		}
		if (run == 1) {
			seatlist[width-1].setWarning();
			valid = false;
		}
		return valid;
	}
}
