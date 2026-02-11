package com.valentines.api.valentines.api.service;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Draft;
import com.google.api.services.gmail.model.Message;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

public interface GmailService {
    List<Draft> listTop5Drafts() throws IOException;
    Message sendDraft(String draftId) throws IOException;
}
