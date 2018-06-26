package com.ayida.cms.api.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ayida.cms.entity.api.ApiCollectedEntity;
import com.ayida.cms.entity.api.ApiMessageEntity;
import com.ayida.cms.entity.api.ApiUserEntity;
import com.ayida.cms.entity.doctor.Doctor;
import com.ayida.cms.entity.user.User;
import com.ayida.cms.service.AuthenticationService;
import com.ayida.cms.service.DoctorService;
import com.ayida.cms.service.UserService;
import com.ayida.common.constant.Constants;
import com.ayida.common.mybatis.Pager;
import com.ayida.common.util.PageUtils;
import com.ayida.common.util.RandomUtils;
import com.ayida.common.util.RequestUtils;
import com.ayida.common.util.ResponseUtils;
import com.ayida.common.util.ValidationUtils;
import com.ayida.common.util.YunTongXunMessageSendUtils;
import com.ayida.common.web.SessionProvider;
import com.ayida.core.web.util.FrontUtils;

import static com.ayida.cms.service.impl.AuthenticationServiceImpl.AUTH_KEY;

@Controller
@RequestMapping(value = "/api/member/")
public class MemberAPI
{
	private static final String USERNAME_EXISTS = "alreadyRegistered";

	private static final String SUCCESS = "success";

	private static final String ERROR_PHONE = "errorPhoneNumber";

	private static final String AUTHC_FAILED = "authenticationFail";

	private static final String UPDATE_SUCCESS = "updateSuccess";

	private static final String UPDATE_ERROR = "updateFail";

	private static final String COLLECTION_FAILED = "collectionFail";

	private static final String COLLECTION_OK = "collectionSuccess";

	private static final String CANCEL_OK = "cancelSuccess";

	private static final Logger log = LoggerFactory.getLogger(MemberAPI.class);

	/**
	 * 短信验证码发送，并将验证码发送到客户端保存，待验证
	 * 
	 * @param request
	 * @param response
	 * @param phone
	 */
	@RequestMapping(value = "sendMsg.jspx")
	public void sendMessage(HttpServletRequest request,
			HttpServletResponse response, String phone)
	{
		// check the phone number
		if (!ValidationUtils.phoneValidate(phone))
		{
			ResponseUtils.renderText(response, ERROR_PHONE);
		}
		// 生成六位随机验证码
		String random = RandomUtils.getRandom();
		// 发送短信验证码
		YunTongXunMessageSendUtils.sendMessage(phone, random);
		// 将验证码发送到客户端隐藏保存
		JSONObject obj = new JSONObject();
		obj.put("code", random);
		ResponseUtils.renderJson(response, obj.toString());
	}

	/**
	 * 校验用户名
	 * 
	 * @param request
	 * @param phone
	 * @param response
	 */
	@RequestMapping(value = "usernameValidation.jspx", method = RequestMethod.POST)
	public void usernameValidete(HttpServletRequest request, String phone,
			HttpServletResponse response)
	{
		// check the user, see whether it exists or not
		User user = userService.findByUsername(phone);
		JSONObject obj = new JSONObject();
		if (null != user)
		{
			obj.put("exist", true);
			ResponseUtils.renderJson(response, obj.toString());
		}
		else
		{
			obj.put("exist", false);
			ResponseUtils.renderJson(response, obj.toString());
		}
	}

