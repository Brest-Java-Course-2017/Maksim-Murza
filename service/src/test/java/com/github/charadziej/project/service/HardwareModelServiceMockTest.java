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
 * Mock tests for HardwareModelService class
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-service-mock.xml"})
public class HardwareModelServiceMockTest {

    private static final Logger LOGGER = LogManager.getLogger();

    private final Integer MODEL_ID = 5;
    private final String MODEL_NAME = "TestName";
    private final String MODEL_TYPE = "ModelType";
    private final String TYPE_NAME = "TypeName";
    private final LocalDate RELEASE_DATE = LocalDate.parse("2014-03-03");
    private final LocalDate BEGIN_DATE = LocalDate.parse("2013-03-03");
    private final LocalDate END_DATE = LocalDate.parse("2016-03-03");

    HardwareModel newModel = new HardwareModel(MODEL_NAME, MODEL_TYPE, RELEASE_DATE);

    @Autowired
    HardwareModelService hardwareModelService;

    @Autowired
    HardwareModelDao mockHardwareModelDao;

    @Autowired
    HardwareTypeDao mockHardwareTypeDao;

    @After
    public void tearDown() {
        verify(mockHardwareModelDao, mockHardwareTypeDao);
        reset(mockHardwareModelDao, mockHardwareTypeDao);
    }

    @Test
    public void getAllModels() throws Exception {
        HardwareModel model1 = new HardwareModel(), model2 = new HardwareModel();
        List<HardwareModel> list = new ArrayList<>();
        list.add(model1);
        list.add(model2);
        expect(mockHardwareModelDao.getAllModels()).andReturn(list);
        replay(mockHardwareModelDao, mockHardwareTypeDao);
        Assert.assertEquals(list, hardwareModelService.getAllModels());
    }

    @Test
    public void getModelById() throws Exception {
        expect(mockHardwareModelDao.getModelById(MODEL_ID)).andReturn(newModel);
        replay(mockHardwareModelDao, mockHardwareTypeDao);
        Assert.assertEquals(newModel, hardwareModelService.getModelById(MODEL_ID));
    }

    @Test
    public void getModelByName() throws Exception {
        expect(mockHardwareModelDao.getModelByName(MODEL_NAME)).andReturn(newModel);
        replay(mockHardwareModelDao, mockHardwareTypeDao);
        Assert.assertEquals(newModel, hardwareModelService.getModelByName(MODEL_NAME));
    }

    @Test
    public void addModel() throws Exception {
        HardwareType type = new HardwareType(TYPE_NAME);
        expect(mockHardwareTypeDao.getTypeByName(newModel.getModelType())).andReturn(type);
        expect(mockHardwareModelDao.getModelByName(newModel.getModelName())).andReturn(null);
        expect(mockHardwareModelDao.addModel(newModel)).andReturn(MODEL_ID);
        replay(mockHardwareModelDao, mockHardwareTypeDao);
        Assert.assertEquals(MODEL_ID, (Integer) hardwareModelService.addModel(newModel));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addModelWithDuplicateName() throws Exception {
        HardwareModel model = newModel;
        model.setModelType(TYPE_NAME);
        HardwareType type = new HardwareType(TYPE_NAME);
        expect(mockHardwareTypeDao.getTypeByName(newModel.getModelType())).andReturn(type);
        expect(mockHardwareModelDao.getModelByName(newModel.getModelName())).andReturn(model);
        replay(mockHardwareModelDao, mockHardwareTypeDao);
        hardwareModelService.addModel(newModel);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addModelWithWrongType() throws Exception {
        expect(mockHardwareTypeDao.getTypeByName(newModel.getModelType())).andReturn(null);
        replay(mockHardwareModelDao, mockHardwareTypeDao);
        hardwareModelService.addModel(newModel);
    }

    @Test
    public void updateModel() throws Exception {
        newModel.setModelId(MODEL_ID);
        HardwareType type = new HardwareType(TYPE_NAME);
        expect(mockHardwareTypeDao.getTypeByName(newModel.getModelType())).andReturn(type);
        expect(mockHardwareModelDao.getModelById(newModel.getModelId())).andReturn(newModel);
        expect(mockHardwareModelDao.getModelByName(newModel.getModelName())).andReturn(null);
        expect(mockHardwareModelDao.updateModel(newModel)).andReturn(1);
        replay(mockHardwareModelDao, mockHardwareTypeDao);
        Assert.assertEquals(1, hardwareModelService.updateModel(newModel));
    }

    @Test
    public void deleteModel() throws Exception {
        newModel.setModelId(MODEL_ID);
        expect(mockHardwareModelDao.getModelById(newModel.getModelId())).andReturn(newModel);
        expect(mockHardwareModelDao.deleteModel(newModel.getModelId())).andReturn(1);
        replay(mockHardwareModelDao, mockHardwareTypeDao);
        Assert.assertEquals(1, hardwareModelService.deleteModel(MODEL_ID));
    }

    @Test
    public void getModelsByPeriod() throws Exception {
        HardwareModel model1 = new HardwareModel(), model2 = new HardwareModel();
        List<HardwareModel> list = new ArrayList<>();
        list.add(model1);
        list.add(model2);
        expect(mockHardwareModelDao.getModelsByPeriod(BEGIN_DATE, END_DATE)).andReturn(list);
        replay(mockHardwareModelDao, mockHardwareTypeDao);
        hardwareModelService.getModelsByPeriod(BEGIN_DATE, END_DATE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getModelsByWrongPeriod() throws Exception {
        replay(mockHardwareModelDao, mockHardwareTypeDao);
        hardwareModelService.getModelsByPeriod(END_DATE, BEGIN_DATE);
    }
}
