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

import java.time.LocalDate;
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

    private final Integer MODEL_ID = 2;
    private final String MODEL_NAME = "ASUS";
    private final String NEW_MODEL_NAME = "Name";
    private final String NEW_MODEL_TYPE_NAME = "CPU";
    private final String BEGIN_DATE = "2013-08-10";
    private final String END_DATE = "2014-12-01";
    private final LocalDate NEW_RELEASE_DATE = LocalDate.parse("2000-09-09");

    HardwareModel newModel = new HardwareModel("Intel", "CPU",
            LocalDate.parse("2012-11-03"));

    @Autowired
    HardwareModelService hardwareModelService;

    @Autowired
    HardwareModelDao hardwareModelDao;

    @Test
    public void getAllModels() throws Exception {
        List<HardwareModel> modelsList = hardwareModelService.getAllModels();
        LOGGER.debug("test getAllModels() in service; Returned list: {}", modelsList);
        assertEquals(hardwareModelDao.getModelsQuantity(), modelsList.size());
    }

    @Test
    public void getModelById() throws Exception {
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
        hardwareModelService.addModel(new HardwareModel("", "CPU", LocalDate.parse("2015-11-11")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addModelWithoutType() throws Exception {
        LOGGER.debug("test addModelWithoutType() in service");
        hardwareModelService.addModel(new HardwareModel("Name", "", LocalDate.parse("2015-11-11")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addModelWithWrongType() throws Exception {
        LOGGER.debug("test addModelWithWrongType() in service");
        hardwareModelService.addModel(new HardwareModel("Name", "Type", LocalDate.parse("2015-11-11")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addModelWithoutDate() throws Exception {
        LOGGER.debug("test addModelWithoutDate() in service");
        hardwareModelService.addModel(new HardwareModel(NEW_MODEL_NAME, NEW_MODEL_TYPE_NAME, null));
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
                new HardwareModel(1, NEW_MODEL_NAME, NEW_MODEL_TYPE_NAME, NEW_RELEASE_DATE));
        Assert.assertNotNull(effectedRowsNumber);
        Assert.assertEquals(NEW_MODEL_NAME, hardwareModelService.getModelById(1).getModelName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateModelWithWrongType() throws Exception {
        LOGGER.debug("test updateModelWithWrongType() in service");
        Integer effectedRowsNumber = hardwareModelService.updateModel(
                new HardwareModel(1, NEW_MODEL_NAME, "Type", NEW_RELEASE_DATE));
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
                new HardwareModel(100, "Name", "Type", LocalDate.parse("2011-11-10")));
        Assert.assertNull(effectedRowsNumber);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateModelWithDuplicateName() throws Exception {
        LOGGER.debug("test updateModelWithDuplicateName() in service");
        Integer effectedRowsNumber = hardwareModelService.updateModel(
                new HardwareModel(1, hardwareModelService.getModelById(2).getModelName(),
                        "Type",
                        LocalDate.parse("2011-11-10")));
        Assert.assertNull(effectedRowsNumber);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateModelWithoutId() throws Exception {
        LOGGER.debug("test updateModelWithoutId() in service");
        Integer effectedRowsNumber = hardwareModelService.updateModel(
                new HardwareModel(null, hardwareModelService.getModelById(2).getModelName(),
                        "Type",
                        LocalDate.parse("2011-11-10")));
        Assert.assertNull(effectedRowsNumber);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateModelWithoutName() throws Exception {
        LOGGER.debug("test updateModelWithoutName() in service");
        Integer effectedRowsNumber = hardwareModelService.updateModel(
                new HardwareModel(1, null, "Type",
                        LocalDate.parse("2011-11-10")));
        Assert.assertNull(effectedRowsNumber);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateModelWithoutType() throws Exception {
        LOGGER.debug("test updateModelWithoutType() in service");
        Integer effectedRowsNumber = hardwareModelService.updateModel(
                new HardwareModel(1, "Name", "",
                        LocalDate.parse("2011-11-10")));
        Assert.assertNull(effectedRowsNumber);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateModelWithoutDate() throws Exception {
        LOGGER.debug("test updateModelWithoutDate() in service");
        Integer effectedRowsNumber = hardwareModelService.updateModel(
                new HardwareModel(1, "Name", "Type",
                        null));
        Assert.assertNull(effectedRowsNumber);
    }

    @Test
    public void deleteModel() throws Exception {
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
        List<HardwareModel> modelsList = hardwareModelService.getModelsByPeriod(LocalDate.parse(BEGIN_DATE), LocalDate.parse(END_DATE));
        LOGGER.debug("test getModelsByPeriod() in service; Returned list: {}", modelsList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getModelsByWrongPeriod() throws Exception {
        LOGGER.debug("test getModelsByWrongPeriod() in service");
        hardwareModelService.getModelsByPeriod(LocalDate.parse("2015-11-11"), LocalDate.parse("2014-11-10"));
    }

}