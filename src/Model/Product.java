package Model;

/**
 * @author Brian Nason, Student Number xxxxxxxxx
 */

import Model.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class holds the information for Parts as well as the information for the parts associated with them
 */
public class Product {

    /**
     * @param associatedParts Holds all the parts associated with the product
     * @param id The id of the associated part
     * @param name The Name of the associated part
     * @param price The price of the associated part
     * @param stock The Stock of the associated part
     * @param min The Minimum inventory value of the associated part
     * @param max The Maximum value of the associated part
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;


    /**
     *
     * @param id The Product's ID
     * @param name The Product's Name
     * @param price The Product's Price
     * @param stock The Product's Inventory Level
     * @param min The Product's Minimum Inventory Level
     * @param max The Product's Maximum Inventory Level
     */
    public Product(int id, String name, double price, int stock, int min, int max)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @param part the part to add
     */
    public void addAssociatedPart(Part part) {associatedParts.add(part);}

    /**
     * @param selectedAssociatedPart the part to delete
     * @return boolean true or false of delete
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {return false;}

    /**
     * @return all associated parts of the product
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

}