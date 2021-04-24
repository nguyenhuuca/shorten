package com.canhlabs.shorten.share.enums;

public enum ResultStatus {
    SUCCESS(0), FAILED(1), ENABLED(2), DISABLED(3);

    private int statusValue;

    ResultStatus(int statusValue) {
        this.statusValue = statusValue;
    }

    public int getStatusValue() {
        return this.statusValue;
    }

}