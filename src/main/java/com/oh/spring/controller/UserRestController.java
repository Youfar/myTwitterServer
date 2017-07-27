package com.oh.spring.controller;

import com.oh.spring.entity.Following;
import com.oh.spring.entity.Tweet;
import com.oh.spring.entity.User;
import com.oh.spring.repository.FollowingRepository;
import com.oh.spring.repository.UserRepository;
import com.oh.spring.security.LoginUser;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author cho.oh
 */
//@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
public class UserRestController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FollowingRepository followingRepository;


    @GetMapping("/getAllUsers")
    public List<User> getAllUsers(@AuthenticationPrincipal LoginUser loginUser) {
        int userId = loginUser.getLoginUserId();
        List<User> userList = userRepository.findAllByOrderByUserIdDesc();
        return userList;
    }

    @GetMapping("/getMyUserId")
    public Integer getMyUserId(@AuthenticationPrincipal LoginUser loginUser) {
        int userId = loginUser.getLoginUserId();
        return userId;
    }

    @GetMapping("/getUser/{userId}")
    public User getUserByUserId(@PathVariable Integer userId) throws NotFoundException {
        User targetUser = userRepository.findByUserId(userId);
        if (targetUser == null) {
            throw new NotFoundException("Target User Not Found");
        }
        return targetUser;
    }


    @RequestMapping(value = "/addFollowing", method = RequestMethod.POST)
    public Object addFollowing(@RequestParam("targetUserId") Integer targetUserId, @AuthenticationPrincipal LoginUser loginUser) throws NotFoundException {
        User targetUser = userRepository.findByUserId(targetUserId);
        if (targetUser == null) {
            throw new NotFoundException("Target User Not Found");
        }
        int loginUserId = loginUser.getLoginUserId();
        User myUser = userRepository.findByUserId(loginUserId);
        if (targetUserId == loginUserId) {
            throw new NotFoundException("Target User must not be yourself.");
        }

        Following following = new Following();
        //TODO 判断是否已经follow 过
        //TODO not found
        following.setFollower(myUser);
        following.setFollowing(targetUser);
        followingRepository.save(following);

        Map<String, String> result = new HashMap<>();
        result.put("status", "success");
        return result;

    }

    @GetMapping("/getFollowings")
    public List<User> getFollowings(@AuthenticationPrincipal LoginUser loginUser) {
        int userId = loginUser.getLoginUserId();
        List<Following> followingList = followingRepository.findAllByFollower_UserIdOrderByFollowingDesc(userId);
        List<Integer> targetUserIdList = followingList.stream().map(following -> following.getFollowing().getUserId()).collect(Collectors.toList());
        List<User> followingUserList = userRepository.findUsersByUserIdInOrderByUserIdDesc(targetUserIdList);
        return followingUserList;
    }

    @GetMapping("/getFollowers")
    public List<User> getFollowers(@AuthenticationPrincipal LoginUser loginUser) {
        int userId = loginUser.getLoginUserId();
        List<Following> followerList = followingRepository.findAllByFollowing_UserIdOrderByFollowerDesc(userId);
        List<Integer> targetUserIdList = followerList.stream().map(follower -> follower.getFollower().getUserId()).collect(Collectors.toList());
        List<User> followerUserList = userRepository.findUsersByUserIdInOrderByUserIdDesc(targetUserIdList);
        return followerUserList;
    }

    @GetMapping("/getFollowingsByUserId/{userId}")
    public List<User> getFollowingsByUserId(@PathVariable Integer userId) throws NotFoundException {
        User targetUser = userRepository.findByUserId(userId);
        if (targetUser == null) {
            throw new NotFoundException("Target User Not Found");
        }
        List<Following> followingList = followingRepository.findAllByFollower_UserIdOrderByFollowingDesc(userId);
        List<Integer> targetUserIdList = followingList.stream().map(following -> following.getFollowing().getUserId()).collect(Collectors.toList());
        List<User> followingUserList = userRepository.findUsersByUserIdInOrderByUserIdDesc(targetUserIdList);
        return followingUserList;
    }

    @GetMapping("/getFollowersByUserId/{userId}")
    public List<User> getFollowersByUserId(@PathVariable Integer userId) throws NotFoundException {
        User targetUser = userRepository.findByUserId(userId);
        if (targetUser == null) {
            throw new NotFoundException("Target User Not Found");
        }
        List<Following> followerList = followingRepository.findAllByFollowing_UserIdOrderByFollowerDesc(userId);
        List<Integer> targetUserIdList = followerList.stream().map(follower -> follower.getFollower().getUserId()).collect(Collectors.toList());
        List<User> followerUserList = userRepository.findUsersByUserIdInOrderByUserIdDesc(targetUserIdList);
        return followerUserList;
    }

}
