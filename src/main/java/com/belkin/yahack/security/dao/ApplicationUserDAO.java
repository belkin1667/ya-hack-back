package com.belkin.yahack.security.dao;

import com.belkin.yahack.security.model.ApplicationUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("database_appuser")
public interface ApplicationUserDAO extends CrudRepository<ApplicationUser, String> {

}

