package com.github.charadziej.project.rest;

import com.github.charadziej.project.dao.HardwareModel;
import com.github.charadziej.project.dao.HardwareType;
import com.github.charadziej.project.service.HardwareTypeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller for HardwareModel
 */
@CrossOrigin
@RestController
public class HardwareTypeRestController {

    private static final Logger LOGGER = LogManager.getLogger();

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler({IllegalArgumentException.class})
    public String incorrectDataError() {
        return "{  \"response\" : \"Incorrect Data Error\" }";
    }

    @Autowired
    HardwareTypeService hardwareTypeService;

    //curl -v localhost:8088/types
    @GetMapping("/types")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<HardwareType> getAllTypes() {
        LOGGER.debug("getAllTypes() in rest");
        return hardwareTypeService.getAllTypes();
    }

    //curl -v localhost:8088/type/id/1
    @GetMapping("/type/id/{typeId}")
    @ResponseStatus(HttpStatus.FOUND)
    public @ResponseBody HardwareType getTypeById(@PathVariable("typeId") Integer typeId) {
        LOGGER.debug("getTypeById({})", typeId);
        return hardwareTypeService.getTypeById(typeId);
    }

    //curl -v "localhost:8088/type/name?typeName=CPU"
    @GetMapping("/type/name")
    @ResponseStatus(HttpStatus.FOUND)
    public @ResponseBody HardwareType getTypeByName(@RequestParam("typeName") String typeName) {
        LOGGER.debug("getTypeByName({})", typeName);
        return hardwareTypeService.getTypeByName(typeName);
    }

    //curl -H "Content-Type: application/json" -X POST -d '{"typeName":"newType"}' -v localhost:8088/type
    @PostMapping("/type")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Integer addType(@RequestBody HardwareType hardwareType) {
        LOGGER.debug("addType({})", hardwareType);
        return hardwareTypeService.addType(hardwareType);
    }

    //curl -H "Content-Type: application/json" -X PUT -d '{"typeId":"6","typeName":"editedName"}' -v localhost:8088/type
    @PutMapping("/type")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public @ResponseBody Integer updateType(@RequestBody HardwareType hardwareType) {
        LOGGER.debug("updateType({})", hardwareType);
        return hardwareTypeService.updateType(hardwareType);
    }

    //curl -v -X DELETE localhost:8088/type/id/7
    @DeleteMapping("/type/id/{typeId}")
    @ResponseStatus(HttpStatus.OK)
    public Integer deleteType(@PathVariable("typeId") Integer typeId) {
        LOGGER.debug("deleteType({})", typeId);
        return hardwareTypeService.deleteType(typeId);
    }
}
