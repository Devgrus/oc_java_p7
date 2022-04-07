package com.nnk.springboot.config.oauth.provider;

import com.nnk.springboot.domain.Provider;

public interface OAuth2UserInfo {
    String getProviderId();
    Provider getProvider();
    String getName();
}
