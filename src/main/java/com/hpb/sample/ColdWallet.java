package com.hpb.sample;

import com.hpb.sample.utils.Environment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.hpb.web3.abi.FunctionEncoder;
import io.hpb.web3.abi.TypeReference;
import io.hpb.web3.abi.datatypes.Address;
import io.hpb.web3.abi.datatypes.Bool;
import io.hpb.web3.abi.datatypes.Function;
import io.hpb.web3.abi.datatypes.Type;
import io.hpb.web3.abi.datatypes.generated.Uint256;
import io.hpb.web3.crypto.*;
import io.hpb.web3.protocol.ObjectMapperFactory;
import io.hpb.web3.protocol.Web3;
import io.hpb.web3.protocol.core.DefaultBlockParameterName;
import io.hpb.web3.protocol.core.methods.response.*;
import io.hpb.web3.protocol.core.methods.response.HpbGetTransactionCount;
import io.hpb.web3.protocol.core.methods.response.HpbSendTransaction;
import io.hpb.web3.protocol.http.HttpService;
import io.hpb.web3.tx.ChainId;
import io.hpb.web3.utils.Convert;
import io.hpb.web3.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 冷钱包
 * 账号 交易相关
 */
public class ColdWallet {

	private static Web3 web3;

	private static String d = "/Users/yangzhengwei/Documents/hpb/coldwallet";

//	private static String address = "0xa530d89646db11abfa701e148e87324355fc6ea7";

	private static String keystore = "{\"address\":\"a530d89646db11abfa701e148e87324355fc6ea7\",\"id\":\"246e7d1d-8f31-4a3e-951d-41722213a44f\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"26d10977bc199f6b678e89d3b7c3874bab3cddda18b92c014890d80657d7cc6a\",\"cipherparams\":{\"iv\":\"beaa9a404f793e86460a1fc71a0372a8\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"f06eb3d208db1643671c6e0210789f05e6de1746252fe5b83a38618e2bd18f1e\"},\"mac\":\"0aa4f85dfecaf8203ad0ee22c47ff6fb35b8f47d8f56ddb890ef2d513a06a801\"}}\n";

//	private static String privateKey = "f4529331f460fa88cc14eb981baf90201e7fc709386bf2f5b9ec687639f70086";

	//test for ok;


    private static String address = "0xdbc3f6a340eb8d065c6ccb06437a34d984d61134";

    private static String privateKey = "66ac6da2c86a87dc633ad9f068d26258bc5b2d41b33404b080fa88bdca6c7496";


