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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Rest controller for HardwareModel
 */
@CrossOrigin
@RestController
public class HardwareModelRestController {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

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

    //curl -v "localhost:8088/model/name?modelName=Intel+Pentium+G4560+Kabylake"
    @RequestMapping("/model/name")
    @ResponseStatus(HttpStatus.FOUND)
    public HardwareModel getModelByName(@RequestParam("modelName") String modelName) {
        LOGGER.debug("getModelByName({})", modelName);
        return hardwareModelService.getModelByName(modelName);
    }

    //curl -H "Content-Type: application/json" -X POST -d '{"modelId":null,"modelName":"TestName","modelType":"CPU","releaseDate":"2014-09-03"}' -v localhost:8088/model
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

    //curl -v -X DELETE localhost:8088/model/id/25
    @DeleteMapping("/model/id/{modelId}")
    @ResponseStatus(HttpStatus.OK)
    public Integer deleteModel(@PathVariable("modelId") Integer modelId) {
        LOGGER.debug("deleteModel({})", modelId);
        return hardwareModelService.deleteModel(modelId);
    }

    //curl -v localhost:8088/models/2014-05-05/2016-05-05
    @GetMapping("/models/{begin}/{end}")
    @ResponseStatus(HttpStatus.FOUND)
    public @ResponseBody List<HardwareModel> getModelsByPeriod(@PathVariable("begin") @DateTimeFormat(pattern = "yyyy-MM-dd") String begin,
                                                               @PathVariable("end") @DateTimeFormat(pattern = "yyyy-MM-dd") String end) throws ParseException {
        LOGGER.debug("getModelsByPeriod({},{})", begin, end);
        Date beginDate = FORMATTER.parse(begin);
        Date endDate = FORMATTER.parse(end);
        return hardwareModelService.getModelsByPeriod(beginDate, endDate);
    }
}
