package com.github.charadziej.project.web_app.controllers;

import com.github.charadziej.project.client.rest.api.HardwareTypeConsumer;
import com.github.charadziej.project.dao.HardwareType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Web controller for requests to Hardware type
 */

@Controller
public class HardwareTypeController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private HardwareTypeConsumer hardwareTypeConsumer;

    @GetMapping("/")
    public String redirect(Model model) {
        return "redirect:/types";
    }

    @GetMapping("/types")
    public String getAllTypes(Model model) {
        LOGGER.debug("getAllTypes()");
        List<HardwareType> typesList = hardwareTypeConsumer.getAllTypes();
        model.addAttribute("typesList", typesList);
        return "types";
    }

    @GetMapping("/type/add")
    public String getTypeForm() {
        return "add_type";
    }

    @GetMapping("/type/edit")
    public String editType(@RequestParam("typeId") Integer typeId, Model model) {
        HardwareType hardwareType = hardwareTypeConsumer.getTypeById(typeId);
        model.addAttribute("hardwareType", hardwareType);
        return "edit_type";
    }

    @PostMapping("/type")
    public String addType(@ModelAttribute HardwareType hardwareType, Model model) {
        hardwareTypeConsumer.addType(hardwareType);
        List<HardwareType> typesList = hardwareTypeConsumer.getAllTypes();
        model.addAttribute("typesList", typesList);
        return "redirect:/types";
    }

    @PostMapping("/type/update")
    public String updateType(@ModelAttribute HardwareType hardwareType, Model model) {
        hardwareTypeConsumer.updateType(hardwareType);
        List<HardwareType> typesList = hardwareTypeConsumer.getAllTypes();
        model.addAttribute("typesList", typesList);
        return "redirect:/types";
    }

    @PostMapping("/type/delete")
    public String deleteType(@RequestParam("typeId") Integer typeId, Model model) {
        LOGGER.debug("deleteType({})", typeId);
        hardwareTypeConsumer.deleteType(typeId);
        return "redirect:/types";
    }
}
