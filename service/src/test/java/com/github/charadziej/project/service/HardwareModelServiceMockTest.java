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

import java.time.LocalDate;
import java.util.List;

import static org.easymock.EasyMock.*;

/**
 * Created by charadziej on 3/20/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-service-mock.xml"})
public class HardwareModelServiceMockTest {

    private static final Logger LOGGER = LogManager.getLogger();

    HardwareModel newModel = new HardwareModel("Intel", "CPU",
            LocalDate.parse("2012-11-03"));

    @Autowired
    HardwareModelService hardwareModelService;

    @Autowired
    HardwareModelDao mockHardwareModelDao;

    @Autowired
    HardwareTypeDao mockHardwareTypeDao;

    @After
    public void clean() {
        verify(mockHardwareModelDao);
        reset(mockHardwareModelDao);
    }

    @Test
    @Ignore
    public void getAllModels() throws Exception {

    }

    @Test
    public void getModelById() throws Exception {
        expect(mockHardwareModelDao.getModelById(6)).andReturn(newModel);
        replay(mockHardwareModelDao);
        Assert.assertEquals(newModel, hardwareModelService.getModelById(6));
    }

    @Test
    public void getModelByName() throws Exception {
        expect(mockHardwareModelDao.getModelByName("Intel")).andReturn(newModel);
        replay(mockHardwareModelDao);
        Assert.assertEquals(newModel, hardwareModelService.getModelByName("Intel"));
    }

    @Test
    @Ignore
    public void addModel() throws Exception {
        HardwareType type = new HardwareType("CPU");
        expect(mockHardwareModelDao.addModel(newModel)).andReturn(6);
        expect(mockHardwareTypeDao.getTypeByName(newModel.getModelType())).andReturn(type);
        expect(mockHardwareModelDao.getModelByName(newModel.getModelName())).andReturn(null);
        replay(mockHardwareModelDao);
        Assert.assertEquals(6, hardwareModelService.addModel(newModel));
    }

    @Test
    @Ignore
    public void updateModel() throws Exception {

    }

    @Test
    @Ignore
    public void deleteModel() throws Exception {

    }
}
