package pattern.strategy;

/**
 * @author 12
 * Create By 下午2:19
 */
public class BitcoinPayment implements PaymentStrategy {
    private String walletAddress;

    public BitcoinPayment(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    @Override
    public void pay(int amount) {
        System.out.println("Paid $" + amount + " using Bitcoin: " +
                                   walletAddress.substring(0, 8) + "...");
    }
}
