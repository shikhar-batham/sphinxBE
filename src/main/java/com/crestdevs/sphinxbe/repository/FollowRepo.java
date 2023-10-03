package com.crestdevs.sphinxbe.repository;

import com.crestdevs.sphinxbe.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepo extends JpaRepository<Follow, Integer> {

    Follow findByFollowingUserId(Integer followingUserId);
}
