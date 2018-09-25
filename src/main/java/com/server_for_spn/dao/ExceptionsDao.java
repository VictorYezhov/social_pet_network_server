package com.server_for_spn.dao;

import com.server_for_spn.entity.Exceptions;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Victor on 21.09.2018.
 */
public interface ExceptionsDao  extends JpaRepository<Exceptions, Long> {

}
