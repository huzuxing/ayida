package com.ayida.cms.action.admin;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ayida.cms.entity.department.Department;
import com.ayida.cms.entity.disease.Disease;
import com.ayida.cms.entity.doctor.Doctor;
import com.ayida.cms.entity.doctor.DoctorExt;
import com.ayida.cms.entity.doctor.SubProfessional;
import com.ayida.cms.entity.hospital.Hospital;
import com.ayida.cms.entity.hotword.SearchWord;
import com.ayida.cms.service.DepartmentService;
import com.ayida.cms.service.DiseaseService;
import com.ayida.cms.service.DoctorExtService;
import com.ayida.cms.service.DoctorService;
import com.ayida.cms.service.HospitalService;
import com.ayida.cms.service.RelativeSearchWordService;
import com.ayida.cms.service.SearchWordService;
import com.ayida.cms.service.SubProfessionalService;
import com.ayida.common.util.FileUtils;
import com.ayida.common.util.ResponseUtils;

@Controller
@RequestMapping(value = "/file/")
public class FileAct
{
	private static final Logger log = LoggerFactory.getLogger(FileAct.class);

	/**
	 * 0 表示disease标签
	 */
	private static final Integer DISEASE = 0;

	/**
	 * 1 表示亚专业标签
	 */
	private static final Integer SUBPROFESSIONAL = 1;

	/**
	 * 2 表示医生标签
	 */
	private static final Integer DOCTOR = 2;

	/**
	 * 3 表示医院标签
	 */
	private static final Integer HOSPITAL = 3;

	/**
	 * 4 表示科室标签
	 */
	private static final Integer DEPARTMENT = 4;

	/**
	 * 成功
	 */
	private static final String OK = "ok";

	/**
	 * 文件错误
	 */
	private static final String FILE_ERROR = "fileError";

	/**
	 * 上传错误
	 */
	private static final String UPLOAD_ERROR = "uploadError";

	/**
	 * 结果
	 */
	private static final String RESULT = "result";

