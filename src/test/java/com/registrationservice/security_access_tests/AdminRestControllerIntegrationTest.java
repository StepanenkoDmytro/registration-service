package com.registrationservice.security_access_tests;

import com.registrationservice.model.request.Decision;
import com.registrationservice.model.request.Request;
import com.registrationservice.rest.AdminRestController;
import com.registrationservice.service.TemporaryTokenService;
import com.registrationservice.service.RegistrationRequestsService;
import com.registrationservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class AdminRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    private TemporaryTokenService tokenService;
    @Mock
    private RegistrationRequestsService registrationService;

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void createRegistrationTokenTest_WithAdminRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/registration/requests"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void createRegistrationTokenTest_WithUserRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/registration/requests"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void createRegistrationTokenTest_WithAnonymous() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/registration/requests"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void getWaitingRequestsTest_WithAdminRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/registration/requests"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void getWaitingRequestsTest_WithUserRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/registration/requests"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void getWaitingRequestsTest_WithAnonymous() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/registration/requests"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void getAllTest_WithAdminRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/registration/requests/all"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void getAllTest_WithUserRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/registration/requests/all"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void getAllTest_WithAnonymous() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/registration/requests/all"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void getRequestTest_WithAdminRole() throws Exception {
        mockMvc = initWithMockRegistrationRequestsService();

        Request testCase = new Request("test@test.com", "123", "some-token", Decision.WAITING, new Date());
        when(registrationService.getByRegistrationToken("some-token")).thenReturn(Optional.of(testCase));

        mockMvc.perform(MockMvcRequestBuilders.get("/registration/requests/some-token"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void getRequestTest_WithUserRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/registration/requests/some-token"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void getRequestTest_WithAnonymous() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/registration/requests/some-token"))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void acceptRequestTest_WithAdminRole() throws Exception {
        mockMvc = initWithMockRegistrationRequestsService();

        Request testCase = new Request("test@test.com", "123", "some-token", Decision.WAITING, new Date());
        when(registrationService.getByRegistrationToken("some-token")).thenReturn(Optional.of(testCase));

        mockMvc.perform(MockMvcRequestBuilders.put("/registration/requests/some-token"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void acceptRequestTest_WithUserRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/registration/requests/some-token"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void acceptRequestTest_WithAnonymous() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/registration/requests/some-token"))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void declineRequestTest_WithAdminRole() throws Exception {
        mockMvc = initWithMockRegistrationRequestsService();

        Request testCase = new Request("test@test.com", "123", "some-token", Decision.WAITING, new Date());
        when(registrationService.getByRegistrationToken("some-token")).thenReturn(Optional.of(testCase));

        mockMvc.perform(MockMvcRequestBuilders.delete("/registration/requests/some-token"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void declineRequestTest_WithUserRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/registration/requests/some-token"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void declineRequestTest_WithAnonymous() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/registration/requests/some-token"))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
    }

    private MockMvc initWithMockRegistrationRequestsService() {
        AdminRestController adminRestController = new AdminRestController(tokenService, registrationService, userService);
        return MockMvcBuilders.standaloneSetup(adminRestController).build();
    }
}
