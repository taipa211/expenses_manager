package com.expensetracker.util;

import com.expensetracker.model.Expense;
import com.expensetracker.model.Category;
import com.expensetracker.model.Budget;
import java.util.List;

public class JsonStorage {
    private String filePath;

    public JsonStorage(String filePath) {}

    public void saveExpenses(List<Expense> expenses) {}
    public List<Expense> loadExpenses() { return null; }
    public void saveCategories(List<Category> categories) {}
    public List<Category> loadCategories() { return null; }
    public void saveBudgets(List<Budget> budgets) {}
    public List<Budget> loadBudgets() { return null; }
}
