package com.sds.metac.vo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;


public abstract class AbstractVO {
	
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		Field[] fields = this.getClass().getDeclaredFields();
		
		for (Field field : fields) {
			if (sb.length() != 0) {
				sb.append(", ");
			}
			
			String fieldName = field.getName();
			Object fieldValue = this.getValue(fieldName);
			
			if (fieldValue == null) {
				sb.append(fieldName);
				sb.append("=");
				sb.append(fieldValue);
			}
			else if (fieldValue instanceof Collection) {
				Collection<?> c = (Collection<?>) fieldValue;
				StringBuffer sbTemp = new StringBuffer();
				for (Object o : c) {
					if (sbTemp.length() != 0) {
						sbTemp.append(", ");
					}
					sbTemp.append(o.toString());
				}			
				sb.append(fieldName);
				sb.append("=");
				sb.append("[");
				sb.append(sbTemp.toString());
				sb.append("]");
			}
			else {
				sb.append(fieldName);
				sb.append("=");
				sb.append(fieldValue.toString());
			}
		}
		sb.insert(0, "{");
		sb.append("}");
		return sb.toString();
	}
	
	private Object getValue(String fieldName) {
		try {
			Method m =  this.getClass().getDeclaredMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
			return m.invoke(this);
		} catch (Exception e) {
			return null;
		}
	}
}
