package com.valentines.api.valentines.api.service;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Draft;
import com.google.api.services.gmail.model.ListDraftsResponse;
import com.google.api.services.gmail.model.Message;
import com.valentines.api.valentines.api.entity.DraftIdEntity;
import com.valentines.api.valentines.api.factory.GmailClientFactory;
import com.valentines.api.valentines.api.repository.DraftIdsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GmailServiceImpl implements GmailService{

    private final GmailClientFactory gmailClientFactory;
    private final DraftIdsRepository draftIdsRepository;

    public GmailServiceImpl(GmailClientFactory gmailClientFactory, DraftIdsRepository draftIdsRepository) throws IOException {
        this.gmailClientFactory = gmailClientFactory;
        this.draftIdsRepository = draftIdsRepository;
    }

    @Override
    public List<Draft> listTop5Drafts() throws IOException {
        ListDraftsResponse response =
                gmailClientFactory.createGmailClient().users().drafts()
                        .list("me")
                        .setMaxResults(5L)
                        .execute();


        return response.getDrafts();
    }

    @Override
    public Message sendDraft() throws IOException {
        DraftIdEntity draftIdEntity = draftIdsRepository.findTopByUserEmailOrderByCreatedAtDesc("me@gmail.com")
                .orElseThrow(IllegalStateException::new);

        Draft draft = new Draft();
        draft.setId(draftIdEntity.getDraftId());

        return gmailClientFactory.createGmailClient()
                .users()
                .drafts()
                .send("me", draft)
                .execute();
    }

    @Override
    public ResponseEntity<String> setDraftId(String draftId) {

        draftIdsRepository.save(DraftIdEntity.builder()
                .userEmail("me@gmail.com")
                .draftId(draftId)
                .createdAt(Instant.now())
                .build());

        return new ResponseEntity<>("Draft ID saved!", HttpStatus.OK);
    }
}
