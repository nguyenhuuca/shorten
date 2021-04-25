package com.canhlabs.shorten.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {



    private static final String[] AUTH_WHITELIST =
            {
                    "/",
                    // -- swagger ui
                    "/v2/api-docs", "/swagger-resources", "/swagger-resources/**",
                    "/configuration/ui", "/configuration/security", "/swagger-ui.html",
                    "/webjars/**"
                    // other public endpoints of your API may be appended to this array
            };

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(HttpMethod.POST, "abc")

        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // add white list urls for swagger ui
        http.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll()
//                .anyRequest().authenticated()
                .anyRequest().permitAll()
                .and().csrf().disable();

        // add custom filter to spring security filter chain
//        http.addFilterBefore(new JWTAuthenticationFilter(jwtServices),
//                UsernamePasswordAuthenticationFilter.class)

        // set session stateless policy
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
