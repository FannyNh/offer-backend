package com.thirdmoira.offer_backend.data;

import com.thirdmoira.offer_backend.data.entities.UserEntity;
import com.thirdmoira.offer_backend.data.jpa.UserJpaRepository;
import com.thirdmoira.offer_backend.data.mappers.EntityDomainUserMapper;
import com.thirdmoira.offer_backend.domain.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepository {
    @Autowired
    UserJpaRepository jpaRepository;
    @Autowired
    EntityDomainUserMapper entityDomainUserMapper;
    public User createOrUpdate(String firstName, String lastName, String email, String password, Long groupId, Long userId) {
        UserEntity user = entityDomainUserMapper.toEntity(firstName, lastName, email, password, groupId,userId);
        UserEntity save = jpaRepository.save(user);
        return entityDomainUserMapper.toDomain(save);
    }
    public List<User> get() {
        List<UserEntity> users = jpaRepository.findAll();
        return users.stream()
                .map((it) -> {
                    return entityDomainUserMapper.toDomain(it);
                })
                .toList();
    }





}
