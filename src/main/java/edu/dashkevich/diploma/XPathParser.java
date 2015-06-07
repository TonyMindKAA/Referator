package edu.dashkevich.diploma;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XPathParser {

    private static final String HEAD_TAG_NAME = "head";
    public static final String TEXT_NODE_NAME = "#text";
    private List<String> sentences = new ArrayList<String>();
    private Set<String> nouns = new HashSet<String>();
    private long allWords;
    private StringBuffer text;

    public static void main(String[] args) throws Exception {
        FileInputStream file = new FileInputStream(new File("C:\\Users\\Mr.Green\\Desktop\\download\\XML\\A8W.xml"));
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document xmlDocument = builder.parse(file);
        XPath xPath = XPathFactory.newInstance().newXPath();
        NodeList nodeList = (NodeList) xPath.compile("/bncDoc/text/div1").evaluate(xmlDocument, XPathConstants.NODESET);
        XPathParser xPathParser = new XPathParser();
        xPathParser.runByDiv1Tags(nodeList);
    }

    public void parseText(String path) throws Exception{
        FileInputStream file = new FileInputStream(new File(path));
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document xmlDocument = builder.parse(file);
        XPath xPath = XPathFactory.newInstance().newXPath();
        NodeList nodeList = (NodeList) xPath.compile("/bncDoc/text/div1").evaluate(xmlDocument, XPathConstants.NODESET);
        XPathParser xPathParser = new XPathParser();
        xPathParser.runByDiv1Tags(nodeList);
    }


    private void runByDiv1Tags(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            runByDiv1ChildTags(nodeList.item(i).getChildNodes());
        }
        System.out.println("Nouns counter:" + nouns.size());
        System.out.println("Sentences counter:" + sentences.size());
        System.out.println("All word in text:" + allWords);
        Object[] sentencesArr = nouns.toArray();
        for (Object word : sentencesArr) {
            System.out.println(word);

        }
    }

    private void runByDiv1ChildTags(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).getChildNodes().getLength() != 0) {
                if (!isHeadTag(nodeList.item(i))) {
                    runByPTag(nodeList.item(i));
                }
            }
        }
    }

    private boolean isHeadTag(Node node) {
        return node.getNodeName().equals(HEAD_TAG_NAME);
    }

    private void runByPTag(Node p) {
        NodeList pChild = p.getChildNodes();
        for (int j = 0; j < pChild.getLength(); j++) {
            if (isSTag(pChild.item(j))) {
                NodeList wTag = pChild.item(j).getChildNodes();
                parseSentence(wTag);
            }
        }
    }

    private boolean isSTag(Node node) {
        return node.getNodeName().equals("s");
    }

    private void parseSentence(NodeList wTags) {
        StringBuffer sentence = new StringBuffer();
        for (int k = 0; k < wTags.getLength(); k++) {
            allWords++;
            if (!wTags.item(k).getNodeName().equals(TEXT_NODE_NAME)) {
                sentence.append(wTags.item(k).getTextContent());
                setNoun(wTags.item(k));
            }
        }
        sentences.add(sentence.toString());
    }

    private void setNoun(Node wTag) {
        String wTagAttribute = wTag.getAttributes().getNamedItem("type").getNodeValue();
        if (wTagAttribute.equals("NN1") || wTagAttribute.equals("NN2") || wTagAttribute.equals("NP1")) {
            nouns.add(wTag.getTextContent().toLowerCase());
        }
    }
}
