package com.example.personalcoach.service.item;

import com.example.personalcoach.exception.NotFoundExceptions;
import com.example.personalcoach.model.Brand;
import com.example.personalcoach.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    private final BrandRepository brandRepository;

    @Autowired
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public Brand createBrand(Brand brand){
        if(brandRepository.findFirstByName(brand.getName()).isPresent()){
            Brand brand1 = brandRepository.findFirstByName(brand.getName())
                            .get();
            brand1.setName(brand.getName());
            return brand1;
        }
        else {
            brandRepository.save(brand);
            return brand;
        }
    }

    public Brand findOneByName(String name){
        return brandRepository.findFirstByName(name)
                .orElse(null);
    }

    public Brand findOneById(Long id){

        return brandRepository.findFirstById(id)
            .orElseThrow(NotFoundExceptions::new);
    }

    public List<Brand> getAllBrand(){
        return brandRepository.findAll();
    }

    public Brand deleteBrand(Long id){
        Brand brand = brandRepository.findFirstById(id)
                .orElseThrow(NotFoundExceptions::new);

            brandRepository.delete(brand);
            return brand;
    }
}
