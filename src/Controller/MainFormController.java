package Controller;

/**
 * @author Brian Nason, Student Number xxxxxxxxx
 */

import Model.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class controls the MainForm.fxml file
 *
 * FIXME: This "else" block of code below was originally written because I thought the Syllabus wanted us to make sure
 *  you couldn't delete a PART if it has a PRODUCT association. I have left it in because I think it is a worthwhile
 *  check to have, though I am not quite sure if it is a requirement. If it isn't, then this is a functionality addition
 *  that I would want to see in later versions.
 *
 * FIXME: By adding a window controller class in the future versions I could clean up the code and make it more acceptable to expansion.
 *
 */
public class MainFormController implements Initializable
{
    /**
     * @param stage This will be used to set a new stage for the window to output depending on the selections of the user
     */
    Stage stage;

    @FXML
    private TableView<Part> partsTable;

    @FXML
    private TableColumn<Part, Integer> id;

    @FXML
    private TableColumn<Part, String> name;

    @FXML
    private TableColumn<Part, Integer> stock;

    @FXML
    private TableColumn<Part, Double> price;

    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableColumn<Product, Integer> prodId;

    @FXML
    private TableColumn<Product, String> prodName;

    @FXML
    private TableColumn<Product, Integer> prodStock;

    @FXML
    private TableColumn<Product, Double> prodPrice;

    /**
     * This method uses the action of the user-selecting the Delete button.
     * It reads the selection of the user from the parts table and then removes the part if certain conditions are met.
     */
    @FXML
    void onActionDeletePart(ActionEvent event) {

        /**
         * @param deletePart This will hold the value of the selected part through the method
         */
        Part deletePart = partsTable.getSelectionModel().getSelectedItem();

        /**
        * This will check to make sure there IS a selection before continuing.
        * If nothing is selected, then a popup will appear to the user to inform them of this
         */
        if(deletePart == null) {

            //this will send information to display on a popup window to the user
            DataCheck.popupWindow("You have either not selected a part", "\nor the part was not found", "Part not found");
            return;

        }
        else
        {
            /**
             * This will make sure the part to be deleted doesn't exist in any product.
             * It goes product-by-product and reads what parts are associated with that product.
             * If any of the product's associated parts is the user-selected part, then it will not delete the part,
             * and a pop-up window will explain this to the user.
             *
             *@param prodToCheck This will hold each of the called products as it sends them through the hasParts check
             */
            for (int i = 0; i < Inventory.getAllProducts().size(); i++)
            {

                Product prodToCheck = Inventory.getAllProducts().get(i);

                if (DataCheck.hasParts(prodToCheck, deletePart))
                {
                    System.out.println("Part exists in product and can't be deleted");
                    return;
                }
            }
        }

        /**
         * This will send the information to the Inventory.deletePart method to handle the deletion
         * the deletePart will return T or F to confirm whether or not the part was deleted
         */
        if (Inventory.deletePart(deletePart))

            //If the part is deleted, then this will appear for debug purposes
            System.out.println("Part is now deleted");

        else

            //If the part was not deleted, then this will appear for debug purposes
            System.out.println("Part WAS NOT deleted");
    }

    /**
     * This method uses the action of the user-selecting the Delete button.
     * It reads the selection of the user from the products table and then removes the product from inventory.
     */
    @FXML
    void onActionDeleteProd(ActionEvent event) {

        /**
         * This will check to make sure there IS a selection before continuing.
         * If nothing is selected, then a popup will appear to the user to inform them of this
         */
        if(productsTable.getSelectionModel().getSelectedItem() == null) {

            //this will send information to display on a popup window to the user
            DataCheck.popupWindow("You have either not selected a product", "\nor the product was not found", "Product not found");
            return;

        }

        //If there is a selection, then the delete process will continue
        else {

            /**
             * This will send the information to the Inventory.deletePart method to handle the deletion.
             * the deletePart will return T or F to confirm whether or not the part was deleted.
            */
            if (Inventory.deleteProduct(productsTable.getSelectionModel().getSelectedItem()))

                //If the part is deleted, then this will appear for debug purposes
                System.out.println("Product is now deleted");

            else

                //If the part was not deleted, then this will appear for debug purposes
                System.out.println("Product WAS NOT deleted");
        }
    }

