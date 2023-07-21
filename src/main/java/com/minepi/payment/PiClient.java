package com.minepi.payment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.minepi.payment.exception.PaymentException;
import com.minepi.payment.utils.OkHttpUtil;
import org.stellar.sdk.*;
import org.stellar.sdk.responses.AccountResponse;
import org.stellar.sdk.responses.SubmitTransactionResponse;
import shadow.com.google.common.collect.ImmutableMap;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author pipicoming
 * @Description　PiClient is a client SDK for facilitating app to user transactions on the Pi Network.
 */
public class PiClient {


    private static final String BASE_URL = "https://api.minepi.com";
    private static final String HORIZON_TEST_NETWORK_URL = "https://api.testnet.minepi.com";
    private static final String HORIZON_MAIN_NETWORK_URL = "https://api.mainnet.minepi.com";
    private static final int BASE_FEE = 100000;

    private String network;
    private String seed;
    private String authorization;
    private Server horizonServer;

    /**
     * Constructs a new PiClient.
     *
     * @param apiKey   the API key for authenticating with the Pi Network.
     * @param seed     the secret seed for signing transactions.
     * @param isTestNet whether to use the test network or the main network.
     */
    public PiClient(String apiKey, String seed, boolean isTestNet) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalArgumentException("API key must not be empty");
        }
        if (seed == null || seed.trim().isEmpty()) {
            throw new IllegalArgumentException("Seed must not be empty");
        }
        this.seed = seed;
        this.network = isTestNet ? "Pi Testnet" : "Pi Network";
        this.authorization = "Key " + apiKey;
        String horizonUrl = isTestNet ? HORIZON_TEST_NETWORK_URL : HORIZON_MAIN_NETWORK_URL;
        this.horizonServer = new Server(horizonUrl);
    }

    /**
     * Transfers a specified amount of Pi to the specified user.
     *
     * @param uid      the user ID of the recipient.
     * @param amount   the amount of Pi to transfer.
     * @param memo     an optional memo to attach to the transaction.
     * @param metadata additional metadata to include with the transaction.
     * @throws AccountRequiresMemoException if the account requires a memo.
     * @throws IOException                  if there is a network or IO problem.
     */
    public void transfer(String uid, String amount, String memo, Map<String, Object> metadata) throws PaymentException {
        // Send API request to the Pi server to create a payment
        // Create Payment
        Map<String, Object> createPaymentResult;
        try {
            createPaymentResult = createPayment(uid, amount, memo, metadata);
        } catch (IOException e) {
            throw new PaymentException("Error creating payment", e);
        }
        String createError = (String) createPaymentResult.get("error");
        if (createError != null) {
            throw new PaymentException(createError);
        }

        String paymentId = (String) createPaymentResult.get("identifier");
        String toAddress = (String) createPaymentResult.get("to_address");

        // Submit the transaction to the Pi blockchain
        String txId;
        try {
            txId = submitPayment(paymentId, toAddress, amount);
        } catch (IOException | AccountRequiresMemoException e) {
            throw new PaymentException("Error submitting payment", e);
        }
        if (txId == null) {
            throw new PaymentException("submitPayment error, paymentId:" + paymentId);
        }

        // Complete the payment
        try {
            completePayment(paymentId, txId);
        } catch (IOException e) {
            throw new PaymentException("Error completing payment", e);
        }

    }

    /**
     * Submits a payment.
     *
     * @param paymentId the ID of the payment.
     * @param toAddress the address to send the payment to.
     * @param amount    the amount of Pi to send.
     * @return the transaction hash of the submitted payment.
     * @throws IOException                  if there is a network or IO problem.
     * @throws AccountRequiresMemoException if the account requires a memo.
     */
    public String submitPayment(String paymentId, String toAddress, String amount) throws IOException, AccountRequiresMemoException {
        KeyPair source = KeyPair.fromSecretSeed(seed);
        KeyPair destination = KeyPair.fromAccountId(toAddress);
        AccountResponse sourceAccount = horizonServer.accounts().account(source.getAccountId());
        // Start building the transaction.
        Transaction transaction = new TransactionBuilder(sourceAccount, new Network(network))
                .addOperation(new PaymentOperation.Builder(destination.getAccountId(), new AssetTypeNative(), amount).build())
                .addMemo(Memo.text(paymentId))
                .addPreconditions(TransactionPreconditions.builder().timeBounds(TimeBounds.expiresAfter(300)).build())
                .setBaseFee(BASE_FEE) // 0.01π
                .build();
        // Sign the transaction to prove you are actually the person sending it.
        transaction.sign(source);
        SubmitTransactionResponse response = horizonServer.submitTransaction(transaction);
        return response.getHash();
    }

    /**
     * Creates a new payment request.
     *
     * @param uid      the user ID of the recipient.
     * @param amount   the amount of Pi to transfer.
     * @param memo     an optional memo to attach to the transaction.
     * @param metadata additional metadata to include with the transaction.
     * @return a Map with the payment details.
     * @throws IOException if there is a network or IO problem during the payment creation.
     */
    public Map createPayment(String uid, String amount, String memo, Map<String, Object> metadata) throws IOException {
        // Parameter validation
        if (uid == null || uid.trim().isEmpty()) {
            throw new IllegalArgumentException("uid cannot be null or empty");
        }

        if (amount == null || amount.trim().isEmpty()) {
            throw new IllegalArgumentException("amount cannot be null or empty");
        }

        if (memo == null || memo.trim().isEmpty()) {
            throw new IllegalArgumentException("memo cannot be null or empty");
        }

        if (metadata == null || metadata.isEmpty()) {
            throw new IllegalArgumentException("metadata cannot be null or empty and must have at least one element");
        }
        Map<String, Object> paymentMap = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("uid", uid);
        dataMap.put("amount", amount);
        dataMap.put("memo", memo);
        dataMap.put("metadata", metadata);
        paymentMap.put("payment", dataMap);

        String response = OkHttpUtil.doPost(BASE_URL + "/v2/payments",
                JSON.toJSONString(paymentMap), authorization);
        return JSON.parseObject(response, Map.class);

    }

    /**
     * Approve a payment before it has been submitted to the Pi blockchain.
     * App-To-User payments do not need this.
     * @param paymentId the ID of the payment.
     * @return a Map with the details of the completed payment.
     * @throws IOException if there is a network or IO problem during the payment completion.
     */
    public Map approvePayment(String paymentId) throws IOException {
        String response = OkHttpUtil.doPost(BASE_URL + String.format("/v2/payments/%s/approve", paymentId),
                new JSONObject().toJSONString(), authorization);
        return JSON.parseObject(response, Map.class);
    }

    /**
     * Completes a payment after it has been submitted to the Pi blockchain.
     *
     * @param paymentId the ID of the payment.
     * @param txId      the transaction hash of the submitted payment.
     * @return a Map with the details of the completed payment.
     * @throws IOException if there is a network or IO problem during the payment completion.
     */
    public Map completePayment(String paymentId, String txId) throws IOException {
        String response = OkHttpUtil.doPost(BASE_URL + String.format("/v2/payments/%s/complete", paymentId),
                JSONObject.toJSONString(ImmutableMap.of("txid", txId)), authorization);
        return JSON.parseObject(response, Map.class);

    }

    /**
     * Cancels a payment.
     *
     * @param paymentId the ID of the payment to cancel.
     * @return a Map with the details of the cancelled payment.
     * @throws IOException if there is a network or IO problem during the payment cancellation.
     */
    public Map cancelPayment(String paymentId) throws IOException {
        String response = OkHttpUtil.doPost(BASE_URL + String.format("/v2/payments/%s/cancel", paymentId) ,
                new JSONObject().toJSONString(), authorization);
        return JSON.parseObject(response, Map.class);
    }

    /**
     * Retrieves a list of incomplete server payments.
     *
     * @return a Map with the list of incomplete server payments.
     * @throws IOException if there is a network or IO problem while retrieving the list.
     */
    public Map getIncompleteServerPayments() throws IOException {
        String response = OkHttpUtil.doGet(BASE_URL + "/v2/payments/incomplete_server_payments", authorization);
        return JSON.parseObject(response, Map.class);
    }

    /**
     * Retrieves the details of a specific payment.
     *
     * @param paymentId the ID of the payment to retrieve.
     * @return a Map with the details of the payment.
     * @throws IOException if there is a network or IO problem while retrieving the payment.
     */
    public Map getPayment(String paymentId) throws IOException {
        String response = OkHttpUtil.doGet(BASE_URL + "/v2/payments/" + paymentId, authorization);
        return JSONObject.parseObject(response, Map.class);
    }

}
