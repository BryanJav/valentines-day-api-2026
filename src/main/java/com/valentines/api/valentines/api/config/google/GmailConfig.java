package com.valentines.api.valentines.api.config.google;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.UserCredentials;
import com.valentines.api.valentines.api.entity.RefreshTokenEntity;
import com.valentines.api.valentines.api.repository.RefreshTokenRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.io.IOException;

@Configuration
public class GmailConfig {

    private final RefreshTokenRepository tokenRepository;
    private final GoogleConfigProps googleConfigProps;

    public GmailConfig(RefreshTokenRepository tokenRepository, GoogleConfigProps googleConfigProps) {
        this.tokenRepository = tokenRepository;
        this.googleConfigProps = googleConfigProps;
    }

//    @Bean
//    @Lazy
//    public Gmail gmailService() throws IOException {
//
//        RefreshTokenEntity tokenEntity =
//                tokenRepository.findTopByUserEmailOrderByCreatedAtDesc("me@gmail.com")
//                    .orElseThrow(()-> new IllegalStateException("No refresh token found"));
//
//        GoogleCredentials credentials = UserCredentials.newBuilder()
//                .setClientId(googleConfigProps.getId())
//                .setClientSecret(googleConfigProps.getSecret())
//                .setRefreshToken(tokenEntity.getRefreshToken())
//                .build();
//
//        credentials.refreshAccessToken();
//
//        return new Gmail.Builder(
//                new NetHttpTransport(),
//                GsonFactory.getDefaultInstance(),
//                new HttpCredentialsAdapter(credentials))
//                .setApplicationName("valentines-api")
//                .build();
//    }
}
