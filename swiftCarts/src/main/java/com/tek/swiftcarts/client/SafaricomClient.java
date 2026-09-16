package com.tek.swiftcarts.client;

import com.tek.swiftcarts.config.MpesaProperties;
import com.tek.swiftcarts.dto.MpesaStkPushRequest;
import com.tek.swiftcarts.dto.MpesaStkPushResponse;
import com.tek.swiftcarts.dto.MpesaTokenResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Component
@AllArgsConstructor
public class SafaricomClient {

    private final MpesaProperties mpesaProperties;
    private  final RestClient restClient;


    public MpesaTokenResponse getAccessToken(){

     try {
         String credentials = mpesaProperties.getConsumerKey() + ":" +
                 mpesaProperties.getConsumerSecret();


         String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

         return restClient
                 .get()
                 .uri(mpesaProperties.getBaseUrl()
                         + "/oauth/v1/generate?grant_type=client_credentials")
                 .header("Authorization", "Basic " + encodedCredentials)
                 .retrieve()
                 .body(MpesaTokenResponse.class);

     } catch (RuntimeException e) {
         throw new RuntimeException(e);
     }

    }

    public String generatePassword( String timestamp){

        String data=mpesaProperties.getBusinessShortCode() + mpesaProperties.getPasskey()
                +timestamp;


        return Base64.getEncoder()
                .encodeToString(data.getBytes(StandardCharsets.UTF_8));

    }

    public MpesaStkPushResponse initiateStkPush(String phoneNumber,
                                                String amount,
                                                String accountReference)
    {

        try {

            MpesaTokenResponse tokenResponse=getAccessToken();

            String timestamp= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

            String password=generatePassword(timestamp);


            MpesaStkPushRequest pushRequest=new MpesaStkPushRequest();

            pushRequest.setAmount(amount);
            pushRequest.setPassword(password);
            pushRequest.setPhoneNumber(phoneNumber);
            pushRequest.setTimestamp(timestamp);
            pushRequest.setBusinessShortCode(mpesaProperties.getBusinessShortCode());
            pushRequest.setPartyA(phoneNumber);
            pushRequest.setPartyB(mpesaProperties.getBusinessShortCode());
            pushRequest.setTransactionType("CustomerPayBillOnline");
            pushRequest.setTransactionDesc("SwiftCart Payments");
            pushRequest.setCallBackUrl(mpesaProperties.getCallbackUrl());
            pushRequest.setAccountReference(accountReference);


            System.out.println(
                    "M-Pesa BusinessShortCode: "
                            + mpesaProperties.getBusinessShortCode()
            );

            return  restClient
                    .post()
                    .uri(mpesaProperties.getBaseUrl()+"/mpesa/stkpush/v1/processrequest")
                    .header(
                            "Authorization",
                            "Bearer " + tokenResponse.getAccessToken())
                    .body(pushRequest)
                    .retrieve()
                    .body(MpesaStkPushResponse.class);



        }  catch (RuntimeException e) {

            throw new RuntimeException(
                    "Failed to initiate M-Pesa STK Push",
                    e);
        }


    }
}
