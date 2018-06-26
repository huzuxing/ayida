package com.ayida.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ayida.cms.entity.disease.Disease;

public class FileUtils
{
	private static final String XLS = "xls";

	private static final String XLSX = "xlsx";

	private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

	/**
	 * 获取文件的后缀
	 * 
	 * @param origFileName
	 * @return
	 */
	public static String getExts(String origFileName)
	{
		int index = origFileName.lastIndexOf(".");
		return origFileName.substring(index + 1, origFileName.length());
	}
	
	/**
	 * 获取文件名，除去后缀
	 * @param origFileName
	 * @return
	 */
	public static String getFileName(String origFileName) 
	{
		int index = origFileName.lastIndexOf(".");
		return origFileName.substring(0, index);
	}

	/**
	 * 获取work工作簿
	 * 
	 * @param ext
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static Workbook getWorkBook(String ext, InputStream is)
			throws IOException
	{
		Workbook workBook = null;
		if (XLS.equalsIgnoreCase(ext))
		{
			/** 返回excel2003以下的workbook **/
			workBook = new HSSFWorkbook(is);
		}
		else if (XLSX.equalsIgnoreCase(ext))
		{
			/** 针对excel 2007 + xml **/
			workBook = new XSSFWorkbook(is);
		}
		return workBook;
	}

	/**
	 * 利用java反射，获取属性，并解析excel表格信息， 同时赋值到java bean，最后返回给调用者，保存到数据库
	 * 
	 * @param <T>
	 * @param <T>
	 * @param sheet
	 * @param t
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 */
	public static <T> T extractExcelData(Row row, T o)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, InvocationTargetException
	{
		/** 动态获取类的对象 **/
		Class<? extends Object> clazz = o.getClass();
		/** 获取类的域集，即属性集，不能获取父类的属性集 **/
		Field[] fields = clazz.getDeclaredFields();
		/** 获取excel　Row的单元格数 **/
		int cellNum = row.getPhysicalNumberOfCells();
		Cell cell = null;
		try
		{
			for (int i = 0; i < cellNum; i++)
			{
				cell = row.getCell(i);
				/**将单元格的数据格式设置为string类型，再转换**/
				cell.setCellType(Cell.CELL_TYPE_STRING);
				String value = null;
				/**
				 * 这里剔除 serialVersionUID 和 id两个属性， serialVersionUID
				 * 类的序列化表示，不需要赋值 id 是在数据库添加的时候，自增长的
				 * **/
				String name = fields[i + 2].getName();
				/** 构造setter方法 **/
				String methodName = "set" + name.substring(0, 1).toUpperCase()
						+ name.substring(1);
				/** 获取域的类型 **/
				String type = fields[i + 2].getGenericType().toString();
				Method method = null;
				/**
				 * 以下是根据域的类型，获取对应的setter方法，并 用invoke方法赋值
				 */
				if ("class java.lang.Integer".equals(type))
				{
					value = cell.getStringCellValue();
					method = clazz.getMethod(methodName, Integer.class);
					method.invoke(o, Integer.valueOf(value));
				}
				if ("class java.lang.String".equals(type))
				{
					value = cell.getStringCellValue();
					method = clazz.getMethod(methodName, String.class);
					method.invoke(o, value);
				}
				if ("class java.lang.Double".equals(type))
				{
					value = cell.getStringCellValue();
					method = clazz.getMethod(methodName, Double.class);
					method.invoke(o, Double.valueOf(value));
				}
			}
		}
		catch (IllegalArgumentException e)
		{
			log.error("Illegal number format", e);
			throw new RuntimeException("reflection error...");
		}
		return o;
	}
}
