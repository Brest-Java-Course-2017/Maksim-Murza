package com.github.charadziej.project.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.charadziej.project.dao.HardwareModel;
import com.github.charadziej.project.dao.HardwareType;
import com.github.charadziej.project.service.HardwareTypeService;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Mock tests for HardwareTypeRestController
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-rest-mock.xml"})
public class HardwareTypeRestControllerMockTest {

    private final Integer TYPE_ID = 5;

    HardwareType newType = new HardwareType("CPU");

    @Resource
    private HardwareTypeRestController hardwareTypeRestController;

    @Autowired
    HardwareTypeService hardwareTypeService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = standaloneSetup(hardwareTypeRestController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    @After
    public void tearDown() {
        verify(hardwareTypeService);
        reset(hardwareTypeService);
    }

    @Test
    public void getAllTypes() throws Exception {
        List<HardwareType> list = new ArrayList<>();
        list.add(newType);
        list.add(new HardwareType());

        expect(hardwareTypeService.getAllTypes()).andReturn(list);
        replay(hardwareTypeService);

        String listStr = new ObjectMapper().writeValueAsString(list);

        mockMvc.perform(
                get("/types")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(content().string(listStr))
                .andExpect(status().isOk());
    }

    @Test
    public void getTypeById() throws Exception {
        expect(hardwareTypeService.getTypeById(TYPE_ID)).andReturn(newType);
        replay(hardwareTypeService);

        String typeStr = new ObjectMapper().writeValueAsString(newType);

        mockMvc.perform(
                get("/type/id/" + TYPE_ID)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(content().string(typeStr))
                .andExpect(status().isFound());
    }

    @Test
    public void getTypeByName() throws Exception {
        final String TYPE_NAME = "TypeName";
        expect(hardwareTypeService.getTypeByName(TYPE_NAME)).andReturn(newType);
        replay(hardwareTypeService);

        String typeStr = new ObjectMapper().writeValueAsString(newType);

        mockMvc.perform(
                get("/type/name").param("typeName", TYPE_NAME)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(content().string(typeStr))
                .andExpect(status().isFound());
    }

    @Test
    public void addType() throws Exception {
        expect(hardwareTypeService.addType(anyObject(HardwareType.class))).andReturn(TYPE_ID);
        replay(hardwareTypeService);

        String typeStr = new ObjectMapper().writeValueAsString(newType);

        mockMvc.perform(
                post("/type")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(typeStr)
        ).andDo(print())
                .andExpect(content().string(TYPE_ID.toString()))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateType() throws Exception {
        expect(hardwareTypeService.updateType(anyObject(HardwareType.class))).andReturn(1);
        replay(hardwareTypeService);

        newType.setTypeId(3);
        String typeStr = new ObjectMapper().writeValueAsString(newType);

        mockMvc.perform(
                put("/type")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(typeStr)
        ).andDo(print())
                .andExpect(content().string("1"))
                .andExpect(status().isAccepted());
    }

    @Test
    public void deleteType() throws Exception {
        expect(hardwareTypeService.deleteType(TYPE_ID)).andReturn(1);
        replay(hardwareTypeService);

        mockMvc.perform(
                delete("/type/id/" + TYPE_ID)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(content().string("1"))
                .andExpect(status().isOk());
    }

}