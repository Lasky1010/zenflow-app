package edu.tms.zenflow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tms.zenflow.data.dto.post.PostCreateDto;
import edu.tms.zenflow.service.PostService;
import edu.tms.zenflow.validations.ResponseErrorValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
class PostControllerTest {

    @Mock
    private PostService postService;
    @Mock
    private ResponseErrorValidation responseErrorValidation;
    @InjectMocks
    private PostController postController;

    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    @Test
    void getAllPostsIsOk() throws Exception {
        mockMvc.perform(get("/api/posts")).andExpect(status().isOk());
    }

    @Test
    void getUsersPostsIsOk() throws Exception {
        mockMvc.perform(get("/api/posts/user")).andExpect(status().isOk());
    }

    @Test
    void createPostIsOk() throws Exception {
        PostCreateDto pdto = new PostCreateDto("Smth descpription");
        String req = objectMapper.writeValueAsString(pdto);
        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(req))
                .andExpect(status().isCreated());
    }

    @Test
    void likePostIsOk() throws Exception {
        mockMvc.perform(post("/api/posts/0/username"))
                .andExpect(status().isOk());
    }

    @Test
    void deletePostIsOk() throws Exception {
        mockMvc.perform(delete("/api/posts/0"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Post was deleted\" }"));
    }

}