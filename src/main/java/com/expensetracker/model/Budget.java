package com.expensetracker.model;

public class Budget {
    private int id;
    private double amount;
    private int month; // 0 = anual
    private int year;

    public Budget(int id, double amount, int month, int year) {
        this.id = id;
        this.amount = amount;
        this.month = month;
        this.year = year;


        
    }
    public Budget(int id, double amount, int year) {
        this.id = id;
        this.amount = amount;
        this.month = 0; // 0 indicates annual budget
        this.year = year;
    }

    public int getId() { return id; }
    public double getAmount() { return amount; }
    public int getMonth() { return month; }
    public int getYear() { return year; }

    public boolean isAnnual() { 
        if (month == 0) {
            return true;
        }
        return false; }
    public boolean isExceeded(double totalExpenses) {
        if (totalExpenses > amount) {
            return true;
        }
        return false; }

    @Override
    public String toString() { return "Budget{id=" + id + ", amount=" + amount + ", month=" + month + ", year=" + year + '}'; }
}
