package devut.buzzerbidder.domain.deal.entity;

import devut.buzzerbidder.domain.deal.enums.Carrier;
import devut.buzzerbidder.domain.deal.enums.DealStatus;
import devut.buzzerbidder.global.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LiveDeal extends BaseEntity {

    @JoinColumn(nullable = false)
    long itemId; // TODO: Item Entity와 연관관계 설정 필요

    @JoinColumn(nullable = false)
    long buyerId; // TODO: User Entity와 연관관계 설정 필요

    @Column(nullable = false)
    long winningPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    DealStatus status;

    String trackingNumber;

    @Enumerated(EnumType.STRING)
    Carrier carrier;

    public void updateDeliveryInfo(String carrierCode, String trackingNumber) {
        this.carrier = Carrier.fromCode(carrierCode);
        this.trackingNumber = trackingNumber;
    }
}
