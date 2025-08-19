package com.thirdmoira.offer_backend.data;

import com.thirdmoira.offer_backend.data.entities.OfferEntity;
import com.thirdmoira.offer_backend.data.jpa.OfferJpaRepository;
import com.thirdmoira.offer_backend.data.mappers.EntityDomainOfferMapper;
import com.thirdmoira.offer_backend.domain.models.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OfferRepository {
    @Autowired
    private OfferJpaRepository jpaRepository;
    @Autowired
    private EntityDomainOfferMapper entityDomainOfferMapper;

    public Offer createOrUpdate(String name, Long id, String description, Long userId) {

        OfferEntity offer = entityDomainOfferMapper.toEntity(name, id, description, userId);

        OfferEntity save = jpaRepository.save(offer);
        return entityDomainOfferMapper.toDomain(save);
    }
}
