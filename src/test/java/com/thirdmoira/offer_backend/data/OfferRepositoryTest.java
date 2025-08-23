package com.thirdmoira.offer_backend.data;

import com.thirdmoira.offer_backend.data.entities.OfferEntity;
import com.thirdmoira.offer_backend.data.jpa.OfferJpaRepository;
import com.thirdmoira.offer_backend.data.mappers.EntityDomainOfferMapper;
import com.thirdmoira.offer_backend.domain.models.Offer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OfferRepositoryTest {
    @InjectMocks
    private OfferRepository offerRepository;
    @Mock
    private OfferJpaRepository offerJpaRepository;
    @Mock
    private EntityDomainOfferMapper entityDomainOfferMapper;

    @Test
    void should_create_offer_entity_and_create_jpa_offer_version_entity_when_create_offer() {
        //given=mock
        when(entityDomainOfferMapper.toEntity(any(), any(), any(), any()))
                .thenReturn(new OfferEntity());
        when(entityDomainOfferMapper.toDomain(any(OfferEntity.class)))
                .thenReturn(new Offer());
        when(offerJpaRepository.save(any(OfferEntity.class)))
                .thenReturn(new OfferEntity());

        //when
        Offer offer = offerRepository.create("jkdjksjksd", null, "boubou", 3L);
        //then
        verify(offerJpaRepository).save(any(OfferEntity.class));
        assertEquals(Offer.class, offer.getClass());

    }
//    void should_update_offer_when_asked()
//    void should_init_offer_version_when_asked()
//    void should_create_new_offer_version_when_offer_version_and_offer_versionid_exist()


}