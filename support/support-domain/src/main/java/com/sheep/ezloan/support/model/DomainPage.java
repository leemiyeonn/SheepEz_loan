package com.sheep.ezloan.support.model;

import java.util.List;

public class DomainPage<S> {

    private final List<S> data;

    private final Long totalItems;

    private final Integer totalPages;

    private final Integer currentPage;

    private final Integer pageSize;

    private final Boolean hasNext;

    public DomainPage(List<S> data, Long totalItems, Integer totalPages, Integer currentPage, Integer pageSize,
            Boolean hasNext) {
        this.data = data;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.hasNext = hasNext;
    }

    public static <S> DomainPage<S> of(List<S> data, Long totalItems, Integer totalPages, Integer currentPage,
            Integer pageSize, Boolean hasNext) {
        return new DomainPage<>(data, totalItems, totalPages, currentPage, pageSize, hasNext);
    }

    public List<S> getData() {
        return data;
    }

    public Long getTotalItems() {
        return totalItems;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Boolean getHasNext() {
        return hasNext;
    }

}
