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
 * This class controls the modifyProductForm.fxml file.
 * FIXME: In Future Projects, I would like to see the Products associated parts updated in real time with any information that the user modified into the parts.
 *
 *
 * FIXME: There is code below from the original submission I created to ensure that no products were created without associated parts.
 *   As this is not a part of the requirements, and in the requirements it is specifically mentioned that a product
 *   can exist without parts, I have commented it out. However, this code might be useful for subsequent iterations
 *   of this program and so I have left it below.
 */
public class modifyProductFormController implements Initializable
{

    /**
     * @param stage Stage is a generic variable used later to switch scenes
     */
    Stage stage;

    /**
     * @param assocParts This is an ObservableList to hold the parts to associate with the product until the save button
     */
    ObservableList<Part> assocParts = FXCollections.observableArrayList();

    @FXML
    private TextField modProdIdText;

    @FXML
    private TextField modProdNameText;

    @FXML
    private TextField modProdStockText;

    @FXML
    private TextField modProdCostText;

    @FXML
    private TextField modProdMaxText;

    @FXML
    private TextField modProdMinText;

    @FXML
    private TableView<Part> modProdAllPartsTable;

    @FXML
    private TableColumn<Part, Integer> id;

    @FXML
    private TableColumn<Part, String> name;

    @FXML
    private TableColumn<Part, Integer> stock;

    @FXML
    private TableColumn<Part, Double> price;

    @FXML
    private TextField modProdSearchText;

    @FXML
    private TableView<Part> modProdProdPartsTable;

    @FXML
    private TableColumn<Part, Integer> pId;

    @FXML
    private TableColumn<Part, String> pName;

    @FXML
    private TableColumn<Part, Integer> pStock;

    @FXML
    private TableColumn<Part, Double> pPrice;


