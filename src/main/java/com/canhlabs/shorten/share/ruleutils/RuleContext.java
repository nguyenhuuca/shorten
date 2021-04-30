package com.canhlabs.shorten.share.ruleutils;

import java.util.LinkedHashSet;
import java.util.Set;

public class RuleContext<T> {
    private final Set<Rule<T>> rules =  new LinkedHashSet<>();

    public RuleContext() {}

    public RuleContext(Rule<T> rule) {
        this.rules.add(rule);
    }

    public RuleContext(Set<Rule<T>> rules) {
        this.rules.addAll(rules);
    }

    public void addRule(Rule<T> rule) {
        this.rules.add(rule);
    }

    public void executeRule(T data) {
        rules.forEach(rule -> rule.execute(data));
    }

}
