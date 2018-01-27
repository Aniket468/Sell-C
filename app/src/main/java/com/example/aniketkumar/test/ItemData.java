package com.example.aniketkumar.test;

/**
 * Created by Aniket Kumar on 24-Jan-18.
 */


public class ItemData {
    public String title,description,price,sr,id,rupee;
    public String imageId;


    ItemData(String imageId,String title,String description,String price,String sr,String id) {
        this.title = title;
        this.description=description;
        this.price=price;
        this.imageId = imageId;
        this.sr=sr;
        this.id=id;
    }

}