package com.github.charadziej.project.service;

import com.github.charadziej.project.dao.HardwareModel;
import com.github.charadziej.project.dao.HardwareModelDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by charadziej on 3/18/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-service.xml"})
@Transactional
public class HardwareModelServiceImplTest {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    private final String NEW_MODEL_NAME = "TestName";
    private final String NEW_MODEL_TYPE_NAME = "CPU";

    @Autowired
    private HardwareModelService hardwareModelService;

    @Autowired
    private HardwareModelDao hardwareModelDao;

    @Test
    public void getAllModels() throws Exception {
        List<HardwareModel> modelsList = hardwareModelService.getAllModels();
        LOGGER.debug("test getAllModels() in service; Returned list: {}", modelsList);
        assertEquals(hardwareModelDao.getModelsQuantity(), modelsList.size());
    }

    @Test
    public void getModelById() throws Exception {
        final Integer MODEL_ID = 2;
        HardwareModel model = hardwareModelService.getModelById(MODEL_ID);
        LOGGER.debug("test getModelById() in service; Returned object: {}", model);
        Assert.assertNotNull(model);
    }

    @Test
    public void getModelByNotExistedId() throws Exception {
        LOGGER.debug("test getModelByNotExistedId() in service");
        HardwareModel model = hardwareModelService.getModelById(hardwareModelDao.getModelsQuantity()+1);
        Assert.assertNull(model);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getModelByNullId() throws Exception {
        LOGGER.debug("test getModelByNullId() in service");
        hardwareModelService.getModelById(null);
    }

    @Test
    public void getModelByName() throws Exception {
        final String MODEL_NAME = "Intel Core 2 Duo E8400";
        HardwareModel model = hardwareModelService.getModelByName(MODEL_NAME);
        LOGGER.debug("test getModelByName() in service; Returned object: {}", model);
        Assert.assertNotNull(model);
    }

    @Test
    public void getModelByNotExistedName() throws Exception {
        LOGGER.debug("test getModelByNotExistedName() in service");
        HardwareModel model = hardwareModelService.getModelByName("Not existed name");
        Assert.assertNull(model);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getModelByEmptyName() throws Exception {
        LOGGER.debug("test getModelByEmptyName() in service");
        hardwareModelService.getModelByName("");
    }

    @Test
    public void addModel() throws Exception {
        HardwareModel newModel = new HardwareModel(NEW_MODEL_NAME, NEW_MODEL_TYPE_NAME,
                FORMATTER.parse("2012-11-03"));
        Integer quantityBefore = hardwareModelService.getAllModels().size();
        Integer key = hardwareModelService.addModel(newModel);
        LOGGER.debug("test addModel() in service; Returned key: {}", key);
        Assert.assertEquals(quantityBefore + 1, hardwareModelDao.getModelsQuantity());
        Assert.assertNotNull(hardwareModelService.getModelById(key));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addNullModel() throws Exception {
        LOGGER.debug("test addNullModel() in service");
        hardwareModelService.addModel(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addModelWithoutName() throws Exception {
        LOGGER.debug("test addModelWithoutName() in service");
        hardwareModelService.addModel(new HardwareModel("", "CPU",
                FORMATTER.parse("2015-11-11")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addModelWithoutType() throws Exception {
        LOGGER.debug("test addModelWithoutType() in service");
        hardwareModelService.addModel(new HardwareModel("Name", "",
                FORMATTER.parse("2015-11-11")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addModelWithWrongType() throws Exception {
        LOGGER.debug("test addModelWithWrongType() in service");
        hardwareModelService.addModel(new HardwareModel("Name", "Type",
                FORMATTER.parse("2015-11-11")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addModelWithoutDate() throws Exception {
        LOGGER.debug("test addModelWithoutDate() in service");
        hardwareModelService.addModel(new HardwareModel(NEW_MODEL_NAME, NEW_MODEL_TYPE_NAME));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addModelWithDuplicateName() throws Exception {
        LOGGER.debug("test addDuplicateName() in service");
        Integer effectedRowsNumber = hardwareModelService.addModel(hardwareModelService.getModelById(1));
        Assert.assertNull(effectedRowsNumber);
    }

    @Test
    public void updateModel() throws Exception {
        LOGGER.debug("test updateModel() in service");
        Integer effectedRowsNumber = hardwareModelService.updateModel(
                new HardwareModel(1, NEW_MODEL_NAME, NEW_MODEL_TYPE_NAME,
                        FORMATTER.parse("2014-09-09")));
        Assert.assertNotNull(effectedRowsNumber);
        Assert.assertEquals(NEW_MODEL_NAME, hardwareModelService.getModelById(1).getModelName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateModelWithWrongType() throws Exception {
        LOGGER.debug("test updateModelWithWrongType() in service");
        Integer effectedRowsNumber = hardwareModelService.updateModel(
                new HardwareModel(1, NEW_MODEL_NAME, "Type",
                        FORMATTER.parse("2014-09-09")));
        Assert.assertNull(effectedRowsNumber);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateNullModel() throws Exception {
        LOGGER.debug("test updateNullModel() in service");
        Integer effectedRowsNumber = hardwareModelService.updateModel(null);
        Assert.assertNull(effectedRowsNumber);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateNotExistedModel() throws Exception {
        LOGGER.debug("test updateNotExistedModel() in service");
        Integer effectedRowsNumber = hardwareModelService.updateModel(
                new HardwareModel(100, "Name", "Type",
                        FORMATTER.parse("2011-11-10")));
        Assert.assertNull(effectedRowsNumber);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateModelWithDuplicateName() throws Exception {
        LOGGER.debug("test updateModelWithDuplicateName() in service");
        Integer effectedRowsNumber = hardwareModelService.updateModel(
                new HardwareModel(1, hardwareModelService.getModelById(2).getModelName(),
                        "Type", FORMATTER.parse("2011-11-10")));
        Assert.assertNull(effectedRowsNumber);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateModelWithoutId() throws Exception {
        LOGGER.debug("test updateModelWithoutId() in service");
        Integer effectedRowsNumber = hardwareModelService.updateModel(
                new HardwareModel(null, hardwareModelService.getModelById(2).getModelName(),
                        "Type", FORMATTER.parse("2011-11-10")));
        Assert.assertNull(effectedRowsNumber);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateModelWithoutName() throws Exception {
        LOGGER.debug("test updateModelWithoutName() in service");
        Integer effectedRowsNumber = hardwareModelService.updateModel(
                new HardwareModel(1, null, "Type",
                        FORMATTER.parse("2011-11-10")));
        Assert.assertNull(effectedRowsNumber);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateModelWithoutType() throws Exception {
        LOGGER.debug("test updateModelWithoutType() in service");
        Integer effectedRowsNumber = hardwareModelService.updateModel(
                new HardwareModel(1, "Name", "",
                        FORMATTER.parse("2011-11-10")));
        Assert.assertNull(effectedRowsNumber);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateModelWithoutDate() throws Exception {
        LOGGER.debug("test updateModelWithoutDate() in service");
        Integer effectedRowsNumber = hardwareModelService.updateModel(
                new HardwareModel(1, "Name", "Type"));
        Assert.assertNull(effectedRowsNumber);
    }

    @Test
    public void deleteModel() throws Exception {
        HardwareModel newModel = new HardwareModel(NEW_MODEL_NAME, NEW_MODEL_TYPE_NAME,
                FORMATTER.parse("2012-11-03"));
        LOGGER.debug("test deleteModel() in service");
        Integer quantityBefore = hardwareModelService.getAllModels().size();
        Integer key = hardwareModelService.addModel(newModel);
        Assert.assertNotNull(hardwareModelService.getModelById(key));
        int effectedRowsNumber = hardwareModelService.deleteModel(key);
        Assert.assertEquals(quantityBefore, (Integer) hardwareModelService.getAllModels().size());
        Assert.assertEquals( 1, effectedRowsNumber);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteModelWithoutId() throws Exception {
        LOGGER.debug("test deleteModelWithoutId() in service");
        hardwareModelService.deleteModel(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteNotExistedModel() throws Exception {
        LOGGER.debug("test deleteNotExistedModel() in service");
        hardwareModelService.deleteModel(100);
    }

    @Test
    public void getModelsByPeriod() throws Exception {
        List<HardwareModel> modelsList = hardwareModelService.getModelsByPeriod(FORMATTER.parse("2013-08-10"),
                FORMATTER.parse("2016-12-01"));
        Assert.assertNotNull(modelsList);
        LOGGER.debug("test getModelsByPeriod() in service; Returned list: {}", modelsList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getModelsByWrongPeriod() throws Exception {
        LOGGER.debug("test getModelsByWrongPeriod() in service");
        hardwareModelService.getModelsByPeriod(FORMATTER.parse("2015-11-11"),
                FORMATTER.parse("2014-11-10"));
    }

    @Test
    public void getModelsByIndefinitePeriod() throws Exception {
        Date begin = FORMATTER.parse("2014-08-10"), end = null;
        List<HardwareModel> modelsByPeriodList = hardwareModelService.getModelsByPeriod(begin, end);

        LOGGER.debug("test getModelsByPeriod() in dao; Returned list: {}", modelsByPeriodList);

        Assert.assertNotNull(modelsByPeriodList);
    }
}