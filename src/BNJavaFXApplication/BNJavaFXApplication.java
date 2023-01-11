package BNJavaFXApplication;

/**
 * @author Brian Nason, Student Number xxxxxxxxx
 */

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class is the starting point for the program. It gets it going.
 */
public class BNJavaFXApplication extends Application {

    /**
     * This simply prints my information into the program to identify me, the student.
     * @throws Exception In case the program doesn't load proper
     */
    @Override
    public void init() throws Exception {
        System.out.println("Name: Brian Nason\nStudent Number: xxxxxxxxx\nFinal Project for Software1");
    }

    /**
     * This is the starting of the program. It will setup the main stage from which the program operates.
     * @param mainStage The stage/window the program will operate in
     * @throws Exception In case the program doesn't start proper
     */
    @Override
    public void start(Stage mainStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
        mainStage.setTitle("Brian Nason: Software 1 Final Project");
        mainStage.setScene(new Scene(root, 800, 400));
        mainStage.show();
    }

    /**
     * This is some initialization data for debug/create/test purposes. Can be deleted if needed.
     * @param args For the program initialization
     */
    public static void main(String[] args) {

        // next 14 lines (28 - 42) is Initialization data for testing purposes
        InHouse part1 = new InHouse(75, "Do-hickie", 14.55, 75, 2, 100, 45684);
        InHouse part2 = new InHouse(456, "Thingamajig", 85.88, 1000, 8455, 50000, 45668554);
        InHouse part3 = new InHouse(475, "Whatchamacallit", 2.55, 12, 8, 24, 45878);
        InHouse part4 = new InHouse(785, "Whosawhatsit", 1.75, 82, 81, 83, 43218);
        InHouse part5 = new InHouse(755, "Sprocket", 1.15, 42, 10, 57, 98743654);
        Outsourced part6 = new Outsourced(575, "Flux Capacitor",17.99,155, 100, 175, "Doc Brown LLC");
        Outsourced part7 = new Outsourced(565, "Chumpdabumb",28.99,125, 25, 126, "Evil Inc.");
        Outsourced part8 = new Outsourced(555, "Filled Flaxiator",2.99,7, 5, 9, "Hogwarts");
        Outsourced part9 = new Outsourced(545, "Chupacabre",7.69,2, 0, 10, "Man's best friend");

        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addPart(part5);
        Inventory.addPart(part6);
        Inventory.addPart(part7);
        Inventory.addPart(part8);
        Inventory.addPart(part9);

        Product product1 = new Product(666, "Compressed Concurance", 125.55, 66, 12, 287);
        product1.addAssociatedPart(part1);
        product1.addAssociatedPart(part3);
        Product product2 = new Product(777, "Expanded Mind", 277.79, 77, 13, 87);
        product2.addAssociatedPart(part8);
        product2.addAssociatedPart(part6);
        product2.addAssociatedPart(part7);
        Product product3 = new Product(888, "Talented Talker", 828.44, 88, 14, 97);
        product3.addAssociatedPart(part5);
        product3.addAssociatedPart(part3);
        product3.addAssociatedPart(part9);
        product3.addAssociatedPart(part4);
        product3.addAssociatedPart(part1);
        Product product4 = new Product(999, "W.W.I.A.F.T.M.", 122.12, 99, 15, 187);
        product4.addAssociatedPart(part8);


        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);
        Inventory.addProduct(product4);


        launch(args);
    } //The launch() is what calls the life-cycle
}
