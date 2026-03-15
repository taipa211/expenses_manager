package com.expensetracker;

import com.expensetracker.manager.BudgetManager;
import com.expensetracker.manager.CategoryManager;
import com.expensetracker.manager.ExpenseManager;

public class Main {
    public static void main(String[] args) {
        CategoryManager categoryManager = new CategoryManager();
        ExpenseManager expenseManager = new ExpenseManager();
        BudgetManager budgetManager = new BudgetManager(expenseManager);

        if (args.length == 0) { printHelp(); return; }

        switch (args[0]) {
            case "add": String description = getArg(args, "--description");
                        double amount = Double.parseDouble(getArg(args, "--amount"));
                        String category = getArg(args, "--category");
                        expenseManager.add(description, amount, category);
                        break;

            case "delete": {
                String Id = getArg(args, "--id");
                expenseManager.delete(Id);
                break;
            }
            case "update": {
                String Id = getArg(args, "--id");
                String Description = getArg(args, "--description");
                double Amount = Double.parseDouble(getArg(args, "--amount"));
                String Category = getArg(args, "--category");
                expenseManager.update(Id, Description, Amount, Category);
                break;
            }
            case "list":  {
                String Category = getArg(args, "--category");
                expenseManager.list(Category);
                break;
            }
            case "summary": {
                String Category = getArg(args, "--category");
                expenseManager.summary( Category);
                break;
            }
            default: printHelp();
        }
    }
private static String getArg(String[] args, String flag) {
    for (int i = 0; i < args.length - 1; i++) {
        if (args[i].equals(flag)) return args[i + 1];
    }
    return null;
}
    private static void printHelp() {}
    
}
