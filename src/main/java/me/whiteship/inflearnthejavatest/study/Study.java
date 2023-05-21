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
}
