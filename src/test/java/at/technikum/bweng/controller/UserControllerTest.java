package at.technikum.bweng.controller;

import at.technikum.bweng.dto.UserCredentialsDto;
import at.technikum.bweng.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserController userController;

    @Test
    void register() throws Exception {
        mvc.perform(
                        MockMvcRequestBuilders.post("/user/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                          "username": "test",
                                          "password": "test",
                                          "details": {
                                            "firstname": "fn",
                                            "lastname": "ln",
                                            "email": "xxl@xx.at",
                                            "country": "AT",
                                            "salutation": "sal"
                                          }
                                        }
                                        """)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpectAll(
                        jsonPath("$.id").exists(),
                        jsonPath("$.firstname").value("fn"),
                        jsonPath("$.lastname").value("ln"),
                        jsonPath("$.email").value("xxl@xx.at"),
                        jsonPath("$.country").value("AT"),
                        jsonPath("$.salutation").value("sal")
                );

    }

    @Test
    void token() throws Exception {
        String user = "us";
        String pw = "pw";
        userController.register(
                new UserDto(
                        new UserCredentialsDto(user, pw),
                        null)
        );

        mvc.perform(MockMvcRequestBuilders.post("/user/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "username": "us",
                                  "password": "pw"
                                }
                                """)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpectAll(jsonPath("$.token").exists());
    }

    @Test
    void details() {
    }

    @Test
    void testDetails() {
    }

    @Test
    void uploadProfilePicture() {
    }

    @Test
    void getAllUsersWithRole() {
    }

    @Test
    void changeRole() {
    }
}