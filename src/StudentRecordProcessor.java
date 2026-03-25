import java.io.*;
import java.util.*;

public class StudentRecordProcessor {
    // Поля для хранения данных
    private final List<Student> students = new ArrayList<>();

    // _____реализуйте класс Student ниже в этом же файле______
    // TODO: реализуйте класс Student ниже в этом же файле

    private double averageScore;
    private Student highestStudent;


    /**
     * Task 1 + Task 2 + Task 5 + Task 6
     */
    public void readFile() {
        // TODO: реализуйте чтение файла здесь
        try (BufferedReader reader = new BufferedReader(new FileReader("input/students.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split(",");
                    if (parts.length != 2) {
                        throw new NumberFormatException();
                    }
                    String name = parts[0].trim();
                    int score = Integer.parseInt(parts[1].trim());

                    if (score < 0 || score > 100) {
                        throw new InvalidScoreException("Score " + score + " out of range (0-100)");
                    }

                    students.add(new Student(name, score));

                } catch (NumberFormatException e) {
                    System.out.println("Invalid data: " + line);
                } catch (InvalidScoreException e) {
                    System.out.println("Invalid data: " + line + " (" + e.getMessage() + ")");
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: File 'input/students.txt' not found!");
        } catch (IOException e) {
            System.err.println("Error: Could not read file - " + e.getMessage());
        }
    }

    /**
     * Task 3 + Task 8
     */
    public void processData() {
        // TODO: обработка данных и сортировка здесь
        if (students.isEmpty()) {
            averageScore = 0;
            highestStudent = null;
            return;
        }

        // Sort by score descending
        students.sort((s1, s2) -> s2.score - s1.score);

        // Find highest
        highestStudent = students.get(0);

        // Compute average
        int sum = 0;
        for (Student s : students) {
            sum += s.score;
        }
        averageScore = (double) sum / students.size();
    }

    /**
     * Task 4 + Task 5 + Task 8
     */
    public void writeFile() {
        // TODO: запись результата в файл здесь
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output/report.txt"))) {
            writer.write("Average: " + String.format("%.1f", averageScore));
            writer.newLine();

            if (highestStudent != null) {
                writer.write("Highest: " + highestStudent.name + " - " + highestStudent.score);
                writer.newLine();
            }

            writer.newLine();
            writer.write("Sorted Students (Highest to Lowest):");
            writer.newLine();

            for (Student s : students) {
                writer.write(s.name + " - " + s.score);
                writer.newLine();
            }

        } catch (IOException e) {
            System.err.println("Error: Could not write output file - " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        StudentRecordProcessor processor = new StudentRecordProcessor();

        try {
            processor.readFile();
            processor.processData();
            processor.writeFile();
            System.out.println("Processing completed. Check output/report.txt");
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}

// class InvalidScoreException реализуйте меня
class InvalidScoreException extends Exception {
    public InvalidScoreException(String message) {
        super(message);
    }
}

// class Student (name, score)
class Student {
    String name;
    int score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }
}