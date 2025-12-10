package devut.buzzerbidder.domain.member.dto;

import devut.buzzerbidder.domain.member.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public class UserResponseDto {

    @Schema(description = "로그인 응답")
    public record LoginResponse(
            @Schema(description = "회원 정보")
            UserInfo userInfo
    ) {
        public static LoginResponse of(User user) {
            return new LoginResponse(
                    UserInfo.from(user)
            );
        }
    }

    @Schema(description = "회원 정보")
    public record UserInfo(
            @Schema(description = "회원 ID", example = "1")
            Long id,

            @Schema(description = "이메일", example = "user@example.com")
            String email,

            @Schema(description = "이름", example = "홍길동")
            String name,

            @Schema(description = "닉네임", example = "hong123")
            String nickname,

            @Schema(description = "생년월일", example = "1990-01-01")
            LocalDate birthDate,

            @Schema(description = "프로필 이미지 URL", example = "https://example.com/image.jpg")
            String profileImageUrl
    ) {
        public static UserInfo from(User user) {
            return new UserInfo(
                    user.getId(),
                    user.getEmail(),
                    user.getName(),
                    user.getNickname(),
                    user.getBirthDate(),
                    user.getProfileImageUrl()
            );
        }
    }
}

