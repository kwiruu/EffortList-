package com.example.effortlist.Model;

public class TodoModel {
    private int id, status;
    private String todo;
    private String date; // New date field

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public String getTodo() {
        return todo;
    }

    public String getDate() {
        return date;
    }
}