	/**
	 * 格式表格导入
	 * 
	 * @param excel
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "department/excelImport.do", method = RequestMethod.POST)
	public void departmentUpload(@RequestParam MultipartFile excel,
			HttpServletResponse response) throws IOException
	{
		InputStream is = excel.getInputStream();
		String ext = FileUtils.getExts(excel.getOriginalFilename());
		Workbook workBook = FileUtils.getWorkBook(ext, is);
		JSONObject obj = null;
		if (null == workBook)
		{
			obj = new JSONObject();
			obj.put(RESULT, FILE_ERROR);
			ResponseUtils.renderJson(response, obj.toString());
			return;
		}
		int sheetNum = workBook.getNumberOfSheets();
		try
		{
			for (int i = 0; i < sheetNum; i++)
			{
				extractData(workBook.getSheetAt(i), DEPARTMENT,
						FileUtils.getFileName(excel.getOriginalFilename()));
			}
		}
		catch (Exception e)
		{
			log.error("data upload error:" + e.getMessage());
			obj = new JSONObject();
			obj.put(RESULT, UPLOAD_ERROR);
			ResponseUtils.renderJson(response, obj.toString());
			return;
		}
		finally
		{
			is.close();
		}
		obj = new JSONObject();
		obj.put(RESULT, OK);
		ResponseUtils.renderJson(response, obj.toString());
	}

	/**
	 * 医生表格导入
	 * 
	 * @param excel
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "doctor/excelImport.do", method = RequestMethod.POST)
	public void doctorUpload(@RequestParam MultipartFile excel,
			HttpServletResponse response) throws IOException
	{
		InputStream is = excel.getInputStream();
		String ext = FileUtils.getExts(excel.getOriginalFilename());
		Workbook workBook = FileUtils.getWorkBook(ext, is);
		JSONObject obj = null;
		if (null == workBook)
		{
			obj = new JSONObject();
			obj.put(RESULT, FILE_ERROR);
			ResponseUtils.renderJson(response, obj.toString());
			return;
		}
		int sheetNum = workBook.getNumberOfSheets();
		try
		{
			for (int i = 0; i < sheetNum; i++)
			{
				extractData(workBook.getSheetAt(i), DOCTOR,
						FileUtils.getFileName(excel.getOriginalFilename()));
			}
		}
		catch (Exception e)
		{
			log.error("data upload error:" + e.getMessage());
			obj = new JSONObject();
			obj.put(RESULT, UPLOAD_ERROR);
			ResponseUtils.renderJson(response, obj.toString());
			return;
		}
		finally
		{
			is.close();
		}
		obj = new JSONObject();
		obj.put(RESULT, OK);
		ResponseUtils.renderJson(response, obj.toString());
	}

	/**
	 * 疾病库excel导入
	 * 
	 * @param excel
	 * @param response
	 * @throws IOException
	 * @throws EncryptedDocumentException
	 * @throws InvalidFormatException
	 */
	@RequestMapping(value = "disease/excelImport.do", method = RequestMethod.POST)
	public void diseaseUpload(@RequestParam MultipartFile excel,
			HttpServletResponse response) throws IOException,
			EncryptedDocumentException, InvalidFormatException
	{
		InputStream is = excel.getInputStream();
		String ext = FileUtils.getExts(excel.getOriginalFilename());
		Workbook workBook = FileUtils.getWorkBook(ext, is);
		JSONObject obj = null;
		if (null == workBook)
		{
			obj = new JSONObject();
			obj.put(RESULT, FILE_ERROR);
			ResponseUtils.renderJson(response, obj.toString());
			return;
		}
		int sheetNum = workBook.getNumberOfSheets();
		try
		{
			for (int i = 0; i < sheetNum; i++)
			{
				extractData(workBook.getSheetAt(i), DISEASE,
						FileUtils.getFileName(excel.getOriginalFilename()));
			}
		}
		catch (Exception e)
		{
			log.error("data upload error:" + e.getMessage());
			obj = new JSONObject();
			obj.put(RESULT, UPLOAD_ERROR);
			ResponseUtils.renderJson(response, obj.toString());
			return;
		}
		finally
		{
			is.close();
		}
		obj = new JSONObject();
		obj.put(RESULT, OK);
		ResponseUtils.renderJson(response, obj.toString());
	}
	/**
	 * 解析excel数据的通用方法
	 * 
	 * @param sheet
	 * @param bean
	 */
	private void extractData(Sheet sheet, Integer tag, String fileName)
	{
		/**
		 * 疾病表上传，一个疾病表的表名就是一个亚专业，所以只需查询一次，获取亚专业ID
		 */
		Integer subId = null;
		if (DISEASE == tag)
		{
			SubProfessional bean = subProfessionalService.findByName(fileName);
			subId = bean.getId();
		}
		Row row = null;
		int i = 0;
		while (true)
		{
			/** 获取工作簿的每一行 **/
			row = sheet.getRow(i + 1);
			/** 当没有行时，默认已经没有物理行数，则终止循环 **/
			if (null == row)
				break;
			/** 当存在物理行数，但是空行，即存在改行，但该行内容为空时，跳出本次循环 **/
			// if (isBlankRow(row))
			// continue;
			try
			{
				/**
				 * 根据传入的不同标签，进行不同的存储
				 */
				if (DISEASE == tag)
				{
					Disease bean = new Disease();
					bean = FileUtils.extractExcelData(row, bean);
					bean = diseaseService.save(bean);
					/** 保存亚专业和疾病的关系 **/
					subProfessionalService.saveSubDisease(subId, bean.getId());
					/** 将疾病名保存到搜索表，便于搜索 **/
					SearchWord word = new SearchWord();
					word.setName(bean.getName());
					searchWordSerivce.save(word);
				}
				if (SUBPROFESSIONAL == tag)
				{
					SubProfessional bean = new SubProfessional();
					bean = FileUtils.extractExcelData(row, bean);
					subProfessionalService.save(bean);
				}
				if (DOCTOR == tag)
				{
					/** 上传Doctorexcel文件存在特殊的关联关系，反射不行 **/
					doctorDataExtract(row);
				}
				if (HOSPITAL == tag)
				{
					Hospital bean = new Hospital();
					bean = FileUtils.extractExcelData(row, bean);
					bean = hospitalService.save(bean);
				}
				if (DEPARTMENT == tag)
				{
					Department bean = new Department();
					bean = FileUtils.extractExcelData(row, bean);
					bean = departmentService.save(bean);
				}
			}
			catch (Exception e)
			{
				log.error("Cell Exeption: cell {" + e.getMessage() + "}");
				break;
			}
			i++;
		}
	}

	/**
	 * 解析医生单条信息
	 * 
	 * @param row
	 * @return
	 */
	private void doctorDataExtract(Row row)
	{
		if (null == row)
			return;
		Doctor bean = new Doctor();
		saveDoctorData(row, bean);
	}

