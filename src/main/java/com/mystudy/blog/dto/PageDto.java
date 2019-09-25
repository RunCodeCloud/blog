package com.mystudy.blog.dto;

import java.util.List;

public class PageDto {

    private List<QuestionDto> list;
    private Integer page;
    private List<Integer> pages;

    public List<QuestionDto> getList() {
        return list;
    }

    public void setList(List<QuestionDto> list) {
        this.list = list;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Integer> getPages() {
        return pages;
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }
}
