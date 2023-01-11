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
 * This class controls the modifyPartForm.fxml file
 * FIXME: In future projects, I would like to control both InHouse and Outsourced through the same window
 */
public class modifyPartFormController implements Initializable
{
    /**
     * @param stage Stage is a generic variable used later to switch scenes
     */
    Stage stage;

    @FXML
    private TextField inHouseModIdText;

    @FXML
    private TextField inHouseModNameText;

    @FXML
    private TextField inHouseModStockText;

    @FXML
    private TextField inHouseModCostText;

    @FXML
    private TextField inHouseModMaxText;

    @FXML
    private TextField inHouseModAddMachIDText;

    @FXML
    private TextField inHouseModMinText;

    @FXML
    private RadioButton inHouseModPartInHouseRadio;

    @FXML
    private ToggleGroup modifyPartRadioIN;

    /**
     * When the user selects the cancel button, they will be directed to the main form.
     * @param event User clicking cancel button
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
     * This will direct the user to the Outsourced modify part form page if the Outsourced radio is selected
     * @param event When the user selects the Outsourced radio button, it will take all the data from the form so far.
     *              It will then transfer it to the modifyPartFormOutsourced.fxml.
     * @throws IOException Handles Scene Loading Exceptions
     */
    @FXML
    void onActionGoToOutsourced(ActionEvent event) throws IOException
    {
        /**
         * @param partId This will hold the value from the ID field
         * @param partName This will hold the value from the Name field
         * @param partInv This will hold the value from the Inv field
         * @param partCost This will hold the value from the Price/Cost field
         * @param partMax This will hold the value from the Max field
         * @param partMin This will hold the value from the Min field
         */
        int partId = Integer.parseInt(inHouseModIdText.getText());
        String partName = inHouseModNameText.getText();
        int partInv = Integer.parseInt(inHouseModStockText.getText());
        double partCost = Double.parseDouble(inHouseModCostText.getText());
        int partMax = Integer.parseInt(inHouseModMaxText.getText());
        int partMin = Integer.parseInt(inHouseModMinText.getText());

        /**
         * @param newPart This will hold the information for the new part that will be transferred to the modifyPartFormOutsourced.fxml scene
         */
        Outsourced newPart = new Outsourced(partId, partName, partCost, partInv, partMin, partMax, "");

        //This sets up the new scene to activate it so we can pass the information into it before it is loaded
        stage = (Stage)((RadioButton)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/modifyPartFormOutsourced.fxml"));
        Parent sceneParent = loader.load();

        //This will pass the part information to the new scene
        modifyPartFormOutsourcedController mpOutsourced = loader.getController();
        mpOutsourced.modSelect((newPart), index);

        //This will show the new scene - the modifyPartFormOutsourced.fxml
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
         * @param partName This will hold the value from the Name field
         * @param partInv This will hold the value from the Inv field
         * @param partCost This will hold the value from the Price/Cost field
         * @param partMax This will hold the value from the Max field
         * @param partMin This will hold the value from the Min field
         * @param partMachineId This will hold the value from the Machine ID field
         */
        String partId = inHouseModIdText.getText();
        String partName = inHouseModNameText.getText();
        String partInv = inHouseModStockText.getText();
        String partCost = inHouseModCostText.getText();
        String partMax = inHouseModMaxText.getText();
        String partMin = inHouseModMinText.getText();
        String partMachineId = inHouseModAddMachIDText.getText();

        /**
         This will run the data through a series of checks to make sure it is the right type of data.
         Each check produces its own popup window if it fails.
        */
        DataCheck.isInteger(partInv, "Inventory");
        DataCheck.isDouble(partCost);
        DataCheck.isInteger(partMax, "Max");
        DataCheck.isInteger(partMin, "Min");
        DataCheck.isInteger(partMachineId, "Machine ID");

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
            Integer.parseInt(partMachineId);
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
        int machineId = Integer.parseInt(partMachineId);

        //A final check to make sure there is nothing negative, no zero price, all the data's filled out, and that the data is in the correct range
        DataCheck.hasData(partName, "Name");
        DataCheck.isNotNegative(stock, "Inventory");
        DataCheck.isNotNegative(min, "Minimum");
        DataCheck.isNotNegative(max, "Maximum");
        DataCheck.isZero(price);
        DataCheck.minMax(min, max);
        DataCheck.isBetween(stock, min, max);

        /**
         * This will run the data through a series of checks to make sure it is valid before saving it
         */
        if(min > 0 && max > 0 && stock > 0)
        {
            if (!(partName.isBlank() || partName.isEmpty()))
            {
                if (max > min)
                {
                    if (stock >= min && stock <= max)
                    {
                        if (price != null && price != 0.00)
                        {
                            System.out.println(partName + "," + stock + "," + price + "," + max + "," + min + "," + machineId);
                            InHouse newPart = new InHouse(id, partName, price, stock, min, max, machineId);

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
     * @param modPartInfo This variable is used as a failsafe for the data
     */
    int index = -1;
    InHouse modPartInfo = (InHouse) null;

    /**
     * This will set up the data into its fields before the scene is fully loaded for the user to see.
     * @param modPart This is the part that the user wishes to modify
     * @param sPIndex This is the index of the part the user wishes to modify so that the part can be updated once modification is complete.
     */
    public void modSelect(Part modPart, int sPIndex)
    {
        modPartInfo = (InHouse) modPart;
        index = sPIndex;

        System.out.println(modPartInfo.getName() + " is at index location: " + index);

        inHouseModIdText.setText(Integer.toString(modPartInfo.getId()));
        inHouseModNameText.setText(modPartInfo.getName());
        inHouseModStockText.setText(Integer.toString(modPartInfo.getStock()));
        inHouseModCostText.setText(Double.toString(modPartInfo.getPrice()));
        inHouseModMaxText.setText(Integer.toString(modPartInfo.getMax()));
        inHouseModMinText.setText(Integer.toString(modPartInfo.getMin()));

        /**
         * This will make sure that the machineID field has a value.
         * If it is empty, it fills the field with "Enter machine id".
         * This is the case when the data is passed FROM the outsourced to the InHouse scenes.
         */
        if(modPartInfo.getMachineId() == 0)
        {
            inHouseModAddMachIDText.setText("Enter Machine ID");
        }
        else
        {
            inHouseModAddMachIDText.setText(Integer.toString(modPartInfo.getMachineId()));
        }
    }
}
