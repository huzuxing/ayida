package com.ayida.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 验证辅助类
 * @author John Hu
 *
 */
/**
 * @author John Hu
 *
 */
public class ValidationUtils
{
	/**
	 * 电话号码正确性验证
	 * @param phone
	 * @return
	 */
	public static boolean phoneValidate(String phone)
	{
		String reg = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
		Pattern pat = Pattern.compile(reg);
		Matcher match = pat.matcher(phone);
		return match.matches();
	}
}
