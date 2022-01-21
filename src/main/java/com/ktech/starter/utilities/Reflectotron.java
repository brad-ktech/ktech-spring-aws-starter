package com.ktech.starter.utilities;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Id;

public class Reflectotron {

    static private final Logger log;

    static {
        log = LoggerFactory.getLogger(Reflectotron.class);
    }



    public static Field getField(Class<?> clazz, String fieldName) throws SecurityException {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            // Do nothing check parents.
        }

        if (field != null || clazz.getSuperclass() == null) {
            return field;
        } else {
            return getField(clazz.getSuperclass(), fieldName);
        }
    }

    public static List<Method> getAllMethods(Class<?> clazz) {

        List<Method> methods = new ArrayList<>();
        Method[] methodArray = clazz.getDeclaredMethods();
        methods.addAll(Arrays.asList(methodArray));
        if (clazz.getSuperclass() != null) {
            List<Method> superMethods = getAllMethods(clazz.getSuperclass());
            methods.addAll(superMethods);
        }
        return methods;
    }

    public static List<Field> getAllFields(Class<?> clazz) {

        List<Field> fields = new ArrayList<>();
        Field[] fieldArray = clazz.getDeclaredFields();
        fields.addAll(Arrays.asList(fieldArray));

        if (clazz.getSuperclass() != null) {
            List<Field> superFields = getAllFields(clazz.getSuperclass());
            fields.addAll(superFields);
        }

        return fields;

    }

    public static List<Method> getMethodsWithAnnotation(Class<?> clazz, Class<? extends Annotation> annotation) {

        return getAllMethods(clazz).stream().filter(f -> f.isAnnotationPresent(annotation))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static Method getFirstMethodWithAnnotation(Class<?> clazz, Class<? extends Annotation> annotation) {
        return getMethodsWithAnnotation(clazz, annotation).get(0);
    }

    /*
     * Throws no such element exception if no field with the annotation is in the
     * clazz.
     */
    public static Field getFirstFieldWithAnnotation(Class<?> clazz, Class<? extends Annotation> annotation) {
        return getFieldsWithAnnotation(clazz, annotation).get(0);
    }

    public static List<Field> getFieldsWithAnnotation(Class<?> clazz, Class<? extends Annotation> annotation) {

        return getAllFields(clazz).stream().filter(f -> f.isAnnotationPresent(annotation))
                .collect(Collectors.toCollection(ArrayList::new));
    }




    public static Class<?> getTypeOfListField(Field field) {
        ParameterizedType listType = (ParameterizedType) field.getGenericType();
        Class<?> listClass = (Class<?>) listType.getActualTypeArguments()[0];

        return listClass;
    }

    static public <T> T getValue(String valueString, Class<T> valueClass)
            throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        T ret;
        if (!valueClass.isPrimitive()) {
            Constructor<T> constructor = valueClass.getConstructor(String.class);
            ret = constructor.newInstance(valueString);
        } else {
            throw new IllegalArgumentException(
                    "When getting value object from string" + " the value class cannot be a primitive: " + "valueClass["
                            + valueClass.getName() + "] valueString[" + valueString + "]");
        }
        return ret;
    }

    static public Class<?> getPrimitiveObjectEquivClass(Class<?> primitiveClass) {
        if (!primitiveClass.isPrimitive()) {
            throw new IllegalArgumentException(
                    "Attempt to get Object equivelent for non-primitive class: " + primitiveClass);
        }
        Class<?> objEquivelent;
        if (primitiveClass == boolean.class) {
            objEquivelent = Boolean.class;
        } else if (primitiveClass == int.class) {
            objEquivelent = Integer.class;
        } else if (primitiveClass == byte.class) {
            objEquivelent = Byte.class;
        } else if (primitiveClass == short.class) {
            objEquivelent = Short.class;
        } else if (primitiveClass == long.class) {
            objEquivelent = Long.class;
        } else if (primitiveClass == float.class) {
            objEquivelent = Float.class;
        } else if (primitiveClass == double.class) {
            objEquivelent = Double.class;
        } else if (primitiveClass == char.class) {
            objEquivelent = Character.class;
        } else {
            throw new IllegalArgumentException(
                    "Attempt to get Object equivelent for non-primitive class: " + primitiveClass);
        }
        return objEquivelent;
    }

    static public Method getBeanPropertyGetter(Class<?> beanClass, String valueClassName, String property)
            throws ClassNotFoundException, NoSuchMethodException, SecurityException {
        Class<?> valueClass = ClassUtils.getClass(valueClassName);
        return getBeanPropertyGetter(beanClass, valueClass, property);
    }

    static public Method getBeanPropertyGetter(Class<?> beanClass, Class<?> valueClass, String property)
            throws NoSuchMethodException, SecurityException {
        String methodBase = property.substring(0, 1).toUpperCase() + property.substring(1);
        String methodName;
        if (valueClass == Boolean.class || valueClass == boolean.class) {
            methodName = "is" + methodBase;
        } else {
            methodName = "get" + methodBase;
        }
        Method method = beanClass.getMethod(methodName);
        return method;
    }

    static public Method getBeanPropertySetter(Class<?> beanClass, Class<?> valueClass, String property)
            throws NoSuchMethodException, SecurityException {
        String methodName = "set" + property.substring(0, 1).toUpperCase() + property.substring(1);
        Method method = beanClass.getMethod(methodName, valueClass);
        return method;
    }

    /**
     * Finds and returns the generic type of a given object
     * @param object
     * @return
     */
    public static <T> Class<T> getParamaterizedType(Object object) {


        Class<T> clazz = (Class<T>)
                ((ParameterizedType)object.getClass().getGenericSuperclass())
                        .getActualTypeArguments()[0];

        return clazz;
    }

    /**
     * Find the generic <code>Class</code> given an instance of the generic object
     * and the generic <code>Class</code> to get the generic type of.
     *
     * @param genericInstance
     *            Instance of the generic <code>Class</code>
     * @param genericClass
     *            The generic <code>Class</code> to get the generic type of
     * @return The generic type of <code>genericClass</code> for
     *         <code>genericInstance</code> instance.
     */
    public static Class<?> findGenericParameterType(Object genericInstance, Class<?> genericClass) {
        return findGenericParameterType(genericInstance, genericClass, 0);
    }

    /**
     * Find the generic <code>Class</code> for the generic parameter of index
     * <code>genericParamIndex</code> given an instance of the generic object and
     * the generic <code>Class</code> to get the generic type of.
     *
     * @param genericInstance
     *            Instance of the generic <code>Class</code>
     * @param genericClass
     *            The generic <code>Class</code> to get the generic type of
     * @param genericParamIndex The index of the generic parameter to get the type of
     * @returnThe generic type of <code>genericClass</code> for
     *         <code>genericInstance</code> instance.
     */
    public static Class<?> findGenericParameterType(Object genericInstance, Class<?> genericClass,
                                                    int genericParamIndex) {
        log.debug("Getting generic type For instance {}[{}] for generic class[{}]", genericInstance,
                genericInstance.getClass().getName(), genericClass.getName());

        Map<Type, Type> genericTypesMap = new HashMap<Type, Type>();
        Class<?> genericInstanceClass = genericInstance.getClass();
        log.debug("Got first instanceClass[{}]", genericInstanceClass.getName());
        while (genericClass != genericInstanceClass.getSuperclass()) {
            extractTypeArguments(genericTypesMap, genericInstanceClass);
            genericInstanceClass = genericInstanceClass.getSuperclass();

            if (genericInstanceClass == null) {
                throw new IllegalArgumentException(
                        "Never found class " + genericClass.getName() + " in class hierarchy of class "
                                + genericInstance.getClass().getName() + " for instance: " + genericInstance);
            }
            log.debug("Got next instanceClass: {}", genericInstanceClass.getName());
        }

        ParameterizedType parameterizedType = (ParameterizedType) genericInstanceClass.getGenericSuperclass();
        Type actualType = parameterizedType.getActualTypeArguments()[genericParamIndex];
        if (genericTypesMap.containsKey(actualType)) {
            actualType = genericTypesMap.get(actualType);
        }
        if (actualType instanceof Class) {
            return (Class<?>) actualType;
        } else {
            throw new IllegalArgumentException(
                    "Never found class " + genericClass.getName() + " in class hierarchy of class "
                            + genericInstance.getClass().getName() + " for instance: " + genericInstance);
        }
    }

    private static void extractTypeArguments(Map<Type, Type> genericTypesMap, Class<?> genericSubClass) {
        log.debug("Extracting type arguments from class {} looking for types: {}", genericSubClass.getName(),
                genericTypesMap);

        Type genericSuperclass = genericSubClass.getGenericSuperclass();
        if (!(genericSuperclass instanceof ParameterizedType)) {
            log.debug("Superclass of class {} not ParameterizedType.", genericSubClass.getName());
        } else {
            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
            Type[] typeParameter = ((Class<?>) parameterizedType.getRawType()).getTypeParameters();
            Type[] actualTypeArgument = parameterizedType.getActualTypeArguments();
            for (int i = 0; i < typeParameter.length; i++) {
                log.debug("Checking for real type[{}] in map.", actualTypeArgument[i]);
                if (genericTypesMap.containsKey(actualTypeArgument[i])) {
                    Type actualType = genericTypesMap.get(actualTypeArgument[i]);
                    log.debug("Changing type[{}] to actualType[{}]", actualTypeArgument[i], actualType);
                    actualTypeArgument[i] = actualType;
                }
                log.debug("Adding actual type for type[{}] as type[{}] to map.", typeParameter[i],
                        actualTypeArgument[i]);
                genericTypesMap.put(typeParameter[i], actualTypeArgument[i]);
            }
        }
    }

    public static List<Annotation> getClassAnnotations(Class<?> clazz){

        return Arrays.asList(clazz.getAnnotations());
    }

    /**
     * Retrieve field annotated with @Id
     *
     * @param className
     *            Name of the class to reflect over
     * @return Entity's ID field
     * @throws ClassNotFoundException
     *             On exception
     */
    public static Field getIdField(String className) throws ClassNotFoundException {

        Class<?> entityClass = Class.forName(className);
        return getIdField(entityClass);
    }

    /**
     * Retrieve field annotated with @Id
     *
     * @param entityClass
     * @return Entity's ID field
     */
    public static Field getIdField(Class<?> entityClass) {

        Field ret = null;
        List<Field> fields = getAllFields(entityClass);

        for (Field field : fields) {

            if (field.isAnnotationPresent(Id.class)) {
                ret = field;
                break;
            }

        }

        return ret;
    }

}
