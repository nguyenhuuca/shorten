package com.canhlabs.shorten.share.ruleutils;

public interface Rule<T> {
    /**
     * Using to execute the rule based on implementation
     * <T> object need to check rule
     */
    void execute(T obj);
}