    /**
     * This method uses the action of the user-selecting the Add button under the parts table.
     * It will open the Add Part screen in the window for the user to add a part to the part's inventory.
     * The in-house part screen is the default scene.
     * @return a new scene (Add Parts) will be displayed in the window.
     */
    @FXML
    void onActionGoToAddParts(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent sceneParent = FXMLLoader.load(getClass().getResource("/view/addPartForm.fxml"));
        stage.setScene(new Scene(sceneParent));
        stage.show();
    }

    /**
     * This method uses the action of the user-selecting the Add button under the products table.
     * It will open the Add Product screen in the window for the user to add a product and its associated parts to inventory.
     * @return a new scene (Add Products) will be displayed in the window.
     */
    @FXML
    void onActionGoToAddProducts(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent sceneParent = FXMLLoader.load(getClass().getResource("/view/addProductForm.fxml"));
        stage.setScene(new Scene(sceneParent));
        stage.show();
    }

    /**
     * This method uses the action of the user-selecting the exit button.
     * It will close out the program and its data will be deleted (will not persist in memory).
     */
    @FXML
    void onActionProgramClose(ActionEvent event) {
        System.exit(0);
    }

    /**
     * This method uses the action of the user-selecting the Modify button under the Parts table.
     * It will open the Modify Part scene - either In House or Outsourced depending on the selected part's model.
     * It will transfer the part's data to that new scene and place it in the appropriate boxes for editing.
     * @return A new scene (Modify Part) will be displayed in the window.
     */
    @FXML
    void onActionGoToModifyParts(ActionEvent event) throws IOException {

        /**
         * @param selectedPart will hold the data of the part that the user selected from the parts table.
         * @param sPIndex will hold the index of the part that the user selected.
         *                It will use this information to update the part once the user's update is complete.
         */
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        int sPIndex = partsTable.getSelectionModel().getSelectedIndex();

        //This will make sure a part is selected
        if(selectedPart == null) {

            //this will send information to display on a popup window to the user
            DataCheck.popupWindow("You have either not selected a part", "\nor the part was not found", "Part not found");
            return;

        }

        else {

            /**
             * If a part is of InHouse model type, then it will send the selected part's information to the modifyPartForm controller.
             * It will then load the modifyPartForm.fxml scene for the user.
             * The part's information will be populated into the form for the user to edit.
             */
            if(selectedPart instanceof InHouse) {

                /**
                 * This will set up the data in the scene BEFORE loading the scene to the user
                 * @param loader This will hold the needed .fxml file
                 * @param sceneParent This will store the newly-created loader variable as a Parent node
                 */
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/modifyPartForm.fxml"));
                Parent sceneParent = loader.load();

                /**
                 * The data will then be passed to the new scene's controller BEFORE the scene is loaded for the user
                 * @param mpInHouse This will hold the InHouse controller information so the info from selectedPart
                 *                  and its index can be passed to the new scene's method called modSelect
                 */
                modifyPartFormController mpInHouse = loader.getController();
                mpInHouse.modSelect(selectedPart, sPIndex);

                /**
                 * Now the compiler will load the new scene for the user to view
                 */
                stage.setScene(new Scene(sceneParent));
                stage.show();

            }

            /**
             * If a part is of Outsourced model type, then it will send the selected part's information to the modifyPartFormOutsourced controller.
             * It will then load the modifyPartFormOutsourced.fxml scene for the user.
             * The part's information will be populated into the form for the user to edit.
             * @param Outsourced This will identify the part if it is an instance of the Outsourced model.
             */
            if(selectedPart instanceof Outsourced) {

                /**
                 * This will set up the data in the scene BEFORE loading the scene to the user
                 * @param loader This will hold the needed .fxml file
                 * @param sceneParent This will store the newly-created loader variable as a Parent node
                 */
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/modifyPartFormOutsourced.fxml"));
                Parent sceneParent = loader.load();

                /**
                 * The data will then be passed to the new scene's controller BEFORE the scene is loaded for the user
                 * @param mpOutsourced This will hold the Outsourced controller information so the info from selectedPart
                 *                  and its index can be passed to the new scene's method called modSelect
                 */

                modifyPartFormOutsourcedController mpOutsourced = loader.getController();
                mpOutsourced.modSelect(selectedPart, sPIndex);

                /**
                 * Now the compiler will load the new scene for the user to view
                 */
                stage.setScene(new Scene(sceneParent));
                stage.show();

            }
        }
    }

