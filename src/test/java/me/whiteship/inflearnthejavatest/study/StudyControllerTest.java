package me.whiteship.inflearnthejavatest.study;

import com.google.gson.Gson;
import me.whiteship.inflearnthejavatest.domain.Study;
import me.whiteship.inflearnthejavatest.domain.StudyStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class StudyControllerTest {

    @InjectMocks
    private StudyController studyController;

    @Mock
    private StudyRepository studyRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(studyController).build();
    }

    @DisplayName("스터디 생성")
    @Test
    void createStudy() throws Exception {

        //given
        Study studyRequest = createStudyRequest();
        Study studyResponse = createStudyResponse(studyRequest);
        when(studyRepository.save(any(Study.class))).thenReturn(studyResponse);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/study")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(studyRequest)));


        //then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("limitCount",studyResponse.getLimitCount()).exists())
                .andExpect(jsonPath("id",studyResponse.getId()).exists());

    }

    private  Study createStudyRequest() {
        return new Study(5, "Java StudY");
    }

    private  Study createStudyResponse(Study study) {
         study.setId(1L);
         study.setStatus(StudyStatus.STARTED);
         return study;
    }


    @DisplayName("스터리 목록조회")
    @Test
    void retrieveStudy() throws Exception {

        //given
        when(studyRepository.findById(any(Long.class))).thenReturn(retrieveStudyResponse());

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/study/{id}", 1)
                .accept(MediaType.APPLICATION_JSON));

        //then
        MvcResult result = resultActions.andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Study study = new Gson().fromJson(result.getResponse().getContentAsString(), Study.class);
        assertThat(study.getId().longValue()).isEqualTo(1L);


    }


    private Optional<Study> retrieveStudyResponse(){

        Study study = new Study(5, "Java StudY");
        study.setOwnerId(100L);
        study.setId(1L);
        study.setStatus(StudyStatus.STARTED);
        return Optional.of(study);
    }

}