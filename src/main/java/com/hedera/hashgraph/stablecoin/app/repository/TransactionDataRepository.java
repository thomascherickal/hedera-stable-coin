package com.hedera.hashgraph.stablecoin.app.repository;

import com.hedera.hashgraph.stablecoin.app.SqlConnectionManager;
import org.jooq.BatchBindStep;

import javax.annotation.Nullable;
import java.sql.SQLException;
import java.time.Instant;

public abstract class TransactionDataRepository<ArgumentsT> {
    protected SqlConnectionManager connectionManager;

    @Nullable
    private BatchBindStep batch;

    protected TransactionDataRepository(SqlConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public abstract BatchBindStep newBatch() throws SQLException;

    public abstract TransactionKind getTransactionKind();

    public synchronized void bindTransaction(Instant consensusTimestamp, ArgumentsT arguments) throws SQLException {
        if (batch == null) {
            batch = newBatch();
        }

        batch = bindArguments(batch, consensusTimestamp, arguments);
    }

    public synchronized void execute() {
        if (batch == null) {
            return;
        }

        batch.execute();
        batch = null;
    }

    protected abstract BatchBindStep bindArguments(BatchBindStep batch, Instant consensusTimestamp, ArgumentsT arguments);
}
