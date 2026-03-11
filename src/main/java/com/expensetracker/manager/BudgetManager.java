package com.expensetracker.manager;

import com.expensetracker.model.Budget;
import java.util.List;
import java.util.ArrayList;

public class BudgetManager {
    private List<Budget> budgets;
    private int nextId;
    private ExpenseManager expenseManager;

    public BudgetManager(ExpenseManager expenseManager) {}

    public Budget addMonthly(double amount, int month, int year) { return null; }
    public Budget addAnnual(double amount, int year) { return null; }
    public boolean delete(int id) { return false; }
    public List<Budget> list() { return null; }
    public void checkWarning(int month, int year) {}
    public void checkAnnualWarning(int year) {}
}
