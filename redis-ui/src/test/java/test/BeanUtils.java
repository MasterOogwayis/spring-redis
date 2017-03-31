package test;

import org.apache.commons.beanutils.BeanUtilsBean;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author ZhangShaowei on 2017/3/13 14:44
 */

public class BeanUtils {
    public BeanUtils() {
    }

    public static void copyNotNullProperties(Object dest, Object orig, String... excludeProperties) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        copyProperties(dest, orig, false, excludeProperties);
    }

    public static void copyProperties(Object dest, Object orig, String... excludeProperties) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        copyProperties(dest, orig, true, excludeProperties);
    }

    private static void copyProperties(Object dest, Object orig, boolean isCopyNull, String... excludeProperties) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        if (dest == null) {
            throw new IllegalArgumentException("No destination bean specified");
        } else if (orig == null) {
            throw new IllegalArgumentException("No origin bean specified");
        } else {
            PropertyDescriptor[] origDescriptors = BeanUtilsBean.getInstance().getPropertyUtils().getPropertyDescriptors(orig);

            for (int i = 0; i < origDescriptors.length; ++i) {
                String name = origDescriptors[i].getName();
                if (!"class".equals(name)) {
                    if (null != excludeProperties && excludeProperties.length > 0) {
                        for (int e = 0; e < excludeProperties.length; ++e) {
                            if (excludeProperties[e].equals(name)) {
                                ;
                            }
                        }
                    }

                    try {
                        Object var9 = BeanUtilsBean.getInstance().getPropertyUtils().getSimpleProperty(orig, name);
                        if (isCopyNull || null != var9) {
                            org.apache.commons.beanutils.BeanUtils.copyProperty(dest, name, var9);
                        }
                    } catch (NoSuchMethodException var8) {
                        ;
                    }
                }
            }

        }
    }
}
