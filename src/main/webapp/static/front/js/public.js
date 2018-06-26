/**
 * 登录和注册js代码（验证码）
 */
jQuery(document).ready(function($) {
	var base = $("input[name=base]").val().trim();
	// 搜索框自动补全
	$("#kw").autocomplete({
		source: base + "/api/search/ajaxSearch.jspx",
		delay: 100
	});
	$('.login').click(function() {
		$('.mask-register').fadeOut(100);
		$('.register-active').slideUp(200);
		$('.mask-forget').fadeOut(100);
		$('.forget-active').slideUp(200);
		$('.mask-login').fadeIn(100);
		$('.login-active').slideDown(200);
	})
	$('.theme-poptit .close').click(function() {
		$('.mask-login').fadeOut(100);
		$('.login-active').slideUp(200);
	});
})
/* 注册js代码 */
jQuery(document).ready(function($) {
	$('#register-active').click(function() {
		$('.mask-login').fadeOut(100);
		$('.login-active').slideUp(200);
		$('.mask-register').fadeIn(100);
		$('.register-active').slideDown(200);
	});
	$('.theme-register .close').click(function() {
		$('.mask-register').fadeOut(100);
		$('.register-active').slideUp(200);
	});
	/** 确认密码校验* */
	$("input[name=password_repeat]").blur(function() {
		var password = $("#register_password").val().trim();
		var repeatPass = $("input[name=password_repeat]").val().trim();
		if (password != repeatPass) {
			alert("两次密码输入不一致!");
		}
	});
	/** 确认密码校验* */
	$("input[name=forget_password_repeat]").blur(function() {
		var password = $("#forget_password").val().trim();
		var repeatPass = $(this).val().trim();
		if (password != repeatPass) {
			alert("两次密码输入不一致!");
		}
	});
	/**
	 * 用户名校验 *
	 */
	$("#register-phone").blur(function() {
		var phone = $(this).val().trim();
		var base = $("input[name=base]").val().trim();
		$.ajax({
			url : base + "/api/member/usernameValidation.jspx", // 目标地址
			type : "POST", // 用POST方式传输
			dataType : "JSON", // 数据格式:JSON
			data : "phone=" + phone,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			},
			success : function(data) {
				var result = eval("(" + data + ")");
				if (result.exist) {
					alert("该用户已被注册");
					$("#register-phone").focus();
				}
			}
		});
	});
	/** 找回密码手机号校验* */
	$("#forget-phone").blur(function() {
		var phone = $(this).val().trim();
		var base = $("input[name=base]").val().trim();
		$.ajax({
			url : base + "/api/member/usernameValidation.jspx", // 目标地址
			type : "POST", // 用POST方式传输
			dataType : "JSON", // 数据格式:JSON
			data : "phone=" + phone,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			},
			success : function(data) {
				var result = eval("(" + data + ")");
				if (!result.exist) {
					alert("该用户不存在!");
					$("#forget-phone").focus();
				}
			}
		});
	});
	/**更多条件选择**/
	$(".dropdown-menu li").click(function() {
		alert(123);
	});
});
// 条件检查
function checkConditions(flag) {
	if ("register" == flag) {
		var registerCode = $("input[name=registerCode]").val().trim();
		var code = $("input[name=code]").val().trim();
		// if ("" == code) {
		// alert("短信验证码不能为空!");
		// return false;
		// }
		// if (code != registerCode) {
		// alert("您输入的短信验证码不正确!");
		// return false;
		// }
	} else if ("login" == flag) {
		var phone = phone_check($("#login-phone").val().trim());
		if (!phone) {
			alert("请输入正确的手机号");
			return phone;
		}
		if ("" == $("#login_password").val()) {
			alert("密码不能为空");
			return false;
		}
	} else if ("forget" == flag) {
		// var registerCode = $("input[name=mod]").val().trim();
		// var code = $("input[name=checkCode]").val().trim();
		// if ("" == code) {
		// alert("短信验证码不能为空!");
		// return false;
		// }
		// if (code != registerCode) {
		// alert("您输入的短信验证码不正确!");
		// return false;
		// }
	}
	return true;
}

/**
 * 用户ajax注册，提交
 */
function submitForm(thisForm, url, flag) {
	var check = checkConditions(flag);
	if (!check) {
		return false;
	}
	/** ajax提交，包括注册和登录,找回密码等* */
	$(this).ajaxSubmit(
			{
				url : url,
				type : "POST",
				data : {
					"phone" : $("#" + flag + "-phone").val().trim(),
					"password" : $("#" + flag + "_password").val().trim()
				},
				dataType : "JSON",
				success : function(data) {
					/***********************************************************
					 * 判断data是否是object 对像，
					 * 若是对象类型，直接使用，若是json格式，则用eval函数将data转换为对象类型
					 **********************************************************/
					var result;
					if (typeof (data) == "object"
							&& Object.prototype.toString.call(data)
									.toLowerCase() == "[object object]"
							&& !data.length) {
						result = data;
					} else {
						result = eval("(" + data + ")");
					}
					if (200 == result.code && "register" == flag) {
						alert("恭喜您!注册成功");
						// 注册成功，刷新当前页
						location.reload();
					} else if (200 == result.code && "login" == flag) {
						location.reload();
					} else if (600 == result.code && "login" == flag) {
						alert("用户名或者密码错误!");
					} else if (200 == result.code && "forget" == flag) {
						alert("修改成功!");
						location.reload();
					}
				}
			});
	// 防止表单自动提交
	return false;
}

