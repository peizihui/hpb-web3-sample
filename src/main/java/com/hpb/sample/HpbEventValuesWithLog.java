package com.hpb.sample;

import io.hpb.web3.abi.EventValues;
import io.hpb.web3.abi.datatypes.Type;
import io.hpb.web3.protocol.core.methods.response.Log;

import java.util.List;

public class HpbEventValuesWithLog  {

    public   EventValues eventValues;
    public   Log log;

    public HpbEventValuesWithLog(EventValues eventValues, Log log) {
        this.eventValues = eventValues;
        this.log = log;
    }

    public List<Type> getIndexedValues() {
        return this.eventValues.getIndexedValues();
    }

    public List<Type> getNonIndexedValues() {
        return this.eventValues.getNonIndexedValues();
    }

    public Log getLog() {
        return this.log;
    }


}
