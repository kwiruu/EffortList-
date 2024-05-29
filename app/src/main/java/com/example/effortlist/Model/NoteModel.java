package com.example.effortlist.Model;

public class NoteModel {
    private int id;
    private String TITLE;
    private String TEXT; // New date field

    public void setId(int id) {
        this.id = id;
    }

    public void setTEXT(String TEXT) {
        this.TEXT = TEXT;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public int getId() {
        return id;
    }

    public String getTEXT() {
        return TEXT;
    }

    public String getTITLE() {
        return TITLE;
    }
}
