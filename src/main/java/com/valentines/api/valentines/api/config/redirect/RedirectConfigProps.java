package com.valentines.api.valentines.api.config.redirect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "redirect")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RedirectConfigProps {

    private String baseUrl;
    private String path;
}
