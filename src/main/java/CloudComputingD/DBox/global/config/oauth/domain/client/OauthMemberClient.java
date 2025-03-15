package CloudComputingD.DBox.global.config.oauth.domain.client;

import CloudComputingD.DBox.entity.User;
import CloudComputingD.DBox.global.config.oauth.domain.OauthServerType;

public interface OauthMemberClient {

    OauthServerType supportServer();

    User fetch(String code);
}