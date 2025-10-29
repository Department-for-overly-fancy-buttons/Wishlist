package com.example.wishlist.model;

public class Wishlist
{
    private int id;
    private String title;
    private int ownerId;

    public Wishlist(int id, String title, int ownerId)
    {
        this.id = id;
        this.title = title;
        this.ownerId = ownerId;
    }

    public Wishlist()
    {

    }

    public int getId()
    {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}
