package Controller;

/**
 * @author Brian Nason, Student Number xxxxxxxxx
 */

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.Random;

/**
 * This class contains methods for checking the data throughout the program run.
 */
public class DataCheck
{
    /**
     * This method checks that data entered is between two values
     * @param inventory the data the user enters
     * @param min The low-end value to check the data against
     * @param max The high-end value to check the data against
     */
    public static void isBetween(Integer inventory, Integer min, Integer max)
    {

        //It first checks to make sure the Inventory is larger than or equal to Min value
        if (inventory < min)
        {
            //Sets the values to pass through to the generic popup window method later in the DataCheck class
            String message = (" (Inv number) needs to be larger than " + min);
            String title = "Range Check Error.";

            //Personalizes the popup to the specific user entered data in the Inventory field
            String input = String.valueOf(inventory);

            //Sends the data to the popup method to create a popup window for the user to interact with.
            popupWindow(input, message, title);
        }

        //It then checks to make sure the Inventory is less than or equal to the Max value
        else if (inventory > max)
        {
            //Sets the values to pass through to the generic popup window method later in the DataCheck class
            String message = (" (Inv number) needs to be less than " + max);
            String title = "Range Check Error.";

            //Personalizes the popup to the specific user entered data in the Inventory field
            String input = String.valueOf(inventory);

            ///Sends the data to the popup method to create a popup window for the user to interact with.
            popupWindow(input, message, title);
        }

        //If the input date for Inventory passes both above checks, then it is good to go
        // and the method returns to the calling window.
        else
        {
            return;
        }

    }

    /**
     * This method checks to make sure the product data that is passed has/doesn't have the passed product.
     * @param product The product to check its associated parts
     * @param part The part to check against the product's associated parts list.
     * @return Returns T if the part already exists, F if it doesn't
     */
    public static boolean hasParts(Product product, Part part)
    {

        /**
         * @param hasPart a boolean return for if the part is contained in the product or not
         */
        boolean hasPart = false;

        /**
         * @param productToSearch A variable to hold the product the user passes through the method
         * @param searchForParts A variable to hold all the associated parts of the product the user passes through
         */
        Product productToSearch = product;
        ObservableList<Part> searchForParts = productToSearch.getAllAssociatedParts();

        /**
         * This goes through the entire associatedParts list of the selected product and checks to see if the part exists already.
         * If it does, it changes the boolean value to T and returns that value to the method call.
         */
        for(int i = 0; i < searchForParts.size(); i++ )
        {
            if(searchForParts.get(i) == part)
            {
                hasPart = true;
                popupWindow(part.getName(), " belongs to " + productToSearch.getName(), "Part Has Parent");
            }
        }
        /**
         * If no part exists, the method returns F to the method call.
         */
        return hasPart;
    }

    /**
     * This method makes sure the user-entered Min value is less than the Max value (and vice versa)
     * @param min The passed min value to check
     * @param max The passed max value to check
     */
    public static void minMax(Integer min, Integer max)
    {
        //As long as min is less than max, then the method will return to the calling window
        if (min < max)
        {
            return;
        }

        /**
        If the input data is not correct (Min is greater than max), then it creates a popup window for the user
        informing them of this and then sending the program back to the calling window for revisions.
        */
        else
        {
            //Sets the values to pass through to the generic popup window method later in the DataCheck class
            String message = " (Min value) is NOT less than " + max + " (Max value)";
            String title = "Range Check Error.";

            //Personalizes the popup to the specific user entered data
            String input = String.valueOf(min);

            //Sends the data to the popup method to create a popup window for the user to interact with.
            popupWindow(input, message, title);
        }
    }

