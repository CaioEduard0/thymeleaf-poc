package com.example.thymeleafpoc.utils;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

public class PageUtils {

    public static void format(Page<?> page, Model model) {
        if (!page.hasPrevious()) {
            model.addAttribute("currentPage", 0);
            model.addAttribute("firstPageToShow", 0);
            model.addAttribute("lastPageToShow", Math.min(page.getTotalPages(), 10));
            model.addAttribute("hasPrevious", page.hasPrevious());
            model.addAttribute("hasNext", page.hasNext());
            return;
        }
        if (!page.hasNext()) {
            model.addAttribute("currentPage", page.getNumber());
            model.addAttribute("firstPageToShow", Math.max(page.getNumber() - 5, 0));
            model.addAttribute("lastPageToShow", page.getNumber());
            model.addAttribute("hasPrevious", page.hasPrevious());
            model.addAttribute("hasNext", page.hasNext());
            return;
        }
        model.addAttribute("currentPage", page.getNumber());
        model.addAttribute("firstPageToShow", Math.max(page.getNumber() - 5, 0));
        model.addAttribute("lastPageToShow", Math.min(page.getNumber() + 4, page.getTotalPages()));
        model.addAttribute("hasPrevious", page.hasPrevious());
        model.addAttribute("hasNext", page.hasNext());
    }
}
