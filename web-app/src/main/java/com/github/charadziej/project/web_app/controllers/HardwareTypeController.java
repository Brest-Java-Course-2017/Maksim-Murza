package com.github.charadziej.project.web_app.controllers;

import com.github.charadziej.project.client.rest.api.HardwareTypeConsumer;
import com.github.charadziej.project.dao.HardwareType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Created by charadziej on 4/16/17.
 */

@Controller
public class HardwareTypeController {

    @Autowired
    HardwareTypeConsumer hardwareTypeConsumer;

    @GetMapping("/types")
    public String getAllTypes(Model model) {
        List<HardwareType> typesList = hardwareTypeConsumer.getAllTypes();
        model.addAttribute("typesList", typesList);
        return "types";
    }
}
