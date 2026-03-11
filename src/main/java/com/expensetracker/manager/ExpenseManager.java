package com.expensetracker.manager;

import com.expensetracker.model.Expense;
import com.expensetracker.model.Category;
import java.util.List;
import java.util.ArrayList;

public class ExpenseManager {
    private List<Expense> expenses;
    private int nextId;

    public ExpenseManager() {}

    public Expense add(String description, double amount, Category category) { return null; }
    public boolean delete(int id) { return false; }
    public boolean update(int id, String description, double amount, Category category) { return false; }
    public List<Expense> list() { return null; }
    public double summary() { return 0; }
    public double summaryByMonth(int month, int year) { return 0; }
    public void exportToCSV(String filePath) {}

    private Expense findById(int id) { return null; }
}
