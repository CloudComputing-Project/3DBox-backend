package CloudComputingD.DBox.service;

import CloudComputingD.DBox.dto.UserLoginResponseDTO;
import CloudComputingD.DBox.entity.Folder;
import CloudComputingD.DBox.entity.User;
import CloudComputingD.DBox.repository.FolderRepository;
import CloudComputingD.DBox.repository.UserRepository;
import CloudComputingD.DBox.global.config.oauth.domain.OauthServerType;
import CloudComputingD.DBox.global.config.oauth.domain.authcode.AuthCodeRequestUrlProviderComposite;
import CloudComputingD.DBox.global.config.oauth.domain.client.OauthMemberClientComposite;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OauthService {

    private final AuthCodeRequestUrlProviderComposite authCodeRequestUrlProviderComposite;
    private final OauthMemberClientComposite oauthMemberClientComposite;
    private final UserRepository oauthMemberRepository;
    private final FolderRepository folderRepository;

    public String getAuthCodeRequestUrl(OauthServerType oauthServerType) {
        return authCodeRequestUrlProviderComposite.provide(oauthServerType);
    }

    @Transactional
    public UserLoginResponseDTO login(OauthServerType oauthServerType, String authCode) {
        User user = oauthMemberClientComposite.fetch(oauthServerType, authCode);
//        User saved = oauthMemberRepository.findByOauthId(user.oauthId())
//                .orElseGet(() -> oauthMemberRepository.save(user));

        Optional<User> optionalUser = oauthMemberRepository.findByOauthId(user.oauthId());

        boolean isNewUser = optionalUser.isEmpty();

        if(isNewUser) {
            //처음 로그인하는 사용자이므로 db에 저장
            oauthMemberRepository.save(user);

            //유저 정보 저장 후 해당하는 유저의 root 폴더까지 생성하여 저장
            Folder folder = Folder.builder()
                    .name(user.oauthId().oauthServerId() + "_root")
                    .created_date(LocalDateTime.now())
                    .is_root(true)
                    .user(user)
                    .build();

            folderRepository.save(folder);
        }

        optionalUser = oauthMemberRepository.findByOauthId(user.oauthId());
        Folder rootFolder = folderRepository.findRootFolder(optionalUser.get().oauthId().oauthServerId());
        Long id = rootFolder.getId();

        String nickname = isNewUser ? null : optionalUser.get().getUserNickname();
        System.out.println(nickname);
        return UserLoginResponseDTO.builder()
                .userId(optionalUser.get().oauthId().oauthServerId())
                .rootFolderId(id)
                .nickname(nickname)
                .build();
    }

    public String getProfileImageLink(String oauthServerId) {
        User user = oauthMemberRepository.findByOauthServerId(oauthServerId);
        return user.profileImageUrl();
    }

    public String setUserNickname(String oauthServerId, String nickname) {
        User user = oauthMemberRepository.findByOauthServerId(oauthServerId);
        user.setUserNickname(nickname);
        oauthMemberRepository.save(user);
        return nickname;
    }

//    public String getUserNickname(String oauthServerId) {
//        User user = oauthMemberRepository.findByOauthServerId(oauthServerId);
//        return user.nickname();
//    }
}