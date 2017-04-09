package com.github.charadziej.project.rest;

import com.fasterxml.jackson.databind.node.TextNode;
import com.github.charadziej.project.dao.HardwareModel;
import com.github.charadziej.project.service.HardwareModelService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Rest controller for HardwareModel
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
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<HardwareModel> getAllModels() {
        LOGGER.debug("getModels() in rest");
        return hardwareModelService.getAllModels();
    }

    //curl -v localhost:8088/model/id/1
    @GetMapping("/model/id/{modelId}")
    @ResponseStatus(HttpStatus.FOUND)
    public @ResponseBody HardwareModel getModelById(@PathVariable("modelId") Integer modelId) {
        LOGGER.debug("getModelById({})", modelId);
        return hardwareModelService.getModelById(modelId);
    }

    @RequestMapping("/model/name")
    @ResponseStatus(HttpStatus.FOUND)
    public HardwareModel getModelByName(@RequestParam("modelName") String modelName) {
        LOGGER.debug("getModelByName({})", modelName);
        return hardwareModelService.getModelByName(modelName);
    }

    //curl -H "Content-Type: application/json" -X POST -d '{"modelId":null,"modelName":"TestName","modelType":"ModelType","releaseDate":{"year":2014,"month":"MARCH","dayOfMonth":3,"dayOfWeek":"MONDAY","era":"CE","dayOfYear":62,"leapYear":false,"monthValue":3,"chronology":{"calendarType":"iso8601","id":"ISO"}}}' -v localhost:8088/model
    @PostMapping("/model")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Integer addModel(@RequestBody HardwareModel hardwareModel) {
        LOGGER.debug("addModel({})", hardwareModel);
        return hardwareModelService.addModel(hardwareModel);
    }

    //curl -H "Content-Type: application/json" -X PUT -d '{"modelId":"2","modelName":"editedName","modelType":"CPU","releaseDate":"2012-03-03"}' -v localhost:8088/model
    @PutMapping("/model")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public @ResponseBody Integer updateModel(@RequestBody HardwareModel hardwareModel) {
        LOGGER.debug("updateModel({})", hardwareModel);
        return hardwareModelService.updateModel(hardwareModel);
    }

    //curl -v -X DELETE localhost:8088/model/25
    @DeleteMapping("/model/id/{modelId}")
    @ResponseStatus(HttpStatus.OK)
    public Integer deleteModel(@PathVariable("modelId") Integer modelId) {
        LOGGER.debug("deleteModel({})", modelId);
        return hardwareModelService.deleteModel(modelId);
    }

    @GetMapping("/models/period")
    @ResponseStatus(HttpStatus.FOUND)
    public @ResponseBody List<HardwareModel> getModelsByPeriod(@RequestParam("begin") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                                               @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        LOGGER.debug("getModelsByPeriod({},{})", begin, end);
        return hardwareModelService.getModelsByPeriod(begin, end);
    }
}
