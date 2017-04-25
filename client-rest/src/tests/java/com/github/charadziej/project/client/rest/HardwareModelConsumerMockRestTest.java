package com.github.charadziej.project.client.rest;

import com.github.charadziej.project.client.rest.api.HardwareModelConsumer;
import com.github.charadziej.project.dao.HardwareModel;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.junit.Assert.*;

/**
 * Created by charadziej on 4/15/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-client-mock.xml"})
public class HardwareModelConsumerMockRestTest {

    private static final HardwareModel model1 = new HardwareModel("modelName1", "CPU");

    @Autowired
    private HardwareModelConsumer hardwareModelRestConsumer;

    @Value("${protocol}://${host}:${port}")
    private String url;

    @Value("${models}")
    private String modelsUrl;

    @Value("${model}")
    private String modelUrl;

    @Autowired
    private RestTemplate mockRestTemplate;

    @After
    public void tearDown() throws Exception {
        reset(mockRestTemplate);
    }

    @Test
    public void getAllModels() throws Exception {
        List<HardwareModel> expectedList = new ArrayList(2);
        expectedList.add(model1);
        expectedList.add(new HardwareModel("modelName2", "CPU"));
        expect(mockRestTemplate.getForEntity( url + modelsUrl, List.class))
                .andReturn(new ResponseEntity<>(expectedList, HttpStatus.OK));
        replay(mockRestTemplate);
        List<HardwareModel> list = hardwareModelRestConsumer.getAllModels();
        assertEquals(2, list.size());
    }

    @Test
    public void getModelById() throws Exception {
        Integer modelId = 1;
        expect(mockRestTemplate.getForEntity(url + modelUrl + "/id/" + modelId,
                HardwareModel.class)).andReturn(new ResponseEntity<>(model1, HttpStatus.FOUND));
        replay(mockRestTemplate);
        HardwareModel hardwareType = hardwareModelRestConsumer.getModelById(1);
        assertEquals(hardwareType, model1);
    }

    @Test
    public void getModelByName() throws Exception {
        expect(mockRestTemplate.getForEntity(url + modelUrl + "/name?modelName=" + model1.getModelName(),
                HardwareModel.class))
                .andReturn(new ResponseEntity<>(model1, HttpStatus.FOUND));
        replay(mockRestTemplate);
        HardwareModel hardwareModel = hardwareModelRestConsumer.getModelByName("modelName1");
        assertEquals("CPU", hardwareModel.getModelType());
    }

    @Test
    public void addModel() throws Exception {
        Integer id = 2;
        expect(mockRestTemplate.postForEntity(url + modelUrl, model1, Integer.class))
                .andReturn(new ResponseEntity<>(id, HttpStatus.CREATED));
        replay(mockRestTemplate);
        Integer newModelId = hardwareModelRestConsumer.addModel(model1);
        assertEquals(id, newModelId);
    }
}