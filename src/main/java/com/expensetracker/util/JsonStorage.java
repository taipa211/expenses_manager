package com.expensetracker.util;

import com.expensetracker.model.Budget;
import com.expensetracker.model.Category;
import com.expensetracker.model.Expense;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonStorage {

    private final Path basePath;

    public JsonStorage(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            filePath = "data";
        }
        this.basePath = Paths.get(filePath);
        // Ensure directory exists when using directory-based storage
        try {
            if (!Files.exists(basePath)) {
                Files.createDirectories(basePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to setup storage path: " + basePath, e);
        }
    }

    public void saveExpenses(List<Expense> expenses) {
        String json = toJsonArray(expenses);
        writeToFile(resolveFile("expenses.json"), json);
    }

    public List<Expense> loadExpenses() {
        Path file = resolveFile("expenses.json");
        if (!Files.exists(file)) {
            return new ArrayList<>();
        }

        String content = readFile(file);
        return parseExpenses(content);
    }

    public void saveCategories(List<Category> categories) {
        String json = toJsonArray(categories);
        writeToFile(resolveFile("categories.json"), json);
    }

    public List<Category> loadCategories() {
        Path file = resolveFile("categories.json");
        if (!Files.exists(file)) {
            return new ArrayList<>();
        }

        String content = readFile(file);
        return parseCategories(content);
    }

    public void saveBudgets(List<Budget> budgets) {
        String json = toJsonArray(budgets);
        writeToFile(resolveFile("budgets.json"), json);
    }

    public List<Budget> loadBudgets() {
        Path file = resolveFile("budgets.json");
        if (!Files.exists(file)) {
            return new ArrayList<>();
        }

        String content = readFile(file);
        return parseBudgets(content);
    }

    // --- Helpers ---
    private Path resolveFile(String name) {
        if (basePath.toString().endsWith(".json")) {
            return basePath;
        }
        return basePath.resolve(name);
    }

    private void writeToFile(Path path, String content) {
        try {
            if (Files.notExists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }
            Files.write(path, content.getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write JSON to file: " + path, e);
        }
    }

    private String readFile(Path path) {
        try {
            return Files.readString(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON file: " + path, e);
        }
    }

    private String toJsonArray(List<?> items) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        boolean first = true;
        for (Object item : items) {
            if (!first) {
                sb.append(",");
            }
            first = false;
            if (item instanceof Expense) {
                sb.append(toJson((Expense) item));
            } else if (item instanceof Category) {
                sb.append(toJson((Category) item));
            } else if (item instanceof Budget) {
                sb.append(toJson((Budget) item));
            } else {
                sb.append("null");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private String toJson(Expense expense) {
        return "{"
                + "\"id\":" + expense.getId() + ","
                + "\"description\":\"" + escape(expense.getDescription()) + "\","
                + "\"amount\":" + expense.getAmount() + ","
                + "\"date\":\"" + expense.getDate() + "\","
                + "\"category\":" + toJson(expense.getCategory())
                + "}";
    }

    private String toJson(Category category) {
        return "{"
                + "\"id\":" + category.getId() + ","
                + "\"name\":\"" + escape(category.getName()) + "\""
                + "}";
    }

    private String toJson(Budget budget) {
        return "{"
                + "\"id\":" + budget.getId() + ","
                + "\"amount\":" + budget.getAmount() + ","
                + "\"month\":" + budget.getMonth() + ","
                + "\"year\":" + budget.getYear()
                + "}";
    }

    private String escape(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private List<Category> parseCategories(String json) {
        List<Category> categories = new ArrayList<>();
        if (json == null || json.isBlank()) {
            return categories;
        }

        Pattern itemPattern = Pattern.compile("\\{\\s*\\\"id\\\"\\s*:\\s*(\\d+)\\s*,\\s*\\\"name\\\"\\s*:\\s*\\\"(.*?)\\\"\\s*\\}", Pattern.DOTALL);
        Matcher matcher = itemPattern.matcher(json);
        while (matcher.find()) {
            int id = Integer.parseInt(matcher.group(1));
            String name = unescape(matcher.group(2));
            categories.add(new Category(id, name));
        }
        return categories;
    }

    private List<Budget> parseBudgets(String json) {
        List<Budget> budgets = new ArrayList<>();
        if (json == null || json.isBlank()) {
            return budgets;
        }

        Pattern itemPattern = Pattern.compile("\\{\\s*\\\"id\\\"\\s*:\\s*(\\d+)\\s*,\\s*\\\"amount\\\"\\s*:\\s*([0-9]+(?:\\.[0-9]+)?)\\s*,\\s*\\\"month\\\"\\s*:\\s*(\\d+)\\s*,\\s*\\\"year\\\"\\s*:\\s*(\\d+)\\s*\\}", Pattern.DOTALL);
        Matcher matcher = itemPattern.matcher(json);
        while (matcher.find()) {
            int id = Integer.parseInt(matcher.group(1));
            double amount = Double.parseDouble(matcher.group(2));
            int month = Integer.parseInt(matcher.group(3));
            int year = Integer.parseInt(matcher.group(4));
            budgets.add(new Budget(id, amount, month, year));
        }
        return budgets;
    }

    private List<Expense> parseExpenses(String json) {
        List<Expense> expenses = new ArrayList<>();
        if (json == null || json.isBlank()) {
            return expenses;
        }

        Pattern itemPattern = Pattern.compile(
                "\\{\\s*\\\"id\\\"\\s*:\\s*(\\d+)\\s*,\\s*\\\"description\\\"\\s*:\\s*\\\"(.*?)\\\"\\s*,\\s*\\\"amount\\\"\\s*:\\s*([0-9]+(?:\\.[0-9]+)?)\\s*,\\s*\\\"date\\\"\\s*:\\s*\\\"(\\d{4}-\\d{2}-\\d{2})\\\"\\s*,\\s*\\\"category\\\"\\s*:\\s*(\\{.*?\\})\\s*\\}",
                Pattern.DOTALL);

        Matcher matcher = itemPattern.matcher(json);
        while (matcher.find()) {
            int id = Integer.parseInt(matcher.group(1));
            String description = unescape(matcher.group(2));
            double amount = Double.parseDouble(matcher.group(3));
            LocalDate date = LocalDate.parse(matcher.group(4));
            String categoryJson = matcher.group(5);
            Category category = parseCategoryObject(categoryJson);
            expenses.add(new Expense(id, description, amount, date, category));
        }
        return expenses;
    }

    private Category parseCategoryObject(String json) {
        Pattern pattern = Pattern.compile("\\{\\s*\\\"id\\\"\\s*:\\s*(\\d+)\\s*,\\s*\\\"name\\\"\\s*:\\s*\\\"(.*?)\\\"\\s*\\}", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            int id = Integer.parseInt(matcher.group(1));
            String name = unescape(matcher.group(2));
            return new Category(id, name);
        }
        return null;
    }

    private String unescape(String value) {
        if (value == null) {
            return null;
        }
        return value.replace("\\\"", "\"").replace("\\\\", "\\");
    }
}
