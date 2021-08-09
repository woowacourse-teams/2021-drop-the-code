package com.wootech.dropthecode.acceptance;

import java.util.HashMap;
import java.util.Map;

import com.wootech.dropthecode.dto.response.OauthTokenResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@RestController
public class FakeGithubController {
    private static final String OAUTH_ID = "OAuth";
    private static final String EMAIL = "@email.com";
    private static final String IMAGE_URL = "s3://image/";
    private static final String GITHUB_URL = "https://github.com/";

    @PostMapping(value = "/fake/login/oauth/access_token", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public OauthTokenResponse fakeAccessToken() {
        return OauthTokenResponse.builder()
                                 .tokenType("Bearer")
                                 .accessToken("access token")
                                 .build();
    }

    @GetMapping(value = "/fake/user")
    public Map<String, Object> fakeUserInfo(@RequestParam String name) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("id", OAUTH_ID + name);
        attributes.put("email", name + EMAIL);
        attributes.put("name", name);
        attributes.put("avatar_url", IMAGE_URL + name);
        attributes.put("html_url", GITHUB_URL + name);
        return attributes;
    }

}
