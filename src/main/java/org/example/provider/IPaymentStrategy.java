package org.example.provider;

import org.example.model.Booking;

public interface IPaymentStrategy {
    public boolean processPayment(Booking booking);
}
