package com.hpb.sample;

import com.hpb.sample.utils.Environment;
import io.hpb.web3.protocol.Web3;
import io.hpb.web3.protocol.core.methods.response.Web3ClientVersion;
import io.hpb.web3.protocol.http.HttpService;

import java.io.IOException;


/**
 * 快速开始
 */
public class QuickStart {

	private static Web3 web3;

	public static void main(String[] args) {
		web3 = Web3.build(new HttpService(Environment.RPC_URL));

		Web3ClientVersion web3ClientVersion = null;
		try {
			web3ClientVersion = web3.web3ClientVersion().send();
			String clientVersion = web3ClientVersion.getWeb3ClientVersion();
			System.out.println("clientVersion " + clientVersion);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
