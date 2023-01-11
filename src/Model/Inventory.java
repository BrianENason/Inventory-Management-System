package Model;

/**
 * @author Brian Nason, Student Number xxxxxxxxx
 */

import Controller.DataCheck;
import Controller.DeleteConfirm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class will keep track of, hold, and manipulate the inventory of the parts and the products.
 */
public class Inventory {

    /**
     * @param allParts This ObservableList will hold the parts inventory
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

     /**
      * @param allProducts This ObservableList will hold the products inventory
      */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * @param newPart the part to add
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * @param newProduct the product to add
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * This method looks through the allParts list and selects the part the user is searching for
     * @param partId the ID of the part to lookup
     * @return the part that matches the input id or null if no part is found
     */
    public static Part lookupPart(int partId) {

        for (Part part : getAllParts())
        {
            if (part.getId() == partId)
            {
                System.out.println("Match Found!");
                return part;
            }
        }

        System.out.println("No Match");
        DataCheck.popupWindow("Your search for: " + partId, "\n Has no Part ID Match", "No ID Match");
        return null;
    }

    /**
     * This method looks through the allProducts list and selects the product the user is searching for
     * @param productId the ID of the Product to look up
     * @return the product that matches the input id of null if no product is found
     */
    public static Product lookupProduct(int productId) {

        for (Product product : getAllProducts())
        {
            if (product.getId() == productId)
            {
                System.out.println("Match Found!");
                return product;
            }
        }

        System.out.println("No Match");
        DataCheck.popupWindow("Your search for: " + productId, "\n Has no Product ID Match", "No ID Match");
        return null;
    }

    /**
     * This method looks through the allParts list to find the part(s) that matches by name
     * @param partName the Name (of string type) of the part to look up
     * @return the part or parts that match either all or part of the input string
     */
    public static ObservableList<Part> lookupPart(String partName) {

        /**
         * @param lowercase This will hold the lowercase value of the user input string
         * @param lowerCasePartName This will hold the lowercase value of the part's name
         * @param filteredParts This list will hold any matching parts to return as a result
         */
        String lowercase = partName.toLowerCase();
        CharSequence lowerCasePartName = lowercase;
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();

        /**
         * This will check to see if the filteredParts list is empty. If not, it will clear it
         */
        if(!(filteredParts.isEmpty()))
            filteredParts.clear();

        /**
         * This will search the parts one-by-one to find if they at all match the input string.
         * For every match, it adds the part to the temp list called filtered parts
         */
        for (Part part : Inventory.getAllParts())
        {
            if (part.getName().equalsIgnoreCase(partName) || (part.getName()).toLowerCase().contains(lowerCasePartName))
            {
                System.out.println("Part found by name");
                filteredParts.add(part);
            }
        }

        /**
         * If the filteredParts list is empty at the end of the search, then no parts were found.
         */
        if(filteredParts.isEmpty())
        {
            System.out.println("No Match");
            DataCheck.popupWindow("Your search for: " + partName, "\n Has no Part Name Match", "No Name Match");
            return Inventory.getAllParts();
        }

        /**
         * If the filteredParts list is not empty, then it is returned as a result to populate the parts table with
         */
        return filteredParts;
    }

    /**
     * This method looks through the allProducts list to find the product(s) that match by name
     * @param productName the name (of string type) of the product to look up
     * @return the product or products that match either all or part of the input string
     */
    public static ObservableList<Product> lookupProduct(String productName)
    {

        /**
         * @param lowercase This will hold the lowercase value of the user input string
         * @param lowerCaseProductName This will hold the lowercase value of the product's name
         * @param filteredProducts This list will hold any matching products to return as a result
         */
        String lowercase = productName.toLowerCase();
        CharSequence lowerCaseProductName = lowercase;
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

        /**
         * This will check to see if the filteredProducts list is empty. If not, it will clear it
         */
        if(!(filteredProducts.isEmpty()))
            filteredProducts.clear();

        /**
         * This will search the products one-by-one to find if they at all match the input string.
         * For every match, it adds the product to the temp list called filteredProducts
         */
        for (Product product : Inventory.getAllProducts())
        {
            if (product.getName().equalsIgnoreCase(productName) || (product.getName()).toLowerCase().contains(lowerCaseProductName))
            {
                System.out.println("Product found by name");
                filteredProducts.add(product);
            }
        }

        /**
         * If the filteredProducts list is empty at the end of the search, then no products were found.
         */
        if(filteredProducts.isEmpty())
        {
            System.out.println("No Match");
            DataCheck.popupWindow("Your search for: " + productName, "\n Has no Product Name Match", "No Name Match");
            return Inventory.getAllProducts();
        }

        /**
         * If the filteredParts list is not empty, then it is returned as a result to populate the parts table with
         */
        return filteredProducts;
    }

