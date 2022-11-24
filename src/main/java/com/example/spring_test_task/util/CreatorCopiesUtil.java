package com.example.spring_test_task.util;

import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

@UtilityClass
public class CreatorCopiesUtil {

    public static String[] getNullPropertyNames (Object objectNew) {

        BeanWrapper source = new BeanWrapperImpl(objectNew);
        java.beans.PropertyDescriptor[] propertyDescriptor = source.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();

        for(java.beans.PropertyDescriptor pd : propertyDescriptor) {

            Object srcValue = source.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static void copyProperties(Object objectNew, Object objectOld) {
        BeanUtils.copyProperties(objectNew, objectOld, getNullPropertyNames(objectNew));
    }
}
