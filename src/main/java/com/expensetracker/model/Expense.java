package com.expensetracker.model;

import java.time.LocalDate;

public class Expense {
    private int id;
    private String description;
    private double amount;
    private LocalDate date;
    private Category category;

    public Expense(int id, String description, double amount, LocalDate date, Category category) {}

    public int getId() { return 0; }
    public String getDescription() { return null; }
    public double getAmount() { return 0; }
    public LocalDate getDate() { return null; }
    public Category getCategory() { return null; }

    public void setDescription(String description) {}
    public void setAmount(double amount) {}
    public void setCategory(Category category) {}

    @Override
    public String toString() { return null; }
}
