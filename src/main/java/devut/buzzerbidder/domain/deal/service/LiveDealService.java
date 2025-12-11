package devut.buzzerbidder.domain.deal.service;

import devut.buzzerbidder.domain.deal.entity.LiveDeal;
import devut.buzzerbidder.domain.deal.repository.LiveDealRepository;
import devut.buzzerbidder.domain.deliveryTracking.dto.response.DeliveryTrackingResponse;
import devut.buzzerbidder.domain.deliveryTracking.service.DeliveryTrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LiveDealService {

    private final LiveDealRepository liveDealRepository;
    private final DeliveryTrackingService deliveryTrackingService;

    public LiveDeal findByIdOrThrow(Long dealId) {
        return liveDealRepository.findById(dealId)
                .orElseThrow(() -> new IllegalArgumentException("LiveDeal not found with id: " + dealId));
    }

    public void patchDeliveryInfo(Long dealId, String carrierCode, String trackingNumber) {
        LiveDeal liveDeal = findByIdOrThrow(dealId);
        liveDeal.updateDeliveryInfo(carrierCode, trackingNumber);
    }

    public DeliveryTrackingResponse track(Long dealId) {
        LiveDeal liveDeal = findByIdOrThrow(dealId);

        String carrierCode = liveDeal.getCarrier() != null ? liveDeal.getCarrier().getCode() : null;
        String trackingNumber = liveDeal.getTrackingNumber();

        if (carrierCode == null || carrierCode.isBlank() || trackingNumber == null || trackingNumber.isBlank()) {
            return null;
        }

        return deliveryTrackingService.track(carrierCode, trackingNumber);
    }

}
