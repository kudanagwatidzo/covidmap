package org.zincat.covidmap.configuration;

import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.zincat.covidmap.filters.JwtRequestFilter;
import org.zincat.covidmap.util.JwtUtil;

@AllArgsConstructor
public class JwtFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private JwtUtil jwtUtil;

    @Override
    public void configure(HttpSecurity http) throws Exception
    {
        JwtRequestFilter jwtRequestFilter = new JwtRequestFilter(jwtUtil);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
