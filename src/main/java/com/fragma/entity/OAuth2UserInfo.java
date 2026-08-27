package com.fragma.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OAuth2UserInfo {

	
	private String provider;
    private String providerId;
    private String name;
    private String email;
    private String picture;
}
