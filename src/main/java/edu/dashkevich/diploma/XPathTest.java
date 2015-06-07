package edu.dashkevich.diploma;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XPathTest {
    public static void main(String[] args) throws Exception {
        String xml = "<resp><status>good</status><msg>hi</msg></resp>";
        xml = readFile("C:\\Users\\Mr.Green\\Desktop\\download\\XML\\A8W.xml", StandardCharsets.UTF_8);

        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();

        InputSource source = new InputSource(new StringReader(xml));
        String status = xpath.evaluate("/bncDoc/text/div1/head/s/w[@type='JJ']", source);

        System.out.println("satus=" + status);


        FileInputStream file = new FileInputStream(new File("C:\\Users\\Mr.Green\\Desktop\\download\\XML\\A8W.xml"));

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = builderFactory.newDocumentBuilder();

        Document xmlDocument = builder.parse(file);
        XPath xPath = XPathFactory.newInstance().newXPath();

        NodeList nodeList = (NodeList) xPath.compile("/bncDoc/text/div1").evaluate(xmlDocument, XPathConstants.NODESET);
        runByDiv1Tags(nodeList);
    }

    private static void runByDiv1Tags(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
             System.out.println("\n\n"+nodeList.item(i).getNodeName()+" level 1");
            runByDiv1ChildTags(nodeList.item(i).getChildNodes());
        }
    }

    static void runByDiv1ChildTags(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).getChildNodes().getLength() != 0) {
                //System.out.println(nodeList.item(i).getNodeName()+" level 2 "+nodeList.item(i).getNodeValue());
                if (!nodeList.item(i).getNodeName().equals("head")) {
                    Node p = nodeList.item(i);
                    // System.out.println(p.getChildNodes().getLength());
                    NodeList pChild = p.getChildNodes();
                    for (int j = 0; j < pChild.getLength(); j++) {
                        if (pChild.item(j).getNodeName().equals("s")) {
                            NodeList ww = pChild.item(j).getChildNodes();
                            // System.out.println(ww.getLength());
                            for (int k = 0; k < ww.getLength(); k++) {
                                if (!ww.item(k).getNodeName().equals("#text")) {
                                    System.out.println(ww.item(k).getNodeName()+" "+ ww.item(k).getTextContent()+ " "+ ww.item(k).getAttributes().getNamedItem("type").getNodeValue());

                                }
                            }
                            System.out.println("sentences finished!\n\n");
                        }
                    }

                }
            }
        }
    }

    private static void runByChildPTagS(NodeList childNodes) {
        for (int i = 0; i < childNodes.getLength(); i++) {
            System.out.println(childNodes.item(i).getNodeName() + " " + childNodes.item(i).getAttributes());
        }
    }

    static void runByPTag(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            System.out.println(nodeList.item(i));
        }
    }

    static void readFile(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).getChildNodes().getLength() != 0) {
                readFile(nodeList.item(i).getChildNodes());
            } else {
                System.out.print(nodeList.item(i).getNodeValue());
            }
        }
    }


    static String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
