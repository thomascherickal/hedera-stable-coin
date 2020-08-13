package com.hedera.hashgraph.stablecoin.handler;

import com.hedera.hashgraph.stablecoin.Address;
import com.hedera.hashgraph.stablecoin.State;
import com.hedera.hashgraph.stablecoin.Status;
import com.hedera.hashgraph.stablecoin.proto.TransactionBody;
import com.hedera.hashgraph.stablecoin.handler.arguments.UnfreezeTransactionArguments;

public final class UnfreezeTransactionHandler extends TransactionHandler<UnfreezeTransactionArguments> {
    @Override
    protected UnfreezeTransactionArguments parseArguments(TransactionBody transactionBody) {
        return new UnfreezeTransactionArguments(transactionBody);
    }

    @Override
    protected void validatePre(State state, Address caller, UnfreezeTransactionArguments args) {
        // i. Owner != 0x
        ensure(state.hasOwner(), Status.UNFREEZE_OWNER_NOT_SET);

        // ii. caller = assetProtectionManager || caller = owner
        ensure(
            caller.equals(state.getAssetProtectionManager()) || caller.equals(state.getOwner()),
            Status.UNFREEZE_NOT_AUTHORIZED
        );
    }

    @Override
    protected void updateState(State state, Address caller, UnfreezeTransactionArguments args) {
        // i. !Frozen[addr] // upon completion, Frozen[addr] must be false
        state.setFreeze(args.address, false);
    }
}
