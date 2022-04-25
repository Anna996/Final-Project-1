package Account;

import java.time.LocalDateTime;

enum ActivityName {
	DEPOSIT_CASH, WITHDRAWAL, GET_LOAN, MAKE_PAYMENTTRANSFER, FEE_COLLECTION, PAY_BILL
}

public class ActivityData {
	private ActivityName activityName;
	private LocalDateTime timeStamp;
	private String info;
	private double balanceChange;
	
	public ActivityData(ActivityName activityName, LocalDateTime timeStamp, String info, double balanceChange) {
		setActivityName(activityName);
		setTimeStamp(timeStamp);
		setInfo(info);
		setBalanceChange(balanceChange);
	}

	private void setActivityName(ActivityName activityName) {
		this.activityName = activityName;
	}

	private void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	private void setInfo(String info) {
		this.info = info;
	}

	private void setBalanceChange(double balanceChange) {
		this.balanceChange = balanceChange;
	}

	@Override
	public String toString() {
		return "Activity data: [ activityName=" + activityName + ", timeStamp=" + timeStamp + ", info=" + info
				+ ", balanceChange=" + balanceChange + "]";
	}
}
