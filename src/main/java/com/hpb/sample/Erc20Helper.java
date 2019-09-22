package com.hpb.sample;

import com.hpb.sample.utils.Environment;
import io.hpb.web3.abi.FunctionEncoder;
import io.hpb.web3.abi.FunctionReturnDecoder;
import io.hpb.web3.abi.TypeReference;
import io.hpb.web3.abi.datatypes.*;
import io.hpb.web3.abi.datatypes.generated.Uint256;
import io.hpb.web3.abi.datatypes.generated.Uint8;
import io.hpb.web3.crypto.CipherException;
import io.hpb.web3.crypto.Credentials;
import io.hpb.web3.crypto.WalletUtils;
import io.hpb.web3.protocol.admin.Admin;
import io.hpb.web3.protocol.admin.methods.response.PersonalUnlockAccount;
import io.hpb.web3.protocol.core.DefaultBlockParameterName;
import io.hpb.web3.protocol.core.RemoteCall;
import io.hpb.web3.protocol.core.methods.request.Transaction;
import io.hpb.web3.protocol.core.methods.response.HpbCall;
import io.hpb.web3.protocol.core.methods.response.HpbGetTransactionCount;
import io.hpb.web3.protocol.core.methods.response.HpbSendTransaction;
import io.hpb.web3.protocol.core.methods.response.TransactionReceipt;
import io.hpb.web3.protocol.http.HttpService;
import io.hpb.web3.tx.ChainId;
import io.hpb.web3.tx.RawTransactionManager;
import io.hpb.web3.utils.Convert;
/*import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;*/

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Erc20Helper {


    private static Admin admin;
    private  String emptyAddress = "0x0000000000000000000000000000000000000000";
    /**
     * 查询代币余额
     */
    public  BigInteger getTokenBalance(String fromAddress, String contractAddress) {

        String methodName = "balanceOf";
        List<Type> inputParameters = new ArrayList<>();
        List<TypeReference<?>> outputParameters = new ArrayList<>();
        Address address = new Address(fromAddress);
        inputParameters.add(address);

        TypeReference<Uint256> typeReference = new TypeReference<Uint256>() {
        };
        outputParameters.add(typeReference);
        Function function = new Function(methodName, inputParameters, outputParameters);
        String data = FunctionEncoder.encode(function);
        Transaction transaction = Transaction.createHpbCallTransaction(fromAddress, contractAddress, data);

        HpbCall hpbCall;
        BigInteger balanceValue = BigInteger.ZERO;
        try {
            hpbCall = admin.hpbCall(transaction, DefaultBlockParameterName.LATEST).send();
            List<Type> results = FunctionReturnDecoder.decode(hpbCall.getValue(), function.getOutputParameters());
            if(results != null && results.size() >0){
                balanceValue = (BigInteger) results.get(0).getValue();
            }else {
                balanceValue = BigInteger.ZERO;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return balanceValue;
    }

    /**
     * 查询代币名称
     *
     * @param contractAddress
     * @return
     */
    public  String getTokenName(String contractAddress) {
        String methodName = "name";
        String name = null;
        String fromAddr = emptyAddress;
        List<Type> inputParameters = new ArrayList<>();
        List<TypeReference<?>> outputParameters = new ArrayList<>();

        TypeReference<Utf8String> typeReference = new TypeReference<Utf8String>() {
        };
        outputParameters.add(typeReference);

        Function function = new Function(methodName, inputParameters, outputParameters);

        String data = FunctionEncoder.encode(function);
        Transaction transaction = Transaction.createHpbCallTransaction(fromAddr, contractAddress, data);

        HpbCall hpbCall;
        try {
            hpbCall = admin.hpbCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
            List<Type> results = FunctionReturnDecoder.decode(hpbCall.getValue(), function.getOutputParameters());
            if(!results.isEmpty()){
                name = results.get(0).getValue().toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

    /**
     * 查询代币符号
     *
     * @param contractAddress
     * @return
     */
    public  String getTokenSymbol( String contractAddress) {
        String methodName = "symbol";
        String symbol = null;
        String fromAddr = emptyAddress;
        List<Type> inputParameters = new ArrayList<>();
        List<TypeReference<?>> outputParameters = new ArrayList<>();

        TypeReference<Utf8String> typeReference = new TypeReference<Utf8String>() {
        };
        outputParameters.add(typeReference);

        Function function = new Function(methodName, inputParameters, outputParameters);

        String data = FunctionEncoder.encode(function);
        Transaction transaction = Transaction.createHpbCallTransaction(fromAddr, contractAddress, data);

        HpbCall hpbCall;
        try {
            hpbCall = admin.hpbCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
            List<Type> results = FunctionReturnDecoder.decode(hpbCall.getValue(), function.getOutputParameters());
            if(!results.isEmpty()) {
                symbol = results.get(0).getValue().toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return symbol;
    }

    /**
     * 查询代币精度
     * @param contractAddress
     * @return
     */
    public  int getTokenDecimals(String contractAddress) {
        String methodName = "decimals";
        String fromAddr = emptyAddress;
        int decimal = 0;
        List<Type> inputParameters = new ArrayList<>();
        List<TypeReference<?>> outputParameters = new ArrayList<>();

        TypeReference<Uint8> typeReference = new TypeReference<Uint8>() {
        };
        outputParameters.add(typeReference);

        Function function = new Function(methodName, inputParameters, outputParameters);

        String data = FunctionEncoder.encode(function);
        Transaction transaction = Transaction.createHpbCallTransaction(fromAddr, contractAddress, data);

        HpbCall hpbCall;
        try {
            hpbCall = admin.hpbCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
            List<Type> results = FunctionReturnDecoder.decode(hpbCall.getValue(), function.getOutputParameters());
            if(!results.isEmpty()) {
                decimal = Integer.parseInt(results.get(0).getValue().toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return decimal;
    }

    /**
     * 查询代币发行总量
     * @param contractAddress
     * @return
     */
    public  BigInteger getTokenTotalSupply(String contractAddress) {
        String methodName = "totalSupply";
        String fromAddr = emptyAddress;
        BigInteger totalSupply = BigInteger.ZERO;
        List<Type> inputParameters = new ArrayList<>();
        List<TypeReference<?>> outputParameters = new ArrayList<>();

        TypeReference<Uint256> typeReference = new TypeReference<Uint256>() {
        };
        outputParameters.add(typeReference);

        Function function = new Function(methodName, inputParameters, outputParameters);

        String data = FunctionEncoder.encode(function);
        Transaction transaction = Transaction.createHpbCallTransaction(fromAddr, contractAddress, data);

        HpbCall hpbCall;
        try {
            hpbCall = admin.hpbCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
            List<Type> results = FunctionReturnDecoder.decode(hpbCall.getValue(), function.getOutputParameters());
            if(!results.isEmpty()) {
                totalSupply = (BigInteger) results.get(0).getValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalSupply;
    }
   /**
     * 代币转账
     */
    public  String sendTokenTransaction(String fromAddress, String password, String toAddress, String contractAddress, BigInteger amount) {
        String txHash = null;
        BigInteger unlockDuration = BigInteger.valueOf(60L);
        try {
            PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(fromAddress, password, BigInteger.valueOf(100L)).send();
//			admin.per
            // testerc201
//			personalUnlockAccount.accountUnlocked()
            if (true) {
                String methodName = "transfer";
                List<Type> inputParameters = new ArrayList<>();
                List<TypeReference<?>> outputParameters = new ArrayList<>();
                Address tAddress = new Address(toAddress);
                Uint256 value = new Uint256(amount);
                inputParameters.add(tAddress);
                inputParameters.add(value);
                TypeReference<Bool> typeReference = new TypeReference<Bool>() {};
                outputParameters.add(typeReference);

                Function function = new Function(methodName, inputParameters, outputParameters);
                String data = FunctionEncoder.encode(function);
                HpbGetTransactionCount ethGetTransactionCount = admin.hpbGetTransactionCount(fromAddress, DefaultBlockParameterName.PENDING).sendAsync().get();
                BigInteger nonce = ethGetTransactionCount.getTransactionCount();
                BigInteger gasPrice = Convert.toWei(BigDecimal.valueOf(5), Convert.Unit.GWEI).toBigInteger();
                Transaction transaction = Transaction.createFunctionCallTransaction(fromAddress, nonce, gasPrice,BigInteger.valueOf(60000), contractAddress, data);
                HpbSendTransaction ethSendTransaction = admin.hpbSendTransaction(transaction).sendAsync().get();
                txHash = ethSendTransaction.getTransactionHash();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return txHash;
    }



    public  String getOwner(String contractAddress,BigInteger tokenId) {
        String FUNC_OWNEROF = "ownerOf";
        String methodName =  FUNC_OWNEROF ;
        String ownerAddress="";
        String fromAddr = emptyAddress;
        List<Type> inputParameters = new ArrayList<>();
        Address address = new Address(fromAddr);
        inputParameters.add(address);
        List<TypeReference<?>> outputParameters = new ArrayList<>();
        TypeReference<Utf8String> typeReference = new TypeReference<Utf8String>() {
        };
        outputParameters.add(typeReference);
        Function function = new Function(methodName, inputParameters, outputParameters);
        String encodedFunction = FunctionEncoder.encode(function);
        try {
            HpbCall hpbCall = (HpbCall)admin.hpbCall(Transaction.createHpbCallTransaction(fromAddr,contractAddress, encodedFunction), DefaultBlockParameterName.LATEST).send();
            List<Type> results = FunctionReturnDecoder.decode(hpbCall.getValue(), function.getOutputParameters());
            System.out.println("contractAddress = [" + contractAddress + "], tokenId = [" + tokenId + "]");
            System.out.println("results = [" + results + "]");
            if( results !=null && !results.isEmpty()) {
                ownerAddress = (String) results.get(0).getValue();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ownerAddress;
    }




/*    public RemoteCall<String> ownerOf(BigInteger _tokenId) {
        final Function function = new Function(FUNC_OWNEROF,
                Arrays.<Type>asList(new io.hpb.web3.abi.datatypes.generated.Uint256(_tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }
    public RemoteCall<BigInteger> balanceOf(String _owner) {
        final Function function = new Function(FUNC_BALANCEOF,
                Arrays.<Type>asList(new io.hpb.web3.abi.datatypes.Address(_owner)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }


        public RemoteCall<String> tokenURI(BigInteger _tokenId) {
        final Function function = new Function(FUNC_TOKENURI,
                Arrays.<Type>asList(new io.hpb.web3.abi.datatypes.generated.Uint256(_tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }


 public RemoteCall<String> tokenMetadataBaseURI() {
        final Function function = new Function(FUNC_TOKENMETADATABASEURI,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    */



    public static void main(String[] args) {
        admin = Admin.build(new HttpService(Environment.RPC_URL));
        Erc20Helper erc20Helper = new Erc20Helper();
        BigInteger x  = erc20Helper.getTokenTotalSupply("0x8c1a267948a8da99812e03ac856c7a07e87bf956");
        System.out.println("x = [" + x + "]");
        String address =   erc20Helper.getOwner("0xba29fa851805767609c25b7a032101cef1c2258b",BigInteger.valueOf(10));

        System.out.println("address = [" + address + "]");

        //0x7e5d10c4d8b812b61a0573ad14504aab0f60d870  15

        String address870 =   erc20Helper.getOwner("0x7e5d10c4d8b812b61a0573ad14504aab0f60d870",BigInteger.valueOf(15));
        System.out.println("870address  = [" + address870  + "]");


    }

}
