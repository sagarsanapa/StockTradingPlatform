import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StockTradingPlatform {
    private static Map<String, Double> marketData = new HashMap<>();
    private static Map<String, Integer> portfolio = new HashMap<>();
    private static double cashBalance = 10000.0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        initializeMarketData();
        boolean continueTrading = true;

        while (continueTrading) {
            System.out.println("Welcome to the Stock Trading Platform!");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stocks");
            System.out.println("3. Sell Stocks");
            System.out.println("4. View Portfolio");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewMarketData();
                    break;
                case 2:
                    buyStocks(scanner);
                    break;
                case 3:
                    sellStocks(scanner);
                    break;
                case 4:
                    viewPortfolio();
                    break;
                case 5:
                    continueTrading = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void initializeMarketData() {
        marketData.put("APPLE", 150.00);
        marketData.put("GOOGLE", 2800.00);
        marketData.put("AMAZON", 3500.00);
        marketData.put("MICROSOFT", 300.00);
        marketData.put("TESLA", 700.00);
    }

    private static void viewMarketData() {
        System.out.println("Market Data:");
        for (Map.Entry<String, Double> entry : marketData.entrySet()) {
            System.out.println(entry.getKey() + ": $" + entry.getValue());
        }
    }

    private static void buyStocks(Scanner scanner) {
        System.out.print("Enter stock symbol: ");
        String symbol = scanner.next().toUpperCase();
        if (marketData.containsKey(symbol)) {
            System.out.print("Enter quantity to buy: ");
            int quantity = scanner.nextInt();
            double cost = marketData.get(symbol) * quantity;
            if (cost <= cashBalance) {
                cashBalance -= cost;
                portfolio.put(symbol, portfolio.getOrDefault(symbol, 0) + quantity);
                System.out.println("Bought " + quantity + " shares of " + symbol);
                System.out.println("Remaining cash balance: $" + cashBalance);
            } else {
                System.out.println("Insufficient funds to buy " + quantity + " shares of " + symbol);
            }
        } else {
            System.out.println("Invalid stock symbol.");
        }
    }

    private static void sellStocks(Scanner scanner) {
        System.out.print("Enter stock symbol: ");
        String symbol = scanner.next().toUpperCase();
        if (portfolio.containsKey(symbol)) {
            System.out.print("Enter quantity to sell: ");
            int quantity = scanner.nextInt();
            int currentQuantity = portfolio.get(symbol);
            if (quantity <= currentQuantity) {
                double proceeds = marketData.get(symbol) * quantity;
                cashBalance += proceeds;
                if (quantity == currentQuantity) {
                    portfolio.remove(symbol);
                } else {
                    portfolio.put(symbol, currentQuantity - quantity);
                }
                System.out.println("Sold " + quantity + " shares of " + symbol);
                System.out.println("New cash balance: $" + cashBalance);
            } else {
                System.out.println("You do not own enough shares of " + symbol);
            }
        } else {
            System.out.println("You do not own any shares of " + symbol);
        }
    }

    private static void viewPortfolio() {
        System.out.println("Portfolio:");
        for (Map.Entry<String, Integer> entry : portfolio.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " shares");
        }
        System.out.println("Cash balance: $" + cashBalance);
    }
}
