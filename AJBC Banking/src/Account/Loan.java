package Account;

public class Loan {
	private int amount;
	private int numOfPayments;
	private float currentDebt;
	private float monthlyPayment;
	private final float INTEREST = 0.05f;

	public Loan(int amount, int numOfPayments) {
		setAmount(amount);
		setNumOfPayments(numOfPayments);
		setCurrentDebt(amount * (INTEREST + 1));
		setMonthlyPayment(calcMonthlyPayment());
	}

	private void setAmount(int amount) {
		this.amount = amount;
	}

	private void setNumOfPayments(int numOfPayments) {
		this.numOfPayments = numOfPayments;
	}

	private void setCurrentDebt(float currentDebt) {
		this.currentDebt = currentDebt;
	}

	private void setMonthlyPayment(float monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}

	private float calcMonthlyPayment() {
		return currentDebt / numOfPayments;
	}

	public float getMonthlyPayment() {
		return monthlyPayment;
	}

	public void printSummary() {
		System.out.println(this);
	}

	@Override
	public String toString() {
		return "Loan [amount= " + amount + ", interest= " + INTEREST + String.format(", currentDebt= %.2f", currentDebt)
				+ String.format(", monthlyPayment= %.2f", monthlyPayment) + "]";
	}
}
