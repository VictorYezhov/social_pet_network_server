package com.server_for_spn.dao;

import com.server_for_spn.entity.PhotoLikes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Victor on 08.11.2018.
 */
public interface PhotoLikeDao extends JpaRepository<PhotoLikes, Long> {
    List<PhotoLikes> findAllByPhotoId(Long photoId);
}
