package com.example.agrmangement;

import android.widget.Toast;

import static java.lang.Integer.parseInt;

public class setCartData {

    String id, proId, image, name, status, category, price, qty, inStock,availableDate;


    public setCartData(String id, String proId, String image, String name, String status, String category, String price, String qty, String inStock,String availableDate) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.status = status;
        this.category = category;
        this.price = price;
        this.qty = qty;
        this.proId = proId;
        this.inStock = inStock;
        this.availableDate=availableDate;

    }

    public String getId() {
        return id;
    }

    public String getProId() {
        return proId;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getCategory() {
        return category;
    }

    public String getPrice() {
        return price;
    }

    public String getQty() {
        return qty;
    }

    public String getAvailableDate() {
        return availableDate;
    }

    public boolean addToQty() {
        if (parseInt(this.inStock) == parseInt(this.qty)) {
            return false;
        } else {
            this.qty = String.valueOf(parseInt(this.qty) + 1);
            return true;
        }
    }

    public boolean reduceToQty() {
        if (parseInt(this.qty) == 1) {
            return false;
        } else {
            this.qty = String.valueOf(parseInt(this.qty) - 1);
            return true;
        }
    }


}
