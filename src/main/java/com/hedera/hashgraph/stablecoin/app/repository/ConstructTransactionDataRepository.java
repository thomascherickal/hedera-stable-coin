package com.hedera.hashgraph.stablecoin.app.repository;

import com.hedera.hashgraph.stablecoin.app.SqlConnectionManager;
import com.hedera.hashgraph.stablecoin.app.handler.arguments.ConstructTransactionArguments;
import com.hedera.hashgraph.stablecoin.sdk.Address;
import io.vertx.core.json.JsonObject;
import org.jooq.BatchBindStep;

import java.sql.SQLException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;

import static com.hedera.hashgraph.stablecoin.app.db.Tables.TRANSACTION_CONSTRUCT;
import static java.util.Map.entry;
import static java.util.Map.ofEntries;

public final class ConstructTransactionDataRepository extends TransactionDataRepository<ConstructTransactionArguments> {
    ConstructTransactionDataRepository(SqlConnectionManager connectionManager) {
        super(connectionManager);
    }

    @Override
    public TransactionKind getTransactionKind() {
        return TransactionKind.CONSTRUCT;
    }

    @Override
    public Collection<Address> getAddressList(ConstructTransactionArguments arguments) {
        return Collections.emptyList();
    }

    @Override
    public JsonObject toTransactionData(ConstructTransactionArguments arguments) {
        return new JsonObject(ofEntries(
            entry("tokenName", arguments.tokenName),
            entry("tokenSymbol", arguments.tokenSymbol),
            entry("totalSupply", arguments.totalSupply.toString()),
            entry("supplyManager", arguments.supplyManager.toString()),
            entry("complianceManager", arguments.complianceManager.toString()),
            entry("enforcementManager", arguments.enforcementManager.toString()),
            entry("tokenDecimal", arguments.tokenDecimal)
        ));
    }

    @Override
    public BatchBindStep newBatch() throws SQLException {
        var cx = connectionManager.dsl();

        return cx.batch(cx.insertInto(TRANSACTION_CONSTRUCT,
            TRANSACTION_CONSTRUCT.TIMESTAMP,
            TRANSACTION_CONSTRUCT.TOKEN_NAME,
            TRANSACTION_CONSTRUCT.TOKEN_SYMBOL,
            TRANSACTION_CONSTRUCT.TOKEN_DECIMAL,
            TRANSACTION_CONSTRUCT.TOTAL_SUPPLY,
            TRANSACTION_CONSTRUCT.COMPLIANCE_MANAGER,
            TRANSACTION_CONSTRUCT.SUPPLY_MANAGER,
            TRANSACTION_CONSTRUCT.ENFORCEMENT_MANAGER
        ).values((Long) null, null, null, null, null, null, null, null).onConflictDoNothing());
    }

    @Override
    public BatchBindStep bindArguments(BatchBindStep batch, Instant consensusTimestamp, ConstructTransactionArguments arguments) {
        return batch.bind(ChronoUnit.NANOS.between(Instant.EPOCH, consensusTimestamp),
            arguments.tokenName,
            arguments.tokenSymbol,
            arguments.tokenDecimal,
            arguments.totalSupply,
            arguments.complianceManager.toBytes(),
            arguments.supplyManager.toBytes(),
            arguments.enforcementManager.toBytes());
    }
}
