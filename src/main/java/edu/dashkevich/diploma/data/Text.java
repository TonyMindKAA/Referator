package edu.dashkevich.diploma.data;

import java.util.List;
import java.util.Set;

public class Text {
    private List<String> sentences;
    private List<String> nouns;
    private long allWords;
    private String text;

    public List<String> getSentences() {
        return sentences;
    }

    public void setSentences(List<String> sentences) {
        this.sentences = sentences;
    }

    public List<String> getNouns() {
        return nouns;
    }

    public void setNouns(List<String> nouns) {
        this.nouns = nouns;
    }

    public long getAllWords() {
        return allWords;
    }

    public void setAllWords(long allWords) {
        this.allWords = allWords;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Text{" +
                "sentences=" + sentences +
                ", nouns=" + nouns +
                ", allWords=" + allWords +
                ", text='" + text + '\'' +
                '}';
    }
}
