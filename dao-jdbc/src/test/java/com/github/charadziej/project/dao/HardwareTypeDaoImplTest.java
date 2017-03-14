package com.github.charadziej.project.dao;

import com.sun.org.apache.xpath.internal.SourceTree;
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

    private final Integer TYPE_ID = 4;
    private final String TYPE_NAME = "CPU";
    private final String NEW_TYPE_NAME = "Random access memory";
    HardwareType newType = new HardwareType("Cooler");

    @Autowired
    HardwareTypeDao hardwareTypeDao;

    @Test
    public void getAllTypes() throws Exception {
        List<HardwareType> typesList = hardwareTypeDao.getAllTypes();
        Assert.assertTrue("Check quantity of types", typesList.size() > 0);
        Assert.assertEquals("Check type's name","Motherboard",typesList.get(2).getTypeName());
        Assert.assertEquals("Check type's quantity",(Integer) 2,typesList.get(0).getQuantity());
    }

    @Test
    public void getTypeById() throws Exception {
        HardwareType type = hardwareTypeDao.getTypeById(TYPE_ID);
        Assert.assertEquals("Check id", TYPE_ID, type.getTypeId());
    }

    @Test
    public void getTypeByName() throws Exception {
        HardwareType type = hardwareTypeDao.getTypeByName(TYPE_NAME);
        Assert.assertEquals("Check id", TYPE_NAME, type.getTypeName());
    }

    @Test
    public void addType() throws Exception {
        Integer quantityBefore = hardwareTypeDao.getAllTypes().size();
        Integer key = hardwareTypeDao.addType(newType);

        Assert.assertEquals(quantityBefore + 1, hardwareTypeDao.getAllTypes().size());
        Assert.assertNotNull(hardwareTypeDao.getTypeById(quantityBefore + 1));
        Assert.assertEquals("Check new type's name", newType.getTypeName(),
                hardwareTypeDao.getTypeById(key).getTypeName());
    }

    @Test
    public void updateType() throws Exception {
        HardwareType type = hardwareTypeDao.getTypeById(4);
        type.setTypeName(NEW_TYPE_NAME);
        Integer effectedRowQuantity= hardwareTypeDao.updateType(type);

        Assert.assertEquals((Integer) 1, effectedRowQuantity);
        Assert.assertEquals(type, hardwareTypeDao.getTypeByName(NEW_TYPE_NAME));
    }

    @Test
    public void deleteType() throws Exception {
        Integer quantityBefore = hardwareTypeDao.getAllTypes().size();
        Integer key = hardwareTypeDao.addType(newType);

        Assert.assertNotNull(hardwareTypeDao.getTypeById(key));
        Assert.assertEquals("Check new type's name", newType.getTypeName(),
                hardwareTypeDao.getTypeById(key).getTypeName());

        hardwareTypeDao.deleteType(key);

        Assert.assertEquals(quantityBefore, (Integer) hardwareTypeDao.getAllTypes().size());
        Assert.assertNull(hardwareTypeDao.getTypeById(key));
    }

}