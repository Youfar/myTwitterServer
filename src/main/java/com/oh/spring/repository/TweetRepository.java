package com.oh.spring.repository;

import com.oh.spring.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * @author cho.oh
 */
@Repository
public interface TweetRepository extends JpaRepository<Tweet, Serializable>{
//    List<Tweet> findTweetsByUser_idOrderByTweet_idDesc (Integer userId);
    List<Tweet> findByCreator_UserIdOrderByTweetIdDesc (Integer userId);
    List<Tweet> findTweetsByCreator_UserIdInOrderByTweetIdDesc(List<Integer> userId);
    void removeByCreator_UserIdAndAndTweetId(Integer userId, Long tweetId);
    Tweet findTweetByCreator_UserIdAndTweetId(Integer userId, Long tweetId);

}
