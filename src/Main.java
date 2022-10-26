import diary.TaskManager;
import diary.task.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_BLUE = "\u001B[34m";

    private static final TaskManager<?> taskManager = new TaskManager<>();

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            label:
            while (true) {
                printMenu();
                System.out.print(ANSI_WHITE + "Выберите пункт меню: ");
                if (scanner.hasNextInt()) {
                    int menu = scanner.nextInt();
                    switch (menu) {
                        case 1:
                            inputTask(scanner);
                            break;
                        case 2:
                            changeTask(scanner);
                            break;
                        case 3:
                            deleteTask(scanner);
                            break;
                        case 4:
                            getTasks(scanner);
                            break;
                        case 5:
                            printActiveTasks();
                            break;
                        case 6:
                            printDeletedTasks();
                            break;
                        case 0:
                            break label;
                    }
                } else {
                    scanner.next();
                    System.out.println(ANSI_WHITE + "Выберите пункт меню из списка!");
                }
            }
        }
    }

    private static void inputTask(Scanner scanner) {
        try {
            System.out.print(ANSI_YELLOW + "Введите заголовок задачи: ");
            String title = scanner.next();
            System.out.print("Введите текст задачи: ");
            String text = scanner.next();
            System.out.print("Введите тип задачи " + TaskType.getStringValues() + ": ");
            String type = scanner.next();
            var taskType = TaskType.getById(Integer.parseInt(type));
            if (taskType == null) {
                System.out.println(ANSI_RED + "Тип задачи не определен");
                return;
            }
            System.out.print("Введите вид задачи " + RepetitionType.getStringValues() + ": ");
            String kind = scanner.next();
            var repType = RepetitionType.getById(Integer.parseInt(kind));
            if (repType == null) {
                System.out.println(ANSI_RED + "Вид задачи не определен");
                return;
            }
            taskManager.add(title, text, taskType, repType);
            System.out.println(ANSI_GREEN + "Задача добавлена");
        } catch (TaskException | NumberFormatException e) {
            System.out.println(ANSI_RED + e.getMessage());
        }
    }

    private static void changeTask(Scanner scanner) {
        try {
            System.out.print(ANSI_YELLOW + "Введите ID задачи: ");
            String id = scanner.next();
            System.out.print("Введите заголовок задачи: ");
            String title = scanner.next();
            System.out.print("Введите текст задачи: ");
            String text = scanner.next();
            taskManager.change(Integer.parseInt(id), title, text);
            System.out.println(ANSI_GREEN + "Задача изменена");
        } catch (TaskException | NumberFormatException e) {
            System.out.println(ANSI_RED + e.getMessage());
        }
    }

    private static void deleteTask(Scanner scanner) {
        try {
            System.out.print(ANSI_YELLOW + "Введите ID задачи: ");
            String id = scanner.next();
            taskManager.delete(Integer.parseInt(id));
            System.out.println(ANSI_GREEN + "Задача удалена");
        } catch (TaskException | NumberFormatException e) {
            System.out.println(ANSI_RED + e.getMessage());
        }
    }

    private static void getTasks(Scanner scanner) {
        try {
            System.out.print(ANSI_YELLOW + "Введите день задачи: ");
            String day = scanner.next();
            System.out.print("Введите месяц задачи: ");
            String month = scanner.next();
            System.out.print("Введите год задачи: ");
            String year = scanner.next();
            LocalDate date = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
            for (Task task : taskManager.getTasksByDate(date)) {
                System.out.print(ANSI_GREEN);
                System.out.println(task);
            }
        } catch (DateTimeException | NumberFormatException | TaskException e) {
            System.out.println(ANSI_RED + e.getMessage());
        }
    }

    private static void printActiveTasks() {
        try {
            for (var entry : taskManager.getActiveTasks().entrySet()) {
                System.out.print(ANSI_BLUE);
                System.out.println(entry.getKey());
                for (Task task : entry.getValue()) {
                    System.out.print(ANSI_GREEN);
                    System.out.println(task);
                }
            }
        } catch (TaskException e) {
            System.out.println(ANSI_RED + e.getMessage());
        }
    }

    private static void printDeletedTasks() {
        try {
            for (var entry : taskManager.getDeletedTasks().entrySet()) {
                System.out.print(ANSI_BLUE);
                System.out.println(entry.getKey());
                for (Task task : entry.getValue()) {
                    System.out.print(ANSI_PURPLE);
                    System.out.println(task);
                }
            }
        } catch (TaskException e) {
            System.out.println(ANSI_RED + e.getMessage());
        }
    }

    private static void printMenu() {
        System.out.println(ANSI_WHITE + "\n1. Добавить задачу"
                + "\n2. Редактировать задачу"
                + "\n3. Удалить задачу"
                + "\n4. Получить задачу на указанный день"
                + "\n5. Отобразить все активные задачи"
                + "\n6. Отобразить все удаленные задачи"
                + "\n0. Выход"
        );
    }
}