package com.wootech.dropthecode.config.auth.controller;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wootech.dropthecode.config.auth.domain.OauthAttributes;
import com.wootech.dropthecode.config.auth.domain.OauthProvider;
import com.wootech.dropthecode.config.auth.domain.UserProfile;
import com.wootech.dropthecode.config.auth.dto.request.AuthorizationRequest;
import com.wootech.dropthecode.config.auth.dto.response.LoginResponse;
import com.wootech.dropthecode.config.auth.dto.response.TokenResponse;
import com.wootech.dropthecode.config.auth.service.OauthService;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import static com.wootech.dropthecode.config.auth.util.UrlUtil.buildFullRequestUrl;

@RestController
public class OauthController {
    private final OauthService oauthService;

    public OauthController(OauthService oauthService) {
        this.oauthService = oauthService;
    }

    @PostMapping("/login/oauth")
    public ResponseEntity<LoginResponse> login(HttpServletRequest request, HttpServletResponse response,
                                               @RequestBody AuthorizationRequest authorizationRequest) {
        OauthProvider oauthProvider = oauthService.findOauthProvider(authorizationRequest.getProviderName());

        TokenResponse accessToken = getToken(request, authorizationRequest, oauthProvider);

        Map<String, Object> oauthUserProfile = getUserProfile(oauthProvider, accessToken);
        UserProfile userProfile = OauthAttributes.extract(authorizationRequest.getProviderName(), oauthUserProfile);

        LoginResponse loginResponse = oauthService.login(userProfile);

        response.addCookie(createCookie("refreshToken", loginResponse.getRefreshToken()));
        return ResponseEntity.ok().body(loginResponse);
    }

    private TokenResponse getToken(HttpServletRequest request, AuthorizationRequest authorizationRequest, OauthProvider oauthProvider) {
        return WebClient.create()
                        .post()
                        .uri(oauthProvider.getTokenUrl())
                        .headers(header -> {
                            header.setBasicAuth(oauthProvider.getClientId(), oauthProvider.getClientSecret());
                            header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                            header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                            header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                        })
                        .bodyValue(tokenRequest(request, authorizationRequest))
                        .retrieve()
                        .bodyToMono(TokenResponse.class)
                        .block();
    }

    private MultiValueMap<String, String> tokenRequest(HttpServletRequest request,
                                                       AuthorizationRequest authorizationRequest) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("code", authorizationRequest.getCode());
        formData.add("grant_type", "authorization_code");

        String redirectUri = UriComponentsBuilder.fromHttpUrl(buildFullRequestUrl(request.getScheme(), request.getServerName(), request
                        .getServerPort(),
                request.getRequestURI(), request.getQueryString()))
                                                 .replaceQuery(null)
                                                 .build()
                                                 .toUriString();

        formData.add("redirect_uri", redirectUri);
        return formData;
    }

    private Map<String, Object> getUserProfile(OauthProvider oauthProvider, TokenResponse accessToken) {
        return WebClient.create()
                        .get()
                        .uri(oauthProvider.getUserInfoUrl())
                        .headers(header -> header.setBearerAuth(accessToken.getAccessToken()))
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                        .block();
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        return cookie;
    }
}
