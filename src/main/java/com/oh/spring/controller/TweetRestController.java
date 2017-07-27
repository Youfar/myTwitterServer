package com.oh.spring.controller;

import com.oh.spring.entity.FavoriteTweet;
import com.oh.spring.entity.Following;
import com.oh.spring.entity.Tweet;
import com.oh.spring.entity.User;
import com.oh.spring.repository.FavoriteTweetRepository;
import com.oh.spring.repository.FollowingRepository;
import com.oh.spring.repository.TweetRepository;
import com.oh.spring.repository.UserRepository;
import com.oh.spring.security.LoginUser;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cho.oh
 */
//@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
public class TweetRestController {

    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FavoriteTweetRepository favoriteTweetRepository;
    @Autowired
    private FollowingRepository followingRepository;

    @PostMapping("/tweet")
    public Tweet receiveTweet(@AuthenticationPrincipal LoginUser loginUser, @RequestParam("tweetContent") String tweet) {
        int userId = loginUser.getLoginUserId();
        User user = userRepository.findOne(userId);
        if (user == null) {
            return null;
        }
        if (tweet.length() > 140) {
            return null;
        }
        Tweet tweetEntity = new Tweet();
        tweetEntity.setTweetContent(tweet);
        tweetEntity.setCreator(user);
        tweetEntity.setTweetDatetime(LocalDateTime.now());
        tweetRepository.save(tweetEntity);
        return tweetEntity;
    }

    @PostMapping("/deleteTweet")
    @Transactional
    public String deleteTweet(@AuthenticationPrincipal LoginUser loginUser, @RequestParam("tweetId") long tweetId) throws NotFoundException {
        int userId = loginUser.getLoginUserId();
        Tweet targetTweet = tweetRepository.findTweetByCreator_UserIdAndTweetId(userId, tweetId);
        if (targetTweet == null) {
            throw new NotFoundException("You are not owner of the tweet");
        }
//        FavoriteTweet favoriteTweet = favoriteTweetRepository.findFavoriteTweetByUserIdAndTweetId()
        //TODO favorite里面也同时删除
        tweetRepository.removeByCreator_UserIdAndAndTweetId(userId, tweetId);
        return "SUCCESS";
    }

    @GetMapping("/getTweet")
    public List<Tweet> getTweet(@AuthenticationPrincipal LoginUser loginUser) {
        int userId = loginUser.getLoginUserId();
        List<Following> followingList = followingRepository.findAllByFollower_UserIdOrderByFollowingDesc(userId);
        List<Integer> targetUserIdList = followingList.stream().map(following -> following.getFollowing().getUserId()).collect(Collectors.toList());
        targetUserIdList.add(userId);
//        List<Tweet> tweetList = tweetRepository.findByCreator_UserIdOrderByTweetIdDesc(userId);
        List<Tweet> tweetList = tweetRepository.findTweetsByCreator_UserIdInOrderByTweetIdDesc(targetUserIdList);
        return tweetList;
    }

    @GetMapping("/getFavoriteTweet")
    public List<FavoriteTweet> getFavoriteTweet(@AuthenticationPrincipal LoginUser loginUser) {
        int userId = loginUser.getLoginUserId();
        List<FavoriteTweet> favoriteTweetList = favoriteTweetRepository.findFavoriteTweetsByUser_UserIdOrderByTweetDesc(userId);
//        List<FavoriteTweet> favoriteTweetList = favoriteTweetRepository.findFavoriteTweetsByUser_UserIdOrderBOrderByTweetId(userId);
        return favoriteTweetList;
    }

    @PostMapping("/addFavoriteTweet")
    public String addFavoriteTweet(@AuthenticationPrincipal LoginUser loginUser, @RequestParam("tweetId") long tweetId) {
        int userId = loginUser.getLoginUserId();
        FavoriteTweet favoriteTweetEntity = new FavoriteTweet();
        favoriteTweetEntity.setUserId(userId);
        favoriteTweetEntity.setTweetId(tweetId);
        favoriteTweetRepository.save(favoriteTweetEntity);
        return "SUCCESS";
    }

    @PostMapping("/deleteFavoriteTweet")
    @Transactional
    public String deleteFavoriteTweet(@AuthenticationPrincipal LoginUser loginUser, @RequestParam("tweetId") long tweetId) throws NotFoundException{
        int userId = loginUser.getLoginUserId();
        FavoriteTweet targetTweet = favoriteTweetRepository.findFavoriteTweetByUserIdAndTweetId(userId, tweetId);
        if (targetTweet == null) {
            throw new NotFoundException("ERROR");
        }
        favoriteTweetRepository.removeFavoriteTweetByUserIdAndTweetId(userId, tweetId);
        return "SUCCESS";
    }

    @GetMapping("/getTweetsByUserId/{userId}")
    public List<Tweet> getTweetsByUserId(@PathVariable Integer userId) throws NotFoundException {
        User targetUser = userRepository.findByUserId(userId);
        if (targetUser == null) {
            throw new NotFoundException("Target User Not Found");
        }
        List<Tweet> tweetList = tweetRepository.findByCreator_UserIdOrderByTweetIdDesc(userId);
        return tweetList;
    }
}
