package me.whiteship.inflearnthejavatest.study;

import me.whiteship.inflearnthejavatest.domain.Member;
import me.whiteship.inflearnthejavatest.domain.Study;
import me.whiteship.inflearnthejavatest.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudyServiceTag {

    @Mock
    MemberService memberService;

    @Mock
    StudyRepository studyRepository;


    @Test
    @DisplayName("스터디만들기 develop")
    @Tag("develop")
    void createStudyServiceMockWithMethod() {
        MemberService memberService = mock(MemberService.class);
        StudyRepository studyRepository = mock(StudyRepository.class);
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
    }


    @Test
    @DisplayName("스터디 만들기 prod")
    @Tag("prod")
    void createStudyServiceMockWithAnnotation() {
//        MemberService memberService = mock(MemberService.class);
//        StudyRepository studyRepository = mock(StudyRepository.class);
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
    }

    @Test
    @DisplayName("스터디만들기 develop & prod")
    @Tag("prod")
    @Tag("develop")
    void createStudyServiceMockOneTest(@Mock MemberService memberService,
                                        @Mock StudyRepository studyRepository) {

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
    }


    @Test
    @DisplayName("develop custom tag")
    @DevelopTest
    void stubMemberTest() {

        Member member = new Member();
        member.setId(1L);
        member.setEmail("yurim@email.com");
    }


    @Test
    @DisplayName("prod custom tag")
    @ProdTest
    void stubMemberAnyTest() {

        Member member = new Member();
        member.setId(1L);
        member.setEmail("yurim@email.com");

        when(memberService.findById(any())).thenReturn(Optional.of(member));

        assertEquals("yurim@email.com",memberService.findById(1L).get().getEmail());
        assertEquals("yurim@email.com",memberService.findById(2L).get().getEmail());

    }


    @Test
    @DisplayName("stub throw 에러")
    void stubMemberExceptionTest() {

        Member member = new Member();
        member.setId(1L);
        member.setEmail("yurim@email.com");

        doThrow(new IllegalArgumentException()).when(memberService).validate(1L);

        assertThrows(IllegalArgumentException.class, () -> {
            memberService.validate(1L);
        });
    }



    @Test
    @DisplayName("stub chaining")
    void stubMemberChaining() {

        Member member = new Member();
        member.setId(1L);
        member.setEmail("yurim@email.com");

        when(memberService.findById(any()))
                .thenReturn(Optional.of(member))
                .thenThrow(new RuntimeException())
                .thenReturn(Optional.empty());

        //첫번째 호출
        assertEquals("yurim@email.com",memberService.findById(1L).get().getEmail());

        //두번째 호출
        assertThrows(RuntimeException.class, () -> {
            memberService.findById(2L);
        });

        //세번째 호출
        assertEquals(Optional.empty(), memberService.findById(3L));
    }



    @Test
    @DisplayName("createNewStudy")
    void createNewStudy() {

        //given
        StudyService studyService = new StudyService(memberService,studyRepository);
        Member member = new Member();
        member.setId(1L);
        member.setEmail("yurim@email.com");

        Study study = new Study(10, "테스트");

        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        //when
        studyService.createNewStudy(1L, study);

        //then
        assertEquals(member.getId(),study.getOwnerId());

    }

}
