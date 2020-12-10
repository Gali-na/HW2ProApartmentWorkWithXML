import java.io.BufferedReader;
import java.util.ArrayList;

public class RealEstateAgency {
    private ArrayList<Apartment> apartments;

    public RealEstateAgency(ArrayList<Apartment> apartments) {
        this.apartments = apartments;
    }

    public RealEstateAgency() {
this.apartments = new ArrayList<>();
    }

    public ArrayList<Apartment> getApartments() {
        return apartments;
    }

    public void setApartments(ArrayList<Apartment> apartments) {
        this.apartments = apartments;
    }

    public void addApartment(Apartment apartment) {
        this.apartments.add(apartment);
    }

    @Override
    public String toString() {
       StringBuilder builder = new StringBuilder();
        for (Apartment apartment:this.apartments) {
            builder.append(apartment +"\n");

        }
        return  builder.toString();
    }
}
