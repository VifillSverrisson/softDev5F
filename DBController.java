/**
 * Created by vifillsverrissonMacBookPro on 07/04/16.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class DBController {


    public boolean update(String sql) {
        Connection c = null;
        Statement stmt = null;

        try {

            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://pellefant-01.db.elephantsql.com:5432/jrfelwfu",
                    "jrfelwfu", "BhAw_gJWfAV49aOHk0eB_iHiccl6gE35");
            c.setAutoCommit(false);

            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();

            System.out.println("Records created successfully");

            return true;

        } catch (Exception e) {

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);

            return false;
        }

    }

    public boolean addFlight(
            String airline, String departure, String arrival,
            String date, int numberOfSeats, int startingPrice) {
        //inserts flight into database with given parameters

        String sql = "INSERT INTO FLIGHTS (Company , Departure , Arrival, DateAndTime, SeatCount, StartPrice) "
                + "VALUES ('" + airline + "', '" + departure + "', '" + arrival + "', '" + date + "', "
                + numberOfSeats + ", " + startingPrice + ");";

        boolean out = update(sql);
        return out;
    }

    public boolean removeFlightByID(int flightID) {

        String sql = "DELETE FROM FLIGHTS "
                + "WHERE id = "+flightID+";";

        boolean out = update(sql);
        return out;

    }

    public boolean removeFlightsByCompany(String company) {

        String sql = "DELETE FROM FLIGHTS "
                + "WHERE Company = "+company+";";

        boolean out = update(sql);
        return out;


    }

    public Flight[] getFlights(String departure, String arrival, String date) {
        //searches database for flights with given parameters

        Connection c = null;
        Statement stmt = null;

        try {

            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://pellefant-01.db.elephantsql.com:5432/jrfelwfu",
                    "jrfelwfu", "BhAw_gJWfAV49aOHk0eB_iHiccl6gE35");
            c.setAutoCommit(false);

            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "SELECT * FROM FLIGHTS WHERE Departure = "
                    + departure + ", Arrival = " + arrival + ", DateAndTime = " + date + ";";

            ResultSet rs = stmt.executeQuery(sql);

            Flight[] flights = new Flight[10];
            int counter = 0;

            while(rs.next()){
                //Retrieve by column name
                int id  = rs.getInt("id");
                String depart = rs.getString("Departure");
                String arrive = rs.getString("Arrival");
                // FIXME: 07/04/16 Parse to Date
                String time = rs.getString("DateAndTime");
                String airline = rs.getString("Company");
                int availableSeats = rs.getInt("SeatCount");
                // FIXME: 07/04/16 Parse to int?
                int totalSeats = rs.getString("TotalSeats");
                int startingPrice = rs.getInt("StartPrice");

                Flight flight = new Flight(availableSeats, totalSeats, arrive, depart, startingPrice, id, time);
                flights[counter] = flight;
                counter++;
                //Display values
                System.out.print("ID: " + id);
                System.out.print(", Departure: " + departure);
                System.out.print(", Arrival: " + arrival);
                System.out.print(", Time: " + time);
                System.out.println(", Airline " + airline);

                if (counter==flights.length) {
                    Flight[] temp = new Flight[counter*2];
                    for (int i=0;i<counter;i++) temp[i]=flights[i];
                    flights = temp;
                }
            }

            Flight[] out = new Flight[counter];
            for (int i=0;i<counter;i++) out[i]=flights[i];

            rs.close();

            stmt.close();
            c.commit();
            c.close();

            return out;

        } catch (Exception e) {

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);

        }
    }

    private boolean addCustomer(Customer cus) {
        //adds customer to database with given parameters

        String sql = "INSERT INTO CUSTOMERS (firstName, lastName, dateOfBirth, phoneNo, email) "
                + "VALUES ('" + cus.getFirstName() + "', '" + cus.getLastName() + "', '" + cus.getDateOfBirth().toString()
                + "', '" + cus.getPhoneNumber() + "', " + cus.getEmailAddress() + ");";

        boolean out = update(sql);
        return out;
    }

    private boolean removeCustomerByID(int id) {
        //adds customer to database with given parameters

        String sql = "DELETE FROM CUSTOMERS "
                + "WHERE id = "+id+";";

        boolean out = update(sql);
        return out;
    }

    /*
	public bookFlights(Ticket[] tickets){
		for (i = 0; i<tickets.length(); i++) {
			addCustomer(tickets[i].customer);
			//also adds a booking
			changeSeatAvailability(tickets[i].flight, (-1)*tickets[i].numberOfSeats);
		}
	}
	*/

    public boolean changeSeatAvailability(int flightID, int noOfSeats) {
        //adds value of 'increment' parameter to matching flight's seatCount attribute in the database
        //fails if seatCount becomes negative
        int seatAvailability = getSeatAvailability(flightID);
        if (noOfSeats>seatAvailability) {return false;}
        else {

            String sql = "UPDATE flights SET SeatCount = SeatCount - " + noOfSeats + " WHERE id ="+ flightID + ";";

            boolean out = update(sql);
            return out;
        }
    }

    // FIXME: 07/04/16 Vantar return statement
    public int getSeatAvailability(int flightID) {

        Connection c = null;
        Statement stmt = null;

        try {

            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://pellefant-01.db.elephantsql.com:5432/jrfelwfu",
                    "jrfelwfu", "BhAw_gJWfAV49aOHk0eB_iHiccl6gE35");
            c.setAutoCommit(false);

            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "SELECT SeatCount FROM FLIGHTS WHERE id = "+ flightID +";";

            ResultSet rs = stmt.executeQuery(sql);

            int numOfSeats = rs.getInt("SeatCount");

            rs.close();

            stmt.close();
            c.commit();
            c.close();

            return numOfSeats;

        } catch (Exception e) {

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);

        }

        System.out.println("Record deleted successfully");
    }


    //not yet fully implemented
    public boolean addTicket(Ticket ticket) {
        String sql = "INSERT INTO TICKETS (flightID, customerID) "
                + "VALUES ('" + ticket.getFlight().getID() + "', '" + getCustomerID(ticket.getCustomer()) + ");";

        boolean out = update(sql);
        return out;
    }


    private boolean removeTicket(Ticket ticket) {
        //adds customer to database with given parameters

        String sql = "DELETE FROM TICKETS "
                + "WHERE FlightId = " + ticket.getFlight().getFlightNumber()
                + "AND CustomerID = " + getCustomerID(ticket.getCustomer()) + ";";

        boolean out = update(sql);
        return out;
    }

    public Ticket[] getTickets(Ticket ticket) {
        //searches database for flights with given parameters

        Connection c = null;
        Statement stmt = null;

        try {

            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://pellefant-01.db.elephantsql.com:5432/jrfelwfu",
                    "jrfelwfu", "BhAw_gJWfAV49aOHk0eB_iHiccl6gE35");
            c.setAutoCommit(false);

            System.out.println("Opened database successfully");

            stmt = c.createStatement();

            String sql = "SELECT * FROM TICKETS WHERE Departure = "departure", Arrival = "arrival", DateAndTime = "date";";

            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                //Retrieve by column name
                int id  = rs.getInt("id");
                String departure = rs.getString("Departure");
                String arrival = rs.getString("Arrival");
                String time = rs.getString("DateAndTime");
                String airline = rs.getString("Company");

                //Display values
                System.out.print("ID: " + id);
                System.out.print(", Departure: " + departure);
                System.out.print(", Arrival: " + arrival);
                System.out.print(", Time: " + time);
                System.out.println(", Airline " + airline);
            }
            rs.close();


            stmt.close();
            c.commit();
            c.close();

        } catch (Exception e) {

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);

        }

    }
}
}
