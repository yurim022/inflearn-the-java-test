package me.whiteship.inflearnthejavatest.study;

import me.whiteship.inflearnthejavatest.domain.Study;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudyControllerTestRestTemplateTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @DisplayName("스터디 생성")
    @Test
    void createStudy(){

        Study studyRequest = createStudyRequest();
        ResponseEntity<Study> studyResponseEntity = testRestTemplate.postForEntity("/study", studyRequest, Study.class);
        assertThat(studyResponseEntity.getStatusCode()).equals(200);
    }

    private  Study createStudyRequest() {
        return new Study(5, "Java StudY");
    }


}