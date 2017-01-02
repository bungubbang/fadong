package com.fadong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by 1000742 on 2017. 1. 2..
 */
@Controller
public class AdminController {

    @RequestMapping("admin")
    private String admin() {
        return "admin";
    }
}
