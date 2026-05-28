package org.seniorcare.shared.domain;

public record Pagination(int page, int size) {

    public Pagination {
        if (page < 0) throw new IllegalArgumentException("Page index must not be negative.");
        if (size < 1) throw new IllegalArgumentException("Page size must be at least 1.");
    }
}
