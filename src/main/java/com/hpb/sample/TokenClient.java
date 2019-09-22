package com.hpb.sample;

import com.hpb.sample.utils.Environment;
import io.hpb.web3.abi.FunctionEncoder;
import io.hpb.web3.abi.FunctionReturnDecoder;
import io.hpb.web3.abi.TypeReference;
import io.hpb.web3.abi.datatypes.*;
import io.hpb.web3.abi.datatypes.generated.Uint256;
import io.hpb.web3.abi.datatypes.generated.Uint8;
import io.hpb.web3.crypto.Hash;
import io.hpb.web3.protocol.Web3;
import io.hpb.web3.protocol.admin.Admin;
import io.hpb.web3.protocol.admin.methods.response.PersonalUnlockAccount;
import io.hpb.web3.protocol.core.DefaultBlockParameterName;
import io.hpb.web3.protocol.core.methods.request.Transaction;
import io.hpb.web3.protocol.core.methods.response.HpbCall;
import io.hpb.web3.protocol.core.methods.response.HpbGetTransactionCount;
import io.hpb.web3.protocol.core.methods.response.HpbSendTransaction;
import io.hpb.web3.protocol.http.HttpService;
import io.hpb.web3.rlp.RlpEncoder;
import io.hpb.web3.rlp.RlpList;
import io.hpb.web3.rlp.RlpString;
import io.hpb.web3.utils.Convert;
import io.hpb.web3.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 基于ERC20的代币
 */
public class TokenClient {

	private static Web3 web3;

	private static Admin admin;

	private static String fromAddress = "0x7b1cc408fcb2de1d510c1bf46a329e9027db4112";

	private static String contractAddress = "0x4c1ae77bc2df45fb68b13fa1b4f000305209b0cb";

	private static String emptyAddress = "0x0000000000000000000000000000000000000000";

	public static void main(String[] args) {
		web3 = Web3.build(new HttpService(Environment.RPC_URL));
		admin = Admin.build(new HttpService(Environment.RPC_URL));
		getTokenBalance(web3, fromAddress, contractAddress);
		System.out.println(getTokenName(web3, contractAddress));
		System.out.println(getTokenDecimals(web3, contractAddress));
		System.out.println(getTokenSymbol(web3, contractAddress));
		System.out.println(getTokenTotalSupply(web3, contractAddress));
		System.out.println(sendTokenTransaction(
				fromAddress, "yzw",
				"0x6c0f49aF552F2326DD851b68832730CB7b6C0DaF", contractAddress,
				BigInteger.valueOf(100000)));
	}

