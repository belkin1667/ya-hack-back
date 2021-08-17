package com.belkin.yahack.serivce;

import com.belkin.yahack.dao.UserDAO;
import com.belkin.yahack.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDAO userDao;

    public UserService(@Qualifier("database_user") UserDAO userDao) {
        this.userDao = userDao;
    }

    public User addUser(User user) {
        return userDao.save(user);
    }

}
