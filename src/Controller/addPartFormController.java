package Controller;

/**
 * @author Brian Nason, Student Number xxxxxxxxx
 */

import Model.InHouse;
import Model.Inventory;
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
 * This class controls the addPartForm.fxml file
 */
public class addPartFormController implements Initializable {

    /**
     * @param stage this sets up a new stage to use if the user navigates from this page
     */
    Stage stage;

    @FXML
    private TextField inHouseAddPartNameText;

    @FXML
    private TextField inHouseAddPartStockText;

    @FXML
    private TextField inHouseAddCostText;

    @FXML
    private TextField inHouseAddMaxText;

    @FXML
    private TextField inHouseAddMinText;

    @FXML
    private TextField inHouseAddMachIDText;

    @FXML
    private RadioButton inHousePartInHouseRadio;

    @FXML
    private ToggleGroup addPartRadioIN;

    /**
     * This will change the scene to the main form
     * @param event When the user clicks the Cancel button, they will be directed to the main form.
     * @throws IOException Handles scene load exceptions
     */
    @FXML
    void onActionGoToMainForm(ActionEvent event) throws IOException
    {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent sceneParent = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(sceneParent));
        stage.show();
    }

    /**
     * This method changes the form from the InHouse form to the Outsourced form
     * @param event User clicking on Outsourced radio Button
     * @throws IOException Handles scene load exceptions
     */
    @FXML
    void onActionGoToOutsourced(ActionEvent event) throws IOException
    {
        stage = (Stage) ((RadioButton) event.getSource()).getScene().getWindow();
        Parent sceneParent = FXMLLoader.load(getClass().getResource("/view/addPartFormOutsourced.fxml"));
        stage.setScene(new Scene(sceneParent));
        stage.show();
    }

    /**
     * This method will save the new part and add it to the inventory.
     * @param event When the user clicks on the save button, the part will be saved and the scene will change to the main form.
     * @throws IOException Handles scene load exceptions
     */
    @FXML
    void onActionSaveNewPart(ActionEvent event) throws IOException
    {
        /**
         * This will get the data from the fields to store as the new/updated part info
         * @param name This will hold the value from the Name field
         * @param partInv This will hold the value from the Inv field
         * @param partCost This will hold the value from the Price/Cost field
         * @param partMax This will hold the value from the Max field
         * @param partMin This will hold the value from the Min field
         * @param machineID This will hold the value from the Machine ID field
         */
        String name = inHouseAddPartNameText.getText();
        String partInv = inHouseAddPartStockText.getText();
        String partCost = inHouseAddCostText.getText();
        String partMax = inHouseAddMaxText.getText();
        String partMin = inHouseAddMinText.getText();
        String machineID = inHouseAddMachIDText.getText();

        /**
         This will run the data through a series of checks to make sure it is the right type of data.
         Each check produces its own popup window if it fails.
         */
        DataCheck.isInteger(partInv, "Inventory");
        DataCheck.isDouble(partCost);
        DataCheck.isInteger(partMax, "Max");
        DataCheck.isInteger(partMin, "Min");
        DataCheck.isInteger(machineID, "Machine ID");
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
            Integer.parseInt(machineID);
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
        int machineId = Integer.parseInt(machineID);

        //A final check to make sure there is nothing negative, no zero price, all the data's filled out, and that the data is in the correct range
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
            if (!(name.isBlank() || name.isEmpty()))
            {
                if (max > min)
                {
                    if (stock >= min && stock <= max)
                    {
                        if (price != null && price != 0.00)
                        {
                            System.out.println(name + "," + stock + "," + price + "," + max + "," + min + "," + machineId);
                            int id = DataCheck.generateID();
                            System.out.println("The part's id is: " + id);

                            InHouse newPart = new InHouse(id, name, price, stock, min, max, machineId);
                            Inventory.addPart(newPart);

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
}
