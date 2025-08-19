package com.thirdmoira.offer_backend.domain;

import com.thirdmoira.offer_backend.data.OfferRepository;
import com.thirdmoira.offer_backend.domain.models.Offer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OfferService {
    @Autowired
    private OfferRepository offerRepository;
    public Offer createOrUpdate(String name, Long id, String description, Long userId) {
                return offerRepository.createOrUpdate( name,  id,  description,  userId);
    }
}
