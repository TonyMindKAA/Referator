package edu.dashkevich.diploma;

import com.google.common.base.Preconditions;
import edu.dashkevich.diploma.data.KeyWord;
import edu.dashkevich.diploma.data.Text;

import java.io.File;
import java.util.*;

public class XmlCorpusReader {
    private String directoryPath;
    private XPathParser xPathParser = new XPathParser();

    public static void main(String[] args) throws Exception {
        String path = "C:\\Users\\Mr.Green\\Desktop\\download\\XML";
        XmlCorpusReader app = new XmlCorpusReader();
        List<Text> allText = app.getAllText(path);
        System.out.println(allText.size());
        KeyWordsManager keyWordsManager = new KeyWordsManager();
        List<KeyWord> resultKeyWords = keyWordsManager.getResultKeyWords(allText);
        System.out.println(resultKeyWords.size() + " after filtering");

        Set<String> resultSentence = new LinkedHashSet<String>();
        long ss= 0;
        StringBuffer referat = new StringBuffer();
        for (int i = 0; i < allText.size(); i++) {
            List<String> sentences = allText.get(i).getSentences();
            ss += sentences.size();
            for (int j = 0; j <resultKeyWords.size(); j++) {
                for (int k = 0; k < sentences.size(); k++) {
                    if(sentences.get(k).contains(resultKeyWords.get(j).getWord())){
                        resultSentence.add(sentences.get(k).trim());
                    }
                }
            }
        }



        for(String sentence: resultSentence){
            referat.append(sentence+"\n");
        }


        System.out.println("Referat: "+resultSentence.size());
        System.out.println("TextSent: "+ss);
        System.out.println("TextSent: "+referat.toString());




    }

    public List<Text> getAllText(String directoryPath) throws Exception {
        List<Text> texts = new ArrayList<Text>();
        List<String> xmlFileNames = getXMLFileNames(directoryPath);
        for (int i = 0; i < xmlFileNames.size(); i++) {
            Text text = xPathParser.parseText(directoryPath + "\\" + xmlFileNames.get(i));
            text.setFileName(xmlFileNames.get(i));
            texts.add(text);
        }
        return texts;
    }  
    
    
    public List<String> getXMLFileNames(String path) {
        Preconditions.checkNotNull(path,"Path can not be null");
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
