package com.example.personalcoach.controller;

import com.example.personalcoach.exception.NotFoundExceptions;
import com.example.personalcoach.model.Brand;
import com.example.personalcoach.model.ImageItem;
import com.example.personalcoach.model.Item;
import com.example.personalcoach.model.Type;
import com.example.personalcoach.service.item.BrandService;
import com.example.personalcoach.service.item.ItemService;
import com.example.personalcoach.service.item.TypeService;
import com.example.pojo.item.ImageItemRequest;
import com.example.pojo.item.ItemRequest;
import com.example.pojo.item.ItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/items")
public class ItemController {
    private final BrandService brandService;

    private final TypeService typeService;

    private final ItemService itemService;

    @Autowired
    public ItemController(BrandService brandService, TypeService typeService, ItemService itemService) {
        this.brandService = brandService;
        this.typeService = typeService;
        this.itemService = itemService;
    }

    // Brand controller

    @GetMapping("/brand")
    public ResponseEntity<List<Brand>> getAllBrands(){
        return new ResponseEntity<>(brandService.getAllBrand(), HttpStatus.OK);
    }

    @PostMapping("/brand/create")
    public ResponseEntity<Brand> createBrand(@RequestBody Brand brand){
        return new ResponseEntity<>(brandService.createBrand(brand), HttpStatus.OK);
    }

    @DeleteMapping("/brand/delete/{id}")
    public ResponseEntity<Brand> deleteBrand(@PathVariable Long id){
        try {
            return new ResponseEntity<>(brandService.deleteBrand(id), HttpStatus.OK);
        } catch (NotFoundExceptions e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("brand/{name}")
    public ResponseEntity<Brand> findBrandByName(@PathVariable String name){
        try {
            return new ResponseEntity<>(brandService.findOneByName(name), HttpStatus.OK);
        } catch (NotFoundExceptions e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    //Type Controller
    @GetMapping("/type")
    public ResponseEntity<List<Type>> getAllTypes(){
        return new ResponseEntity<>(typeService.getAllTypes(), HttpStatus.OK);
    }

    @PostMapping("/type/create")
    public ResponseEntity<Type> createBrand(@RequestBody Type type){
        return new ResponseEntity<>(typeService.createType(type), HttpStatus.OK);
    }

    @DeleteMapping("/type/delete/{id}")
    public ResponseEntity<Type> deleteType(@PathVariable Long id){
        try {
            return new ResponseEntity<>(typeService.deleteType(id), HttpStatus.OK);
        } catch (NotFoundExceptions e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("type/{name}")
    public ResponseEntity<Type> findTypeByName(@PathVariable String name){
        try {
            return new ResponseEntity<>(typeService.findOneByName(name), HttpStatus.OK);
        } catch (NotFoundExceptions e){
            Type type = new Type();
            type.setName("Not found");
            return new ResponseEntity<>(type, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Item> createItem(@RequestBody ItemRequest itemRequest){
        Brand brand = new Brand();
        Type type = new Type();
        if(itemRequest.getBrandName() != null){
            brand = brandService.createBrand(new Brand(itemRequest.getBrandName()));
        }
        if(itemRequest.getTypeName() != null){
            type = typeService.createType(new Type(itemRequest.getTypeName()));
        }

        Item item = new Item(itemRequest.getName(), itemRequest.getPrice(), itemRequest.getRating(),
                brand,type);
        itemService.createItem(item);

        return ResponseEntity.ok(item);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ItemResponse>> getAllItems(
            @RequestParam("name") String name,
            @RequestParam("brandName") String brandName,
            @RequestParam("typeName") String typeName
    ){
        Brand brand = brandService.findOneByName(brandName);
        Type type = typeService.findOneByName(typeName);

        List<Item> itemList = itemService.getAllItem(name, brand, type);
        List<ItemResponse> response = new ArrayList<>();
        for (Item item:itemList
             ) {
            List<ImageItem> images = itemService.getImageByItemId(item.getId());
            response.add(new ItemResponse(item, images));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItemById(
            @PathVariable Long id){

            Item item = itemService.findOneById(id);
            List<ImageItem> itemList = itemService.getImageByItemId(item.getId());
            ItemResponse itemResponse = new ItemResponse(item, itemList);
            return ResponseEntity.ok(itemResponse);

    }

    @PostMapping("/image/create")
    public ResponseEntity<?> createImageItem(
            @RequestBody ImageItemRequest imageItem
    ){
        try {
            Item item = itemService.findOneById(imageItem.getItemId());

            itemService.createImageItem(new ImageItem(item, imageItem.getUrl()));
            return ResponseEntity.ok(
                    imageItem
            );
        }catch (NotFoundExceptions e){
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

}

//
