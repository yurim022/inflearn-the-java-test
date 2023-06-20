package me.whiteship.inflearnthejavatest.study;

import com.google.gson.Gson;
import me.whiteship.inflearnthejavatest.domain.Study;
import me.whiteship.inflearnthejavatest.domain.StudyStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudyController.class)
class StudyControllerWebMvcTest {

    @MockBean
    private StudyRepository studyRepository;

    @Autowired
    private MockMvc mockMvc;

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