package com.example.agrmangement;

public class catSetData {
    String catName,description,image,qty,price,id,category;
    catSetData(String catName,String description,String image,String id,String qty,String price,String category){
        this.catName=catName;
        this.description=description;
        this.image=image;
        this.qty=qty;
        this.price=price;
        this.id=id;
        this.category=category;
    }

    public String getCatName() {
        return catName;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getQty() {
        return qty;
    }

    public String getId() {
        return id;
    }

    public String getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }
}
