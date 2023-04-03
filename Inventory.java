import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
*  Inventory.java sets the carSeatCovers list and adds a car into the list. Creates a list for themes.
* method for comparing the user entry to the products in the inventory.txt file */

public class Inventory {

    // field
    private final List<CarSeatCover> carSeatCovers = new ArrayList<>();

    // method to add a car seat to the above field
    public void addCarSeatCover(CarSeatCover carseatcover) {this.carSeatCovers.add(carseatcover);}

    /* Adding the themes to a set. Called back in getUserCriteria */
    public Set<String> getAllThemes(){
        Set<String> allThemes = new HashSet<>();
        for (CarSeatCover c: carSeatCovers){
            allThemes.add(c.getTheme());
        }
        return allThemes;
    }

    /* Method to find a matching car seat cover between the user entry and what is stored in inventory.txt*/
    public List<CarSeatCover> findMatch(CarSeatCover dreamProduct) {
        List<CarSeatCover> productMatches = new ArrayList<>();
        for (CarSeatCover c: this.carSeatCovers) {
            if (c.getPrice() < dreamProduct.getMinPrice() || c.getPrice() > dreamProduct.getMaxPrice()) continue;
            if(!c.getTheme().equals(dreamProduct.getTheme())) continue;
            //for(int x=0; x<listTypes.size; x++)
            if (!c.getAllTypes().contains(dreamProduct.getAllTypes().iterator().next())) continue;
            if(dreamProduct.getAllTypes().size()==0) productMatches.add(c);
            else{
                Set<Type> commonTypes = new HashSet<>(dreamProduct.getAllTypes());
                if(commonTypes.size()>0) productMatches.add(c);
            }
        }
        return productMatches;
    }

}

