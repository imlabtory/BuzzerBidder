package devut.buzzerbidder.domain.liveitem.controller;

import devut.buzzerbidder.domain.liveitem.dto.request.LiveItemCreateRequest;
import devut.buzzerbidder.domain.liveitem.dto.response.LiveItemListResponse;
import devut.buzzerbidder.domain.liveitem.dto.request.LiveItemModifyRequest;
import devut.buzzerbidder.domain.liveitem.dto.response.LiveItemResponse;
import devut.buzzerbidder.domain.liveitem.entity.LiveItem.AuctionStatus;
import devut.buzzerbidder.domain.liveitem.service.LiveItemService;
import devut.buzzerbidder.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auction/live")
public class LiveItemController {

    private final LiveItemService liveItemService;

    @PostMapping
    public ApiResponse<LiveItemResponse> createLiveItem(
        @RequestBody LiveItemCreateRequest reqBody,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ){

        LiveItemResponse response = liveItemService.writeLiveItem(reqBody, userDetails.getMember());

        return ApiResponse.ok("경매품 생성",response);

    }

    @PutMapping("/{id}")
    public ApiResponse<LiveItemResponse> modifyLiveItem(
        @PathVariable Long id,
        @RequestBody LiveItemModifyRequest reqBody,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {

        LiveItemResponse response =liveItemService.modifyLiveItem(id, reqBody, userDetails.getMember());

        return ApiResponse.ok("%d번 경매품 수정".formatted(id), response);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteLiveItem(
        @PathVariable Long id,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {

        liveItemService.deleteLiveItem(id, userDetails.getMember());

        return ApiResponse.ok("%d번 경매품 삭제".formatted(id));

    }

    //TODO : QUERYDSL 적용하기 + 페이징
    @GetMapping
    public ApiResponse<LiveItemListResponse> getLiveItems(

        ) {

        LiveItemListResponse response = liveItemService.getLiveItems(

        );

        return ApiResponse.ok("경매품 다건 조회", response);
    }

    @GetMapping("/{id}")
    public ApiResponse<LiveItemResponse> getLiveItem(
        @PathVariable Long id,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {

        LiveItemResponse response = liveItemService.getLiveItem(id, userDetails.getMember());


        return ApiResponse.ok("%d번 경매품 단건 조회".formatted(id), response);
    }

    //TODO : 쿼리 만들고 다시 오기
    @GetMapping("/hot")
    public ApiResponse<LiveItemListResponse> getHotLiveItems(

    ) {

        LiveItemListResponse response = liveItemService.getHotLiveItems(

        );

        return ApiResponse.ok("인기 경매품 다건 조회", response);
    }

    @PatchMapping("/{id}")
    public ApiResponse<Void> changeAuctionStatus(
        @PathVariable Long id,
        @RequestParam AuctionStatus auctionStatus,
        @AuthenticationPrincipal CustomUserDetails userDetails

    ) {

        liveItemService.changeAuctionStatus(id, userDetails.getMember(), auctionStatus);

        return ApiResponse.ok("%d번 경매품 경매 상태 수정".formatted(id));
    }

}