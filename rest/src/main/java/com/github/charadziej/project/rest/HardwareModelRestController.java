package com.github.charadziej.project.rest;

import com.fasterxml.jackson.databind.node.TextNode;
import com.github.charadziej.project.dao.HardwareModel;
import com.github.charadziej.project.service.HardwareModelService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by charadziej on 3/21/17.
 */
@CrossOrigin
@RestController
public class HardwareModelRestController {

    private static final Logger LOGGER = LogManager.getLogger();

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler({IllegalArgumentException.class})
    public String incorrectDataError() {
        return "{  \"response\" : \"Incorrect Data Error\" }";
    }

    @Autowired
    HardwareModelService hardwareModelService;

    //curl -v localhost:8088/models
    @GetMapping("/models")
    public @ResponseBody
    List<HardwareModel> getAllModels() {
        LOGGER.debug("getModels() in rest");
        return hardwareModelService.getAllModels();
    }

    //curl -v localhost:8088/models/id/1
    @GetMapping("/models/id/{modelId}")
    @ResponseStatus(value = HttpStatus.FOUND)
    public @ResponseBody HardwareModel getModelById(@PathVariable(value = "modelId") Integer modelId) {
        LOGGER.debug("getModelById({})", modelId);
        return hardwareModelService.getModelById(modelId);
    }

    //curl -H "Content-Type: application/json" -X GET -d '{"modelName":"AMD FX-8350 Vishera"}' -v localhost:8088/models/name
    /*@GetMapping("/models/name")
    @ResponseStatus(value = HttpStatus.FOUND)
    public @ResponseBody HardwareModel getModelByName(@RequestParam("modelName") String modelName) {
        LOGGER.debug("getModelByName({})", modelName);
        return hardwareModelService.getModelByName(modelName);
    }*/

    //curl -H "Content-Type: application/json" -X POST -d '{"modelName":"testName","modelType":"CPU","releaseDate":"2012-03-03"}' -v localhost:8088/add
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Integer addModel(@RequestBody HardwareModel hardwareModel) {
        LOGGER.debug("addModel({})", hardwareModel);
        return hardwareModelService.addModel(hardwareModel);
    }
}
