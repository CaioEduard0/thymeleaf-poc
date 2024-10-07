package com.example.thymeleafpoc.controller;

import com.example.thymeleafpoc.dto.OrderDTO;
import com.example.thymeleafpoc.dto.ProductDTO;
import com.example.thymeleafpoc.enums.Category;
import com.example.thymeleafpoc.mapper.ProductMapper;
import com.example.thymeleafpoc.model.Product;
import com.example.thymeleafpoc.model.User;
import com.example.thymeleafpoc.service.ProductService;
import com.example.thymeleafpoc.utils.PageUtils;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private static final String REDIRECT_PRODUCTS = "redirect:/products";

    private final ProductService productService;

    @GetMapping
    public String findAll(Model model,
                          @RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "") String search,
                          @AuthenticationPrincipal User user,
                          HttpSession session) {
        Page<Product> products = productService.findAll(page, search);
        user.getAuthorities().stream().findFirst().ifPresent(authority -> model.addAttribute("authority", authority));
        model.addAttribute("products", products);
        if (session.getAttribute("orderDTO") == null) {
            session.setAttribute("orderDTO", new OrderDTO());
        }
        PageUtils.format(products, model);
        return "product/list";
    }

    @GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        return "product/create";
    }

    @PostMapping
    public String save(@ModelAttribute @Valid ProductDTO productDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "product/create";
        }
        productService.save(productDTO);
        return REDIRECT_PRODUCTS;
    }

    @GetMapping("/update/{id}")
    public String updatePage(Model model, @PathVariable Long id) {
        Product product = productService.find(id);
        model.addAttribute("id", id);
        model.addAttribute("productDTO", ProductMapper.toProductDTO(product));
        return "product/update";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute @Valid ProductDTO productDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "product/update";
        }
        productService.update(id, productDTO);
        return REDIRECT_PRODUCTS;
    }

    @GetMapping("/{id}")
    public String delete(@PathVariable Long id) {
        productService.delete(id);
        return REDIRECT_PRODUCTS;
    }

    @GetMapping("{id}/add-to-cart")
    public String addToCart(@PathVariable Long id, HttpSession session) {
        OrderDTO orderDTO = (OrderDTO) session.getAttribute("orderDTO");
        orderDTO.getProductsIds().add(id);
        return REDIRECT_PRODUCTS;
    }

    @GetMapping("{id}/remove-from-cart")
    public String removeFromCart(@PathVariable Long id, HttpSession session) {
        OrderDTO orderDTO = (OrderDTO) session.getAttribute("orderDTO");
        orderDTO.getProductsIds().remove(id);
        return REDIRECT_PRODUCTS;
    }

    @GetMapping("/charts")
    public String charts(Model model) {
        Map<String, Integer> pricesByRange = productService.getPricesByRange();
        Map<String, Integer> categories = productService.getCategoriesQuantity();
        model.addAttribute("priceRange", pricesByRange.keySet());
        model.addAttribute("quantityByPriceRange", pricesByRange.values());
        if (categories.containsKey(Category.CLOTHES.getName())) {
            model.addAttribute("clothesQuantity", categories.get(Category.CLOTHES.getName()));
        }
        if (categories.containsKey(Category.ELECTRONICS.getName())) {
            model.addAttribute("electronicsQuantity", categories.get(Category.ELECTRONICS.getName()));
        }
        if (categories.containsKey(Category.FURNITURE.getName())) {
            model.addAttribute("furnitureQuantity", categories.get(Category.FURNITURE.getName()));
        }
        return "product/chart";
    }
}
