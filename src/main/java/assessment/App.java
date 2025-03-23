package assessment;

import assessment.services.JsonService;

import java.io.IOException;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws IOException {

        JsonService jsonService = new JsonService();
        System.out.println("Processing JSON files...");
        jsonService.processJson();

        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.println("Get Analytics Data...");

        Scanner scanner = new Scanner(System.in);

        label:
        while (true) {
            System.out.println("\nEnter a command number:");
            System.out.println("1. countValidPointsExistPerType");
            System.out.println("2. calculateAverageRatingPerType");
            System.out.println("3. calculateLocationWithHighestReviews");
            System.out.println("4. calculateLocationWithIncompleteData");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> jsonService.countValidPointsExistPerType();
                case "2" -> jsonService.calculateAverageRatingPerType();
                case "3" -> jsonService.calculateLocationWithHighestReviews();
                case "4" -> jsonService.calculateLocationWithIncompleteData();
                default -> {
                    System.out.println("Would you like to continue? (y/n)");

                    String continueChoice = scanner.nextLine().trim().toLowerCase();
                    if (continueChoice.equals("n")) {
                        System.out.println("Exiting the program...");
                        break label;
                    } else if (!continueChoice.equals("y")) {
                        System.out.println("Invalid choice. Exiting the program...");
                        break label;
                    }
                    continue;
                }
            }

            System.out.println("Do you want to perform more analytics? (y/n)");

            String continueAnalytics = scanner.nextLine().trim().toLowerCase();
            if (continueAnalytics.equals("n")) {
                System.out.println("Exiting the program...");
                break;
            } else if (!continueAnalytics.equals("y")) {
                System.out.println("Invalid choice. Exiting the program...");
                break;
            }
        }

        scanner.close();

    }
}
