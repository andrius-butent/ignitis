package com.task.group.ignitis;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static com.task.group.ignitis.TestUtils.*;

import com.task.group.ignitis.dao.UserDAO;
import com.task.group.ignitis.entity.User;
import org.hamcrest.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserDAO userDAO;

    @Test
    public void when_userTryToRegisterWithoutEmailFormat_Expect_BadRequest() throws Exception {

        // given
        User user = new User(TestUtils.INVALID_USERNAME, PASSWORD);

        // then
        mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(CoreMatchers.containsString("Email is not valid")));
    }

    @Test
    public void when_userTryToRegisterWithoutPassword_Expect_BadRequest() throws Exception {

        // given
        User user = new User(USERNAME, null);

        // then
        mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(CoreMatchers.containsString("Password is required")));
    }

    @Test
    public void when_userTryToRegisterWithoutUsername_Expect_BadRequest() throws Exception {

        // given
        User user = new User(null, PASSWORD);

        // then
        mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(CoreMatchers.containsString("Username is required")));
    }

    @Test
    public void when_userTryToRegisterWithExistingUsername_Expect_NotFound() throws Exception {

        // given
        User user = new User(USERNAME, PASSWORD);

        // when
        Mockito.when(userDAO.findByUsername(USERNAME)).thenReturn(new User(USERNAME, PASSWORD));

        // then
        mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(CoreMatchers.containsString("User with username 'user@user.com' already exist.")));
    }

    @Test
    public void when_userTryToRegisterWithCorrectCredentials_Expect_UserIsCreated() throws Exception {

        // given
        User user = new User(USERNAME, PASSWORD);

        // when
        Mockito.when(userDAO.findByUsername(USERNAME)).thenReturn(null);
        Mockito.when(userDAO.save(user)).thenReturn(user);

        // then
        mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andExpect(status().isCreated())
                .andExpect(content().string(CoreMatchers.containsString("User is registered successfully!")));
    }

    @Test
    public void when_userTryToLoginWithoutEmailFormat_Expect_BadRequest() throws Exception {

        // given
        User user = new User(INVALID_USERNAME, PASSWORD);

        // then
        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(CoreMatchers.containsString("Email is not valid")));
    }

    @Test
    public void when_userTryToLoginWithoutPassword_Expect_BadRequest() throws Exception {

        // given
        User user = new User(USERNAME, null);

        // then
        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(CoreMatchers.containsString("Password is required")));
    }

    @Test
    public void when_userTryToLoginWithoutUsername_Expect_BadRequest() throws Exception {

        // given
        User user = new User(null, PASSWORD);

        // then
        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(CoreMatchers.containsString("Username is required")));
    }

    @Test
    public void when_userTryToLoginWithCorrectCredentialsAndExistInDatabase_Expect_UserLoggedIn() throws Exception {

        // given
        User user = new User(USERNAME, PASSWORD);

        // when
        Mockito.when(userDAO.findByUsername(Mockito.anyString())).thenReturn(new User(USERNAME, passwordEncoder.encode(PASSWORD)));

        // then
        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andExpect(status().isOk())
                .andExpect(content().string(CoreMatchers.containsString("User with username 'user@user.com' is logged in.")));
    }

    @Test
    public void when_userTryToLoginWithIncorrectUsername_Expect_BadRequest() throws Exception {

        // given
        User user = new User(USERNAME, PASSWORD);

        // when
        Mockito.when(userDAO.findByUsername(Mockito.anyString())).thenReturn(null);

        // then
        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(CoreMatchers.containsString("Username or password is invalid!")));
    }

    @Test
    public void when_userTryToLoginWithIncorrectPassword_Expect_BadRequest() throws Exception {

        // given
        User user = new User(USERNAME, INVALID_PASSWORD);

        // when
        Mockito.when(userDAO.findByUsername(Mockito.anyString())).thenReturn(new User(USERNAME, passwordEncoder.encode(PASSWORD)));

        // then
        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(CoreMatchers.containsString("Username or password is invalid!")));
    }
}
