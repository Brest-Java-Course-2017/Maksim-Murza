package com.github.charadziej.project.client.rest;

import com.github.charadziej.project.client.ServerDataAccessException;
import com.github.charadziej.project.client.rest.api.HardwareModelConsumer;
import com.github.charadziej.project.dao.HardwareModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Class which receives data from rest
 */
public class HardwareModelRestConsumer implements HardwareModelConsumer {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    private String url;
    private String modelsUrl;
    private String modelUrl;

    ClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
    RestTemplate restTemplate = new RestTemplate(requestFactory);
    {
        restTemplate.setErrorHandler(new CustomResponseErrorHandler());
    }

    public HardwareModelRestConsumer(String url, String modelsUrl, String modelUrl) {
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
        Integer addedModelId = restTemplate.postForObject(url + modelUrl, hardwareModel,
                Integer.class);
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
    public List<HardwareModel> getModelsByPeriod(Date begin, Date end)
            throws ServerDataAccessException {

        String beginStr = (begin != null ? "begin=" + FORMATTER.format(begin) + "&" : "");
        String endStr = (end != null ? "end=" + FORMATTER.format(end) : "");

        LOGGER.debug("logger getModelsByPeriod({},{})", begin, end);
        ResponseEntity responseEntity = restTemplate.getForEntity(url + modelsUrl +
                "/period?" + beginStr + endStr, List.class);
        List<HardwareModel> hardwareModels = (List<HardwareModel>) responseEntity.getBody();
        return hardwareModels;
    }
}
