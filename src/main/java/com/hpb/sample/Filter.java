package com.hpb.sample;

import com.hpb.sample.utils.Environment;
import io.hpb.web3.protocol.Web3;
import io.hpb.web3.protocol.core.DefaultBlockParameter;
import io.hpb.web3.protocol.http.HttpService;
import rx.Subscription;

import java.math.BigInteger;

/**
 * filter相关
 * 监听区块、交易
 * 所有监听都在Web3Rx中
 */
public class Filter {
	private static Web3 web3;

	public static void main(String[] args) {
		web3 = Web3.build(new HttpService(Environment.RPC_URL));
		/**
		 * 新区块监听
		 */
		newBlockFilter(web3);
		/**
		 * 新交易监听
		 */
		newTransactionFilter(web3);
		/**
		 * 遍历旧区块、交易
		 */
		replayFilter(web3);
		/**
		 * 从某一区块开始直到最新区块、交易
		 */
		catchUpFilter(web3);

		/**
		 * 取消监听
		 */
//		subscription.unsubscribe();
	}

	private static void newBlockFilter(Web3 web3) {
		Subscription subscription = web3.
				blockObservable(false).
				subscribe(block -> {
					System.out.println("new block come in");
					System.out.println("block number" + block.getBlock().getNumber());
				});
	}

	private static void newTransactionFilter(Web3 web3) {
		Subscription subscription = web3.
				transactionObservable().
				subscribe(transaction -> {
					System.out.println("transaction come in");
					System.out.println("transaction txHash " + transaction.getHash());
				});
	}

	private static void replayFilter(Web3 web3) {
		BigInteger startBlock = BigInteger.valueOf(2000000);
		BigInteger endBlock = BigInteger.valueOf(2010000);
		/**
		 * 遍历旧区块
		 */
		Subscription subscription = web3.
				replayBlocksObservable(DefaultBlockParameter.valueOf(startBlock), DefaultBlockParameter.valueOf(endBlock), false).
				subscribe(hpbBlock -> {
					System.out.println("replay block");
					System.out.println(hpbBlock.getBlock().getNumber());
					hpbBlock.getBlock().getTransactions().stream().forEach(tx ->{
					});
				});

		/**
		 * 遍历旧交易
		 */
		Subscription subscription1 = web3.
				replayTransactionsObservable(
						DefaultBlockParameter.valueOf(startBlock),
						DefaultBlockParameter.valueOf(endBlock)).
				subscribe(transaction -> {
					System.out.println("replay transaction");
					System.out.println("txHash " + transaction.getHash());
					System.out.println("web3 = [" + transaction.getInput() + "]");
				});
	}

	private static void catchUpFilter(Web3 web3) {
		BigInteger startBlock = BigInteger.valueOf(2000000);

		/**
		 * 遍历旧区块，监听新区块
		 */
		Subscription subscription = web3.catchUpToLatestAndSubscribeToNewBlocksObservable(
				DefaultBlockParameter.valueOf(startBlock), false)
				.subscribe(block -> {
					System.out.println("block");
					System.out.println(block.getBlock().getNumber());
				});

		/**
		 * 遍历旧交易，监听新交易
		 */
		Subscription subscription2 = web3.catchUpToLatestAndSubscribeToNewTransactionsObservable(
				DefaultBlockParameter.valueOf(startBlock))
				.subscribe(tx -> {
					System.out.println("transaction");
					System.out.println(tx.getHash());
				});
	}
}
