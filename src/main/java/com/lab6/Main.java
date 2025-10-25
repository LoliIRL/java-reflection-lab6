package com.lab6;

import java.io.IOException;

/**
 * Главный класс для запуска лабораторной работы №6
 * Демонстрирует работу с рефлексией, аннотациями и файловой системой
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== Лабораторная работа №6 ===");
        System.out.println("Рефлексия, аннотации и работа с файловой системой\n");

        // Часть 1: Рефлексия и аннотации
        demonstrateReflectionAndAnnotations();

        // Часть 2: Работа с файловой системой
        demonstrateFileSystemOperations();

        System.out.println("=== Программа завершена ===");
    }

    /**
     * Демонстрация работы с рефлексией и аннотациями
     */
    private static void demonstrateReflectionAndAnnotations() {
        System.out.println("ЧАСТЬ 1: Рефлексия и аннотации");
        System.out.println("================================\n");

        MyClass myObject = new MyClass();

        // Вызываем аннотированные методы через рефлексию
        Invoker.invokeAnnotatedMethods(myObject);

        System.out.println("\n" + "=".repeat(50) + "\n");
    }

    /**
     * Демонстрация работы с файловой системой
     */
    private static void demonstrateFileSystemOperations() {
        System.out.println("ЧАСТЬ 2: Работа с файловой системой");
        System.out.println("====================================\n");

        String surname = "Ivanov";  // Замените на вашу фамилию
        String name = "Ivan";       // Замените на ваше имя

        try {
            // 1. Создаем файловую структуру
            FileSystemManager.createFileStructure(surname, name);

            // 2. Выполняем рекурсивный обход
            FileSystemManager.walkFileTree(surname);

            // 3. Удаляем директорию dir1 со всем содержимым
            FileSystemManager.deleteDirectoryRecursively(surname + "/dir1");

            // 4. Показываем итоговую структуру
            System.out.println("Итоговая структура после удаления dir1:");
            FileSystemManager.walkFileTree(surname);

        } catch (IOException e) {
            System.err.println("Ошибка при работе с файловой системой: " + e.getMessage());
            e.printStackTrace();
        }
    }
}