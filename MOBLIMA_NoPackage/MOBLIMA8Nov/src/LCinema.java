
/**
 * LCinema hold an object for cinema with L type
 *
 * @author SS3-grp6
 *
 */
public class LCinema extends Cinema{
	/**
	 * Construct Lcinema with initial 3 sections adn 19 rows
	 */
	public LCinema(){
		super(3,19);

		for (int i = 0; i<rows; i++) {
			for (int j = 0; j<sections; j++) {
				if (i<rows-3) {
					if (j==0||j==2) {
						layout[i][j] = new NormalRow(4);
					}
					else if (j==1){
						layout[i][j] = new NormalRow(10);
					}
				}
				else {
					if (j == 0||j==2) {
						layout[i][j] = new CoupleRow(4);
					}
					else if (j==1){
						if (i<rows-2)
							layout[i][j] = new CoupleRow(10);
						else
							layout[i][j] = new EmptyRow(10);
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
	 * Check whether the cinema is premium type.
	 *
	 * @return false;
	 */

	public boolean isPremium(){
		return false;
	}
}
