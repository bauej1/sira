package com.sira.sira;

import android.os.Environment;
import android.util.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * This class is used to load the implant default formular for the SIRIS Database.
 * All the methods in this class are used to load, edit or change the formular.
 */
public class XmlBuilder {

    private static XmlBuilder instance;
    private static final File xmlFile = new File(Environment.getExternalStorageDirectory() + "/Documents/siris.xml");
    private DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    private DocumentBuilder dBuilder;
    private Document doc;
    private NodeList patient;

    public static XmlBuilder getInstance(){
        if(instance == null){
            instance = new XmlBuilder();
        }
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
            patient = doc.getElementsByTagName("*");

        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

    public void loadAllDataIntoXml(Patient p) throws IllegalAccessException {
        Field[] fields = p.getClass().getDeclaredFields();
        String tag;
        Object value;

        for (Field f : fields) {
            f.setAccessible(true);
            tag = f.getName();
            value = f.get(p);

            if(tag.equals("hprimImplantData")){
                loadAllImplantDataIntoXml(p.getHPrimaryImplantData());
            } else {
                try {
                    fillXmlValue(tag, value, false);
                } catch (TransformerException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void loadAllImplantDataIntoXml(HPrimaryImplantData implantData) throws IllegalAccessException {
        Field[] fields = implantData.getClass().getDeclaredFields();
        String tag;
        Object value;

        for(Field f : fields){
            f.setAccessible(true);
            tag = f.getName();
            value = f.get(implantData);

            try {
                fillXmlValue(tag, value, true);
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Dynamic Method to edit the siris.xml file.
     *
     * @param value - the value to be edited
     * @param tag - the tag from the xml file
     */
    public void fillXmlValue(String tag, Object value, boolean implantData) throws TransformerException {

        for (int i = 1; i < patient.getLength(); i++) {
            Element element = (Element) patient.item(i);

            Log.d("element", element.getTagName() + "break " + element.getNodeName() + "break " + element.getTextContent() + "break " + element.getFirstChild());
            Log.d("--------------------------------", "\"--------------------------------");

            if(implantData){
                if(element.getElementsByTagName("questionname").item(i).getTextContent().equals(tag)){
                    element.getElementsByTagName("value").item(i).setTextContent(value.toString());
                }
            } else {
                if(element.getTagName().equals(tag)){
                    element.setTextContent(value.toString());
                }
            }
        }
        rewriteToXml();
    }

    /**
     * Rewrites the changed xml document to the external storage.
     *
     * @throws TransformerException
     */
    public void rewriteToXml() throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(Environment.getExternalStorageDirectory() + "/Documents/siris.xml"));
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result);
    }
}