    /**
     * This method will take in a part and an index and update that part in the allParts list
     * @param index This is the Index of the part in the allParts list that the user is trying to modify
     * @param selectedPart This is the updated part information to insert into the allParts list
     */
    public static void updatePart(int index, Part selectedPart)
    {
        allParts.remove(index);
        allParts.add(index, selectedPart);
    }

    /**
     * This method will take in a Product and an index and update that product in the allProduct's list
     * @param index This is the index of the product in the allProducts list that the user is trying to modify
     * @param newProduct This is the updated product information to insert into the allProducts list
     */
    public static void updateProduct(int index, Product newProduct)
    {
        allProducts.remove(index);
        allProducts.add(index, newProduct);
    }

    /**
     * This method will delete a user-selected part from the allParts inventory
     * @param selectedPart This is the part the user wishes to remove from the allParts list
     * @return true if the part is deleted, false if it isn't
     */
    public static boolean deletePart(Part selectedPart)
    {
        /**
         * @param delete A boolean value to check whether or not the selected part was deleted
         */
        boolean delete = false;

        /**
         * @param message This variable will hold the name of the part the user wishes to delete
         *                It will use this name in the pop-up window for confirm
         */
        String message = selectedPart.getName();

            //This will provide a popup for the user to confirm their choice to delete a selected part
            if (DeleteConfirm.showBox("Delete", "delete ", message))
            {
                //If the user selects "yes" from the popup, then the delete occurs
                allParts.remove(selectedPart);
                //sets the boolean to true for debug purposes
                delete = true;
            }
            else
                //If the part is not chosen to be deleted, this sets the boolean to false
                delete = false;

        return delete;
    }

    /**
     * This method will delete a user-selected product from the allProducts inventory
     * @param selectedProduct This is the product the user wishes to remove from the allProducts list
     * @return true if the product is deleted, false if it isn't
     */
    public static boolean deleteProduct(Product selectedProduct) {

        /**
         * @param delete A boolean value to check whether or not the selected part was deleted
         */
        boolean delete = false;

        /**
         * @param message This variable will hold the name of the part the user wishes to delete.
         *                It will use this name in the pop-up window for confirm
         */
        String message = selectedProduct.getName();

        /**
         * This "if" statement will allow a product that has NO ASSOCIATED PARTS to be deleted (assuming the user
         * decides to click "yes" in the delete confirm popup).
         */
        if(selectedProduct.getAllAssociatedParts().isEmpty()){
            //This will provide a popup for the user to confirm their choice to delete a selected product
            if (DeleteConfirm.showBox("Delete", "delete ", message)) {

                //If the user selects "yes" from the popup, then the delete occurs
                allProducts.remove(selectedProduct);
                //sets the boolean to true for debug purposes
                delete = true;
            } else
                //If the part is not chosen to be deleted, this sets the boolean to false
                delete = false;
        }

        /**
         * This "else if" will be checking to make sure that there are not parts associated with a product.
         * If the product has any parts, then a popup will appear for the user informing them of this.
         * The method will then end and return false to the calling method.
         * FIXME: When I first submitted the project, I thought the syllabus must have had a typo and that you
         *  need to run a check to make sure you can't delete a PART if it is associated with a PRODUCT, not
         *  the other way around. I have added the required code to make sure the program
         *  meets the requirements to the T.
         */
        else if (selectedProduct.getAllAssociatedParts() != null)
        {
            DataCheck.popupWindow( message, " has parts associated with it." , "Delete Error");
            return false;
        }
        else
        {
            //This will provide a popup for the user to confirm their choice to delete a selected product
            if (DeleteConfirm.showBox("Delete", "delete ", message)) {

                //If the user selects "yes" from the popup, then the delete occurs
                allProducts.remove(selectedProduct);
                //sets the boolean to true for debug purposes
                delete = true;
            } else
                //If the part is not chosen to be deleted, this sets the boolean to false
                delete = false;
        }
        return delete;
    }

    /**
     * @return returns all parts in inventory
     */
    public static ObservableList<Part> getAllParts() {return allParts;}

    /**
     * @return returns all products in inventory
     */
    public static ObservableList<Product> getAllProducts() {return allProducts;}

}