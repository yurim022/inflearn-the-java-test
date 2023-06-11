package me.whiteship.inflearnthejavatest.study;

import lombok.extern.slf4j.Slf4j;
import me.whiteship.inflearnthejavatest.domain.Member;
import me.whiteship.inflearnthejavatest.domain.Study;
import me.whiteship.inflearnthejavatest.member.MemberService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@Testcontainers
@Slf4j
class StudyServiceTestDocker {

    @Mock
    MemberService memberService;

    @Autowired
    StudyRepository studyRepository;

    //static으로 정의하면 클래스 내 모든 테스트에서 사용, 없으면 매번 새로 만들어서 비효율적
    @Container
    static PostgreSQLContainer postgreSQLContainer =
            new PostgreSQLContainer().withDatabaseName("studytest");

//    @Container
//    static GenericContainer postgreSQLContainer =
//            new GenericContainer("postgres")
//                    .withExposedPorts(5432)
//                    .withEnv("POSTGRES_DB","studytest");
//
//
//    @BeforeAll
//    static void beforeAll() {
//        Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(log);
//        postgreSQLContainer.followOutput(logConsumer);
////        postgreSQLContainer.start();
//        //testContainer는 spring이 가지고 있는 jdbc url(spring.datasource.url)을 알지 못함
//    }

//    @AfterAll
//    static void afterAll() {
//        postgreSQLContainer.stop();
//    }

    @BeforeEach
    void beforeEach() {

        System.out.println("========");
        System.out.println(postgreSQLContainer.getMappedPort(5432));
        studyRepository.deleteAll();
        //데이터 conflict 방지
    }

    @Test
    void createNewStudy() {

        //given
        StudyService studyService = new StudyService(memberService,studyRepository);
        Member member = new Member();
        member.setId(1L);
        member.setEmail("yurim@email.com");

        Study study = new Study(10, "테스트");

        given(memberService.findById(1L)).willReturn(Optional.of(member));

        //when
        studyService.createNewStudy(1L, study);

        //then
        assertEquals(member.getId(),study.getOwnerId());
        then(memberService).should(times(1)).notify(study);
    }

}