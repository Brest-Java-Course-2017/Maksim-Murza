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

import static org.easymock.EasyMock.*;

/**
 * Created by charadziej on 3/20/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-service-mock.xml"})
public class HardwareTypeServiceMockTest {

    private static final Logger LOGGER = LogManager.getLogger();

    HardwareType newType = new HardwareType("CPU");

    @Autowired
    HardwareTypeService hardwareTypeService;

    @Autowired
    HardwareTypeDao mockHardwareTypeDao;

    @After
    public void clean() {
        verify(mockHardwareTypeDao);
        reset(mockHardwareTypeDao);
    }

    @Test
    @Ignore
    public void getTypeById() throws Exception {
        expect(mockHardwareTypeDao.getTypeById(6)).andReturn(newType);
        replay(mockHardwareTypeDao);
        Assert.assertEquals(newType, hardwareTypeService.getTypeById(6));
    }

    @Test
    @Ignore
    public void getTypeByName() throws Exception {

    }

    @Test
    @Ignore
    public void addType() throws Exception {

    }

    @Test(expected = IllegalArgumentException.class)
    @Ignore
    public void addTypeWithDuplicateName() throws Exception {

    }

    @Test
    @Ignore
    public void updateType() throws Exception {

    }

    @Test
    @Ignore
    public void deleteType() throws Exception {

    }
}
