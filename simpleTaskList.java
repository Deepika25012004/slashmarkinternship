import java.util.ArrayList;
import java.util.Scanner;

public class simpleTaskList {
    private static ArrayList<String> tasks = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- Task List Menu ---");
            System.out.println("1. Add a Task");
            System.out.println("2. Remove a Task");
            System.out.println("3. List All Tasks");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addTask(scanner);
                    break;
                case 2:
                    removeTask(scanner);
                    break;
                case 3:
                    listTasks();
                    break;
                case 4:
                    running = false;
                    System.out.println("Exiting Task List...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void addTask(Scanner scanner) {
        System.out.print("Enter the task: ");
        String task = scanner.nextLine();
        tasks.add(task);
        System.out.println("Task added successfully.");
    }

    private static void removeTask(Scanner scanner) {
        listTasks();
        System.out.print("Enter the number of the task to remove: ");
        int index = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        if (index > 0 && index <= tasks.size()) {
            tasks.remove(index - 1);
            System.out.println("Task removed successfully.");
        } else {
            System.out.println("Invalid task number.");
        }
    }

    private static void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks to display.");
        } else {
            System.out.println("\n--- Task List ---");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }
}
