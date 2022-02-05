package com.book;

import com.string.Strings;

public class Book {
    private String resourceID,title,language,category,author,price,updatedOn,copies;
    private static Book bookSingleton;
    
    public Book() {
        super();
    }

    public Book(String resourceID, String title, String language, String category, String author, String price, String updatedOn, String copies) {
        this.resourceID = resourceID;
        this.title = title;
        this.language = language;
        this.category = category;
        this.author = author;
        this.price = price;
        this.updatedOn = updatedOn;
        this.copies = copies;
    }
    
    public static Book getInstance() {
        if(bookSingleton == null) {
            synchronized(Book.class) {
                bookSingleton = new Book();
                System.out.println(Strings.created_addItemUISingleton);
            }
        }
        
        return bookSingleton;
    }
    
    public String getResourceID() {
        return resourceID;
    }

    public void setResourceID(String resourceID) {
        this.resourceID = resourceID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getCopies() {
        return copies;
    }

    public void setCopies(String copies) {
        this.copies = copies;
    }
    
    
}
