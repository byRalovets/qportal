package by.ralovets.qportal.service;

public interface MailSenderService {

    void send(String mailTo, String subject, String message);
}
