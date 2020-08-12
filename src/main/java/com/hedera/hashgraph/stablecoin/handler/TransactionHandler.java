package com.hedera.hashgraph.stablecoin.handler;

import com.hedera.hashgraph.stablecoin.Address;
import com.hedera.hashgraph.stablecoin.State;
import com.hedera.hashgraph.stablecoin.Status;
import com.hedera.hashgraph.stablecoin.proto.TransactionBody;

public abstract class TransactionHandler<ArgumentsT> {
    protected abstract ArgumentsT parseArguments(TransactionBody transactionBody);

    protected abstract void validatePre(State state, Address caller, ArgumentsT args);

    protected abstract void validatePost(State state, Address caller, ArgumentsT args);

    protected abstract void updateState(State state, Address caller, ArgumentsT args);

    protected void ensure(boolean condition, Status status) {
        // todo: log transaction as failed rather than exploding
        if (!condition) {
            throw new IllegalStateException("pre-condition failed with status " + status);
        }
    }

    protected void ensureTransferAllowed(State state, Address address) {
        // TODO: ensure(!state.isFrozen(address) && state.isKycPassed(address), Status.TRANSFER_NOT_ALLOWED);
    }

    public void handle(State state, Address caller, TransactionBody transactionBody) {
        var arguments = parseArguments(transactionBody);

        // check pre-conditions
        // fixme: if these fail, mark the transaction as failed with the given status from the exception
        validatePre(state, caller, arguments);

        // now update our state, this should not be able to fail
        updateState(state, caller, arguments);

        // check post-conditions
        // if these fail, terminate the process
        // these should only be a development aid
        validatePost(state, caller, arguments);
    }
}
