package cn.tool;

import java.util.Map;
import java.util.UUID;

import com.sun.org.apache.commons.beanutils.BeanUtils;
/**
 * 需要跟commons-beanutils.jar和commons-logging.jar一起使用。
 * 因为第二个方法需要使用到这两个jar包的类
 * @author Administrator
 *
 */
public class CommonUtil {
	/**
	 * 返回一个随机的uuid。大写的。已去除掉“-”
	 * @return
	 */
	public static String uuid(){
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
	/**
	 * 依赖：
	 * 	jar包：commons-beanutils
	 * 		  commons-logging
	 * 
	 *	把map的数据直接填充到JavaBean中。并返回bean
	 * 更新时间：2015/1/1
	 * @param <T>
	 * @param map
	 * @param clazz
	 * @return
	 */
	public static <T> T toBean(Map map ,Class<T> clazz){
		try {
			//1. 创建指定类型的实例
			T bean = clazz.newInstance();
			//2. 把数据封装到bean
			BeanUtils.populate(bean, map);
			//3. 返回JavaBean对象
			return bean;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 	
	}
}