	/**
	 * 查询代币余额
	 */
	public static BigInteger getTokenBalance(Web3 web3, String fromAddress, String contractAddress) {

		String mhpbodName = "balanceOf";
		List<Type> inputParameters = new ArrayList<>();
		List<TypeReference<?>> outputParameters = new ArrayList<>();
		Address address = new Address(fromAddress);
		inputParameters.add(address);

		TypeReference<Uint256> typeReference = new TypeReference<Uint256>() {
		};
		outputParameters.add(typeReference);
		Function function = new Function(mhpbodName, inputParameters, outputParameters);
		String data = FunctionEncoder.encode(function);
		Transaction transaction = Transaction.createHpbCallTransaction(fromAddress, contractAddress, data);

	HpbCall hpbCall;
		BigInteger balanceValue = BigInteger.ZERO;
		try {
			hpbCall = web3.hpbCall(transaction, DefaultBlockParameterName.LATEST).send();
			List<Type> results = FunctionReturnDecoder.decode(hpbCall.getValue(), function.getOutputParameters());
			balanceValue = (BigInteger) results.get(0).getValue();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return balanceValue;
	}

	/**
	 * 查询代币名称
	 *
	 * @param web3
	 * @param contractAddress
	 * @return
	 */
	public static String getTokenName(Web3 web3, String contractAddress) {
		String mhpbodName = "name";
		String name = null;
		String fromAddr = emptyAddress;
		List<Type> inputParameters = new ArrayList<>();
		List<TypeReference<?>> outputParameters = new ArrayList<>();

		TypeReference<Utf8String> typeReference = new TypeReference<Utf8String>() {
		};
		outputParameters.add(typeReference);

		Function function = new Function(mhpbodName, inputParameters, outputParameters);

		String data = FunctionEncoder.encode(function);
		Transaction transaction = Transaction.createHpbCallTransaction(fromAddr, contractAddress, data);

	HpbCall hpbCall;
		try {
			hpbCall = web3.hpbCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
			List<Type> results = FunctionReturnDecoder.decode(hpbCall.getValue(), function.getOutputParameters());
			name = results.get(0).getValue().toString();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return name;
	}

	/**
	 * 查询代币符号
	 *
	 * @param web3
	 * @param contractAddress
	 * @return
	 */
	public static String getTokenSymbol(Web3 web3, String contractAddress) {
		String mhpbodName = "symbol";
		String symbol = null;
		String fromAddr = emptyAddress;
		List<Type> inputParameters = new ArrayList<>();
		List<TypeReference<?>> outputParameters = new ArrayList<>();

		TypeReference<Utf8String> typeReference = new TypeReference<Utf8String>() {
		};
		outputParameters.add(typeReference);

		Function function = new Function(mhpbodName, inputParameters, outputParameters);

		String data = FunctionEncoder.encode(function);
		Transaction transaction = Transaction.createHpbCallTransaction(fromAddr, contractAddress, data);

	HpbCall hpbCall;
		try {
			hpbCall = web3.hpbCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
			List<Type> results = FunctionReturnDecoder.decode(hpbCall.getValue(), function.getOutputParameters());
			symbol = results.get(0).getValue().toString();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return symbol;
	}

	/**
	 * 查询代币精度
	 *
	 * @param web3
	 * @param contractAddress
	 * @return
	 */
	public static int getTokenDecimals(Web3 web3, String contractAddress) {
		String mhpbodName = "decimals";
		String fromAddr = emptyAddress;
		int decimal = 0;
		List<Type> inputParameters = new ArrayList<>();
		List<TypeReference<?>> outputParameters = new ArrayList<>();

		TypeReference<Uint8> typeReference = new TypeReference<Uint8>() {
		};
		outputParameters.add(typeReference);

		Function function = new Function(mhpbodName, inputParameters, outputParameters);

		String data = FunctionEncoder.encode(function);
		Transaction transaction = Transaction.createHpbCallTransaction(fromAddr, contractAddress, data);

	HpbCall hpbCall;
		try {
			hpbCall = web3.hpbCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
			List<Type> results = FunctionReturnDecoder.decode(hpbCall.getValue(), function.getOutputParameters());
			decimal = Integer.parseInt(results.get(0).getValue().toString());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return decimal;
	}

	/**
	 * 查询代币发行总量
	 *
	 * @param web3
	 * @param contractAddress
	 * @return
	 */
	public static BigInteger getTokenTotalSupply(Web3 web3, String contractAddress) {
		String mhpbodName = "totalSupply";
		String fromAddr = emptyAddress;
		BigInteger totalSupply = BigInteger.ZERO;
		List<Type> inputParameters = new ArrayList<>();
		List<TypeReference<?>> outputParameters = new ArrayList<>();

		TypeReference<Uint256> typeReference = new TypeReference<Uint256>() {
		};
		outputParameters.add(typeReference);

		Function function = new Function(mhpbodName, inputParameters, outputParameters);

		String data = FunctionEncoder.encode(function);
		Transaction transaction = Transaction.createHpbCallTransaction(fromAddr, contractAddress, data);

	HpbCall hpbCall;
		try {
			hpbCall = web3.hpbCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
			List<Type> results = FunctionReturnDecoder.decode(hpbCall.getValue(), function.getOutputParameters());
			totalSupply = (BigInteger) results.get(0).getValue();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return totalSupply;
	}

	/**
	 * 代币转账
	 */
	public static String sendTokenTransaction(String fromAddress, String password, String toAddress, String contractAddress, BigInteger amount) {
		String txHash = null;

		try {
			PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(
					fromAddress, password, BigInteger.valueOf(10)).send();
			if (personalUnlockAccount.accountUnlocked()) {
				String mhpbodName = "transfer";
				List<Type> inputParameters = new ArrayList<>();
				List<TypeReference<?>> outputParameters = new ArrayList<>();

				Address tAddress = new Address(toAddress);

				Uint256 value = new Uint256(amount);
				inputParameters.add(tAddress);
				inputParameters.add(value);

				TypeReference<Bool> typeReference = new TypeReference<Bool>() {
				};
				outputParameters.add(typeReference);

				Function function = new Function(mhpbodName, inputParameters, outputParameters);

				String data = FunctionEncoder.encode(function);

			HpbGetTransactionCount hpbGetTransactionCount = web3
						.hpbGetTransactionCount(fromAddress, DefaultBlockParameterName.PENDING).sendAsync().get();
				BigInteger nonce = hpbGetTransactionCount.getTransactionCount();
				BigInteger gasPrice = Convert.toWei(BigDecimal.valueOf(5), Convert.Unit.GWEI).toBigInteger();

				Transaction transaction = Transaction.createFunctionCallTransaction(fromAddress, nonce, gasPrice,
						BigInteger.valueOf(60000), contractAddress, data);

			HpbSendTransaction hpbSendTransaction = web3.hpbSendTransaction(transaction).sendAsync().get();
				txHash = hpbSendTransaction.getTransactionHash();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return txHash;
	}

}
