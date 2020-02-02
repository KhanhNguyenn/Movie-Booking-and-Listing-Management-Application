
/**
 * Hold for seat object of type couple wiht status of booking
 *
 * @author SS3-grp6
 *
 */
public class CoupleSeat extends Seat{
	/**
	 * Construct couple seat with intial status is empty
	 */
	public CoupleSeat() {
		super();
	}
	/**
	 * Display couple seat with status
	 */
	public void displayseat() {
		String occ;
		if (this.isOccupied()) {
			occ = ANSI_RED+"XX"+ANSI_RESET+".."+ANSI_RED+"XX"+ANSI_RESET;
		}
		else if (this.isReserved()){
			occ = ANSI_GREEN+"ME"+ANSI_RESET+".."+ANSI_GREEN+"ME"+ANSI_RESET;
		}
		else {
			occ = ANSI_BLUE+"__"+ANSI_RESET+".."+ANSI_BLUE+"__"+ANSI_RESET;
		}
		System.out.print("[" + occ + "]");
	}

}
