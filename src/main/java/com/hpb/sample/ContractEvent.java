package com.hpb.sample;

import com.hpb.sample.sol.RedPacketToken;
import com.hpb.sample.sol.TokenERC20;
import com.hpb.sample.utils.Environment;
import com.hpb.sample.utils.GsonUtil;
import com.sun.org.apache.xpath.internal.operations.Number;
import io.hpb.web3.abi.EventEncoder;
import io.hpb.web3.abi.EventValues;
import io.hpb.web3.abi.FunctionReturnDecoder;
import io.hpb.web3.abi.TypeReference;
import io.hpb.web3.abi.datatypes.Address;
import io.hpb.web3.abi.datatypes.Event;
import io.hpb.web3.abi.datatypes.Type;
import io.hpb.web3.abi.datatypes.generated.Uint160;
import io.hpb.web3.abi.datatypes.generated.Uint256;
import io.hpb.web3.protocol.Web3;
import io.hpb.web3.protocol.core.DefaultBlockParameterName;
import io.hpb.web3.protocol.core.DefaultBlockParameterNumber;
import io.hpb.web3.protocol.core.methods.request.HpbFilter;
import io.hpb.web3.protocol.core.methods.request.HpbFilter;
import io.hpb.web3.protocol.core.methods.response.Log;
import io.hpb.web3.protocol.core.methods.response.TransactionReceipt;
import io.hpb.web3.protocol.http.HttpService;
import io.hpb.web3.protocol.websocket.events.LogNotification;
import io.hpb.web3.tx.Contract;
import io.hpb.web3.utils.Numeric;
import org.omg.CORBA.PUBLIC_MEMBER;
import rx.Observable;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static io.hpb.web3.tx.Contract.staticExtractEventParameters;

/**
 * Event log相关
 * 监听合约event
 */
public class ContractEvent {

//	private static String contractAddress = "0xb7bbc918c85566a3bc37847b3c807bc12eeff8ad";
	// 0x7ba64cf39aafd53823267248b327f1f24dcf0c2d
	// private static String contractAddress = "0x7ba64cf39aafd53823267248b327f1f24dcf0c2d";
    // 0x721fe25eae4948051b213f8fd126da0f76974819
    private static String contractAddress = "0x721fe25eae4948051b213f8fd126da0f76974819";
	private static Web3 web3;
	public static void main(String[] args) {
		web3 = Web3.build(new HttpService(Environment.RPC_URL));
		/**
		 * 监听ERC20 token 交易
		 */
		BigInteger startBlock = BigInteger.valueOf(3888000);
		BigInteger endBlock = BigInteger.valueOf(3922572);
		BigInteger b = BigInteger.valueOf(2998000);
	/*	HpbFilter filter = new HpbFilter(
				DefaultBlockParameterName.EARLIEST,
				DefaultBlockParameterName.LATEST,
				contractAddress);*/

//		testInnerTopics(startBlock);
		testErcIcoInnerTopics(startBlock);

        Address address = new Address(new Uint160(new BigInteger("1440751163237344316631156039055342943103576393042")));
        System.out.println("address.getValue() = [" + address.getValue() + "]"); ;

        Address toAddress = new Address(new Uint160(new BigInteger("1040664720171605076416141594882559365605526185493")));
        System.out.println("toAddress.getValue() = [" + toAddress.getValue() + "]"); ;




     /*   ContractEvent e = new ContractEvent();
        e.test721InnerTopics(startBlock);*/

      BigInteger bi =  Numeric.decodeQuantity("0x3735a4");
        System.out.println("bi = [" + bi + "]");
	}

	private static void testInnerTopics(BigInteger startBlock) {
//		private static String contractAddress = "0xb7bbc918c85566a3bc37847b3c807bc12eeff8ad";
        HpbFilter filter = new HpbFilter(new DefaultBlockParameterNumber(startBlock), DefaultBlockParameterName.LATEST, "0xb7bbc918c85566a3bc37847b3c807bc12eeff8ad");
        Event event = new Event("Transfer",
                Arrays.<TypeReference<?>>asList(
                        new TypeReference<Address>(true) {
                        },
                        new TypeReference<Address>(true) {
                        }, new TypeReference<Uint256>(false) {
                        }
                )
        );

        String topicData = EventEncoder.encode(event);
        filter.addSingleTopic(topicData);
        System.out.println("testInnerTopics topicData ==="+topicData);
        web3.hpbLogObservable(filter).subscribe(log -> {
            System.out.println("log.getBlockNumber() =="+log.getBlockNumber());
            System.out.println("log.getTransactionHash()=="+log.getTransactionHash());
            List<String> topics = log.getTopics();
            for (String topic : topics) {
                System.out.println("topic ===="+topic);
            }
        });
    }

