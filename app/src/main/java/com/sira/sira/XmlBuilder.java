package com.sira.sira;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * This class is used to load the implant default formular for the SIRIS Database.
 * All the methods in this class are used to load, edit or change the formular.
 */
public class XmlBuilder {

    private static XmlBuilder instance = new XmlBuilder();
    private static final String filePath = "xml/siris.xml";
    private static final File xmlFile = new File(filePath);
    private DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    private DocumentBuilder dBuilder;
    private Document doc;

    private XmlBuilder(){
    }

    public static XmlBuilder getInstance(){
        return instance;
    }

    /**
     * Loads the template XML file into the doc Variable to access it from outside.
     */
    public void loadXmlTemplate(){
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            System.out.println("XML file loaded successfully");

        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Dynamic Method to edit the siris.xml file.
     *
     * @param value - the value to be edited
     * @param tag - the tag from the xml file
     */
    public void fillXmlValue(String value, String tag){
        NodeList patients = doc.getElementsByTagName("patients");
        Element patient = null;
        for(int i = 0; i <= patients.getLength(); i++){
            patient = (Element) patients.item(i);
            Node node = patient.getAttributeNode(tag);
            node.setNodeValue(value);
        }
    }
}
