package com.expensetracker.model;

import java.time.LocalDate;

public class Expense {
    private int id;
    private String description;
    private double amount;
    private LocalDate date;
    private Category category;

    public Expense(int id, String description, double amount, LocalDate date, Category category) {
     this.id = id;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.category = category;

    }

    public int getId() { 
        return id; }
    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    public LocalDate getDate() { return date; }
    public Category getCategory() { return category; }

    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        this.description = description;
    }
    public void setAmount(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.amount = amount;
    }
    public void setCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        this.category = category;
    }

    @Override
    public String toString() { return "Expense{id=" + id + ", date=" + date + ", description=" + description + ", amount=" + amount + '}'; }
}
