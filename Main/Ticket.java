package Main;

/**
 * Created by vifillsverrissonMacBookPro on 03/04/16.
 */
public class Ticket {

    private Customer customer;
    private Flight flight;

    public Ticket(Customer customer, Flight flight) {
        this.customer = customer;
        this.flight = flight;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}
