package com.hedera.hashgraph.stablecoin.sdk;

import com.google.protobuf.ByteString;
import com.hedera.hashgraph.sdk.PrivateKey;
import com.hedera.hashgraph.stablecoin.proto.ConstructTransactionData;
import com.hedera.hashgraph.stablecoin.proto.TransactionBody;

import java.math.BigInteger;

public final class ConstructTransaction extends Transaction {
    public ConstructTransaction(
        PrivateKey owner,
        String tokenName,
        String tokenSymbol,
        BigInteger tokenDecimal,
        BigInteger totalSupply,
        Address supplyManager,
        Address assetProtectionManager
    ) {
        super(owner, TransactionBody.newBuilder()
            .setConstruct(ConstructTransactionData.newBuilder()
                .setTokenSymbol(tokenSymbol)
                .setTokenName(tokenName)
                .setTokenDecimal(ByteString.copyFrom(tokenDecimal.toByteArray()))
                .setTotalSupply(ByteString.copyFrom(totalSupply.toByteArray()))
                .setSupplyManager(ByteString.copyFrom(supplyManager.publicKey.toBytes()))
                .setAssetProtectionManager(ByteString.copyFrom(assetProtectionManager.publicKey.toBytes()))));
    }
}