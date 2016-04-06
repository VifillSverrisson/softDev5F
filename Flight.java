import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by vifillsverrissonMacBookPro on 03/04/16.
 */
public class Flight {

    private int availableSeats;
    private int seats;
    private String to;
    private String from;
    private int startPrice;
    private String flightNmbr;
    private Date departureDate;

    public static void main(String[] args) {
        DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        Date departDate = null;
        try {
            departDate = df.parse("Thu Apr 9 08:00:00 GMT 2016");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(departDate);
        Flight United93 = new Flight(5, 20, "Akureyri","Reykjavik", 13000, "f3fh398498f", departDate);
        System.out.println(United93.calcPrice());
    }

    public Flight(int nmbrOfAvalebleSeats, int nmbrOfSeats, String to, String from, int startPrice, String flightNmbr, Date departueDate) {
        this.availableSeats = nmbrOfAvalebleSeats;
        this.seats = nmbrOfSeats;
        this.to = to;
        this.from = from;
        this.startPrice = startPrice;
        this.flightNmbr = flightNmbr;
        this.departureDate = departueDate;
    }

    public int calcPrice() {
        Date today = new Date();
        int until = (int) (departureDate.getTime() - today.getTime());
        double timeFactor = 1/(Math.log10((until/86400000)+4));
        double seatFactor = Math.pow((0.85-(1.0*availableSeats/seats)),3);
        return (int) (startPrice*(timeFactor+seatFactor));
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(int startPrice) {
        this.startPrice = startPrice;
    }

    public String getFlightNmbr() {
        return flightNmbr;
    }

    public void setFlightNmbr(String flightNmbr) {
        this.flightNmbr = flightNmbr;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }
}
