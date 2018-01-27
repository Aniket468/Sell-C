package com.example.aniketkumar.test;

/**
 * Created by Aniket Kumar on 28-Jan-18.
 */

public class Item_Data_Card {
    public String title, description, price, sr, id, rupee;
    public String imageId;


    Item_Data_Card(String imageId, String title, String description, String price, String id) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.imageId = imageId;
        this.id = id;
    }
}
