package org.example.provider;

import org.example.model.Booking;

import java.awt.print.Book;

public class UpiPaymentStrategy implements  IPaymentStrategy {
    @Override
    public boolean processPayment(Booking booking) {
        System.out.println("payment done through upi");
        return true;
    }
}
