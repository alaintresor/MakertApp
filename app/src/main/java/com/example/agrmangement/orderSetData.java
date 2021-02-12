package com.example.agrmangement;

public class orderSetData {
    String id,name,quantity,price,amount,image;
    orderSetData(String id,String name,String quantity,String price, String amount,String image){
        this.id=id;
        this.name=name;
        this.quantity=quantity;
        this.price=price;
        this.amount=amount;
        this.image=image;
    }

    public String getImage() {
        return image;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

    public String getAmount() {
        return amount;
    }
}
