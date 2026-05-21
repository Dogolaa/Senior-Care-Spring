package org.seniorcare.identityaccess.application.ports.output;

public interface IEmailNotificationPort {

    void sendFamilyMemberWelcomeEmail(String toEmail, String recipientName, String temporaryPassword);
}
