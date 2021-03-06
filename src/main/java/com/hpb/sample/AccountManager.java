package com.hpb.sample;

import com.hpb.sample.utils.Environment;
import io.hpb.web3.protocol.admin.Admin;
import io.hpb.web3.protocol.admin.methods.response.NewAccountIdentifier;
import io.hpb.web3.protocol.admin.methods.response.PersonalListAccounts;
import io.hpb.web3.protocol.admin.methods.response.PersonalUnlockAccount;
import io.hpb.web3.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

/**
 * 账号管理相关
 */
public class AccountManager {
	private static Admin admin;

	public static void main(String[] args) {
		admin = Admin.build(new HttpService(Environment.RPC_URL));
		// createNewAccount();// 0x9af766b27f1f67afb2131cafa5b18ed7414672a5
		// getAccountList();
		unlockAccount();

//		admin.personalSendTransaction(); 该方法与web3.sendTransaction相同 不在此写例子。
	}

	/**
	 * 创建账号
	 */
	private static void createNewAccount() {
		String password = "123456789";
		try {
			NewAccountIdentifier newAccountIdentifier = admin.personalNewAccount(password).send();
			String address = newAccountIdentifier.getAccountId();
			System.out.println("new account address " + address);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取账号列表
	 */
	private static void getAccountList() {
		try {
			PersonalListAccounts personalListAccounts = admin.personalListAccounts().send();
			List<String> addressList;
			addressList = personalListAccounts.getAccountIds();
			System.out.println("account size " + addressList.size());
			for (String address : addressList) {
				System.out.println(address);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 账号解锁
	 */
	private static void unlockAccount() {
		String address = "0x05f50cd5a97d9b3fec35df3d0c6c8234e6793bdf";
		String password = "123456789";
		//账号解锁持续时间 单位秒 缺省值300秒
		BigInteger unlockDuration = BigInteger.valueOf(60L);
		try {
			PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(address, password, unlockDuration).send();
			Boolean isUnlocked = personalUnlockAccount.accountUnlocked();
			System.out.println("account unlock " + isUnlocked);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
