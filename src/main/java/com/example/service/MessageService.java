package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

/**
 * Service class for messages.
 */
@Service
public class MessageService {

    public final MessageRepository messageRepository;
    public final AccountRepository accountRepository;

    /**
     * Constructs a new MessageService with the given message and account
     * repositories.
     * 
     * @param messageRepository the message repository
     * @param accountRepository the account repository
     */
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * Creates a new message.
     * 
     * @param message the message to create
     * @return the created message
     */
    public Message createMessage(Message message) {
        if (message.getPostedBy() == null || message.getMessageText().isBlank()
                || message.getMessageText().length() > 255) {
            throw new IllegalArgumentException("Message text cannot be blank and must be under 255 characters");
        }
        if (!accountRepository.existsById(message.getPostedBy())) {
            throw new IllegalArgumentException("Posted by must refer to an existing user");
        }
        return messageRepository.save(message);
    }

    /**
     * Gets all messages.
     * 
     * @return a list of all messages
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * Gets a message by its id.
     * 
     * @param messageId the message id
     * @return the message with the given id
     */
    public Message getMessageById(int messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }

    /**
     * Deletes a message by its id.
     * 
     * @param messageId the message id
     * @return true if the message was deleted, false if the message did not exist
     */
    public boolean deleteMessageById(int messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return true;
        }
        return false;
    }

    /**
     * Updates a message with the given id to have the given text.
     * 
     * @param messageId   the message id
     * @param messageText the new message text
     * @return the updated message
     * @throws IllegalArgumentException if the message text is blank or too long
     */
    public Message updateMessage(int messageId, String messageText) {
        if (messageText == null || messageText.isBlank() || messageText.length() > 255) {
            throw new IllegalArgumentException("Message text cannot be blank and must be under 255 characters");
        }
        Message existingMessage = messageRepository.findById(messageId).orElse(null);
        if (existingMessage != null) {
            existingMessage.setMessageText(messageText);
            return messageRepository.save(existingMessage);
        }
        return null;
    }

    /**
     * Gets all messages by a specific user.
     * 
     * @param userId the user id
     * @return a list of all messages by the user
     */
    public List<Message> getMessagesByUserId(int userId) {
        return messageRepository.findByPostedBy(userId);
    }
}