    /**
     * This method will activate when the user types something into the Part Search text Field and hits return
     * @param event Hitting Enter will activate this event
     */
    @FXML
    void onActionSearchPartTable(ActionEvent event)
    {

        /**
         * @param searchParameter This will hold the data the user typed into the search field
         */
        String searchParameter = modProdSearchText.getText();

        /**
         * This will make sure the search field wasn't left empty. If it was, it will display a popup to the user.
         * The search field will then be reset to the allParts list from Inventory.
         */
        if (searchParameter.isEmpty())
        {
            System.out.println("No name or ID was entered");
            modProdAllPartsTable.setItems(Inventory.getAllParts());
            DataCheck.popupWindow("You didn't enter any Search Data.", "\nTable data will be reset.", "Please enter a PartID or Name");
        }
        else
        {
            /**
             * @param firstLetter This will hold the first letter in the user's search
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
            //This will see if the search input is an id (int) or a Name (String)
            else
            {
                if (DataCheck.intOrString(searchParameter))
                {
                    /**
                     * This will search the allParts list and compare to the search value by part Id.
                     * It will then select the matching part in the parts table.
                     * Then the search text field will be cleared.
                     * @param searchId This will hold the int value of the user search if it is an integer.
                     */
                    int searchId = Integer.parseInt(searchParameter);
                    modProdAllPartsTable.getSelectionModel().select(Inventory.lookupPart(searchId));
                    modProdSearchText.clear();
                }
                else
                {
                    /**
                     * If the search data isn't an integer, then it is assumed to be a Name search by String value.
                     * This will send the data to the Inventory to search the allParts by name and return any matches.
                     * The table will only contain matching parts if any are found.
                     * It will then clear the text search field.
                     */
                    modProdAllPartsTable.setItems(Inventory.lookupPart(searchParameter));
                    modProdSearchText.clear();
                }
            }
        }
    }

    /**
     * When the user selects a part from the top table and clicks the add button the part will be added to the bottom table.
     * @param event when user clicks add button, this code will execute
     */
    @FXML
    void onActionAddPartToProd(ActionEvent event)
    {
        /**
         * @param selectedPart This variable will hold the part the user wishes to add to the product's associatedPart list
         * @param assocParts This variable will hold the associated items.
         */
        Part selectedPart = modProdAllPartsTable.getSelectionModel().getSelectedItem();
        assocParts = modProdProdPartsTable.getItems();

        boolean repeat = false;

        //This will make sure a part is selected
        if(selectedPart == null)
        {
            //this will send information to display on a popup window to the user
            DataCheck.popupWindow("You have either not selected a part", "\nor the part was not found", "Part not found");
            return;
        }
        //This will search through the assocParts list to see if the partId is already there.
        else
        {
            int id = selectedPart.getId();
            for (int i = 0; i < assocParts.size(); i++)
            {
                if (assocParts.get(i).getId() == id)
                {
                    repeat = true;
                    DataCheck.popupWindow("Your selected part", " already exists.", "Duplicate");
                }
            }

            if (!repeat)
            {
                assocParts.add(selectedPart);
            }

        }
        //The associated parts table will be populated and refreshed to reflect any changes.
        modProdProdPartsTable.setItems(assocParts);
        modProdProdPartsTable.refresh();
    }

    /**
     * This will remove a part the user selects from the bottom table of associated parts.
     * @param event when the user clicks on Remove Associated Part, this code executes.
     */
    @FXML
    void onActionRemovePartFromProd(ActionEvent event)
    {
        /**
         * @param selectedPart This variable will hold the part the user wishes to remove from the product's associatedPart list
         * @param assocParts This variable will hold the associated items.
         */
        Part selectedPart = modProdProdPartsTable.getSelectionModel().getSelectedItem();
        assocParts = modProdProdPartsTable.getItems();

        //This will check to see if there is a part selected. If not, the user will be informed of this and the code will end.
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
        //Once the part is removed, the table is refreshed
        modProdProdPartsTable.refresh();
    }

    /**
     * This will save any changes the user made to the product and return the user to the main view.
     * @param event When the user clicks Save, this chunk of code will execute.
     * @throws IOException Handles Scene Loading Exceptions
     */
    @FXML
    void onActionSaveChanges(ActionEvent event) throws IOException
    {
        /**
         * @param costMin This variable will be used to make sure the cost of the product is larger than the SUM of the cost of the parts.
         */
        double costMin = 0;

        //This will get the data from the product information fields. Data will be in string form.
        String prodId = modProdIdText.getText();
        String name = modProdNameText.getText();
        String partInv = modProdStockText.getText();
        String partCost = modProdCostText.getText();
        String partMax = modProdMaxText.getText();
        String partMin = modProdMinText.getText();

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

        int id = Integer.parseInt(prodId);
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

                            Product newProduct = new Product(id, name, price, stock, min, max);
                             /**
                              * This was the code that held me at bay for a while. I couldn't figure out how to call the non-static addAssociatedParts
                              * from the Product class before creating an instance of the product. This was the solution.
                              * I realized that the table was holding data and I could use that table to set the data AFTER
                              * a product object was instantiated.
                              */
                             for (Part part : modProdProdPartsTable.getItems())
                                 newProduct.addAssociatedPart(part);

                             Inventory.updateProduct(index, newProduct);

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
        /**
         *
         * FIXME: This code was in the original submission to ensure that no products were created without associated parts.
         *   As this is not a part of the requirements, and in the requirements it is specifically mentioned that a product
         *   can exist without parts, I have commented it out. However, this code might be useful for subsequent iterations
         *   of this program and so I have left it here.
         *
         * if(assocParts.size() > 0)
         * {
         * ....
         * }
         * else
         * {
         *    System.out.println("No associated Parts were selected");
         *    DataCheck.popupWindow("No parts were selected", "\nProducts must have at least 1 part", "NO SELECTION");
         *    return;
         * }
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
        modProdAllPartsTable.setItems(Inventory.getAllParts());

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * @param index This is used as a failsafe in case no index is found.
     * @param modPartInfo This is used as a failsafe in case no part info is found.
     */
    int index = -1;
    Product modPartInfo = null;

    /**
     * This method executes first to set the data in the fields from the product that is passed into it.
     * @param modPart Variable modpart is updated during the initialization with the product that is being passed into this scene
     * @param sPIndex This is the location of the product in the allProducts list so that it can be updated.
     */
    public void modSelect(Product modPart, int sPIndex)
    {
        modPartInfo = modPart;
        index = sPIndex;

        //For debugging purposes
        System.out.println(modPartInfo.getName() + " is at index location: " + index);

        modProdIdText.setText(Integer.toString(modPartInfo.getId()));
        modProdNameText.setText(modPartInfo.getName());
        modProdStockText.setText(Integer.toString(modPartInfo.getStock()));
        modProdCostText.setText(Double.toString(modPartInfo.getPrice()));
        modProdMaxText.setText(Integer.toString(modPartInfo.getMax()));
        modProdMinText.setText(Integer.toString(modPartInfo.getMin()));

        modProdProdPartsTable.setItems(modPartInfo.getAllAssociatedParts());
        pId.setCellValueFactory(new PropertyValueFactory<>("id"));
        pName.setCellValueFactory(new PropertyValueFactory<>("name"));
        pStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        pPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
