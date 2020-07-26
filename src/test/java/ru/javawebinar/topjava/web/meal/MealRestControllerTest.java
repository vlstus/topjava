package ru.javawebinar.topjava.web.meal;


import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;


import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class MealRestControllerTest extends AbstractControllerTest {

    @Test
    void getAllEntities() throws Exception {
        perform(MockMvcRequestBuilders.get("/rest/meals"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(content().string(JsonUtil.writeValue(MealTestData.MEALS)));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get("/rest/meals/" + MealTestData.MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(content().string(JsonUtil.writeValue(MealTestData.MEAL1)));
    }


    @Test
    void createWithLocation() throws Exception {
        perform(MockMvcRequestBuilders.post("/rest/meals").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(new Meal(LocalDateTime.now(), "created", 200))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(header().exists("Location"));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete("/rest/meals/" + MealTestData.MEAL1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void update() throws Exception {
        perform(MockMvcRequestBuilders.put("/rest/meals/" + MealTestData.MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(
                        new Meal(
                                MealTestData.MEAL1.getId(),
                                MealTestData.MEAL1.getDateTime(),
                                "updated",
                                MealTestData.MEAL1.getCalories()))))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void getEntitiesBetween() throws Exception {
        perform(MockMvcRequestBuilders.get("/rest/meals")
                .param("startDate", MealTestData.FROM.toLocalDate().toString())
                .param("endDate", MealTestData.TO.toLocalDate().toString())
                .param("startTime", "")
                .param("endTime", ""))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(content().string(JsonUtil.writeValue(MealTestData.MEALS_BETWEEN)));
    }
}