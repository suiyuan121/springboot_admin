package com.fuwo.b3d.event;

public class CheckinEvent {

    private Integer source;

    public CheckinEvent(Integer source) {
        this.source = source;
    }

    public Integer getSource() {
        return this.source;
    }
}
