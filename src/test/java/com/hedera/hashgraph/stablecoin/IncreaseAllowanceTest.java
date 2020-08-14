package com.hedera.hashgraph.stablecoin;

import com.google.protobuf.InvalidProtocolBufferException;
import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.PrivateKey;
import com.hedera.hashgraph.sdk.TopicId;
import com.hedera.hashgraph.stablecoin.proto.Transaction;
import com.hedera.hashgraph.stablecoin.transaction.ConstructTransaction;
import com.hedera.hashgraph.stablecoin.transaction.IncreaseAllowanceTransaction;
import com.hedera.hashgraph.stablecoin.transaction.SetKycPassedTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

public class IncreaseAllowanceTest {
    State state = new State();
    Client client = Client.forTestnet();
    TopicListener topicListener = new TopicListener(state, client, new TopicId(1));

    @Test
    public void increaseAllowanceTest() throws InvalidProtocolBufferException {
        var callerKey = PrivateKey.generate();
        var spenderKey = PrivateKey.generate();
        var caller = new Address(callerKey.getPublicKey());
        var spender = new Address(spenderKey.getPublicKey());
        var value = BigInteger.ONE;

        // prepare state
        var tokenName = "tokenName";
        var tokenSymbol = "tokenSymbol";
        var tokenDecimal = new BigInteger("2");
        var totalSupply = new BigInteger("10000");

        var constructTransaction = new ConstructTransaction(
            callerKey,
            tokenName,
            tokenSymbol,
            tokenDecimal,
            totalSupply,
            caller,
            caller
        );

        var setKycTransaction = new SetKycPassedTransaction(callerKey, spender);
        topicListener.handleTransaction(Transaction.parseFrom(constructTransaction.toByteArray()));
        topicListener.handleTransaction(Transaction.parseFrom(setKycTransaction.toByteArray()));

        // get allowance before test
        var allowance = state.getAllowance(caller, spender);

        // prepare test transaction
        var increaseAllowanceTransaction = new IncreaseAllowanceTransaction(
            callerKey,
            spender,
            value
        );

        // Pre-Check

        // i. Owner != 0x
        Assertions.assertFalse(state.getOwner().isZero());

        // ii. value >= 0
        Assertions.assertTrue(value.compareTo(BigInteger.ZERO) >= 0);

        // iii. CheckTransferAllowed(caller)
        Assertions.assertTrue(state.checkTransferAllowed(caller));

        // iv. CheckTransferAllowed(spender)
        Assertions.assertTrue(state.checkTransferAllowed(spender));

        // v. Allowances[caller][spender] + value <= MAX_INT
        // Overflow not possible

        // Update State
        topicListener.handleTransaction(Transaction.parseFrom(increaseAllowanceTransaction.toByteArray()));

        // Post-Check

        // i. Allowances[caller][spender]’ = Allowances[caller][spender] + value
        Assertions.assertEquals(allowance.add(value), state.getAllowance(caller, spender));

        // Increase once more
        // Pre-Check

        // i. Owner != 0x
        Assertions.assertFalse(state.getOwner().isZero());

        // ii. value >= 0
        Assertions.assertTrue(value.compareTo(BigInteger.ZERO) >= 0);

        // iii. CheckTransferAllowed(caller)
        Assertions.assertTrue(state.checkTransferAllowed(caller));

        // iv. CheckTransferAllowed(spender)
        Assertions.assertTrue(state.checkTransferAllowed(spender));

        // v. Allowances[caller][spender] + value <= MAX_INT
        // Overflow not possible

        // Update State
        topicListener.handleTransaction(Transaction.parseFrom(increaseAllowanceTransaction.toByteArray()));

        // Post-Check

        // i. Allowances[caller][spender]’ = Allowances[caller][spender] + value
        // add value twice since we're increasing a second time
        Assertions.assertEquals(allowance.add(value).add(value), state.getAllowance(caller, spender));
    }
}