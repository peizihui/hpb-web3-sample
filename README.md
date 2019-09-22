# web3-sample
web3 for java 样例程序 (基于web3 3.6.0)   
环境 idea maven  
运行前提 需要有一个开启RPC或者IPC服务的以太坊节点

- [QuickStart](https://github.com/hpbjava/web3-sample/blob/master/src/main/java/com/hpbjava/QuickStart.java) 快速开始

- [AccountManager](https://github.com/hpbjava/web3-sample/blob/master/src/main/java/com/hpbjava/AccountManager.java) 账号相关接口
- [TransactionClient](https://github.com/hpbjava/web3-sample/blob/master/src/main/java/com/hpbjava/TransactionClient.java) hpb转账相关接口
- [TokenClient](https://github.com/hpbjava/web3-sample/blob/master/src/main/java/com/hpbjava/TokenClient.java) token代币相关查询及转账
- [ColdWallet](https://github.com/hpbjava/web3-sample/blob/master/src/main/java/com/hpbjava/ColdWallet.java) 冷钱包创建、交易
- [TokenBalanceTask](https://github.com/hpbjava/web3-sample/blob/master/src/main/java/com/hpbjava/TokenBalanceTask.java) 批量token代币余额查询
- HpbInfo](https://github.com/hpbjava/web3-sample/blob/master/src/main/java/com/hpbjavaHpbInfo.java) 连接节点的相关信息接口
- [Security](https://github.com/hpbjava/web3-sample/blob/master/src/main/java/com/hpbjava/Security.java) 公钥私钥相关接口
- HpbMnemonic](https://github.com/hpbjava/web3-sample/blob/master/src/main/java/com/hpbjavaHpbMnemonic.java) 生成、导入助记词(需要比特币的jar包 可以查看pom.xml)
- [Filter](https://github.com/hpbjava/web3-sample/blob/master/src/main/java/com/hpbjava/Filter.java) 新块、新交易相关监听
- [ContractEvent](https://github.com/hpbjava/web3-sample/blob/master/src/main/java/com/hpbjava/ContractEvent.java) 执行合约相关log监听
- [DecodeMessage](https://github.com/hpbjava/web3-sample/blob/master/src/main/java/com/hpbjava/DecodeMessage.java) 加密后的交易数据解析
- [IBAN](https://github.com/hpbjava/web3-sample/blob/master/src/main/java/com/hpbjava/IBAN.java) 根据官方规则生成iban及付款二维码
- [Calculate](https://github.com/hpbjava/web3-sample/blob/master/src/main/java/com/hpbjava/Calculate.java) 在发布合约前计算合约地址，根据签名后的交易信息计算TxHash
- [Solidity](https://github.com/hpbjava/web3-sample/tree/master/src/main/java/com/hpbjava/sol) 合约类相关
- [LinkToken Support](https://github.com/hpbjava/web3-sample/tree/LinkToken) 链克支持

--- 

打赏 hpb地址:0x248F272180db4D079443753336c5C847A080275c

[博客Blog](https://blog.csdn.net/u011181222)