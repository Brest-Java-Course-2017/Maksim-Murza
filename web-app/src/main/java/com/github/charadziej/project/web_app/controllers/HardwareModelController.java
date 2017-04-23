package com.github.charadziej.project.web_app.controllers;

import com.github.charadziej.project.client.rest.api.HardwareModelConsumer;
import com.github.charadziej.project.client.rest.api.HardwareTypeConsumer;
import com.github.charadziej.project.dao.HardwareModel;
import com.github.charadziej.project.dao.HardwareType;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by charadziej on 4/16/17.
 */

@Controller
public class HardwareModelController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    HardwareModelConsumer hardwareModelConsumer;

    @Autowired
    HardwareTypeConsumer hardwareTypeConsumer;

    @GetMapping("/models")
    public String getAllModels(Model model) {
        LOGGER.debug("getAllModels()");
        List<HardwareModel> modelsList = hardwareModelConsumer.getAllModels();
        model.addAttribute("modelsList", modelsList);
        return "models";
    }

    @GetMapping("/model/add")
    public String getModelForm(Model model) {
        List<HardwareType> typesList = hardwareTypeConsumer.getAllTypes();
        model.addAttribute("typesList", typesList);
        return "add_model";
    }

    @PostMapping("/model")
    public String addModel(@RequestParam("modelName") String modelName,
                           @RequestParam("modelType") String modelType,
                           @RequestParam("releaseDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date releaseDate,
                           Model model) {
        hardwareModelConsumer.addModel(new HardwareModel(modelName, modelType, releaseDate));
        List<HardwareModel> modelsList = hardwareModelConsumer.getAllModels();
        model.addAttribute("modelsList", modelsList);
        return "models";
    }

    @GetMapping("/model/edit")
    public String editModel(@RequestParam("modelId") Integer modelId, Model model) {
        HardwareModel hardwareModel = hardwareModelConsumer.getModelById(modelId);
        List<HardwareType> typesList = hardwareTypeConsumer.getAllTypes();
        model.addAttribute("hardwareModel", hardwareModel);
        model.addAttribute("typesList", typesList);
        return "edit_model";
    }

    @PostMapping("/model/update")
    public String updateModel(@RequestParam("modelId") Integer modelId,
                              @RequestParam("modelName") String modelName,
                              @RequestParam("modelType") String modelType,
                              @RequestParam("releaseDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date releaseDate,
                              Model model) {
        hardwareModelConsumer.updateModel(new HardwareModel(modelId, modelName, modelType, releaseDate));
        List<HardwareModel> modelsList = hardwareModelConsumer.getAllModels();
        model.addAttribute("modelsList", modelsList);
        return "models";
    }

    @PostMapping("/model/delete")
    public String deleteModel(@RequestParam("modelId") Integer modelId, Model model) {
        LOGGER.debug("deleteModel({})", modelId);
        hardwareModelConsumer.deleteModel(modelId);
        return "redirect:/models";
    }

    @PostMapping("/models/period")
    public String getModelsByPeriod(@RequestParam(value = "begin", required = false)
                                        @DateTimeFormat(pattern = "yyyy-MM-dd") Date begin,
                                    @RequestParam(value = "end", required = false)
                                        @DateTimeFormat(pattern = "yyyy-MM-dd") Date end, Model model) {
        List<HardwareModel> modelsList;

       /* if(begin == null && end == null)
            return "redirect:/models";
        else*/ modelsList = hardwareModelConsumer.getModelsByPeriod(begin, end);

        model.addAttribute("modelsList", modelsList);
        return "models";
    }
}
