
/**
 * Hold an cinema object and operations on it.
 *
 * @author SS3-grp6
 *
 */
 public abstract class Cinema {
	protected int rows;
	protected int sections;
	protected Row[][] layout;
	protected int width;

	private final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/**
	 * Construct cinema with initial number of sections and rows
	 *
	 * @param sections
	 * @param rows
	 */
	public Cinema(int sections, int rows) {
		this.rows = rows;
		this.sections = sections;
		layout = new Row[rows][sections];
	}

	/**
	 * Display layout of cinema with current status of each seat.
	 */
	public void displaycinema() {
		this.displayscreen();

		System.out.print("   ");
		int curr = 0;
		for (int i = 0; i<sections; i++) {
			for (int j = 0; j< layout[0][i].getWidth();j++) {
				System.out.printf(" %2d ",(curr+j+1));
			}
			curr += layout[0][i].getWidth();
			System.out.print("     ");
		}
		System.out.println();

		for (int i = 0; i<rows; i++) {
			System.out.print(alphabet.charAt(i));
			for (int j = 0; j<sections; j++) {
				if (j>0){
					switch(i%7) {
					case 0:
						System.out.print("A");
						break;
					case 1:
						System.out.print("I");
						break;
					case 2:
						System.out.print("S");
						break;
					case 3:
						System.out.print("L");
						break;
					case 4:
						System.out.print("E");
						break;
					default:
						System.out.print(" ");
						break;
					}
				}

				System.out.print(" |");
				layout[i][j].displayrow();
				System.out.print("| ");
			}
			System.out.print(alphabet.charAt(i));
			System.out.println();
		}
		this.displayentrance();
	}

	/**
	 * Display the layout of screen
	 */
	private void displayscreen() {
		System.out.println();
		int space = 4 + (this.sections-1)*5 + 4*this.width;

		for (int i = 0; i<space/2-5; i++) {
			System.out.print("=");
		}
		System.out.print(" S C R E E N ");
		for (int i = 0; i<space/2-6; i++) {
			System.out.print("=");
		}
		System.out.print("\n\n");
	}

	/**
	 * Display layout of entry.
	 */
	private void displayentrance() {

		System.out.println("\n");

		int space = 4 + (this.sections-1)*5 + 4*this.width;

		for (int i = 0; i<space/2-7; i++) {
			System.out.print("=");
		}
		System.out.print("|   ENTRANCE   |");
		for (int i = 0; i<space/2-7; i++) {
			System.out.print("=");
		}
		System.out.print("\n");
	}

	/**
	 * Set status of seat according to rowID and seatID to reserved.
	 *
	 * @param rowID
	 * @param seatID
	 * @return result[][], result[0]- rowID, result[1]- columnID
	 * @throws Exception
	 */
	public int reserveSeat(int rowID, int seatID) throws Exception {
		int result;
		int[] index = findSeatIndex(rowID, seatID);

		result = layout[rowID][index[0]].reserveSeat(index[1]-1);
		return result;
	}

	/**
	 * Set status of seat according to rowID and seatID to assigned.
	 *
	 * @param rowID
	 * @param seatID
	 */
	public void assignSeat(String seat) {
		int rowID = seat.charAt(0)-'A';
		int[] index = findSeatIndex(rowID, Integer.parseInt(seat.substring(1)));
		layout[rowID][index[0]].assignSeat(index[1]-1);
	}

	/**
	 * Clear status reserved of seat according to rowID and seatID.
	 *
	 * @param rowID
	 * @param seatID
	 */
	public void unreserveSeat(int rowID, int seatID) {
		int[] index = findSeatIndex(rowID, seatID);
		layout[rowID][index[0]].unreserveSeat(index[1]-1);
	}

	/**
	* Check whether cinema eixsts sinle empty seat.
	*
	*@return true if exists, otherwise false
	*/
	public boolean checkNoSingleEmptySeat() {
		boolean valid = true;

		for (int i = 0; i<rows; i++) {
			for (int j = 0; j<sections;j++) {
				if (!layout[i][j].checkNoSingleEmptySeat()) {
					valid = false;
				}
			}
		}
		return valid;
	}

	/**
	 * Find seat index according to rowID and seatID
	 *
	 * @param rowID
	 * @param seatID
	 * @return
	 */
	public int[] findSeatIndex(int rowID, int seatID) {
		int[] result = new int[2];
		result[0] = -1;
		result[1] = seatID;

		for (int i = 0; i<sections; i++) {
			if (result[1]<=layout[0][i].getWidth()) {
				result[0] = i;
				break;
			}
			else {
				result[1] -= layout[0][i].getWidth();
			}
		}
		return result;
	}

	/**
	 * Get layout;
	 *
	 * @return layout
	 */
	public Row[][] getLayout(){
		return layout;
	}
	/**
	 * Check whether the cinema is premium type.
	 *
	 * @return true if cinema is premium, otherwise false
 	 */
	public abstract boolean isPremium();

	/**
	 * Reset status of seats in cinema to empty.
	 */
	public void reset() {
		for (int i = 0; i<rows; i++) {
			for (int j = 0; j<sections;j++) {
				layout[i][j].reset();
			}
		}
	}
}