/** 验证手机号是否正确* */
function phone_check(phone) {
	var reg = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
	if (reg.test(phone)) {
		return true;
	} else {
		return false;
	}
}
/* 注册短信验证码 */
var InterValObjregister; // timer变量，控制时间
var countregister = 60; // 间隔函数，1秒执行
var curCountregister;// 当前剩余秒数
var coderegister = ""; // 验证码
var codeLengthregister = 6;// 验证码长度
function sendRegisterMessage() {
	curCountregister = countregister;
	var phone = $("#register-phone").val();// 手机号码
	if ("" == phone) {
		alert("手机号不能为空!");
		return;
	}
	if (!phone_check(phone)) {
		alert("请输入正确的手机号!");
		return;
	}
	//
	var base = $("input[name=base]").val().trim();
	$("input[name=registerCode]").val("");
	// 设置button效果，开始计时
	$("#btnCode").attr("disabled", "true");
	$("#btnCode").val("请在" + curCountregister + "秒内输入验证码");
	InterValObjregister = window.setInterval(SetRegisterTime, 1000); // 启动计时器，1秒执行一次
	// 向后台发送处理数据
	$.ajax({
		url : base + "/api/member/sendMsg.jspx", // 目标地址
		type : "POST", // 用POST方式传输
		dataType : "json", // 数据格式:JSON
		data : "phone=" + phone.trim(),
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		},
		success : function(data) {
			$("input[name=registerCode]").val(data.code);
		}
	});
}

// timer处理函数
function SetRegisterTime() {
	if (curCountregister == 0) {
		window.clearInterval(InterValObjregister);// 停止计时器
		$("#btnCode").removeAttr("disabled");// 启用按钮
		$("#btnCode").val("重新发送验证码");
		$("input[name=registerCode]").val(""); // 清除验证码。如果不清除，过时间后，输入收到的验证码依然有效
	} else {
		curCountregister--;
		$("#btnCode").val("请在" + curCountregister + "秒内输入验证码");
	}
}

/* 忘记密码 */
jQuery(document).ready(function($) {
	$('#forget-active').click(function() {
		$('.mask-login').fadeOut(100);
		$('.login-active').slideUp(200);
		$('.mask-forget').fadeIn(100);
		$('.forget-active').slideDown(200);
	});
	$('.theme-forget .close').click(function() {
		$('.mask-forget').fadeOut(100);
		$('.forget-active').slideUp(200);
	});

})
$(function() {
	$("#btnCode02").click(function() {
		// 执行获取验证码的操作
		GetNumber();
	});
})

/* 忘记短信密码验证码 */
var InterValObjforget; // timer变量，控制时间
var countforget = 60; // 间隔函数，1秒执行
var curCountforget;// 当前剩余秒数
var codeLengthforget = 6;// 验证码长度
function sendForgetMessage() {
	curCountforget = countforget;
	var phone = $("#forget-phone").val();// 手机号码
	if ("" == phone) {
		alert("手机号不能为空!");
		return;
	}
	if (!phone_check(phone)) {
		alert("请输入正确的手机号!");
		return;
	}
	//
	var base = $("input[name=base]").val().trim();
	$("input[name=registerCode]").val("");
	// 设置button效果，开始计时
	$("#btnCode02").attr("disabled", "true");
	$("#btnCode02").val("请在" + curCountforget + "秒内输入验证码");
	InterValObjregister = window.setInterval(SetforgetTime, 1000); // 启动计时器，1秒执行一次
	// 向后台发送处理数据
	$.ajax({
		url : base + "/api/member/sendMsg.jspx", // 目标地址
		type : "POST", // 用POST方式传输
		dataType : "json", // 数据格式:JSON
		data : "phone=" + phone.trim(),
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		},
		success : function(data) {
			$("input[name=mod]").val(data.code);
		}
	});
}
// timer处理函数
function SetforgetTime() {
	if (curCountforget == 0) {
		window.clearInterval(InterValObjforget);// 停止计时器
		$("#btnCode02").removeAttr("disabled");// 启用按钮
		$("#btnCode02").val("重新发送验证码");
		$("input[name=mod]").val(""); // 清除验证码。如果不清除，过时间后，输入收到的验证码依然有效
	} else {
		curCountforget--;
		$("#btnCode02").val("请在" + curCountforget + "秒内输入验证码");
	}
}
