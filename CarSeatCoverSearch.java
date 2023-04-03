import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/* CarSeatCoverSearch.java has a main method that uses the methods getUserDetails(), getUserCriteria(), loadInventory(), writeOrderRequestToFile(), and findMatch() */
public class CarSeatCoverSearch {

    // fields
    private final static String appName = "SuperGeek Auto";
    private final static String filePath = "./inventory.txt";
    private static Inventory carSeatCovers;

    /**
     * main method used to interact with the user
     * @param args not required
     */
    public static void main(String[] args) {
        carSeatCovers = loadInventory();

        JOptionPane.showMessageDialog(null, "Welcome to SuperGeek Auto!\n\tTo start, click OK.", appName, JOptionPane.QUESTION_MESSAGE);
        CarSeatCover dreamProduct = getUserCriteria();
        List<CarSeatCover> potentialMatches = carSeatCovers.findMatch(dreamProduct);
        if(potentialMatches.size()>0){
            Map<String,CarSeatCover> options = new HashMap<>();
            StringBuilder infoToShow = new StringBuilder("Matches found!! The following products meet your criteria: \n");
            for (CarSeatCover potentialMatch : potentialMatches) {
                infoToShow.append(potentialMatch.formatProductDescription());
                options.put(potentialMatch.getProductName(), potentialMatch);
            }
            String order = (String) JOptionPane.showInputDialog(null,infoToShow+"\n\nPlease select which car seat cover you'd like to order:",appName, JOptionPane.QUESTION_MESSAGE,null,options.keySet().toArray(), "");
            if(order==null) System.exit(0);
            else{
                CarSeatCover chosenCarSeatCover = options.get(order);
                Geek applicant = getUserDetails();
                writeOrderRequestToFile(applicant, chosenCarSeatCover, dreamProduct.getAllTypes().iterator().next());
                JOptionPane.showMessageDialog(null, "Thank you! Your order request has been submitted. " +
                        "One of our friendly staff will be in touch shortly.", appName, JOptionPane.QUESTION_MESSAGE);
            }

            // result for no matches
        } else JOptionPane.showMessageDialog(null, "Unfortunately none of our car seat covers meet your criteria :(" +
                "\n\tTo exit, click OK.", appName, JOptionPane.QUESTION_MESSAGE);
        System.exit(0);
    }

    /**
     * method to get user to input name, ph num, with appropriate input validation
     * @return a Geek object representing the user of the program
     */
    private static Geek getUserDetails(){
        // asking for user name
        String name;
        do {
            name = JOptionPane.showInputDialog(null, "Please enter your full name.", appName, JOptionPane.QUESTION_MESSAGE);
            if(name==null) System.exit(0);
        }while (!name.contains(" "));

        // ask for user phone number
        long phoneNumber = 0;
        String phoneNumberInput;
        do {
            phoneNumberInput = JOptionPane.showInputDialog(null,"Please enter your phone number. Format: 0412838475",appName, JOptionPane.QUESTION_MESSAGE);
            if (phoneNumberInput == null) System.exit(0);
            try {
                phoneNumber = Long.parseLong(phoneNumberInput);
            } catch (NumberFormatException n) {
                JOptionPane.showMessageDialog(null, "Invalid entry. Please enter your 10 digit phone number.");
            }
        }while(phoneNumberInput.length()!=10);

        return new Geek(name, phoneNumber);
    }

    /**
     * generates JOptionPanes requesting user input for car seat cover type, theme, min and max price
     * @return a CarSeatCover object representing the user's dream product
     */
    private static CarSeatCover getUserCriteria(){

        // asking for users type
        Type type = (Type) JOptionPane.showInputDialog(null,"Please select your preferred type:",appName, JOptionPane.QUESTION_MESSAGE,null,Type.values(),Type.U30);
        if(type==null) System.exit(0);

        //String userType = Type.valueOf(type);

        // asking for users theme
        String theme  = (String) JOptionPane.showInputDialog(null,"Please select your preferred theme.",appName, JOptionPane.QUESTION_MESSAGE,null, carSeatCovers.getAllThemes().toArray(), "");
        if(theme==null) System.exit(0);

        // asking for users min price
        float minPrice = -1, maxPrice = -1;
        while(minPrice==-1) {
            try {
                minPrice = Float.parseFloat(JOptionPane.showInputDialog(null,"Please enter the lowest price ",appName,JOptionPane.QUESTION_MESSAGE));
            }
            catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Invalid input. Please try again.");
            }
        }

