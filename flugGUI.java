import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by vifillsverrisson on 24/02/16.
 */
public class flugGUI extends Application {

    public static void main(String[] args) { launch(args); }

    Stage window;
    Scene scene1;
    ComboBox<String> to,from;
    ComboBox<Integer> adults, children;
    RadioButton oneWay, twoWay;
    ToggleGroup group1;
    DatePicker departure, arrival;
    CheckBox flex;
    Button searchBtn;

    GridPane layout1 = new GridPane();

    @Override
    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;
        window.setTitle("FlightSearch®");


        // To and from dropdownbox
        to = new ComboBox<String>();
        to.getItems().addAll("Reykjavík","Vestmanneyjar", "Akureyri", "Ísafjörður", "Egilsstaðir", "Nuuk",
                "Bíldudalur");
        to.setPromptText("Áfangastaður");
        layout1.add(to,0,2);
        GridPane.setColumnSpan(to,2);

        from = new ComboBox<String>();
        from.getItems().addAll("Reykjavík","Vestmanneyjar", "Akureyri", "Ísafjörður", "Egilsstaðir");
        from.setPromptText("Brottfararstaður");
        layout1.add(from,0,1);
        GridPane.setColumnSpan(from,2);

        // One or two way
        group1 = new ToggleGroup();

        twoWay = new RadioButton("Báðar leiðir");
        twoWay.setToggleGroup(group1);
        twoWay.setSelected(true);
        twoWay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                arrival.setVisible(true);
            }
        });
        layout1.add(twoWay,0,0); // hér vantar handler sem tekur út datePicker'inn fyrir áfangastaðinn
        GridPane.setColumnSpan(twoWay,2);

        oneWay = new RadioButton("Ein leið");
        oneWay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                arrival.setVisible(false);
            }
        });
        oneWay.setToggleGroup(group1);
        layout1.add(oneWay,2,0);


        // DatePicker
        Date now = Calendar.getInstance().getTime();;
        LocalDate today = now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        departure = new DatePicker();
        departure.setValue(today);
        layout1.add(departure,2,1);

        arrival = new DatePicker();
        arrival.setValue(today.plusDays(7));
        layout1.add(arrival,2,2);


        // Number of adult and child tickets
        Label adult = new Label("Fullorðnir:");
        layout1.add(adult,0,3);
        adults = new ComboBox<Integer>();
        adults.getItems().addAll(0,1,2,3,4,5,6,7,8,9);
        adults.setValue(1);
        layout1.add(adults,0,4);

        Label child = new Label("Börn:");
        layout1.add(child,1,3);
        children = new ComboBox<Integer>();
        children.getItems().addAll(0,1,2,3,4,5,6,7,8,9);
        children.setValue(0);
        layout1.add(children,1,4);


        // Flexable Dates
        flex = new CheckBox("Sveigjanlegar \ndagsetningar");
        flex.setSelected(true);
        flex.setMinHeight(Region.USE_PREF_SIZE);
        layout1.add(flex,2,4);


        // Layout
        layout1.setHgap(20);
        layout1.setVgap(20);
        layout1.setPadding(new Insets(270,80,120,580));
        layout1.getColumnConstraints().add(0, new ColumnConstraints(65));
        layout1.getColumnConstraints().add(1, new ColumnConstraints(65));
        layout1.setGridLinesVisible(false);

        // Search button
        searchBtn = new Button("Search");
        layout1.add(searchBtn,2,5);
        searchBtn.setAlignment(Pos.CENTER);


        // Background
        BackgroundImage Airplane = new BackgroundImage(
                new Image("file:///Users/vifillsverrissonMacBookPro/Documents/UniversityOfIceland/FirstYear/" +
                        "SpringSemester/SoftwareDevelopment/flugvel.BI.jpg",960,600,false,true),
                BackgroundRepeat.REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
       // layout1.getStylesheets().add("Flugvel.css");



        // Scene & show
        scene1 = new Scene(layout1,960,600);


        primaryStage.setScene(scene1);
        primaryStage.show();
    }


}
