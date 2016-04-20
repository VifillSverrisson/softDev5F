package Main;

/**
 * Created by vifillsverrissonMacBookPro on 16/04/16.
 */
public interface DBControllerInterface {

	/*
	To use the Main.DBController the jar file : postgresql-9.4.1208.jar
	Needs to be a part of the projects dependencies.
	 */

    /*
    Adds a flight to the database.
    Returns true if it was successfull.
     */
    public boolean addFlight(String Company, String departure, String arrival, String dateAndTime, int seatsAvailable, int totalSeats, int startPrice);

    /*
    Removes a flight from the database with this ID.
    Returns true if it was successfull.
     */
    public boolean removeFlightByID(int flightID);

    /*
    Gets the flights from the database that match the given input.
    Matches partial of the date string.
    Returns an array of flights => Main.Flight[].
     */
    public Flight[] getFlights(String departure, String arrival, String date, int noOfSeatsNeeded);

    /*
    Gets the flight that matches this flightId.
    Returns an array that contains the flight.
     */
    public Flight[] getFlightById(int flightId);

    /*
    Adds a customer to the database.
    Returns true if it was successfull.
     */
    public boolean addCustomer(Customer cus) ;

    /*
    Gets the customer from the database with this email address.
    Returns a Main.Customer object.
     */
    public Customer getCustomer(String emailAddress);

    /*
    Removes the customer from the database with this email address.
    Returns true if it was successfull.
     */
    public boolean removeCustomer(String email);

    /*
    Changes the seat availability of the flight with this flightID by
    decrementing the available seats by noOfSeats.
    Returns true if it was successfull.
     */
    public boolean changeSeatAvailability(int flightID, int noOfSeats);

    /*
    Adds a ticket to the database.
    Main.Ticket is the connection between flights and customers.
    Returns true if it was successfull.
     */
    public boolean addTicket(Ticket ticket);

    /*
    Gets the corresponding ticket from the database.
    Returns a Main.Ticket object.
     */
    public Ticket getTicket(Ticket ticket);

}