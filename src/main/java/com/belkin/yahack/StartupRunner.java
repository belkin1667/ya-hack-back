package com.belkin.yahack;

import com.belkin.yahack.security.ApplicationUserRole;
import com.belkin.yahack.security.dto.RegistrationRequest;
import com.belkin.yahack.security.service.ApplicationUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
@RequiredArgsConstructor
public class StartupRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        generateMockUsers();
    }

    private final ApplicationUserService applicationUserService;


    private void generateMockUsers() {
        RegistrationRequest user = new RegistrationRequest();
        user.setUsername("user");
        user.setPassword("pass");
        applicationUserService.register(user, ApplicationUserRole.USER);

        RegistrationRequest author = new RegistrationRequest();
        author.setUsername("author");
        author.setPassword("pass");
        applicationUserService.register(author, ApplicationUserRole.AUTHOR);

    }
}