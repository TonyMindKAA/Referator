package edu.dashkevich.diploma.data;

import java.util.List;
import java.util.Set;

public class Referat {
    private List<Text> allText;
    private Set<String> resultSentence;
    private List<KeyWord> allKeyWords;
    private List<KeyWord> filteringKeyWords;
    private String referat;
    private int max;
    private int min;
    private int maxRange;
    private int minRange;
    private int numberSentenceFromAllText;
    private int numberSentenceFromeReferat;

    public List<KeyWord> getAllKeyWords() {
        return allKeyWords;
    }

    public void setAllKeyWords(List<KeyWord> allKeyWords) {
        this.allKeyWords = allKeyWords;
    }

    public List<KeyWord> getFilteringKeyWords() {
        return filteringKeyWords;
    }

    public void setFilteringKeyWords(List<KeyWord> filteringKeyWords) {
        this.filteringKeyWords = filteringKeyWords;
    }

    public List<Text> getAllText() {
        return allText;
    }

    public void setAllText(List<Text> allText) {
        this.allText = allText;
    }

    public Set<String> getResultSentence() {
        return resultSentence;
    }

    public void setResultSentence(Set<String> resultSentence) {
        this.resultSentence = resultSentence;
    }

    public String getReferat() {
        return referat;
    }

    public void setReferat(String referat) {
        this.referat = referat;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMaxRange() {
        return maxRange;
    }

    public void setMaxRange(int maxRange) {
        this.maxRange = maxRange;
    }

    public int getMinRange() {
        return minRange;
    }

    public void setMinRange(int minRange) {
        this.minRange = minRange;
    }

    public int getNumberSentenceFromAllText() {
        return numberSentenceFromAllText;
    }

    public void setNumberSentenceFromAllText(int numberSentenceFromAllText) {
        this.numberSentenceFromAllText = numberSentenceFromAllText;
    }

    public int getNumberSentenceFromeReferat() {
        return numberSentenceFromeReferat;
    }

    public void setNumberSentenceFromeReferat(int numberSentenceFromeReferat) {
        this.numberSentenceFromeReferat = numberSentenceFromeReferat;
    }

    @Override
    public String toString() {
        return
                "\n[---------------------------------------------] " +
                "\n[---------------[Informations]----------------] " +
                "\n[---------------------------------------------] " +
                "\n|------>[File numbers]: " + allText.size() +
                "\n|------>[Key words before filtering]: " + allKeyWords.size() +
                "\n|------>[Key words after filtering]:" + filteringKeyWords.size() +
                "\n|------>[General max idf]: " + max +
                "\n|------>[General min idf]: " + min +
                "\n|------>[Max range idf]: " + maxRange +
                "\n|------>[Min range idf]: " + minRange +
                "\n|------>[Number sentence from all text]: " + numberSentenceFromAllText +
                "\n|------>[Number sentence frome referat]: " + numberSentenceFromeReferat +
                "\n|------>[Text zip]: " + (((double)numberSentenceFromeReferat/(double)numberSentenceFromAllText)*100)+"%"+
                "\n|------>[Text of referat]:\n " +referat;

    }
}
