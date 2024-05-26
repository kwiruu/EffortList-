package com.example.effortlist.Model;

public class TodoModel {
    private int id, status;
    private String todo;

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setTodo(String todo) {
        this.todo = todo;
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
}
