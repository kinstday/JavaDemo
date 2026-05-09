package pattern.strategy;

/**
 * @author 12
 * Create By 下午2:18
 */
public class PayPalPayment implements PaymentStrategy {
    private String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    public void pay(int amount) {
        System.out.println("Paid $" + amount + " using PayPal: " + email);
    }
}