	// block 3031309  tx 0xcb8d6be396cfd6b92473ca5b67eaa5d63e0fc940737148ecd9d0aafa62c28182

	// block 3031326  tx  0x93abcc02265a2dc4c25304780a740eeef503331db85c0a46ab417c39b41e5e50
	private static void testErcIcoInnerTopics(BigInteger startBlock) {
		HpbFilter filter = new HpbFilter(new DefaultBlockParameterNumber(startBlock), DefaultBlockParameterName.LATEST,
				contractAddress);
	/*	Event event = new Event("Issue",
				Arrays.<TypeReference<?>>asList(
						new TypeReference<Uint256>(false) {
						},
						new TypeReference<Address>(true) {
						}, new TypeReference<Uint256>(false) {
						},new TypeReference<Uint256>(false) {
						}
				)
		);*/


        Event event721 = new Event("Transfer",
                Arrays.<TypeReference<?>>asList(
                        new TypeReference<Address>(true) {
                        },
                        new TypeReference<Address>(true) {
                        }, new TypeReference<Uint256>(false) {
                        }
                )
        );

		String topicData = EventEncoder.encode(event721);
		filter.addSingleTopic(topicData);
		System.out.println("testErcIcoInnerTopics topicData ==="+topicData);

        web3.hpbLogObservable(filter).subscribe(log -> {
            System.out.println("testErcIcoInnerTopics og.getBlockNumber() =="+log.getBlockNumber());
            System.out.println("testErcIcoInnerTopics log.getTransactionHash()=="+log.getTransactionHash());
			System.out.println("testErcIcoInnerTopics log.getAddress()=="+ log.getAddress());
			System.out.println("testErcIcoInnerTopics log.getLogIndexRaw()=="+ log.getLogIndexRaw());
			System.out.println("testErcIcoInnerTopics log.getData()=="+ log.getData());

            List<String> topics = log.getTopics();
            for (String topic : topics) {
                System.out.println("testErcIcoInnerTopics topic ===="+topic);
            }
        });
	}

	//logsNotifications


	private static void testErcLogsNotifications(BigInteger startBlock) {
		HpbFilter filter = new HpbFilter(new DefaultBlockParameterNumber(startBlock), DefaultBlockParameterName.LATEST,
				contractAddress);
		Event event = new Event("Issue",
				Arrays.<TypeReference<?>>asList(
						new TypeReference<Uint256>(false) {
						},
						new TypeReference<Address>(true) {
						}, new TypeReference<Uint256>(false) {
						},new TypeReference<Uint256>(false) {
						}
				)
		);

		String topicData = EventEncoder.encode(event);
		filter.addSingleTopic(topicData);
		System.out.println("testErcLogsNotifications topicData ==="+topicData);

		// 0x73C2a5B1A32fa8E33101A6ab119203f4417feAE4 为合约地址

        Observable<LogNotification> logNotifications = web3.logsNotifications(Arrays.asList("0x7ba64cf39aafd53823267248b327f1f24dcf0c2d"),
                Arrays.asList(topicData));
		// EventEncoder 这里主要为了生成事件签名

  /*     logNotifications.subscribe(eventLog -> {
            Log log = eventLog.getParams().getResult();
            List<Type> eventsData = FunctionReturnDecoder.decode(log.getData(),logNotifications.get);
            for (Type eventDataItem : eventsData) {
                System.out.println(eventDataItem.getTypeAsString() + ": " + eventDataItem.getValue());
            }
        });*/




	}


