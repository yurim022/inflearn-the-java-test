package me.whiteship.inflearnthejavatest.study;

import lombok.Getter;
import lombok.ToString;
import me.whiteship.inflearnthejavatest.domain.StudyStatus;

@Getter
@ToString
public class Study {
    private StudyStatus status = StudyStatus.DRAFT;
    private int limit;
    private String name;

    public StudyStatus getStatus() {
        return this.status;
    }

    public Study(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("참여제한 인원은 0보다 적을 수 없습니다.");
        }
        this.limit = limit;
    }

    public Study() {
    }

    public Study(StudyStatus status, int limit) {
        this.status = status;
        this.limit = limit;
    }

    public Study(int limit, String name) {
        this.limit = limit;
        this.name = name;
    }
}
