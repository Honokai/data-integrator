package dev.honokai.data_integrator_backend.unit;

import dev.honokai.data_integrator_backend.application.controllers.EnumController;
import dev.honokai.data_integrator_backend.domain.enums.FileFilterType;
import dev.honokai.data_integrator_backend.domain.enums.SourceType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EnumController.class)
@AutoConfigureMockMvc(addFilters = false)
public class EnumRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenRequestForFileFilterTypes_whenRetrievingValues_thenReturnArrayWithStringValues() throws Exception {
        mockMvc.perform(get(URI.create("/api/enums/file-filter-types")).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$[*]").value(containsInAnyOrder(Stream.of(FileFilterType.values()).map(Enum::name).toArray())));
    }

    @Test
    void givenRequestForSourceTypes_whenRetrievingValues_thenReturnArrayWithStringValues() throws Exception {
        mockMvc.perform(get(URI.create("/api/enums/file-source-types")).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$[*]").value(containsInAnyOrder(Stream.of(SourceType.values()).map(Enum::name).toArray())));
    }
}
