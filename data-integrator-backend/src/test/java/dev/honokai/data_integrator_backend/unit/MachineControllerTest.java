package dev.honokai.data_integrator_backend.unit;

import com.github.f4b6a3.ulid.UlidCreator;
import dev.honokai.data_integrator_backend.application.controllers.MachineController;
import dev.honokai.data_integrator_backend.application.services.MachineService;
import dev.honokai.data_integrator_backend.application.services.TaskService;
import dev.honokai.data_integrator_backend.domain.entities.Machine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MachineController.class)
@AutoConfigureMockMvc(addFilters = false)
public class MachineControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private MachineService machineService;
    @MockitoBean
    private TaskService taskService;

    @Test
    void testGivenMachines_whenListingMachines_thenReturnMachinesJsonArray() throws Exception {
        Machine machine = new Machine(UlidCreator.getUlid().toString(), "any-machine", "192.111.66.22", true, Set.of());

        when(machineService.listOne(machine.getId())).thenReturn(Optional.of(machine));

        mockMvc.perform(get(URI.create("/api/machines/" + machine.getId())).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(machine.getName()));
    }
}
