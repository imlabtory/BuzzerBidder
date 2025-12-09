package devut.buzzerbidder.domain.deal.entity;

import devut.buzzerbidder.domain.deal.enums.Carrier;
import devut.buzzerbidder.domain.deal.enums.DealStatus;
import devut.buzzerbidder.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LiveDeal extends BaseEntity {
    long itemId;
    long buyerId;
    long winningPrice;
    DealStatus status;
    String trackingNumber;
    Carrier carrier;

    public void updateDeliveryInfo(String carrierCode, String trackingNumber) {
        this.carrier = Carrier.fromCode(carrierCode);
        this.trackingNumber = trackingNumber;
    }
}
