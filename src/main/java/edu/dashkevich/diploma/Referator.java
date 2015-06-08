package edu.dashkevich.diploma;

import edu.dashkevich.diploma.data.KeyWord;
import edu.dashkevich.diploma.data.Referat;
import edu.dashkevich.diploma.data.Text;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Referator {
    public Referat referate(String directoryPath) throws Exception {
        Referat referatObj = new Referat();
        String path = "d:\\XML";
        XmlCorpusReader app = new XmlCorpusReader();
        List<Text> allText = app.getAllText(path);
        KeyWordsManager keyWordsManager = new KeyWordsManager(referatObj);
        List<KeyWord> resultKeyWords = keyWordsManager.getResultKeyWords(allText);
        Set<String> resultSentence = getResultSentences(referatObj, allText, resultKeyWords);
        createReferat(resultSentence);
        referatObj.setAllText(allText);
        referatObj.setResultSentence(resultSentence);
        referatObj.setNumberSentenceFromeReferat(resultSentence.size());
        referatObj.setReferat(createReferat(resultSentence));
        return referatObj;
    }

    private Set<String> getResultSentences(Referat referatObj, List<Text> allText, List<KeyWord> resultKeyWords) {
        Set<String> resultSentence = new LinkedHashSet<String>();
        int numberSentenceFromAllText= 0;
        for (int i = 0; i < allText.size(); i++) {
            List<String> sentences = allText.get(i).getSentences();
            numberSentenceFromAllText += sentences.size();
            for (int j = 0; j <resultKeyWords.size(); j++) {
                for (int k = 0; k < sentences.size(); k++) {
                    if(sentences.get(k).contains(resultKeyWords.get(j).getWord())){
                        resultSentence.add(sentences.get(k).trim());
                    }
                }
            }
        }
        referatObj.setNumberSentenceFromAllText(numberSentenceFromAllText);
        return resultSentence;
    }

    private String createReferat(Set<String> resultSentence) {
        StringBuffer referat = new StringBuffer();
        for(String sentence: resultSentence){
            referat.append(sentence + "\n");
        }
        return referat.toString();
    }
}
