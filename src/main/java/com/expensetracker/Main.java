package com.expensetracker;

import com.expensetracker.manager.ExpenseManager;
import com.expensetracker.manager.CategoryManager;
import com.expensetracker.manager.BudgetManager;

public class Main {
    public static void main(String[] args) {
        CategoryManager categoryManager = new CategoryManager();
        ExpenseManager expenseManager = new ExpenseManager();
        BudgetManager budgetManager = new BudgetManager(expenseManager);

        if (args.length == 0) { printHelp(); return; }

        switch (args[0]) {
            case "add":     break;
            case "delete":  break;
            case "update":  break;
            case "list":    break;
            case "summary": break;
            default: printHelp();
        }
    }

    private static void printHelp() {}
}
