package com.example.minilibrary;

public class Book {
    private String id;
    private String title;
    private String authour;
    private int pages;
    private String desc;
    private String imgUrl;

    public Book(String id, String title, String author, int pages, String imgUrl, String desc) {
        this.id = id;
        this.title = title;
        this.authour = author;
        this.pages = pages;
        this.desc = desc;
        this.imgUrl = imgUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return authour;
    }

    public void setAuthor(String author) {
        this.authour = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String longDesc) {
        this.desc = longDesc;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + title + '\'' +
                ", author='" + authour + '\'' +
                ", pages=" + pages +
                ", desc='" + desc + '\'' +
                '}';
    }
}
