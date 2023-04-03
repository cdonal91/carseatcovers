/*
    Geek.java is to set up the fields, constructor, and getters for the user to enter their ph num and name.
    This is used in CarSeatCoverSearch.java in the methods getUsersDetails and writeOrderRequestsToFile.
 */

public class Geek {

    //fields
    private final String name;
    private final long phoneNumber;

    /**
     * constructor to initialise a Geek object with name, ph num
     *
     * @param name        the program user's full name
     * @param phoneNumber the program user's 10-digit phone number
     */
    public Geek(String name, long phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    //getters

    /**
     * @return the program user's name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the program user's 10 digit ph num
     */
    public long getPhoneNumber() {
        return phoneNumber;
    }
}


