import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DataBaseXML {

    public static RealEstateAgency loadCatalogFromXMLFile(File file) {
        RealEstateAgency catalog = new RealEstateAgency();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            Element root = document.getDocumentElement();
            NodeList apartment = root.getChildNodes();
            for (int i = 0; i < apartment.getLength(); i++) {
                Node node = apartment.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    Apartment apartmentTemp = getApartmentFromNode(element);
                    if (apartmentTemp != null) {
                        catalog.addApartment(apartmentTemp);
                    }
                }
            }
        } catch (Exception e) {
            return null;
        }
        return catalog;
    }

    private static Apartment getApartmentFromNode(Element apartmentElement) {
        if (!apartmentElement.getTagName().equals("Apartment")) {
            return null;
        }
        String numberOfRooms = apartmentElement.getElementsByTagName("numberOfRooms").item(0).getTextContent();
        String address = apartmentElement.getElementsByTagName("address").item(0).getTextContent();
        String floor = apartmentElement.getElementsByTagName("floor").item(0).getTextContent();
        String cost = apartmentElement.getElementsByTagName("cost").item(0).getTextContent();
        Apartment apartment = new Apartment(Integer.valueOf(numberOfRooms), address, Integer.valueOf(floor), Integer.valueOf(cost));
        return apartment;
    }

    public static void changeCostApartments(File file, String address, Integer costNew) {
        RealEstateAgency catalog = new RealEstateAgency();
        Document document = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(file);
            Element root = document.getDocumentElement();
            NodeList apartment = root.getChildNodes();
            for (int i = 0; i < apartment.getLength(); i++) {
                Node node = apartment.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String oldAddress = element.getElementsByTagName("address").item(0).getTextContent();
                    if (oldAddress.equals(address)) {
                        Node staff = element.getElementsByTagName("cost").item(0);
                        staff.setTextContent(costNew.toString());
                        String a = element.getElementsByTagName("cost").item(0).getTextContent();
                        element.getElementsByTagName("cost").item(0).getTextContent();
                    }
                }
            }
        } catch (Exception e) {
        }
        updateXML(document);
    }

    private static void updateXML(Document document) {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException transformerConfigurationException) {
            transformerConfigurationException.printStackTrace();
        }
        DOMSource source = new DOMSource(document);

        StreamResult consoleResult = new StreamResult(new File("file.xml"));
        try {
            transformer.transform(source, consoleResult);
        } catch (TransformerException transformerException) {
            transformerException.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void delApartment(File file, String address) {
        RealEstateAgency catalog = new RealEstateAgency();
        Document document = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(file);
            Element root = document.getDocumentElement();
            NodeList apartments = root.getChildNodes();
            for (int i = 0; i < apartments.getLength(); i++) {
                Node node = apartments.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    if (!element.getTagName().equals("Apartment")) {
                        return;
                    }
                    String oldAddress = element.getElementsByTagName("address").item(0).getTextContent();
                    if (oldAddress.equals(address)) {
                        Node old = element.getElementsByTagName("address").item(0);
                        root.removeChild(element);
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException  | IOException e) {
            e.printStackTrace();
        }
        updateXML(document);
    }

    public static RealEstateAgency filterByPrice(File file, Integer minPrice, Integer maxPrice) {
        Apartment apartment = new Apartment();
        RealEstateAgency realEstateAgency = new RealEstateAgency();
        XMLInputFactory factory = XMLInputFactory.newInstance();
        try {
            XMLStreamReader streamReader = factory.createXMLStreamReader(new FileInputStream("file.xml"));
            while ((streamReader.hasNext())) {
                int event = streamReader.next();
                if (event == XMLStreamConstants.START_ELEMENT) {
                    if (streamReader.getLocalName().equals("numberOfRooms")) {
                        apartment.setNumberOfRooms(Integer.valueOf(streamReader.getElementText()));
                    }
                    if (streamReader.getLocalName().equals("address")) {
                        apartment.setAddress(streamReader.getElementText());
                    }
                    if (streamReader.getLocalName().equals("floor")) {
                        apartment.setFloor(Integer.valueOf(streamReader.getElementText()));
                    }
                    if (streamReader.getLocalName().equals("cost")) {
                        Integer currentCost = Integer.valueOf(streamReader.getElementText());
                        apartment.setCost(currentCost);
                        if (currentCost >= minPrice && currentCost <= maxPrice) {
                            realEstateAgency.addApartment(apartment);
                            apartment = new Apartment();
                        }
                    }
                }
            }
        } catch (FileNotFoundException | XMLStreamException ex) {
            ex.printStackTrace();
        }
        return realEstateAgency;
    }

    public static RealEstateAgency filterByCountRoom(File file, Integer rooms) {
        boolean filter = false;
        Apartment apartment = new Apartment();
        RealEstateAgency realEstateAgency = new RealEstateAgency();
        XMLInputFactory factory = XMLInputFactory.newInstance();
        try {
            XMLStreamReader streamReader = factory.createXMLStreamReader(new FileInputStream("file.xml"));
            while ((streamReader.hasNext())) {
                int event = streamReader.next();
                if (event == XMLStreamConstants.START_ELEMENT) {
                    if (streamReader.getLocalName().equals("numberOfRooms")) {
                        Integer currentCountRooms = Integer.valueOf(streamReader.getElementText());
                        if (currentCountRooms >= rooms) {
                            apartment.setNumberOfRooms(currentCountRooms);
                            filter = true;
                        }
                    }
                    if (streamReader.getLocalName().equals("address")) {
                        apartment.setAddress(streamReader.getElementText());
                    }
                    if (streamReader.getLocalName().equals("floor")) {
                        apartment.setFloor(Integer.valueOf(streamReader.getElementText()));
                    }
                    if (streamReader.getLocalName().equals("cost")) {
                        apartment.setCost(Integer.valueOf(streamReader.getElementText()));
                        if (filter == true) {
                            realEstateAgency.addApartment(apartment);
                            apartment = new Apartment();
                            filter = false;
                        }
                    }
                }
            }
        } catch (FileNotFoundException | XMLStreamException ex) {
            ex.printStackTrace();
        }
        return realEstateAgency;
    }
}
