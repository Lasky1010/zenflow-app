package edu.tms.zenflow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tms.zenflow.service.ImageService;
import edu.tms.zenflow.validations.ResponseErrorValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ImageControllerTest {

    @Mock
    private ImageService imageService;
    @Mock
    private ResponseErrorValidation responseErrorValidation;
    @InjectMocks
    private ImageController imageController;

    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
    }

    @Test
    void getImageForUserIsOk() throws Exception {
        mockMvc.perform(get("/api/image")).andExpect(status().isOk());
    }

    @Test
    void getIamgeToUserByUserIdIsOk() throws Exception {
        mockMvc.perform(get("/api/image/user/0")).andExpect(status().isOk());
    }

    @Test
    void uploadImageToUserIsOk() throws Exception {
        var file = new MockMultipartFile("file", "image".getBytes());
        mockMvc.perform(
                        MockMvcRequestBuilders.multipart("/api/image")
                                .file(file))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void uploadImageToPostIsOk() throws Exception {
        var file = new MockMultipartFile("file", "image".getBytes());
        mockMvc.perform(
                        MockMvcRequestBuilders.multipart("/api/image/1")
                                .file(file))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


}