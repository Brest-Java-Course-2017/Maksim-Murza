package com.github.charadziej.project.dao;

import org.junit.Assert;
import org.junit.Test;

/**
 * Class for testing HardwareType's methods.
 */

public class HardwareTypeTest {

    private static final String NAME = "CPU";
    private static final Integer ID = 1;

    HardwareType type = new HardwareType();

    @Test
    public void getName() throws Exception {
        type.setName(NAME);
        Assert.assertEquals("Check type's name: ", NAME, type.getName());
    }

    @Test
    public void getTypeIdTest() throws Exception {
        type.setId(ID);
        Assert.assertEquals("Check type's id: ", ID, type.getId());
    }

}