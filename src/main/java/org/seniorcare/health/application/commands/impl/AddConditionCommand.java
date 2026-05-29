package org.seniorcare.health.application.commands.impl;

import java.util.UUID;

public record AddConditionCommand(UUID residentId, String conditionDescription) {}
