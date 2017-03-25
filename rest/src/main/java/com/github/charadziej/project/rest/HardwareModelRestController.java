package com.github.charadziej.project.rest;

import com.github.charadziej.project.dao.HardwareModel;
import com.github.charadziej.project.service.HardwareModelService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
}
