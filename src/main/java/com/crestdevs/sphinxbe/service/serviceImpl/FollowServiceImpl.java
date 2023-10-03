package com.crestdevs.sphinxbe.service.serviceImpl;

import com.crestdevs.sphinxbe.entity.Follow;
import com.crestdevs.sphinxbe.entity.User;
import com.crestdevs.sphinxbe.exception.ResourceNotFoundException;
import com.crestdevs.sphinxbe.payload.FollowDto;
import com.crestdevs.sphinxbe.repository.FollowRepo;
import com.crestdevs.sphinxbe.repository.UserRepo;
import com.crestdevs.sphinxbe.service.FollowService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FollowRepo followRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public FollowDto createFollower(Integer followerId, Integer followingId) {

        User follwerUser = this.userRepo.findById(followerId)
                .orElseThrow(() -> new ResourceNotFoundException("Follower", "follow_user_id", followerId));

        User followingUser = this.userRepo.findById(followingId)
                .orElseThrow(() -> new ResourceNotFoundException("Following_User", "following_user_id", followingId));

        Follow follow = new Follow();
        follow.setFollowingUser(followingUser);
        follow.setFollowerUser(follwerUser);

        Follow updatedFollow = this.followRepo.save(follow);

        return this.modelMapper.map(updatedFollow, FollowDto.class);
    }

    @Override
    public FollowDto unfollow(Integer follower, Integer following) {
        return null;
    }

    @Override
    public List<FollowDto> getAllFollowerByUserId(Integer userId) {

        return null;
    }
}
