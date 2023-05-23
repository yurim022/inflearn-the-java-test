package me.whiteship.inflearnthejavatest.study;

import me.whiteship.inflearnthejavatest.domain.StudyStatus;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

    @Test
    @DisplayName("새로운 스터디를 생성한다.")
    void create_new_study() {
        Study study = new Study(-10);
        assertNotNull(study);
        assertEquals(StudyStatus.DRAFT,study.getStatus(), "스터디를 처음 만들면 상태값이 DRAFT여야 한다.");
        assertTrue(study.getLimit() > 0, "스터디 최대 참석 인원은 0보다 커야 한다.");
        System.out.println("create");
    }


    @Test
    @DisplayName("여러개의 assert문을 테스트한다.")
    void create_new_study_test_assertAll() {
        Study study = new Study(StudyStatus.ENDED,-10);

        assertAll(
                ()-> assertNotNull(study),
                () -> assertEquals(StudyStatus.DRAFT,study.getStatus(), "스터디를 처음 만들면 상태값이 DRAFT여야 한다.") ,
                () -> assertTrue(study.getLimit() > 0, "스터디 최대 참석 인원은 0보다 커야 한다.")
        );
        System.out.println("create");
    }



    @Test
    @DisplayName("특정 exception이 발생하는지 테스트하고 싶을 때")
    void create_new_study_test_assertThrow() {

        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> new Study(-10));
        String message = illegalArgumentException.getMessage();
        assertEquals(message,"참여제한 인원은 0보다 적을 수 없습니다.");
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