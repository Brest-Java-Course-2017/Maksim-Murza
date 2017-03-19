package com.github.charadziej.project.service;

import com.github.charadziej.project.dao.HardwareModel;
import com.github.charadziej.project.dao.HardwareType;
import com.github.charadziej.project.dao.HardwareTypeDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by charadziej on 3/18/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-service.xml"})
@Transactional
public class HardwareTypeServiceImplTest {

    private static final Logger LOGGER = LogManager.getLogger();

    private final Integer TYPE_ID = 4;
    private final String TYPE_NAME = "CPU";
    private final String NEW_TYPE_NAME = "Random access memory";
    HardwareType newType = new HardwareType("Cooler");

    @Autowired
    HardwareTypeService hardwareTypeService;

    @Autowired
    HardwareTypeDao hardwareTypeDao;

    @Test
    public void getAllTypes() throws Exception {
        List<HardwareType> typesList = hardwareTypeService.getAllTypes();
        LOGGER.debug("test getAllTypes() in service; Returned list: {}", typesList);
        assertEquals(hardwareTypeDao.getTypesQuantity(), typesList.size());
    }

    @Test
    public void getTypeById() throws Exception {
        HardwareType type = hardwareTypeService.getTypeById(TYPE_ID);
        LOGGER.debug("test getModelById() in service; Returned object: {}", type);
        assertEquals(TYPE_ID, type.getTypeId());
    }

    @Test
    public void getTypeByNotExistedId() throws Exception {
        LOGGER.debug("test getTypeByNotExistedId()");
        HardwareType type = hardwareTypeService.getTypeById(200);
        Assert.assertNull(type);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTypeByNullId() throws Exception {
        LOGGER.debug("test getTypeByNullId() in service");
        HardwareType type = hardwareTypeService.getTypeById(null);
        Assert.assertNull(type);
    }

    @Test
    public void getTypeByName() throws Exception {
        HardwareType type = hardwareTypeService.getTypeByName(TYPE_NAME);
        LOGGER.debug("test getTypeByName() in service; Returned object: {}", type);
        assertEquals(TYPE_NAME, type.getTypeName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTypeByNullName() throws Exception {
        LOGGER.debug("test getTypeByNullName() in service");
        HardwareType type = hardwareTypeService.getTypeByName("");
        Assert.assertNull(type);
    }

    @Test
    public void getTypeByNotExistedName() throws Exception {
        LOGGER.debug("test getTypeByNotExistedName() in service");
        HardwareType type = hardwareTypeService.getTypeByName("Some Name");
        Assert.assertNull(type);
    }

    @Test
    public void addType() throws Exception {
        LOGGER.debug("test addType() in service");
        Integer key = hardwareTypeService.addType(newType);
        Assert.assertEquals(newType.getTypeName(), hardwareTypeService.getTypeById(key).getTypeName());

    }

    @Test(expected = IllegalArgumentException.class)
    public void addTypeWithoutName() throws Exception {
        LOGGER.debug("test addTypeWithoutName() in service");
        hardwareTypeService.addType(new HardwareType(""));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addTypeWithDuplicateName() throws Exception {
        LOGGER.debug("test addTypeWithDuplicateName() in service");
        hardwareTypeService.addType(new HardwareType("CPU"));
    }

    @Test
    public void updateType() throws Exception {
        LOGGER.debug("test updateType() in service");
        HardwareType type = new HardwareType("typeName");
        type.setTypeId(1);
        Integer rowsEffectedQuantity = hardwareTypeService.updateType(type);

        LOGGER.debug("test updateType() in service, updated object:", hardwareTypeService.getTypeById(1));
        Assert.assertEquals(type.getTypeName(), hardwareTypeService.getTypeById(1).getTypeName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateNotExistedType() throws Exception {
        LOGGER.debug("test updateNotExistedType() in service");
        HardwareType type = new HardwareType("typeName");
        type.setTypeId(200);
        Integer rowsEffectedQuantity = hardwareTypeService.updateType(type);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateNullType() throws Exception {
        LOGGER.debug("test updateNullType() in service");
        hardwareTypeService.addType(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateTypeWithDuplicateName() throws Exception {
        LOGGER.debug("test updateTypeWithDuplicateName() in service");
        HardwareType type = new HardwareType("CPU");
        type.setTypeId(2);
        Integer rowsEffectedQuantity = hardwareTypeService.updateType(type);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateTypeWithoutName() throws Exception {
        LOGGER.debug("test updateTypeWithoutName() in service");
        HardwareType type = new HardwareType();
        type.setTypeId(2);
        Integer rowsEffectedQuantity = hardwareTypeService.updateType(type);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateTypeWithoutId() throws Exception {
        LOGGER.debug("test updateTypeWithoutId() in service");
        Integer rowsEffectedQuantity = hardwareTypeService.updateType(new HardwareType("typeName"));
    }

    @Test
    public void deleteType() throws Exception {
        LOGGER.debug("test deleteType() in service");
        Integer key = hardwareTypeService.addType(newType);
        System.out.println(key);
        Assert.assertEquals(newType.getTypeName(), hardwareTypeService.getTypeById(key).getTypeName());
        Integer rowsEffectedQuantity = hardwareTypeService.deleteType(key);

        Assert.assertEquals((Integer) 1, rowsEffectedQuantity);
        Assert.assertNull(hardwareTypeService.getTypeById(key));

    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteTypeWithoutId() throws Exception {
        LOGGER.debug("test deleteTypeWithoutId() in service");
        Integer rowsEffectedQuantity = hardwareTypeService.deleteType(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteNotExistedType() throws Exception {
        LOGGER.debug("test deleteNotExistedType() in service");
        Integer rowsEffectedQuantity = hardwareTypeService.deleteType(200);
    }

}