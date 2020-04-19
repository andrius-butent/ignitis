package com.task.group.ignitis;

import com.task.group.ignitis.dao.BlogDAO;
import com.task.group.ignitis.entity.Blog;
import org.hamcrest.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import static com.task.group.ignitis.TestUtils.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BlogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BlogDAO blogDAO;

    @WithMockUser(username = USERNAME)
    @Test
    public void when_userTryToGetBlogListWithInvalidAuthentication_Expect_Unauthorized() throws Exception {

        // then
        mockMvc.perform(get("/blog/list")
                .with(httpBasic(INVALID_USERNAME, PASSWORD)))
                .andExpect(status().isUnauthorized());
    }


    @WithMockUser(username = USERNAME)
    @Test
    public void when_userTryToGetBlogListWithValidAuthentication_Expect_ResponseList() throws Exception {

        // given
        List<Blog> blogs = new ArrayList<>();
        blogs.add(new Blog(USERNAME, "title1", "text1"));
        blogs.add(new Blog(USERNAME, "title2", "text2"));

        // when
        Mockito.when(blogDAO.findAllByUsername(USERNAME)).thenReturn(blogs);

        // then
        mockMvc.perform(get("/blog/list")
                .with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "test")
    @Test
    public void when_userTryToAddBlogRecordWitIncorrectUsername_Expect_UnauthorizedRequest() throws Exception {

        // given
        Blog blog = new Blog("test", "title", "text");

        // then
        mockMvc.perform(post("/blog/addBlog")
                .with(httpBasic(INVALID_USERNAME, PASSWORD))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(blog)))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(username = USERNAME)
    @Test
    public void when_userTryToAddBlogRecordWithoutTitle_Expect_BadRequest() throws Exception {

        // given
        Blog blog = new Blog("test", null, "text");

        // then
        mockMvc.perform(post("/blog/addBlog")
                .with(httpBasic(USERNAME, PASSWORD))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(blog)))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(username = USERNAME)
    @Test
    public void when_userTryToAddBlogRecordWithoutText_Expect_BadRequest() throws Exception {

        // given
        Blog blog = new Blog("test", "title", null);

        // then
        mockMvc.perform(post("/blog/addBlog")
                .with(httpBasic(USERNAME, PASSWORD))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(blog)))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(username = USERNAME)
    @Test
    public void when_userTryToAddBlogRecordWithCorrectData_Expect_RecordIsAdded() throws Exception {

        // given
        Blog blog = new Blog("test", "title", "text");

        // when
        Mockito.when(blogDAO.save(blog)).thenReturn(blog);

        // then
        mockMvc.perform(post("/blog/addBlog")
                .with(httpBasic(USERNAME, PASSWORD))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(blog)))
                .andExpect(status().isCreated())
                .andExpect(content().string(CoreMatchers.containsString("User " + USERNAME +" added a record to blog.")));
    }

    @WithMockUser(username = USERNAME)
    @Test
    public void when_userTryToDeleteRecordWithIncorrectUsername_Expect_Unauthorized() throws Exception {

        // then
        mockMvc.perform(delete("/blog/delete/1")
                .with(httpBasic(INVALID_USERNAME, PASSWORD)))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(username = USERNAME)
    @Test
    public void when_userTryToDeleteRecordWithCorrectData_Expect_RecordIsDeleted() throws Exception {

        // when
        Mockito.doNothing().when(blogDAO).deleteByIdAndUsername(1, USERNAME);

        // then
        mockMvc.perform(delete("/blog/delete/1")
                .with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(content().string(CoreMatchers.containsString("Record is deleted.")));

        Mockito.verify(blogDAO, Mockito.times(1)).deleteByIdAndUsername(1, USERNAME);
    }

    @WithMockUser(username = USERNAME)
    @Test
    public void when_userTryToUpdateBlogRecordWithIncorrectUsername_Expect_UnauthorizedRequest() throws Exception {

        // given
        Blog blog = new Blog(USERNAME, "updatedTitle", "updatedText");

        // then
        mockMvc.perform(post("/blog/update/1")
                .with(httpBasic(INVALID_USERNAME, PASSWORD))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(blog)))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(username = USERNAME)
    @Test
    public void when_userTryToUpdateBlogWhichNotExist_Expect_NotFound() throws Exception {

        // given
        Blog blog = new Blog(USERNAME, "updatedTitle", "updatedText");

        // when
        Mockito.when(blogDAO.findByIdAndUsername(1, USERNAME)).thenReturn(null);

        // then
        mockMvc.perform(post("/blog/update/1")
                .with(httpBasic(USERNAME, PASSWORD))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(blog)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(CoreMatchers.containsString("No such blog")));
    }

    @WithMockUser(username = USERNAME)
    @Test
    public void when_userTryToUpdateBlogWhichCorrectData_Expect_RecordIsUpdated() throws Exception {

        // given
        Blog updatedBlog = new Blog(USERNAME, "updatedTitle", "updatedText");

        // when
        Mockito.when(blogDAO.findByIdAndUsername(1, USERNAME)).thenReturn(new Blog(USERNAME, "title", "text"));
        Mockito.when(blogDAO.save(updatedBlog)).thenReturn(updatedBlog);

        // then
        mockMvc.perform(post("/blog/update/1")
                .with(httpBasic(USERNAME, PASSWORD))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatedBlog)))
                .andExpect(status().isOk())
                .andExpect(content().string(CoreMatchers.containsString("Blog is updated.")));
    }
}