	/**
	 * register api
	 * 
	 * @param request
	 * @param phone
	 * @param password
	 * @param messageCode
	 * @return
	 */
	@RequestMapping(value = "register.jspx", method = RequestMethod.POST)
	public ResponseEntity<String> register(HttpServletRequest request,
			String phone, String password, String messageCode)
	{
		ApiMessageEntity entity = new ApiMessageEntity();
		// check the phone number
		if (!ValidationUtils.phoneValidate(phone))
		{
			entity.setCode(Constants.FAILD);
			entity.setText(ERROR_PHONE);
			return new ResponseEntity<String>(ResponseUtils.getJson(entity),
					ResponseUtils.getJsonHttpHeaders(), HttpStatus.OK);
		}
		// check the user, see whether it exists or not
		User user = userService.findByUsername(phone);
		if (null != user)
		{
			entity.setCode(Constants.FAILD);
			entity.setText(USERNAME_EXISTS);
			return new ResponseEntity<String>(ResponseUtils.getJson(entity),
					ResponseUtils.getJsonHttpHeaders(), HttpStatus.OK);
		}
		userService.save(phone, password, phone,
				RequestUtils.getIpAddr(request));
		entity.setCode(Constants.SUCCESS);
		entity.setText(SUCCESS);
		return new ResponseEntity<String>(ResponseUtils.getJson(entity),
				ResponseUtils.getJsonHttpHeaders(), HttpStatus.OK);
	}

	/**
	 * user login api
	 * 
	 * @param request
	 * @param response
	 * @param phone
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "login.jspx", method = RequestMethod.POST)
	public ResponseEntity<String> login(HttpServletRequest request,
			HttpServletResponse response, String phone, String password)
	{
		// check the phone number
		if (!ValidationUtils.phoneValidate(phone))
		{
			ApiMessageEntity apiEntity = new ApiMessageEntity();
			apiEntity.setCode(Constants.FAILD);
			apiEntity.setText(ERROR_PHONE);
			return new ResponseEntity<String>(ResponseUtils.getJson(apiEntity),
					ResponseUtils.getJsonHttpHeaders(), HttpStatus.OK);
		}
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(phone, password);
		try
		{
			subject.login(token);
			// 当验证登录成功后，将用户的认证基本信息入库
			authcService.login(phone, password, phone, response, request,
					session);
		}
		catch (AuthenticationException e)
		{
			log.error("Login AuthenticationException" + e.getMessage());
			ApiMessageEntity apiEntity = new ApiMessageEntity();
			apiEntity.setCode(Constants.FAILD);
			apiEntity.setText(AUTHC_FAILED);
			return new ResponseEntity<String>(ResponseUtils.getJson(apiEntity),
					ResponseUtils.getJsonHttpHeaders(), HttpStatus.OK);
		}
		ApiUserEntity entity = new ApiUserEntity();
		entity.setCode(Constants.SUCCESS);
		entity.setText(SUCCESS);
		entity.setUser(userService.findByUsername(phone));
		return new ResponseEntity<String>(ResponseUtils.getJson(entity),
				ResponseUtils.getJsonHttpHeaders(), HttpStatus.OK);
	}

	/**
	 * 退出登录
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "logout.jspx")
	public String logout(HttpServletRequest request,
			HttpServletResponse response)
	{
		Integer authId = (Integer) session.getAttribute(request, AUTH_KEY);
		if (null != authId)
		{
			authcService.deleteOneAuthentication(authId);
			session.logOut(request, response);
		}
		return "redirect:/index.html";
	}

	/**
	 * 重置密码api
	 * 
	 * @param newPassword
	 * @return
	 */
	@RequestMapping(value = "resetPassword.jspx", method = RequestMethod.POST)
	public ResponseEntity<String> resetPassword(HttpServletRequest request, String phone,
			String password)
	{
		ApiMessageEntity entity = new ApiMessageEntity();
		User user = userService.findByUsername(phone);
		if (null != user)
		{
			userService.updateUserPassword(user.getId(), password);
			entity.setCode(Constants.SUCCESS);
			entity.setText(UPDATE_SUCCESS);
			return new ResponseEntity<String>(ResponseUtils.getJson(entity),
					ResponseUtils.getJsonHttpHeaders(), HttpStatus.OK);
		}
		entity.setCode(Constants.FAILD);
		entity.setText(UPDATE_ERROR);
		return new ResponseEntity<String>(ResponseUtils.getJson(entity),
				ResponseUtils.getJsonHttpHeaders(), HttpStatus.OK);
	}

