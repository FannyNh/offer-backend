package com.thirdmoira.offer_backend.data;

import com.thirdmoira.offer_backend.data.entities.UserEntity;
import com.thirdmoira.offer_backend.data.exceptions.EmailAlreadyExistException;
import com.thirdmoira.offer_backend.data.jpa.UserJpaRepository;
import com.thirdmoira.offer_backend.data.mappers.EntityDomainUserMapper;
import com.thirdmoira.offer_backend.domain.models.User;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserRepository {
    @Autowired
    UserJpaRepository jpaRepository;
    @Autowired
    EntityDomainUserMapper entityDomainUserMapper;
    public User createOrUpdate(String firstName, String lastName, String email, String password, Long groupId, Long userId) {

        UserEntity user = entityDomainUserMapper.toEntity(firstName, lastName, email, password, groupId,userId);
        checkExist(user);
        UserEntity save = jpaRepository.save(user);
        return entityDomainUserMapper.toDomain(save);
    }

    private void checkExist(UserEntity user) {
        Optional<UserEntity> byEmail = jpaRepository.findByEmail(user.getEmail());
        byEmail.ifPresent(it -> {
            throw new EmailAlreadyExistException("user with email :" + it.getEmail() + "already exist");
        });
    }

    public List<User> get() {
        List<UserEntity> users = jpaRepository.findAll();
        return users.stream()
                .map(entityDomainUserMapper::toDomain)
                .toList();
    }





}
