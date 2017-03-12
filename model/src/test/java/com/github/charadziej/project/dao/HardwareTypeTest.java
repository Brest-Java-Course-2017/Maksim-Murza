package com.github.charadziej.project.dao;

import org.junit.Assert;
import org.junit.Test;

/**
 * Class for testing HardwareType's methods.
 */

public class HardwareTypeTest {

    private static final Integer TYPE_ID = 1;
    private static final String TYPE_NAME = "CPU";
    private static final Integer QUANTITY = 1;

    HardwareType type = new HardwareType();

    @Test
    public void getTypeIdTest() throws Exception {
        type.setTypeId(TYPE_ID);
        Assert.assertEquals("Check type's id: ", TYPE_ID, type.getTypeId());
    }

    @Test
    public void getName() throws Exception {
        type.setTypeName(TYPE_NAME);
        Assert.assertEquals("Check type's name: ", TYPE_NAME, type.getTypeName());
    }

    @Test
    public void getQuantityTest() throws Exception {
        type.setQuantity(QUANTITY);
        Assert.assertEquals("Check type's quantity: ", QUANTITY, type.getQuantity());
    }

}