	/**
	 * 用户收藏医生 api
	 * 
	 * @param phone
	 * @param doctorId
	 * @return
	 */
	@RequestMapping(value = "doctorCollection.jspx")
	public ResponseEntity<String> collection(Integer userId, Integer doctorId,
			boolean collect)
	{
		ApiMessageEntity entity = new ApiMessageEntity();
		User user = userService.findById(userId);
		Doctor doctor = docService.findById(doctorId);
		if (null == user || null == doctor)
		{
			entity.setCode(Constants.FAILD);
			entity.setText(COLLECTION_FAILED);
			return new ResponseEntity<String>(ResponseUtils.getJson(entity),
					ResponseUtils.getJsonHttpHeaders(), HttpStatus.OK);
		}
		if (collect)
		{
			// 检查是否已经收藏
			collectionStatusCheck(userId, doctorId);
			userService.collection(user.getId(), doctor.getId());
			entity.setCode(Constants.SUCCESS);
			entity.setText(COLLECTION_OK);
			return new ResponseEntity<String>(ResponseUtils.getJson(entity),
					ResponseUtils.getJsonHttpHeaders(), HttpStatus.OK);
		}
		else
		{
			userService.cancelCollection(user.getId(), doctor.getId());
			entity.setCode(Constants.SUCCESS);
			entity.setText(CANCEL_OK);
			return new ResponseEntity<String>(ResponseUtils.getJson(entity),
					ResponseUtils.getJsonHttpHeaders(), HttpStatus.OK);
		}
	}

	/**
	 * 我的收藏列表
	 * 
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "myCollections.jspx")
	public ResponseEntity<String> myCollections(Integer userId, Integer pageNo,
			Integer pageSize)
	{
		Pager<Doctor> page = docService.getCollectedDoctorsByUserId(userId,
				PageUtils.cpn(pageNo), PageUtils.cps(pageSize));
		return new ResponseEntity<String>(ResponseUtils.getJson(page),
				ResponseUtils.getJsonHttpHeaders(), HttpStatus.OK);
	}

	/**
	 * web端我的收藏
	 * 
	 * @param request
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "web/myCollections.jspx", method = RequestMethod.GET)
	public String webCollections(HttpServletRequest request, Integer userId,
			Integer pageNo, Integer pageSize, Model model)
	{
		Pager<Doctor> page = docService.getCollectedDoctorsByUserId(userId,
				PageUtils.cpn(pageNo), PageUtils.cps(pageSize));
		User user = userService.findById(userId);
		model.addAttribute("page", page);
		model.addAttribute("user", user);
		FrontUtils.frontData(request, model);
		return "front/member/my_collections";
	}

	/**
	 * 判断是否收藏
	 * 
	 * @param userId
	 * @param doctorId
	 * @return
	 */
	@RequestMapping(value = "isCollectedOrNot.jspx")
	public ResponseEntity<String> collectionStatusCheck(Integer userId,
			Integer doctorId)
	{
		Doctor bean = docService.isCollected(userId, doctorId);
		ApiCollectedEntity entity = new ApiCollectedEntity();
		if (null != bean)
		{
			entity = new ApiCollectedEntity();
			entity.setCode(Constants.FAILD);
			entity.setCollected(true);
			return new ResponseEntity<String>(ResponseUtils.getJson(entity),
					ResponseUtils.getJsonHttpHeaders(), HttpStatus.OK);
		}
		entity = new ApiCollectedEntity();
		entity.setCode(Constants.SUCCESS);
		entity.setCollected(false);
		return new ResponseEntity<String>(ResponseUtils.getJson(entity),
				ResponseUtils.getJsonHttpHeaders(), HttpStatus.OK);
	}

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationService authcService;

	@Autowired
	private DoctorService docService;

	@Autowired
	private SessionProvider session;
}
