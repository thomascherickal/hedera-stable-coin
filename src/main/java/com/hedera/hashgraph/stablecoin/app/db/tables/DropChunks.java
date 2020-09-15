/*
 * This file is generated by jOOQ.
 */
package com.hedera.hashgraph.stablecoin.app.db.tables;


import com.hedera.hashgraph.stablecoin.app.db.Public;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DropChunks extends TableImpl<Record> {

    private static final long serialVersionUID = -463205493;

    /**
     * The reference instance of <code>public.drop_chunks</code>
     */
    public static final DropChunks DROP_CHUNKS = new DropChunks();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<Record> getRecordType() {
        return Record.class;
    }

    /**
     * The column <code>public.drop_chunks.drop_chunks</code>.
     */
    public final TableField<Record, String> DROP_CHUNKS_ = createField(DSL.name("drop_chunks"), org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * Create a <code>public.drop_chunks</code> table reference
     */
    public DropChunks() {
        this(DSL.name("drop_chunks"), null);
    }

    /**
     * Create an aliased <code>public.drop_chunks</code> table reference
     */
    public DropChunks(String alias) {
        this(DSL.name(alias), DROP_CHUNKS);
    }

    /**
     * Create an aliased <code>public.drop_chunks</code> table reference
     */
    public DropChunks(Name alias) {
        this(alias, DROP_CHUNKS);
    }

    private DropChunks(Name alias, Table<Record> aliased) {
        this(alias, aliased, new Field[7]);
    }

    private DropChunks(Name alias, Table<Record> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.function());
    }

    public <O extends Record> DropChunks(Table<O> child, ForeignKey<O, Record> key) {
        super(child, key, DROP_CHUNKS);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public DropChunks as(String alias) {
        return new DropChunks(DSL.name(alias), this, parameters);
    }

    @Override
    public DropChunks as(Name alias) {
        return new DropChunks(alias, this, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public DropChunks rename(String name) {
        return new DropChunks(DSL.name(name), null, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public DropChunks rename(Name name) {
        return new DropChunks(name, null, parameters);
    }

    /**
     * Call this table-valued function
     */
    public DropChunks call(Object olderThan, String tableName, String schemaName, Boolean cascade, Object newerThan, Boolean verbose, Boolean cascadeToMaterializations) {
        return new DropChunks(DSL.name(getName()), null, new Field[] { 
              DSL.val(olderThan, org.jooq.impl.SQLDataType.OTHER.defaultValue(org.jooq.impl.DSL.field("NULL::unknown", org.jooq.impl.SQLDataType.OTHER)))
            , DSL.val(tableName, org.jooq.impl.SQLDataType.VARCHAR.defaultValue(org.jooq.impl.DSL.field("NULL::name", org.jooq.impl.SQLDataType.VARCHAR)))
            , DSL.val(schemaName, org.jooq.impl.SQLDataType.VARCHAR.defaultValue(org.jooq.impl.DSL.field("NULL::name", org.jooq.impl.SQLDataType.VARCHAR)))
            , DSL.val(cascade, org.jooq.impl.SQLDataType.BOOLEAN.defaultValue(org.jooq.impl.DSL.field("false", org.jooq.impl.SQLDataType.BOOLEAN)))
            , DSL.val(newerThan, org.jooq.impl.SQLDataType.OTHER.defaultValue(org.jooq.impl.DSL.field("NULL::unknown", org.jooq.impl.SQLDataType.OTHER)))
            , DSL.val(verbose, org.jooq.impl.SQLDataType.BOOLEAN.defaultValue(org.jooq.impl.DSL.field("false", org.jooq.impl.SQLDataType.BOOLEAN)))
            , DSL.val(cascadeToMaterializations, org.jooq.impl.SQLDataType.BOOLEAN.defaultValue(org.jooq.impl.DSL.field("NULL::boolean", org.jooq.impl.SQLDataType.BOOLEAN)))
        });
    }

    /**
     * Call this table-valued function
     */
    public DropChunks call(Field<Object> olderThan, Field<String> tableName, Field<String> schemaName, Field<Boolean> cascade, Field<Object> newerThan, Field<Boolean> verbose, Field<Boolean> cascadeToMaterializations) {
        return new DropChunks(DSL.name(getName()), null, new Field[] { 
              olderThan
            , tableName
            , schemaName
            , cascade
            , newerThan
            , verbose
            , cascadeToMaterializations
        });
    }
}