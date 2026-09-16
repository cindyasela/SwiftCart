package com.tek.swiftcarts.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SwiftCartPrincipal {

    private String keyCloakId;
    private  String username;
    private String email;
    private  String client;
    private List<String> roles;

}
