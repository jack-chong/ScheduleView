package com.jack.schedule.utlis;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者: jack(黄冲)
 * 邮箱: 907755845@qq.com
 * create on 2018/2/7 20:17
 */

public class ListUtils {

    public static List<String> getStrList(int start, int end){
        if (end - start < 0 ){
            return null;
        }
        List<String> list = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            list.add(i < 10 ? "0" + i : i + "");
        }
        return list;
    }
    private static final String[] TIME_NUM = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09"};

    public static String[] getStrArray(int start, int end){
        if (end - start < 0 ){
            throw new IllegalStateException("end 不能小于 start");
        }
        String[] arr = new String[end - start];

        for (int i = 0; i < end - start; i++) {
            arr[i] = String.valueOf(getFixNum(start + i));
        }
        return arr;
    }

    private static String getFixNum(int timeNum) {
        return timeNum >= 0 && timeNum < 10 ? TIME_NUM[timeNum] : String.valueOf(timeNum);
    }

    public static <T> T[] toArray(List<T> list){
        if (list == null || list.size() == 0) return null;
        T[] ts = (T[]) Array.newInstance(list.get(0).getClass(), list.size());

        for (int i = 0; i < list.size(); i++) {
            ts[i] = list.get(i);
        }
        return ts;
    }


    public static int[] substring(int[] data, int start, int len) {
        if (start > len){
            throw new IllegalStateException("len 不能小于 start");
        }
        int count = len - start > data.length ? data.length : len - start;
        int[] ints = new int[len - start];
        for (int i = start; i < count; i++) {
            ints[i] = data[i];
        }
        return ints;
    }

    public static void fill(Object[] obj, Object val){
        for (int i = 0; i < obj.length; i++) {
            obj[i] = ReflexUtils.clone(val);
        }
    }

    /**
     * 去重
     * @param list
     * @param name 条件, 字段名
     */
    public static <T> void distinct(List<T> list, String name) {
        if (list == null || list.size() == 0) return;
        try {
            for (int i = 0; i < list.size(); i++) {
                Field field1 = list.get(i).getClass().getDeclaredField(name);
                field1.setAccessible(true);
                Object val1 = field1.get(list.get(i));
                if (val1 == null) continue;
                for (int j = i + 1; j < list.size(); j++) {
                    Field field2 = list.get(j).getClass().getDeclaredField(name);
                    field2.setAccessible(true);
                    Object val2 = field2.get(list.get(j));
                    if (val2 ==  null) continue;
                    if (val1.equals(val2)) {
                        list.remove(j);
                        j--;
                    }
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    /**
     * 排序, 默认是从小到大
     * @param list
     * @param name 字段名
     * @param <T>
     * @return
     */
    public static <T> List<T> sort(List<T> list, String name){
        return sort(list, name, false);
    }

    public static <T> List<T> sort(List<T> list, String name, boolean method){
        return sort(list, name, method, false);
    }


    /**
     * 排序
     * @param list
     * @param name 字段名
     * @param <T>
     * @return
     */
    public static <T> List<T> sort(List<T> list, String name, boolean method, boolean reversal){
        if (list == null) return null;

        if (list.size() <= 1) return list;

        Collections.sort(list, (o1, o2) -> {
            Object field1;
            Object field2;

            if (method){
                field1 = ReflexUtils.getMethod(o1, o1.getClass(), name, false);
                field2 = ReflexUtils.getMethod(o2, o2.getClass(), name, false);
            }else {
                field1 = ReflexUtils.getField(o1, name);
                field2 = ReflexUtils.getField(o2, name);;
            }

            if (field1 == field2) return 0;

            try{
                double val1 = instanceType(field1);
                double val2 = instanceType(field2);

                if (val1 == val2) return 0;

                if (reversal && val1 > val2){
                    return -1;
                }

                if (val1 > val2) {
                    return 1;
                }

            }catch (Exception e){}

            return reversal ? 1 : -1;
        });

        return list;
    }

    private static double instanceType(Object object){
        double value = 0;
        if (object instanceof String){
            value = Double.parseDouble((String) object);
        }else if (object instanceof Integer){
            value = ((Integer) object) + 0.0;
        }else if (object instanceof Float){
            value = ((Float) object) + 0.0;
        }else if (object instanceof Short){
            value = ((Short) object) + 0.0;
        }else if (object instanceof Byte){
            value = ((Byte) object) + 0.0;
        }
        return value;
    }



    public static <T> List<List<T>> split(List<T> list, String name){
        return split(list, name, false);
    }


    /**
     * 切割list, 按照重复的字段名进行切割, 将字段值相同的对象切割为一个list
     * @param list
     * @param name 字段名
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> split(List<T> list, String name, boolean method){
        if (list == null) return null;

        List<List<T>> listList = new ArrayList<>();
        Map<Object, List<T>> map = new LinkedHashMap<>();

        for (int i = 0; i < list.size(); i++) {
            try {

                Object val1 = null;
                if (method){
                    Method method1 = list.get(i).getClass().getDeclaredMethod(name);
                    method1.setAccessible(true);
                    val1 = method1.invoke(list.get(i));
                }else {
                    Field field1 = list.get(i).getClass().getDeclaredField(name);
                    field1.setAccessible(true);
                    val1 = field1.get(list.get(i));
                }

                List<T> tList = map.get(val1);

                if (tList != null){
                    continue;
                }
                tList = new ArrayList<>();
                tList.add(list.get(i));
                map.put(val1, tList);

                for (int j = i + 1; j < list.size(); j++) {
                    Object val2 = null;
                    if (method){
                        Method method2 = list.get(j).getClass().getDeclaredMethod(name);
                        method2.setAccessible(true);
                        val2 = method2.invoke(list.get(j));
                    }else {
                        Field field2 = list.get(j).getClass().getDeclaredField(name);
                        field2.setAccessible(true);
                        val2 = field2.get(list.get(j));
                    }
                    if (val1 == val2){
                        tList.add(list.get(j));
                        continue;
                    }
                    if (val1 != null && val1.equals(val2)){
                        tList.add(list.get(j));
                    }
                }

            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        for (Map.Entry<Object, List<T>> entry : map.entrySet()) {
            listList.add(entry.getValue());
        }
        return listList;
    }

    /**
     * is null or its size is 0
     * <p>
     * <pre>
     * isEmpty(null)   =   true;
     * isEmpty({})     =   true;
     * isEmpty({1})    =   false;
     * </pre>
     *
     * @param <V>
     * @param sourceList
     * @return if list is null or its size is 0, return true, else return false.
     */
    public static <V> boolean isEmpty(List<V> sourceList) {
        return (sourceList == null || sourceList.size() == 0);
    }
 }
