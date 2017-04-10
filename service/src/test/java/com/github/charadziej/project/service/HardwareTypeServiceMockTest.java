package com.github.charadziej.project.service;

import com.github.charadziej.project.dao.HardwareModel;
import com.github.charadziej.project.dao.HardwareModelDao;
import com.github.charadziej.project.dao.HardwareType;
import com.github.charadziej.project.dao.HardwareTypeDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;

/**
 * Mock tests for HardwareTypeService class
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-service-mock.xml"})
public class HardwareTypeServiceMockTest {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    HardwareType newType = new HardwareType("CPU");

    @Autowired
    HardwareTypeService hardwareTypeService;

    @Autowired
    HardwareTypeDao mockHardwareTypeDao;

    @Autowired
    HardwareModelDao mockHardwareModelDao;

    @After
    public void tearDown() {
        verify(mockHardwareModelDao, mockHardwareTypeDao);
        reset(mockHardwareModelDao, mockHardwareTypeDao);
    }

    @Test
    public void getTypeById() throws Exception {
        expect(mockHardwareTypeDao.getTypeById(6)).andReturn(newType);
        replay(mockHardwareModelDao, mockHardwareTypeDao);
        Assert.assertEquals(newType, hardwareTypeService.getTypeById(6));
    }

    @Test
    public void getTypeByName() throws Exception {
        final String TYPE_NAME = "TypeName";
        expect(mockHardwareTypeDao.getTypeByName(TYPE_NAME)).andReturn(newType);
        replay(mockHardwareModelDao, mockHardwareTypeDao);
        Assert.assertEquals(newType, hardwareTypeService.getTypeByName(TYPE_NAME));
    }

    @Test
    public void addType() throws Exception {
        final Integer TYPE_ID = 5;
        expect(mockHardwareTypeDao.getTypeByName(newType.getTypeName())).andReturn(null);
        expect(mockHardwareTypeDao.addType(newType)).andReturn(TYPE_ID);
        replay(mockHardwareModelDao, mockHardwareTypeDao);
        Assert.assertEquals(TYPE_ID, (Integer) hardwareTypeService.addType(newType));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addTypeWithDuplicateName() throws Exception {
        HardwareType type = newType;
        expect(mockHardwareTypeDao.getTypeByName(newType.getTypeName())).andReturn(type);
        replay(mockHardwareModelDao, mockHardwareTypeDao);
        hardwareTypeService.addType(newType);
    }

    @Test
    public void updateType() throws Exception {
        newType.setTypeId(5);
        HardwareType type = newType;
        expect(mockHardwareTypeDao.getTypeById(newType.getTypeId())).andReturn(type);
        expect(mockHardwareTypeDao.getTypeByName(newType.getTypeName())).andReturn(null);
        expect(mockHardwareTypeDao.updateType(newType)).andReturn(1);
        replay(mockHardwareModelDao, mockHardwareTypeDao);
        Assert.assertEquals(1, hardwareTypeService.updateType(newType));
    }

    @Test
    public void deleteType() throws Exception {
        HardwareType type = newType;
        type.setTypeId(5);
        HardwareModel model1 = new HardwareModel(), model2 = new HardwareModel();
        List<HardwareModel> list = new ArrayList<>();
        list.add(model1);
        list.add(model2);
        expect(mockHardwareTypeDao.getTypeById(type.getTypeId())).andReturn(type).times(2);
        expect(mockHardwareModelDao.getAllModels()).andReturn(list);
        expect(mockHardwareTypeDao.deleteType(type.getTypeId())).andReturn(1);
        replay(mockHardwareModelDao, mockHardwareTypeDao);
        Assert.assertEquals(1, hardwareTypeService.deleteType(type.getTypeId()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteTypeWithModels() throws Exception {
        HardwareType type = newType;
        type.setTypeId(5);
        HardwareModel model1 = new HardwareModel("Name","CPU", FORMATTER.parse("2014-09-08")),
                model2 = new HardwareModel();
        List<HardwareModel> list = new ArrayList<>();
        list.add(model1);
        list.add(model2);
        expect(mockHardwareTypeDao.getTypeById(type.getTypeId())).andReturn(type).times(2);
        expect(mockHardwareModelDao.getAllModels()).andReturn(list);
        replay(mockHardwareModelDao, mockHardwareTypeDao);
        hardwareTypeService.deleteType(type.getTypeId());
    }
}
