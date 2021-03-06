/*
 * This file is generated by jOOQ.
 */
package com.hedera.hashgraph.stablecoin.app.db.tables;


import com.hedera.hashgraph.stablecoin.app.db.Keys;
import com.hedera.hashgraph.stablecoin.app.db.Public;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TransactionDecreaseAllowance extends TableImpl<Record> {

    private static final long serialVersionUID = -1243174724;

    /**
     * The reference instance of <code>public.transaction_decrease_allowance</code>
     */
    public static final TransactionDecreaseAllowance TRANSACTION_DECREASE_ALLOWANCE = new TransactionDecreaseAllowance();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<Record> getRecordType() {
        return Record.class;
    }

    /**
     * The column <code>public.transaction_decrease_allowance.timestamp</code>.
     */
    public final TableField<Record, Long> TIMESTAMP = createField(DSL.name("timestamp"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.transaction_decrease_allowance.spender</code>.
     */
    public final TableField<Record, byte[]> SPENDER = createField(DSL.name("spender"), org.jooq.impl.SQLDataType.BLOB.nullable(false), this, "");

    /**
     * The column <code>public.transaction_decrease_allowance.value</code>.
     */
    public final TableField<Record, BigInteger> VALUE = createField(DSL.name("value"), org.jooq.impl.SQLDataType.DECIMAL_INTEGER.precision(78).nullable(false), this, "");

    /**
     * Create a <code>public.transaction_decrease_allowance</code> table reference
     */
    public TransactionDecreaseAllowance() {
        this(DSL.name("transaction_decrease_allowance"), null);
    }

    /**
     * Create an aliased <code>public.transaction_decrease_allowance</code> table reference
     */
    public TransactionDecreaseAllowance(String alias) {
        this(DSL.name(alias), TRANSACTION_DECREASE_ALLOWANCE);
    }

    /**
     * Create an aliased <code>public.transaction_decrease_allowance</code> table reference
     */
    public TransactionDecreaseAllowance(Name alias) {
        this(alias, TRANSACTION_DECREASE_ALLOWANCE);
    }

    private TransactionDecreaseAllowance(Name alias, Table<Record> aliased) {
        this(alias, aliased, null);
    }

    private TransactionDecreaseAllowance(Name alias, Table<Record> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> TransactionDecreaseAllowance(Table<O> child, ForeignKey<O, Record> key) {
        super(child, key, TRANSACTION_DECREASE_ALLOWANCE);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public UniqueKey<Record> getPrimaryKey() {
        return Keys.TRANSACTION_DECREASE_ALLOWANCE_PKEY;
    }

    @Override
    public List<UniqueKey<Record>> getKeys() {
        return Arrays.<UniqueKey<Record>>asList(Keys.TRANSACTION_DECREASE_ALLOWANCE_PKEY);
    }

    @Override
    public TransactionDecreaseAllowance as(String alias) {
        return new TransactionDecreaseAllowance(DSL.name(alias), this);
    }

    @Override
    public TransactionDecreaseAllowance as(Name alias) {
        return new TransactionDecreaseAllowance(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public TransactionDecreaseAllowance rename(String name) {
        return new TransactionDecreaseAllowance(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public TransactionDecreaseAllowance rename(Name name) {
        return new TransactionDecreaseAllowance(name, null);
    }
}
