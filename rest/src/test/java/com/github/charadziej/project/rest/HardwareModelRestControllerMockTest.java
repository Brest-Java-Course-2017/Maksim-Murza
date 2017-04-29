package com.github.charadziej.project.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.charadziej.project.dao.HardwareModel;
import com.github.charadziej.project.service.HardwareModelService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

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

    private static final Logger LOGGER = LogManager.getLogger();
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    private final Integer MODEL_ID = 5;
    private final String MODEL_NAME = "TestName";
    private final String MODEL_TYPE = "CPU";

    @Resource
    private HardwareModelRestController hardwareModelRestController;

    @Autowired
    private HardwareModelService hardwareModelService;

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
        HardwareModel newModel = new HardwareModel(MODEL_NAME, MODEL_TYPE, FORMATTER.parse("2014-03-03"));
        List<HardwareModel> list = new ArrayList<>();
        list.add(newModel);
        list.add(new HardwareModel());

        String listStr = new ObjectMapper().writeValueAsString(list);

        expect(hardwareModelService.getAllModels()).andReturn(list);
        replay(hardwareModelService);

        mockMvc.perform(
                get("/models")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(content().string(listStr))
                .andExpect(status().isOk());
    }

    @Test
    public void getModelById() throws Exception {
        HardwareModel newModel = new HardwareModel(MODEL_NAME, MODEL_TYPE, FORMATTER.parse("2014-03-03"));
        expect(hardwareModelService.getModelById(MODEL_ID)).andReturn(newModel);
        replay(hardwareModelService);

        String modelStr = new ObjectMapper().writeValueAsString(newModel);

        mockMvc.perform(
                get("/model/id/" + MODEL_ID)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(content().string(modelStr))
                .andExpect(status().isFound());
    }

    @Test
    public void getModelByName() throws Exception {
        HardwareModel newModel = new HardwareModel(MODEL_NAME, MODEL_TYPE, FORMATTER.parse("2014-03-03"));
        expect(hardwareModelService.getModelByName(MODEL_NAME)).andReturn(newModel);
        replay(hardwareModelService);

        String modelStr = new ObjectMapper().writeValueAsString(newModel);

        mockMvc.perform(
                get("/model/name").param("modelName", MODEL_NAME)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(content().string(modelStr))
                .andExpect(status().isFound());
    }

    @Test
    public void addModel() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        //FORMATTER.setTimeZone(TimeZone.getTimeZone("GMP"));
        mapper.setTimeZone(TimeZone.getTimeZone("Europe/Minsk"));

        HardwareModel newModel = new HardwareModel(MODEL_NAME, MODEL_TYPE, FORMATTER.parse("2014-03-03"));
        expect(hardwareModelService.addModel(anyObject(HardwareModel.class))).andReturn(3);
        replay(hardwareModelService);

        String newModelStr = mapper.writeValueAsString(newModel);

        System.out.println(newModel);
        System.out.println(newModelStr);

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
        HardwareModel newModel = new HardwareModel(MODEL_NAME, MODEL_TYPE, FORMATTER.parse("2014-03-03"));
        expect(hardwareModelService.updateModel(anyObject(HardwareModel.class))).andReturn(1);
        replay(hardwareModelService);

        String newModelStr = new ObjectMapper().writeValueAsString(newModel);

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
        HardwareModel newModel = new HardwareModel(MODEL_NAME, MODEL_TYPE, FORMATTER.parse("2014-03-03"));
        List<HardwareModel> list = new ArrayList<>();
        list.add(newModel);
        list.add(new HardwareModel());

        expect(hardwareModelService.getModelsByPeriod(FORMATTER.parse("2013-03-03"),
                FORMATTER.parse("2016-03-03"))).andReturn(list);
        replay(hardwareModelService);

        String listStr = new ObjectMapper().writeValueAsString(list);
        MultiValueMap<String, String> period = new LinkedMultiValueMap<>();
        period.add("begin","2013-03-03");
        period.add("end", "2016-03-03");

        mockMvc.perform(
                get("/models/period").params(period)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(content().string(listStr))
                .andExpect(status().isFound());
    }

    @Test
    public void getModelsByIndefinitePeriod() throws Exception {
        HardwareModel newModel = new HardwareModel(MODEL_NAME, MODEL_TYPE, FORMATTER.parse("2014-03-03"));
        List<HardwareModel> list = new ArrayList<>();
        list.add(newModel);
        list.add(new HardwareModel());

        expect(hardwareModelService.getModelsByPeriod(null, FORMATTER.parse("2016-03-03"))).andReturn(list);
        replay(hardwareModelService);

        String listStr = new ObjectMapper().writeValueAsString(list);
        MultiValueMap<String, String> period = new LinkedMultiValueMap<>();
        period.add("begin",null);
        period.add("end", "2016-03-03");

        mockMvc.perform(
                get("/models/period").params(period)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(content().string(listStr))
                .andExpect(status().isFound());
    }
}