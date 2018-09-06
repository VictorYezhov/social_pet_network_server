package com.server_for_spn.dao;

import com.server_for_spn.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Victor on 06.09.2018.
 */
public interface PhotoDao extends JpaRepository<Photo, Long> {

}
