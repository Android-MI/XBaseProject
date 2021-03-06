package com.mi.xproject.common;

import java.lang.reflect.ParameterizedType;

/**
 * 感谢，少写了很多的重复性质的代码。
 */
@SuppressWarnings("unchecked")
public class CreateObjUtil {

    /**
     * 获取T类型然后自动new出对象
     * @param o
     * @param i
     * @param <T>
     * @return
     */
    public static <T> T getT(Object o, int i) {

        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }


}
