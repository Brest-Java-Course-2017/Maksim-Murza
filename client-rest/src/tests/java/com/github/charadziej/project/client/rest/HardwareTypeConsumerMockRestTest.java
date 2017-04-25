package com.github.charadziej.project.client.rest;

import com.github.charadziej.project.client.rest.api.HardwareTypeConsumer;
import com.github.charadziej.project.dao.HardwareType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * Created by charadziej on 4/13/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-client-mock.xml"})
public class HardwareTypeConsumerMockRestTest {

    private static final HardwareType type1 = new HardwareType("typeName1");

    @Autowired
    private HardwareTypeConsumer hardwareTypeConsumerRest;

    @Value("${protocol}://${host}:${port}")
    private String url;

    @Value("${types}")
    private String typesUrl;

    @Value("${type}")
    private String typeUrl;

    @Autowired
    private RestTemplate mockRestTemplate;

    @After
    public void tearDown() throws Exception {
        reset(mockRestTemplate);
    }

    @Test
    public void getAllTypes() throws Exception {
        List<HardwareType> expectedList = new ArrayList(2);
        expectedList.add(type1);
        expectedList.add(new HardwareType("typeName2"));
        expect(mockRestTemplate.getForEntity( url + typesUrl, List.class))
                .andReturn(new ResponseEntity<>(expectedList, HttpStatus.OK));
        replay(mockRestTemplate);
        List<HardwareType> list = hardwareTypeConsumerRest.getAllTypes();
        assertEquals(2, list.size());
    }

    @Test
    public void getTypeById() throws Exception {
        Integer typeId = 1;
        expect(mockRestTemplate.getForEntity(url + typeUrl + "/id/" + typeId,
                HardwareType.class)).andReturn(new ResponseEntity<>(type1, HttpStatus.FOUND));
        replay(mockRestTemplate);
        HardwareType hardwareType = hardwareTypeConsumerRest.getTypeById(1);
        assertEquals(hardwareType, type1);
    }

    @Test
    public void getTypeByName() throws Exception {
        String typeName = "typeName1";
        type1.setQuantity(2);
        expect(mockRestTemplate.getForEntity(url + typeUrl + "/name?typeName=" + typeName, HardwareType.class))
                .andReturn(new ResponseEntity<>(type1, HttpStatus.FOUND));
        replay(mockRestTemplate);
        HardwareType hardwareType = hardwareTypeConsumerRest.getTypeByName("typeName1");
        assertEquals(hardwareType.getQuantity(), (Integer) 2);
    }

    @Test
    public void addType() throws Exception {
        Integer typeId = 2;
        expect(mockRestTemplate.postForEntity(url + typeUrl, type1, Integer.class))
                .andReturn(new ResponseEntity<>(typeId, HttpStatus.CREATED));
        replay(mockRestTemplate);
        Integer newTypeId = hardwareTypeConsumerRest.addType(type1);
        assertEquals(typeId, newTypeId);
    }
}