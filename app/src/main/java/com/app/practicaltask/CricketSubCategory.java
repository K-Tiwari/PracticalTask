package com.app.practicaltask;

public class CricketSubCategory {

    private String title;
    private String content;

    public CricketSubCategory(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /*private String title;
    public ArrayList<Content> contentArrayList;

    public CricketSubCategory(String title, ArrayList<Content> contentArrayList) {
        this.title = title;
        this.contentArrayList = contentArrayList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Content> getContentArrayList() {
        return contentArrayList;
    }

    public void setContentArrayList(ArrayList<Content> contentArrayList) {
        this.contentArrayList = contentArrayList;
    }

    public static class Content{

        public Content(String content) {
            this.content = content;
        }

        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }*/
}
