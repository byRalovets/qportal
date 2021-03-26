package by.ralovets.qportal.service;

public interface MailSenderService {

    void send(String to, String subject, String message);
}
