package devut.buzzerbidder.domain.liveitem.service;

import devut.buzzerbidder.domain.liveitem.dto.request.LiveItemCreateRequest;
import devut.buzzerbidder.domain.liveitem.dto.response.LiveItemResponse;
import devut.buzzerbidder.domain.liveitem.entity.LiveItem;
import devut.buzzerbidder.domain.liveitem.repository.LiveItemRepository;
import devut.buzzerbidder.global.exeption.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LiveItemService {

    private final LiveItemRepository liveItemRepository;

    @Transactional
    public LiveItemResponse writeLiveItem(LiveItemCreateRequest reqBody, Member member) {

        LiveItem liveItem = new LiveItem(reqBody, member);

        if (reqBody.imageUrls() == null || reqBody.imageUrls().isEmpty()) {
            throw new BusinessException(ErrorCode.IMAGE_FILE_EMPTY);
        }

        reqBody.imageUrls().forEach(url ->
            liveItem.addImage(new LiveItemImage(url, liveItem)));

        liveItemRepository.save(liveItem);

        return LiveItemResponse(liveItem);

    }

}

/*
writeLiveItems > 생성 > 이미지 빈 것 검사 > 이미지 추가 > 만든거 반환
modifyLiveItems
deleteLiveItems
getLiveItems
getLiveItem
getHotLiveItems
changeAuctionStatus
 */