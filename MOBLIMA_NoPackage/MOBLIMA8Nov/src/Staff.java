
public class Staff
{
    private String username;
    private String password;

    /**
     * Construct Staff with username and password
     * @param username
     * @param password
     */
    public Staff(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    /**
     * Check whether a person is a staff or not.
     *
     * @param username
     * @param password
     * @return true if the person is staff, otherwise false
     */
    public int authenticate(String username, String password) {
    	if ((this.getUsername().equals(username))&&(this.getPassword().equals(password))) {
			return 1;
		}
    	else if (this.getUsername().equals(username)&&(!this.getPassword().equals(password))) {
			return 0;
		}
    	else {return -1;}
    }

    /**
     * Get username of the staff.
     *
     * @return username
     */
    public String getUsername(){return username;}
    /**
     * Get password of the staff.
     *
     * @return password
     */
    public String getPassword(){return password;}
}
