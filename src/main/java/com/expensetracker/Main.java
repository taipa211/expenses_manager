package com.expensetracker;

import com.expensetracker.manager.CategoryManager;
import com.expensetracker.manager.ExpenseManager;
import com.expensetracker.model.Category;
import com.expensetracker.model.Expense;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        CategoryManager categoryManager = new CategoryManager();
        ExpenseManager expenseManager = new ExpenseManager();

        if (args.length == 0) {
            printHelp();
            return;
        }

        switch (args[0]) {
            case "add": {

                String description = getArg(args, "--description");
                double amount = Double.parseDouble(getArg(args, "--amount"));
                String category = getArg(args, "--category");

                Category cat = categoryManager.findByName(
                        category != null ? category : "Other"
                );

                expenseManager.add(description, amount, cat);
                System.out.println("Expense added successfully.");
                break;
            }

            case "delete": {
                String Id = getArg(args, "--id");
                expenseManager.delete((Integer.parseInt(Id)));
                break;
            }
            case "update": {
                String Id = getArg(args, "--id");
                String Description = getArg(args, "--description");
                String amountStr = getArg(args, "--amount");

                if (amountStr == null) {
                    System.out.println("Amount is required for update, please provide it and try again.");
                    return;
                }

                double Amount = Double.parseDouble(amountStr);
                String categoryName = getArg(args, "--category");
                Category cat = categoryManager.findByName(categoryName);

                expenseManager.update(Integer.parseInt(Id), Description, Amount, cat);
                break;
            }
            case "list": {

                List<Expense> list = expenseManager.list();
                if (list.size() == 0) {
                    System.out.println("No expenses found.");
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        System.out.println(list.get(i));
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
    }

}
