package com.api.gbookpdf.dtos;

import org.springframework.stereotype.Component;

@Component
public class UserBookDTO {
    private String userId;
    private String bookId;
    public UserBookDTO() {}
    public UserBookDTO(String userId, String bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getBookId() {
        return bookId;
    }
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}
