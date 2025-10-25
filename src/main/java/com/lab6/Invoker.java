package com.lab6;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 * Класс для вызова методов через Reflection API
 * Вызывает только защищенные и приватные методы, помеченные аннотацией @Repeat
 */
public class Invoker {

    /**
     * Вызывает все защищенные и приватные методы класса MyClass, помеченные аннотацией @Repeat
     * @param obj экземпляр MyClass для вызова методов
     */
    public static void invokeAnnotatedMethods(MyClass obj) {
        Class<?> clazz = obj.getClass();
        Method[] methods = clazz.getDeclaredMethods();

        System.out.println("=== Начало вызова аннотированных методов ===");

        for (Method method : methods) {
            // Проверяем, что метод защищенный или приватный И имеет аннотацию @Repeat
            if ((isProtectedOrPrivate(method)) && method.isAnnotationPresent(Repeat.class)) {
                Repeat repeatAnnotation = method.getAnnotation(Repeat.class);
                int times = repeatAnnotation.times();

                System.out.println("\nМетод: " + method.getName());
                System.out.println("Тип: " + getAccessModifier(method));
                System.out.println("Аннотация @Repeat(times = " + times + ")");
                System.out.println("Параметры: " + method.getParameterCount());

                // Разрешаем доступ к приватным методам
                method.setAccessible(true);

                // Вызываем метод указанное количество раз
                for (int i = 0; i < times; i++) {
                    try {
                        System.out.print("Вызов " + (i + 1) + ": ");

                        // Обрабатываем методы с параметрами и без
                        if (method.getParameterCount() == 0) {
                            method.invoke(obj);
                        } else {
                            // Для методов с параметрами передаем тестовые значения
                            Object result = invokeMethodWithParameters(method, obj);
                            if (result != null) {
                                System.out.println("Результат: " + result);
                            }
                        }

                    } catch (IllegalAccessException | InvocationTargetException e) {
                        System.err.println("Ошибка при вызове метода " + method.getName() + ": " + e.getMessage());
                    }
                }
            }
        }

        System.out.println("\n=== Завершение вызова аннотированных методов ===");
    }

    /**
     * Проверяет, является ли метод защищенным или приватным
     */
    private static boolean isProtectedOrPrivate(Method method) {
        int modifiers = method.getModifiers();
        return java.lang.reflect.Modifier.isProtected(modifiers) ||
                java.lang.reflect.Modifier.isPrivate(modifiers);
    }

    /**
     * Возвращает строковое представление модификатора доступа метода
     */
    private static String getAccessModifier(Method method) {
        int modifiers = method.getModifiers();
        if (java.lang.reflect.Modifier.isPrivate(modifiers)) {
            return "private";
        } else if (java.lang.reflect.Modifier.isProtected(modifiers)) {
            return "protected";
        } else if (java.lang.reflect.Modifier.isPublic(modifiers)) {
            return "public";
        } else {
            return "package-private";
        }
    }

    /**
     * Вызывает метод с параметрами, подставляя тестовые значения
     */
    private static Object invokeMethodWithParameters(Method method, Object obj)
            throws IllegalAccessException, InvocationTargetException {
        Class<?>[] paramTypes = method.getParameterTypes();
        Object[] params = new Object[paramTypes.length];

        // Заполняем параметры тестовыми значениями в зависимости от типа
        for (int i = 0; i < paramTypes.length; i++) {
            if (paramTypes[i] == String.class) {
                params[i] = "тест" + (i + 1);
            } else if (paramTypes[i] == int.class || paramTypes[i] == Integer.class) {
                params[i] = (i + 1) * 10;
            } else if (paramTypes[i] == boolean.class || paramTypes[i] == Boolean.class) {
                params[i] = i % 2 == 0;
            } else {
                params[i] = null;
            }
        }

        return method.invoke(obj, params);
    }
}