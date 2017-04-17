package com.github.charadziej.project.client.rest;

import com.github.charadziej.project.client.ServerDataAccessException;
import com.github.charadziej.project.client.rest.api.HardwareModelConsumer;
import com.github.charadziej.project.dao.HardwareModel;
import com.github.charadziej.project.dao.HardwareType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by charadziej on 4/13/17.
 */
public class HardwareModelConsumerRest implements HardwareModelConsumer {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    private String url;
    private String modelsUrl;
    private String modelUrl;

    @Autowired
    RestTemplate restTemplate;

    public HardwareModelConsumerRest(String url, String modelsUrl, String modelUrl) {
        this.url = url;
        this.modelsUrl = modelsUrl;
        this.modelUrl = modelUrl;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<HardwareModel> getAllModels() throws ServerDataAccessException {
        LOGGER.debug("getAllModels()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url + modelsUrl, List.class);
        List<HardwareModel> hardwareModels = (List<HardwareModel>) responseEntity.getBody();
        return hardwareModels;
    }

    @Override
    public HardwareModel getModelById(Integer modelId) throws ServerDataAccessException {
        LOGGER.debug("getModelById(), {}", url + modelUrl + "/id/" + modelId);
        ResponseEntity responseEntity = restTemplate.getForEntity(url + modelUrl + "/id/" + modelId,
                HardwareModel.class);
        HardwareModel hardwareModel = (HardwareModel) responseEntity.getBody();
        return hardwareModel;
    }

    @Override
    public HardwareModel getModelByName(String modelName) throws ServerDataAccessException {
        LOGGER.debug("getModelByName({})", modelName);
        modelName.replace(" ", "+");
        ResponseEntity responseEntity = restTemplate.getForEntity(url + modelUrl + "/name?modelName=" + modelName,
                HardwareModel.class);
        HardwareModel hardwareModel = (HardwareModel) responseEntity.getBody();
        return hardwareModel;
    }

    @Override
    public int addModel(HardwareModel hardwareModel) throws ServerDataAccessException {
        LOGGER.debug("addModel({})", hardwareModel);
        ResponseEntity responseEntity = restTemplate.postForEntity(url + modelUrl, hardwareModel,
                Integer.class);
        Integer addedModelId = (Integer) responseEntity.getBody();
        return addedModelId;
    }

    @Override
    public int updateModel(HardwareModel hardwareModel) throws ServerDataAccessException {
        LOGGER.debug("updateModel({})", hardwareModel);
        restTemplate.put(url + modelUrl,  hardwareModel);
        return 0;
    }

    @Override
    public int deleteModel(Integer modelId) throws ServerDataAccessException {
        LOGGER.debug("deleteModel({})", modelId);
        restTemplate.delete(url + modelUrl + "/id/" + modelId);
        return 0;
    }

    @Override
    public List<HardwareModel> getModelsByPeriod(Date begin, Date end) throws ServerDataAccessException {
        LOGGER.debug("logger getModelsByPeriod({},{})", FORMATTER.format(begin), FORMATTER.format(end));
        ResponseEntity responseEntity = restTemplate.getForEntity(url + modelsUrl +
                "/period?begin=" +  (begin == null ? "%00" : FORMATTER.format(begin)) + "&end=" +
                (end == null ? "%00" : FORMATTER.format(end)), List.class);
        List<HardwareModel> hardwareModels = (List<HardwareModel>) responseEntity.getBody();
        return hardwareModels;
    }
}
