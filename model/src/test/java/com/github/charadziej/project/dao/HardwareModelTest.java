package com.github.charadziej.project.dao;

import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * Class for testing HardwareModel's methods.
 */
public class HardwareModelTest {

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    private static final Integer MODEL_ID = 1;
    private static final String MODEL_NAME = "Intel core i5";
    private static final String MODEL_TYPE_NAME = "CPU";

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
        model.setModelType(MODEL_TYPE_NAME);
        Assert.assertEquals("check model's type: ", MODEL_TYPE_NAME, model.getModelType());
    }

    @Test
    public void getReleaseDate() throws Exception {
        Date releaseDate = FORMATTER.parse("2016-12-11");
        model.setReleaseDate(releaseDate);
        Assert.assertEquals("check model's release date: ", releaseDate, model.getReleaseDate());
    }

}