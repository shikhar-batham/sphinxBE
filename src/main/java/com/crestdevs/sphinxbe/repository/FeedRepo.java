package com.crestdevs.sphinxbe.repository;

import com.crestdevs.sphinxbe.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedRepo extends JpaRepository<Feed, Integer> {
}
