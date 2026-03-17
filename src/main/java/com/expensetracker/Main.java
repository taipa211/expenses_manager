package com.expensetracker;

import com.expensetracker.manager.BudgetManager;
import com.expensetracker.manager.CategoryManager;
import com.expensetracker.manager.ExpenseManager;
import com.expensetracker.model.Category;
import com.expensetracker.model.Expense;
import com.expensetracker.util.JsonStorage;
import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        JsonStorage storage = new JsonStorage("data");

        CategoryManager categoryManager = new CategoryManager();
        ExpenseManager expenseManager = new ExpenseManager();
        BudgetManager budgetManager = new BudgetManager(expenseManager);

        categoryManager.load(storage.loadCategories());
        expenseManager.load(storage.loadExpenses());
        budgetManager.load(storage.loadBudgets());

        // Check current budget status (if any budgets exist)
        budgetManager.checkWarning(LocalDate.now().getMonthValue(), LocalDate.now().getYear());

        if (args.length == 0) {
            printHelp();
            return;
        }

        switch (args[0]) {
            case "add": {
                String description = getArg(args, "--description");
                String amountStr = getArg(args, "--amount");
                String category = getArg(args, "--category");

                if (description == null || amountStr == null) {
                    System.out.println("--description and --amount are required for add");
                    return;
                }

                double amount;
                try {
                    amount = Double.parseDouble(amountStr);
                } catch (NumberFormatException e) {
                    System.out.println("Amount must be a valid number.");
                    return;
                }

                Category cat = categoryManager.findByName(
                        category != null ? category : "Other"
                );

                expenseManager.add(description, amount, cat);
                storage.saveExpenses(expenseManager.list());
                System.out.println("Expense added successfully.");
                break;
            }

            case "delete": {
                String idStr = getArg(args, "--id");
                if (idStr == null) {
                    System.out.println("--id is required for delete");
                    return;
                }

                try {
                    int id = Integer.parseInt(idStr);
                    expenseManager.delete(id);
                    storage.saveExpenses(expenseManager.list());
                } catch (NumberFormatException e) {
                    System.out.println("ID must be a valid integer.");
                }
                break;
            }
            case "update": {
                String idStr = getArg(args, "--id");
                String description = getArg(args, "--description");
                String amountStr = getArg(args, "--amount");

                if (idStr == null || amountStr == null) {
                    System.out.println("--id and --amount are required for update");
                    return;
                }

                int id;
                double amount;
                try {
                    id = Integer.parseInt(idStr);
                    amount = Double.parseDouble(amountStr);
                } catch (NumberFormatException e) {
                    System.out.println("ID and amount must be valid numbers.");
                    return;
                }

                String categoryName = getArg(args, "--category");
                Category cat = categoryManager.findByName(categoryName);

                expenseManager.update(id, description, amount, cat);
                storage.saveExpenses(expenseManager.list());
                break;
            }
            case "list": {
                List<Expense> list = expenseManager.list();
                if (list.isEmpty()) {
                    System.out.println("No expenses found.");
                } else {
                    for (Expense expense : list) {
                        System.out.println(expense);
                    }
                }
                break;
            }
            case "summary": {
                double sum = expenseManager.summary();
                System.out.println("Total expenses: " + sum);
                break;
            }
            default:
                printHelp();
        }
    }

    private static String getArg(String[] args, String flag) {
        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].equals(flag)) {
                return args[i + 1];
            }
        }
        return null;
    }

    private static void printHelp() {
        System.out.println("Usage: java -jar expense-tracker.jar <command> [options]");
        System.out.println("Commands:");
        System.out.println("  add --description <text> --amount <number> [--category <name>]");
        System.out.println("  delete --id <expenseId>");
        System.out.println("  update --id <expenseId> --amount <number> [--description <text>] [--category <name>]");
        System.out.println("  list");
        System.out.println("  summary");
        System.out.println();
        System.out.println("All data is stored to disk as JSON under the 'data' folder.");
    }
}
