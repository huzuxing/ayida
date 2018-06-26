package com.ayida.test;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.ayida.common.util.RandomUtils;
import com.cloopen.rest.sdk.CCPRestSmsSDK;

public class MessageTest
{
	public static void main(String[] args)
	{
		HashMap<String, Object> result = null;
		CCPRestSmsSDK restApi = new CCPRestSmsSDK();
		restApi.init("sandboxapp.cloopen.com", "8883");
		/**
		 * 测试账号：8a48b5514f1702fd014f1be8d16206c6
		 * 
		 * auth token:fdb2448f4db24e99a80ed8c4073d4a8a
		 */
		restApi.setAccount("8a48b5514f1702fd014f1be8d16206c6", "fdb2448f4db24e99a80ed8c4073d4a8a");
		// 测试appId
		restApi.setAppId("aaf98f894f16fdb7014f1be95fdb0716");
		
		result = restApi.sendTemplateSMS("13808221061", "1", new String[]{RandomUtils.getRandom()});
		
		if ("000000".equals(result.get("statusCode")))
		{
			/**正常返回输出包体信息（map）**/
			HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
			Set<String> keySet = data.keySet();	
			keySet.forEach(key -> {
				Object obj = data.get(key);
				System.out.println(obj.toString());
			});
		}
		else
		{
			/**记录异常信息**/
			System.out.println(result.get("statusCode"));
		}
	}
	
}