    /**
     * This method uses the action of the user-selecting the Modify button under the products table.
     * It will open the Modify Product scene.
     * It will transfer the product's data to that new scene and place it in the appropriate boxes for editing.
     * @return A new scene (Modify Product) will be displayed in the window.
     */
    @FXML
    void onActionGoToModProducts(ActionEvent event) throws IOException {

        /**
         * @param selectedProduct will hold the data of the product that the user selected from the product table.
         * @param sPIndex will hold the index of the product that the user selected.
         *                It will use this information to update the product once the user's update is complete.
         */
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        int sPIndex = productsTable.getSelectionModel().getSelectedIndex();

        //This will make sure a part is selected
        if(selectedProduct == null) {

            //this will send information to display on a popup window to the user
            DataCheck.popupWindow("You have either not selected a product", "\nor the product was not found", "Product not found");
            return;

        }

        /**
         * If a part is selected, the else statement will send that part to the next scene for editing
         */
        else {

            /**
             * This will set up the data in the scene BEFORE loading the scene to the user
             * @param loader This will hold the needed .fxml file
             * @param sceneParent This will store the newly-created loader variable as a Parent node
             */
             stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/modifyProductForm.fxml"));
             Parent sceneParent = loader.load();

            /**
             * The data will then be passed to the new scene's controller BEFORE the scene is loaded for the user
             * @param modProd This will hold the modify product's controller information so the info from selected
             *                  product and its index can be passed to the new scene's method called modSelect
             */
             modifyProductFormController modProd = loader.getController();
             modProd.modSelect(selectedProduct, sPIndex);

            /**
             * Now the compiler will load the new scene for the user to view
             */
             stage.setScene(new Scene(sceneParent));
             stage.show();
        }
    }

    /**
     * This is the "Main Method of this controller"
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partsTable.setItems(Inventory.getAllParts());
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTable.setItems(Inventory.getAllProducts());
        prodId.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodName.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    @FXML
    private TextField partSearchTextField;

    /**
     * This method will use the action of the user typing in search data in the field above the parts table and then pressing enter.
     * Once the user does this, this method will search the existing parts ID's and Names to find a partial or full search match.
     * @return If part is found, then the matching part will be selected in the table, OR the matching parts will be displayed
     * in the table and all other parts will be removed from view.
     */
    @FXML
    void onActionSearchPartTable(ActionEvent event) {

        /**
         * @param searchParameter This variable will hold the information that the user types into the search field.
         */
        String searchParameter = partSearchTextField.getText();

        /**
         * Using the searchParameter, this if/else will decide if the search field is empty.
         * If it is empty, then the part's table will be repopulated with all the available parts.
         * @return A popup window will then be displayed to the user informing them that they didn't enter any data.
         */
        if (searchParameter.isEmpty()) {
            System.out.println("No name or ID was entered");
            partsTable.setItems(Inventory.getAllParts());
            DataCheck.popupWindow("You didn't enter any Search Data.", "\nTable data will be reset.", "Please enter a PartID or Name");
            partsTable.getSelectionModel().clearSelection();
        } else {

            /**
             * Even if data is typed into the field, the system needs to check to make sure it is Good data.
             * this means that the data can't be a space (or all spaces) and that the first character of the data can't be a space.
             * @param firstLetter This will hold the data of the FIRST ENTERED character in the search field.
             * @return A popup window if data is invalid (either blank or spaces)
             */
            char firstLetter = searchParameter.charAt(0);

            //This will check to make sure that there isn't all spaces in the search field. If so, then a pop-up window error message will be displayed
            if (searchParameter.isBlank()) {

                System.out.println("No name or ID was entered");
                DataCheck.popupWindow("You didn't enter any Search Data", "\n Please enter an ID or Name", "Only Spaces");

            }
            //This will make sure the first character the user searches isn't a space. If so, then a pop-up window error message will be displayed
            else if (firstLetter == ' ') {

                System.out.println("The first character can't be a space");
                DataCheck.popupWindow(" ", "The first character can't be a space", "First character is space");

            }

            /**
             * Once the search data has passed the above checks, then it will send the data through one more check.
             * this will see if the user data is referencing a part ID or a part Name.
             */
            else {

                /**
                 * If the entered data is decided to be an integer, then it will search for the part ID number.
                 * It sends the integer value of the user-entered data to the Inventory controller's lookupPart method.
                 * If a matching part ID is found, then it is returned.
                 * Once the method is completed, the search field is cleared.
                 * @param searchId This variable holds the integer value of the search data to pass it through to the
                 *                 Inventory controller.
                 * @return If the part is found by ID, it will be selected in the part table
                 */
                if (DataCheck.intOrString(searchParameter)) {

                    int searchId = Integer.parseInt(searchParameter);
                    partsTable.getSelectionModel().select(Inventory.lookupPart(searchId));
                    partSearchTextField.clear();

                }

                /**
                 * If the entered data ISN'T an integer, it is assumed to be a string and therefore assumed to be a name search.
                 * It then sends the user-entered data to the Inventory controller's lookupPart method.
                 * If a matching part name is found, then it is returned.
                 * Once the method is completed, the search field is cleared.
                 * @return the matching parts will be displayed in the table and all other parts will be removed from the table.
                 */
                else {

                    partsTable.setItems(Inventory.lookupPart(searchParameter));
                    partSearchTextField.clear();

                }
            }
        }
    }


