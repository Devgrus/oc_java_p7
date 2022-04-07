package com.nnk.springboot.config.oauth;

import com.nnk.springboot.config.auth.CustomUserDetails;
import com.nnk.springboot.config.oauth.provider.GithubUserInfo;
import com.nnk.springboot.config.oauth.provider.OAuth2UserInfo;
import com.nnk.springboot.domain.Role;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Autowired
    public CustomOauth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException, NullPointerException {
        System.out.println(userRequest.getAccessToken().getTokenType().getValue());
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User.getName());
        System.out.println(oAuth2User.getAttributes());
        System.out.println(oAuth2User.getAuthorities());
        return processOAuth2User(userRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("github")) oAuth2UserInfo = new GithubUserInfo(oAuth2User.getAttributes());

        Optional<User> userOptional
                = userRepository.findByUsername(oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId());

        User user;

        if(userOptional.isPresent()) user = userOptional.get();
        else user = User.builder()
                .username(oAuth2UserInfo.getProvider()+"_"+oAuth2UserInfo.getProviderId())
                .role(Role.USER)
                .fullname(oAuth2UserInfo.getName())
                .provider(oAuth2UserInfo.getProvider())
                .build();

        userRepository.save(user);

        return new CustomUserDetails(user, oAuth2User.getAttributes());
    }
}