	/**
	 * 保存Doctor信息
	 * 
	 * @param row
	 * @param bean
	 */
	private void saveDoctorData(Row row, Doctor bean)
	{
		Cell cell0 = row.getCell(0);
		cell0.setCellType(Cell.CELL_TYPE_STRING);
		bean.setName(cell0.getStringCellValue().trim());

		Cell cell1 = row.getCell(1);
		cell1.setCellType(Cell.CELL_TYPE_STRING);
		BigInteger phone = BigInteger.valueOf(Long.valueOf(cell1
				.getStringCellValue().trim()));
		bean.setPhone(phone);

		Cell cell2 = row.getCell(2);
		cell2.setCellType(Cell.CELL_TYPE_STRING);
		bean.setEmail(cell2.getStringCellValue().trim());

		Cell cell3 = row.getCell(3);
		cell3.setCellType(Cell.CELL_TYPE_STRING);
		bean.setNationality(cell3.getStringCellValue().trim());

		Cell cell4 = row.getCell(4);
		cell4.setCellType(Cell.CELL_TYPE_STRING);
		bean.setCardType(Integer.valueOf(cell4.getStringCellValue().trim()));

		Cell cell5 = row.getCell(5);
		cell5.setCellType(Cell.CELL_TYPE_STRING);
		bean.setCardId(cell5.getStringCellValue().trim());

		Cell cell6 = row.getCell(6);
		cell6.setCellType(Cell.CELL_TYPE_STRING);
		bean.setGender(Integer.valueOf(cell6.getStringCellValue().trim()));

		Cell cell7 = row.getCell(7);
		cell7.setCellType(Cell.CELL_TYPE_STRING);
		String degree = cell7.getStringCellValue().trim();
		bean.setDegree(degree);
		/** 入库保存 **/
		bean = doctorService.save(bean);
		/** 将医生姓名保存到搜索表，便于搜索 **/
		SearchWord word = new SearchWord();
		word.setName(bean.getName());
		searchWordSerivce.save(word);
		/** 保存DoctorExt **/
		DoctorExt extBean = new DoctorExt();
		extBean.setId(bean.getId());
		extBean.setName(bean.getName());
		extBean = saveDoctorExt(row, bean, extBean);
		/** 保存医生与亚专业、医院的关系 **/
		saveRelationships(row, bean, extBean);

	}

	/**
	 * 保存关联关系
	 * 
	 * @param row
	 * @param bean
	 */
	private void saveRelationships(Row row, Doctor bean, DoctorExt extBean)
	{
		/**
		 * 坐诊时间与医院相关，因此在这里解析，并保存医生与医院的关系
		 * 坐诊时间格式：医院:时间|上午&下午,时间|上午&下午;医院:时间|上午&下午,时间|上午&下午
		 * **/
		String[] seeTimes = extBean.getSeeTime().split(";");
		// 每个医生所包含的医院信息
		List<Hospital> hospitals = new ArrayList<Hospital>();
		for (String time : seeTimes)
		{
			Hospital hos = hospitalService.findByName(time.split(":")[0]);
			if (null != hos)
			{
				doctorService.saveDoctorHospital(bean.getId(), hos.getId());
				hospitals.add(hos);
			}
		}
		/** 保存医生的其他信息 **/
		doctorExtSerivce.save(extBean);

		Cell cell18 = row.getCell(18);
		cell18.setCellType(Cell.CELL_TYPE_STRING);
		StringBuilder diseaseStr = new StringBuilder();
		for (String name : cell18.getStringCellValue().trim().split(";"))
		{
			SubProfessional sub = subProfessionalService.findByName(name);
			if (null != sub)
			{
				doctorService.saveDoctorSubprofessional(bean.getId(),
						sub.getId());
				sub.getDiseases().forEach(disease -> {
					diseaseStr.append(disease.getName() + ",");
				});
			}
		}
		// 处理每个医生的医院的位置信息和医院属性信息，用','连接，便于分词
		StringBuilder cities = new StringBuilder();
		StringBuilder belongings = new StringBuilder();
		hospitals.forEach(hospital -> {
			cities.append(hospital.getCityName() + ",");
			belongings.append(hospital.getBelongings() + ",");
		});
		// 学历排序、职称等级处理，只在索引文件里，不入库，包括后台管理系统添加医生数据时也要这样处理
		Cell cell8 = row.getCell(8);
		cell8.setCellType(Cell.CELL_TYPE_STRING);
		Cell cell12 = row.getCell(12);
		cell12.setCellType(Cell.CELL_TYPE_STRING);
		relativeService.save(bean.getId(), bean.getName(),
				diseaseStr.toString(), cities.toString(),
				belongings.toString(),
				Integer.valueOf(cell12.getStringCellValue().trim()),
				Integer.valueOf(cell8.getStringCellValue().trim()));
	}