    private void test721InnerTopics(BigInteger startBlock) {
//		private static String contractAddress = "0xb7bbc918c85566a3bc37847b3c807bc12eeff8ad";
        HpbFilter filter = new HpbFilter(new DefaultBlockParameterNumber(startBlock), DefaultBlockParameterName.LATEST, "0xba29fa851805767609c25b7a032101cef1c2258b");
        Event event = new Event("Transfer",
                Arrays.<TypeReference<?>>asList(
                        new TypeReference<Address>(true) {
                        },
                        new TypeReference<Address>(true) {
                        }, new TypeReference<Uint256>(false) {
                        }
                )
        );

        String topicData = EventEncoder.encode(event);
        filter.addSingleTopic(topicData);
        System.out.println("testInnerTopics topicData ==="+topicData);
        web3.hpbLogObservable(filter).subscribe(log -> {
            System.out.println("log.getBlockNumber() =="+log.getBlockNumber());
            System.out.println("log.getTransactionHash()=="+log.getTransactionHash());
            List<String> topics = log.getTopics();

            System.out.println("topics.size = [" + topics.size() + "]");

            String fromAddress = topics.get(1);
            String toAddress = topics.get(2);
            String topic3 = topics.get(3);
            String value = log.getData();

            System.out.println("fromAddress = [" + fromAddress + "]");
            System.out.println("toAddress = [" + toAddress + "]");
            System.out.println("topic3 = [" + topic3 + "]");
            System.out.println("value = [" + value + "]");



           String finalFromAddress = "0x" + fromAddress.substring(26);
           String finalToAddress = "0x" + toAddress.substring(26);
           String dataValue = value.substring(2);
//           BigInteger tempValue = new BigInteger(value.substring(2), 16);
            System.out.println("finalFromAddress = [" + finalFromAddress + "]" + " ------  finalToAddress = [" + finalToAddress + "]" + " ----- dataValue = [" + dataValue + "]" );

            /*for (String topic : topics) {
                System.out.println("topic ===="+topic);
            }*/
            HpbTransferEventResponse transferEventResponse = (HpbTransferEventResponse) getTransferEventsByLog(log);
            System.out.println("transferEventResponse = [" + GsonUtil.gsonString(transferEventResponse) + "]");
        });
    }


    public  final Event TRANSFER_EVENT = new Event("Transfer",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}));

/*    public List<TokenERC20.TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TokenERC20.TransferEventResponse> responses = new ArrayList<TokenERC20.TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TokenERC20.TransferEventResponse typedResponse = new TokenERC20.TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }*/


    public HpbTransferEventResponse getTransferEventsByLog(Log log) {
        HpbEventValuesWithLog  eventValues = extractEventParametersWithLog(TRANSFER_EVENT, log);
        HpbTransferEventResponse typedResponse = new HpbTransferEventResponse();
        typedResponse.log = eventValues.getLog();
        typedResponse.from = (Address) eventValues.getIndexedValues().get(0);
        typedResponse.to = (Address) eventValues.getIndexedValues().get(1);
        typedResponse.tokenId = (Uint256) eventValues.getIndexedValues().get(2);
        return typedResponse;
    }

    public   HpbEventValuesWithLog extractEventParametersWithLog(Event event, Log log) {
        EventValues eventValues = staticExtractEventParameters(event, log);
        return eventValues == null ? null :new HpbEventValuesWithLog(eventValues, log);
    }

    public  EventValues staticExtractEventParameters(Event event, Log log) {
        List<String> topics = log.getTopics();
        String encodedEventSignature = EventEncoder.encode(event);
        if (!((String)topics.get(0)).equals(encodedEventSignature)) {
            return null;
        } else {
            List<Type> indexedValues = new ArrayList();
            List<Type> nonIndexedValues = FunctionReturnDecoder.decode(log.getData(), event.getNonIndexedParameters());
            List<TypeReference<Type>> indexedParameters = event.getIndexedParameters();

            for(int i = 0; i < indexedParameters.size(); ++i) {
                Type value = FunctionReturnDecoder.decodeIndexedValue((String)topics.get(i + 1), (TypeReference)indexedParameters.get(i));
                indexedValues.add(value);
            }

            return new EventValues(indexedValues, nonIndexedValues);
        }
    }

    public static void getT(){
        String str = "0xba29fa851805767609c25b7a032101cef1c2258b";
    }




}
