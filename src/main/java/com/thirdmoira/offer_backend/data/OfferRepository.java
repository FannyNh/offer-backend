package com.thirdmoira.offer_backend.data;

import com.thirdmoira.offer_backend.data.entities.OfferEntity;
import com.thirdmoira.offer_backend.data.jpa.OfferJpaRepository;
import com.thirdmoira.offer_backend.data.mappers.EntityDomainOfferMapper;
import com.thirdmoira.offer_backend.domain.models.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public List<Offer> get() {
        List<OfferEntity> offers = jpaRepository.findAll();


        return offers.stream().map(entityDomainOfferMapper::toDomain).toList();
    }

    public void delete(Long id) {
        jpaRepository.deleteById(id);
    }

    public Offer update(String name, Long id, String description, Long userId) {
        return null;
    }

    public Offer create(String name, Long id, String description, Long userId) {
        OfferEntity newOffer = entityDomainOfferMapper.toEntity(name, id, description, userId);
        OfferEntity save = jpaRepository.save(newOffer);
        return entityDomainOfferMapper.toDomain(save);
    }
}
