package com.maharecruitment.gov.in.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {
    @GetMapping("/common")
    public String home(Model model) {
        model.addAttribute("message", "Hello HRMS COMMON CONFIG Module");
        return "common/common_home";
    }
}


