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
import java.util.ArrayList;
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
    public void getAllModels() throws Exception {
        HardwareModel model1 = new HardwareModel(), model2 = new HardwareModel();
        List<HardwareModel> list = new ArrayList<HardwareModel>();
        list.add(model1);
        list.add(model2);
        expect(mockHardwareModelDao.getAllModels()).andReturn(list);
        replay(mockHardwareModelDao);
        Assert.assertEquals(list, hardwareModelService.getAllModels());
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
    public void addModel() throws Exception {
        HardwareType type = new HardwareType("CPU");
        expect(mockHardwareTypeDao.getTypeByName(newModel.getModelType())).andReturn(type);
        expect(mockHardwareModelDao.getModelByName(newModel.getModelName())).andReturn(null);
        expect(mockHardwareModelDao.addModel(newModel)).andReturn(6);
        replay(mockHardwareModelDao);
        replay(mockHardwareTypeDao);
        Assert.assertEquals(6, hardwareModelService.addModel(newModel));
    }

    @Test(expected = IllegalArgumentException.class)
    @Ignore
    public void addModelWithDuplicateName() throws Exception {
        HardwareModel model = new HardwareModel(newModel.getModelName(), "CPU", newModel.getReleaseDate());
        HardwareType type = new HardwareType("CPU");
        expect(mockHardwareTypeDao.getTypeByName(newModel.getModelType())).andReturn(type);
        expect(mockHardwareModelDao.getModelByName(newModel.getModelName())).andReturn(model);
        replay(mockHardwareModelDao);
        replay(mockHardwareTypeDao);
        Assert.assertEquals(6, hardwareModelService.addModel(newModel));
    }

    @Test
    @Ignore
    public void addModelWithWrongType() throws Exception {

    }

    @Test
    @Ignore
    public void updateModel() throws Exception {

    }

    @Test
    @Ignore
    public void deleteModel() throws Exception {

    }

    @Test
    @Ignore
    public void getModelsByPeriod() throws Exception {

    }

    @Test
    @Ignore
    public void getModelsByWrongPeriod() throws Exception {

    }
}
