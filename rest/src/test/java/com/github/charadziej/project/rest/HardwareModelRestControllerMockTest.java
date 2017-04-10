package com.github.charadziej.project.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.charadziej.project.dao.HardwareModel;
import com.github.charadziej.project.service.HardwareModelService;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Mock tests for HardwareModelRestController
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-rest-mock.xml"})
public class HardwareModelRestControllerMockTest {

    private final Integer MODEL_ID = 5;
    private final String MODEL_NAME = "TestName";
    private final String MODEL_TYPE = "CPU";
    private final String TYPE_NAME = "TypeName";
    private final LocalDate RELEASE_DATE = LocalDate.parse("2014-03-03");
    private final LocalDate BEGIN_DATE = LocalDate.parse("2013-03-03");
    private final LocalDate END_DATE = LocalDate.parse("2016-03-03");

    HardwareModel newModel = new HardwareModel(MODEL_NAME, MODEL_TYPE, RELEASE_DATE);

    @Resource
    private HardwareModelRestController hardwareModelRestController;

    @Autowired
    HardwareModelService hardwareModelService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = standaloneSetup(hardwareModelRestController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    @After
    public void tearDown() {
        verify(hardwareModelService);
        reset(hardwareModelService);
    }

    @Test
    public void getAllModels() throws Exception {
        List<HardwareModel> list = new ArrayList<>();
        list.add(newModel);
        list.add(new HardwareModel());

        expect(hardwareModelService.getAllModels()).andReturn(list);
        replay(hardwareModelService);

        mockMvc.perform(
                get("/models")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getModelById() throws Exception {
        expect(hardwareModelService.getModelById(MODEL_ID)).andReturn(newModel);
        replay(hardwareModelService);

        String modelStr = new ObjectMapper().writeValueAsString(newModel);

        mockMvc.perform(
                get("/model/id/" + MODEL_ID)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                //.andExpect(content().string(modelStr))
                .andExpect(status().isFound());
    }

    @Test
    public void getModelByName() throws Exception {
        expect(hardwareModelService.getModelByName(MODEL_NAME)).andReturn(newModel);
        replay(hardwareModelService);

        String modelStr = new ObjectMapper().writeValueAsString(newModel);

        mockMvc.perform(
                get("/model/name").param("modelName", MODEL_NAME)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
                //.andExpect(content().string(modelStr))
                .andExpect(status().isFound());
    }

    @Test
    public void addModel() throws Exception {
        expect(hardwareModelService.addModel(anyObject(HardwareModel.class))).andReturn(3);
        replay(hardwareModelService);

        /*String newModelStr = new ObjectMapper().writeValueAsString(newModel);
        System.out.println(newModel);
        System.out.print("model: ");
        System.out.println(newModelStr);*/

        //FIXME: fix LocalDate serialization or change date type

        String newModelStr = "{\"modelId\":null,\"modelName\":\"TestName\",\"modelType\":\"CPU\",\"releaseDate\":\"2014-09-09\"}";

                mockMvc.perform(
                post("/model")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newModelStr)
        ).andDo(print())
                .andExpect(content().string("3"))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateModel() throws Exception {
        expect(hardwareModelService.updateModel(anyObject(HardwareModel.class))).andReturn(1);
        replay(hardwareModelService);

        String newModelStr = "{\"modelId\":\"5\",\"modelName\":\"TestName\",\"modelType\":\"CPU\",\"releaseDate\":\"2014-09-09\"}";

        mockMvc.perform(
                put("/model")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newModelStr)
        ).andDo(print())
                .andExpect(content().string("1"))
                .andExpect(status().isAccepted());
    }

    @Test
    public void deleteModel() throws Exception {
        expect(hardwareModelService.deleteModel(MODEL_ID)).andReturn(1);
        replay(hardwareModelService);

        mockMvc.perform(
                delete("/model/id/" + MODEL_ID)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(content().string("1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getModelsByPeriod() throws Exception {
        List<HardwareModel> list = new ArrayList<>();
        list.add(newModel);
        list.add(new HardwareModel());

        expect(hardwareModelService.getModelsByPeriod(BEGIN_DATE, END_DATE)).andReturn(list);
        replay(hardwareModelService);

        mockMvc.perform(
                get("/models/" + BEGIN_DATE + "/" + END_DATE)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isFound());
    }
}