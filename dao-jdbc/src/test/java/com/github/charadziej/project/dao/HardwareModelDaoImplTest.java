package com.github.charadziej.project.dao;

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
 * Tests for HardwareModelDaoImpl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-dao.xml"})
@Transactional
public class HardwareModelDaoImplTest {

    private static final Logger LOGGER = LogManager.getLogger();

    private final int QUANTITY = 25;
    private final Integer MODEL_ID = 2;
    private final String SECOND_MODEL_NAME = "Intel Core i5-4670 Haswell";
    private final String FIRST_MODEL_TYPE = "CPU";
    private final String MODEL_NAME = "Intel Core 2 Duo E8400";
    private final String NEW_MODEL_NAME = "TestName";
    private final String NEW_MODEL_TYPE_NAME = FIRST_MODEL_TYPE;
    private final String BEGIN_DATE = "2013-08-10";
    private final String END_DATE = "2016-12-01";
    private final String NEW_RELEASE_DATE = "2015-09-09";

    HardwareModel newModel = new HardwareModel(NEW_MODEL_NAME + "1", NEW_MODEL_TYPE_NAME, "2012-11-03");

    @Autowired
    HardwareModelDao hardwareModelDao;

    @Test
    public void getModelsQuantity() throws Exception {
        int quantity = hardwareModelDao.getModelsQuantity();
        LOGGER.debug("test getModelsQuantity() in dao; Returned int: {}", quantity);
        Assert.assertEquals("Check models quantity", QUANTITY, quantity);
    }

    @Test
    public void getAllModels() throws Exception {
        List<HardwareModel> modelsList = hardwareModelDao.getAllModels();
        LOGGER.debug("test getAllModels() in dao; Returned list: {}", modelsList);
        Assert.assertTrue("Check quantity of models", modelsList.size() > 0);
        Assert.assertEquals("Check model's name",SECOND_MODEL_NAME, modelsList.get(1).getModelName());
        Assert.assertEquals("Check model's type", FIRST_MODEL_TYPE, modelsList.get(0).getModelType());
    }

    @Test
    public void getModelById() throws Exception {
        HardwareModel model = hardwareModelDao.getModelById(MODEL_ID);
        LOGGER.debug("test getModelById() in dao; Returned object: {}", model);
        Assert.assertEquals("Check id", MODEL_ID, model.getModelId());
    }

    @Test
    public void getModelByName() throws Exception {
        HardwareModel model = hardwareModelDao.getModelByName(MODEL_NAME);
        LOGGER.debug("test getModelById() in dao; Returned object: {}", model);
        Assert.assertEquals("Check name", MODEL_NAME, model.getModelName());
    }

    @Test
    public void addModel() throws Exception {
        Integer quantityBefore = hardwareModelDao.getAllModels().size();
        Integer key = hardwareModelDao.addModel(newModel);
        LOGGER.debug("test addModel() in dao; Returned key: {}", key);

        Assert.assertEquals(quantityBefore + 1, hardwareModelDao.getAllModels().size());
        Assert.assertNotNull(hardwareModelDao.getModelById(key));
        Assert.assertEquals("Check new model's name", newModel.getModelName(),
                hardwareModelDao.getModelById(key).getModelName());
    }

    @Test
    public void updateModel() throws Exception {
        HardwareModel model =  hardwareModelDao.getModelById(5);
        model.setModelName(NEW_MODEL_NAME);
        model.setModelType(NEW_MODEL_TYPE_NAME);
        model.setReleaseDate(LocalDate.parse(NEW_RELEASE_DATE));
        LOGGER.debug("test updateModel() in dao; Object: {}", model);

        Integer effectedRowQuantity = hardwareModelDao.updateModel(model);
        System.out.println(hardwareModelDao.getModelById(5));

        Assert.assertEquals((Integer) 1, effectedRowQuantity);
        Assert.assertEquals(model, hardwareModelDao.getModelByName(NEW_MODEL_NAME));
    }

    @Test
    public void deleteModel() throws Exception {
        Integer quantityBefore = hardwareModelDao.getAllModels().size();
        Integer key = hardwareModelDao.addModel(newModel);

        Assert.assertNotNull(hardwareModelDao.getModelById(key));
        Assert.assertEquals("Check new model's name", newModel.getModelName(),
                hardwareModelDao.getModelById(key).getModelName());

        hardwareModelDao.deleteModel(key);
        LOGGER.debug("test deleteModel() in dao; Deleted object with key: {}", key);

        Assert.assertEquals(quantityBefore, (Integer) hardwareModelDao.getAllModels().size());
        Assert.assertNull(hardwareModelDao.getModelById(key));
    }

    @Test
    public void getModelsByPeriod() throws Exception {
        LocalDate begin = LocalDate.parse(BEGIN_DATE);
        LocalDate end = LocalDate.parse(END_DATE);
        boolean less = false, more = false;

        List<HardwareModel> modelsByPeriodList = hardwareModelDao.getModelsByPeriod(begin, end);
        LOGGER.debug("test getModelsByPeriod() in dao; Returned list: {}", modelsByPeriodList);

        for(int i = 0; i < modelsByPeriodList.size(); i++) {
            LocalDate temp = modelsByPeriodList.get(i).getReleaseDate();
            System.out.println(modelsByPeriodList.get(i).getReleaseDate().compareTo(begin));
            if(modelsByPeriodList.get(i).getReleaseDate().compareTo(begin) > 0)
                more = true;
            if(modelsByPeriodList.get(i).getReleaseDate().compareTo(end) < 0)
                less = true;
            System.out.println(more && less);
            Assert.assertTrue(more && less);
        }
    }

}