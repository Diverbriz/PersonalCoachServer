package com.example.personalcoach.service.item;

import com.example.personalcoach.exception.NotFoundExceptions;
import com.example.personalcoach.model.Brand;
import com.example.personalcoach.model.Item;
import com.example.personalcoach.model.Type;
import com.example.personalcoach.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item createItem(Item item){
       if(itemRepository.findFirstByName(item.getName()).isEmpty()){
           itemRepository.save(item);
       }
       return item;
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
}
