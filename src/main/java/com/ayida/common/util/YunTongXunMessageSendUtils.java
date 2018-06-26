package com.ayida.common.util;

import java.util.HashMap;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloopen.rest.sdk.CCPRestSmsSDK;

public class YunTongXunMessageSendUtils
{
	private static final Logger log = LoggerFactory
			.getLogger(YunTongXunMessageSendUtils.class);

	/**
	 * 短信发送成功状态码
	 */
	private static final String SUCCESS = "000000";

	private static final String STATUS = "statusCode";

	/**
	 * 云通讯包体名
	 */
	private static final String DATA = "data";

	/**
	 * 状态提示内容
	 */
	private static final String TEXT = "statusMsg";

	/**
	 * 账户ID
	 */
	private static final String ACCOUNT_SID = "8a48b5514f1702fd014f1be8d16206c6";

	/**
	 * 认证鉴权
	 */
	private static final String AUTH_TOKEN = "fdb2448f4db24e99a80ed8c4073d4a8a";

	/**
	 * 应用ID
	 */
	private static final String APP_ID = "aaf98f894f16fdb7014f1be95fdb0716";

	/**
	 * 短信模板id
	 */
	private static final String TEMPLATE_ID = "1";

	/**
	 * 接口地址
	 */
	private static final String ADDRESS = "sandboxapp.cloopen.com";

	/**
	 * 接口地址端口
	 */
	private static final String PORT = "8883";

	/**
	 * 自定义字符类型 验证码 有效时间
	 */
	private static final String TIME = "1";

	@SuppressWarnings("unchecked")
	public static void sendMessage(String phone, String text)
	{
		HashMap<String, Object> result = null;
		CCPRestSmsSDK restApi = new CCPRestSmsSDK();
		restApi.init(ADDRESS, PORT);
		/**
		 * 所有数据均为测试，生产时再换
		 */
		restApi.setAccount(ACCOUNT_SID, AUTH_TOKEN);
		// 测试appId
		restApi.setAppId(APP_ID);

		result = restApi.sendTemplateSMS(phone, TEMPLATE_ID, new String[]{text,
				TIME});

		if (SUCCESS.equals(result.get(STATUS)))
		{
			/** 正常返回输出包体信息（map） **/
			HashMap<String, Object> data = (HashMap<String, Object>) result
					.get(DATA);
			Set<String> keySet = data.keySet();
			keySet.forEach(key -> {
				Object obj = data.get(key);
			});
		}
		else
		{
			/** 记录异常信息 **/
			log.error("错误码为：" + result.get(STATUS) + "  错误信息为："
					+ result.get(TEXT));
		}
	}
}
