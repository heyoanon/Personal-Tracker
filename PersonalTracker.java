import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Account {
    private double balance;

    public Account(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }
}

class User {
    private String userId;
    private String name;
    private String email;

    public User(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
    
    public void updateProfile(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getFinancialSummary(List<Transaction> userTransactions) {
        double totalIncome = 0;
        double totalExpenses = 0;

        for (Transaction t : userTransactions) {
            if (t.getTransactionType().equalsIgnoreCase("income")) {
                totalIncome += t.getAmount();
            } else if (t.getTransactionType().equalsIgnoreCase("expense")) {
                totalExpenses += t.getAmount();
            }
        }

        return String.format(
            "=== Financial Summary for %s ===\n" +
            "Total Income: %.2f\n" +
            "Total Expenses: %.2f\n" +
            "Net Balance: %.2f\n",
            this.name,
            totalIncome,
            totalExpenses,
            (totalIncome - totalExpenses)
        );
    }
}

class InvalidAmountException extends Exception {
    public InvalidAmountException(String message) {
        super(message);
    }
} 

abstract class Transaction {
    double amount;
    private String date;
    private String description;
    private Category category;

    public Transaction(double amount, String date, String description, Category category) throws InvalidAmountException {
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }
    
    public abstract String getTransactionType();
}

class IncomeTransaction extends Transaction {
    public IncomeTransaction(double amount, String date, String description, Category category) throws InvalidAmountException {
        super(amount, date, description, category);
    }

    @Override
    public String getTransactionType() {
        return "Income";
    }
}

class ExpenseTransaction extends Transaction {
    public ExpenseTransaction(double amount, String date, String description, Category category) throws InvalidAmountException {
        super(amount, date, description, category);
    }

    @Override
    public String getTransactionType() {
        return "Expense";
    }
}

class Category {
    private String categoryName;
    private String description;
    private double monthlyBudgetLimit;
    private double monthlyExpenses;

    public Category(String categoryName, String description, double monthlyBudgetLimit, double monthlyExpenses) {
        this.categoryName = categoryName;
        this.description = description;
        this.monthlyBudgetLimit = monthlyBudgetLimit;
        this.monthlyExpenses = monthlyExpenses;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getDescription() {
        return description;
    }

    public double getMonthlyBudgetLimit() {
        return monthlyBudgetLimit;
    }

    public double getMonthlyExpenses() {
        return monthlyExpenses;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setMonthlyBudgetLimit(double monthlyBudgetLimit) {
        this.monthlyBudgetLimit = monthlyBudgetLimit;
    }
}

public class PersonalTracker {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Personal Tracker!");
        System.out.println("Please enter your details to get started.");
        System.out.print("Enter your User ID: ");
        String userId = sc.nextLine();
        System.out.print("Enter your Name: ");
        String name = sc.nextLine();
        System.out.print("Enter your Email: ");
        String email = sc.nextLine();
        System.out.print("Enter the Month and Year: ");
        String date = sc.nextLine();
        System.out.print("Enter your Monthly Income:  ");
        double monthlyIncome = sc.nextDouble();
        sc.nextLine();

        List<Category> categories = new ArrayList<>();
        String addAnotherCategory;
        do {
            System.out.print("Enter a category: ");
            String categoryName = sc.nextLine();
            System.out.print("Enter any additional description: ");
            String description = sc.nextLine();
            System.out.print("Enter a monthly budget limit for this category: ");
            double budgetLimit = sc.nextDouble();
            sc.nextLine();

            categories.add(new Category(categoryName, description, budgetLimit, 0));

            System.out.print("Add another category? (y/n): ");
            addAnotherCategory = sc.nextLine();
        } while (addAnotherCategory.equalsIgnoreCase("y"));
        
        System.out.println("-----Overall Summary Report-----");
        System.out.println("Name: " + name);
        System.out.println("Monthly Income: " + monthlyIncome);
        System.out.println("Date: " + date);
        System.out.println("Categories and Budget Limits:");
        double overallExpenses = 0;
        for (Category category : categories) {
            System.out.println("Category: " + category.getCategoryName());
            System.out.println("Description: " + category.getDescription());
            System.out.println("Budget Limit: " + category.getMonthlyBudgetLimit());
            overallExpenses += category.getMonthlyBudgetLimit();
        }
        
        System.out.printf("Overall Expenses: %.2f%n", overallExpenses);
        System.out.printf("Remaining Budget: %.2f%n", monthlyIncome - overallExpenses);
        sc.close();
    }
}
