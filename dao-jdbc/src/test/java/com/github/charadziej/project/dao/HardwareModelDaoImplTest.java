package com.github.charadziej.project.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests for HardwareModelDaoImpl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-dao.xml"})
@Transactional
public class HardwareModelDaoImplTest {

    private final Integer MODEL_ID = 2;
    private final String MODEL_NAME = "ASUS";
    private final String NEW_MODEL_NAME = "Test";
    private final String NEW_MODEL_TYPE_NAME = "CPU";
    private final String BEGIN_DATE = "2013-08-10";
    private final String END_DATE = "2014-12-01";
    private final LocalDate NEW_RELEASE_DATE = LocalDate.parse("2000-09-09");

    HardwareModel newModel = new HardwareModel("Intel Core i3", "CPU",
            LocalDate.parse("2012-11-03"));

    @Autowired
    HardwareModelDao hardwareModelDao;

    @Test
    public void getAllModels() throws Exception {
        List<HardwareModel> modelsList = hardwareModelDao.getAllModels();
        Assert.assertTrue("Check quantity of models", modelsList.size() > 0);
        Assert.assertEquals("Check model's name","GTX Titan", modelsList.get(2).getModelName());
        Assert.assertEquals("Check model's type", "CPU", modelsList.get(0).getModelType());
    }

    @Test
    public void getModelById() throws Exception {
        HardwareModel model = hardwareModelDao.getModelById(MODEL_ID);
        Assert.assertEquals("Check id", MODEL_ID, model.getModelId());
    }

    @Test
    public void getModelByName() throws Exception {
        HardwareModel model = hardwareModelDao.getModelByName(MODEL_NAME);
        Assert.assertEquals("Check name", MODEL_NAME, model.getModelName());
    }

    @Test
    public void addModel() throws Exception {
        Integer quantityBefore = hardwareModelDao.getAllModels().size();
        Integer key = hardwareModelDao.addModel(newModel);

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
        model.setReleaseDate(NEW_RELEASE_DATE);

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

        Assert.assertEquals(quantityBefore, (Integer) hardwareModelDao.getAllModels().size());
        Assert.assertNull(hardwareModelDao.getModelById(key));
    }

    @Test
    public void getModelsByPeriod() throws Exception {
        LocalDate begin = LocalDate.parse(BEGIN_DATE);
        LocalDate end = LocalDate.parse(END_DATE);
        boolean less = false, more = false;

        List<HardwareModel> modelsByPeriodList = hardwareModelDao.getModelsByPeriod(begin, end);

        for(int i = 0; i < modelsByPeriodList.size(); i++) {
            if(modelsByPeriodList.get(i).getReleaseDate().compareTo(begin) == 1)
                more = true;
            if(modelsByPeriodList.get(i).getReleaseDate().compareTo(end) == -1)
                less = true;
            Assert.assertTrue(more && less);
        }
    }

}