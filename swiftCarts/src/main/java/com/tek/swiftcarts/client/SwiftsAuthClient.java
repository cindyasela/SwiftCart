package com.tek.swiftcarts.client;

import com.tek.swiftcarts.config.SwiftsAuthProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

/**
 * Calls swiftsAuth (the Keycloak-fronting auth service) to actually provision
 * a login identity. Registering a staff member is only complete once BOTH
 * this account and the matching swiftCarts {@code Users} row exist - see
 * UsersServiceImpl#createUser, which calls this before saving that row so the
 * two are never created out of sync.
 */
@Component
public class SwiftsAuthClient {

    private final SwiftsAuthProperties properties;

    private final RestClient restClient;

    public SwiftsAuthClient(SwiftsAuthProperties properties) {
        this.properties = properties;
        this.restClient = RestClient.create();
    }

    // username is set to the person's email so that whatever they type at
    // login (the app only ever asks for email) matches Keycloak's username
    // attribute directly - not dependent on the realm's "login with email"
    // setting, which we can't control from here.
    public String registerUser(String firstName, String lastName, String email, String password) {

        Map<String, Object> body = Map.of(
                "firstName", firstName,
                "lastName", lastName,
                "username", email,
                "email", email,
                "credentials", List.of(Map.of(
                        "type", "password",
                        "value", password,
                        "temporary", false
                )),
                "enabled", true
        );

        try {
            // Body is the bare Keycloak user id (swiftsAuth reads it off the
            // Location header Keycloak returns) - strip stray quotes in case
            // it comes back JSON-encoded rather than as raw text.
            String response = restClient.post()
                    .uri(properties.getBaseUrl() + "/auth/register")
                    .body(body)
                    .retrieve()
                    .body(String.class);

            return response == null ? null : response.replace("\"", "").trim();

        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to create Keycloak account via swiftsAuth", e);
        }
    }
}
