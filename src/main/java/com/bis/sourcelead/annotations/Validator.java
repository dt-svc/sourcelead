package com.bis.sourcelead.annotations;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 *
 * @author RajaGopal Reddy
 */
public class Validator {

    public static boolean validate(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Required.class)) {
                try {
                    field.setAccessible(true);
                    if (field.get(object) == null || field.get(object).toString().isEmpty()) {
                        object.getClass().getMethod("setErrorKey", String.class).invoke(object, "error.required");
                        object.getClass().getMethod("setParams", new Class[]{String[].class}).invoke(object, new Object[]{new String[]{field.getAnnotation(Required.class).desc()}});
                        return false;
                    } else if (field.getAnnotation(Required.class).alphaNumericOnly()) {
                        String value = field.get(object).toString();
                        String pattern = "^[a-zA-Z0-9]*$";
                        if (value.matches(pattern)) {
                            return true;
                        }
                        object.getClass().getMethod("setErrorKey", String.class).invoke(object, "error.required");
                        object.getClass().getMethod("setParams", new Class[]{String[].class}).invoke(object, new Object[]{new String[]{field.getAnnotation(Required.class).desc()}});
                        return false;
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    return false;
                } catch (NoSuchMethodException | SecurityException | InvocationTargetException ex) {
                    return false;
                }
            }
            if (field.isAnnotationPresent(Email.class)) {
                try {
                    field.setAccessible(true);
                    if (field.get(object) != null && !field.get(object).toString().trim().isEmpty()) {
                        try {
                            new InternetAddress(field.get(object).toString().trim()).validate();
                        } catch (IllegalArgumentException | IllegalAccessException | AddressException ex) {
                            object.getClass().getMethod("setErrorKey", String.class).invoke(object, "error.email");
                            object.getClass().getMethod("setParams", new Class[]{String[].class}).invoke(object, new Object[]{new String[]{}});
                            return false;
                        }
                        return true;
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    return false;
                } catch (NoSuchMethodException | SecurityException | InvocationTargetException ex) {
                    return false;
                }
            }
            if (field.isAnnotationPresent(Type.class)) {
                try {
                    field.setAccessible(true);
                    Object obj = field.get(object);
                    if (!field.isAnnotationPresent(Required.class)) {
                        if (obj == null) {
                            continue;
                        }
                    }

                    Type type = field.getAnnotation(Type.class);
                    if (type.minLength() > 0 && obj.toString().length() < type.minLength()) {
                        object.getClass().getMethod("setErrorKey", String.class).invoke(object, "error.type2");
                        object.getClass().getMethod("setParams", new Class[]{String[].class}).invoke(object, new Object[]{new String[]{type.desc(), String.valueOf(type.minLength()) + (type.name().equals("String") ? " character " : "digit")}});

                        return false;
                    }
                    if (type.maxLength() > 0 && obj.toString().length() > type.maxLength()) {
                        object.getClass().getMethod("setErrorKey", String.class).invoke(object, "error.type3");
                        object.getClass().getMethod("setParams", new Class[]{String[].class}).invoke(object, new Object[]{new String[]{type.desc(), String.valueOf(type.maxLength()) + (type.name().equals("String") ? " character " : "digit")}});

                        return false;
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    return false;
                } catch (InvocationTargetException | NoSuchMethodException | SecurityException ex) {
                    return false;
                }
            }
        }
        return true;
    }
}
