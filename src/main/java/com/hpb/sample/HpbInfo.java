package com.hpb.sample;

import com.hpb.sample.utils.Environment;
import io.hpb.web3.protocol.Web3;
import io.hpb.web3.protocol.core.methods.response.*;
import io.hpb.web3.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;

public class HpbInfo {
	private static Web3 web3;

	public static void main(String[] args) {
		web3 = Web3.build(new HttpService(Environment.RPC_URL));
		geHpbInfo();
	}

	/**
	 * 请求区块链的信息
	 */
	private static void geHpbInfo() {

		Web3ClientVersion web3ClientVersion = null;
		try {
			//客户端版本
			web3ClientVersion = web3.web3ClientVersion().send();
			String clientVersion = web3ClientVersion.getWeb3ClientVersion();
			System.out.println("clientVersion " + clientVersion);
			//区块数量
		HpbBlockNumber hpbBlockNumber = web3.hpbBlockNumber().send();
			BigInteger blockNumber = hpbBlockNumber.getBlockNumber();
			System.out.println(blockNumber);
			//挖矿奖励账户
		HpbCoinbase hpbCoinbase = web3.hpbCoinbase().send();
			String coinbaseAddress = hpbCoinbase.getAddress();
			System.out.println(coinbaseAddress);
			//是否在同步区块
		HpbSyncing hpbSyncing = web3.hpbSyncing().send();
			boolean isSyncing = hpbSyncing.isSyncing();
			System.out.println(isSyncing);
			//是否在挖矿
		HpbMining hpbMining = web3.hpbMining().send();
			boolean isMining = hpbMining.isMining();
			System.out.println(isMining);
			//当前gas price
		HpbGasPrice hpbGasPrice = web3.hpbGasPrice().send();
			BigInteger gasPrice = hpbGasPrice.getGasPrice();
			System.out.println(gasPrice);
			//挖矿速度
		HpbHashrate hpbHashrate = web3.hpbHashrate().send();
			BigInteger hashRate = hpbHashrate.getHashrate();
			System.out.println(hashRate);
			//协议版本
		HpbProtocolVersion hpbProtocolVersion = web3.hpbProtocolVersion().send();
			String protocolVersion = hpbProtocolVersion.getProtocolVersion();
			System.out.println(protocolVersion);
			//连接的节点数
			NetPeerCount netPeerCount = web3.netPeerCount().send();
			BigInteger peerCount = netPeerCount.getQuantity();
			System.out.println(peerCount);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
