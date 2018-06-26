package com.ayida.login;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.jasper.tagplugins.jstl.core.Out;
import org.junit.Test;

public class LoginTest
{
	private static final String REMOTE_URL = "http://127.0.0.1:8080/ayida";
	protected String getUrl(String method)
	{
		return REMOTE_URL + method;
	}
	@Test
	public void testLogin()
	{
		try
		{
			String username = "username=huzuxing";
			URL url = new URL(getUrl("/login.do?username=huzuxing"));
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			//connection.setUseCaches(false);
			//connection.setRequestProperty("Content-Type", "application/octet-stream");
			connection.connect();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String str = null;
			while((str = reader.readLine()) != null) {
				System.out.println(str);
			}
			reader.close();
			int returnCode = connection.getResponseCode();
			System.out.println(returnCode);
			connection.disconnect();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
