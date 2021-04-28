package com.canhlabs.shorten.validator;

import com.canhlabs.shorten.share.ruleutils.ExternalUrlRule;
import com.canhlabs.shorten.share.ruleutils.HttpRule;
import com.canhlabs.shorten.share.ruleutils.Rule;
import com.canhlabs.shorten.share.ruleutils.RuleContext;
import org.springframework.stereotype.Component;

@Component
public class ShortenValidator {
    public void validate(String url) {
        RuleContext<String> context = new RuleContext<>();
        Rule<String> httpRule = new HttpRule();
        Rule<String> externalUrlRule = new ExternalUrlRule();
        context.addRule(httpRule);
        context.addRule(externalUrlRule);
        context.executeRule(url);
    }
}
