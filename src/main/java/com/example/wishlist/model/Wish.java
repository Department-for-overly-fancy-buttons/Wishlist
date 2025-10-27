package com.example.wishlist.model;

public class Wish
{
    private String name;
    private String description;
    private String url;
    private int id;

    public Wish()
    {

    }



    public Wish(String name, String description, String url, int id)
    {
        this.name = name;
        this.description = description;
        this.url = url;
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

}
