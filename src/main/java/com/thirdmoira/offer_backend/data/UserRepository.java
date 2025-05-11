package com.thirdmoira.offer_backend.data;

import com.thirdmoira.offer_backend.data.entities.UserEntity;
import com.thirdmoira.offer_backend.data.jpa.UserJpaRepository;
import com.thirdmoira.offer_backend.domain.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepository {
    @Autowired
    UserJpaRepository jpaRepository;
    public User createOrUpdate(String firstName, String lastName, String email, String password, Long groupId, Long userId) {
        UserEntity user = toEntity(firstName, lastName, email, password, groupId,userId);
        UserEntity save = jpaRepository.save(user);
        return toDomain(save);
    }
    public List<User> get() {
        List<UserEntity> users = jpaRepository.findAll();
        return users.stream()
                .map(UserRepository::toDomain)
                .toList();
    }
    private static UserEntity toEntity(String firstName, String lastName, String email, String password, Long groupId, Long userId) {
        UserEntity user = new UserEntity();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setGroupId(groupId);
        user.setUserId(userId);
        return user;
    }

    private static User toDomain(UserEntity save) {
        User newUser = new User();
        newUser.setUserId(save.getUserId());
        newUser.setFirstName(save.getFirstName());
        newUser.setLastName(save.getLastName());
        newUser.setEmail(save.getEmail());
        newUser.setPassword(save.getPassword());
        newUser.setGroupId(save.getGroupId());
        return newUser;
    }


}