    /**
     * This Method is here to check if the input is an int value
     * @param input This holds the input value to check if the data is an integer type or string type
     * @return Returns T if the input is of Int type, F if it is String (or not Int)
     */
    public static boolean intOrString(String input)
    {
        /**
        The below try/catch statement is to see if the user input passed as a string can be parsed into an integer value.
        If it can't, then the catch statement executes, returning the boolean value as false.
         */
        try
        {
            int intCheck = Integer.parseInt(input);
        }
        catch (NumberFormatException e)
        {
            return false;
        }

        /**
         * If the catch statement doesn't execute, then the input information is of an Int type, and so the method
         * returns the boolean value of true.
         */
        return true;
    }

    /**
     * This method will check the input data to make sure it isn't a 0 or a negative number
     * @param input The user input to check if it is of a negative value
     * @param type The type of error depending on which window it is called from
     */
    public static void isNotNegative (int input, String type)
    {
        //If the input value is less than or equal to 0, then this branch will execute
        if (input <= 0)
        {

            //This is for debug purposes and only prints to the console
            System.out.println(input + " cannot be empty, zero, or negative.");

            /**
             * @param message Used to set up data for the popup window
             * @param title Used to set up data for the popup window
             */
            String message = " cannot be empty, zero, or negative.";
            String title = "Negative Number Alert!";

            /**
             * Sends the data to the popup method to create a popup window for the user to interact with.
             * Uses the input String value "type" to specify the type of error to the user.
             */
            popupWindow(type, message, title);
        }

        //If the input value is greater than zero, then the method returns to the calling window
        else
            return;
    }


    /**
     * This method is used to verify that the data input is of integer type
     * @param input The input string to validate if it is of Int type
     */
    public static void isInteger(String input, String type)
    {
        /**
         * The try executes to see if it can convert the input string into a number.
         * If conversion is successful, then it returns to the calling screen.
         * @param intCheck This is used to hold the data if it IS converted.
         */
        try
        {
            int intCheck = Integer.parseInt(input);
            System.out.println(input + " is a number.");
            return;
        }

        /**
         * If the parse fails, then the Catch statement creates a popup window to the user.
         * It then returns to the calling scene.
         */
        catch (NumberFormatException e)
        {
            System.out.println(input + " is NOT a number.");
            String message = ("Your input of \"" + input + "\" in the " + type + " field");
            String title = "Integer Check Error";
            popupWindow(message, "\n is Not an Integer Number.", title);
        }
    }

    /**
     * This method confirms that the input data is of Double type
     * @param input The input in string form to convert/test
     */
    public static void isDouble(String input)
    {
        /**
         * This will take in the string data and try to convert it to a Double value
         * If it succeeds, it returns nothing to the calling screen.
         * @param doubleCheck This is the variable to hold the user input while checking
         */
        try
        {
            Double doubleCheck = Double.parseDouble(input);
            System.out.println(input + " is a Double.");
            return;
        }
        /**
         * If the parse fails, then the number is not of a double type, the catch statement executes,
         * and a popup window is created for the user to inform them of this.
         * It is then returned to the calling screen.
         */
        catch (NumberFormatException e)
        {
            //Used for debug purposes
            System.out.println(input + " is NOT a Double.");

            /**
             * @param message This creates the message to pass to the popup window
             * @param title This creates the Title to pass to the popup window.
             */
            String message = ("Your input value \"" + input + "\"");
            String title = "Double Check Error.";
            popupWindow(message, "\nis not of a Price type.", title);
        }
    }

    /**
     * This method is used to make sure the PartIDs are not duplicated
     * @param input The id of the part that is passed in to check if it's id already exists.
     * @return Returns T if it is a Duplicate, F if it isn't
     */
    public static boolean isDuplicate(int input)
    {
        /**
         * @param isDup The boolean variable to hold the T/F value if the input is T or F.
         *              Initialized to F.
         */
        boolean isDup = false;

        /**
         * This will check through the getAllParts to compare ID. if the ID exists, then the part ID can't be used.
         */
        for(Part part : Inventory.getAllParts())
        {
            if (input == part.getId())
            {
                System.out.println(input + " is already a part ID.");
                isDup = true;
            }
        }
        for(Product product : Inventory.getAllProducts())
        {
            if (input == product.getId())
            {
                System.out.println(input + " is already a product ID");
                isDup = true;
            }
        }

        return isDup;
    }

