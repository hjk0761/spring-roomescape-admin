package roomescape.domain;

public class Time {
    private Long id;
    private String startAt;

    public Time(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public Time() {

    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
