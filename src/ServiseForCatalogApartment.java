import java.io.File;

public class ServiseForCatalogApartment {
    public static RealEstateAgency showListApartmens (File name) {
      RealEstateAgency realEstateAgency = DataBaseXML.loadCatalogFromXMLFile(new File("file.xml"));
    return realEstateAgency;
    }
}
