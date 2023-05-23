package me.whiteship.inflearnthejavatest.study;

import me.whiteship.inflearnthejavatest.domain.StudyStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

    @DisplayName("ArgumentsAccessor로 파라미터 받기")
    @ParameterizedTest(name = "{index} {displayName} message = {0}")
    @CsvSource({"10, 자바 스터디", "20, 스프링"})
    void parameterizedTestWithClass(@AggregateWith(StudyAggregator.class) Study study) {
        System.out.println(study.toString());
    }



    //static함수에 public class 여야 사용 가능
    static class StudyAggregator implements ArgumentsAggregator {
        @Override
        public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext parameterContext) throws ArgumentsAggregationException {
            return new Study(accessor.getInteger(0), accessor.getString(1));
        }
    }

    @DisplayName("ArgumentsAccessor로 파라미터 받기")
    @ParameterizedTest(name = "{index} {displayName} message = {0}")
    @CsvSource({"10, 자바 스터디", "20, 스프링"})
    void parameterizedTestWithClass(ArgumentsAccessor argumentsAccessor) {
        Study study = new Study(argumentsAccessor.getInteger(0),argumentsAccessor.getString(1));
        System.out.println(study.toString());
    }


    @DisplayName("함수 파라미터로 인자 받기")
    @ParameterizedTest(name = "{index} {displayName} message = {0}")
    @CsvSource({"10, 자바 스터디", "20, 스프링"})
    void parameterizedTestWithClass(Integer limit, String name) {
        Study study = new Study(limit,name);
        System.out.println(study.toString());
    }


    @DisplayName("Converter로 인자 받기")
    @ParameterizedTest(name = "{index} {displayName} message = {0}")
    @ValueSource(ints = {10,20,40})
    void parameterizedTestWithClassAndOneArgument(@ConvertWith(StudyConverter.class) Study study) {
        System.out.println(study.getLimit());
    }

    static class StudyConverter extends SimpleArgumentConverter {
        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertEquals(Study.class,targetType,"Can only convert to Study");
            return new Study(Integer.parseInt(source.toString()));
        }
    }


    @DisplayName("스터디 반복테스트")
    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
    void repeatTest(RepetitionInfo repetitionInfo) {
        System.out.println("repeat test " + repetitionInfo.getCurrentRepetition()
                + "/" + repetitionInfo.getTotalRepetitions());
    }


    @DisplayName("스터디 파라미터 인자로 받는 테스트")
    @ParameterizedTest(name = "{index} {displayName} message = {0}")
    @ValueSource(strings = {"날씨가", "많이", "더워지고", "있네요."})
    //    @EmptySource
//    @NullSource
    @NullAndEmptySource
    @CsvSource({"java"})
    void paramiterizedTest(String message) {
        System.out.println(message);
    }


    @ParameterizedTest
    @ValueSource(ints = {10, 20, 30})
    void paramiterizedIntegerTest(int limit) {
        Study study = new Study(limit);
        System.out.println("참여제한인원 " + study.getLimit());
    }


    @Test
    @DisplayName("특정 시간안에 끝나야 하는 코드 테스트")
    void creat_new_study_timeout() {

        assertTimeout(Duration.ofMillis(100), () -> {
            new Study(10);
            Thread.sleep(300);
        });
    }

    @Test
    @DisplayName("새로운 스터디를 생성한다.")
    void create_new_study() {
        Study study = new Study(-10);
        assertNotNull(study);
        assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 상태값이 DRAFT여야 한다.");
        assertTrue(study.getLimit() > 0, "스터디 최대 참석 인원은 0보다 커야 한다.");
        System.out.println("create");
    }


    @Test
    @DisplayName("여러개의 assert문을 테스트한다.")
    void create_new_study_test_assertAll() {
        Study study = new Study(StudyStatus.ENDED, -10);

        assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 상태값이 DRAFT여야 한다."),
                () -> assertTrue(study.getLimit() > 0, "스터디 최대 참석 인원은 0보다 커야 한다.")
        );
        System.out.println("create");
    }


    @Test
    @DisplayName("특정 exception이 발생하는지 테스트하고 싶을 때")
    void create_new_study_test_assertThrow() {

        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> new Study(-10));
        String message = illegalArgumentException.getMessage();
        assertEquals(message, "참여제한 인원은 0보다 적을 수 없습니다.");
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