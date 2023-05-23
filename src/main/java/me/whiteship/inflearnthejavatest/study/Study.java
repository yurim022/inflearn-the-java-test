package me.whiteship.inflearnthejavatest.study;

import me.whiteship.inflearnthejavatest.domain.StudyStatus;

public class Study {
    private StudyStatus status = StudyStatus.DRAFT;

    private int limit;
    public StudyStatus getStatus() {
        return this.status;
    }

    public int getLimit() {
        return limit;
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
}
