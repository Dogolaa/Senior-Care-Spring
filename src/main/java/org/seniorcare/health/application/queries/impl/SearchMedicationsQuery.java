package org.seniorcare.health.application.queries.impl;

public record SearchMedicationsQuery(
        String productName,
        int page,
        int count
) {
}