	public static void main(String[] args) {
		web3 = Web3.build(new HttpService(Environment.RPC_URL));
		try {
//			createWallet("11111111");
//			decryptWallet(keystore, "11111111");
            getBalance("0xdbc3f6a340eb8d065c6ccb06437a34d984d61134");
			testTransaction();

//			testTokenTransaction();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    /**
     * 获取余额
     *
     * @param address 钱包地址
     * @return 余额
     */
    private static BigInteger getBalance(String address) {
        BigInteger balance = null;
        try {
            HpbGetBalance hpbGetBalance = web3.hpbGetBalance(address, DefaultBlockParameterName.LATEST).send();
            balance = hpbGetBalance.getBalance();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("address " + address + " balance " + balance + "wei");
        return balance;
    }


    private static void testTransaction() {
		BigInteger nonce;
	HpbGetTransactionCount hpbGetTransactionCount = null;
		try {
			hpbGetTransactionCount = web3.hpbGetTransactionCount(address, DefaultBlockParameterName.PENDING).send();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (hpbGetTransactionCount == null)
		{
			return;
		};
		nonce = hpbGetTransactionCount.getTransactionCount();
		BigInteger gasPrice = Convert.toWei(BigDecimal.valueOf(3), Convert.Unit.GWEI).toBigInteger();
		BigInteger gasLimit = BigInteger.valueOf(30000);
		String to = "0x6c0f49aF552F2326DD851b68832730CB7b6C0DaF".toLowerCase();
		 to = "0xbfdc9d8da67abd04a0fdbb9ebd593687801b3885";
		BigInteger value = Convert.toWei(BigDecimal.valueOf(0.01), Convert.Unit.HPB).toBigInteger();
		String data = "";
		int chainId = ChainId.MAINNET_269;//测试网络
		String privateKey = ColdWallet.privateKey;
		String signedData;
        System.out.println("nonce =="+nonce.longValue());
        try {
            signedData = signTransaction(nonce, gasPrice, gasLimit, to, value, data, chainId, privateKey);
            if (signedData != null) {
            HpbSendTransaction hpbSendTransaction = web3.hpbSendRawTransaction(signedData).sendAsync().get(2,TimeUnit.MINUTES)
;
                System.out.println(hpbSendTransaction.getTransactionHash());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }

	private static void testTokenTransaction(Web3 web3, String fromAddress, String privateKey, String contractAddress, String toAddress, double amount, int decimals) {
	BigInteger nonce;
	HpbGetTransactionCount hpbGetTransactionCount = null;
		try {
			hpbGetTransactionCount = web3.hpbGetTransactionCount(fromAddress, DefaultBlockParameterName.PENDING).send();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (hpbGetTransactionCount == null){
			return;
		} ;
		nonce = hpbGetTransactionCount.getTransactionCount();
		System.out.println("nonce " + nonce);
		BigInteger gasPrice = Convert.toWei(BigDecimal.valueOf(3), Convert.Unit.GWEI).toBigInteger();
		BigInteger gasLimit = BigInteger.valueOf(60000);
		BigInteger value = BigInteger.ZERO;
		//token转账参数
		String mhpbodName = "transfer";
		List<Type> inputParameters = new ArrayList<>();
		List<TypeReference<?>> outputParameters = new ArrayList<>();
		Address tAddress = new Address(toAddress);
		Uint256 tokenValue = new Uint256(BigDecimal.valueOf(amount).multiply(BigDecimal.TEN.pow(decimals)).toBigInteger());
		inputParameters.add(tAddress);
		inputParameters.add(tokenValue);
		TypeReference<Bool> typeReference = new TypeReference<Bool>() {
		};
		outputParameters.add(typeReference);
		Function function = new Function(mhpbodName, inputParameters, outputParameters);
		String data = FunctionEncoder.encode(function);

		byte chainId = ChainId.NONE;
		String signedData;
		try {
			signedData = ColdWallet.signTransaction(nonce, gasPrice, gasLimit, contractAddress, value, data, chainId, privateKey);
			if (signedData != null) {
                HpbSendTransaction hpbSendTransaction = null;
                try {
                    hpbSendTransaction = web3.hpbSendRawTransaction(signedData).sendAsync().get(2, TimeUnit.MINUTES);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
                System.out.println(hpbSendTransaction.getTransactionHash());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 创建钱包
	 *
	 * @param password 密码
	 */
	public static void createWallet(String password) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, CipherException, JsonProcessingException {
        WalletFile walletFile;
        ECKeyPair ecKeyPair = Keys.createEcKeyPair();
        walletFile = Wallet.createStandard(password, ecKeyPair);
        System.out.println("address " + walletFile.getAddress());
        ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(walletFile);
        System.out.println("keystore json file " + jsonStr);
    }

	/**
	 * 解密keystore 得到私钥
	 *
	 * @param keystore
	 * @param password
	 */
	public static String decryptWallet(String keystore, String password) {
		String privateKey = null;
		ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
		try {
			WalletFile walletFile = objectMapper.readValue(keystore, WalletFile.class);
			ECKeyPair ecKeyPair = null;
			ecKeyPair = Wallet.decrypt(password, walletFile);
			privateKey = ecKeyPair.getPrivateKey().toString(16);
			System.out.println(privateKey);
		} catch (CipherException e) {
			if ("Invalid password provided".equals(e.getMessage())) {
				System.out.println("密码错误");
			}
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return privateKey;
	}

	/**
	 * 签名交易
	 */
	public static String signTransaction(BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to,
										 BigInteger value, String data, int chainId, String privateKey) throws IOException {
		byte[] signedMessage;
		RawTransaction rawTransaction = RawTransaction.createTransaction(
				nonce,
				gasPrice,
				gasLimit,
				to,
				value,
				data);

		if (privateKey.startsWith("0x")) {
			privateKey = privateKey.substring(2);
		}
		ECKeyPair ecKeyPair = ECKeyPair.create(new BigInteger(privateKey, 16));
		Credentials credentials = Credentials.create(ecKeyPair);

		if (chainId > ChainId.NONE) {
			signedMessage = TransactionEncoder.signMessage(rawTransaction, chainId, credentials);
		} else {
			signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
		}

		String hexValue = Numeric.toHexString(signedMessage);
		return hexValue;
	}

}
