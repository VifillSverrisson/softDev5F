/**
 * Created by vifillsverrissonMacBookPro on 06/04/16.
 */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Scenes extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    Stage window3;

    Scene Scene3;
    GridPane layout3;

    TextField firstName;
    TextField lastName;

    TextField phoneNumber;
    TextField emailAddress;

    @Override
    public void start(Stage primaryStage) {
        window3 = primaryStage;
        window3.setTitle("FlightSearch®");
        window3.show();

        layout3 = new GridPane();
        layout3.setPadding(new Insets(30,50,50,30));
        layout3.setHgap(20);
        layout3.setVgap(20);

        Scene3 = new Scene(layout3,800,700);

        firstName = new TextField();
        lastName = new TextField();
        dateOfBirth = new TextField();
        phoneNumber = new TextField();
        emailAddress = new TextField();

        Label  = new Label("First name: ");
        Label  = new Label("Last name: ");
        Label  = new Label("D");
        Label  = new Label("");
        Label  = new Label("");


    }
}
