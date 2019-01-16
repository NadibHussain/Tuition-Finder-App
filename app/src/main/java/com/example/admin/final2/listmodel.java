package com.example.admin.final2;

public class listmodel {
    private String name;
    private String uni;
    private String img;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public listmodel(String name, String uni, String img, String uid) {
        this.name = name;
        this.uni = uni;
        this.img = img;
        this.uid = uid;
    }

    private String uid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUni() {
        return uni;
    }

    public void setUni(String uni) {
        this.uni = uni;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public listmodel(String name, String uni, String img) {
        this.name = name;
        this.uni = uni;
        this.img = img;
    }
}
