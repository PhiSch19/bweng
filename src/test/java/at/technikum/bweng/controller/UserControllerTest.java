package at.technikum.bweng.controller;

import at.technikum.bweng.dto.MovieDto;
import at.technikum.bweng.dto.UserCredentialsDto;
import at.technikum.bweng.dto.UserDto;
import at.technikum.bweng.entity.User;
import at.technikum.bweng.repository.UserRepository;
import at.technikum.bweng.service.AuthService;
import at.technikum.bweng.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
    }

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
    void registerExistingUser() throws Exception{

        User user = new User();
        user.setUsername("test");
        user.setPassword("AAAAaaaa123!!!");
        user.setFirstname("fn");
        user.setLastname("ln");
        user.setEmail("xxl@xx.at");
        user.setCountry("AT");
        user.setSalutation("sal");

        userService.register(user);

        Assertions.assertThatExceptionOfType(jakarta.servlet.ServletException.class)
                        .isThrownBy(() -> mvc.perform(
                                        MockMvcRequestBuilders.post("/user/register")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content("""
                                        {
                                          "username": "test",
                                          "password": "AAAAaaaa123!!!",
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
                                .andExpect(MockMvcResultMatchers.status().isOk()));



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
    void tokenInvalidCreds() throws Exception {
        /*Tests wether invalid credentials lead to a 401 response.*/
        String user = "us";
        String pw = "AAAAaaaa123!!!";
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
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }


    @Test
    void testGetDetails() throws Exception{
        /*Tests if a user is can access details of another user.*/
        String password = "AAAAaaaa123!!!";
        User user = new User();
        user.setUsername("us");
        user.setPassword(password);
        user.setLastname("ln");
        user.setFirstname("fn");
        user.setEmail("user@mail.com");
        user.setCountry("AT");
        user.setSalutation("mrs.");

        userService.register(user);
        String token = authService.authenticate(user.getUsername(), password);

        ResultActions response  = mvc.perform(MockMvcRequestBuilders.get("/user/"+ user.getId() +"/details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "bearer " + token));

        response.andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value(user.getFirstname()));
    }

    @Test
    void testGetDetailsOtherUser() throws Exception{
        /*Tests if a user is forbidden to access details of another user.*/
        String password = "AAAAaaaa123!!!";
        User user = new User();
        user.setUsername("us");
        user.setPassword(password);
        user.setLastname("ln");
        user.setFirstname("fn");
        user.setEmail("user@mail.com");
        user.setCountry("AT");
        user.setSalutation("mrs.");

        User other = new User();
        other.setUsername("other");
        other.setPassword(password);
        other.setLastname("other");
        other.setFirstname("other");
        other.setEmail("other@mail.com");
        other.setCountry("AT");
        other.setSalutation("mrs.");

        userService.register(user);
        userService.register(other);

        String token = authService.authenticate(user.getUsername(), password);

        ResultActions response  = mvc.perform(MockMvcRequestBuilders.get("/user/"+ other.getId() +"/details")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "bearer " + token));

        response.andExpect(MockMvcResultMatchers.status().isForbidden());
    }
    @Test
    void testGetDetailsNoToken() throws Exception{
        /*test wether the access of details is prevented for users that don't provide a token*/
        String password = "AAAAaaaa123!!!";
        User user = new User();
        user.setUsername("us");
        user.setPassword(password);
        user.setLastname("ln");
        user.setFirstname("fn");
        user.setEmail("user@mail.com");
        user.setCountry("AT");
        user.setSalutation("mrs.");

        userService.register(user);

        ResultActions response  = mvc.perform(MockMvcRequestBuilders.get("/user/"+ user.getId() +"/details")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isUnauthorized());

    }

    @Test
    void testPatchDetails() throws Exception{
        /*tests if users can update their details*/
        String password = "AAAAaaaa123!!!";
        String initFirstName = "us";
        String patchFirstName = "patched";

        User user = new User();
        user.setUsername(initFirstName);
        user.setPassword(password);
        user.setLastname("ln");
        user.setFirstname("fn");
        user.setEmail("user@mail.com");
        user.setCountry("AT");
        user.setSalutation("mrs.");

        userService.register(user);
        String token = authService.authenticate(user.getUsername(), password);

        ResultActions response  = mvc.perform(MockMvcRequestBuilders.patch("/user/"+ user.getId() +"/details")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "bearer " + token)
                .content(String.format("""
                                        {"firstname": "%s"}
                                        """, patchFirstName))
                );

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.firstname").value(patchFirstName));
    }

    @Test
    void testPatchDetailsLastName() throws Exception{
        /*tests if users can update their details*/
        String password = "AAAAaaaa123!!!";
        String initFirstName = "us";
        String patched = "patched";

        User user = new User();
        user.setUsername(initFirstName);
        user.setPassword(password);
        user.setLastname("ln");
        user.setFirstname("fn");
        user.setEmail("user@mail.com");
        user.setCountry("AT");
        user.setSalutation("mrs.");

        userService.register(user);
        String token = authService.authenticate(user.getUsername(), password);

        ResultActions response  = mvc.perform(MockMvcRequestBuilders.patch("/user/"+ user.getId() +"/details")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "bearer " + token)
                .content(String.format("""
                                        {"lastname": "%s"}
                                        """, patched))
        );

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.lastname").value(patched));
    }

    @Test
    void testPatchDetailsEmail() throws Exception{
        /*tests if users can update their details*/
        String password = "AAAAaaaa123!!!";
        String initFirstName = "us";
        String patched = "patched@mail.com";

        User user = new User();
        user.setUsername(initFirstName);
        user.setPassword(password);
        user.setLastname("ln");
        user.setFirstname("fn");
        user.setEmail("user@mail.com");
        user.setCountry("AT");
        user.setSalutation("mrs.");

        userService.register(user);
        String token = authService.authenticate(user.getUsername(), password);

        ResultActions response  = mvc.perform(MockMvcRequestBuilders.patch("/user/"+ user.getId() +"/details")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "bearer " + token)
                .content(String.format("""
                                        {"email": "%s"}
                                        """, patched))
        );

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.email").value(patched));
    }

    @Test
    void testPatchDetailsCountry() throws Exception{
        /*tests if users can update their details*/
        String password = "AAAAaaaa123!!!";
        String initFirstName = "us";
        String patched = "US";

        User user = new User();
        user.setUsername(initFirstName);
        user.setPassword(password);
        user.setLastname("ln");
        user.setFirstname("fn");
        user.setEmail("user@mail.com");
        user.setCountry("AT");
        user.setSalutation("mrs.");

        userService.register(user);
        String token = authService.authenticate(user.getUsername(), password);

        ResultActions response  = mvc.perform(MockMvcRequestBuilders.patch("/user/"+ user.getId() +"/details")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "bearer " + token)
                .content(String.format("""
                                        {"country": "%s"}
                                        """, patched))
        );

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.country").value(patched));
    }

    @Test
    void testPatchDetailsSalutation() throws Exception{
        /*tests if users can update their details*/
        String password = "AAAAaaaa123!!!";
        String initFirstName = "us";
        String patched = "other";

        User user = new User();
        user.setUsername(initFirstName);
        user.setPassword(password);
        user.setLastname("ln");
        user.setFirstname("fn");
        user.setEmail("user@mail.com");
        user.setCountry("AT");
        user.setSalutation("mrs.");

        userService.register(user);
        String token = authService.authenticate(user.getUsername(), password);

        ResultActions response  = mvc.perform(MockMvcRequestBuilders.patch("/user/"+ user.getId() +"/details")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "bearer " + token)
                .content(String.format("""
                                        {"salutation": "%s"}
                                        """, patched))
        );

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.salutation").value(patched));
    }

    @Test
    void getAllUsersAdmin() throws Exception{
        /*tests if an admin user can fetch a list of all users*/
        String password = "AAAAaaaa123!!!";
        String initFirstName = "admin";

        User user = new User();
        user.setUsername(initFirstName);
        user.setPassword(password);
        user.setLastname("ln");
        user.setFirstname("fn");
        user.setEmail("user@mail.com");
        user.setCountry("AT");
        user.setSalutation("mrs.");

        userService.register(user);
        user.setRole("ROLE_ADMIN");
        userRepository.save(user);

        String token = authService.authenticate(user.getUsername(), password);

        ResultActions response  = mvc.perform(MockMvcRequestBuilders.get("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "bearer " + token)

        );

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.size()").value(1)
                );


    }
    @Test
    void getAllUsersNoToken() throws Exception{
        /*tests is all a list of users can be fetched without a valid token*/
        String password = "AAAAaaaa123!!!";
        String initFirstName = "admin";

        User user = new User();
        user.setUsername(initFirstName);
        user.setPassword(password);
        user.setLastname("ln");
        user.setFirstname("fn");
        user.setEmail("user@mail.com");
        user.setCountry("AT");
        user.setSalutation("mrs.");

        userService.register(user);
        user.setRole("ROLE_ADMIN");
        userRepository.save(user);

        ResultActions response  = mvc.perform(MockMvcRequestBuilders.get("/user")
                .contentType(MediaType.APPLICATION_JSON)
        );

        response.andExpect(MockMvcResultMatchers.status().isUnauthorized());


    }
    @Test
    void getAllUsersWithoutAdminRights() throws Exception{
        /*tests if a user without admin rights can fetch a list of all users*/
        String password = "AAAAaaaa123!!!";
        String initFirstName = "admin";

        User user = new User();
        user.setUsername(initFirstName);
        user.setPassword(password);
        user.setLastname("ln");
        user.setFirstname("fn");
        user.setEmail("user@mail.com");
        user.setCountry("AT");
        user.setSalutation("mrs.");

        userService.register(user);


        String token = authService.authenticate(user.getUsername(), password);

        ResultActions response  = mvc.perform(MockMvcRequestBuilders.get("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "bearer " + token)

        );

        response.andExpect(MockMvcResultMatchers.status().isForbidden());

    }
    @Test
    void changeRole() throws Exception{
        /*Tests if a user is forbidden to access details of another user.*/
        String newRole = "ROLE_STAFF";

        String password = "AAAAaaaa123!!!";
        User user = new User();
        user.setUsername("us");
        user.setPassword(password);
        user.setLastname("ln");
        user.setFirstname("fn");
        user.setEmail("user@mail.com");
        user.setCountry("AT");
        user.setSalutation("mrs.");

        User other = new User();
        other.setUsername("other");
        other.setPassword(password);
        other.setLastname("other");
        other.setFirstname("other");
        other.setEmail("other@mail.com");
        other.setCountry("AT");
        other.setSalutation("mrs.");

        userService.register(user);
        userService.register(other);

        user.setRole("ROLE_ADMIN");
        userRepository.save(user);

        String token = authService.authenticate(user.getUsername(), password);

        ResultActions response  = mvc.perform(MockMvcRequestBuilders.post("/user/"+ other.getId() +"/role")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "bearer " + token)
                .content(newRole));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.role").value(newRole));
    }

    @Test
    void uploadProfilePicture() throws Exception{

        MockMultipartFile file = new MockMultipartFile(
                "file", "profileImage.png",
                MediaType.IMAGE_PNG_VALUE,
                "This is a test".getBytes()
        );

        String password = "AAAAaaaa123!!!";
        User user = new User();
        user.setUsername("us");
        user.setPassword(password);
        user.setLastname("ln");
        user.setFirstname("fn");
        user.setEmail("user@mail.com");
        user.setCountry("AT");
        user.setSalutation("mrs.");

        userService.register(user);
        String token = authService.authenticate(user.getUsername(), password);

        ResultActions response = mvc.perform(MockMvcRequestBuilders.multipart("/user/"+ user.getId() +"/profile-picture")
                        .file(file)
                        .header("Authorization", "bearer " + token));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.profilePictureId").isNotEmpty());


    }

    @Test
    void uploadProfilePictureNoToken() throws Exception{

        MockMultipartFile file = new MockMultipartFile(
                "file", "profileImage.png",
                MediaType.IMAGE_PNG_VALUE,
                "This is a test".getBytes()
        );

        String password = "AAAAaaaa123!!!";
        User user = new User();
        user.setUsername("us");
        user.setPassword(password);
        user.setLastname("ln");
        user.setFirstname("fn");
        user.setEmail("user@mail.com");
        user.setCountry("AT");
        user.setSalutation("mrs.");

        userService.register(user);

        ResultActions response = mvc.perform(MockMvcRequestBuilders.multipart("/user/"+ user.getId() +"/profile-picture")
                .file(file));
        response.andExpect(MockMvcResultMatchers.status().isUnauthorized());


    }


    @Test
    void uploadProfilePictureOfOtherUser() throws Exception{

        MockMultipartFile file = new MockMultipartFile(
                "file", "profileImage.png",
                MediaType.IMAGE_PNG_VALUE,
                "This is a test".getBytes()
        );

        String password = "AAAAaaaa123!!!";
        User user = new User();
        user.setUsername("us");
        user.setPassword(password);
        user.setLastname("ln");
        user.setFirstname("fn");
        user.setEmail("user@mail.com");
        user.setCountry("AT");
        user.setSalutation("mrs.");

        User other = new User();
        other.setUsername("other");
        other.setPassword(password);
        other.setLastname("other");
        other.setFirstname("other");
        other.setEmail("other@mail.com");
        other.setCountry("AT");
        other.setSalutation("mrs.");



        userService.register(user);
        userService.register(other);
        String token = authService.authenticate(user.getUsername(), password);

        ResultActions response = mvc.perform(MockMvcRequestBuilders.multipart("/user/"+ other.getId() +"/profile-picture")
                .file(file)
                .header("Authorization", "bearer " + token));

        response.andExpect(MockMvcResultMatchers.status().isForbidden());


    }


}