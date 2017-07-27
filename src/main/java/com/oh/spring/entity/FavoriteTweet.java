package com.oh.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author cho.oh
 */
@Entity
@Table(name="favorite_tweet")
public class FavoriteTweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FAVORITE_TWEET_ID")
    private Long favoriteTweetId;
    @ManyToOne
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "TWEET_ID", insertable = false, updatable = false)
    private Tweet tweet;

    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "TWEET_ID")
    private Long tweetId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getTweetId() {
        return tweetId;
    }

    public void setTweetId(Long tweetId) {
        this.tweetId = tweetId;
    }

    public Long getFavoriteTweetId() {
        return favoriteTweetId;
    }

    public void setFavoriteTweetId(Long favoriteTweetId) {
        this.favoriteTweetId = favoriteTweetId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tweet getTweet() {
        return tweet;
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }
}
