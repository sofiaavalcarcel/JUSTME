package com.sena.JustMe.Controller;

import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/stripe")
public class StripeController {

    @PostMapping("/create-payment-intent")
    public Map<String, Object> createPaymentIntent(@RequestBody Map<String, Object> data) throws Exception {
        Long amount = Long.parseLong(data.get("amount").toString()); // valor en centavos

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amount)
                .setCurrency("cop")
                .build();

        PaymentIntent intent = PaymentIntent.create(params);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("clientSecret", intent.getClientSecret());
        return responseData;
    }
}

