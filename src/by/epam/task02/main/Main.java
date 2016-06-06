package by.epam.task02.main;

import by.epam.task02.entity.Dish;
import by.epam.task02.entity.Section;
import by.epam.task02.entity.Menu;

import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


public class Main {

    private static final String xmlFileURI = "xml/menu.xml";

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        Menu menu = new Menu();

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(xmlFileURI);

        Element menuElement = document.getDocumentElement();

        NodeList sectionElements = menuElement.getElementsByTagName("section");
        
        for (int i = 0; i < sectionElements.getLength(); i++) {
            Element sectionElement = (Element) sectionElements.item(i);
            Section section = new Section();
            section.setName(sectionElement.getElementsByTagName("section-name").item(0).getTextContent());
            menu.addSection(section);

            NodeList dishElements = sectionElement.getElementsByTagName("dish");
            
            for (int j = 0; j < dishElements.getLength(); j++) {
                Element dishElement = (Element) dishElements.item(j);
                Dish dish = new Dish();
                
                dish.setPhoto(dishElement.getElementsByTagName("dish-photo").item(0).getTextContent());
                dish.setName(dishElement.getElementsByTagName("dish-name").item(0).getTextContent());
                if (dishElement.getElementsByTagName("dish-description").item(0) != null)
                	dish.setDescription(dishElement.getElementsByTagName("dish-description").item(0).getTextContent());
                dish.setPortion(dishElement.getElementsByTagName("dish-portion").item(0).getTextContent());
                if (dishElement.getElementsByTagName("dish-price").item(0) != null)
                	dish.setPrice(Integer.parseInt(dishElement.getElementsByTagName("dish-price").item(0).getTextContent()));
                
                section.addDish(dish);
            }
        }

        System.out.println("МЕНЮ:\n");
        for (Section section : menu.getSections()) {
            System.out.println(section.getName());
            for (Dish dish : section.getDishes()) {
                System.out.println(
                		"\tНазвание: " + dish.getName() +
                		"\n\tОписание: " + (dish.getDescription() != null ? dish.getDescription() : "отсутствует") +
                		"\n\tПорция: " + dish.getPortion() + 
                		"\n\tЦена: " + (dish.getPrice() != 0 ? dish.getPrice() : "не указана") +
                		"\n\tФото: " + dish.getPhoto() +
                		"\n"); 		
            }
        }
    }
}