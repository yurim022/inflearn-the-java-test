package me.whiteship.inflearnthejavatest.study;

import me.whiteship.inflearnthejavatest.domain.StudyStatus;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

    @Test
    @DisplayName("새로운 스터디를 생성한다.")
    void create_new_study() {
        Study study = new Study();
        assertNotNull(study);
        assertEquals(StudyStatus.DRAFT,study.getStatus(), "스터디를 처음 만들면 상태값이 DRAFT여야 한다.");
        System.out.println("create");
    }

    @Test
    void create2() {
        Study study = new Study();
        assertNotNull(study);
        System.out.println("create 2");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("before all");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("after all");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("before each");
    }

    @AfterEach
    void afterEach() {
        System.out.println("after each");
    }

}