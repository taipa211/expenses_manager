package com.expensetracker.model;

public class Budget {
    private int id;
    private double amount;
    private int month; // 0 = anual
    private int year;

    public Budget(int id, double amount, int month, int year) {}
    public Budget(int id, double amount, int year) {}

    public int getId() { return 0; }
    public double getAmount() { return 0; }
    public int getMonth() { return 0; }
    public int getYear() { return 0; }

    public boolean isAnnual() { return false; }
    public boolean isExceeded(double totalExpenses) { return false; }

    @Override
    public String toString() { return null; }
}