        // asking for users max price
        while(maxPrice<minPrice) {
            try {
                maxPrice = Float.parseFloat(JOptionPane.showInputDialog(null,"Please enter the highest price ",appName,JOptionPane.QUESTION_MESSAGE));
            }
            catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Invalid input. Please try again.");
            }
            if(maxPrice<minPrice) JOptionPane.showMessageDialog(null,"Highest price must be >= lowest price.");
        }

        CarSeatCover dreamProduct = new CarSeatCover("", 0, -1, theme, Collections.singleton(type), "");
        dreamProduct.setMinPrice(minPrice);
        dreamProduct.setMaxPrice(maxPrice);
        //dreamProduct.setUserType(userType);
        return dreamProduct;
    }

    /**
     * method to load all car seat cover data from file, using the data objects in an instance of Inventory
     * @return an CarSeatCovers object - this is a dataset of the data objects
     */
    private static Inventory loadInventory() {
        Inventory carSeatCovers = new Inventory();
        Path path = Path.of(filePath);
        Type type =Type.U30;
        List<String> carSeatData = null;
        try{
            carSeatData = Files.readAllLines(path);
        }catch (IOException io){
            System.out.println("Could not load the file. \nError message: "+io.getMessage());
            System.exit(0);
        }

        // looping through the inventory.txt file to get the different elements
        for (int i=1;i<carSeatData.size();i++) {
            String[] elements = carSeatData.get(i).split("\\[");
            String[] carInfo = elements[0].split(",");
            // getting the product name
            String productName = carInfo[0];
            // getting the product code and convert to long
            long productCode = 0;
            try{
                productCode = Long.parseLong(carInfo[1]);
            }
            catch (NumberFormatException n){
                System.out.println("Error in file. Microchip number could not be parsed for dog on line "+(i+1)+". Terminating. \nError message: "+n.getMessage());
                System.exit(0);
            }

            // getting the price and convert to float
            float price = 0;
            try{
                price = Float.parseFloat(carInfo[2]);
            }catch (NumberFormatException n){
                System.out.println("Error in file. Age could not be parsed for dog on line "+(i+1)+". Terminating. \nError message: "+n.getMessage());
                System.exit(0);
            }

            // getting the themes
            String theme = carInfo[3];

            // creating a list for the data in types []
            String[] TypesArray = elements[1].replace("],", "").split(",");
            Set<Type> getAllTypes = new HashSet<>();
            for (String types: TypesArray) {
                getAllTypes.add(Type.valueOf(types.toUpperCase().strip()));
            }

            // getting the description within the description []
            String description = elements[2].replace("]","");

            for(String myStr: elements){
                System.out.println(myStr);
            }
            CarSeatCover carSeatCover = new CarSeatCover(productName, productCode, price, theme, getAllTypes, description);
            carSeatCovers.addCarSeatCover(carSeatCover);
        }
        return carSeatCovers;
    }

    /**
     * provides Greek Geek with a file containing the user's order request
     * @param geek a Geek object representing the user
     * @param carSeatCover a CarSeatCover object representing the product that the user wants to order
     */
    private static void writeOrderRequestToFile(Geek geek, CarSeatCover carSeatCover, Type type) {
        // removes spacing and replace with _
        String filePath = geek.getName().replace(" ","_")+"_"+carSeatCover.getProductCode()+"_"+type.name()+".txt";
        Path path = Path.of(filePath);
        // printing out the user and order details
        String lineToWrite = "Order details: \n   Name: "+geek.getName()+"\n   Phone number: 0"+geek.getPhoneNumber()+"\n   Item: "+carSeatCover.getProductName()+" ("+carSeatCover.getProductCode()+
                ")\n   Size: "+type.name();
        try {
            Files.writeString(path, lineToWrite);
        }catch (IOException io){
            System.out.println("File could not be written. \nError message: "+io.getMessage());
            System.exit(0);
        }
    }


}