    /**
     * This method makes sure the input data for Cost isn't 0
     * @param cost The input Cost of a part or product
     */
    public static void isZero (Double cost)
    {
        /**
         * If the cost of a part/product is either not entered (null) or 0 or negative, this branch executes.
         * It creates a popup to the user informing them of the error.
         */
        if (cost <= 0.00 || cost == null)
        {
            // Used for debugging purposes
            System.out.println(cost + " price cannot be empty, zero, or negative.");

            /**
             * @param message Sets up the message for the popup window
             * @param title sets the title for the popup window
             * @param sCost takes the value of the passed variable and puts it in the popup window.
             */
            String message = "cannot be empty, zero, or negative.";
            String title = "Zero Data Error!.";
            String sCost = String.valueOf(cost);
            popupWindow("PRICE/COST ", message, title);
        }
        else
            return;
    }

    /**
     * This method makes sure the input has data
     * @param dataCheck The data being passed in to verify if it has any data or not
     * @param type the type of data being passed in.
     */
    public static void hasData(String dataCheck, String type)
    {
        /**
         * This branch executes to inspect the input data.
         * If it is found to be empty or blank, then a popup window is presented to the user informing them of that fact.
         */
        if(dataCheck.isEmpty() || dataCheck.isBlank())
        {
            //for debugging purposes
            System.out.println(type + " Cannot be left blank.");

            /**
             * @param message used to set up the message in the popup window
             * @param title Used to set up the title of the popup window.
             */
            String message = " cannot be left blank";
            String title = "Data Error!";
            popupWindow(type, message, title);
        }
        else
            return;
    }

    /**
     * This is the method constructor for the generic popup window used throughout the code.
     * @param input This is the data in question for the popup window to display
     * @param message This is the message for the popup window to display
     * @param title This is the title of the popup window.
     */
    public static void popupWindow(String input, String message, String title)
    {
        /**
         * @param popUp Sets up a new stage for the popup window to display to the user
         */
        Stage popUp = new Stage();
        popUp.initModality(Modality.APPLICATION_MODAL);
        popUp.setTitle(title);

        /**
         * @param popUpMessage Sets up the message to display by combining a couple variables.
         * @param standardMessage This is the message that is placed between the custom message and the ok button
         */
        Label popUpMessage = new Label(input + message);
        Label standardMessage = new Label("Please fix this error and try again.");

        /**
         * @param okButton sets up the okay button. When the button is clicked, the window disappears.
         */
        Button okButton = new Button("OK");
        okButton.setOnAction(e -> popUp.close());

        VBox popUpBox = new VBox();
        popUpBox.getChildren().addAll(popUpMessage, standardMessage, okButton);
        popUpBox.setAlignment(Pos.CENTER);


        Scene scene = new Scene(popUpBox, 300, 100);
        popUp.setScene(scene);
        popUp.showAndWait();

    }

    /**
     * This method is used to generate unique IDs for the parts and products.
     * @return Returns an ID of Int type
     */
    public static int generateID()
    {
        /**
         * @param genID sets up a blank variable called genID to hold the generated ID
         * @param randID sets up a blank Double variable to hold the randomID before reduction to Int type
         * @param rand sets up a new random call.
         */
        int genID;
        double randID;
        Random rand = new Random();

        /**
         * This will find a random double, multiply it by 10 mil to move the decimal point, and then convert it to int type
         */
        randID = (rand.nextDouble() * 100000000);
        genID = (int) randID;

        /**
         * This will send the newly created ID through a check to make sure it isn't a duplicate.
         * If it is, then it will re-run the ID generation until it is unique.
         */
        if(isDuplicate(genID))
        {
            generateID();
        }
        return genID;
    }
}