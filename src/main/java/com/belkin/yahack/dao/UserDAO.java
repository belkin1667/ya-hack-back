package com.belkin.yahack.dao;

import com.belkin.yahack.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("database_user")
public interface UserDAO extends CrudRepository<User, String> {

}


