package devut.buzzerbidder.domain.member.controller;

import devut.buzzerbidder.domain.member.dto.UserRequestDto;
import devut.buzzerbidder.domain.member.dto.UserResponseDto;
import devut.buzzerbidder.domain.member.entity.User;
import devut.buzzerbidder.domain.member.service.AuthTokenService;
import devut.buzzerbidder.domain.member.service.UserService;
import devut.buzzerbidder.global.requestcontext.RequestContext;
import devut.buzzerbidder.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthTokenService authTokenService;
    private final RequestContext requestContext;

    @PostMapping("/signup")
    public ApiResponse<UserResponseDto.LoginResponse> signUp(
            @Valid @RequestBody UserRequestDto.EmailSignUpRequest request) {
        UserResponseDto.LoginResponse response = userService.signUp(request);

        return ApiResponse.ok("회원가입에 성공했습니다.", response);
    }

    @PostMapping("/signin")
    public ApiResponse<UserResponseDto.LoginResponse> login(
            @Valid @RequestBody UserRequestDto.EmailLoginRequest request) {
        UserResponseDto.LoginResponse response = userService.login(request);
        
        // 토큰 생성 및 헤더/쿠키에 설정
        setTokensInResponse(response.userInfo().id());
        
        return ApiResponse.ok("로그인에 성공했습니다.", response);
    }
    
    private void setTokensInResponse(Long userId) {
        // User 조회
        User user = userService.findById(userId);
        
        // 토큰 생성
        String accessToken = authTokenService.genAccessToken(user);
        String refreshToken = authTokenService.genRefreshToken(user);
        
        // 헤더에 토큰 설정
        requestContext.setHeader("Authorization", "Bearer " + accessToken);
        requestContext.setHeader("Refresh-Token", refreshToken);
        
        // 쿠키에 토큰 설정
        requestContext.setCookie("accessToken", accessToken);
        requestContext.setCookie("refreshToken", refreshToken);
    }

}
