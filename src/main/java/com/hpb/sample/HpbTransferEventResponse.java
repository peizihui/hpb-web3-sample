package com.hpb.sample;

import io.hpb.web3.abi.datatypes.Address;
import io.hpb.web3.abi.datatypes.generated.Uint256;
import io.hpb.web3.protocol.core.methods.response.Log;

import java.io.Serializable;

public class HpbTransferEventResponse implements Serializable  {

    public Log log;

    public Address from;

    public Address to;

    public Uint256 tokenId;
}
