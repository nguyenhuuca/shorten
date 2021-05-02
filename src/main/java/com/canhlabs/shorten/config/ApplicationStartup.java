package com.canhlabs.shorten.config;

import com.canhlabs.shorten.share.AppConstant;
import com.canhlabs.shorten.share.AppProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Using to init the data before application start
 */
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
        loadProperties();
    }

    private void loadProperties() {
        log.info("load AppProperties...");
        AppConstant.props.setAes(appProps.getAes());
        AppConstant.props.setEndpoint(appProps.getEndpoint());
        AppConstant.props.setPrefixRedirect(appProps.getPrefixRedirect());
        AppConstant.props.setBaseDomain(appProps.getBaseDomain());
        AppConstant.props.setTimeLimit(appProps.getTimeLimit());
        AppConstant.props.setCountLimit(appProps.getCountLimit());
    }
}
