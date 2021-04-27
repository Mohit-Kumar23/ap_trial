package com.hp.appoindone;

public class categoryClass {
    String Name,url;

    categoryClass(){}

    public categoryClass(String name, String url) {
        this.Name = name;
        this.url = url;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
