package Controller;

/**
 * @author Brian Nason, Student Number xxxxxxxxx
 */

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class controls the addProductForm.fxml file
 *
 * FIXME: The code in the save button action held me at bay for a while. I couldn't figure out how to call the non-static addAssociatedParts
 *  from the Product class before creating an instance of the product. This was the solution:
 *  I realized that the table was holding data and I could use that table to set the data AFTER
 *  a product object was instantiated.

 */
public class addProductFormController implements Initializable
{
    /**
     * @param stage Stage is a generic variable used later to switch scenes
     * @param assocParts Sets up an ObservableList to add associated parts to the product being created
     */
    Stage stage;
    ObservableList<Part> assocParts = FXCollections.observableArrayList();

    @FXML
    private TextField addProdNameText;

    @FXML
    private TextField addProdStockText;

    @FXML
    private TextField addProdCostText;

    @FXML
    private TextField addProdMaxText;

    @FXML
    private TextField addProdMinText;

    @FXML
    private TableView<Part> addProdAllPartsTable;

    @FXML
    private TableColumn<Part, Integer> id;

    @FXML
    private TableColumn<Part, String> name;

    @FXML
    private TableColumn<Part, Integer> stock;

    @FXML
    private TableColumn<Part, Double> price;

    @FXML
    private TableView<Part> addProdProdPartsTable;

    @FXML
    private TableColumn<Part, Integer> aId;

    @FXML
    private TableColumn<Part, String> aName;

    @FXML
    private TableColumn<Part, Integer> aStock;

    @FXML
    private TableColumn<Part, Double> aPrice;

    @FXML
    private TextField addProductSearchText;

    /**
     * When the user types in something in the parts search field and hits enter, this code is executed
     * @param event user hitting enter
     */
    @FXML
    void onActionSearchPartTable(ActionEvent event)
    {

        /**
         * @param searchParameter This holds the data the user entered in the text search field.
         */
        String searchParameter = addProductSearchText.getText();

        /**
         * If the searchParameter is empty, then the field was empty and this code executes to let the user know of this error.
         * It then resets the parts table to its default showing all parts.
         */
        if (searchParameter.isEmpty())
        {
            System.out.println("No name or ID was entered");
            addProdAllPartsTable.setItems(Inventory.getAllParts());
            DataCheck.popupWindow("You didn't enter any Search Data.", "\nTable data will be reset.", "Please enter a PartID or Name");
        }
        else
        {
            /**
             * @param firstLetter This holds the first character from the search field.
             */
            char firstLetter = searchParameter.charAt(0);

            //This will check to make sure that there isn't all spaces in the search field. If so, then a pop-up window error message will be displayed
            if (searchParameter.isBlank())
            {
                System.out.println("No name or ID was entered");
                DataCheck.popupWindow("You didn't enter any Search Data", "\n Please enter an ID or Name", "Only Spaces");
            }
            //This will make sure the first character the user searches isn't a space. If so, then a pop-up window error message will be displayed
            else if (firstLetter == ' ')
            {
                System.out.println("The first character can't be a space");
                DataCheck.popupWindow(" ", "The first character can't be a space", "First character is space");
            }
            else
            {
                //This will see if the search input is an id (int) or a Name (String)
                if (DataCheck.intOrString(searchParameter))
                {
                    /**
                     * If the data is of integer type, then this will search for matching IDs.
                     * If it finds a match, then it highlights/selects the part in the table
                     * It will then clear the search field.
                     * @param searchID This will hold the ID value of the part to be searched if it is of int type
                     *
                     */
                    int searchId = Integer.parseInt(searchParameter);
                    addProdAllPartsTable.getSelectionModel().select(Inventory.lookupPart(searchId));
                    addProductSearchText.clear();
                }
                else
                {
                    /**
                     * If the data isn't of int type, then it is assumed to be a name (string type).
                     * this will then search all the names of all the products for any partial or full matches.
                     * it will add the matches to the ObservableList and then set that list as the table's value.
                     * It will then clear the search field
                     */
                    addProdAllPartsTable.setItems(Inventory.lookupPart(searchParameter));
                    addProductSearchText.clear();
                }
            }
        }
    }

