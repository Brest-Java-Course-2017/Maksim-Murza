package com.github.charadziej.project.dao;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * Class for testing HardwareModel's methods.
 */

public class HardwareModelTest {

    private static final String NAME = "Intel core i5";
    private static final String TYPE = "CPU";
    private static final Date RELEASE = new Date(2015, 11, 10); //Date(int year, int month, int date)
    private static final Integer ID = 1;

    HardwareModel model = new HardwareModel();

    @Test
    public void getModelId() throws Exception {
        model.setId(ID);
        Assert.assertEquals("check model's id: ", ID, model.getId());
    }

    @Test
    public void getName() throws Exception {
        model.setName(NAME);
        Assert.assertEquals("check model's name: ", NAME, model.getName());
    }

    @Test
    public void getType() throws Exception {
        model.setType(TYPE);
        Assert.assertEquals("check model's type: ", TYPE, model.getType());
    }

    @Test
    public void getReleaseDate() throws Exception {
        model.setReleaseDate(RELEASE);
        Assert.assertEquals("check model's release date: ", RELEASE, model.getReleaseDate());
    }

}