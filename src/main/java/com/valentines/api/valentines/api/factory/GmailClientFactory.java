package com.valentines.api.valentines.api.factory;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.UserCredentials;
import com.valentines.api.valentines.api.config.google.GoogleConfigProps;
import com.valentines.api.valentines.api.entity.RefreshTokenEntity;
import com.valentines.api.valentines.api.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GmailClientFactory {

    private final RefreshTokenRepository tokenRepo;
    private final GoogleConfigProps googleConfigProps;

    public GmailClientFactory(RefreshTokenRepository tokenRepo, GoogleConfigProps googleConfigProps) {
        this.tokenRepo = tokenRepo;
        this.googleConfigProps = googleConfigProps;
    }

    public Gmail createGmailClient() throws IOException {

        RefreshTokenEntity token =
                tokenRepo.findTopByUserEmailOrderByCreatedAtDesc("me@gmail.com")
                        .orElseThrow(() ->
                                new IllegalStateException("No refresh token found. Authorize first."));

        GoogleCredentials credentials =
                UserCredentials.newBuilder()
                        .setClientId(googleConfigProps.getId())
                        .setClientSecret(googleConfigProps.getSecret())
                        .setRefreshToken(token.getRefreshToken())
                        .build();

        credentials.refreshIfExpired();

        return new Gmail.Builder(
                new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials))
                .setApplicationName("valentines-api")
                .build();
    }
}
