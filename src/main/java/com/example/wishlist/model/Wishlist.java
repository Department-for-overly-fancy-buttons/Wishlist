package com.example.wishlist.model;

import java.util.List;

public class Wishlist
{
    private int id;
    private String title;
    private int ownerId;
    private List<Wish> wishes;

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

    public List<Wish> getWishes(){
        return wishes;
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

    public void setWishes(List<Wish> wishes){
        this.wishes = wishes;
    }

}
