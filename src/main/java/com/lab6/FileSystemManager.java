package com.lab6;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Comparator;

/**
 * Класс для работы с файловой системой через java.nio.file
 */
public class FileSystemManager {

    /**
     * Создает файловую структуру согласно заданию
     * @param surname фамилия для названия корневой директории
     * @param name имя для названия файла
     */
    public static void createFileStructure(String surname, String name) throws IOException {
        Path rootDir = Paths.get(surname);

        System.out.println("=== Создание файловой структуры ===");

        // 1. Создаем корневую директорию
        if (Files.notExists(rootDir)) {
            Files.createDirectories(rootDir);
            System.out.println("Создана директория: " + rootDir.toAbsolutePath());
        }

        // 2. Создаем файл с именем в корневой директории
        Path nameFile = rootDir.resolve(name + ".txt");
        if (Files.notExists(nameFile)) {
            Files.createFile(nameFile);
            System.out.println("Создан файл: " + nameFile.getFileName());

            // Записываем некоторое содержимое в файл
            String content = "Файл: " + name + "\nСоздан для лабораторной работы №6";
            Files.writeString(nameFile, content, StandardOpenOption.WRITE);
        }

        // 3. Создаем вложенные директории dir1/dir2/dir3
        Path nestedDir = rootDir.resolve("dir1/dir2/dir3");
        Files.createDirectories(nestedDir);
        System.out.println("Созданы вложенные директории: dir1/dir2/dir3");

        // 4. Копируем файл во вложенные директории
        Path copiedFile = nestedDir.resolve(name + ".txt");
        Files.copy(nameFile, copiedFile, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Скопирован файл в: " + copiedFile);

        // 5. Создаем file1 в dir1
        Path file1 = rootDir.resolve("dir1/file1.txt");
        if (Files.notExists(file1)) {
            Files.createFile(file1);
            Files.writeString(file1, "Это file1 в директории dir1");
            System.out.println("Создан файл: dir1/file1.txt");
        }

        // 6. Создаем file2 в dir2
        Path file2 = rootDir.resolve("dir1/dir2/file2.txt");
        if (Files.notExists(file2)) {
            Files.createFile(file2);
            Files.writeString(file2, "Это file2 в директории dir2");
            System.out.println("Создан файл: dir1/dir2/file2.txt");
        }

        System.out.println("Файловая структура создана успешно!\n");
    }

    /**
     * Рекурсивно обходит директорию и выводит структуру
     * @param directoryPath путь к корневой директории для обхода
     */
    public static void walkFileTree(String directoryPath) throws IOException {
        Path startDir = Paths.get(directoryPath);

        System.out.println("=== Рекурсивный обход директории " + directoryPath + " ===");

        if (Files.notExists(startDir)) {
            System.out.println("Директория не существует: " + directoryPath);
            return;
        }

        Files.walk(startDir)
                .forEach(path -> {
                    try {
                        if (Files.isDirectory(path)) {
                            System.out.println("D: " + startDir.relativize(path) + "/");
                        } else {
                            System.out.println("F: " + startDir.relativize(path) +
                                    " (размер: " + Files.size(path) + " байт)");
                        }
                    } catch (IOException e) {
                        System.err.println("Ошибка при чтении информации о файле: " + e.getMessage());
                    }
                });

        System.out.println();
    }

    /**
     * Удаляет директорию со всем содержимым
     * @param directoryPath путь к директории для удаления
     */
    public static void deleteDirectoryRecursively(String directoryPath) throws IOException {
        Path dirToDelete = Paths.get(directoryPath);

        System.out.println("=== Удаление директории " + directoryPath + " ===");

        if (Files.notExists(dirToDelete)) {
            System.out.println("Директория для удаления не существует: " + directoryPath);
            return;
        }

        // Удаляем рекурсивно с сортировкой в обратном порядке (сначала файлы, потом директории)
        Files.walk(dirToDelete)
                .sorted(Comparator.reverseOrder())
                .forEach(path -> {
                    try {
                        Files.delete(path);
                        System.out.println("Удалено: " + dirToDelete.relativize(path));
                    } catch (IOException e) {
                        System.err.println("Ошибка при удалении " + path + ": " + e.getMessage());
                    }
                });

        System.out.println("Директория " + directoryPath + " удалена успешно!\n");
    }
}