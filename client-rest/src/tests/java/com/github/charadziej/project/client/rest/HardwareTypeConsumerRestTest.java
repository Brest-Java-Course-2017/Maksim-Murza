package com.github.charadziej.project.client.rest;

import com.github.charadziej.project.client.rest.api.HardwareTypeConsumer;
import com.github.charadziej.project.dao.HardwareType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.List;

/**
 * Created by charadziej on 4/16/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-client.xml"})
public class HardwareTypeConsumerRestTest {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private HardwareTypeConsumer hardwareTypeConsumerRest;

    @Test
    public void getAllTypes() throws Exception {
        List<HardwareType> typesList = hardwareTypeConsumerRest.getAllTypes();
        LOGGER.debug("Returned list: {}", typesList);
        Assert.assertNotNull(typesList);
    }

    @Test
    public void getTypeById() throws Exception {
        HardwareType hardwareType = hardwareTypeConsumerRest.getTypeById(1);
        LOGGER.debug("Returned object: {}", hardwareType);
        Assert.assertEquals("CPU", hardwareType.getTypeName());
    }

    @Test
    public void getTypeByName() throws Exception {
        HardwareType hardwareType = hardwareTypeConsumerRest.getTypeByName("Keyboard");
        LOGGER.debug("Returned object: {}", hardwareType);
        Assert.assertEquals((Integer) 7, hardwareType.getTypeId());
    }

    @Test
    public void addType() throws Exception {
        int typesEquals = hardwareTypeConsumerRest.getAllTypes().size();
        Integer typeId = hardwareTypeConsumerRest.addType(new HardwareType("newType"));
        LOGGER.debug("Created object with id: {}", typeId);
        Assert.assertEquals((Integer) (typesEquals + 1), typeId);
    }

    @Test
    public void updateType() throws Exception {
        HardwareType updatedType = hardwareTypeConsumerRest.getTypeById(2);
        updatedType.setTypeName("newName");
        LOGGER.debug("updateType({})", updatedType);
        hardwareTypeConsumerRest.updateType(updatedType);
        Assert.assertEquals(updatedType, hardwareTypeConsumerRest.getTypeById(2));
    }

    @Test
    public void deleteType() throws Exception {
        Assert.assertNotNull(hardwareTypeConsumerRest.getTypeById(8));
        int i = hardwareTypeConsumerRest.deleteType(8);
        Assert.assertEquals(0, i);
    }
}