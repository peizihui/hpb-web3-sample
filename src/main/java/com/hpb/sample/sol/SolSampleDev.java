package com.hpb.sample.sol;

import com.hpb.sample.ColdWallet;
import com.hpb.sample.utils.Environment;
import com.hpb.sample.utils.GsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.hpb.web3.abi.EventEncoder;
import io.hpb.web3.abi.TypeReference;
import io.hpb.web3.abi.datatypes.Address;
import io.hpb.web3.abi.datatypes.Event;
import io.hpb.web3.abi.datatypes.generated.Uint256;
import io.hpb.web3.crypto.CipherException;
import io.hpb.web3.crypto.Credentials;
import io.hpb.web3.crypto.WalletUtils;
import io.hpb.web3.protocol.Web3;
import io.hpb.web3.protocol.Web3Service;
import io.hpb.web3.protocol.admin.Admin;
import io.hpb.web3.protocol.core.DefaultBlockParameterName;
import io.hpb.web3.protocol.core.DefaultBlockParameterNumber;
import io.hpb.web3.protocol.core.RemoteCall;
import io.hpb.web3.protocol.core.methods.request.HpbFilter;
import io.hpb.web3.protocol.core.methods.response.HpbGetTransactionReceipt;
import io.hpb.web3.protocol.core.methods.response.TransactionReceipt;
import io.hpb.web3.protocol.http.HttpService;
import io.hpb.web3.tx.ChainId;
import io.hpb.web3.tx.RawTransactionManager;
import io.hpb.web3.utils.Convert;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SolSampleDev {
    private static final Logger logger = LoggerFactory.getLogger(SolSample.class);

    //链的标识
    private static Long chainId =  269L;

    private static Admin admin;

    public static void main(String[] args) {
        Web3Service web3Service = new HttpService(Environment.RPC_URL, new OkHttpClient.Builder().retryOnConnectionFailure(true).connectTimeout(60 * 30, TimeUnit.SECONDS).build(), true);
        Web3 web3 = Web3.build(web3Service);
        admin = Admin.build(web3Service);
//        	test();
        //	deploy();
        //	use();
        //  创建账户；
        // createWallet();
//        testErc20Transaction();
//        getTransferEvent();
//        getContractAddressByTxHash();
    /*    testErc20Transaction0x8d46b1512a27c9dca42f12254d19c721aa1e1d53();
        testErc20Transaction0x760f7ab61f1dd457ba42a8d39d9f4575d613e626();
        testErc20Transaction0x1e697349d8e8d7e24a4268cd50d827b5460e70b7();
        testErc20TransactionBIGBIGTOKEN();
        testErc20TransactionChinaToken();
        testErc20TransactionShangToken1();*/
//        testErc20TransactionShangToken1();
//        testErc20TransactionShangToken2();
         //testErc20TransactionShangToken0xb6b5408f889355c068e910d0f468b055cb88a551();
/*
        try {
            String hash = "0xcb8d6be396cfd6b92473ca5b67eaa5d63e0fc940737148ecd9d0aafa62c28182";
            HpbTransaction transaction =   web3.hpbGetTransactionByHash(hash).sendAsync().get(2,TimeUnit.MINUTES);
            if(transaction.getTransaction() != null){
                Optional<Transaction> transactionOptional = transaction.getTransaction();
                    Transaction transaction1 = transactionOptional.get();
                  logger.info("input ===="+ transaction1.getInput());
                }

            HpbGetTransactionReceipt hpbGetTransactionReceipt = admin.hpbGetTransactionReceipt(hash).sendAsync().get(2, TimeUnit.MINUTES);
            if( hpbGetTransactionReceipt != null && hpbGetTransactionReceipt.getTransactionReceipt() != null ){
                if( hpbGetTransactionReceipt.getTransactionReceipt().isPresent()){
                    TransactionReceipt tx = hpbGetTransactionReceipt.getTransactionReceipt().get();
                    testErcIco(tx);
                };
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        testEncode();*/

//        testErc20TransactionShangTokenTestNet();
        testErc20TransactionShangToken0x0d34b19f91ac06036157f577fc74fb9375ca554d();
    }
    public  static void testEncode(){
        Event event = new Event("Issue",
                Arrays.<TypeReference<?>>asList(
                        new TypeReference<Uint256>(true) {},
                        new TypeReference<Address>(true) {},
                        new TypeReference<Uint256>(true) {},
                        new TypeReference<Uint256>(true) {}));
        String encodedEventSignature = EventEncoder.encode(event);
        logger.info("encodedEventSignature ===="+encodedEventSignature);


    String name =    EventEncoder.buildEventSignature("0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef");

    logger.info("name ==="+name);
    }
    public static void testErcIco(TransactionReceipt transactionReceipt) {
        try {
            Web3Service web3Service = new HttpService(Environment.RPC_URL, new OkHttpClient.Builder().retryOnConnectionFailure(true).connectTimeout(60 * 30, TimeUnit.SECONDS).build(), true);
            Web3 web3 = Web3.build(web3Service);
            String contractAddress = "0xb7bbc918c85566a3bc37847b3c807bc12eeff8ad";
            contractAddress = "0x7ba64cf39aafd53823267248b327f1f24dcf0c2d";
            Credentials credentials = WalletUtils.loadCredentials("135246pzh", "C:\\Work\\testkeystore\\test-keystore-2.json");
            ERC20 tokenERC20 = ERC20.load(contractAddress, web3, credentials, Convert.toWei("20", Convert.Unit.GWEI).toBigInteger(), BigInteger.valueOf(100000000));
            BigInteger startBlock =  BigInteger.valueOf(2998000);

            HpbFilter filter = new HpbFilter(new DefaultBlockParameterNumber(startBlock), DefaultBlockParameterName.LATEST,contractAddress);
            Event event = new Event("Issue",
                    Arrays.<TypeReference<?>>asList(
                            new TypeReference<Uint256>(false){},
                            new TypeReference<Address>(true){},
                            new TypeReference<Uint256>(false){},
                            new TypeReference<Uint256>(false){}
                    ));

            Event APPROVAL_EVENT = new Event("Approval",Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
            String topicData = EventEncoder.encode(event);
            filter.addSingleTopic(topicData);
        /*   Observable<TokenERC20.TransferEventResponse> responseObservable = tokenERC20.transferEventObservable(filter);
            responseObservable.flatMap()*/

            /*TransactionReceipt transactionReceipt = new TransactionReceipt();
            transactionReceipt.setTransactionHash("0x400e79931e78accdf346eee609eca918f4eba00727b8e07338967bef358be2fd");*/

//            ERC20.ApprovalEventResponse approvalEventValues =  tokenERC20.getApprovalEvents(transactionReceipt).get(0);

           /* if(approvalEventValues != null){
                ERC20.ApprovalEventResponse tr =  approvalEventValues ;
                logger.info(" _owner [{}] ,_spender [{}] ,value [{}], log [{ }]",tr._owner,tr._spender,tr._value,tr.log);
            }*/



        /*    List<ERC20.ApprovalEventResponse>  list =  tokenERC20.getApprovalEvents(transactionReceipt);
            logger.info("list.size ====="+list.size());
            for(ERC20.ApprovalEventResponse tr :list){
              logger.info(" _owner [{}] ,_spender [{}] ,value [{}], log [{ }]",tr._owner,tr._spender,tr._value,tr.log);
            }*/


//            Flowable<ERC20.ApprovalEventResponse>  responseFlowable =  tokenERC20.approvalEventFlowable(filter);


            List<ERC20.TransferEventResponse>  list =  tokenERC20.getTransferEvents(transactionReceipt);

            for(ERC20.TransferEventResponse tr:list){
                logger.info("from [{}],to [{}],value [{}],log [{}]",tr._from,tr._to,tr._value,tr.log);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        } finally {
        }
    }

    public static void test() {
        try {
            Web3Service web3Service = new HttpService(Environment.RPC_URL, new OkHttpClient.Builder().retryOnConnectionFailure(true).connectTimeout(60 * 30, TimeUnit.SECONDS).build(), true);
            Web3 web3 = Web3.build(web3Service);
            String contractAddress = "0xfda5331d0ad8bdfdefe650218429f9924fd3b18c";
            contractAddress = "0x7ba64cf39aafd53823267248b327f1f24dcf0c2d";
            Credentials credentials = WalletUtils.loadCredentials("135246pzh", "C:\\Work\\testkeystore\\test-keystore-2.json");
            TokenERC20 tokenERC20 = TokenERC20.load(contractAddress, web3, credentials, Convert.toWei("20", Convert.Unit.GWEI).toBigInteger(), BigInteger.valueOf(100000000));
			/*	Object f = tokenERC20.symbol().sendAsync().get();
				System.out.println("f===="+f);*/
            // # 1.查询余额；
            //  合约创建者  0x2071049cf01a7c357c67a7e34f00705c0335f033
            try {
                RemoteCall<BigInteger> bigIntegerRemoteCall = tokenERC20.balanceOf("0xdb4d90c1fe9f840eabbd0a7fba0b0f7293301725");
                BigInteger balance = bigIntegerRemoteCall.sendAsync().get(2, TimeUnit.MINUTES);
                logger.info("balance =================" + balance);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
            // # 2.  转账出去；
            boolean isValidFlag = tokenERC20.isValid();
            logger.info("isValidFlag =====" + isValidFlag);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * 创建钱包账户；
     */
    public static void createWallet() {

        // # 3.  创建账户；
        try {
            ColdWallet.createWallet("testerc201");
            ColdWallet.createWallet("testerc202");
            ColdWallet.createWallet("testerc203");
            ColdWallet.createWallet("testerc204");
            ColdWallet.createWallet("testerc205");
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    private static void deploy() {
        Web3 web3 = Web3.build(new HttpService(Environment.RPC_URL));
        Credentials credentials = null;//可以根据私钥生成
        try {
            String address = "0x2071049CF01A7C357c67a7e34f00705c0335F033";
            credentials = WalletUtils.loadCredentials("135246pzh", "C:\\Work\\testkeystore\\test-keystore-2.json");

            String password = "135246pzh";
            String mnemonic = " way actress sadness remain right catalog advance lobster inquiry quote river extend ";

//			credentials = WalletUtils.loadBip39Credentials(password,mnemonic);
//			gasPrice	18000000000
//			gasLimit    100000000

//			deploy(Web3 web3, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialSupply, String tokenName, String tokenSymbol)
            RemoteCall<TokenERC20> deploy = TokenERC20.deploy(web3, credentials,
                    Convert.toWei("20", Convert.Unit.GWEI).toBigInteger(),
                    BigInteger.valueOf(100000000),
                    BigInteger.valueOf(5201314),
                    "my token", "mt");

            TokenERC20 tokenERC20 = deploy.send();
            tokenERC20.isValid();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void use() {
        Web3 web3 = Web3.build(new HttpService(Environment.RPC_URL));
        String contractAddress = null;
        Credentials credentials = null;//可以根据私钥生成

        // load(String contractAddress, Web3 web3, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit)


        TokenERC20 contract = TokenERC20.load(contractAddress, web3, credentials,
                Convert.toWei("20", Convert.Unit.GWEI).toBigInteger(),
                BigInteger.valueOf(100000000));
        String myAddress = null;
        String toAddress = null;
        BigInteger amount = BigInteger.ONE;
        try {
            BigInteger balance = contract.balanceOf(myAddress).send();
            TransactionReceipt receipt = contract.transfer(toAddress, amount).send();
            //etc..
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getTransferEvent() {
        try {
            logger.info(" getTransferEvent   ");
            Web3 web3 = Web3.build(new HttpService(Environment.RPC_URL));
            String contractAddress = "0xfda5331d0ad8bdfdefe650218429f9924fd3b18c";
            Credentials credentials = WalletUtils.loadCredentials("135246pzh", "C:\\Work\\testkeystore\\test-keystore-2.json");
            TokenERC20 tokenERC20 = TokenERC20.load(contractAddress, web3, credentials, Convert.toWei("20", Convert.Unit.GWEI).toBigInteger(), BigInteger.valueOf(100000000));
            TransactionReceipt transactionReceipt = new TransactionReceipt();
            transactionReceipt.setTransactionHash("0xe2c88678ae8493e598983f72a4f7f64f88d341f8dafeaef9bbaecbbebab08aa4");
            tokenERC20.getTransferEvents(transactionReceipt);
        } catch (IOException e) {

        } catch (CipherException e) {
            e.printStackTrace();
            e.printStackTrace();
        } finally {
        }
        // 9f786fbc8a5f69f64834043670e03a9cdad1c3880edd0886a4e3939b8314ccb1
    }

    private static void testErc20Transaction() {
        try {
//        Web3 web3 = Web3.build(new HttpService(Environment.RPC_URL));
            Web3Service web3Service = new HttpService(Environment.RPC_URL, new OkHttpClient.Builder().retryOnConnectionFailure(true).connectTimeout(60 * 30, TimeUnit.SECONDS).build(), true);
            Web3 web3 = Web3.build(web3Service);
            admin = Admin.build(web3Service);
      /*  String contractAddress = null;
        Credentials credentials = null;//可以根据私钥生成*/
            String contractAddress = "0xfda5331d0ad8bdfdefe650218429f9924fd3b18c";
            Credentials credentials = WalletUtils.loadCredentials("135246pzh", "C:\\Work\\testkeystore\\test-keystore-2.json");
            String myAddress = "0x2071049cf01a7c357c67a7e34f00705c0335f033";
            // load(String contractAddress, Web3 web3, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit)
            RawTransactionManager transactionManager = new RawTransactionManager(admin, credentials, ChainId.MAINNET);
            TokenERC20 contract = TokenERC20.load(contractAddress, web3, transactionManager, Convert.toWei("20", Convert.Unit.GWEI).toBigInteger(), BigInteger.valueOf(10000000));
            BigInteger balance = contract.balanceOf(myAddress).send();
            System.out.println("testErc20Transaction  balance ====" + balance);
            String toAddress = "0xdb4d90c1fe9f840eabbd0a7fba0b0f7293301725";
            BigInteger amount = BigInteger.valueOf(10000L);
            amount = BigInteger.TEN;
            amount = BigInteger.valueOf(100086L);
            TransactionReceipt receipt = contract.transfer(toAddress, amount).sendAsync().get(7, TimeUnit.MINUTES);
            logger.info("testErc20Transaction receipt  hash ====" + receipt.getTransactionHash());
            List<TokenERC20.TransferEventResponse> transferEventResponseList = contract.getTransferEvents(receipt);
            for (TokenERC20.TransferEventResponse tr : transferEventResponseList) {
                logger.info("testErc20Transaction tr =====" + GsonUtil.gsonString(tr));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private static void testErc20Transaction0x8d46b1512a27c9dca42f12254d19c721aa1e1d53() {
        try {
//        Web3 web3 = Web3.build(new HttpService(Environment.RPC_URL));
            Web3Service web3Service = new HttpService(Environment.RPC_URL, new OkHttpClient.Builder().retryOnConnectionFailure(true).connectTimeout(60 * 30, TimeUnit.SECONDS).build(), true);
            Web3 web3 = Web3.build(web3Service);
            admin = Admin.build(web3Service);
      /*  String contractAddress = null;
        Credentials credentials = null;//可以根据私钥生成*/
            String contractAddress = "0x8d46b1512a27c9dca42f12254d19c721aa1e1d53";
            Credentials credentials = WalletUtils.loadCredentials("135246pzh", "C:\\Work\\testkeystore\\test-keystore-2.json");
            String myAddress = "0x2071049cf01a7c357c67a7e34f00705c0335f033";
            // load(String contractAddress, Web3 web3, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit)
            RawTransactionManager transactionManager = new RawTransactionManager(admin, credentials, ChainId.MAINNET);
            TokenERC20 contract = TokenERC20.load(contractAddress, web3, transactionManager, Convert.toWei("20", Convert.Unit.GWEI).toBigInteger(), BigInteger.valueOf(10000000));
            BigInteger balance = contract.balanceOf(myAddress).send();
            System.out.println("testErc20Transaction0x8d46b1512a27c9dca42f12254d19c721aa1e1d53  balance ====" + balance);
            String toAddress = "0xdb4d90c1fe9f840eabbd0a7fba0b0f7293301725";
            BigInteger amount = BigInteger.valueOf(10000L);
            amount = BigInteger.TEN;
            amount = BigInteger.valueOf(100086L);



            TransactionReceipt receipt = contract.transfer(toAddress, amount).sendAsync().get(7, TimeUnit.MINUTES);
            logger.info("testErc20Transaction receipt  hash ====" + receipt.getTransactionHash());
            List<TokenERC20.TransferEventResponse> transferEventResponseList = contract.getTransferEvents(receipt);
            for (TokenERC20.TransferEventResponse tr : transferEventResponseList) {
                logger.info("testErc20Transaction tr =====" + GsonUtil.gsonString(tr));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testErc20Transaction0x65cbdcd32d854f74c88946d58b90ff4fc9550aaf() {
        try {
//        Web3 web3 = Web3.build(new HttpService(Environment.RPC_URL));
            Web3Service web3Service = new HttpService(Environment.RPC_URL, new OkHttpClient.Builder().retryOnConnectionFailure(true).connectTimeout(60 * 30, TimeUnit.SECONDS).build(), true);
            Web3 web3 = Web3.build(web3Service);
            admin = Admin.build(web3Service);
      /*  String contractAddress = null;
        Credentials credentials = null;//可以根据私钥生成*/
            String contractAddress = "0x65cbdcd32d854f74c88946d58b90ff4fc9550aaf";
            Credentials credentials = WalletUtils.loadCredentials("135246pzh", "C:\\Work\\testkeystore\\test-keystore-2.json");
            String myAddress = "0x2071049cf01a7c357c67a7e34f00705c0335f033";
            // load(String contractAddress, Web3 web3, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit)
            RawTransactionManager transactionManager = new RawTransactionManager(admin, credentials, ChainId.MAINNET);
            TokenERC20 contract = TokenERC20.load(contractAddress, web3, transactionManager, Convert.toWei("20", Convert.Unit.GWEI).toBigInteger(), BigInteger.valueOf(10000000));
            BigInteger balance = contract.balanceOf(myAddress).send();
            System.out.println("testErc20Transaction0x65cbdcd32d854f74c88946d58b90ff4fc9550aaf  balance ====" + balance);
            String toAddress = "0xdb4d90c1fe9f840eabbd0a7fba0b0f7293301725";
            BigInteger amount = BigInteger.valueOf(10000L);
            amount = BigInteger.TEN;
            amount = BigInteger.valueOf(100086L);
            TransactionReceipt receipt = contract.transfer(toAddress, amount).sendAsync().get(7, TimeUnit.MINUTES);
            logger.info("testErc20Transaction receipt  hash ====" + receipt.getTransactionHash());
            List<TokenERC20.TransferEventResponse> transferEventResponseList = contract.getTransferEvents(receipt);
            for (TokenERC20.TransferEventResponse tr : transferEventResponseList) {
                logger.info("testErc20Transaction tr =====" + GsonUtil.gsonString(tr));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void testErc20Transaction0x760f7ab61f1dd457ba42a8d39d9f4575d613e626() {
        try {
//        Web3 web3 = Web3.build(new HttpService(Environment.RPC_URL));
            Web3Service web3Service = new HttpService(Environment.RPC_URL, new OkHttpClient.Builder().retryOnConnectionFailure(true).connectTimeout(60 * 30, TimeUnit.SECONDS).build(), true);
            Web3 web3 = Web3.build(web3Service);
            admin = Admin.build(web3Service);
      /*  String contractAddress = null;
        Credentials credentials = null;//可以根据私钥生成*/
            String contractAddress = "0x760f7ab61f1dd457ba42a8d39d9f4575d613e626";
            Credentials credentials = WalletUtils.loadCredentials("135246pzh", "C:\\Work\\testkeystore\\test-keystore-2.json");
            String myAddress = "0x2071049cf01a7c357c67a7e34f00705c0335f033";
            // load(String contractAddress, Web3 web3, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit)
            RawTransactionManager transactionManager = new RawTransactionManager(admin, credentials, ChainId.MAINNET);
            TokenERC20 contract = TokenERC20.load(contractAddress, web3, transactionManager, Convert.toWei("20", Convert.Unit.GWEI).toBigInteger(), BigInteger.valueOf(10000000));
            BigInteger balance = contract.balanceOf(myAddress).send();
            System.out.println("testErc20Transaction0x760f7ab61f1dd457ba42a8d39d9f4575d613e626  balance ====" + balance);
            String toAddress = "0xdb4d90c1fe9f840eabbd0a7fba0b0f7293301725";
            BigInteger amount = BigInteger.valueOf(10000L);
            amount = BigInteger.TEN;
            amount = BigInteger.valueOf(100086L);
            TransactionReceipt receipt = contract.transfer(toAddress, amount).sendAsync().get(7, TimeUnit.MINUTES);
            logger.info("testErc20Transaction receipt  hash ====" + receipt.getTransactionHash());
            List<TokenERC20.TransferEventResponse> transferEventResponseList = contract.getTransferEvents(receipt);
            for (TokenERC20.TransferEventResponse tr : transferEventResponseList) {
                logger.info("testErc20Transaction tr =====" + GsonUtil.gsonString(tr));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 0x1e697349d8e8d7e24a4268cd50d827b5460e70b7

    private static void testErc20Transaction0x1e697349d8e8d7e24a4268cd50d827b5460e70b7() {
        try {
//        Web3 web3 = Web3.build(new HttpService(Environment.RPC_URL));
            Web3Service web3Service = new HttpService(Environment.RPC_URL, new OkHttpClient.Builder().retryOnConnectionFailure(true).connectTimeout(60 * 30, TimeUnit.SECONDS).build(), true);
            Web3 web3 = Web3.build(web3Service);
            admin = Admin.build(web3Service);
      /*  String contractAddress = null;
        Credentials credentials = null;//可以根据私钥生成*/
            String contractAddress = "0x1e697349d8e8d7e24a4268cd50d827b5460e70b7";
            Credentials credentials = WalletUtils.loadCredentials("135246pzh", "C:\\Work\\testkeystore\\test-keystore-2.json");
            String myAddress = "0x2071049cf01a7c357c67a7e34f00705c0335f033";
            // load(String contractAddress, Web3 web3, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit)
            RawTransactionManager transactionManager = new RawTransactionManager(admin, credentials, ChainId.MAINNET);
            TokenERC20 contract = TokenERC20.load(contractAddress, web3, transactionManager, Convert.toWei("20", Convert.Unit.GWEI).toBigInteger(), BigInteger.valueOf(10000000));
            BigInteger balance = contract.balanceOf(myAddress).send();
            System.out.println("testErc20Transaction0x1e697349d8e8d7e24a4268cd50d827b5460e70b7  balance ====" + balance);
            String toAddress = "0xdb4d90c1fe9f840eabbd0a7fba0b0f7293301725";
            BigInteger amount = BigInteger.valueOf(10000L);
            amount = BigInteger.TEN;
            amount = BigInteger.valueOf(100086L);
            TransactionReceipt receipt = contract.transfer(toAddress, amount).sendAsync().get(7, TimeUnit.MINUTES);
            logger.info("testErc20Transaction receipt  hash ====" + receipt.getTransactionHash());
            List<TokenERC20.TransferEventResponse> transferEventResponseList = contract.getTransferEvents(receipt);
            for (TokenERC20.TransferEventResponse tr : transferEventResponseList) {
                logger.info("testErc20Transaction tr =====" + GsonUtil.gsonString(tr));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // BIGBIGTOKEN 0x2d6212fc7bbb3c25c0c8ef645bf69f6339784db9

    private static void testErc20TransactionBIGBIGTOKEN() {
        try {
//        Web3 web3 = Web3.build(new HttpService(Environment.RPC_URL));
            Web3Service web3Service = new HttpService(Environment.RPC_URL, new OkHttpClient.Builder().retryOnConnectionFailure(true).connectTimeout(60 * 30, TimeUnit.SECONDS).build(), true);
            Web3 web3 = Web3.build(web3Service);
            admin = Admin.build(web3Service);
      /*  String contractAddress = null;
        Credentials credentials = null;//可以根据私钥生成*/
            String contractAddress = "0x2d6212fc7bbb3c25c0c8ef645bf69f6339784db9";
            Credentials credentials = WalletUtils.loadCredentials("135246pzh", "C:\\Work\\testkeystore\\test-keystore-2.json");
            String myAddress = "0x2071049cf01a7c357c67a7e34f00705c0335f033";
            // load(String contractAddress, Web3 web3, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit)
            RawTransactionManager transactionManager = new RawTransactionManager(admin, credentials, ChainId.MAINNET);
            TokenERC20 contract = TokenERC20.load(contractAddress, web3, transactionManager, Convert.toWei("20", Convert.Unit.GWEI).toBigInteger(), BigInteger.valueOf(10000000));
            BigInteger balance = contract.balanceOf(myAddress).send();
            System.out.println("testErc20TransactionBIGBIGTOKEN  balance ====" + balance);
            String toAddress = "0xdb4d90c1fe9f840eabbd0a7fba0b0f7293301725";
            BigInteger amount = BigInteger.valueOf(10000L);
            amount = BigInteger.TEN;
            amount = BigInteger.valueOf(100086L);
            TransactionReceipt receipt = contract.transfer(toAddress, amount).sendAsync().get(7, TimeUnit.MINUTES);
            logger.info("testErc20Transaction receipt  hash ====" + receipt.getTransactionHash());
            List<TokenERC20.TransferEventResponse> transferEventResponseList = contract.getTransferEvents(receipt);
            for (TokenERC20.TransferEventResponse tr : transferEventResponseList) {
                logger.info("testErc20Transaction tr =====" + GsonUtil.gsonString(tr));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ChinaToken  0xfc571eac81278eb62380b91d62b1adcf4dcc0fc3

    private static void testErc20TransactionChinaToken() {
        try {
//        Web3 web3 = Web3.build(new HttpService(Environment.RPC_URL));
            Web3Service web3Service = new HttpService(Environment.RPC_URL, new OkHttpClient.Builder().retryOnConnectionFailure(true).connectTimeout(60 * 30, TimeUnit.SECONDS).build(), true);
            Web3 web3 = Web3.build(web3Service);
            admin = Admin.build(web3Service);
      /*  String contractAddress = null;
        Credentials credentials = null;//可以根据私钥生成*/
            String contractAddress = "0xfc571eac81278eb62380b91d62b1adcf4dcc0fc3";
            Credentials credentials = WalletUtils.loadCredentials("135246pzh", "C:\\Work\\testkeystore\\test-keystore-2.json");
            String myAddress = "0x2071049cf01a7c357c67a7e34f00705c0335f033";
            // load(String contractAddress, Web3 web3, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit)
            RawTransactionManager transactionManager = new RawTransactionManager(admin, credentials, ChainId.MAINNET);
            TokenERC20 contract = TokenERC20.load(contractAddress, web3, transactionManager, Convert.toWei("20", Convert.Unit.GWEI).toBigInteger(), BigInteger.valueOf(10000000));
            BigInteger balance = contract.balanceOf(myAddress).send();
            System.out.println("testErc20TransactionChinaToken  balance ====" + balance);
            String toAddress = "0xdb4d90c1fe9f840eabbd0a7fba0b0f7293301725";
            BigInteger amount = BigInteger.valueOf(10000L);
            amount = BigInteger.TEN;
            amount = BigInteger.valueOf(100086L);
            TransactionReceipt receipt = contract.transfer(toAddress, amount).sendAsync().get(7, TimeUnit.MINUTES);
            logger.info("testErc20Transaction receipt  hash ====" + receipt.getTransactionHash());
            List<TokenERC20.TransferEventResponse> transferEventResponseList = contract.getTransferEvents(receipt);
            for (TokenERC20.TransferEventResponse tr : transferEventResponseList) {
                logger.info("testErc20Transaction tr =====" + GsonUtil.gsonString(tr));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //


    private static void testErc20TransactionShangToken1() {
        try {
//        Web3 web3 = Web3.build(new HttpService(Environment.RPC_URL));
            Web3Service web3Service = new HttpService(Environment.RPC_URL, new OkHttpClient.Builder().retryOnConnectionFailure(true).connectTimeout(60 * 30, TimeUnit.SECONDS).build(), true);
            Web3 web3 = Web3.build(web3Service);
            admin = Admin.build(web3Service);
      /*  String contractAddress = null;
        Credentials credentials = null;//可以根据私钥生成*/
            String contractAddress = "0xa112125702b5b54362a37c7aa633692ad480f20e";
            Credentials credentials = WalletUtils.loadCredentials("135246pzh", "C:\\Work\\testkeystore\\test-keystore-2.json");
            String myAddress = "0x2071049cf01a7c357c67a7e34f00705c0335f033";
            // load(String contractAddress, Web3 web3, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit)
            RawTransactionManager transactionManager = new RawTransactionManager(admin, credentials, ChainId.MAINNET);
            TokenERC20 contract = TokenERC20.load(contractAddress, web3, transactionManager, Convert.toWei("20", Convert.Unit.GWEI).toBigInteger(), BigInteger.valueOf(10000000));
            BigInteger balance = contract.balanceOf(myAddress).send();
            System.out.println("testErc20TransactionShangToken1  balance ====" + balance);
            String toAddress = "0xdb4d90c1fe9f840eabbd0a7fba0b0f7293301725";
            BigInteger amount = BigInteger.valueOf(10000L);
            amount = BigInteger.TEN;
            amount = BigInteger.valueOf(100086L);
            TransactionReceipt receipt = contract.transfer(toAddress, amount).sendAsync().get(7, TimeUnit.MINUTES);
            logger.info("testErc20Transaction receipt  hash ====" + receipt.getTransactionHash());
            List<TokenERC20.TransferEventResponse> transferEventResponseList = contract.getTransferEvents(receipt);
            for (TokenERC20.TransferEventResponse tr : transferEventResponseList) {
                logger.info("testErc20Transaction tr =====" + GsonUtil.gsonString(tr));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testErc20TransactionShangToken2() {
        try {
            Web3Service web3Service = new HttpService(Environment.RPC_URL, new OkHttpClient.Builder().retryOnConnectionFailure(true).connectTimeout(60 * 30, TimeUnit.SECONDS).build(), true);
            Web3 web3 = Web3.build(web3Service);
            admin = Admin.build(web3Service);
      /*  String contractAddress = null;
        Credentials credentials = null;//可以根据私钥生成*/
            String contractAddress = "0xaa5417ba9d214a0e32a1b089c350eb09004e9cbb2b139f7028ea39e5447707a9";
            contractAddress = "0xa112125702b5b54362a37c7aa633692ad480f20e";
            Credentials credentials = WalletUtils.loadCredentials("135246pzh", "C:\\Work\\testkeystore\\test-keystore-2.json");
            String myAddress = "0x2071049cf01a7c357c67a7e34f00705c0335f033";
            // load(String contractAddress, Web3 web3, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit)
            RawTransactionManager transactionManager = new RawTransactionManager(admin, credentials, ChainId.MAINNET);
            TokenERC20 contract = TokenERC20.load(contractAddress, web3, transactionManager, Convert.toWei("20", Convert.Unit.GWEI).toBigInteger(), BigInteger.valueOf(10000000));
            BigInteger balance = contract.balanceOf(myAddress).send();
            System.out.println("testErc20TransactionShangToken2  balance ====" + balance);
            String toAddress = "0xdb4d90c1fe9f840eabbd0a7fba0b0f7293301725";
            BigInteger amount = BigInteger.valueOf(10000L);
            amount = BigInteger.TEN;
            amount = BigInteger.valueOf(100086L);
            TransactionReceipt receipt = contract.transfer(toAddress, amount).sendAsync().get(7, TimeUnit.MINUTES);
            logger.info("testErc20Transaction receipt  hash ====" + receipt.getTransactionHash());
            List<TokenERC20.TransferEventResponse> transferEventResponseList = contract.getTransferEvents(receipt);
            for (TokenERC20.TransferEventResponse tr : transferEventResponseList) {
                logger.info("testErc20Transaction tr =====" + GsonUtil.gsonString(tr));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //0xb6b5408f889355c068e910d0f468b055cb88a551

    private static void testErc20TransactionShangToken0xb6b5408f889355c068e910d0f468b055cb88a551() {
        try {
            Web3Service web3Service = new HttpService(Environment.RPC_URL, new OkHttpClient.Builder().retryOnConnectionFailure(true).connectTimeout(60 * 30, TimeUnit.SECONDS).build(), true);
            Web3 web3 = Web3.build(web3Service);
            admin = Admin.build(web3Service);
            /*  String contractAddress = null;
                Credentials credentials = null;//可以根据私钥生成
            */
            String contractAddress = "0xb6b5408f889355c068e910d0f468b055cb88a551";
            contractAddress="0xa112125702b5b54362a37c7aa633692ad480f20e";
            contractAddress = "0x990634344c50566a5ba26f883a2ab16ae656b329";
            Credentials credentials = WalletUtils.loadCredentials("135246pzh", "C:\\Work\\testkeystore\\test-keystore-2.json");
            String myAddress = "0x2071049cf01a7c357c67a7e34f00705c0335f033";
            // load(String contractAddress, Web3 web3, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit)
            RawTransactionManager transactionManager = new RawTransactionManager(admin, credentials, ChainId.MAINNET);
            TokenERC20 contract = TokenERC20.load(contractAddress, web3, transactionManager, Convert.toWei("20", Convert.Unit.GWEI).toBigInteger(), BigInteger.valueOf(10000000));
            BigInteger balance = contract.balanceOf(myAddress).send();
            System.out.println("testErc20TransactionShangToken0xb6b5408f889355c068e910d0f468b055cb88a551  balance ====" + balance);
            String toAddress = "0xdb4d90c1fe9f840eabbd0a7fba0b0f7293301725";
            toAddress = "0x73e21bc8289fabb00d3da963693b1971dbb9e424";
            BigInteger amount = BigInteger.valueOf(10000L);
            amount = BigInteger.TEN;
            amount = BigInteger.valueOf(186L);
           TransactionReceipt receipt = contract.transfer(toAddress, amount).sendAsync().get(7, TimeUnit.MINUTES);
            logger.info("testErc20Transaction receipt  hash ====" + receipt.getTransactionHash());
            List<TokenERC20.TransferEventResponse> transferEventResponseList = contract.getTransferEvents(receipt);
            for (TokenERC20.TransferEventResponse tr : transferEventResponseList) {
                logger.info("testErc20Transaction tr =====" + GsonUtil.gsonString(tr));
            }

           boolean flag = contract.isValid();
            System.out.println("flag ===="+flag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getContractAddressByTxHash() {
        logger.info("getContractAddressByTxHash");
        String hash = "0x0013aa0a680aed31cca985534e3e9bacfa3722d625e6cf07259ec201ebd37b08";
        try {
            HpbGetTransactionReceipt hpbGetTransactionReceipt = admin.hpbGetTransactionReceipt(hash).sendAsync().get(2, TimeUnit.MINUTES);
            if (hpbGetTransactionReceipt != null && hpbGetTransactionReceipt.getTransactionReceipt().isPresent()) {
                String contractAddress = hpbGetTransactionReceipt.getTransactionReceipt().get().getContractAddress();
                logger.info("contractAddress ===" + contractAddress);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }



    private static void testErc20TransactionShangTokenTestNet() {
        try {
            Web3Service web3Service = new HttpService(Environment.RPC_URL, new OkHttpClient.Builder().retryOnConnectionFailure(true).connectTimeout(60 * 30, TimeUnit.SECONDS).build(), true);
            Web3 web3 = Web3.build(web3Service);
            admin = Admin.build(web3Service);

            String
            contractAddress = "0xfda5331d0ad8bdfdefe650218429f9924fd3b18c";
            Credentials credentials = WalletUtils.loadCredentials("135246pzh", "C:\\Work\\testkeystore\\test-keystore-2.json");
            String myAddress = "0x2071049cf01a7c357c67a7e34f00705c0335f033";
            RawTransactionManager transactionManager = new RawTransactionManager(admin, credentials, 269);
            TokenERC20 contract = TokenERC20.load(contractAddress, web3, transactionManager, Convert.toWei("20", Convert.Unit.GWEI).toBigInteger(), BigInteger.valueOf(10000000));
            BigInteger balance = contract.balanceOf(myAddress).send();
            System.out.println("testErc20TransactionShangTokenTestNet  balance ====" + balance);
            String toAddress = "0xdb4d90c1fe9f840eabbd0a7fba0b0f7293301725";
            toAddress = "0x784c9d2190462BA2E2af8071003BBD3A498D9259";
            toAddress="0xc132aE8cebeFe984Ccd56C4876fa22326a22ddC9";
            BigInteger amount = BigInteger.valueOf(10000L);
            amount = BigInteger.TEN;
            Long LL =  1000000000000000000L ;
            amount = BigInteger.valueOf(LL);
            amount = balance.divide(BigInteger.valueOf(5));
            TransactionReceipt receipt = contract.transfer(toAddress, amount).sendAsync().get(7, TimeUnit.MINUTES);
            logger.info("testErc20Transaction receipt  hash ====" + receipt.getTransactionHash());
            List<TokenERC20.TransferEventResponse> transferEventResponseList = contract.getTransferEvents(receipt);
            for (TokenERC20.TransferEventResponse tr : transferEventResponseList) {
                logger.info("testErc20Transaction tr =====" + GsonUtil.gsonString(tr));
            }
            boolean flag = contract.isValid();
            System.out.println("flag ===="+flag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 0x0d34b19f91ac06036157f577fc74fb9375ca554d

    private static void testErc20TransactionShangToken0x0d34b19f91ac06036157f577fc74fb9375ca554d() {
        try {
            Web3Service web3Service = new HttpService(Environment.RPC_URL, new OkHttpClient.Builder().retryOnConnectionFailure(true).connectTimeout(60 * 30, TimeUnit.SECONDS).build(), true);
            Web3 web3 = Web3.build(web3Service);
            admin = Admin.build(web3Service);
            /*  String contractAddress = null;
                Credentials credentials = null;//可以根据私钥生成
            */
            String contractAddress = "0xb6b5408f889355c068e910d0f468b055cb88a551";
            contractAddress="0xa112125702b5b54362a37c7aa633692ad480f20e";
            contractAddress = "0x0d34b19f91ac06036157f577fc74fb9375ca554d";
            Credentials credentials = WalletUtils.loadCredentials("135246pzh", "C:\\Work\\testkeystore\\test-keystore-2.json");
            String myAddress = "0x2071049cf01a7c357c67a7e34f00705c0335f033";
            // load(String contractAddress, Web3 web3, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit)
            RawTransactionManager transactionManager = new RawTransactionManager(admin, credentials, ChainId.MAINNET);
            TokenERC20 contract = TokenERC20.load(contractAddress, web3, transactionManager, Convert.toWei("20", Convert.Unit.GWEI).toBigInteger(), BigInteger.valueOf(10000000));
            BigInteger balance = contract.balanceOf(myAddress).send();
            System.out.println("testErc20TransactionShangToken0x0d34b19f91ac06036157f577fc74fb9375ca554d  balance ====" + balance);
            String toAddress = "0xdb4d90c1fe9f840eabbd0a7fba0b0f7293301725";
            toAddress = "0x73e21bc8289fabb00d3da963693b1971dbb9e424";
            BigInteger amount = BigInteger.valueOf(10000L);
            amount = BigInteger.TEN;
            //amount = BigInteger.valueOf(186L);
            TransactionReceipt receipt = contract.transfer(toAddress, amount).sendAsync().get(7, TimeUnit.MINUTES);
            logger.info("testErc20Transaction receipt  hash ====" + receipt.getTransactionHash());
            List<TokenERC20.TransferEventResponse> transferEventResponseList = contract.getTransferEvents(receipt);
            for (TokenERC20.TransferEventResponse tr : transferEventResponseList) {
                logger.info("testErc20Transaction tr =====" + GsonUtil.gsonString(tr));
            }

            boolean flag = contract.isValid();
            System.out.println("flag ===="+flag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
