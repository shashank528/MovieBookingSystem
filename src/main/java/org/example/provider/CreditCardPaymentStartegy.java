package org.example.provider;

import org.example.model.Booking;

public class CreditCardPaymentStartegy implements  IPaymentStrategy{
    @Override
    public boolean processPayment(Booking booking) {
        return false;
    }
}
