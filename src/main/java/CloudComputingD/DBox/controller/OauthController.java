package CloudComputingD.DBox.controller;

import CloudComputingD.DBox.dto.UserLoginResponseDTO;
import CloudComputingD.DBox.service.OauthService;
import CloudComputingD.DBox.global.config.oauth.domain.OauthServerType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "3.유저 로그인")
@RequiredArgsConstructor
@RequestMapping("/oauth")
@RestController
public class OauthController {

    private final OauthService oauthService;

    @SneakyThrows
    @GetMapping("/{oauthServerType}")
    @Operation(summary = "카카오 로그인 url 생성 및 redirect", description = "인가 코드를 얻기 위한 로그인 url 생성하여 client redirect")
    ResponseEntity<Void> redirectAuthCodeRequestUrl(
            @PathVariable OauthServerType oauthServerType,
            HttpServletResponse response
    ) {
        String redirectUrl = oauthService.getAuthCodeRequestUrl(oauthServerType);
        response.sendRedirect(redirectUrl);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/login/{oauthServerType}")
    @Operation(summary = "인가 토큰을 통해 사용자 정보 조회", description = "이름, 사용자 고유 id, 프로필 이미지 정보 db에 저장")
    ResponseEntity<UserLoginResponseDTO> login(
            @PathVariable OauthServerType oauthServerType,
            @RequestParam("code") String code
    ) {
        UserLoginResponseDTO userLoginResponseDTO = oauthService.login(oauthServerType, code);

        return new ResponseEntity<>(userLoginResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/login/profile/{oauthServerId}")
    @Operation(summary = "사용자 id를 통해 프로필 링크 조회", description = "사용자 고유 id를 받아 프로필 이미지 링크 반환")
    ResponseEntity<String> getUserProfileImageLink(
            @PathVariable String oauthServerId
    ) {
        String imageLink = oauthService.getProfileImageLink(oauthServerId);
        return ResponseEntity.ok(imageLink);
    }

    @PatchMapping ("/login/{oauthServerId}/nickname/{nickname}")
    @Operation(summary = "사용자 id를 통해 유저 닉네임 설정", description = "사용자 고유 id를 받아 유저 닉네임 설정 후 반환 유저 닉네임 반환")
    ResponseEntity<String> setUserNickname(
            @PathVariable String oauthServerId,
            @PathVariable String nickname
    ) {
        String userNickname = oauthService.setUserNickname(oauthServerId, nickname);
        return ResponseEntity.ok(userNickname);
    }

    //    @GetMapping("/login/{oauthServerId}/nickname/")
//    @Operation(summary = "사용자 id를 통해 유저 닉네임 조회", description = "사용자 고유 id를 받아 유저 닉네임 반환")
//    ResponseEntity<String> getUserNickname(
//            @PathVariable String oauthServerId
//    ) {
//        String nickname = oauthService.getUserNickname(oauthServerId);
//        return ResponseEntity.ok(nickname);
//    }
}