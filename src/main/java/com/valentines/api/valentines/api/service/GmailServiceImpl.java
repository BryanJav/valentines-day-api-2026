package com.valentines.api.valentines.api.service;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Draft;
import com.google.api.services.gmail.model.ListDraftsResponse;
import com.google.api.services.gmail.model.Message;
import com.valentines.api.valentines.api.factory.GmailClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GmailServiceImpl implements GmailService{

    private final GmailClientFactory gmailClientFactory;

    public GmailServiceImpl(GmailClientFactory gmailClientFactory) throws IOException {
        this.gmailClientFactory = gmailClientFactory;
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
    public Message sendDraft(String draftId) throws IOException {
        Draft draft = new Draft();
        draft.setId(draftId);

        return gmailClientFactory.createGmailClient()
                .users()
                .drafts()
                .send("me", draft)
                .execute();
    }
}
