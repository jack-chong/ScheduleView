package com.jack.schedule.utlis;

import java.lang.reflect.Field;

/**
 * 作者: jack(黄冲)
 * 邮箱: 907755845@qq.com
 * create on 2018/12/10 11:51
 */

public class AutoSizeUtils {

    /**
     * 适配小写c开头的Number类型字段
     * @param target
     */
    public static void autoSize(Object target){

        Field[] fields = target.getClass().getDeclaredFields();
        for (Field field : fields) {

            if (field.getName().charAt(0) != 'c'){
                continue;
            }

            field.setAccessible(true);
            Class<?> type = field.getType();
            try {
                if (type == int.class){
                    field.setInt(target, AutoUtils.getScaleWidth(field.getInt(target)));
                }
                if (type == float.class){
                    field.setFloat(target, AutoUtils.getScaleWidth((int) field.getFloat(target)));
                }
                if (type == double.class){
                    field.setDouble(target, AutoUtils.getScaleWidth((int) field.getDouble(target)));
                }
            }catch (Exception e){
                throw new IllegalStateException("适配出错: " + e.getLocalizedMessage());
            }

        }
    }
}
