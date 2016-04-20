package GUI;

import Main.*;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.text.DateFormatter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

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

    ArrayList<Flight[]> searchResults = new ArrayList<>();


    @Override
    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;
        window.setTitle("FlightSearch®");

        scene1();
    }


    private void scene1() {

        GridPane layout1 = new GridPane();
        // To and from dropdownbox
        to = new ComboBox<String>();
        to.getItems().addAll("Akureyri", "Egilsstaðir", "Ísafjörður", "Reykjavík", "Vopnafjörður", "Grímsey", "Þórshöfn", "Vestmannaeyjar",
                "Höfn í Hornafirði", "Húsavík", "Bíldudalur", "Gjögur", "Keflavík");
        to.setPromptText("Áfangastaður");
        layout1.add(to,0,2);
        GridPane.setColumnSpan(to,2);

        from = new ComboBox<String>();
        from.getItems().addAll("Akureyri", "Egilsstaðir", "Ísafjörður", "Reykjavík", "Vopnafjörður", "Grímsey", "Þórshöfn", "Vestmannaeyjar",
                "Höfn í Hornafirði", "Húsavík", "Bíldudalur", "Gjögur", "Keflavík");
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
        adult.setId("basic");
        layout1.add(adult,0,3);
        adults = new ComboBox<Integer>();
        adults.getItems().addAll(0,1,2,3,4,5,6,7,8,9);
        adults.setValue(1);
        layout1.add(adults,0,4);

        Label child = new Label("Börn:");
        child.setId("basic");
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

        Alert error = new Alert(Alert.AlertType.ERROR);
        searchBtn.setOnAction(event -> {
            if ( to.getValue() != null && from.getValue() != null) {
                if (departure.getValue().isBefore(arrival.getValue()) && (departure.getValue().isAfter(today) || departure.getValue().isEqual(today))) {
                    if (adults.getValue() + children.getValue() > 0) {
                        scene2();
                    } else {
                        error.setHeaderText("No passengers!");
                        error.setContentText("Please enter the amount of tickets you wish to order.");
                        error.showAndWait();
                    }
                } else {
                    error.setHeaderText("Invalid dates!");
                    error.setContentText("Return date is before departure date.");
                    error.showAndWait();
                }
            } else {
                error.setHeaderText("Missing destination or departure point");
                error.setContentText("Please describe where you are departing from\nand where you are going.");
                error.showAndWait();
            }
        });
        layout1.add(searchBtn,2,5);
        searchBtn.setAlignment(Pos.CENTER);


        // Background
        BackgroundImage Airplane = new BackgroundImage(
                new Image(flugGUI.class.getResourceAsStream("flugvelBI.jpg"),960,600,false,true),
                BackgroundRepeat.REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
        layout1.setBackground(new Background(Airplane));



        // Scene & show
        scene1 = new Scene(layout1,960,600);

        scene1.getStylesheets().add(this.getClass().getResource("Skyblue.css").toExternalForm());

        window.setScene(scene1);
        window.show();
    }

    private Date convertLocalDateToDate(LocalDate localDate){
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        return date;

    }

    private void search(){
        SearchController searchController = new SearchController();
        Date departureDate = convertLocalDateToDate(departure.getValue());
        System.out.println(departureDate);
        if (oneWay.isSelected()) {
            Flight[] tmp = searchController.searchOne(from.getValue(), to.getValue(), adults.getValue(), children.getValue(), convertLocalDateToDate(departure.getValue()), flex.isSelected());
            System.out.println("Number of matching flights: " + tmp.length);
            searchResults.add(tmp);
        } else {
            Flight[][] tmp = searchController.searchBoth(from.getValue(), to.getValue(), adults.getValue(), children.getValue(),convertLocalDateToDate(departure.getValue()), convertLocalDateToDate(arrival.getValue()),  flex.isSelected());
            searchResults.addAll(Arrays.asList(tmp));
        }

    }

    Scene scene2;
    GridPane layout2;

    ArrayList<Flight> selectedFlights = new ArrayList<>();

    private void scene2() {
        search();

        ArrayList<String> labs= new ArrayList<>();
        labs.add( "Please select your departure flight:");
        labs.add( "Please select your return flight:");

        IntegerProperty total = new SimpleIntegerProperty();
        IntegerProperty departurePrice = new SimpleIntegerProperty();
        IntegerProperty returnPrice = new SimpleIntegerProperty();
        total.bind(departurePrice.add(returnPrice));


        Label priceLab = new Label();
        priceLab.textProperty().bind(total.asString());

        layout2 = new GridPane();
        layout2.setPadding(new Insets(50,170,50,170));
        layout2.setHgap(30);
        layout2.setVgap(20);

        scene2 = new Scene(layout2, 960, 600);

        ArrayList<TableView<Flight>> selectionTables = new ArrayList<>();

        // Table  #1 = departure flight, #2 = return flight
        for (int i = 0; i < searchResults.size(); i++) {
            selectionTables.add(new TableView<Flight>());

            TableColumn<Flight,String> departureCol = new TableColumn<>("Departure");
            departureCol.setMinWidth(80);
            departureCol.setCellValueFactory(new PropertyValueFactory<>("departure"));

            SimpleDateFormat getTime = new SimpleDateFormat("HH:mm");

            TableColumn<Flight,String> timeCol = new TableColumn<>("Time");
            timeCol.setMinWidth(50);
            timeCol.setCellValueFactory( param -> {
                return new ReadOnlyObjectWrapper<>(getTime.format(param.getValue().getDateAndTime()));
            });

            TableColumn<Flight,String> arrivalCol = new TableColumn<>("Arrival");
            arrivalCol.setMinWidth(80);
            arrivalCol.setCellValueFactory(new PropertyValueFactory<>("arrival"));

            TableColumn<Flight,String> priceCol = new TableColumn<>("Price");
            priceCol.setMinWidth(50);
            priceCol.setCellValueFactory( param -> {
                 return new ReadOnlyObjectWrapper<>(Integer.toString(param.getValue().calcPrice()));
            });

            SimpleDateFormat getDate = new SimpleDateFormat("dd. MMM EEEEEEEEE yyyy");

            TableColumn<Flight,String> dateCol = new TableColumn<>("Departure date");
            dateCol.setMinWidth(180);
            dateCol.setCellValueFactory( param -> {
                return new ReadOnlyObjectWrapper<>(getDate.format(param.getValue().getDateAndTime()));
            });

            TableColumn<Flight,String> airlineCol = new TableColumn<>("Airline");
            airlineCol.setMinWidth(130);
            airlineCol.setCellValueFactory(new PropertyValueFactory<>("company"));

            selectionTables.get(i).setItems(FXCollections.observableArrayList(searchResults.get(i)));

            selectionTables.get(i).getColumns().addAll(departureCol,timeCol,arrivalCol,priceCol,dateCol,airlineCol);

            selectionTables.get(i).setMinWidth(620);

            Label tmp = new Label(labs.get(i));
            tmp.setId("bold");

            layout2.add(tmp,0,2*i);
            layout2.add(selectionTables.get(i), 0, 2*i+1);
            GridPane.setColumnSpan(selectionTables.get(i),5);
        }

        layout2.getStylesheets().add(this.getClass().getResource("Skyblue.css").toExternalForm());

        selectionTables.get(0).setRowFactory( tv -> {
            TableRow<Flight> row = new TableRow<>();
            row.setOnMouseClicked( event -> {
                if (!row.isEmpty()) {
                    departurePrice.setValue(row.getItem().calcPrice());
                    System.out.println("selected");
                }
            });
            return row;
        });

        if (searchResults.size() > 1) {
            selectionTables.get(1).setRowFactory( tv -> {
                TableRow<Flight> row = new TableRow<>();
                row.setOnMouseClicked( event -> {
                    if (!row.isEmpty()) {
                        returnPrice.setValue(row.getItem().calcPrice());
                        System.out.println("selected");
                    }
                });
                return row;
            });
        }

        int pos = 2*searchResults.size();

        Button backBtn = new Button("Back");
        backBtn.setMinWidth(100);
        backBtn.setOnAction( event -> {
            searchResults.clear();
            window.setScene(scene1);
        });

        layout2.add(backBtn,0,pos+1);

        HBox align = new HBox();
        layout2.add(align,2,pos);

        HBox align2 = new HBox();
        layout2.add(align2,1,pos+1);

        layout2.add(priceLab,4, pos);
        Label totalLab = new Label("Total:");
        totalLab.setId("bold");
        layout2.add(totalLab, 3, pos);
        priceLab.setId("bold");
        GridPane.setHgrow(align,Priority.ALWAYS);

        Button toScene3btn = new Button("Next step");
        toScene3btn.setMinWidth(100);
        layout2.add(toScene3btn, 3, pos+1);
        GridPane.setColumnSpan(toScene3btn,2);
        GridPane.setHalignment(toScene3btn, HPos.RIGHT);

        toScene3btn.setOnAction( event -> {
            try {
                selectedFlights.clear();
                for (int i = 0; i < searchResults.size(); i++) {
                    Flight rowdata = selectionTables.get(i).getSelectionModel().getSelectedItem();
                    if(rowdata != null) {
                        selectedFlights.add(selectionTables.get(i).getSelectionModel().getSelectedItem());
                    } else {
                        throw new NullPointerException();
                    }
                }
                System.out.println("selectedFligths.size : " + selectedFlights.size());
                scene3();
            } catch (Exception e) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setHeaderText("No flight selected!");
                error.setContentText("Please select a flight.");
                error.showAndWait();
            }
        });

        BackgroundImage clouds = new BackgroundImage(
                new Image(flugGUI.class.getResourceAsStream("cloudsBI.jpg"),960,600,false,true),
                BackgroundRepeat.REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
        layout2.setBackground(new Background(clouds));

        window.setScene(scene2);
    }




    Scene scene3;
    GridPane layout3;

    ArrayList<Customer> customers = new ArrayList<>();

    private void scene3() {

        layout3 = new GridPane();
        layout3.setPadding(new Insets(100,100,100,100));
        layout3.setHgap(20);
        layout3.setVgap(10);

        ArrayList<String> labelList = new ArrayList<>();
        labelList.add("First name:");
        labelList.add("Last name:");
        labelList.add("Date of birth:");
        labelList.add("Phone number:");
        labelList.add("Email address");

        ArrayList<TextField> firstNameList = new ArrayList<>();
        ArrayList<TextField> lastNameList = new ArrayList<>();
        ArrayList<ComboBox<Integer>> dateList = new ArrayList<>();
        ArrayList<ComboBox<String>> monthList = new ArrayList<>();
        ArrayList<ComboBox<Integer>> yearList = new ArrayList<>();
        ArrayList<TextField> phoneNumberList = new ArrayList<>();
        ArrayList<TextField> emailList = new ArrayList<>();

        int numberOfPassengers = adults.getValue() + children.getValue();

        for (int i = 0; i < numberOfPassengers; i++) {
            int labCount = 0;

            Label custmerLab = new Label("Fill in info for customer " + (i+1) + "\n");
            custmerLab.setId("bolder");
            GridPane.setColumnSpan(custmerLab,2);
            layout3.add(custmerLab,0, 7*i);

            layout3.add(new Label(labelList.get(labCount++)),0,7*i + 1);
            TextField firstName = new TextField();
            firstNameList.add(firstName);
            layout3.add(firstName, 1, 7*i + 1);
            GridPane.setColumnSpan(firstName, 3);

            layout3.add(new Label(labelList.get(labCount++)),0,7*i + 2);
            TextField lastName = new TextField();
            lastNameList.add(lastName);
            layout3.add(lastName, 1, 7*i + 2);
            GridPane.setColumnSpan(lastName, 3);

            layout3.add(new Label(labelList.get(labCount++)),0,7*i + 3);
            ComboBox<Integer> dateCombo = new ComboBox<>();
            dateCombo.setPromptText("Date");
            dateCombo.setMinWidth(75);
            dateCombo.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31);
            dateList.add(dateCombo);
            layout3.add(dateCombo, 1, 7*i + 3);

            ComboBox<String> month = new ComboBox<>();
            month.setPromptText("Month");
            month.setMinWidth(75);
            month.getItems().addAll("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Dec");
            monthList.add(month);
            layout3.add(month, 2, 7*i + 3);

            ComboBox<Integer> year = new ComboBox<>();
            year.setPromptText("Year");
            year.setMinWidth(75);
            for (int j = 2016; j > 1899; j--) {
                year.getItems().add(j);
            }
            yearList.add(year);
            layout3.add(year, 3, 7*i + 3);

            layout3.add(new Label(labelList.get(labCount++)),0,7*i + 4);
            TextField phoneNumber = new TextField();
            phoneNumberList.add(phoneNumber);
            layout3.add(phoneNumber, 1, 7*i + 4);
            GridPane.setColumnSpan(phoneNumber, 3);

            layout3.add(new Label(labelList.get(labCount) + "\n"),0,7*i + 5);
            TextField emailAddress = new TextField();
            emailList.add(emailAddress);
            layout3.add(emailAddress, 1, 7*i + 5);
            GridPane.setColumnSpan(emailAddress, 3);

            if (numberOfPassengers > 1 && i < numberOfPassengers - 1) {
                Separator separator = new Separator(Orientation.HORIZONTAL);
                layout3.add(separator,0,7*i +6);
                GridPane.setColumnSpan(separator,4);
            }

        }

        Label outboundLab = new Label("Outbound:");
        outboundLab.setId("bolder");
        GridPane.setColumnSpan(outboundLab,2);
        layout3.add(outboundLab,7,0);

        Label depLab = new Label("To:\nFrom:\nDate:\nTime:\nPrice:");
        depLab.setId("bold");
        layout3.add(depLab,7,1);
        GridPane.setRowSpan(depLab,4);

        if (twoWay.isSelected()) {
            Label returnLab = new Label("Return:");
            returnLab.setId("bolder");
            layout3.add(returnLab,7,5);
            Label retLab = new Label("To:\nFrom:\nDate:\nTime:\nPrice:");
            retLab.setId("bold");
            layout3.add(retLab,7,6);
            GridPane.setRowSpan(retLab,4);
        }

        SimpleDateFormat getDate = new SimpleDateFormat("dd. MMMMMMMMMMM yyyy");
        SimpleDateFormat getTime = new SimpleDateFormat("HH:mm");

        int j = 0;
        for (Flight flight : selectedFlights) {
            if (j < 2) {
                String tmp = "" + flight.getArrival() + "\n" + flight.getDeparture() + "\n" + getDate.format(flight.getDateAndTime()) + "\n" + getTime.format(flight.getDateAndTime()) + "\n" + flight.calcPrice();
                Label tmpLab = new Label(tmp);
                tmpLab.setId("bold");
                layout3.add(tmpLab, 8, j++ * 5 + 1);
                GridPane.setRowSpan(tmpLab, 4);
            }
        }


        SimpleDateFormat toDate = new SimpleDateFormat("ddMMMyyyy");

        Button checkOutBtn = new Button("Check out");
        checkOutBtn.setMinWidth(100);
        layout3.add(checkOutBtn,3,numberOfPassengers*7);

        Button backBtn = new Button("Back");
        backBtn.setMinWidth(100);
        backBtn.setOnAction(event1 -> {
            window.setScene(scene2);
            selectedFlights.clear();
        });
        layout3.add(backBtn,0,numberOfPassengers*7);

        Alert error = new Alert(Alert.AlertType.ERROR);
        checkOutBtn.setOnAction( event -> {
            try {
                for (int i = 0; i < numberOfPassengers; i++) {
                    String dateString = dateList.get(i).getValue() + monthList.get(i).getValue() + yearList.get(i).getValue();
                    System.out.println(dateString);
                    Date bday = new Date();
                    try {
                        bday = toDate.parse(dateString);
                    } catch (ParseException e) {
                        throw new NullPointerException();
                    }
                    System.out.println("Bua til cust");
                    if (firstNameList.get(i).getText() != null && lastNameList.get(i).getText() != null && phoneNumberList.get(i).getText().length() == 7 && emailList.get(i).getText() != null) {
                        customers.add(new Customer(firstNameList.get(i).getText(), lastNameList.get(i).getText(), bday, Integer.parseInt(phoneNumberList.get(i).getText()), emailList.get(i).getText()));
                    } else {
                        throw new NullPointerException();
                    }
                    System.out.println("Buin ad bua til cust");
                    scene4();
                }
            } catch (Exception e) {
                error.setHeaderText("Invalid information!");
                error.setContentText("Please fill out all the requested information.");
                error.showAndWait();
            }
        });

        ScrollPane scrollpane = new ScrollPane();

        scrollpane.setContent(layout3);
        scrollpane.setFitToWidth(true);
        scrollpane.setFitToHeight(true);

        BackgroundImage clouds = new BackgroundImage(
                new Image(flugGUI.class.getResourceAsStream("cloudsBI.jpg"),960,600,false,true),
                BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
        layout3.setBackground(new Background(clouds));

        layout3.getStylesheets().add(this.getClass().getResource("Skyblue.css").toExternalForm());


        scene3 = new Scene(scrollpane, 960, 600);

        window.setScene(scene3);
    }

    public void scene4() {
        Stage popup = new Stage();

        popup.initModality(Modality.APPLICATION_MODAL);
        GridPane grid = new GridPane();
        Label outbndLab = new Label("Outbound:");
        outbndLab.setId("bolder");
        grid.add(outbndLab,0,0);
        grid.setVgap(20);
        grid.setHgap(20);

        BackgroundImage takeoff = new BackgroundImage(
                new Image(flugGUI.class.getResourceAsStream("takeoff.jpg"),500,375,false,true),
                BackgroundRepeat.REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
        grid.setBackground(new Background(takeoff));

        Label depLab = new Label("To:\nFrom:\nDate:\nTime:\nPrice:");
        depLab.setId("bold");
        grid.add(depLab,0,1);
        GridPane.setRowSpan(depLab,4);

        if (twoWay.isSelected()) {
            Label returnLab = new Label("Return:");
            returnLab.setId("bolder");
            grid.add(returnLab,0,5);
            Label retLab = new Label("To:\nFrom:\nDate:\nTime:\nPrice:");
            retLab.setId("bold");
            grid.add(retLab,0,6);
            GridPane.setRowSpan(retLab,4);
        }

        SimpleDateFormat getDate = new SimpleDateFormat("dd. MMMMMMMMMMM yyyy");
        SimpleDateFormat getTime = new SimpleDateFormat("HH:mm");

        int j = 0;
        for (Flight flight : selectedFlights) {
            if (j < 2) {
                String tmp = "" + flight.getArrival() + "\n" + flight.getDeparture() + "\n" + getDate.format(flight.getDateAndTime()) + "\n" + getTime.format(flight.getDateAndTime()) + "\n" + flight.calcPrice();
                Label tmpLab = new Label(tmp);
                tmpLab.setId("bold");
                grid.add(tmpLab, 1, j++ * 5 + 1);
                GridPane.setRowSpan(tmpLab, 4);
            }
        }

        Scene pop = new Scene(grid,500,375);

        grid.setPadding(new Insets(30,50,50,50));

        Button confirmBtn = new Button("Confirm");
        confirmBtn.setOnAction( event -> {
            try {
                ArrayList<Ticket> tickets = new ArrayList<>();
                DBController dbc = new DBController();
                BookingController bc = new BookingController(dbc);
                for (int i = 0; i < selectedFlights.size(); i++) {
                    for (int k = 0; k < customers.size(); k++) {
                        tickets.add(bc.makeTicket(selectedFlights.get(i), customers.get(k)));
                    }
                }
                Ticket[] tmp = new Ticket[tickets.size()];
                System.arraycopy(tickets.toArray(), 0, tmp, 0, tickets.size());
                bc.makeBookings(tmp);
                System.out.println("Flott!");
                popup.close();
                searchResults.clear();
                customers.clear();
                selectedFlights.clear();
                scene1();
            } catch (Exception e) {
                System.out.println("Vesen");
            }
        });
        grid.add(confirmBtn,3,9);

        grid.getStylesheets().add(this.getClass().getResource("Skyblue.css").toExternalForm());

        popup.setScene(pop);
        popup.showAndWait();

    }




}
