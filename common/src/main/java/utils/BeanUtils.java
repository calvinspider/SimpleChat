package utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanUtils {
	/**
     * Map集合对象转化成 JavaBean集合对象
     * 
     * @param clazz JavaBean实例对象
     * @param mapList Map数据集对象
     * @return
     */
    public static <T> List<T> map2Java(Class<T> clazz, List<Map<String, Object>> mapList) {
        if(mapList == null || mapList.isEmpty()){
            return null;
        }
        List<T> objectList = new ArrayList<T>();
        
        T object = null;
        for(Map<String, Object> map : mapList){
            if(map != null){
                object = map2Java(clazz, map);
                objectList.add(object);
            }
        }
        return objectList;
        
    }
    
    /**
     * Map对象转化成 JavaBean对象
     * 
     * @param clazz JavaBean实例对象
     * @param map Map对象
     * @return
     */
    @SuppressWarnings("unchecked")
	public static <T> T map2Java(Class<T> clazz, Map<String, Object> map) {
        try {
            // 获取javaBean属性
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            // 创建 JavaBean 对象
            Object obj = clazz.newInstance();

            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            if (propertyDescriptors != null && propertyDescriptors.length > 0) {
                String propertyName = null; // javaBean属性名
                Object propertyValue = null; // javaBean属性值
                for (PropertyDescriptor pd : propertyDescriptors) {
                    propertyName = pd.getName();
                    if (map.containsKey(propertyName)) {
                        propertyValue = map.get(propertyName);
                        pd.getWriteMethod().invoke(obj, new Object[] { propertyValue });
                    }
                }
                return (T) obj;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * JavaBean对象转化成Map对象
     * 
     * @param javaBean
     * @return
     */
    public static Map<String, Object> java2Map(Object javaBean) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            // 获取javaBean属性
            BeanInfo beanInfo = Introspector.getBeanInfo(javaBean.getClass());

            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            if (propertyDescriptors != null && propertyDescriptors.length > 0) {
                String propertyName = null; // javaBean属性名
                Object propertyValue = null; // javaBean属性值
                for (PropertyDescriptor pd : propertyDescriptors) {
                    propertyName = pd.getName();
                    if (!propertyName.equals("class")) {
                        Method readMethod = pd.getReadMethod();
                        propertyValue = readMethod.invoke(javaBean, new Object[0]);
                        map.put(propertyName, propertyValue);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }  
        return map;
    }
}
