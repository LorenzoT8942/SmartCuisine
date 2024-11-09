package com.authentication.authenticationManagement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationManagementApplicationTests {

	@Autowired
    private MockMvc mockMvc;

	@Value("${authentication.service.apikey}")
    private String validApiKey;


	@Test
    void testTokenWithApiKey() throws Exception {
		// Chiamata POST per creare un token jwt
        MvcResult result = mockMvc.perform(post("/auth/token")
                .header("Authorization", validApiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"test\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.jwtToken").exists())
                .andReturn();

        String token = result.getResponse().getContentAsString();
        token = token.split(":")[1].replaceAll("\"", "").trim();

        // Chiamata GET per validare il token
        mockMvc.perform(get("/auth/validate")
                .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testTokenWithoutApiKey() throws Exception {
		// Chiamata POST per creare un token jwt
        mockMvc.perform(post("/auth/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"test\"}"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}