    @FXML
    private TextField productSearchTextField;

    /**
     * This method will use the action of the user typing in search data in the field above the products table and then pressing enter.
     * Once the user does this, this method will search the existing products ID's and Names to find a partial or full search match.
     * @return If product is found, then the matching product will be selected in the table, OR the matching products will be displayed
     * in the table and all other products will be removed from view.
     */
    @FXML
    void onActionSearchProductTable(ActionEvent event) {

        /**
         * @param searchParameter This variable will hold the information that the user types into the search field.
         */
        String searchParameter = productSearchTextField.getText();

        /**
         * Using the searchParameter, this if/else will decide if the search field is empty.
         * If it is empty, then the products table will be repopulated with all the available products.
         * @return A popup window will then be displayed to the user informing them that they didn't enter any data.
         */
        if (searchParameter.isEmpty()) {
            System.out.println("No name or ID was entered");
            productsTable.setItems(Inventory.getAllProducts());
            DataCheck.popupWindow("You didn't enter any Search Data.", "\nTable data will be reset.", "Please enter a PartID or Name");
            productsTable.getSelectionModel().clearSelection();
        }

        /**
         * Even if data is typed into the field, the system needs to check to make sure it is Good data.
         * this means that the data can't be a space (or all spaces) and that the first character of the data can't be a space.
         * @param firstLetter This will hold the data of the FIRST ENTERED character in the search field
         * @return A popup window if data is invalid (either blank or spaces)
         */
        else {

            char firstLetter = searchParameter.charAt(0);

            //This will check to make sure that there isn't all spaces in the search field. If so, then a pop-up window error message will be displayed
            if (searchParameter.isBlank()) {
                System.out.println("No name or ID was entered");
                DataCheck.popupWindow("You didn't enter any Search Data", "\n Please enter an ID or Name", "Only Spaces");

            //This will make sure the first character the user searches isn't a space. If so, then a pop-up window error message will be displayed
            }
            else if (firstLetter == ' ') {
                System.out.println("The first character can't be a space");
                DataCheck.popupWindow(" ", "The first character can't be a space", "First character is space");
            }

            /**
             * Once the search data has passed the above checks, then it will send the data through one more check.
             * this will see if the user data is referencing a product ID or a product Name.
             */
            else {

                /**
                 * If the entered data is decided to be an integer, then it will search for the product's ID number.
                 * It sends the integer value of the user-entered data to the Inventory controller's lookupProduct method.
                 * If a matching product ID is found, then it is returned.
                 * Once the method is completed, the search field is cleared.
                 * @param searchId This variable holds the integer value of the search data to pass it through to the
                 *                 Inventory controller.
                 * @return If the product is found by ID, it will be selected in the product table
                 */
                if (DataCheck.intOrString(searchParameter)) {

                    int searchId = Integer.parseInt(searchParameter);
                    productsTable.getSelectionModel().select(Inventory.lookupProduct(searchId));
                    productSearchTextField.clear();

                }

                /**
                 * If the entered data ISN'T an integer, it is assumed to be a string and therefore assumed to be a name search.
                 * It then sends the user-entered data to the Inventory controller's lookupProduct method.
                 * If a matching product name is found, then it is returned.
                 * Once the method is completed, the search field is cleared.
                 * @return the matching products will be displayed in the table and all other products will be removed from the table.
                 */
                else {

                    productsTable.setItems(Inventory.lookupProduct(searchParameter));
                    productSearchTextField.clear();

                }
            }
        }
    }
}
