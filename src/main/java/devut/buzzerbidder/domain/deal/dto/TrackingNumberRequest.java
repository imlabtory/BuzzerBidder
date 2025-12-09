package devut.buzzerbidder.domain.deal.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record TrackingNumberRequest(
        @Schema(description = "택배사 코드", example = "kr.cjlogistics")
        String carrierCode,
        @Schema(description = "운송장 번호", example = "1234567890")
        String trackingNumber
) {}
