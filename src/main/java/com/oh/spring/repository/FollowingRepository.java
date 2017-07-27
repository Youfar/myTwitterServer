package com.oh.spring.repository;

import com.oh.spring.entity.Following;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * @author cho.oh
 */
@Repository
public interface FollowingRepository extends JpaRepository<Following, Serializable>{
//    List<Following> findAll();
//    //get following list of userid
////    List<Following> findAllByFollowedIDOrderById(Integer followedID);
//    //get follower list of userid
//    List<Following> findAllByFollowerIDOrderById(Integer followerID);
    List<Following> findAllByFollowing_UserIdOrderByFollowerDesc(Integer userId);
    List<Following> findAllByFollower_UserIdOrderByFollowingDesc(Integer userId);
}
