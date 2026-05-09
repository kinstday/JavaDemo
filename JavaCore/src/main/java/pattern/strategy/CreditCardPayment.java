package pattern.strategy;

/**
 * @author 12
 * Create By 下午2:18
 */
public class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private String cvv;

    public CreditCardPayment(String cardNumber, String cvv) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
    }

    @Override
    public void pay(int amount) {
        System.out.println("Paid $" + amount + " using Credit Card: " +
                                   cardNumber.substring(0, 4) + "****");
    }
}

