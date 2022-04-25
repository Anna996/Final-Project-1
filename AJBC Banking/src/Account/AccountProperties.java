package Account;

public enum AccountProperties {
	BRONZE(4.5f,6f,5f,7.5f,10000,2500),
	SILVER(3f,4.5f,3.8f,5f,20000,4000),
	GOLD(1.5f,3f,1.75f,3.8f,50000,6000),
	TITANIUM(0,0,0,0,0,0);
	
	float minInterestRate, maxInterestRate;
	float minOperationFee, maxOperationFee;
	int maxLoanAmmount;
	int maxWithdrawalAmount;
	
	AccountProperties(float minInterestRate, float maxInterestRate, float minOperationFee, float maxOperationFee,
			int maxLoanAmmount, int maxWithdrawalAmount) {
		this.minInterestRate = minInterestRate;
		this.maxInterestRate = maxInterestRate;
		this.minOperationFee = minOperationFee;
		this.maxOperationFee = maxOperationFee;
		this.maxLoanAmmount = maxLoanAmmount;
		this.maxWithdrawalAmount = maxWithdrawalAmount;
	}	
}
