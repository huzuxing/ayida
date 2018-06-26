package com.ayida.common.util;

import java.util.Random;

public class RandomUtils
{
	/**
	 * 生成6位数字的短信验证码
	 * 
	 * @return
	 */
	public static String getRandom()
	{
		Random rand = new Random();
		String randStr = Math.abs(rand.nextInt() % 1000000) + "";
		return randStr.trim();
	}
}
