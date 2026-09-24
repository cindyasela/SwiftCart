package com.tek.swiftcarts.client;

import com.tek.swiftcarts.config.MpesaProperties;
import com.tek.swiftcarts.dto.MpesaStkPushRequest;
import com.tek.swiftcarts.dto.MpesaStkPushResponse;
import com.tek.swiftcarts.dto.MpesaTokenResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Component
@AllArgsConstructor
@Slf4j
public class SafaricomClient {

    private final MpesaProperties mpesaProperties;
    private  final RestClient restClient;


    public MpesaTokenResponse getAccessToken(){

     try {
         log.debug("Fetching M-Pesa access token");
         String credentials = mpesaProperties.getConsumerKey() + ":" +
                 mpesaProperties.getConsumerSecret();

         String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

         String tokenUrl = mpesaProperties.getBaseUrl() + "/oauth/v1/generate?grant_type=client_credentials";
         log.debug("Token URL: {}", tokenUrl);

         MpesaTokenResponse response = restClient
                 .get()
                 .uri(tokenUrl)
                 .header("Authorization", "Basic " + encodedCredentials)
                 .retrieve()
                 .body(MpesaTokenResponse.class);

         log.info("Access token obtained, expires in: {}", response.getExpiresIn());
         return response;

     } catch (RuntimeException e) {
         log.error("Failed to fetch access token from M-Pesa", e);
         throw new RuntimeException("Failed to get M-Pesa access token: " + e.getMessage(), e);
     }

    }

    public String generatePassword( String timestamp){

        String data=mpesaProperties.getBusinessShortCode() + mpesaProperties.getPasskey()
                +timestamp;


        return Base64.getEncoder()
                .encodeToString(data.getBytes(StandardCharsets.UTF_8));

    }

    private String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new RuntimeException("Phone number cannot be empty");
        }

        String cleaned = phoneNumber.replaceAll("[^0-9]", "");

        if (cleaned.startsWith("0")) {
            cleaned = "254" + cleaned.substring(1);
        }

        if (!cleaned.startsWith("254")) {
            cleaned = "254" + cleaned;
        }

        if (!cleaned.matches("^254\\d{9}$")) {
            throw new RuntimeException("Invalid phone number format. Expected: 254XXXXXXXXX (Kenya format)");
        }

        log.debug("Phone number formatted from {} to {}", phoneNumber, cleaned);
        return cleaned;
    }

    public MpesaStkPushResponse initiateStkPush(String phoneNumber,
                                                String amount,
                                                String accountReference)
    {

        try {
            String formattedPhone = formatPhoneNumber(phoneNumber);
            log.info("Initiating STK push for phone: {}, amount: {}, account: {}",
                    formattedPhone, amount, accountReference);

            MpesaTokenResponse tokenResponse=getAccessToken();

            if (tokenResponse == null || tokenResponse.getAccessToken() == null) {
                log.error("Failed to get M-Pesa access token");
                throw new RuntimeException("Failed to obtain access token from M-Pesa");
            }

            log.info("Access token obtained successfully");

            String timestamp= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

            String password=generatePassword(timestamp);


            MpesaStkPushRequest pushRequest=new MpesaStkPushRequest();

            pushRequest.setAmount(amount);
            pushRequest.setPassword(password);
            pushRequest.setPhoneNumber(formattedPhone);
            pushRequest.setTimestamp(timestamp);
            pushRequest.setBusinessShortCode(mpesaProperties.getBusinessShortCode());
            pushRequest.setPartyA(formattedPhone);
            pushRequest.setPartyB(mpesaProperties.getBusinessShortCode());
            pushRequest.setTransactionType("CustomerPayBillOnline");
            pushRequest.setTransactionDesc("SwiftCart Payments");
            pushRequest.setCallBackUrl(mpesaProperties.getCallbackUrl());
            pushRequest.setAccountReference(accountReference);

            log.debug("STK Push Request: {}", pushRequest);
            log.info("M-Pesa BusinessShortCode: {}", mpesaProperties.getBusinessShortCode());

            MpesaStkPushResponse response = restClient
                    .post()
                    .uri(mpesaProperties.getBaseUrl()+"/mpesa/stkpush/v1/processrequest")
                    .header(
                            "Authorization",
                            "Bearer " + tokenResponse.getAccessToken())
                    .body(pushRequest)
                    .retrieve()
                    .body(MpesaStkPushResponse.class);

            log.info("STK Push Response: {}", response);
            return response;

        }  catch (RuntimeException e) {
            log.error("Failed to initiate M-Pesa STK Push", e);
            throw new RuntimeException(
                    "Failed to initiate M-Pesa STK Push: " + e.getMessage(),
                    e);
        }


    }

    public String queryTransactionStatus(String checkoutRequestId) {
        try {
            MpesaTokenResponse tokenResponse = getAccessToken();
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String password = generatePassword(timestamp);

            String requestBody = "{\n" +
                    "  \"BusinessShortCode\": \"" + mpesaProperties.getBusinessShortCode() + "\",\n" +
                    "  \"Password\": \"" + password + "\",\n" +
                    "  \"Timestamp\": \"" + timestamp + "\",\n" +
                    "  \"CheckoutRequestID\": \"" + checkoutRequestId + "\"\n" +
                    "}";

            return restClient
                    .post()
                    .uri(mpesaProperties.getBaseUrl() + "/mpesa/stkpushquery/v1/query")
                    .header("Authorization", "Bearer " + tokenResponse.getAccessToken())
                    .header("Content-Type", "application/json")
                    .body(requestBody)
                    .retrieve()
                    .body(String.class);


        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to query M-Pesa transaction status", e);
        }
    }
}
