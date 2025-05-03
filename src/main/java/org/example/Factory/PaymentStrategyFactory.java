package org.example.Factory;

import org.example.model.Booking;
import org.example.provider.CreditCardPaymentStartegy;
import org.example.provider.IPaymentStrategy;
import org.example.provider.UpiPaymentStrategy;

public class PaymentStrategyFactory {
    public static IPaymentStrategy createPaymentStrategy(String key)
    {
        if(key.equalsIgnoreCase("upi"))
        {
            return new UpiPaymentStrategy();
        }
        else
        {
            return new CreditCardPaymentStartegy();
        }
    }
}
