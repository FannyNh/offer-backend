package com.thirdmoira.offer_backend.api;

// Import des annotations nécessaires de Spring

import com.thirdmoira.offer_backend.api.rest.ApiCreateUserRequest;
import com.thirdmoira.offer_backend.api.rest.ApiUser;
import com.thirdmoira.offer_backend.domain.UserService;
import com.thirdmoira.offer_backend.domain.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Annotation pour dire que cette classe est un contrôleur REST
@RestController

// Spécifie la route de base pour toutes les requêtes traitées par ce contrôleur
@RequestMapping("/api/users")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PutMapping(consumes = "application/json", produces = "application/json")

    public ApiUser createOrUpdateUser(@RequestBody ApiCreateUserRequest request) {
        log.info("request creat user :" + request);
        // Retourne le user creer
        User newUser = userService.createOrUpdate(request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                request.getGroupId(),
                request.getUserId());

        return toApi(newUser);
    }

    @GetMapping(produces = "application/json")
    public List<ApiUser> getUsers() {
        log.info("get test");
        List<User> users = userService.get();

        return users.stream()
                .map(UserController::toApi)
                .toList();
    }

    private static ApiUser toApi(User save) {
        ApiUser newUser = new ApiUser();
        newUser.setUserId(save.getUserId());
        newUser.setFirstName(save.getFirstName());
        newUser.setLastName(save.getLastName());
        newUser.setEmail(save.getEmail());
        newUser.setPassword(save.getPassword());
        newUser.setGroupId(save.getGroupId());
        return newUser;
    }
}
