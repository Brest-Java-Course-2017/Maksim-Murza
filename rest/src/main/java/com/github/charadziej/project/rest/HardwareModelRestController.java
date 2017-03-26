package com.github.charadziej.project.rest;

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

    @Autowired
    HardwareModelService hardwareModelService;

    //curl -v localhost:8088/models
    @RequestMapping(value = "/models", method = RequestMethod.GET)
    public @ResponseBody
    List<HardwareModel> getAllModels() {
        LOGGER.debug("getModels() in rest");
        return hardwareModelService.getAllModels();
    }

    //doesn't work
    //curl -H "Content-Type: application/json" -X POST -d '{"modelName":"AMD A10","modelType":"CPU","releaseDate":"03-03-2012"}' -v localhost:8088/model
    @RequestMapping(value = "/model", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Integer addModel(@RequestBody HardwareModel hardwareModel) {
        LOGGER.debug("addModel({})", hardwareModel);
        return hardwareModelService.addModel(hardwareModel);
    }
}
