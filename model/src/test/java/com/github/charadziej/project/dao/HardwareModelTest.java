package com.github.charadziej.project.dao;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * Class for testing HardwareModel's methods.
 */

public class HardwareModelTest {

    private static final Integer MODEL_ID = 1;
    private static final String MODEL_NAME = "Intel core i5";
    private static final String MODEL_TYPE = "CPU";
    private static final Date RELEASE_DATE = new Date(2015, 11, 10); //Date(int year, int month, int date)

    HardwareModel model = new HardwareModel();

    @Test
    public void getModelId() throws Exception {
        model.setModelId(MODEL_ID);
        Assert.assertEquals("check model's id: ", MODEL_ID, model.getModelId());
    }

    @Test
    public void getName() throws Exception {
        model.setModelName(MODEL_NAME);
        Assert.assertEquals("check model's name: ", MODEL_NAME, model.getModelName());
    }

    @Test
    public void getType() throws Exception {
        model.setModelType(MODEL_TYPE);
        Assert.assertEquals("check model's type: ", MODEL_TYPE, model.getModelType());
    }

    @Test
    public void getReleaseDate() throws Exception {
        model.setReleaseDate(RELEASE_DATE);
        Assert.assertEquals("check model's release date: ", RELEASE_DATE, model.getReleaseDate());
    }

}