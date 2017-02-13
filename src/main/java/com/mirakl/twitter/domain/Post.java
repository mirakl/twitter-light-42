package com.mirakl.twitter.domain;

import javax.persistence.*;
import java.time.Instant;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;
    private String author;
    private Instant creationDate;

    public Post() {
    }

    public Post(String content, String author) {
        this.content = content;
        this.author = author;
    }

    @PrePersist
    public void onCreate() {
        creationDate = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }
}
