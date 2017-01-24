package com.fadong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by 1000742 on 2017. 1. 2..
 */
@Controller
@RequestMapping("admin")
public class AdminController {

    @RequestMapping("page")
    private String page() {
        return "admin/page";
    }

    @RequestMapping("card")
    private String card() {
        return "admin/card";
    }
}
