package com.edu.tech.major.controller;

import com.edu.tech.major.global.GlobalData;
import com.edu.tech.major.model.Category;
import com.edu.tech.major.model.Product;
import com.edu.tech.major.repository.chitietdathangRepository;
import com.edu.tech.major.repository.dathangRepository;
import com.edu.tech.major.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.edu.tech.major.model.dathang;
import com.edu.tech.major.model.chitietdathang;


@Controller
public class CartController {
    @Autowired
    ProductService productService;
    @Autowired
    dathangRepository dathangRepository;
    @Autowired
    chitietdathangRepository chitietdathangRepository;

    @GetMapping("/cart")
    public String cartGet(Model model){
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        model.addAttribute("cart", GlobalData.cart);
        return "cart";
    }//page cart

    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable int id,  @RequestParam(value = "quantity", required = false) String quantity){
        System.out.println(quantity);
        GlobalData.cart.add(productService.getProductById(id).get());
        return "redirect:/shop";
    }//click add from page viewProduct

    @GetMapping("/cart/removeItem/{index}")
    public String cartItemRemove(@PathVariable int index){
        GlobalData.cart.remove(index);
        return "redirect:/cart";
    } // delete 1 product

    @GetMapping("/checkout")
    public String checkout(Model model){
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        //model.addAttribute("cart", GlobalData.cart);
        model.addAttribute("dathang", new dathang());

        return "checkout";
    } // checkout totalPrice


    @PostMapping("/checkout")
    public String postcheckout(Model model,@ModelAttribute("dathang") dathang dathang){
        dathang.setTongtien(GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        dathangRepository.save(dathang);

        for(int i=0;i<GlobalData.cart.size();i++){
            chitietdathang chitietdathang1 = new chitietdathang();
            String tensp = GlobalData.cart.get(i).getName();
            double tongtiensanpham = GlobalData.cart.get(i).getPrice();
            System.out.println(tongtiensanpham);
            chitietdathang1.setTensanpham(tensp);
            chitietdathang1.setTiensanpham(tongtiensanpham);
            chitietdathang1.setDathang(dathang);
            chitietdathang1.setSoluong(1);
            chitietdathangRepository.save(chitietdathang1);
        }

        return "checkout";
    }

}
