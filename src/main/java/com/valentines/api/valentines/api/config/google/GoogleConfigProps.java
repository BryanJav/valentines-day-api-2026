package com.valentines.api.valentines.api.config.google;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "google.client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GoogleConfigProps {

    private String id;
    private String secret;
}
