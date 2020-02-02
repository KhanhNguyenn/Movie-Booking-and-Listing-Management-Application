
/**
 * Scinema hold for cinema object of type S.
 *
 * @author SS3-grp6
 *
 */
public class SCinema extends Cinema{
	/**
	 * Construct of Scinema with initial 1 section and 12 rows
	 */
	public SCinema(){
		super(1,12);

		for (int i = 0; i<rows; i++) {
			for (int j = 0; j<sections; j++) {
				layout[i][j] = new NormalRow(12);
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
