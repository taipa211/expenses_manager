package com.expensetracker.manager;

import com.expensetracker.model.Category;
import com.expensetracker.model.Expense;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpenseManager {
    private List<Expense> expenses;
    private int nextId;

    public ExpenseManager() {
        this.expenses = new ArrayList<>();
        this.nextId = 1;
    
    }

    public Expense add(String description, double amount, Category category ) {
if (description.isEmpty() || description.isBlank() || amount <= 0 || category == null) {
    System.out.println("Invalid expense data, please try again.");
    return null;
}
LocalDate date = LocalDate.now();
 Expense expense = new Expense (nextId++, description, amount, date, category);
 expenses.add(expense);

 return expense;



     }
    public boolean delete(int id) { 
        return expenses.removeIf(x -> x.getId() == id); }
    public boolean update(int id, String description, double amount, Category category) { 
        
        if (findById(id) == null) {
            System.out.println("Expense not found, please try again.");
            return false;
        }
        if (description.isEmpty() || description.isBlank() || amount <= 0 || category == null) {
            System.out.println("Invalid expense data, please try again.");
            return false;
        } else {
            Expense expense = findById(id);
            expense.setDescription(description);
            expense.setAmount(amount);
            expense.setCategory(category);
            return true;
        }
    }
        

     
    public List<Expense> list() {
        System.out.println("Expenses:"+ expenses);
        return expenses;
     }
    public double summary() {
        double sum = 0;
        int i = 0;
        for (i=0;i<expenses.size();i++) {
            sum += expenses.get(i).getAmount();
        }
        return sum;
    }
    public double summaryByMonth(int month, int year) {
        double sum = 0;
        int i = 0;
        for (i=0;i<expenses.size();i++) {
             if (expenses.get(i).getDate().getMonthValue() == month && expenses.get(i).getDate().getYear() == year) {
                sum += expenses.get(i).getAmount();
        
             }
        }
        return sum;
    }
    public void exportToCSV(String filePath) {

    }

    private Expense findById(int id) { 
        int i=0;
        if (id<=0) {
            System.out.println("Invalid expense ID, please try again.");
            return null;
        } else {
           for (i=0;i<expenses.size();i++) {
            if (expenses.get(i).getId() == id) {
                return expenses.get(i);
            }
           } 
           return null;
        }
    }
}
