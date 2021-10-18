package util;

import java.lang.reflect.*;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.function.Function;

public class Reflection {
    private static abstract class AbstractField<T extends AccessibleObject> {
        private final Object target;
        private final String fieldName;

        AbstractField(Object of, String name) {
            target = of;
            fieldName = name;
        }

        public Object getTargetObject() {
            return target;
        }
        public String getFieldName() {
            return fieldName;
        }

        public boolean safelyMakeAccessible() {
            Runnable proc = () -> getRaw().setAccessible(true);
            try {
                if (System.getSecurityManager() == null) {
                    proc.run();
                }
                else {
                    AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
                        proc.run();
                        return null;
                    });
                }
                return true;
            } catch (Throwable e) {
                try {
                    proc.run();
                    return true;
                }
                catch (Throwable e2) {
                    return false;
                }
            }
        }

        public abstract T getRaw();
    }

    public static class Method<R> extends AbstractField<java.lang.reflect.Method> {
        final Class<?>[] paramTypes;
        java.lang.reflect.Method method = null;

        public Method(Object of, String name, Class<?> ...paramTypes) {
            super(of, name);
            this.paramTypes = paramTypes;

            try {
                this.method = getTargetObject()
                    .getClass()
                    .getDeclaredMethod(getFieldName(), paramTypes);
            }
            catch (NoSuchMethodException | SecurityException e) {
                // Ignored
            }
            if (!super.safelyMakeAccessible()) {
                throw new SecurityException("Failed to make field \"" + name + "\" accessible.");
            }
        }

        @Override
        public java.lang.reflect.Method getRaw() {
            return this.method;
        }

        public R call(Object ...params) {
            try {
                return (R) this.method.invoke(getTargetObject(), params);
            }
            catch (InvocationTargetException | IllegalAccessException e) {
                return null;
            }
        }
    }

    public static class Field<T> extends AbstractField<java.lang.reflect.Field> {
        java.lang.reflect.Field field;

        public Field(Object of, String name) {
            super(of, name);
            try {
                this.field = getTargetObject()
                    .getClass()
                    .getDeclaredField(getFieldName());
            }
            catch (SecurityException | NoSuchFieldException e) {
                // Ignored
            }

            if (!super.safelyMakeAccessible()) {
                throw new SecurityException("Failed to make field \"" + name + "\" accessible.");
            }
        }

        @Override
        public java.lang.reflect.Field getRaw() {
            return field;
        }

        public T get() {
            try {
                return (T) field.get(getTargetObject());
            }
            catch (IllegalAccessException e) {
                return null;
            }
        }

        public Field<T> set(T newValue) {
            try {
                field.set(getTargetObject(), newValue);
                return this;
            }
            catch (IllegalAccessException e) {
                return null;
            }
        }
    }
}
