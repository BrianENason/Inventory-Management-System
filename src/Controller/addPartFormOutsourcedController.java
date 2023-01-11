package Controller;

/**
 * @author Brian Nason, Student Number xxxxxxxxx
 */

import Model.Inventory;
import Model.Outsourced;
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
 * This class controls the addPartFormOutsourced.fxml file
 */
public class addPartFormOutsourcedController implements Initializable {

    /**
     * @param stage this sets up a new stage to use if the user navigates from this page
     */
    Stage stage;

    @FXML
    private TextField outsourcedAddPartNameText;

    @FXML
    private TextField outsourcedAddPartStockText;

    @FXML
    private TextField outsourcedAddCostText;

    @FXML
    private TextField outsourcedAddMaxText;

    @FXML
    private TextField outsourcedAddMinText;

    @FXML
    private TextField outsourcedAddCompanyText;

    @FXML
    private RadioButton outsourcedPartOutsourcedRadio;

    @FXML
    private ToggleGroup addPartRadioOUT;

    /**
     * This method changes the form from the Outsourced form to the InHouse form
     * @param event User clicking on InHouse radio Button
     * @throws IOException Handles Scene Loading Exceptions
     */
    @FXML
    void onActionGoToInHouse(ActionEvent event) throws IOException
    {
        stage = (Stage)((RadioButton)event.getSource()).getScene().getWindow();
        Parent sceneParent = FXMLLoader.load(getClass().getResource("/view/addPartForm.fxml"));
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
     * This method will save the new part and add it to the inventory.
     * @param event When the user clicks on the save button, the part will be saved and the scene will change to the main form.
     * @throws IOException Handles Scene Loading Exceptions
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
         * @param companyName This will hold the value from the Company Name field
         */
        String name = outsourcedAddPartNameText.getText();
        String partInv = outsourcedAddPartStockText.getText();
        String partCost = outsourcedAddCostText.getText();
        String partMax = outsourcedAddMaxText.getText();
        String partMin = outsourcedAddMinText.getText();
        String companyName = outsourcedAddCompanyText.getText();

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
            if (!(name.isBlank() || name.isEmpty()))
            {
                if (!(companyName.isBlank() || companyName.isEmpty()))
                {
                    if (max > min)
                    {
                        if (stock >= min && stock <= max)
                        {
                            if (price != null && price != 0.00)
                            {
                                System.out.println(name + "," + stock + "," + price + "," + max + "," + min + "," + companyName);
                                int id = DataCheck.generateID();
                                System.out.println("The part's id is: " + id);

                                Outsourced newPart = new Outsourced(id, name, price, stock, min, max, companyName);
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
}
