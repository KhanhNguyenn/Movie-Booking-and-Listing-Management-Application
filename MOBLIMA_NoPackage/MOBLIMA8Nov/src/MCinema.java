
/**
 * MCinema hold for cinema object of type M
 *
 * @author SS3-grp6
 *
 */
public class MCinema extends Cinema{
	/**
	 * Construct Mcinema type with initial 2 sections, 15 rows.
	 */
	public MCinema(){
		super(2,15);

		for (int i = 0; i<rows; i++) {
			for (int j = 0; j<sections; j++) {
				if (i<rows-1) {
					layout[i][j] = new NormalRow(8);
				}
				else {
					layout[i][j] = new CoupleRow(8);
				}
			}
		}

		this.width = 0;
		for (int i=0; i<sections; i++) {
			this.width += layout[0][i].getWidth();
		}
	}

	/**
	 * Check whether the cinema is premium type.
	 *
	 * @return false;
	 */

	public boolean isPremium(){
		return false;
	}
}
