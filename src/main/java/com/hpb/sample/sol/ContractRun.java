package com.hpb.sample.sol;

import com.hpb.sample.utils.Environment;
import io.hpb.web3.crypto.Credentials;
import io.hpb.web3.crypto.WalletUtils;
import io.hpb.web3.protocol.Web3;
import io.hpb.web3.protocol.Web3Service;
import io.hpb.web3.protocol.admin.Admin;
import io.hpb.web3.protocol.core.DefaultBlockParameterName;
import io.hpb.web3.protocol.core.methods.response.HpbGetBalance;
import io.hpb.web3.protocol.http.HttpService;
import io.hpb.web3.tuples.generated.Tuple2;
import io.hpb.web3.tx.ChainId;
import io.hpb.web3.tx.RawTransactionManager;
import io.hpb.web3.tx.gas.ContractGasProvider;
import io.hpb.web3.tx.gas.StaticGasProvider;
import io.hpb.web3.utils.Convert;
import okhttp3.OkHttpClient;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

public class ContractRun {

    private static Admin admin;

    public static void main(String[] args) {
        try {
            Web3Service web3Service = new HttpService(Environment.RPC_URL, new OkHttpClient.Builder().retryOnConnectionFailure(true).connectTimeout(60 * 30, TimeUnit.SECONDS).build(), true);
            Web3 web3 = Web3.build(web3Service);
            admin = Admin.build(web3Service);
            HpbGetBalance hpbGetBalance = admin.hpbGetBalance("0x2071049cf01a7c357c67a7e34f00705c0335f033", DefaultBlockParameterName.LATEST).sendAsync().get(2,TimeUnit.MINUTES);
            System.out.println(" hpbGetBalance = [" + hpbGetBalance.getBalance() + "]");
            Credentials credentials = WalletUtils.loadCredentials("135246pzh", "C:\\Work\\testkeystore\\test-keystore-2.json");
            RawTransactionManager transactionManager = new RawTransactionManager(admin, credentials, ChainId.MAINNET);
            ContractGasProvider gasProvider = new StaticGasProvider(Convert.toWei("40", Convert.Unit.GWEI).toBigInteger(),BigInteger.valueOf(100000000));
//          FCT0817Token contract = FCT0817Token.deploy(web3,transactionManager,gasProvider).sendAsync().get();
//            CardOwnershipTwo erc721 = CardOwnershipTwo.deploy(web3,transactionManager,Convert.toWei("18", Convert.Unit.GWEI).toBigInteger(),BigInteger.valueOf(100000000)).sendAsync().get();
//          System.out.println("contract.isValid = [" + contract.isValid()  + "]");
//            System.out.println("erc721.isValid = [" + erc721.isValid()  + "]");

        /*    RedPacketToken redPacketToken = RedPacketToken.load("0xba29fa851805767609c25b7a032101cef1c2258b",web3,credentials,Convert.toWei("18", Convert.Unit.GWEI).toBigInteger(),BigInteger.valueOf(100000000));
            System.out.println("   redPacketToken.getOwner().sendAsync().get(2,TimeUnit.MINUTES).getValue() = [" +    redPacketToken.getOwner().sendAsync().get(2,TimeUnit.MINUTES).getValue() + "]");
            System.out.println("   redPacketToken.getContractAddress() = [" +     redPacketToken.getContractAddress() + "]");

            System.out.println("   redPacketToken.getContractAddress() = [" +     redPacketToken.getContractBinary() + "]");*/

            HpbContractProxy hpbContractProxy = HpbContractProxy.load("0xdcdf1ac0b9f1a8ac4cbf03beb864cfcee6e92f18",web3,credentials,Convert.toWei("18", Convert.Unit.GWEI).toBigInteger(),BigInteger.valueOf(100000000));;
            String nodeBallotAddrInService = hpbContractProxy.nodeBallotAddrInService().sendAsync().get(2, TimeUnit.MINUTES);

            System.out.println("nodeBallotAddrInService = [" + nodeBallotAddrInService + "]");
            // hpbNodesAddress;
            String hpbNodesAddress =  hpbContractProxy.hpbNodesAddress().sendAsync().get(2,TimeUnit.MINUTES);
            System.out.println("hpbNodesAddress = [" + hpbNodesAddress + "]");

            Tuple2<String, BigInteger> stringBigIntegerTuple2 = hpbContractProxy.getLastestBallotAddrAndIndex().sendAsync().get(2, TimeUnit.MINUTES);
            System.out.println("Value1 [" + stringBigIntegerTuple2.getValue1() + "]");
            System.out.println("Value2 = [" + stringBigIntegerTuple2.getValue2() + "]");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
