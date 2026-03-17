package com.expensetracker.manager;

import com.expensetracker.model.Budget;
import java.util.ArrayList;
import java.util.List;

public class BudgetManager {

    private List<Budget> budgets;
    private int nextId;
    private ExpenseManager expenseManager;

    public BudgetManager(ExpenseManager expenseManager) {
        this.budgets = new ArrayList<>();
        this.nextId = 1;
        this.expenseManager = expenseManager;
    }

    public void load(List<Budget> savedBudgets) {
        if (savedBudgets == null || savedBudgets.isEmpty()) {
            return;
        }
        this.budgets = new ArrayList<>(savedBudgets);
        this.nextId = budgets.stream().mapToInt(Budget::getId).max().orElse(0) + 1;
    }

    public Budget addMonthly(double amount, int month, int year) {
        if (month < 1 || month > 12 || year < 1) {
            throw new IllegalArgumentException("Invalid month or year");
        }

        Budget budget = new Budget(month, amount, year, nextId++);
        budgets.add(budget);

        return budget;
    }

    public Budget addAnnual(double amount, int year) {
        if (year < 1) {
            throw new IllegalArgumentException("Invalid year");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        Budget budget = new Budget(0, amount, year, nextId++);
        budgets.add(budget);

        return budget;

    }

    public boolean delete(int id) {
        if (budgets.removeIf(x -> x.getId() == id)) {
            return true;
        } else {
            System.out.println("Budget not found, please try again.");
            return false;
        }
    }

    public List<Budget> list() {
        return new ArrayList<>(budgets);

    }

    public void checkWarning(int month, int year) {
        if (month < 1 || month > 12 || year < 1) {
            throw new IllegalArgumentException("Invalid month or year");

        }
        budgets.stream()
                .filter(b -> (b.getMonth() == month && b.getYear() == year) || (b.isAnnual() && b.getYear() == year))
                .forEach(b -> {
                    double totalExpenses = expenseManager.summaryByMonth(month, year);
                    if (b.isExceeded(totalExpenses)) {
                        System.out.println("Warning: Budget exceeded for " + (b.isAnnual() ? "year " : "month ") + b.getYear());
                    }
                });
    }

}
