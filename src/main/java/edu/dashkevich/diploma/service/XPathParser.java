package edu.dashkevich.diploma.service;

import edu.dashkevich.diploma.data.Text;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// ?????? ????? ?????? xml-??? ? ????????? ?????, ??????? ????????? ? ??? ? ???? ???????
// ??????? ? ???? <div1></div1> ?????? ????? ???????????, ??????????? ? ??? ?? ???????? ?????,
// ???????? ?????? ?????????? - ????????? ????????? ????????????? ??????.
public class XPathParser {

    private static final String HEAD_TAG_NAME = "head";
    public static final String TEXT_NODE_NAME = "#text";
    private List<String> sentences;
    private Set<String> nouns;
    private long allWords;
    private StringBuffer text;
    private NodeList nodeList;

    public Text parseText(String path) throws Exception {
        init(path);
        runByDiv1Tags(nodeList);
        return getText();
    }

    private Text getText() {
        Text text = new Text();
        text.setAllWords(allWords);
        text.setNouns(new ArrayList<String>(nouns));
        text.setSentences(sentences);
        text.setText(this.text.toString());
        return text;
    }

    private void init(String path) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        FileInputStream file = new FileInputStream(new File(path));
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document xmlDocument = builder.parse(file);
        XPath xPath = XPathFactory.newInstance().newXPath();
        nodeList = (NodeList) xPath.compile("/bncDoc/text/div1").evaluate(xmlDocument, XPathConstants.NODESET);
        sentences = new ArrayList<String>();
        nouns = new HashSet<String>();
        text = new StringBuffer();
    }


    private void runByDiv1Tags(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            runByDiv1ChildTags(nodeList.item(i).getChildNodes());
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
                text.append(wTags.item(k).getTextContent());
                setNoun(wTags.item(k));
            }
        }
        sentences.add(sentence.toString());
    }

    private void setNoun(Node wTag) {
        if (wTag != null) {
            Node type = wTag.getAttributes().getNamedItem("type");
            if (type != null) {
                String wTagAttribute = type.getNodeValue();
                if (wTagAttribute != null && (wTagAttribute.equals("NN1") || wTagAttribute.equals("NN2") || wTagAttribute.equals("NP1"))) {
                    nouns.add(wTag.getTextContent().toLowerCase());
                }
            }
        }
    }
}
