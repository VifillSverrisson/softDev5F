package Main;

public class BookingController implements BookingControllerInterface{

    DBController dbc;

    public BookingController(DBController dbc) {
        this.dbc = dbc;
    }

    @Override
    public void makeBookings(Ticket[] tickets) {
        for (int i=0; i<tickets.length;i++) {
            dbc.changeSeatAvailability(tickets[i].getFlight().getFlightId(), 1);
            Customer cus = tickets[i].getCustomer();
            addCustomer(cus);
            dbc.addTicket(tickets[i]);
        }
    }

    @Override
    public Ticket makeTicket(Flight flight, Customer customer) {
        return new Ticket(customer, flight);
    }

    @Override
    public void addCustomer(Customer cus) {
        if(dbc.getCustomer(cus.getEmailAddress())==null) {
            dbc.addCustomer(cus);
        }
    }
}