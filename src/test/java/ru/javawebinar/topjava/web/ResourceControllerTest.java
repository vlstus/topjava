package ru.javawebinar.topjava.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import ru.javawebinar.topjava.MealTestData;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ResourceControllerTest extends AbstractControllerTest {

    @Autowired
    ConversionService conversionService;

    @Test
    void getStyleSheets() throws Exception {
        perform(get("/resources/css/style.css"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("text/css"));
    }

    @Test
    void conversionTest() {
        assertEquals(conversionService.convert("25", Integer.class), 25);
        assertEquals(conversionService.convert(MealTestData.FROM.toLocalDate().toString(), LocalDate.class),
                MealTestData.FROM.toLocalDate());
    }
}
