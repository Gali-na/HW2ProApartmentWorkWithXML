import java.io.File;

public class Main {
    public static void main(String[] args) {
        DataBaseXML.changeCostApartments(new File("file.xml"), "Gagarina 5", 9);
        System.out.println(ServiseForCatalogApartment.showListApartmens(new File("file.xml")));
        DataBaseXML.delApartment(new File("file.xml"), "Gagarina 5");
        System.out.println(ServiseForCatalogApartment.showListApartmens(new File("file.xml")));
        RealEstateAgency r = DataBaseXML.filterByPrice(new File("file.xml"), 7, 35000);
        RealEstateAgency g = DataBaseXML.filterByCountRoom(new File("file.xml"), 3);
        System.out.println(g);
    }
}
