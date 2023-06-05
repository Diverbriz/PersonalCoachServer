package com.example.personalcoach.service.item;

import com.example.personalcoach.exception.NotFoundExceptions;
import com.example.personalcoach.model.Brand;
import com.example.personalcoach.model.Type;
import com.example.personalcoach.repository.BrandRepository;
import com.example.personalcoach.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeService {
    private final TypeRepository typeRepository;

    @Autowired
    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public Type createType(Type type){
        if(typeRepository.findFirstByName(type.getName()).isPresent()){
            Type type1 = typeRepository.findFirstByName(type.getName())
                    .get();
            type1.setName(type.getName());
            return type1;
        }
        else {
            typeRepository.save(type);
            return type;
        }

    }

    public Type findOneByName(String name){
        return typeRepository.findFirstByName(name)
                .orElse(null);
    }

    public Type findOneById(Long id){

        return typeRepository.findFirstById(id)
                .orElseThrow(NotFoundExceptions::new);
    }

    public List<Type> getAllTypes(){
        return typeRepository.findAll();
    }

    public Type deleteType(Long id){
        Type brand = typeRepository.findFirstById(id)
                .orElseThrow(NotFoundExceptions::new);

        typeRepository.delete(brand);
        return brand;
    }
}
