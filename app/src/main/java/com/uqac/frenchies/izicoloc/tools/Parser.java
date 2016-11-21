package com.uqac.frenchies.izicoloc.tools;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by Thomas on 2016-11-11.
 */

public class Parser {

    private static String TAG = "Parser";

    public static void modifyNode(String pathToFile, String node_String, String oldInformation, String newInformation){
        try {
            File file = new File(pathToFile);
            if (!file.exists()) {
                generate(pathToFile);
                Log.i(TAG, "Creating new XML file : " + pathToFile);
            }
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            Node node = getNode(document, node_String, oldInformation);
            node.setTextContent(newInformation);

            //Write the content into XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(file);

            transformer.transform(source, result);
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
    }

    public static void addNode(String pathToFile, String parentNode_String, String node_String, String information){
        try{
            File file = new File(pathToFile);
            if (!file.exists()) {
                generate(pathToFile);
                Log.i(TAG, "Creating new XML file : " + pathToFile);
            }
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            Node parentNode = document.getElementsByTagName(parentNode_String).item(0);

            Element newElement = document.createElement(node_String);
            newElement.appendChild(document.createTextNode(information));
            parentNode.appendChild(newElement);

            //Write the content into XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(file);

            transformer.transform(source, result);
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
    }

    private static void generate(String path){
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            //Root elements
            Document document = documentBuilder.newDocument();
            Element rootElement = document.createElement("root");
            document.appendChild(rootElement);

            //Example how to add an element
            //Element exampleElement = document.createElement("example");
            //exampleElement.appendChild(document.createTextNode("content of the new node"));
            //rootElement.appendChild(exampleElement);

            //Write the content into XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(path));

            transformer.transform(source, result);
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * Method called to get a specific node
     * @param document Document parsed containing the needed node
     * @param name The node name
     * @param value The node value
     * @return The needed node
     */
    private static Node getNode(Document document, String name, String value){
        NodeList list = document.getElementsByTagName(name);
        for (int i = 0; i < list.getLength(); i++) {
            Node n = list.item(i);
            if (n.hasChildNodes()) {

                NodeList nl = n.getChildNodes();
                for (int j = 0 ; j < nl.getLength() ; j ++){
                    Node n2 = nl.item(j);
                    String currentValue = n2.getTextContent();
                    if (currentValue != null) {
                        if (currentValue.equals(value)) {
                            return n;
                        }
                    }
                }
            }
        }
        return null;
    }

    public static String getInformation(String pathToFile, String node_String){
        try {
            String response = null;
            File file = new File(pathToFile);
            if (!file.exists()) {
                generate(pathToFile);
                Log.i(TAG, "Creating new XML file : " + pathToFile);
                return null;
            }
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);

            NodeList nodeList = document.getElementsByTagName(node_String);
            Node node = document.getElementsByTagName(node_String).item(0);
            response = node.getFirstChild().getTextContent();
            return response;
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    public static ArrayList<String> getAllInformation(String pathToFile, String node_String){
        try {
            ArrayList<String> response = new ArrayList<>();
            File file = new File(pathToFile);
            if (!file.exists()) {
                generate(pathToFile);
                Log.i(TAG, "Creating new XML file : " + pathToFile);
                return null;
            }
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            NodeList nodeList = document.getElementsByTagName(node_String);

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element e = (Element) nodeList.item(i);
                NodeList children = e.getChildNodes();
                String detail = "";
                for (int j = 0; j < children.getLength(); j++) {
                    detail += children.item(j).getNodeName()+":"+children.item(j).getTextContent()+";";
                }
                response.add(detail);
            }
            return response;
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
        return null;
    }
}
