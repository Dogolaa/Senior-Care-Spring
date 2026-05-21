package org.seniorcare.identityaccess.application.commands.impl.user;

public record CreateFamilyMemberUserCommand(
        String name,
        String email,
        String phone
) {
}
