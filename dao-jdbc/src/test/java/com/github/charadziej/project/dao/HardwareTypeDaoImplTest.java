package com.github.charadziej.project.dao;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests for HardwareTypeDaoImpl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-dao.xml"})
@Transactional
public class HardwareTypeDaoImplTest {

    private final Integer TYPE_ID = 3;
    private final String TYPE_NAME = "CPU";
    HardwareType newType = new HardwareType(6, "Cooler");

    @Autowired
    HardwareTypeDao hardwareTypeDao;

    @Test
    public void getAllTypes() throws Exception {
        List<HardwareType> typesList = hardwareTypeDao.getAllTypes();
        Assert.assertEquals("Check quantity of types",5,typesList.size());
        Assert.assertEquals("Check type's name","Motherboard",typesList.get(2).getTypeName());
        Assert.assertEquals("Check type's quantity",(Integer) 2,typesList.get(0).getQuantity());
    }

    @Test
    public void getTypeById() throws Exception {

    }

    @Test
    public void getTypeByName() throws Exception {

    }

    @Test
    public void addType() throws Exception {

    }

    @Test
    public void updateType() throws Exception {

    }

    @Test
    public void deleteType() throws Exception {

    }

}