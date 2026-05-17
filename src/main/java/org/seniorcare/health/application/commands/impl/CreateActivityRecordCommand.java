package org.seniorcare.health.application.commands.impl;

import java.util.UUID;

public record CreateActivityRecordCommand(UUID residentId, UUID conductedById) {
}
