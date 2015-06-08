package edu.dashkevich.diploma;

import edu.dashkevich.diploma.data.KeyWord;
import edu.dashkevich.diploma.data.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KeyWordsManager {
    private int max = 0;
    private int min = 0;
    private int maxRange = 0;
    private int minRange = 0;

    public List<KeyWord> getResultKeyWords(List<Text> texts) {
        List<String> keyWords = removeDuplicate(texts);
        List<KeyWord> resultKeyWords = new ArrayList<KeyWord>();
        setIDF(texts, keyWords, resultKeyWords);
        computingMinMaxRanges();
        System.out.println(resultKeyWords.size() + " before filtering");
        System.out.println(minRange+" mi::ma "+maxRange);
        return filterByMinMaxRange(resultKeyWords);
    }

    private List<KeyWord> filterByMinMaxRange(List<KeyWord> resultKeyWords) {
        List<KeyWord> result = new ArrayList<KeyWord>();
        for (int i = 0; i < resultKeyWords.size(); i++) {
            if(resultKeyWords.get(i).getIdf()>=minRange && resultKeyWords.get(i).getIdf()<= maxRange){
                result.add(resultKeyWords.get(i));
            }
        }
        return result;
    }

    private void computingMinMaxRanges() {
        maxRange = (int) ((max + min) / 2 + ((max + min) / 2) * 0.1);
        minRange = (int) (max * 0.3);
    }

    private void setIDF(List<Text> texts, List<String> keyWords, List<KeyWord> resultKeyWords) {
        for (int i = 0; i < keyWords.size(); i++) {
            int counter = 0;
            for (int j = 0; j < texts.size(); j++) {
                if (texts.get(j).getText().toLowerCase().contains(keyWords.get(i))) {
                    counter++;
                }
            }
            KeyWord keyWord = createKeyWord(texts.size(), keyWords.get(i), counter);
            resultKeyWords.add(keyWord);
        }
    }

    // log(N/n) ??????? idf ??? ??????? ????????? ?????
    private KeyWord createKeyWord(final int N, String keyWords, int counter) {
        double n = N;
        double idf = Math.log(n / counter) * 100;
        KeyWord keyWord = new KeyWord();
        keyWord.setIdf((int) idf);
        keyWord.setWord(keyWords);
        setMinMax(idf);
        return keyWord;
    }

    private void setMinMax(double idf) {
        if (idf > max)
            max = (int) idf;
        if (idf < min && idf != 0)
            min = (int) idf;
        if (min == 0)
            min = max;
    }

    private List<String> removeDuplicate(List<Text> texts) {
        Set<String> keyWords = new HashSet<String>();
        for (int i = 0; i < texts.size(); i++) {
            keyWords.addAll(texts.get(i).getNouns());
        }
        return new ArrayList<String>(keyWords);
    }
}
