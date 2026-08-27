package com.fragma.entity;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class OAuthProvider {

    private String registrationId;

    private String clientId;

    private String clientSecret;

    private String authorizationUri;

    private String tokenUri;

    private String userInfoUri;

    private String RedirectUri;

    private Set<String> scope;

}
