package com.lab6;

/**
 * Класс с различными методами для демонстрации рефлексии и аннотаций
 */
public class MyClass {
    private String privateField = "Приватное поле";
    protected String protectedField = "Защищенное поле";
    public String publicField = "Публичное поле";

    // Публичные методы
    public void publicMethod() {
        System.out.println("Вызван публичный метод без параметров");
    }

    public String publicMethodWithParams(String message, int number) {
        String result = "Публичный метод с параметрами: " + message + ", " + number;
        System.out.println(result);
        return result;
    }

    // Защищенные методы с аннотацией
    @Repeat(times = 2)
    protected void protectedMethod() {
        System.out.println("Вызван защищенный метод без параметров");
    }

    @Repeat(times = 3)
    protected String protectedMethodWithParams(String text) {
        String result = "Защищенный метод с параметром: " + text;
        System.out.println(result);
        return result;
    }

    // Приватные методы с аннотацией
    @Repeat(times = 4)
    private void privateMethod() {
        System.out.println("Вызван приватный метод без параметров. Поле: " + privateField);
    }

    @Repeat(times = 2)
    private int privateMethodWithParams(int a, int b) {
        int result = a + b;
        System.out.println("Приватный метод с параметрами: " + a + " + " + b + " = " + result);
        return result;
    }

    @Repeat(times = 1)
    private void complexPrivateMethod(String name, int age, boolean active) {
        System.out.println("Комплексный приватный метод: " + name + ", " + age + " лет, активен: " + active);
    }

    // Обычные методы без аннотации (не должны вызываться Invoker'ом)
    protected void normalProtectedMethod() {
        System.out.println("Обычный защищенный метод (без аннотации)");
    }

    private void normalPrivateMethod() {
        System.out.println("Обычный приватный метод (без аннотации)");
    }
}