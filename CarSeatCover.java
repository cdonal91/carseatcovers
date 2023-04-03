/*
    CarSeatCover.java is to set up the fields, constructor, getters, and setters for the user to enter their product preferences
 */

import java.util.HashSet;
import java.util.Set;

public class CarSeatCover {

    //Fields
    private final String productName;
    private final long productCode;
    private final float price;
    private float minPrice;
    private float maxPrice;
    private final String theme;
    private Set<Type> allTypes = new HashSet<>();
    //private Type type;
    private final String description;
    private String userType;


    //Constructor

    /**
     * constructor to initialise a CarSeatCover object with product name, product code, price, theme, type, and description
     *
     * @param productName the car seat covers product name
     * @param productCode the car seat covers product code
     * @param price       the car seat covers product price
     * @param theme       the car seat covers product theme
     * @param allTypes    is the enum of the car seat covers product type
     * @param description the car seat covers product description
     */
    public CarSeatCover(String productName, long productCode, float price, String theme, Set<Type> allTypes, String description) {
        this.productName = productName;
        this.productCode = productCode;
        this.price = price;
        this.theme = theme;
        if (allTypes != null) this.allTypes = new HashSet<>(allTypes);
        //this.type = type;
        this.description = description;
    }

    //Getters
    /* @return the products name */
    public String getProductName() {
        return productName;
    }

    /* @return the products code */
    public long getProductCode() {
        return productCode;
    }

    /* @return the products price */
    public float getPrice() {
        return price;
    }

    /* @return the users min price */
    public float getMinPrice() {
        return minPrice;
    }

    /* @return the users max price */
    public float getMaxPrice() {
        return maxPrice;
    }

    /* @return the products theme */
    public String getTheme() {
        return theme;
    }

    /*
     * contains the String of all the types
     * @return a Set (HashSet) of all the different types */
    public Set<Type> getAllTypes() {
        return new HashSet<>(allTypes);
    }

    /* @return the products type */
    //public Type getType() {
    //   return type;
    //}

    /* @return the products description */
    public String getDescription() {
        return description;
    }

    /* @return the users type choice */
    public String getUserType() {
        return userType;
    }

    //setters
    /*
       By allowing the user to search based on a price range (rather than price values) improves functionality
       by offering client more product options in their price range. Rather that just offering one product for the exact value.
       You might also have cases where nothing matches that exact value so the user wouldn't buy anything.
    */

    /* Setting the users min price */
    public void setMinPrice(float minPrice) {
        this.minPrice = minPrice;
    }

    /* Setting the users max price */
    public void setMaxPrice(float maxPrice) {
        this.maxPrice = maxPrice;
    }

    /* Setting the users type choice */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String formatProductDescription() {
        StringBuilder infoToShow = new StringBuilder();
        infoToShow.append("\nItem name: ").append(this.getProductName()).append("\nCaption: ").append(this.getDescription()).append("\nProduct code: ").append(this.getProductCode()).append("\nTheme: ").append(this.getTheme()).append("\nPrice: ").append(this.getPrice()).append("\n");
        return infoToShow.toString();
    }

}