    /**
     * This will take a part from the upper table and pass it into the associated parts table (the lower table)
     * @param event When user clicks add
     */
    @FXML
    void onActionAddPartToProduct(ActionEvent event)
    {
        /**
         * @param selectedPart Holds the value of the selected part the user wishes to move to the associated parts table.
         * @param repeat Holds a boolean value to make sure the part hasn't already been added to the associated parts.
         *               By default, it is false.
         */
        Part selectedPart = addProdAllPartsTable.getSelectionModel().getSelectedItem();
        boolean repeat = false;

        /**
         * This will make sure a part is selected.
         * if no part is selected, the user will be presented with a popup window explaining it.
         */
        if(selectedPart == null)
        {
            DataCheck.popupWindow("You have either not selected a part", "\nor the part was not found", "Part not found");
            return;
        }
        else
        {
            /**
             * @param id This holds the ID of the selected part to run it through the associatedParts list to find if there is a match.
             */
            int id = selectedPart.getId();

            /**
             * This will scan through the associatedParts of the selected product to find a matching part ID.
             * If it finds one, then the selected part is a duplicate and doesn't need to be added to the associated parts.
             * a popup window is then created informing the user of this.
             */
            for (int i = 0; i < assocParts.size(); i++)
            {
                if (assocParts.get(i).getId() == id)
                {
                    repeat = true;
                    DataCheck.popupWindow("Your selected part", " already exists.", "Duplicate");
                }
            }

            /**
             * If there are no repeats, then it will add the part to the associatedParts list
             */
            if (!repeat)
            {
                assocParts.add(selectedPart);
            }
        }

        /**
         * The associatedParts table is then re-set with any changes.
         */
        addProdProdPartsTable.setItems(assocParts);
        aId.setCellValueFactory(new PropertyValueFactory<>("id"));
        aName.setCellValueFactory(new PropertyValueFactory<>("name"));
        aStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        aPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * When the user wants to remove an associated part, this code executes.
     * @param event User clicking the Remove Associated Part button
     */
    @FXML
    void onActionRemovePart(ActionEvent event)
    {
        Part selectedPart = addProdProdPartsTable.getSelectionModel().getSelectedItem();

        if(selectedPart == null)
        {
            //this will send information to display on a popup window to the user
            DataCheck.popupWindow("You have either not selected a part", "\nor the part was not found", "Part not found");
            return;
        }
        else
        {
            /**
             * This presents the user with the chance to confirm or deny the removal of the associated part
             */
            if(DeleteConfirm.showBox("Remove", " remove ", selectedPart.getName()))
            {
                assocParts.remove(selectedPart);
            }
        }

        /**
         * The associated parts table is refreshed at the end to reflect any changes.
         */
        addProdProdPartsTable.refresh();
    }

    /**
     * This will save the new product and return the user to the main view.
     * @param event When the user clicks Save, this chunk of code will execute.
     * @throws IOException Handles Scene Loading Exceptions
     */
    @FXML
    void onActionSaveProduct(ActionEvent event) throws IOException
    {
        /**
         * @param costMin This is a variable to ensure the Product cost is not any less than the SUM of the Parts costs
         */
        double costMin = 0;

        //This will get the data from the product information fields. Data will be in string form.
        String name = addProdNameText.getText();
        String partInv = addProdStockText.getText();
        String partCost = addProdCostText.getText();
        String partMax = addProdMaxText.getText();
        String partMin = addProdMinText.getText();

        /**
         This will run the data through a series of checks to make sure it is the right type of data.
         Each check produces its own popup window if it fails.
         */
        DataCheck.isInteger(partInv, "Inventory");
        DataCheck.isDouble(partCost);
        DataCheck.isInteger(partMax, "Max");
        DataCheck.isInteger(partMin, "Min");
        DataCheck.hasData(name, "Name");

        /**
         If the data is confirmed to be of the right type, it is then run through this try/catch to convert it to that type.
         If it fails any one, the Check will display a popup window describing the fail
         */
        try
        {
            Integer.parseInt(partInv);
            Double.parseDouble(partCost);
            Integer.parseInt(partMax);
            Integer.parseInt(partMin);
        }
        catch (Exception e)
        {
            System.out.println(e + " is not a number. Please try again");
            return;
        }

        int stock = Integer.parseInt(partInv);
        Double price = Double.parseDouble(partCost);
        int max = Integer.parseInt(partMax);
        int min = Integer.parseInt(partMin);

        //A final check to make sure there is nothing negative, no zero price, and that the data is in the correct range
        DataCheck.isNotNegative(stock, "Inventory");
        DataCheck.isNotNegative(min, "Minimum");
        DataCheck.isNotNegative(max, "Maximum");
        DataCheck.isZero(price);
        DataCheck.minMax(min, max);
        DataCheck.isBetween(stock, min, max);

        /**
         * This is what checks to make sure the cost of the Product is no less than the SUM of the cost of the parts.
         */
        for (int i = 0; i < assocParts.size(); i++)
        {
            costMin += assocParts.get(i).getPrice();
        }

        if (price < costMin)
        {
            System.out.println("Cost of product is not greater than total SUM of parts");
            DataCheck.popupWindow("Cost of product ", "must be less than SUM of parts", "Pricing Error");
            return;
        }

        /**
         * This will run the data through a series of checks to make sure it is valid before saving it
         */
        if (min > 0 && max > 0 && stock > 0)
        {
            if (!(name.isBlank() || name.isEmpty()))
            {
                if (max > min)
                {
                    if (stock >= min && stock <= max)
                    {
                        if (price != null && price != 0.00)
                        {
                            System.out.println(name + "," + stock + "," + price + "," + max + "," + min);
                            int id = DataCheck.generateID();
                            System.out.println("The part's id is: " + id);

                            Product newProduct = new Product(id, name, price, stock, min, max);

                            for(Part part : addProdProdPartsTable.getItems())
                                newProduct.addAssociatedPart(part);

                            Inventory.addProduct(newProduct);

                            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                            Parent sceneParent = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                            stage.setScene(new Scene(sceneParent));
                            stage.show();

                        }
                        else
                        {
                            System.out.println("Price must be greater than zero.");
                        }
                    }
                    else
                    {
                        System.out.println(stock + " is not between " + min + " and " + max);
                    }
                }
                else
                {
                    System.out.println(max + " is not larger than " + min);
                }
            }
            else
            {
                System.out.println("Name field was left blank");
            }
        }
        else
        {
            System.out.println("Negative number alert");
        }
    }
    /** FIXME: This code was in the original submission to ensure that no products were created without associated parts.
     *   As this is not a part of the requirements, and in the requirements it is specifically mentioned that a product
     *   can exist without parts, I have commented it out. However, this code might be useful for subsequent iterations
     *   of this program and so I have left it here.
     *
     *   if(assocParts.size() > 0)
     *   {
     *   ...
     *   }
     *   else
     *   {
     *   System.out.println("No associated Parts were selected");
     *   DataCheck.popupWindow("No parts were selected", "\nProducts must have at least 1 part", "NO SELECTION");
     *   return;
     *   }
     */


    /**
     * This will return the user to the main form.
     * @param event When the user clicks on the cancel button, it takes them back to the main form.
     * @throws IOException Handles Scene Loading Exceptions
     */
    @FXML
    void onActionGoToMainForm(ActionEvent event) throws IOException
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent sceneParent = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(sceneParent));
        stage.show();
    }

    /**
     * This is the initialization method for this screen that will take the data from the product that is passed into this screen.
     * It will put the data in its appropriate fields.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        addProdAllPartsTable.setItems(Inventory.getAllParts());

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        assocParts.clear();
    }
}
