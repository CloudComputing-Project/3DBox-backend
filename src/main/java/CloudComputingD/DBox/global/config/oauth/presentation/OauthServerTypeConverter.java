package CloudComputingD.DBox.global.config.oauth.presentation;

import CloudComputingD.DBox.global.config.oauth.domain.OauthServerType;
import org.springframework.core.convert.converter.Converter;

public class OauthServerTypeConverter implements Converter<String, OauthServerType> {
    @Override
    public OauthServerType convert(String source) {
        return OauthServerType.fromName(source);
    }
}
