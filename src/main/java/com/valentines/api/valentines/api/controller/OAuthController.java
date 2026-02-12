package com.valentines.api.valentines.api.controller;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.model.Draft;
import com.google.api.services.gmail.model.Message;
import com.valentines.api.valentines.api.config.google.GmailConfig;
import com.valentines.api.valentines.api.config.google.GoogleConfigProps;
import com.valentines.api.valentines.api.config.redirect.RedirectConfigProps;
import com.valentines.api.valentines.api.entity.RefreshTokenEntity;
import com.valentines.api.valentines.api.repository.RefreshTokenRepository;
import com.valentines.api.valentines.api.service.GmailService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;

@RestController
public class OAuthController {

    private final RefreshTokenRepository refreshTokenRepository;
    private final GoogleConfigProps googleConfigProps;
    private final RedirectConfigProps redirectConfigProps;
    private final GmailService gmailService;

    public OAuthController(RefreshTokenRepository refreshTokenRepository, GoogleConfigProps googleConfigProps, RedirectConfigProps redirectConfigProps, GmailService gmailService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.googleConfigProps = googleConfigProps;
        this.redirectConfigProps = redirectConfigProps;
        this.gmailService = gmailService;
    }

    @GetMapping("/oauth2/authorize")
    public void authorize(HttpServletResponse response) throws IOException {
        String authUrl = "https://accounts.google.com/o/oauth2/v2/auth" +
                "?client_id=" +  googleConfigProps.getId() +
                "&redirect_uri=" + URLEncoder.encode(redirectConfigProps.getBaseUrl() + redirectConfigProps.getPath(), StandardCharsets.UTF_8)  +
                "&response_type=code" +
                "&scope=https://www.googleapis.com/auth/gmail.compose https://www.googleapis.com/auth/gmail.modify" +
                "&access_type=offline" +
                "&prompt=consent";

        response.sendRedirect(authUrl);

    }

    @GetMapping("/oauth2/callback")
    public String callback(@RequestParam String code) throws IOException {
        TokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                "https://oauth2.googleapis.com/token",
                googleConfigProps.getId(),
                googleConfigProps.getSecret(),
                code,
                redirectConfigProps.getBaseUrl() + redirectConfigProps.getPath()
        ).execute();

        String refreshToken = tokenResponse.getRefreshToken();

        if (refreshToken == null) {
            return "No refresh token returned (already authorized?)";
        }

        // Example: single-user app
        String userEmail = "me@gmail.com";

        refreshTokenRepository.save(RefreshTokenEntity.builder()
                .userEmail(userEmail)
                .refreshToken(refreshToken)
                .createdAt(Instant.now())
                .build()
        );

        return "Refresh token save successfully";
    }

    @GetMapping("/drafts")
    public List<Draft> listDrafts() throws IOException {
        return gmailService.listTop5Drafts();
    }

    @PostMapping("/drafts/send")
    public Message sendDraft() throws IOException {
        return gmailService.sendDraft();
    }

    @PostMapping("/drafts/set-id")
    public ResponseEntity<?> setDraftId(@RequestParam String draftId) throws IOException{
        return gmailService.setDraftId(draftId);
    }


}
