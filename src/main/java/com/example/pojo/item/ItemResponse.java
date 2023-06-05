package com.example.pojo.item;

import com.example.personalcoach.model.ImageItem;
import com.example.personalcoach.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemResponse {
    private Item item;
    private List<String> url;

    public ItemResponse(){}
    public ItemResponse(Item item, List<ImageItem> imageItems) {
        this.item = item;
        url = new ArrayList<>();
        imageItems.forEach(it -> url.add(it.getUrl()));
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }
}
