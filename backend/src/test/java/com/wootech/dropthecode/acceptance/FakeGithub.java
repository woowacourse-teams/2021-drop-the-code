package com.wootech.dropthecode.acceptance;

import com.wootech.dropthecode.dto.response.OauthTokenResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@RestController
public class FakeGithub {

    @PostMapping(value = "/fake/oauth/token", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public OauthTokenResponse test() {
        r
    }

}
