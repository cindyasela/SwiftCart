package com.tek.swiftcarts.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Shape of the callback Safaricom's Daraja API POSTs to /api/v1/mpesa/callback
 * once an STK push is completed (paid, cancelled, or timed out) by the customer.
 * CallbackMetadata is only present when ResultCode is 0 (success).
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MpesaCallbackDTO {

    @JsonProperty("Body")
    private Body body;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Body {
        @JsonProperty("stkCallback")
        private StkCallback stkCallback;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StkCallback {
        @JsonProperty("MerchantRequestID")
        private String merchantRequestId;

        @JsonProperty("CheckoutRequestID")
        private String checkoutRequestId;

        @JsonProperty("ResultCode")
        private Integer resultCode;

        @JsonProperty("ResultDesc")
        private String resultDesc;

        @JsonProperty("CallbackMetadata")
        private CallbackMetadata callbackMetadata;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CallbackMetadata {
        @JsonProperty("Item")
        private List<CallbackItem> item;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CallbackItem {
        @JsonProperty("Name")
        private String name;

        @JsonProperty("Value")
        private Object value;
    }
}
