package com.oh.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author cho.oh
 */
@Entity
@Table(name="tweet")
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TWEET_ID")
    private Long tweetId;
//    @Column(name = "USER_ID")
//    private int user_id;
    @Column(name = "TWEET_BODY")
    private String tweetContent;
    @Column(name = "TWEET_DATETIME")
    private LocalDateTime tweetDatetime;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User creator;

    public Tweet() {

    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getTweetContent() {
        return tweetContent;
    }

    public void setTweetContent(String tweetContent) {
        this.tweetContent = tweetContent;
    }

    public LocalDateTime getTweetDatetime() {
        return tweetDatetime;
    }

    public void setTweetDatetime(LocalDateTime tweetDatetime) {
        this.tweetDatetime = tweetDatetime;
    }

    public Long getTweetId() {
        return tweetId;
    }

    public void setTweetId(Long tweetId) {
        this.tweetId = tweetId;
    }
}
