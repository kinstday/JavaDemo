package pattern.strategy;

/**
 * 策略模式
 *
 * 算法可以自由切换
 * 避免多重条件判断
 * 扩展性好
 *
 * 策略类数量增多
 * 客户端必须知道所有策略类
 * 增加了系统复杂度
 *
 * @author 12
 * Create By 下午2:20
 */
public class StrategyPatternDemo {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();

        // 使用信用卡支付
        cart.setPaymentStrategy(new CreditCardPayment("1234567890123456", "123"));
        cart.checkout(100);

        // 使用PayPal支付
        cart.setPaymentStrategy(new PayPalPayment("user@example.com"));
        cart.checkout(200);

        // 使用比特币支付
        cart.setPaymentStrategy(new BitcoinPayment("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa"));
        cart.checkout(300);
    }
}