	/**
	 * 根据Doctor信息保存DoctorExt信息
	 * 
	 * @param row
	 * @param bean
	 */
	private DoctorExt saveDoctorExt(Row row, Doctor bean, DoctorExt extBean)
	{
		Cell cell9 = row.getCell(9);
		cell9.setCellType(Cell.CELL_TYPE_STRING);
		extBean.setJob(cell9.getStringCellValue().trim());

		Cell cell10 = row.getCell(10);
		cell10.setCellType(Cell.CELL_TYPE_STRING);
		Department department = departmentService.findByName(cell10
				.getStringCellValue().trim());
		extBean.setDepartmentId(department.getId());

		Cell cell11 = row.getCell(11);
		cell11.setCellType(Cell.CELL_TYPE_STRING);
		extBean.setProfessionalTitles(cell11.getStringCellValue().trim());

		Cell cell13 = row.getCell(13);
		cell13.setCellType(Cell.CELL_TYPE_STRING);
		extBean.setSkilledField(cell13.getStringCellValue().trim());

		Cell cell14 = row.getCell(14);
		cell14.setCellType(Cell.CELL_TYPE_STRING);
		extBean.setCertification(cell14.getStringCellValue().trim());

		Cell cell15 = row.getCell(15);
		cell15.setCellType(Cell.CELL_TYPE_STRING);
		extBean.setImg(cell15.getStringCellValue().trim());

		Cell cell16 = row.getCell(16);
		cell16.setCellType(Cell.CELL_TYPE_STRING);
		extBean.setComment(cell16.getStringCellValue().trim());

		Cell cell17 = row.getCell(17);
		cell17.setCellType(Cell.CELL_TYPE_STRING);
		String seeTime = cell17.getStringCellValue().trim();
		extBean.setSeeTime(seeTime);

		Cell cell19 = row.getCell(19);
		cell19.setCellType(Cell.CELL_TYPE_STRING);
		extBean.setDescription(cell19.getStringCellValue().trim());;
		return extBean;
	}

	/**
	 * 亚专业excel数据导入
	 * 
	 * @param excel
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "subprofessional/excelImport.do", method = RequestMethod.POST)
	public void subprofessionalUpload(@RequestParam MultipartFile excel,
			HttpServletResponse response) throws IOException
	{
		InputStream is = excel.getInputStream();
		String ext = FileUtils.getExts(excel.getOriginalFilename());
		Workbook workBook = FileUtils.getWorkBook(ext, is);
		JSONObject obj = null;
		if (null == workBook)
		{
			obj = new JSONObject();
			obj.put(RESULT, FILE_ERROR);
			ResponseUtils.renderJson(response, obj.toString());
			return;
		}
		int sheetNum = workBook.getNumberOfSheets();
		try
		{
			for (int i = 0; i < sheetNum; i++)
			{
				extractData(workBook.getSheetAt(i), SUBPROFESSIONAL,
						excel.getName());
			}
		}
		catch (Exception e)
		{
			log.error("data upload error:" + e.getMessage());
			obj = new JSONObject();
			obj.put(RESULT, UPLOAD_ERROR);
			ResponseUtils.renderJson(response, obj.toString());
			return;
		}
		finally
		{
			is.close();
		}
		obj = new JSONObject();
		obj.put(RESULT, OK);
		ResponseUtils.renderJson(response, obj.toString());
	}

	/**
	 * 医院信息excel 导入
	 * 
	 * @param excel
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "hospital/excelImport.do", method = RequestMethod.POST)
	public void hospitalUpload(@RequestParam MultipartFile excel,
			HttpServletResponse response) throws IOException
	{
		InputStream is = excel.getInputStream();
		String ext = FileUtils.getExts(excel.getOriginalFilename());
		Workbook workBook = FileUtils.getWorkBook(ext, is);
		JSONObject obj = null;
		if (null == workBook)
		{
			obj = new JSONObject();
			obj.put(RESULT, FILE_ERROR);
			ResponseUtils.renderJson(response, obj.toString());
			return;
		}
		int sheetNum = workBook.getNumberOfSheets();
		try
		{
			for (int i = 0; i < sheetNum; i++)
			{
				extractData(workBook.getSheetAt(i), HOSPITAL, excel.getName());
			}
		}
		catch (Exception e)
		{
			log.error("data upload error:" + e.getMessage());
			obj = new JSONObject();
			obj.put(RESULT, UPLOAD_ERROR);
			ResponseUtils.renderJson(response, obj.toString());
			return;
		}
		finally
		{
			is.close();
		}
		obj = new JSONObject();
		obj.put(RESULT, OK);
		ResponseUtils.renderJson(response, obj.toString());
	}

	@Autowired
	private DiseaseService diseaseService;

	@Autowired
	private RelativeSearchWordService relativeService;

	@Autowired
	private SubProfessionalService subProfessionalService;

	@Autowired
	private DoctorService doctorService;

	@Autowired
	private DoctorExtService doctorExtSerivce;

	@Autowired
	private HospitalService hospitalService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private SearchWordService searchWordSerivce;
}
