package com.blueprint.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Getter
@Component
@PropertySources({
        @PropertySource("classpath:environment.properties")
})
public class EnvironmentVars {

    @Value("${env.application.name}")
    private String applicationName;
}
