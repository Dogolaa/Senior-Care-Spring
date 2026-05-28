package org.seniorcare.shared.domain;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PageResult<T> {

    private final List<T> content;
    private final long totalElements;
    private final int totalPages;
    private final int currentPage;
    private final int pageSize;

    public PageResult(List<T> content, long totalElements, int currentPage, int pageSize) {
        this.content = content;
        this.totalElements = totalElements;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalPages = pageSize == 0 ? 0 : (int) Math.ceil((double) totalElements / pageSize);
    }

    public <U> PageResult<U> map(Function<T, U> mapper) {
        List<U> mapped = content.stream().map(mapper).collect(Collectors.toList());
        return new PageResult<>(mapped, totalElements, currentPage, pageSize);
    }

    public List<T> content() {
        return content;
    }

    public long totalElements() {
        return totalElements;
    }

    public int totalPages() {
        return totalPages;
    }

    public int currentPage() {
        return currentPage;
    }

    public int pageSize() {
        return pageSize;
    }
}
