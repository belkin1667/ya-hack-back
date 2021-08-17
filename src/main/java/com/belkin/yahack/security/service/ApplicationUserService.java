package com.belkin.yahack.security.service;

import java.util.HashSet;
import java.util.Set;

import com.belkin.yahack.model.User;
import com.belkin.yahack.security.ApplicationUserRole;
import com.belkin.yahack.security.dao.ApplicationUserDAO;
import com.belkin.yahack.security.dto.RegistrationRequest;
import com.belkin.yahack.security.exception.UserAlreadyRegisteredException;
import com.belkin.yahack.security.model.ApplicationUser;
import com.belkin.yahack.serivce.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationUserService implements UserDetailsService {

    private final ApplicationUserDAO userDetailsDAO;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDetailsDAO.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));
    }

    public boolean isUserPresent(String username) {
        return userDetailsDAO.findById(username).isPresent();
    }

    public void addUser(ApplicationUser user) {
        userDetailsDAO.save(user);
    }

    public void register(RegistrationRequest registrationRequest, Set<ApplicationUserRole> userRoleSet) {
        if (isUserPresent(registrationRequest.getUsername())) {
            throw new UserAlreadyRegisteredException(registrationRequest.getUsername());
        }

        User user = new User(registrationRequest.getUsername());
        userService.addUser(user);

        ApplicationUser applicationUser = new ApplicationUser(registrationRequest.getUsername(), null,
                passwordEncoder.encode(registrationRequest.getPassword()),
                userRoleSet,
                true, true, true, true);
        addUser(applicationUser);
    }

    public void register(RegistrationRequest registrationRequest, ApplicationUserRole role) {
        Set<ApplicationUserRole> userRoleSet = new HashSet<>();
        userRoleSet.add(role);
        register(registrationRequest, userRoleSet);
    }
}
