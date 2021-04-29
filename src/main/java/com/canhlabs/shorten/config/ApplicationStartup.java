package com.canhlabs.shorten.config;

import com.canhlabs.shorten.config.prop.AppProperties;
import com.canhlabs.shorten.share.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
    AppProperties appProps;
    @Autowired
    void injectProps(AppProperties props) {
        this.appProps = props;

    }
    @Override
    @NonNull
    public void onApplicationEvent(@NonNull ContextRefreshedEvent contextRefreshedEvent) {
        log.info("load AppProperties...");
        AppConstant.props.setAes(appProps.getAes());
        AppConstant.props.setEndpoint(appProps.getEndpoint());
        AppConstant.props.setPrefixRedirect(appProps.getPrefixRedirect());
        AppConstant.props.setBaseDomain(appProps.getBaseDomain());
    }
}
