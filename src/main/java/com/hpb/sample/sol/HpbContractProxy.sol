pragma solidity ^0.5.1;
contract AdminInterface {
    function addCandidate(
        address payable _candidateAddr
    ) public returns(bool);

    function deleteCandidate(
        address payable _candidateAddr
    ) public returns(bool);

    function setCapacity(
        uint _capacity
    ) public returns(bool);

	function addCoinBase(
        address payable _coinBase
    )  public returns(bool);
    
    function initHolderAddr(
        address payable _coinBase,
        address payable _holderAddr
    ) public returns(bool);
    
    function calVoteResult() public returns(bool);
}
contract VoteInterface {
    /**
     * 投票  
     */
    function vote(
        address payable voterAddr, 
        address payable candidateAddr, 
        uint num
    ) public returns(bool);

    /**
     * 用于批量投票
     */
    function batchVote(
        address payable voterAddr, 
        address payable[] memory candidateAddrs, 
        uint[] memory nums
    ) public returns(bool);
    
    function updateCoinBase(
        address payable _coinBase,
        address payable _newCoinBase
    ) public returns(bool);
    
    function setHolderAddr(
        address payable _coinBase,
        address payable _holderAddr
    ) public returns(bool);
    
    function updateCandidateAddr(
        address payable _candidateAddr, 
        address payable _newCandidateAddr
    ) public returns(bool);
    /**
     * 撤回对某个候选人的投票
     */
    function cancelVoteForCandidate(
        address payable voterAddr, 
        address payable candidateAddr, 
        uint num
    ) public returns(bool);

    function refreshVoteForAll() public returns(bool);
    
    function refreshVoteForVoter(address payable voterAddr) public returns(bool);

}
contract FetchVoteInterface {
    /**
     * 是否为竞选阶段
     */
	function isRunUpStage() public view returns (bool);
    /**
     * 获取所有候选人的详细信息
     */
    function fetchAllCandidates() public view returns (
        address payable[] memory
    );

    /**
     * 获取所有投票人的详细信息
     */
    function fetchAllVoters() public view returns (
        address payable[] memory, 
        uint[] memory
    );

    /**
     * 获取所有投票人的投票情况
     */
    function fetchVoteInfoForVoter(
        address payable voterAddr
    ) public view returns (
        address payable[] memory, 
        uint[] memory
    );

    /**
     * 获取某个候选人的总得票数
     */
    function fetchVoteNumForCandidate(
        address payable candidateAddr
    ) public view returns (uint);

    /**
     * 获取某个投票人已投票数
     */
    function fetchVoteNumForVoter(
        address payable voterAddr
    ) public view returns (uint);

    /**
     * 获取某个候选人被投票详细情况
     */
    function fetchVoteInfoForCandidate(
        address payable candidateAddr
    ) public view returns (
        address payable[] memory, 
        uint[] memory
    );
	function fetchVoteNumForVoterToCandidate(
        address payable voterAddr,
        address payable candidateAddr
    ) public view returns (uint);
    /**
     * 获取所有候选人的得票情况
     */
    function fetchAllVoteResult() public view returns (
        address payable[] memory,
        uint[] memory
    );
    function getHolderAddr(
        address payable _coinBase
    )  public view returns (
        address payable
    );
    function getAllCoinBases(
    ) public view returns (
        address payable[] memory
    );
}
contract HpbNodesInterface {
    function addStage() public returns(bool);
    function addHpbNodeBatch(
        address payable[] memory coinbases, 
        bytes32[] memory cid1s,
        bytes32[] memory cid2s,
        bytes32[] memory hids
    ) public returns(bool);
}
contract ContractSimpleProxyInterface{
    function addContractMethod(
        address _contractAddr,
        bytes4 _methodId
    ) public returns(bool);
    
    function updateInvokeContract(
        uint invokeIndex,
        uint contractIndex,
        uint methodIdIndex
    )public returns(bool);
    
    function getContractIndexAndMethodIndex(
        address _contractAddr,
        bytes4 _methodId
    )public view returns (uint,uint);
    
    function getInvokeContract(
        uint invokeIndex
    ) public view returns (address,bytes4);
  
}
contract Ownable {
    address payable public owner;
    modifier onlyOwner {
        require(msg.sender == owner);
        // Do not forget the "_;"! It will be replaced by the actual function
        // body when the modifier is used.
        _;
    }

    function transferOwnership(address payable newOwner) onlyOwner public returns(bool) {
        owner = newOwner;
        addAdmin(newOwner);
        deleteAdmin(owner);
        return true;
    }

    function getOwner() public view returns (address payable) {
        return owner;
    }
    // 合约管理员，可以添加和删除候选人
    mapping (address  => address payable) public adminMap;

    modifier onlyAdmin {
        require(adminMap[msg.sender] != address(0));
        _;
    }

    function addAdmin(address payable addr) onlyOwner public returns(bool) {
        require(adminMap[addr] == address(0));
        adminMap[addr] = addr;
        return true;
    }

    function deleteAdmin(address payable addr) onlyOwner public returns(bool) {
        require(adminMap[addr] != address(0));
        adminMap[addr] = address(0);
        delete adminMap[addr];
        return true;
    }
}
library SafeMath {
    /**
     * @dev Multiplies two numbers, throws on overflow.
     */
    function mul(uint256 a, uint256 b) internal pure returns (uint256 c) {
        // Gas optimization: this is cheaper than asserting 'a' not being zero, but the
        // benefit is lost if 'b' is also tested.
        // See: https://github.com/OpenZeppelin/openzeppelin-solidity/pull/522
        if (a == 0) {
            return 0;
        }

        c = a * b;
        assert(c / a == b);
        return c;
    }

    /**
     * @dev Integer division of two numbers, truncating the quotient.
     */
    function div(
        uint256 a, 
        uint256 b
    ) internal pure returns (
        uint256
    ) {
        // assert(b > 0); // Solidity automatically throws when dividing by 0
        // uint256 c = a / b;
        // assert(a == b * c + a % b); // There is no case in which this doesn't hold
        return a / b;
    }

    /**
     * @dev Subtracts two numbers, throws on overflow (i.e. if subtrahend is greater than minuend).
     */
    function sub(
        uint256 a, 
        uint256 b
    ) internal pure returns (
        uint256
    ) {
        assert(b <= a);
        return a - b;
    }

    /**
     * @dev Adds two numbers, throws on overflow.
     */
    function add(
        uint256 a, 
        uint256 b
    ) internal pure returns (
        uint256 c
    ) {
        c = a + b;
        assert(c >= a);
        return c;
    }
}
contract NodeBallotProx{
    using SafeMath for uint256;
    
    address payable public hpbNodesAddress;
    address payable public contractSimpleProxyAddress;
    
    address payable[] public nodeBallotAddrs;
    mapping (address => uint) public nodeBallotIndex;
    address payable public nodeBallotAddrInService;

    function _getAdminInterface(
        uint _index
    ) internal view returns  (
        AdminInterface
    ) {
        require(_index != 0);
        return AdminInterface(nodeBallotAddrs[_index]);
    }

    function _getVoteInterface(
        uint _index
    ) internal view returns (
        VoteInterface
    ) {
        require(_index != 0);
        return VoteInterface(nodeBallotAddrs[_index]);
    }

    function _getFetchVoteInterface(
        uint _index
    ) internal view returns  (
        FetchVoteInterface
    ) {
        require(_index != 0);
        return FetchVoteInterface(nodeBallotAddrs[_index]);
    }

    function _getHpbNodesInterface() internal view returns  (
        HpbNodesInterface
    ) {
        require(hpbNodesAddress != address(0));
        return HpbNodesInterface(hpbNodesAddress);
    }

    function _getContractSimpleProxyInterface() internal view returns (
        ContractSimpleProxyInterface
    ) {
        require(contractSimpleProxyAddress != address(0));
        return ContractSimpleProxyInterface(contractSimpleProxyAddress);
    }
    function addCandidate(
        address payable _candidateAddr
    ) public returns(bool) {
        return addCandidateByIndex(nodeBallotAddrs.length - 1, _candidateAddr);
    }

    function getLastestBallotAddrAndIndex() public view returns(
        address payable,
        uint
    ) {
        return (
            nodeBallotAddrs[nodeBallotAddrs.length - 1],
            nodeBallotAddrs.length - 1
        );
    }
    
    function addCandidateByIndex(
        uint index, 
        address payable _candidateAddr
    ) public returns(bool) {
        return _getAdminInterface(index).addCandidate(_candidateAddr);
    }

    function batchAddCandidate(
        address payable[] memory _candidateAddrs
    ) public returns(bool) {
        return batchAddCandidateByIndex(nodeBallotAddrs.length - 1, _candidateAddrs);
    }

    function batchAddCandidateByIndex(
        uint index, 
        address payable[] memory _candidateAddrs
    ) public returns(bool) {
        for (uint i=0;i < _candidateAddrs.length;i++) {
            require(addCandidateByIndex(index, _candidateAddrs[i]));
        }
        return true;
    }

    /**
     * 根据阶段删除候选者
     * @param _candidateAddr 候选者账户地址 Candidate account address
     */
    function deleteCandidate(
        address payable _candidateAddr
    ) public returns(bool) {
        return deleteCandidateByIndex(nodeBallotAddrs.length - 1, _candidateAddr);
    }

    function deleteCandidateByIndex(
        uint index, 
        address payable _candidateAddr
    ) public returns(bool) {
        return _getAdminInterface(index).deleteCandidate(_candidateAddr);
    }
    
    function batchDeleteCandidate(
        address payable[] memory _candidateAddrs
    ) public returns(bool) {
        return batchDeleteCandidateByIndex(nodeBallotAddrs.length - 1, _candidateAddrs);
    }

    function batchDeleteCandidateByIndex(
        uint index, 
        address payable[] memory _candidateAddrs
    ) public returns(bool) {
        for (uint i=0;i < _candidateAddrs.length;i++) {
            require(deleteCandidateByIndex(index, _candidateAddrs[i]));
        }
        return true;
    }
    
    function updateCandidateAddr(
        address payable _candidateAddr, 
        address payable _newCandidateAddr
    ) public returns(bool) {
        return updateCandidateAddrByIndex(nodeBallotAddrs.length - 1, _candidateAddr, _newCandidateAddr);
    }

    function updateCandidateAddrByIndex(
        uint index, 
        address payable _candidateAddr, 
        address payable _newCandidateAddr
    ) public returns(bool) {
        return _getVoteInterface(index).updateCandidateAddr(_candidateAddr, _newCandidateAddr);
    }
    
    function batchUpdateCandidateAddr(
        address payable[] memory _candidateAddrs,
        address payable[] memory _newCandidateAddrs
    ) public returns(bool) {
        return batchUpdateCandidateAddrByIndex(nodeBallotAddrs.length - 1, _candidateAddrs,_newCandidateAddrs);
    }

    function batchUpdateCandidateAddrByIndex(
        uint index, 
        address payable[] memory _candidateAddrs,
        address payable[] memory _newCandidateAddrs
    ) public returns(bool) {
        for (uint i=0;i < _candidateAddrs.length;i++) {
            require(updateCandidateAddrByIndex(index, _candidateAddrs[i],_newCandidateAddrs[i]));
        }
        return true;
    }

    function setHolderAddr(
        address payable _coinBase,
        address payable _holderAddr
    ) public returns(bool) {
        return setHolderAddrByIndex(nodeBallotAddrs.length - 1, _coinBase,_holderAddr);
    }
    function setHolderAddrByIndex(
        uint index, 
        address payable _coinBase,
        address payable _holderAddr
    ) public returns(bool) {
        return _getVoteInterface(index).setHolderAddr(_coinBase,_holderAddr);
    }
    
    function batchSetHolderAddr(
        address payable[] memory _coinBases,
        address payable[] memory _holderAddrs
    ) public returns(bool) {
        return batchSetHolderAddrByIndex(nodeBallotAddrs.length - 1, _coinBases,_holderAddrs);
    }

    function batchSetHolderAddrByIndex(
        uint index, 
        address payable[] memory _coinBases,
        address payable[] memory _holderAddrs
    ) public returns(bool) {
        for (uint i=0;i < _coinBases.length;i++) {
            require(setHolderAddrByIndex(index, _coinBases[i],_holderAddrs[i]));
        }
        return true;
    }
    function initHolderAddr(
        address payable _coinBase,
        address payable _holderAddr
    ) public returns(bool) {
        return initHolderAddrByIndex(nodeBallotAddrs.length - 1, _coinBase,_holderAddr);
    }
    function initHolderAddrByIndex(
        uint index, 
        address payable _coinBase,
        address payable _holderAddr
    ) public returns(bool) {
        return _getAdminInterface(index).initHolderAddr(_coinBase,_holderAddr);
    }
    
    function batchInitHolderAddr(
        address payable[] memory _coinBases,
        address payable[] memory _holderAddrs
    ) public returns(bool) {
        return batchInitHolderAddrByIndex(nodeBallotAddrs.length - 1, _coinBases,_holderAddrs);
    }

    function batchInitHolderAddrByIndex(
        uint index, 
        address payable[] memory _coinBases,
        address payable[] memory _holderAddrs
    ) public returns(bool) {
        for (uint i=0;i < _coinBases.length;i++) {
            require(initHolderAddrByIndex(index, _coinBases[i],_holderAddrs[i]));
        }
        return true;
    }
 
    function addCoinBase(
        address payable _coinBase
    ) public returns(bool) {
        return addCoinBaseByIndex(nodeBallotAddrs.length - 1, _coinBase);
    }
    function addCoinBaseByIndex(
        uint index, 
        address payable _coinBase
    ) public returns(bool) {
        return _getAdminInterface(index).addCoinBase(_coinBase);
    }
    
    function batchAddCoinBase(
        address payable[] memory _coinBases
    ) public returns(bool) {
        return batchAddCoinBaseByIndex(nodeBallotAddrs.length - 1, _coinBases);
    }

    function batchAddCoinBaseByIndex(
        uint index, 
        address payable[] memory _coinBases
    ) public returns(bool) {
        for (uint i=0;i < _coinBases.length;i++) {
            require(addCoinBaseByIndex(index,_coinBases[i]));
        }
        return true;
    }
    function updateCoinBase(
        address payable _coinBase,
        address payable _newCoinBase
    ) public returns(bool) {
        return updateCoinBaseByIndex(nodeBallotAddrs.length - 1, _coinBase, _newCoinBase);
    }

    function updateCoinBaseByIndex(
        uint index, 
        address payable _coinBase, 
        address payable _newCoinBase
    ) public returns(bool) {
        return _getVoteInterface(index).updateCoinBase(_coinBase, _newCoinBase);
    }

    function batchUpdateCoinBase(
        address payable[] memory _coinBases, 
        address payable[] memory _newCoinBases
    ) public returns(bool) {
        return batchUpdateCoinBaseByIndex(nodeBallotAddrs.length - 1, _coinBases, _newCoinBases);
    }

    function batchUpdateCoinBaseByIndex(
        uint index, 
        address payable[] memory _coinBases, 
        address payable[] memory _newCoinBases
    ) public returns(bool) {
        for (uint i=0;i < _coinBases.length;i++) {
            require(updateCoinBaseByIndex(index, _coinBases[i], _newCoinBases[i]));
        }
        return true;
    }

    function calVoteResult() public returns(bool) {
        return calVoteResultByIndex(nodeBallotAddrs.length - 1);
    }

    function calVoteResultByIndex(
        uint index
    ) public returns(bool) {
        return _getAdminInterface(index).calVoteResult();
    }

    /**
     * 投票  
     */
    function  vote(
        address payable candidateAddr, 
        uint num
    ) public returns(bool) {
        return voteByIndex(nodeBallotAddrs.length - 1, candidateAddr, num) ;
    }

    function  voteByIndex(
        uint index, 
        address payable candidateAddr, 
        uint num
    ) public returns(bool) {
        return _getVoteInterface(index).vote(msg.sender, candidateAddr, num);
    }

    /**
     * 用于批量投票  For non locked voting
     */
    function  batchVote(
        address payable[] memory candidateAddrs, 
        uint[] memory nums
    ) public returns(bool) {
        return batchVoteByIndex(nodeBallotAddrs.length - 1, candidateAddrs, nums);
    }

    function  batchVoteByIndex(
        uint index, 
        address payable[] memory candidateAddrs, 
        uint[] memory nums
    ) public returns(bool) {
        return _getVoteInterface(index).batchVote(msg.sender, candidateAddrs, nums);
    }

    function refreshVoteForAll() public returns(bool) {
        return refreshVoteForAllByIndex(nodeBallotAddrs.length - 1);
    }

    function refreshVoteForAllByIndex(
        uint index
    ) public returns(bool) {
        return _getVoteInterface(index).refreshVoteForAll();
    }
    
    function batchRefreshVoteForVoter(
        address payable[] memory voterAddrs
    ) public returns(bool){
        for (uint i=0;i < voterAddrs.length;i++) {
            refreshVoteForVoter(voterAddrs[i]);
        }
        return true;
    }
    function batchRefreshVoteForVoterByIndex(
        uint index,
        address payable[] memory voterAddrs
    ) public returns(bool){
        for (uint i=0;i < voterAddrs.length;i++) {
            refreshVoteForVoterByIndex(index,voterAddrs[i]);
        }
        return true;
    }
    function refreshVoteForVoter(
        address payable voterAddr
    ) public returns(bool){
        return refreshVoteForVoterByIndex(nodeBallotAddrs.length - 1,voterAddr);
    }

    function refreshVoteForVoterByIndex(
        uint index,
        address payable voterAddr
    ) public returns(bool) {
        return _getVoteInterface(index).refreshVoteForVoter(voterAddr);
    }
  

    /**
     * 撤回对某个候选人的投票 Withdraw a vote on a candidate.
     */
    function cancelVoteForCandidate(
        address payable candidateAddr, 
        uint num
    ) public returns(bool) {
        return cancelVoteForCandidateByIndex(nodeBallotAddrs.length - 1, candidateAddr, num);
    }

    function cancelVoteForCandidateByIndex(
        uint index, 
        address payable candidateAddr, 
        uint num
    ) public returns(bool) {
        return _getVoteInterface(index).cancelVoteForCandidate(msg.sender, candidateAddr, num);
    }

    function  batchCancelVoteForCandidate(
        address payable[] memory candidateAddrs, 
        uint[] memory nums
    ) public returns(bool) {
        return batchCancelVoteForCandidateByIndex(nodeBallotAddrs.length - 1, candidateAddrs, nums);
    }

    function  batchCancelVoteForCandidateByIndex(
        uint index, 
        address payable[] memory candidateAddrs, 
        uint[] memory nums
    ) public returns(bool) {
        for (uint i=0;i < candidateAddrs.length;i++) {
            require(_getVoteInterface(index).cancelVoteForCandidate(msg.sender, candidateAddrs[i], nums[i]));
        }
        return true;
    }

    /**
     * 获取所有候选人的详细信息
     */
    function fetchAllCandidates() public view returns (
        address payable[] memory
    ) {
        return fetchAllCandidatesByIndex(nodeBallotAddrs.length - 1);
    }

    function fetchAllCandidatesByIndex(
        uint index
    ) public view returns (
        address payable[] memory
    ) {
        return _getFetchVoteInterface(index).fetchAllCandidates();
    }

	function fetchAllVoterWithBalance() public view returns (
        address payable[] memory, 
        uint[] memory,
        uint[] memory
    ) {
        address payable[] memory _addrs;
        uint[] memory _voteNumbers;
        (_addrs,_voteNumbers)=fetchAllVoters();
        uint cl=_addrs.length ;
        uint[] memory _voteBalances=new uint[](cl);
        for (uint i=0;i <cl;i++) {
            _voteBalances[i] = _addrs[i].balance;
        }
        return (_addrs, _voteNumbers,_voteBalances);
    }
	function getToRefreshResult() public view returns (
	    address payable[] memory
	) {
        address payable[] memory _addrs;
        uint[] memory _voteNumbers;
        (_addrs,_voteNumbers)=fetchAllVoters();
        uint j=0;
        for (uint i=0;i <_addrs.length;i++) {
            if(_voteNumbers[i]>_addrs[i].balance){
                j++;
            }
        }
        address payable[] memory addrs=new address payable[](j);
        uint n=0;
        for (uint k=0;k <_addrs.length;k++) {
            if(_voteNumbers[k]>_addrs[k].balance){
                addrs[n]=_addrs[k];
                n++;
            }
        }
        return addrs;
    }
	function getToRefreshResultByIndex(
	    uint index
	) public view returns (
	    address payable[] memory
	) {
        address payable[] memory _addrs;
        uint[] memory _voteNumbers;
        (_addrs,_voteNumbers)=fetchAllVotersByIndex(index);
        uint j=0;
        for (uint i=0;i <_addrs.length;i++) {
            if(_voteNumbers[i]>_addrs[i].balance){
                j++;
            }
        }
        address payable[] memory addrs=new address payable[](j);
        uint n=0;
        for (uint k=0;k <_addrs.length;k++) {
            if(_voteNumbers[k]>_addrs[k].balance){
                addrs[n]=_addrs[k];
                n++;
            }
        }
        return addrs;
    }
    
    /**
     * 获取所有投票人的详细信息
     */
    function fetchAllVoters() public view returns (
        address payable[] memory, 
        uint[] memory
    ) {
        return fetchAllVotersByIndex(nodeBallotAddrs.length - 1);
    }

    function fetchAllVotersByIndex(
        uint index
    ) public view returns (
        address payable[] memory, 
        uint[] memory
    ) {
        return _getFetchVoteInterface(index).fetchAllVoters();
    }

    /**
     * 获取所有投票人的投票情况
     */
    function fetchVoteInfoForVoter(
        address payable voterAddr
    ) public view returns (
        address payable[] memory, 
        uint[] memory
    ) {
        return fetchVoteInfoForVoterByIndex(nodeBallotAddrs.length - 1, voterAddr);
    }

    function fetchVoteInfoForVoterByIndex(
        uint index, 
        address payable voterAddr
    ) public view returns (
        address payable[] memory, 
        uint[] memory
    ) {
        return _getFetchVoteInterface(index).fetchVoteInfoForVoter(voterAddr);
    }

    /**
     * 获取某个候选人的总得票数
     * Total number of votes obtained from candidates
     */
    function fetchVoteNumForCandidate(
        address payable candidateAddr
    ) public view returns (uint) {
        return fetchVoteNumForCandidateByIndex(nodeBallotAddrs.length - 1, candidateAddr);
    }

    function fetchVoteNumForCandidateByIndex(
        uint index, 
        address payable candidateAddr
    ) public view returns (uint) {
        return _getFetchVoteInterface(index).fetchVoteNumForCandidate(candidateAddr);
    }

    /**
     * 获取某个投票人已投票数
     * Total number of votes obtained from voterAddr
     */
    function fetchVoteNumForVoter(
        address payable voterAddr
    ) public view returns (uint) {
        return fetchVoteNumForVoterByIndex(nodeBallotAddrs.length - 1, voterAddr);
    }
    function fetchVoteNumForVoterWithBalance(
        address payable voterAddr
    ) public view returns (uint,uint) {
        return (fetchVoteNumForVoter(voterAddr),voterAddr.balance);
    }
    function fetchVoteNumForVoterWithBalance(
        address payable[] memory voterAddrs
    ) public view returns (
        uint[] memory,
        uint[] memory
    ) {
        uint cl=voterAddrs.length;
        uint[] memory _voteNumbers=new uint[](cl);
        uint[] memory _voteBalances=new uint[](cl);
        for(uint i=0;i<cl;i++){
            _voteNumbers[i]=fetchVoteNumForVoter(voterAddrs[i]);
            _voteBalances[i]=voterAddrs[i].balance;
        }
        return (_voteNumbers,_voteBalances);
    }

    function fetchVoteNumForVoterByIndex(
        uint index, 
        address payable voterAddr
    ) public view returns (uint) {
        return _getFetchVoteInterface(index).fetchVoteNumForVoter(voterAddr);
    }

    /**
     * 获取某个候选人被投票详细情况
     */
    function fetchVoteInfoForCandidate(
        address payable candidateAddr
    ) public view returns (
        address payable[] memory, 
        uint[] memory
    ) {
        return fetchVoteInfoForCandidateByIndex(nodeBallotAddrs.length - 1, candidateAddr);
    }

    function fetchVoteInfoForCandidateByIndex(
        uint index, 
        address payable candidateAddr
    ) public view returns (
        address payable[] memory, 
        uint[] memory
    ) {
        return _getFetchVoteInterface(index).fetchVoteInfoForCandidate(candidateAddr);
    }
	function fetchVoteNumForVoterToCandidate(
        address payable voterAddr,
        address payable candidateAddr
    ) public view returns (uint){
        return fetchVoteNumForVoterToCandidateByIndex(nodeBallotAddrs.length - 1,voterAddr,candidateAddr);
    }
	function fetchVoteNumForVoterToCandidateByIndex(
	    uint index,
        address payable voterAddr,
        address payable candidateAddr
    ) public view returns (uint){
        return _getFetchVoteInterface(index).fetchVoteNumForVoterToCandidate(voterAddr,candidateAddr);
    }

    function getHolderAddr(
        address payable _coinBase
    )  public view returns (
        address payable
    ){
        return getHolderAddrByIndex(nodeBallotAddrs.length - 1,_coinBase);
    }
    function getHolderAddrByIndex(
        uint index,
        address payable _coinBase
    )  public view returns (
        address payable
    ){
        return _getFetchVoteInterface(index).getHolderAddr(_coinBase);
    }
    function fetchAllHolderAddrsByIndex(
        uint index
    )  public view returns (
        address payable[] memory,
        address payable[] memory
    ){
        address payable[] memory coinbases=_getFetchVoteInterface(index).getAllCoinBases();
        address payable[] memory holderAddrs=new address payable[](coinbases.length);
        for (uint i =0;i <coinbases.length;i++) {
            holderAddrs[i] =getHolderAddrByIndex(index,coinbases[i]);
        }
        return (coinbases,holderAddrs);
    }
    function fetchAllHolderAddrs(
    )  public view returns (
        address payable[] memory,
        address payable[] memory
    ){
        return fetchAllHolderAddrsByIndex(nodeBallotAddrs.length - 1);
    }


    function fetchAllVoteResult() public view returns (
        address payable[] memory, 
        uint[] memory
    ) {
        return fetchAllVoteResultByIndex(nodeBallotAddrs.length - 1);
    }
    
    function fetchAllVoteResultByIndex(
        uint index
    ) public view returns (
        address payable [] memory, 
        uint[] memory
    ) {
        return _getFetchVoteInterface(index).fetchAllVoteResult();
    }
  
    function fetchResultForNodes() public view returns (
        address payable[] memory, 
        uint[] memory
    ) {
        if (FetchVoteInterface(nodeBallotAddrInService).isRunUpStage()) {
            return (new address payable[](0),new uint[](0));
        }
        address payable[] memory _candidateAddrs;
        uint[] memory _nums;
        (_candidateAddrs,_nums) = FetchVoteInterface(nodeBallotAddrInService).fetchAllVoteResult();
        if (_nums.length < 1) {
            return (new address payable[](0),new uint[](0));
        } else {
            return (_candidateAddrs,_nums);
        }
    }

	function fetchAllHolderAddrsForNodes(
    )  public view returns (
        address payable[] memory,
        address payable[] memory
    ){
        if (FetchVoteInterface(nodeBallotAddrInService).isRunUpStage()) {
            return (new address payable[](0),new address payable[](0));
        }
        address payable[] memory _coinbaseAddrs;
        address payable[] memory _holderAddrs;
        uint ballotIndex=nodeBallotIndex[nodeBallotAddrInService];
        (_coinbaseAddrs,_holderAddrs)=fetchAllHolderAddrsByIndex(ballotIndex);
        if (_coinbaseAddrs.length < 1) {
            return (new address payable[](0),new address payable[](0));
        } else {
            return (_coinbaseAddrs,_holderAddrs);
        }
    }
    function addHpbNodeBatch(
        address payable[] memory coinbases, 
        bytes32[] memory cid1s,
        bytes32[] memory cid2s,
        bytes32[] memory hids
    ) public returns(bool) {
        return _getHpbNodesInterface().addHpbNodeBatch(coinbases, cid1s, cid2s, hids);
    }


    function addContractMethod(
        address _contractAddr,
        bytes4 _methodId
    ) public returns(bool){
        return _getContractSimpleProxyInterface().addContractMethod(_contractAddr,_methodId);
    }
    function updateInvokeContract(
        uint invokeIndex,
        uint contractIndex,
        uint methodIdIndex
    )public returns(bool){
        return _getContractSimpleProxyInterface().updateInvokeContract(invokeIndex,contractIndex,methodIdIndex);
    }
  
    function getContractIndexAndMethodIndex(
        address _contractAddr,
        bytes4 _methodId
    )public view returns (uint,uint){
       return _getContractSimpleProxyInterface().getContractIndexAndMethodIndex(_contractAddr,_methodId);
    }
    function getInvokeContract(
        uint invokeIndex
    ) public view returns (address,bytes4){
       return _getContractSimpleProxyInterface().getInvokeContract(invokeIndex);   
    }
}
contract HpbContractProxy is Ownable ,NodeBallotProx{

    struct HpbNodeCache {
        address payable coinbase;
        bytes32 cid1;
        bytes32 cid2;
        bytes32 hid;
    }

    HpbNodeCache[] hpbNodeCacheArray;
    mapping (address => uint) hpbNodeCacheIndexMap;
    event ReceivedHpb(
        address payable indexed sender, 
        uint amount
    );
    
    // 接受HPB转账
    function () payable external {
        emit ReceivedHpb(
            msg.sender, 
            msg.value
        );
    }
    // 销毁合约，并把合约余额返回给合约拥有者
    function kill() onlyOwner payable public returns(bool) {
        selfdestruct(owner);
        return true;
    }

    function withdraw(
        uint _value
    ) onlyOwner payable public returns(bool) {
        require(address(this).balance >= _value);
        owner.transfer(_value);
        return true;
    }

    constructor () payable public {
        owner = msg.sender;
        adminMap[msg.sender] = msg.sender;
        nodeBallotAddrs.push(address(0));
        hpbNodeCacheArray.push(HpbNodeCache(msg.sender, 0, 0, 0));
    }
    
    function setHpbNodesAddress(
        address payable _hpbNodesAddress
    ) onlyAdmin public returns(bool) {
        hpbNodesAddress = _hpbNodesAddress;
        return true;
    }
    function setContractSimpleProxyAddress(
        address payable _contractSimpleProxyAddress
    ) onlyAdmin public returns(bool) {
        contractSimpleProxyAddress = _contractSimpleProxyAddress;
        return true;
    }

    function addNodeBallotAddress(
        address payable _nodeBallotAddress
    ) onlyAdmin public returns(bool) {
        require(nodeBallotIndex[_nodeBallotAddress] == 0);
        nodeBallotIndex[_nodeBallotAddress]=nodeBallotAddrs.push(_nodeBallotAddress) - 1;
        return true;
    }
    
	function updateNodeBallotAddress(
        address payable _nodeBallotAddress,
        address payable _newNodeBallotAddress
    ) onlyAdmin public returns(bool) {
        uint index=nodeBallotIndex[_nodeBallotAddress];
        require(index != 0);
        nodeBallotAddrs[index] = _newNodeBallotAddress;
        nodeBallotIndex[_nodeBallotAddress] = 0;
        nodeBallotIndex[_newNodeBallotAddress] = index;
        return true;
    }
    
    function addNodesCache(
        address payable[] memory coinbases, 
        bytes32[] memory cid1s,
        bytes32[] memory cid2s,
        bytes32[] memory hids
    ) onlyAdmin public returns(bool) {
        require(coinbases.length == cid1s.length);
        require(coinbases.length == cid2s.length);
        require(coinbases.length == hids.length);
        for (uint i = 0;i < coinbases.length;i++) {
            uint index = hpbNodeCacheIndexMap[coinbases[i]];
            // 必须地址还未使用
            require(index == 0);
            hpbNodeCacheIndexMap[coinbases[i]] = hpbNodeCacheArray.push(
                HpbNodeCache(coinbases[i],cid1s[i],cid2s[i],hids[i])
            )-1;
        }
        return true;
    }

    function deleteHpbNodeCache(
        address payable coinbase
    ) onlyAdmin public returns(bool) {
        uint index = hpbNodeCacheIndexMap[coinbase];
        // 必须地址存在
        require(index != 0);
        delete hpbNodeCacheArray[index];
        for (uint i = index;i < hpbNodeCacheArray.length - 1;i++) {
            hpbNodeCacheArray[i] = hpbNodeCacheArray[i+1];
            hpbNodeCacheIndexMap[hpbNodeCacheArray[i].coinbase] = i;
        }
        delete hpbNodeCacheArray[hpbNodeCacheArray.length - 1];
        hpbNodeCacheArray.length--;
        hpbNodeCacheIndexMap[coinbase] = 0;
        delete hpbNodeCacheIndexMap[coinbase];
        return true;
    }

    function clearHpbNodeCache(address payable[] memory _coinbases) onlyAdmin public returns(bool) {
        require(hpbNodeCacheArray.length > 1);
        for (uint j = 0;j < _coinbases.length;j++) {
            require(deleteHpbNodeCache(_coinbases[j]));
        }
        return true;
    }

    function getAllHpbNodesCache() public view returns (
        address payable[] memory, 
        bytes32[] memory,
        bytes32[] memory,
        bytes32[] memory
    ) {
        require(hpbNodeCacheArray.length > 1);
        uint cl=hpbNodeCacheArray.length - 1;
        address payable[] memory _coinbases=new address payable[](cl);
        bytes32[] memory _cid1s=new bytes32[](cl);
        bytes32[] memory _cid2s=new bytes32[](cl);
        bytes32[] memory _hids=new bytes32[](cl);
        for (uint i = 1;i < hpbNodeCacheArray.length;i++) {
            _coinbases[i-1] = hpbNodeCacheArray[i].coinbase;
            _cid1s[i-1] = hpbNodeCacheArray[i].cid1;
            _cid2s[i-1] = hpbNodeCacheArray[i].cid2;
            _hids[i-1] = hpbNodeCacheArray[i].hid;
        }
        return (_coinbases, _cid1s,_cid2s,_hids);
    }
    
    function switchNodes(
        bytes4 _methodId2,
        bytes4 _methodId3
    ) onlyAdmin public returns(bool) {
        require(hpbNodeCacheArray.length > 1);
        uint cl=hpbNodeCacheArray.length - 1;
        address payable[] memory _coinbases=new address payable[](cl);
        bytes32[] memory _cid1s=new bytes32[](cl);
        bytes32[] memory _cid2s=new bytes32[](cl);
        bytes32[] memory _hids=new bytes32[](cl);
        for (uint i = 1;i < hpbNodeCacheArray.length;i++) {
            _coinbases[i-1] = hpbNodeCacheArray[i].coinbase;
            _cid1s[i-1] = hpbNodeCacheArray[i].cid1;
            _cid2s[i-1] = hpbNodeCacheArray[i].cid2;
            _hids[i-1] = hpbNodeCacheArray[i].hid;
        }
        require(_getHpbNodesInterface().addStage());
        require(_getHpbNodesInterface().addHpbNodeBatch(_coinbases, _cid1s, _cid2s, _hids));
        nodeBallotAddrInService = nodeBallotAddrs[nodeBallotAddrs.length-1];
        
        address _contractAddr=address(this);
        if(_methodId2==bytes4(0)){
        	_methodId2=bytes4(keccak256("fetchResultForNodes()")); 
        }
        address _oldContractAddr;
        bytes4 _oldMethodId2;
        (_oldContractAddr,_oldMethodId2)=getInvokeContract(2);
        if(_oldContractAddr!=_contractAddr||_oldMethodId2!=_methodId2){
	        require(addContractMethod(_contractAddr,_methodId2));
	        uint contractIndex2;
	        uint methodIdIndex2;
	        (contractIndex2,methodIdIndex2)=getContractIndexAndMethodIndex(_contractAddr,_methodId2);
	        require(updateInvokeContract(2,contractIndex2,methodIdIndex2));
        }
        if(_methodId3==bytes4(0)){
            _methodId3=bytes4(keccak256("fetchAllHolderAddrsForNodes()")); 
        }
        bytes4 _oldMethodId3;
        (_oldContractAddr,_oldMethodId3)=getInvokeContract(3);
        if(_oldContractAddr!=_contractAddr||_oldMethodId3!=_methodId3){
	        require(addContractMethod(_contractAddr,_methodId3));
	        uint contractIndex3;
	        uint methodIdIndex3;
	        (contractIndex3,methodIdIndex3)=getContractIndexAndMethodIndex(_contractAddr,_methodId3);
	        require(updateInvokeContract(3,contractIndex3,methodIdIndex3));
        }
    }
}