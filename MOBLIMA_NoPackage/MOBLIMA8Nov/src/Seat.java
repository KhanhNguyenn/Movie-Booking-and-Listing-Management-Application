
public class Seat {
	private boolean occupied;
	private boolean reserved;
	private boolean warning;

	protected static final String ANSI_RESET = "\u001B[0m";
	protected static final String ANSI_RED = "\u001b[38;5;196m";
	protected static final String ANSI_GREEN = "\u001b[38;5;40m";
	protected static final String ANSI_BLUE = "\u001b[38;5;26m";
	protected static final String ANSI_ORANGE = "\u001b[38;5;214m";

	/**
	 * Construct an empty seat
	 */
	public Seat() {
		occupied = false;
		reserved = false;
		warning = false;
	}

	/**
	 * Check whether this seat is already booked.
	 *
	 * @return true if assigned, otherwise false
	 */
	public boolean isOccupied() {
		return this.occupied;
	}

	/**
	 * Check whether this seat is already reserved.
	 *
	 * @return true if reserved, otherwise false
	 */
	public boolean isReserved() {
		return this.reserved;
	}

	/**
	 * Check whether this seat is warning.
	 *
	 * @return true if the seat is warning, otherwise false
	 */
	public boolean isWarning() {
		return this.warning;
	}

	/**
	 * Reserve the seat.
	 *
	 * @return 1 if can reserve, otherwise 0
	 */
	public int reserve() {
		if (!occupied && !reserved) {
			reserved = true;
			return 1;
		}
		else {
			return 0;
		}
	}

	/**
	 * Assign the seat
	 */
	public void assign() {
		occupied = true;
	}
	/**
	 * Set the seat to empty.
	 * Clear all status of the seat
	 */
	public void reset() {
		occupied = false;
		reserved = false;
		warning = false;
	}

	/**
	 * Unreserve the seat
	 */
	public void unreserve() {
		reserved = false;
	}

	/**
	 * Set the seat to warning
	 */
	public void setWarning() {
		warning = true;
	}

	/**
	 * Display layout of seat
	 */
	public void displayseat() {
		String occ;
		if (this.isOccupied()) {
			occ = ANSI_RED + "XX" + ANSI_RESET;
		}
		else if (this.isReserved()){
			occ = ANSI_GREEN+"ME"+ANSI_RESET;
		}
		else if (this.isWarning()) {
			occ = ANSI_ORANGE+"!!"+ANSI_RESET;
		}
		else {
			occ = ANSI_BLUE+"__"+ANSI_RESET;
		}
		System.out.print("[" + occ + "]");
	}
}
