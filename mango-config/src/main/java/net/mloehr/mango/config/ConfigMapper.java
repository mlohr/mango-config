package net.mloehr.mango.config;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map.Entry;

import lombok.val;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigValue;

public class ConfigMapper {

	public static <T> void map(Config conf, String path, List<T> list,
			Class<T> clazz) {
		val nodeList = conf.getConfigList(path);
		for (val node : nodeList) {
			try {
				final T subject = clazz.newInstance();
				list.add(subject);
				mapObject(subject, clazz, node);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void map(Config conf, String path, Object model) {
		Class<? extends Object> clz = model.getClass();
		val node = conf.getConfig(path);
		mapObject(model, clz, node);
	}

	private static void mapObject(Object model, Class<? extends Object> clz,
			Config node) {
		Method[] methods = clz.getMethods();
		for (val entry : node.entrySet()) {
			val setter = getSetter(entry);
			for (val method : methods) {
				if (method.getName().equals(setter)) {
					invokeSetter(model, entry, method);
				}
			}
		}
	}

	private static java.lang.String getSetter(
			final Entry<String, ConfigValue> entry) {
		val key = entry.getKey();
		val setter = "set" + key.substring(0, 1).toUpperCase()
				+ key.substring(1);
		return setter;
	}

	private static void invokeSetter(Object model,
			Entry<String, ConfigValue> entry, Method method) {
		try {
			method.invoke(model, entry.getValue().unwrapped());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
