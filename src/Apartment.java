import java.util.Objects;

public class Apartment {
    private Integer numberOfRooms;
    private String address;
    private Integer floor;
    private Integer cost;

    public Apartment() {

    }

    public Apartment(int numberOfRooms, String address, int floor, int cost) {
        this.numberOfRooms = numberOfRooms;
        this.address = address;
        this.floor = floor;
        this.cost = cost;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Apartment)) return false;
        Apartment apartment = (Apartment) o;
        return numberOfRooms == apartment.numberOfRooms &&
                floor == apartment.floor &&
                cost == apartment.cost &&
                address.equals(apartment.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfRooms, address, floor, cost);
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "numberOfRooms=" + numberOfRooms +
                ", address='" + address + '\'' +
                ", floor=" + floor +
                ", cost=" + cost +
                '}';
    }
}
