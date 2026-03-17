# 💸 Expense Tracker CLI

A command-line expense tracker built in Java using Object-Oriented Programming principles.

## About

This project was built to practice OOP concepts such as encapsulation, class design, single responsibility principle, and relationships between objects. It allows users to manage their personal finances directly from the terminal.

## Features

- Add, update, and delete expenses
- View all expenses
- View a summary of total expenses
- View a summary by month
- Categorize expenses
- Set monthly and annual budgets with overspending warnings
- Export expenses to CSV

## Project Structure

```
src/main/java/com/expensetracker/
├── Main.java                  # Entry point, handles CLI commands
├── model/
│   ├── Expense.java           # Expense entity
│   ├── Category.java          # Category entity
│   └── Budget.java            # Budget entity (monthly & annual)
├── manager/
│   ├── ExpenseManager.java    # Business logic for expenses
│   ├── CategoryManager.java   # Business logic for categories
│   └── BudgetManager.java     # Business logic for budgets
└── util/
    └── JsonStorage.java       # Data persistence (in progress)
```

## Usage

```bash
# Add an expense
java -cp out com.expensetracker.Main add --description "Lunch" --amount 20

# Add an expense with category
java -cp out com.expensetracker.Main add --description "Lunch" --amount 20 --category Food

# List all expenses
java -cp out com.expensetracker.Main list

# View total summary
java -cp out com.expensetracker.Main summary

# View summary for a specific month
java -cp out com.expensetracker.Main summary --month 3

# Delete an expense
java -cp out com.expensetracker.Main delete --id 1

# Update an expense
java -cp out com.expensetracker.Main update --id 1 --description "Dinner" --amount 25

# Set a monthly budget
java -cp out com.expensetracker.Main budget-add --amount 500 --month 3 --year 2026

# Set an annual budget
java -cp out com.expensetracker.Main budget-add --amount 6000 --year 2026
```

## Default Categories

Food, Transport, Entertainment, Health, Clothes, Shopping, Utilities, Subscriptions, Education, Other

## OOP Concepts Applied

- **Encapsulation** — all attributes are private, accessed through getters/setters
- **Single Responsibility Principle** — each class has one clear responsibility
- **Object Relationships** — `BudgetManager` depends on `ExpenseManager` to check spending totals
- **Constructors** — `Budget` uses constructor overloading for monthly vs annual budgets

## Tech Stack

- Java 25
- No external dependencies

## Status

✅ Data persistence via JSON is now implemented (stored under the `data/` folder).
