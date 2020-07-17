package com.shmkane.wwf;

public class WebpageRequest {

    private String url;
    private String formattingType;
    private boolean sorted;
    private boolean wordOnly;

    private String results;
    private int totalWords;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public int getTotalWords() {
        return totalWords;
    }

    public void setTotalWords(int totalWords) {
        this.totalWords = totalWords;
    }

    public String getFormattingType() {
        return formattingType;
    }

    public void setFormattingType(String formattingType) {
        this.formattingType = formattingType;
    }

    public boolean isSorted() {
        return sorted;
    }

    public void setSorted(boolean sorted) {
        this.sorted = sorted;
    }

    public boolean isWordOnly() {
        return wordOnly;
    }

    public void setWordOnly(boolean wordOnly) {
        this.wordOnly = wordOnly;
    }
}