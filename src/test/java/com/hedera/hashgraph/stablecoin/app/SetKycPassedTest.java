package com.hedera.hashgraph.stablecoin.app;

import com.google.protobuf.InvalidProtocolBufferException;
import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.PrivateKey;
import com.hedera.hashgraph.sdk.TopicId;
import com.hedera.hashgraph.stablecoin.proto.Transaction;
import com.hedera.hashgraph.stablecoin.sdk.Address;
import com.hedera.hashgraph.stablecoin.sdk.ConstructTransaction;
import com.hedera.hashgraph.stablecoin.sdk.SetKycPassedTransaction;
import com.hedera.hashgraph.stablecoin.sdk.UnsetKycPassedTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.sql.SQLException;
import java.time.Instant;

public class SetKycPassedTest {
    State state = new State();
    Client client = Client.forTestnet();
    TopicListener topicListener = new TopicListener(state, client, new TopicId(1), null);

    @Test
    public void setKycPassedTest() throws InvalidProtocolBufferException, SQLException {
        var callerKey = PrivateKey.generate();
        var assetManagerKey = PrivateKey.generate();
        var addrKey = PrivateKey.generate();
        var caller = new Address(callerKey.getPublicKey());
        var assetManager = new Address(assetManagerKey.getPublicKey());
        var addr = new Address(addrKey.getPublicKey());

        // prepare state
        var tokenName = "tokenName";
        var tokenSymbol = "tokenSymbol";
        var tokenDecimal = 2;
        var totalSupply = new BigInteger("10000");

        var constructTransaction = new ConstructTransaction(
            callerKey,
            tokenName,
            tokenSymbol,
            tokenDecimal,
            totalSupply,
            caller,
            assetManager
        );

        topicListener.handleTransaction(Instant.EPOCH, Transaction.parseFrom(constructTransaction.toByteArray()));

        // prepare test transaction
        var setKycPassedTransaction = new SetKycPassedTransaction(
            callerKey,
            addr
        );

        // Pre-Check

        // i. Owner != 0x
        Assertions.assertFalse(state.getOwner().isZero());

        // ii. caller = complianceManager || caller = Owner
        Assertions.assertEquals(caller, state.getOwner());

        // Update State
        topicListener.handleTransaction(Instant.EPOCH, Transaction.parseFrom(setKycPassedTransaction.toByteArray()));

        // Post-Check

        // i. KycPassed[addr]
        Assertions.assertTrue(state.isKycPassed(addr));

        // unset and check for caller == complianceManager instead this time
        var unsetKycPassedTransaction = new UnsetKycPassedTransaction(
            callerKey,
            addr
        );

        // update State
        topicListener.handleTransaction(Instant.EPOCH, Transaction.parseFrom(unsetKycPassedTransaction.toByteArray()));

        Assertions.assertFalse(state.isKycPassed(addr));

        var setKycPassedTransaction2 = new SetKycPassedTransaction(
            assetManagerKey,
            addr
        );

        // Update State
        topicListener.handleTransaction(Instant.EPOCH, Transaction.parseFrom(setKycPassedTransaction2.toByteArray()));

        // Pre-Check

        // i. Owner != 0x
        Assertions.assertFalse(state.getOwner().isZero());

        // ii. caller = complianceManager || caller = Owner
        Assertions.assertEquals(assetManager, state.getComplianceManager());

        // Post-Check

        // i. KycPassed[addr]
        Assertions.assertTrue(state.isKycPassed(addr));
    }
}
