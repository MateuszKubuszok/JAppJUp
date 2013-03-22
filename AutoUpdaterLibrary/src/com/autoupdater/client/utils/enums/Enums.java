/**
 * Copyright 2012-2013 Maciej Jaworski, Mariusz Kapcia, Paweł Kędzia, Mateusz Kubuszok
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at</p> 
 * 
 * <p>http://www.apache.org/licenses/LICENSE-2.0</p>
 *
 * <p>Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.</p>
 */
package com.autoupdater.client.utils.enums;

import static com.google.common.base.Throwables.propagate;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Contains methods useful in work with Enums objects.
 */
public class Enums {
    /**
     * Obtains enum by its toString() value.
     * 
     * @param klass
     *            Enum klass
     * @param message
     *            message returned by Enums toString() - should be unique
     * @return Enum instance
     */
    public static <E extends Enum<E>> E parseMessage(Class<E> klass, String message) {
        try {
            E[] values = (E[]) klass.getDeclaredMethod("values").invoke(null);

            for (E value : values)
                if (message.equals(value.toString()))
                    return value;

        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            // should never happen since all enum E got method (E[] values())
        }

        return null;
    }

    /**
     * Obtains Enum instance by value of one of its fields.
     * 
     * @param klass
     *            Enum class
     * @param fieldName
     *            field's name
     * @param value
     *            field's value unique to some Enum
     * @return Enum instance
     * @throws NoSuchFieldException
     *             thrown if field with such name doesn't exist
     */
    public static <E extends Enum<E>> E parseField(Class<E> klass, String fieldName, Object value)
            throws NoSuchFieldException {
        if (value == null || fieldName == null)
            throw new NullPointerException();

        Field messageField = klass.getDeclaredField(fieldName);
        boolean accessible = messageField.isAccessible();
        messageField.setAccessible(true);
        EParsingStrategy parsingStrategy = EParsingStrategy.resolve(value);

        try {
            E[] eValues = (E[]) klass.getDeclaredMethod("values").invoke(null);

            for (E eValue : eValues)
                if (value.equals(parsingStrategy.getFieldObtainer().obtain(messageField, eValue)))
                    return eValue;

        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            // should never happen since all enum E got method (E[] values())
            propagate(e);
        } finally {
            messageField.setAccessible(accessible);
        }

        return null;
    }

    /**
     * Interface used for obtaining contents from Enums.
     */
    private interface IFieldObtainer {
        Object obtain(Field field, Object source) throws IllegalArgumentException,
                IllegalAccessException;
    }

    /**
     * Used for finding right IFieldObtainer for required field.
     */
    private enum EParsingStrategy {
        BOOLEAN(Boolean.class, new IFieldObtainer() {
            @Override
            public Object obtain(Field field, Object source) throws IllegalArgumentException,
                    IllegalAccessException {
                return field.getBoolean(source);
            }
        }),

        BYTE(Byte.class, new IFieldObtainer() {
            @Override
            public Object obtain(Field field, Object source) throws IllegalArgumentException,
                    IllegalAccessException {
                return field.getByte(source);
            }
        }),

        CHAR(Character.class, new IFieldObtainer() {
            @Override
            public Object obtain(Field field, Object source) throws IllegalArgumentException,
                    IllegalAccessException {
                return field.getChar(source);
            }
        }),

        INTEGER(Integer.class, new IFieldObtainer() {
            @Override
            public Object obtain(Field field, Object source) throws IllegalArgumentException,
                    IllegalAccessException {
                return field.getInt(source);
            }
        }),

        SHORT(Short.class, new IFieldObtainer() {
            @Override
            public Object obtain(Field field, Object source) throws IllegalArgumentException,
                    IllegalAccessException {
                return field.getShort(source);
            }
        }),

        LONG(Long.class, new IFieldObtainer() {
            @Override
            public Object obtain(Field field, Object source) throws IllegalArgumentException,
                    IllegalAccessException {
                return field.getLong(source);
            }
        }),

        FLOAT(Float.class, new IFieldObtainer() {
            @Override
            public Object obtain(Field field, Object source) throws IllegalArgumentException,
                    IllegalAccessException {
                return field.getFloat(source);
            }
        }),

        DOUBLE(Double.class, new IFieldObtainer() {
            @Override
            public Object obtain(Field field, Object source) throws IllegalArgumentException,
                    IllegalAccessException {
                return field.getDouble(source);
            }
        }),

        GENERIC(Object.class, new IFieldObtainer() {
            @Override
            public Object obtain(Field field, Object source) throws IllegalArgumentException,
                    IllegalAccessException {
                return field.get(source);
            }
        });

        private final Class<?> klass;
        private final IFieldObtainer fieldObtainer;

        private EParsingStrategy(Class<?> klass, IFieldObtainer fieldObtainer) {
            this.klass = klass;
            this.fieldObtainer = fieldObtainer;
        }

        /**
         * Returns IFieldObtainer.
         * 
         * @return IFieldObtainer
         */
        public IFieldObtainer getFieldObtainer() {
            return fieldObtainer;
        }

        /**
         * Resolves IFieldObtainer for given object.
         * 
         * @param source
         *            object for which IFieldObtainer is sought
         * @return right parsing strategy
         */
        public static EParsingStrategy resolve(Object source) {
            for (EParsingStrategy value : EParsingStrategy.values())
                if (value.klass.isInstance(source))
                    return value;
            return null;
        }
    }
}
