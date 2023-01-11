package Controller;

/**
 * @author Brian Nason, Student Number xxxxxxxxx
 */

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class controls the modifyPartFormOutsourced.fxml file
 * FIXME: In future projects, i would like to control both InHouse and Outsourced through the same window
 */
public class modifyPartFormOutsourcedController implements Initializable
{

    /**
     * @param stage Stage is a generic variable used later to switch scenes
     */
    Stage stage;

    @FXML
    private TextField outsourcedModIdText;

    @FXML
    private TextField outsourcedModNameText;

    @FXML
    private TextField outsourcedModStockText;

    @FXML
    private TextField outsourcedModCostText;

    @FXML
    private TextField outsourcedModMaxText;

    @FXML
    private TextField outsourcedModCompanyText;

    @FXML
    private TextField outsourcedModMinText;

    @FXML
    private ToggleGroup modifyPartRadioOUT;

    @FXML
    private RadioButton outsourcedModPartOutsourcedRadio;

    /**
     * This will direct the user to the In House modify part form page if the InHouse radio is selected
     * @param event When the user selects the InHouse radio button, it will take all the data from the form so far.
     *              It will then transfer it to the modifyPartForm.fxml.
     * @throws IOException Handles Scene Loading Exceptions
     */
    @FXML
    void onActionGoToInHouse(ActionEvent event) throws IOException
    {
        /**
         * @param partId This will hold the value from the ID field
         * @param partName This will hold the value from the Name field
         * @param partInv This will hold the value from the Inv field
         * @param partCost This will hold the value from the Price/Cost field
         * @param partMax This will hold the value from the Max field
         * @param partMin This will hold the value from the Min field
         */
        int partId = Integer.parseInt(outsourcedModIdText.getText());
        String partName = outsourcedModNameText.getText();
        int partInv = Integer.parseInt(outsourcedModStockText.getText());
        double partCost = Double.parseDouble(outsourcedModCostText.getText());
        int partMax = Integer.parseInt(outsourcedModMaxText.getText());
        int partMin = Integer.parseInt(outsourcedModMinText.getText());

        /**
         * @param newPart This will hold the information for the new part that will be transferred to the modifyPartForm.fxml scene
         */
        InHouse newPart = new InHouse(partId, partName, partCost, partInv, partMin, partMax, 0);

        //This sets up the new scene to activate it so we can pass the information into it before it is loaded
        stage = (Stage)((RadioButton)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/modifyPartForm.fxml"));
        Parent sceneParent = loader.load();

        //This will pass the part information to the new scene
        modifyPartFormController mpInHouse = loader.getController();
        mpInHouse.modSelect((newPart), index);

        //This will show the new scene - the modifyPartForm.fxml
        stage.setScene(new Scene(sceneParent));
        stage.show();
    }

    /**
     * This will change the scene to the main form
     * @param event When the user clicks the Cancel button, they will be directed to the main form.
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
     * This method will save the changes to the part and overwrite the part in the inventory with the new part.
     * @param event When the user clicks on the save button, the part will be saved and the scene will change to the main form.
     * @throws IOException Handles Scene Loading Exceptions
     */
    @FXML
    void onActionSaveMod(ActionEvent event) throws IOException
    {
        /**
         * This will get the data from the fields to store as the new/updated part info
         * @param partId This will hold the value from the ID field
         * @param name This will hold the value from the Name field
         * @param partInv This will hold the value from the Inv field
         * @param partCost This will hold the value from the Price/Cost field
         * @param partMax This will hold the value from the Max field
         * @param partMin This will hold the value from the Min field
         * @param companyName This will hold the value from the Company Name field
         */
        String partId = outsourcedModIdText.getText();
        String name = outsourcedModNameText.getText();
        String partInv = outsourcedModStockText.getText();
        String partCost = outsourcedModCostText.getText();
        String partMax = outsourcedModMaxText.getText();
        String partMin = outsourcedModMinText.getText();
        String companyName = outsourcedModCompanyText.getText();

        /**
         This will run the data through a series of checks to make sure it is the right type of data.
         Each check produces its own popup window if it fails.
        */
        DataCheck.isInteger(partInv, "Inventory");
        DataCheck.isDouble(partCost);
        DataCheck.isInteger(partMax, "Max");
        DataCheck.isInteger(partMin, "Min");

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
        catch(Exception e)
        {
            System.out.println(e + " is not a number. Please try again");
            return;
        }

        int id = Integer.parseInt(partId);
        int stock = Integer.parseInt(partInv);
        Double price = Double.parseDouble(partCost);
        int max = Integer.parseInt(partMax);
        int min = Integer.parseInt(partMin);

        //A final check to make sure there is nothing negative, no zero price, all the data's filled out, and that the data is in the correct range
        DataCheck.hasData(name, "Name");
        DataCheck.isNotNegative(stock, "Inventory");
        DataCheck.isNotNegative(min, "Minimum");
        DataCheck.isNotNegative(max, "Maximum");
        DataCheck.hasData(companyName, "Company Name");
        DataCheck.isZero(price);
        DataCheck.minMax(min, max);
        DataCheck.isBetween(stock, min, max);

        /**
         * This will run the data through a series of checks to make sure it is valid before saving it
         */
        if(min > 0 && max > 0 && stock > 0)
        {
            if (!(name.isEmpty() || name.isBlank()))
            {
                if (!(companyName.isEmpty() || companyName.isBlank()))
                {
                    if (max > min)
                    {
                        if (stock >= min && stock <= max)
                        {
                            if (price != null && price != 0.00)
                            {
                                System.out.println(name + "," + stock + "," + price + "," + max + "," + min + "," + companyName);
                                Outsourced newPart = new Outsourced(id, name, price, stock, min, max, companyName);

                                Inventory.updatePart(index, newPart);

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
                    System.out.println("Company Name field was left blank");
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }


    /**
     * @param index This variable is used as a failsafe for the data
     */
    int index = -1;

    /**
     * This will set up the data into its fields before the scene is fully loaded for the user to see.
     * @param modPart This is the part that the user wishes to modify
     * @param sPIndex This is the index of the part the user wishes to modify so that the part can be updated once modification is complete.
     */
    public void modSelect(Part modPart, int sPIndex)
    {
        Outsourced modPartInfo = (Outsourced) modPart;
        index = sPIndex;

        System.out.println(modPart.getName() + " is at index location: " + index);

        outsourcedModIdText.setText(Integer.toString(modPartInfo.getId()));
        outsourcedModNameText.setText(modPartInfo.getName());
        outsourcedModStockText.setText(Integer.toString(modPartInfo.getStock()));
        outsourcedModCostText.setText(Double.toString(modPartInfo.getPrice()));
        outsourcedModMaxText.setText(Integer.toString(modPartInfo.getMax()));
        outsourcedModMinText.setText(Integer.toString(modPartInfo.getMin()));
        outsourcedModCompanyText.setText(modPartInfo.getCompanyName());
    }
}