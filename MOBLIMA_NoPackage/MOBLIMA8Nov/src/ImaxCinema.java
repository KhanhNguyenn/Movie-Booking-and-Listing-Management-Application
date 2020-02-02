
/**
 * ImaxCinema hold for an cinema object with imax type
 *
 * @author SS3-grp6
 *
 */
public class ImaxCinema extends Cinema {
	/**
	 * Construct imaxcinema with initial 6 sections, 12 rows.
	 */
	public ImaxCinema() {
		super(6,12);

		for (int i = 0; i<rows; i++) {
			for (int j = 0; j<sections; j++) {
				if (j == 0||j == 5) {
					if (i<rows-2) {
						layout[i][j] = new NormalRow(3);
					}
					else {
						layout[i][j] = new EmptyRow(3);
					}
				}
				else if (j == 1||j == 4) {
					if (i<rows-1 && i>2) {
						layout[i][j] = new NormalRow(3);
					}
					else {
						layout[i][j] = new EmptyRow(3);
					}
				}
				else {
					if (i>4) {
						layout[i][j] = new NormalRow(2);
					}
					else {
						layout[i][j] = new EmptyRow(2);
					}
				}
			}
		}

		this.width = 0;
		for (int i=0; i<sections; i++) {
			this.width += layout[0][i].getWidth();
		}
	}

	/**
	 * Check whether cinema is premium
	 * @return true
	 */
	public boolean isPremium() {
		return true;
	}
}
