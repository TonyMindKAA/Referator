package edu.dashkevich.diploma.service;

import com.google.common.base.Preconditions;
import edu.dashkevich.diploma.data.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

// ????? ??????? ???????? ?? ?????????? ??????? ? ????? ? ???????? ?? ???????? ? ????? ??????
public class XmlCorpusReader {
    private String directoryPath;
    private XPathParser xPathParser = new XPathParser();

    // ??????? ?????? ???? ???????, ??????? ?????? ? ???????? ????? ? ??????????? .xml
    public List<Text> getAllText(String directoryPath) throws Exception {
        List<Text> texts = new ArrayList<Text>();
        List<String> xmlFileNames = getXMLFileNames(directoryPath);
        setTextFromFileToList(directoryPath, texts, xmlFileNames);
        return texts;
    }

    private void setTextFromFileToList(String directoryPath, List<Text> texts, List<String> xmlFileNames) throws Exception {
        for (int i = 0; i < xmlFileNames.size(); i++) {
            Text text = xPathParser.parseText(directoryPath + "\\" + xmlFileNames.get(i));
            text.setFileName(xmlFileNames.get(i));
            texts.add(text);
        }
    }

    // ??????????????? ?-? ?????????? ?????? ???????? ?????? ??????? ? ???????? ?????
    public List<String> getXMLFileNames(String path) {
        Preconditions.checkNotNull(path, "Path can not be null");
        directoryPath = path;
        final File folder = new File(path);
        Preconditions.checkArgument(folder.isDirectory(), "It's not a directory!");
        return findXMLFilesInDirectory(folder);
    }

    private List<String> findXMLFilesInDirectory(File folder) {
        List<String> fileNames = new ArrayList<String>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isFile() && fileEntry.getName().endsWith(".xml")) {
                fileNames.add(fileEntry.getName());
            } else {
                printLog(fileEntry);
            }
        }
        return fileNames;
    }

    private void printLog(File fileEntry) {
        if (fileEntry.isDirectory()) {
            System.out.println("Skip directory: " + fileEntry.getName());
        } else {
            System.out.println("It's not xml-file(skiped): " + fileEntry.getName());
        }
    }
}
