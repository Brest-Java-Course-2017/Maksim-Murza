package com.github.charadziej.project.client.rest;

import com.github.charadziej.project.client.rest.api.HardwareModelConsumer;
import com.github.charadziej.project.dao.HardwareModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by charadziej on 4/17/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-client.xml"})
public class HardwareModelConsumerRestTest {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private HardwareModelConsumer hardwareModelConsumerRest;

    @Test
    public void getAllModels() throws Exception {
        List<HardwareModel> modelsList = hardwareModelConsumerRest.getAllModels();
        LOGGER.debug("Returned list: {}", modelsList);
        Assert.assertNotNull(modelsList);
    }

    @Test
    public void getModelById() throws Exception {
        HardwareModel hardwareModel = hardwareModelConsumerRest.getModelById(4);
        LOGGER.debug("Returned object: {}", hardwareModel);
        Assert.assertEquals("AMD Athlon X4 860K Kaveri", hardwareModel.getModelName());
    }

    @Test
    public void getModelByName() throws Exception {
        HardwareModel hardwareModel =
                hardwareModelConsumerRest.getModelByName("AMD Radeon RX 480");
        LOGGER.debug("Returned object: {}", hardwareModel);
        Assert.assertEquals((Integer) 14, hardwareModel.getModelId());
    }

    @Test
    public void addModel() throws Exception {
        int modelsEquals = hardwareModelConsumerRest.getAllModels().size();
        HardwareModel newModel = new HardwareModel("newModel",
                "CPU", FORMATTER.parse("2012-11-03"));
        Integer modelId = hardwareModelConsumerRest.addModel(newModel);
        LOGGER.debug("Created object with id: {}", modelId);
        Assert.assertEquals((Integer) (modelsEquals + 1), modelId);
        Assert.assertEquals(newModel.getReleaseDate(),
                hardwareModelConsumerRest.getModelById(modelId).getReleaseDate());
    }

    @Test
    public void updateModel() throws Exception {
        HardwareModel updatedModel = hardwareModelConsumerRest.getModelById(2);
        updatedModel.setModelName("newName");
        updatedModel.setReleaseDate(FORMATTER.parse("2014-01-01"));
        LOGGER.debug("updateModel({})", updatedModel);
        hardwareModelConsumerRest.updateModel(updatedModel);
        Assert.assertEquals(updatedModel, hardwareModelConsumerRest.getModelById(2));
    }

    @Test
    public void deleteModel() throws Exception {
        Assert.assertNotNull(hardwareModelConsumerRest.getModelById(8));
        int i = hardwareModelConsumerRest.deleteModel(8);
        Assert.assertEquals(0, i);
    }

    @Test
    public void getModelsByPeriod() throws Exception {
        Date begin = FORMATTER.parse("2010-05-05");
        Date end = FORMATTER.parse("2014-05-05");
        List<HardwareModel> modelsList = hardwareModelConsumerRest.getModelsByPeriod(begin, end);
        LOGGER.debug("Returned list: {}", modelsList);
        Assert.assertNotNull(modelsList);
    }
}