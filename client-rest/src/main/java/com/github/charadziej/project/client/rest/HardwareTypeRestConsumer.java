package com.github.charadziej.project.client.rest;

import com.github.charadziej.project.client.ServerDataAccessException;
import com.github.charadziej.project.client.rest.api.HardwareTypeConsumer;
import com.github.charadziej.project.dao.HardwareType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Class which receives data from rest
 */
public class HardwareTypeRestConsumer implements HardwareTypeConsumer{

    private static final Logger LOGGER = LogManager.getLogger();

    private String url;
    private String typesUrl;
    private String typeUrl;

    ClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
    RestTemplate restTemplate = new RestTemplate(requestFactory);
    {
        restTemplate.setErrorHandler(new CustomResponseErrorHandler());
    }

    public HardwareTypeRestConsumer(String url, String typesUrl, String typeUrl) {
        this.url = url;
        this.typesUrl = typesUrl;
        this.typeUrl = typeUrl;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<HardwareType> getAllTypes() throws ServerDataAccessException {
        LOGGER.debug("getAllTypes()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url + typesUrl, List.class);
        List<HardwareType> hardwareTypes = (List<HardwareType>) responseEntity.getBody();
        return hardwareTypes;
    }

    @Override
    public HardwareType getTypeById(Integer typeId) throws ServerDataAccessException {
        LOGGER.debug("getTypeById(), {}", url + typeUrl + "/id/" + typeId);
        ResponseEntity responseEntity = restTemplate.getForEntity(url + typeUrl + "/id/" + typeId,
                HardwareType.class);
        HardwareType hardwareType = (HardwareType) responseEntity.getBody();
        return hardwareType;
    }

    @Override
    public HardwareType getTypeByName(String typeName) throws ServerDataAccessException {
        LOGGER.debug("getTypeByName({})", typeName);
        typeName.replace(" ", "+");
        ResponseEntity responseEntity = restTemplate.getForEntity(url + typeUrl + "/name?typeName=" + typeName,
                HardwareType.class);
        HardwareType hardwareType = (HardwareType) responseEntity.getBody();
        return hardwareType;
    }

    @Override
    public int addType(HardwareType hardwareType) throws ServerDataAccessException {
        LOGGER.debug("addType({})", hardwareType);
        ResponseEntity responseEntity = restTemplate.postForEntity(url + typeUrl, hardwareType,
                Integer.class);
        Integer addedTypeId = (Integer) responseEntity.getBody();
        return addedTypeId;
    }

    @Override
    public int updateType(HardwareType hardwareType) throws ServerDataAccessException {
        LOGGER.debug("updateType({})", hardwareType);
        restTemplate.put(url + typeUrl,  hardwareType);
        return 0;
    }

    @Override
    public int deleteType(Integer typeId) throws ServerDataAccessException {
        LOGGER.debug("deleteType({})", typeId);
        restTemplate.delete(url + typeUrl + "/id/" + typeId);
        return 0;
    }
}
