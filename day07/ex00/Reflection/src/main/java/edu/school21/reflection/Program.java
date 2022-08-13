package edu.school21.reflection;

import edu.school21.classes.Car;
import edu.school21.classes.User;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {
    private static final String SPLITTER = "__________";

    public static void main(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?>[] classes = new Class<?>[]{Car.class, User.class};
        System.out.println("Classes:");
        for (Class<?> aClass : classes) {
            System.out.println(aClass.getSimpleName());
        }
        System.out.println(SPLITTER);

        Scanner scanner = new Scanner(System.in);
        Class<?> selectClass = selectClass(classes, scanner);
        printClassInfo(selectClass);
        System.out.println(SPLITTER);

        Object obj = createObject(selectClass, scanner);
        System.out.println(SPLITTER);

        changeField(obj, scanner);
        System.out.println(SPLITTER);

        callMethod(obj, scanner);
    }

    private static void callMethod(Object obj, Scanner scanner) throws IllegalAccessException, InvocationTargetException {
        System.out.println("Enter name of the method for call:");
        while (true) {
            String classMethod = scanner.nextLine();
            Method method = null;
            for (Method m : obj.getClass().getDeclaredMethods()) {
                if (m.getName().equals(classMethod)) {
                    method = m;
                    break;
                }
            }
            if (method == null) {
                System.out.println("Specified method does not exist. Try again.");
                continue;
            }
            List<Object> parameterUser = new ArrayList<>();
            for (Class<?> parameterType : method.getParameterTypes()) {
                System.out.println("Enter " + parameterType.getSimpleName() + " value:");
                parameterUser.add(scannerGetType(parameterType, scanner));
            }
            Object result = method.invoke(obj, parameterUser.toArray());
            if (method.getReturnType() != void.class) {
                System.out.println("Method returned:\n" + result);
            }
            break;
        }
    }

    private static void changeField(Object obj, Scanner scanner) throws IllegalAccessException {
        System.out.println("Enter name of the field for changing:");
        while (true) {
            String classField = scanner.nextLine();
            Field field;
            try {
                field = obj.getClass().getDeclaredField(classField);
            } catch (NoSuchFieldException e) {
                System.out.println("Specified field does not exist. Try again.");
                continue;
            }
            System.out.println("Enter " + field.getType().getSimpleName() + " value:");
            Object value = scannerGetType(field.getType(), scanner);
            field.setAccessible(true);
            field.set(obj, value);
            break;
        }
        System.out.println("Object updated: " + obj);
    }

    private static Class<?> selectClass(Class<?>[] classes, Scanner scanner) {
        System.out.println("Enter class name:");
        while (true) {
            String className = scanner.nextLine();
            for (Class<?> aClass : classes) {
                if (aClass.getSimpleName().equals(className)) {
                    return aClass;
                }
            }
            System.out.println("Specified class does not exist. Try again.");
        }
    }

    private static void printClassInfo(Class<?> myClass) {
        Field[] fields = myClass.getDeclaredFields();
        Method[] methods = myClass.getDeclaredMethods();
        if (fields.length > 0) {
            System.out.println("fields:");
            for (Field field : fields) {
                System.out.println("\t" + field.getType().getSimpleName() + " " +
                        field.getName());
            }
        }
        if (methods.length > 0) {
            System.out.println("methods:");
            for (Method method : methods) {
                System.out.print("\t" + method.getReturnType().getSimpleName() + " " +
                        method.getName() + "(");
                Class<?>[] parameters = method.getParameterTypes();
                for (int i = 0; i < parameters.length; i++) {
                    System.out.print(parameters[i].getSimpleName());
                    if (i < parameters.length - 1)
                        System.out.print(", ");
                }
                System.out.println(")");
            }
        }
    }

    private static Object createObject(Class<?> clazz, Scanner scanner) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Object result;
        System.out.println("Let's create an object.");
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        Constructor<?> ctr = null;
        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterCount() > 0) {
                ctr = constructor;
                break;
            }
        }
        if (ctr == null) {
            throw new RuntimeException("Can not find constructor with params [class = " + clazz + "]");
        }
        Field[] fields = clazz.getDeclaredFields();
        List<Object> constructorStuffFromConsole = new ArrayList<>();
        int i = 0;
        for (Parameter param : ctr.getParameters()) {
            System.out.println(fields[i].getName() + " (" + param.getType().getSimpleName() + "): ");
            constructorStuffFromConsole.add(scannerGetType(param.getType(), scanner));
            i++;
        }
        result = ctr.newInstance(constructorStuffFromConsole.toArray());
        System.out.println("Object created: " + result);
        return result;
    }

    private static Object scannerGetType(Class<?> clazz, Scanner scanner) {
        String line = scanner.nextLine();
        switch (clazz.getSimpleName().toLowerCase()) {
            case "string":
                return line;
            case "int":
                return Integer.parseInt(line);
            case "double":
                return Double.parseDouble(line);
            case "boolean":
                return Boolean.parseBoolean(line);
            case "long":
                return Long.parseLong(line);
            default:
                throw new RuntimeException("Unrecognized type");
        }
    }
}
