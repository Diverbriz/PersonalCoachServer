package com.example.personalcoach.service.item;

import com.example.personalcoach.exception.NotFoundExceptions;
import com.example.personalcoach.model.Brand;
import com.example.personalcoach.model.ImageItem;
import com.example.personalcoach.model.Item;
import com.example.personalcoach.model.Type;
import com.example.personalcoach.repository.ImageItemRepository;
import com.example.personalcoach.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ImageItemRepository imageItemRepository;

    private final String defaultUrl = "https://yandex.ru/images/search?img_url=https%3A%2F%2Flookaside.fbsbx.com%2Flookaside%2Fcrawler%2Fmedia%2F%3Fmedia_id%3D2413253388917436&lr=213&pos=8&rpt=simage&text=%D0%B8%D0%B7%D0%BE%D0%B1%D1%80%D0%B0%D0%B6%D0%B5%D0%BD%D0%B8%D0%B5%20%D0%BA%D0%BE%D0%BC%D0%B8%D0%BA%D1%81%D0%B0";
    @Autowired
    public ItemService(ItemRepository itemRepository, ImageItemRepository imageItemRepository) {
        this.itemRepository = itemRepository;
        this.imageItemRepository = imageItemRepository;
    }

    public Item createItem(Item item){
       if(itemRepository.findFirstByName(item.getName()).isEmpty()){
           itemRepository.save(item);
       }
       return item;
    }

    public Item findOneById(Long id){
        return itemRepository.findFirstById(id)
                .orElseThrow(NotFoundExceptions::new);
    }
    public Item findOneByName(String name){

        return itemRepository.findFirstByName(name)
                .orElseThrow(NotFoundExceptions::new);
    }

    public List<Item> getAllItem(
            String name, Brand brand, Type type
    ){
        List<Item> itemList;
        if(Objects.equals(name, "") && brand == null && type == null){
            itemList = itemRepository.findAll();
        } else if (brand == null && type == null && name.length() > 3) {
            itemList = itemRepository.findItemsByName(name)
                    .orElse(new ArrayList<>());
        } else if (name.length() > 3 && brand != null && type != null) {
            itemList = itemRepository.findAllByNameAndBrandIdAndTypeId(name, brand, type)
                    .orElse(new ArrayList<>());
        }
        else {
            itemList = new ArrayList<>();
        }

        return itemList;
    }

    public Item deleteItem(String name){
        Item item = itemRepository.findFirstByName(name)
                .orElseThrow(NotFoundExceptions::new);
        itemRepository.delete(item);
        return item;
    }

    public List<ImageItem> getImageByItemId(Long itemId){
        Item item = itemRepository.findFirstById(itemId)
                .orElseThrow(NotFoundExceptions::new);
        ImageItem imageItem = new ImageItem(item, defaultUrl);

        List<ImageItem> list = new ArrayList<>();
        list.add(imageItem);
        return imageItemRepository.findAllByItemId(item)
                .orElse(list);
    }

    public ImageItem createImageItem(ImageItem imageItem){
        imageItemRepository.save(imageItem);
        return imageItem;
    }
}
