package CloudComputingD.DBox.global.config.oauth.domain;

import java.util.Locale;

//"kakao"를 통해 OauthServerType.KAKAO를 찾아오기 위해 fromName() 이라는 메서드를 구현
public enum OauthServerType {

    KAKAO,
    ;

    public static OauthServerType fromName(String type) {
        return OauthServerType.valueOf(type.toUpperCase(Locale.ENGLISH));
    }
}
