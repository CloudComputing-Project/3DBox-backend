package CloudComputingD.DBox.global.config.oauth.domain.authcode;

import CloudComputingD.DBox.global.config.oauth.domain.OauthServerType;

//여러 서비스에게 인가 코드를 받아오기 위한 URL 제공 인터페이스
public interface AuthCodeRequestUrlProvider {
    OauthServerType supportServer();
    String provide();
}
