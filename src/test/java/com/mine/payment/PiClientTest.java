package com.mine.payment;

import com.minepi.payment.PiClient;
import com.minepi.payment.exception.PaymentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class PiClientTest {

    private PiClient piClient;

    @BeforeEach
    void setUp() {
        // Replace with valid apiKey and seed for testing.
        String apiKey = "your_api_key";
        String seed = "your_seed";
        boolean isTestNet = true;
        
        piClient = new PiClient(apiKey, seed, isTestNet);
    }

    @Test
    void testCreatePayment() {
        // Setup
        String uid = "your-uid";
        String amount = "1.1";
        String memo = "From app to user test";
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("test", "test metadata");

        // Execute
        Map<String, Object> result = null;
        try {
            result = piClient.createPayment(uid, amount, memo, metadata);
        } catch (IOException e) {
            fail("IOException occurred during test: " + e.getMessage());
        }

        // Verify
        assertNotNull(result);
    }

    @Test
    void testTransfer() {
        // Setup
        String uid = "your-uid";
        String amount = "1.1";
        String memo = "From app to user test";
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("test", "test metadata");
        // Execute
        try {
            piClient.transfer(uid, amount, memo, metadata);
        } catch (PaymentException e) {
            fail("Exception occurred during transfer: " + e.getMessage());
        }
        // Verify
    }


    @Test
    void testCancelPayment() {

        // Execute
        Map<String, Object> result = null;
        try {
            result = piClient.cancelPayment("your-payment-id");
        } catch (IOException e) {
            fail("IOException occurred during test: " + e.getMessage());
        }

        // Verify
        assertNotNull(result);
    }

    @Test
    void testCompletePayment() {

        // Execute
        Map<String, Object> result = null;
        try {
            result = piClient.completePayment("your-payment-id", "your-txid");
        } catch (IOException e) {
            fail("IOException occurred during test: " + e.getMessage());
        }

        // Verify
        assertNotNull(result);
    }

    @Test
    void testGetPayment() {

        // Execute
        Map<String, Object> result = null;
        try {
            result = piClient.getPayment("your-payment-id");
        } catch (IOException e) {
            fail("IOException occurred during test: " + e.getMessage());
        }

        // Verify
        assertNotNull(result);
    }

}
