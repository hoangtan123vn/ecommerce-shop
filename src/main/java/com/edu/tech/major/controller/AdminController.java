package com.edu.tech.major.controller;

import com.edu.tech.major.dto.ProductDTO;
import com.edu.tech.major.model.Category;
import com.edu.tech.major.model.Product;
import com.edu.tech.major.repository.ProductRepository;
import com.edu.tech.major.service.CategoryService;
import com.edu.tech.major.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class AdminController {
    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";


    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @GetMapping("/admin")
    public String adminHome(){
        return "adminHome";
    }//page admin home

    //Categories session
    @GetMapping("/admin/categories")
    public String getCat(Model model){
        model.addAttribute("categories", categoryService.getAllCategory());
        return "categories";
    }//view all categories

    @GetMapping("/admin/categories/add")
    public String getCatAdd(Model model){
        model.addAttribute("category", new Category());
        return "categoriesAdd";
    }//form add new category

    @PostMapping("/admin/categories/add")
    public String postCatAdd(@ModelAttribute("category") Category category){
        categoryService.updateCategory(category);
        return "redirect:/admin/categories";
    }//form add new category > do add

    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCat(@PathVariable int id){
        categoryService.removeCategoryById(id);
        return "redirect:/admin/categories";
    }//delete 1 category

    @GetMapping("/admin/categories/update/{id}")
    public String updateCat(@PathVariable int id, Model model){
        Optional<Category> category = categoryService.getCategoryById(id);
        if(category.isPresent()){
            model.addAttribute("category", category.get());
            return "categoriesAdd";
        }else {
            return "404";
        }
    }//form edit category, fill old data into form

    //Products session
    @GetMapping("/admin/products")
    public String getPro(Model model){
        model.addAttribute("products", productService.getAllProduct());
        return "products";
    }//view all products

    @GetMapping("/admin/products/add")
    public String getProAdd(Model model){
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategory());
        return "productsAdd";
    }// form add new product

    @PostMapping("/admin/products/add")
    public String postProAdd(@ModelAttribute("productDTO") ProductDTO productDTO,
                             @RequestParam("productImage") MultipartFile fileProductImage,
                             @RequestParam("imgName") String imgName,
                             @RequestParam("productImage2") MultipartFile fileProductImage2,
                             @RequestParam("imgName2") String imgName2,
                             @RequestParam("productImage3") MultipartFile fileProductImage3,
                             @RequestParam("imgName3") String imgName3,
                             @RequestParam("productImage4") MultipartFile fileProductImage4,
                             @RequestParam("imgName4") String imgName4) throws IOException {
        //convert dto > entity
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setDescription(productDTO.getDescription());

//        set image1
        String imageUUID;
        if(!fileProductImage.isEmpty()){
            imageUUID = fileProductImage.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
            Files.write(fileNameAndPath, fileProductImage.getBytes());
        }else {
            imageUUID = imgName;
        }
        product.setImageName(imageUUID);

//        set image2
        String imageUUID2;
        if(!fileProductImage2.isEmpty()){
            imageUUID2 = fileProductImage2.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDir, imageUUID2);
            Files.write(fileNameAndPath, fileProductImage2.getBytes());
        }else {
            imageUUID2 = imgName2;
        }
        product.setImageName2(imageUUID2);

        // set image 3
        String imageUUID3;
        if(!fileProductImage3.isEmpty()){
            imageUUID3 = fileProductImage3.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDir, imageUUID3);
            Files.write(fileNameAndPath, fileProductImage3.getBytes());
        }else {
            imageUUID3 = imgName3;
        }
        product.setImageName3(imageUUID3);

        //set image 4
        String imageUUID4;
        if(!fileProductImage4.isEmpty()){
            imageUUID4 = fileProductImage4.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDir, imageUUID4);
            Files.write(fileNameAndPath, fileProductImage4.getBytes());
        }else {
            imageUUID4 = imgName4;
        }
        product.setImageName4(imageUUID4);

        productService.updateProduct(product);
        return "redirect:/admin/products";
    }//form add new product > do add

    @GetMapping("/admin/products/delete/{id}")
    public String deletePro(@PathVariable long id){
        productService.removeProductById(id);
        return "redirect:/admin/products";
    }//delete 1 product

    @GetMapping("/admin/products/update/{id}")
    public String updatePro(@PathVariable long id, Model model){
        Optional<Product> opProduct = productService.getProductById(id);
        if (opProduct.isPresent()){
            Product product = opProduct.get();
            //convert entity > dto
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setCategoryId(product.getCategory().getId());
            productDTO.setPrice(product.getPrice());
            productDTO.setWeight(product.getWeight());
            productDTO.setDescription(product.getDescription());
            productDTO.setImageName(product.getImageName());
            productDTO.setImageName2(product.getImageName2());
            productDTO.setImageName3(product.getImageName3());
            productDTO.setImageName4(product.getImageName4());
            model.addAttribute("productDTO", productDTO);
            model.addAttribute("categories", categoryService.getAllCategory());
            return "productsAdd";
        }else {
            return "404";
        }

    }//form edit product, fill old data into form
}
