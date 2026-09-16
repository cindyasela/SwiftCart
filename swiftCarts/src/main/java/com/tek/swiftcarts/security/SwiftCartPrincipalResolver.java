package com.tek.swiftcarts.security;

import org.springframework.security.core.Authentication;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.List;
import java.util.Map;

@Component
public class SwiftCartPrincipalResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(SwiftCartPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        Jwt jwt = (Jwt) authentication.getPrincipal();

        String userId=jwt.getSubject();
        String userName= jwt.getClaimAsString("preferred_username");
        String email=jwt.getClaimAsString("email");
        String  client= jwt.getClaimAsString("azp");

        Map<String,Object>realmAccess=jwt.getClaim("realm_access");


        List<String> roles =
                (List<String>) realmAccess.get("roles");


        return new SwiftCartPrincipal(
                userId,
                userName,
                email,
                client,
                roles
        );
    }

}
