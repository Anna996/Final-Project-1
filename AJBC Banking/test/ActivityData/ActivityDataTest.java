package ActivityData;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class ActivityDataTest {

	private ActivityData activity;
	private LocalDateTime timeStamp;
	private String info;
	private double balanceChange;

	public ActivityDataTest() {
		timeStamp = LocalDateTime.now();
		info = "info about deposit";
		balanceChange = 20.5;
		activity = new ActivityData(ActivityName.DEPOSIT, timeStamp, info, balanceChange);
	}
	
	@Test
	void checkActivityName() {
		assertEquals(ActivityName.DEPOSIT, activity.getActivityName());
		assertNotEquals(ActivityName.PAY_BILL, activity.getActivityName());
	}

	@Test
	void checkTimeStamp() {
		assertEquals(timeStamp, activity.getTimeStamp());
		assertNotEquals(LocalDateTime.now(), activity.getTimeStamp());
	}
	
	@Test
	void checkBalanceChange() {
		assertEquals(balanceChange, activity.getBalanceChange());
		assertNotEquals(20.4, activity.getBalanceChange());
	}
	
	@Test
	void checkInfo() {
		assertEquals(info, activity.getInfo());
		assertNotEquals(" ", activity.getInfo());
	}
}
