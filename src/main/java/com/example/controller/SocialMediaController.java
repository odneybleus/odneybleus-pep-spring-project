package com.example.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * Controller class for social media functionality.
 */
@RestController
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    /**
     * Constructs a new SocialMediaController with the given account service.
     * 
     * @param accountService the account service
     * @param messageService the message service
     */
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    /**
     * Registers a new user account.
     * 
     * @param account the account to register
     * @return the registered account
     */
    @PostMapping("/register")
    public ResponseEntity<Account> registerUser(@RequestBody Account account) {
        Account createdAccount = accountService.registerAccount(account);
        return ResponseEntity.ok(createdAccount);
    }

    /**
     * Logs in a user account.
     * 
     * @param account the account to log in
     * @return the logged in account
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Account account) {
        try {
            Account loggedInAccount = accountService.login(account.getUsername(), account.getPassword());
            return ResponseEntity.ok(loggedInAccount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    /**
     * Creates a new message.
     * 
     * @param message the message to create
     * @return the created message
     */
    @PostMapping("/messages")
    public ResponseEntity<?> createMessage(@RequestBody Message message) {
        Message createdMessage = messageService.createMessage(message);
        return ResponseEntity.ok(createdMessage);
    }

    /**
     * Gets all messages.
     * 
     * @return a list of all messages
     */
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    /**
     * Gets a message by its ID.
     * 
     * @param messageId the ID of the message
     * @return the message with the given ID
     */
    @GetMapping("/messages/{message_id}")
    public ResponseEntity<?> getMessageById(@PathVariable("message_id") int messageId) {
        Message message = messageService.getMessageById(messageId);
        if (message != null) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.ok().body("");
        }
    }

    /**
     * Deletes a message by its ID.
     * 
     * @param messageId the ID of the message
     * @return the number of messages deleted
     */
    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<?> deleteMessageById(@PathVariable("message_id") int messageId) {
        boolean deleted = messageService.deleteMessageById(messageId);
        if (deleted) {
            return ResponseEntity.ok(1);
        } else {
            return ResponseEntity.ok().body("");
        }
    }

    /**
     * Updates a message by its ID.
     * 
     * @param messageId the ID of the message
     * @param message   the updated message
     * @return the number of messages updated
     */
    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<?> updateMessageById(@PathVariable("message_id") int messageId,
            @RequestBody Message message) {
        Message updatedMessage = messageService.updateMessage(messageId, message.getMessageText());
        if (updatedMessage != null) {
            return ResponseEntity.ok(1);
        } else {
            return ResponseEntity.status(400).body("");
        }
    }

    /**
     * Gets all messages posted by a user.
     * 
     * @param postedBy the ID of the user who posted the messages
     * @return a list of messages posted by the user
     */
    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getMessagesByUserId(@PathVariable("account_id") int userId) {
        List<Message> messages = messageService.getMessagesByUserId(userId);
        return ResponseEntity.ok(messages);
    }
}