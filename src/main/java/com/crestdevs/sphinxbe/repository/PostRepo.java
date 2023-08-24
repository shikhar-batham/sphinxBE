
package com.crestdevs.sphinxbe.repository;


import com.crestdevs.sphinxbe.entity.Post;
import com.crestdevs.sphinxbe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {

    List<Post> findByUser(User user);

    List<Post> findByTitleContaining(String title);

    List<Post> findAllByUserId(Integer userId);

}
