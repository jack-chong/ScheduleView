package com.jack.schedule.utlis;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者: jack(黄冲)
 * 邮箱: 907755845@qq.com
 * create on 2018/6/20 09:50
 */

public class ReflexUtils {

    public static void setField(Object obj, String field, Object vlaue){
        setField(obj, obj.getClass(), field, vlaue);
    }

    public static void setField(Object obj, Class cls, String field, Object vlaue){
        try {
            Field f = cls.getDeclaredField(field);
            f.setAccessible(true);
            f.set(obj, vlaue);
        } catch (NoSuchFieldException e) {
            if (cls.getSuperclass() != null && cls.getSuperclass() != Object.class){
                setField(obj, cls.getSuperclass(), field, vlaue);
                return;
            }
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Object getField(Object obj, String field) {
        return getField(obj, obj.getClass(), field);
    }


    public static Object getField(Object obj, Class cls, String field){
        try {
            Field f = cls.getDeclaredField(field);
            f.setAccessible(true);
            return f.get(obj);
        } catch (NoSuchFieldException e) {
            if (cls.getSuperclass() != null && cls.getSuperclass() != Object.class){
                return getField(obj, cls.getSuperclass(), field);
            }
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     *
     * @param obj 对象
     * @param cls  对象class
     * @param method 方法名
     * @param isObj 是否执行到Objcet
     * @param vlaue 方法参数
     * @param <T>
     * @return
     */
    public static <T> T getMethod(Object obj, Class cls, String method, boolean isObj, Object... vlaue){
        try {
            Class[] classes = new Class[vlaue.length];
            for (int i = 0; i < vlaue.length; i++) {
                classes[i] = vlaue[i] == null ? Object.class : vlaue[i].getClass();
            }
            Method m = cls.getDeclaredMethod(method, classes);
            m.setAccessible(true);
            return (T) m.invoke(obj, vlaue);

        } catch (NoSuchMethodException e) {
            if (cls.getSuperclass() != null && (isObj || cls.getSuperclass() != Object.class)){
                return setMethod(obj, cls.getSuperclass(), method, isObj, vlaue);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T setMethod(Object obj, String method, boolean isObj, Object... vlaue){
        return setMethod(obj, obj.getClass(), method, isObj, vlaue);
    }

    /**
     *
     * @param obj 对象
     * @param cls  对象class
     * @param method 方法名
     * @param isObj 是否执行到Objcet
     * @param vlaue 方法参数
     * @param <T>
     * @return
     */
    public static <T> T setMethod(Object obj, Class cls, String method, boolean isObj, Object... vlaue){
        try {
            Class[] classes = new Class[vlaue.length];
            for (int i = 0; i < vlaue.length; i++) {
                classes[i] = vlaue[i] == null ? Object.class : vlaue[i].getClass();
            }
            Method m = cls.getDeclaredMethod(method, classes);
            m.setAccessible(true);
            return (T) m.invoke(obj, vlaue);

        } catch (NoSuchMethodException e) {
            if (cls.getSuperclass() != null && (isObj || cls.getSuperclass() != Object.class)){
                return setMethod(obj, cls.getSuperclass(), method, isObj, vlaue);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T clone(T t){
        return setMethod(t, "clone", true);
    }

    public static <T> List<T> clone(List<T> t){
        List<T> ts = new ArrayList<>();
        for (T t1 : t) {
            ts.add((T) setMethod(t1, "clone", true));
        }
        return ts;
    }

}
