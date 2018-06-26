/*医生介绍详情页包括鼠标移入，点击更多和收起事件，与控制医生介绍，科室介绍，医院介绍*/
/*公共方法有收起的时候让加载更多不显示*/

/*鼠标移动到推荐医生列表事件*/
$(document).ready(function() {
	var base = $("input[name=base]").val().trim();
	/** 滚动条滚动到底部，自动加载* */
	var pageNo = $("input[name=pageNo]").val();
	var pageSize = $("input[name=pageSize]").val();
	$(window).scroll(function() {
		if ($(document).scrollTop() + $(window).height() >= $(document).height()) {
			pageNo = parseInt(pageNo) + 1;
			loadPage(pageNo, pageSize);
		} 
	});
	// 分页加载数据
	function loadPage(pageNo, pageSize) {
		var query = $("input[name=query]").val();
		var city = $("input[name=cityName]").val();
		var hospital_P = $("input[name=hospital_P]").val();
		var hospital_S = $("input[name=hospital_S]").val();
		var viceSenior = $("input[name=viceSenior]").val();
		var degree = $("input[name=degree]").val();
		$.ajax({
			url : base + "/api/search/search.jspx", 
			type : "POST", 
			dataType : "json", 
			data : {"query" : query, "cityName" : city, 
				"hospital_P" : hospital_P, "hospital_S" : hospital_S, 
				"viceSenior" : viceSenior, "degree" : degree, 
				"pageNo" : pageNo, "pageSize" : pageSize}, 
			success : function(data) {
				var nextPage = data.pageNo + 1;
				$("input[name=pageNo]").val(nextPage);
				$("input[name=pageSize]").val(data.pageSize);
				/**异步加载更多信息**/
				$.each(data.results, function(index, content) {
					create(index, content);
				});
			}
		});
	}
	/**添加content**/
	function create(index, content) {
		var genderPng;
		if (0 == content.gender) {
			genderPng = 'man.png" class="body-people">';
		}
		else if (1 == content.gender) {
			genderPng = 'girl.png" class="body-people">';
		}
		var collectDiv;
		var userId = $("input[name=userId]").val();
		collectDiv = '<input type="hidden" name="collected_' + content.id + '" value="0"/>'
	       	 + '<input id="collect_' + content.id + '" onclick="doctorCollect(' + content.id + ');"'
	         +	'type="button" class="btn02 btn-round color-2 material-design" ata-color="#ffffff" value="收&emsp;藏"/>';
		if ("" != userId) {
			$.ajax({
				url : base + "/api/member/isCollectedOrNot.jspx", 
				type : "POST", 
				data : {"userId" : userId, 
					"doctorId" : content.id}, 
				dataType : "JSON", 
				success : function(data) {
					if (data.collected) {
						$("#collect_" + content.id).val("已收藏");
						$("input[name=collected_" + content.id + "]").val("1");
					}
				}  
			});
		}
		// 循环 '备注'
		var seeTime = content.doctorExt.seeTime;
		var times = seeTime.split(";");
		var commentDiv = "";
		for(var i = 0; i< times.length; i++) {
			var count = i + 1;
			commentDiv += '<li><img src="'+ base +'/static/front/Image/'+ count +'.png" class="img-size">&emsp;<span>'+ times[i].split(":")[0] +'</span></li>';
		}
		// 处理擅长领域
		var skilledField = content.doctorExt.skilledField;
		var skilled = skilledField.split(";");
		var skilledDiv = "";
		for(var i = 0; i < skilled.length; i++) {
			if (i > 3) break;
			skilledDiv += '<li class="body-fon04">'+ skilled[i] +'</li>';
		}
		skilledDiv += '<li><!-- 该隐藏标签不能去掉，用以做条件判断 --><input type="hidden" value="1" id="key_'+ content.id +'"/>'
			+ '<input type="button" id="more_button_'+ content.id +'" class="btn02 btn-round color-3 material-design"'
	        + 'data-color="#ffffff" value="更多信息>>" onclick="expreadInfo('+ content.id +');"/></li></ul></div><div style="clear: both;"></div>';
		// 处理更多介绍
		var tag = "'tag_'";
		var moreInfoDiv = '<div style="display: none;" id="more_info_'+ content.id +'" class="more_introduction"><div><ul>'
    		+ '<li data-tag="p" id="tag_1_'+ content.id +'" class="on" onclick="moreInfo('+ content.id +', '+ tag +', 1, 3);"><img class="p" src="'+ base +'/static/front/Image/headBlue.png"><span>个人介绍</span></li>'
    		+ '<li data-tag="d" id="tag_2_'+ content.id +'" class="left_20" onclick="moreInfo('+ content.id +', '+ tag +', 2, 3);"><img class="d" src="'+ base +'/static/front/Image/keshiGrey.png"><span>科室介绍</span></li>'
    		+ '<li data-tag="h" id="tag_3_'+ content.id +'" class="left_20" onclick="moreInfo('+ content.id +', '+ tag +', 3, 3);"><img class="h" src="'+ base +'/static/front/Image/houseGrey.png"><span>所在医院介绍</span></li>'
    		+ '</ul></div><div style="clear:both;"></div><div class="cont_intro_p" style="display: block;">'
    		+ '<div><p style="font-size: 16px; padding: 20px 40px;text-indent: 2em">'+ content.doctorExt.description 
    		+ '</p></div></div><div class="cont_intro_d" style="display: none;">'
    		+ '<div><p style="font-size: 16px; padding: 20px 40px;text-indent: 2em">'+ content.department.description 
    		+ '</p></div></div>'
    		+ ' <div class="cont_intro_h" style="display: none;"><div><p style="font-size: 16px; padding: 20px 40px;text-indent: 2em">';
		var hospitals = content.hospitals;
		for (var i = 0; i < hospitals.length; i++) {
			var h = hospitals[i];
			var hDiv = h.name + ":" + h.desc;
			moreInfoDiv += hDiv;
		}
		moreInfoDiv += '</p></div></div></div></div>';
		//将动态页面信息追加到body中
		var _obj = $(".body-bg");
		_obj.append('<div class="body-bg04"></div><div class="body-bg03" id="body_bg03_'+ content.id +'"><div class="top-div"><table> <tr><td>'
	           + '<img src="'+ base +'/static/front/Image/header.png" style="border-radius:50%;" class="body-header"/>'
	           +  '<img src="'+ base +'/static/front/Image/' + genderPng + '</td></tr><tr class="body-font"><td class="body-font"><span >'
				+ content.name + '</span>&emsp;&emsp;<span>' + content.department.name + '</span></td></tr>' 
		           + '<tr><td class="body-font02"><span>' + content.doctorExt.job + '</span>&emsp;|&emsp;<span>' 
		           +  content.doctorExt.professionalTitles + '</span></td></tr><tr ><td><section class="container02">'
		           + collectDiv + '</section></td></tr></table></div><div class="top-div left-100" id="time01">'
				+ '<table border="1px" width="600px" align="center" style="border-color: #cccccc;table-layout:fixed">'
                + '<caption>坐诊时间<div class="contain"><div class="line"></div></div></caption>'
                + '<tr><td></td><td>一</td><td>二</td><td>三</td><td>四</td><td>五</td><td>六</td><td>日</td></tr>'
                + '<tr id="morning_' + content.id +'" ><td>上午</td><td></td><td></td><td></td>'
                + '<td></td><td></td><td></td><td></td></tr>'
                + '<tr id="afternoon_' + content.id +'"><td>下午</td><td></td><td></td><td></td><td></td>'
                + '<td></td><td></td><td></td></tr></table>'
                + '<div><div style="margin-top: 5px"><span class="bz">备注:</span></div><div class="top-div"><ul>'
				+ commentDiv + '</ul></div></div></div>'
				+ '<div class="skilled">擅长领域<div class="contain03"><div class="line03"></div></div><ul>'
				+ skilledDiv + moreInfoDiv);
		// 处理坐诊时间显示
		for(var i = 0; i < times.length; i++) {
			var count = i + 1;
			var weeks = times[i].split(":")[1];
			var week = weeks.split(",");
			for(var j = 0; j < week.length; j++) {
				var weekday = week[j].split("|")[0];
				var t = week[j].split("|")[1].split("&");
				for(var k = 0; k < t.length; k++) {
					var t_time = t[k];
					if ("周一" == weekday && "上午" == t_time)
						$("#morning_" + content.id + " td:eq(1)").append('<tr><td>上午</td><img src="' + base + '/static/front/Image/' + count + '.png" class="img-size">');
		    		else if ("周一" == weekday && "下午" == t_time)
		    			$("#afternoon_" + content.id + " td:eq(1)").append('<img src="' + base + '/static/front/Image/' + count + '.png" class="img-size">');
		    		else if ("周二" == weekday && "上午" == t_time)
		    			$("#morning_" + content.id + " td:eq(2)").append('<img src="' + base + '/static/front/Image/' + count + '.png" class="img-size">');
		    		else if ("周二" == weekday && "下午" == t_time)
		    			$("#afternoon_" + content.id + " td:eq(2)").append('<img src="' + base + '/static/front/Image/' + count + '.png" class="img-size">');
		    		else if ("周三" == weekday && "上午" == t_time)
		    			$("#morning_" + content.id + " td:eq(3)").append('<img src="' + base + '/static/front/Image/' + count + '.png" class="img-size">');
		    		else if ("周三" == weekday && "下午" == t_time)
		    			$("#afternoon_" + content.id + " td:eq(3)").append('<img src="' + base + '/static/front/Image/' + count + '.png" class="img-size">');
		    		else if ("周四" == weekday && "上午" == t_time)
		    			$("#morning_" + content.id + " td:eq(4)").append('<img src="' + base + '/static/front/Image/' + count + '.png" class="img-size">');
		    		else if ("周四" == weekday && "下午" == t_time)
		    			$("#afternoon_" + content.id + " td:eq(4)").append('<img src="' + base + '/static/front/Image/' + count + '.png" class="img-size">');
		    		else if ("周五" == weekday && "上午" == t_time)
		    			$("#morning_" + content.id + " td:eq(5)").append('<img src="' + base + '/static/front/Image/' + count + '.png" class="img-size">');
		    		else if ("周五" == weekday && "下午" == t_time)
		    			$("#afternoon_" + content.id + " td:eq(5)").append('<img src="' + base + '/static/front/Image/' + count + '.png" class="img-size">');
		    		else if ("周六" == weekday && "上午" == t_time)
		    			$("#morning_" + content.id + " td:eq(6)").append('<img src="' + base + '/static/front/Image/' + count + '.png" class="img-size">');
		    		else if ("周六" == weekday && "下午" == t_time)
		    			$("#afternoon_" + content.id + " td:eq(6)").append('<img src="' + base + '/static/front/Image/' + count + '.png" class="img-size">');
		    		else if ("周日" == weekday && "上午" == t_time)
		    			$("#morning_" + content.id + " td:eq(7)").append('<img src="' + base + '/static/front/Image/' + count + '.png" class="img-size">');
		    		else if ("周日" == weekday && "下午" == t_time)
		    			$("#afternoon_" + content.id + " td:eq(7)").append('<img src="' + base + '/static/front/Image/' + count + '.png" class="img-size">');
				}
			}
		}
	}
});
/** 点击更多信息事件**/
function expreadInfo(id) {
	var key = $("#key_" + id).val();
	if (1 == key) {
		$("#more_button_" + id).val("收起信息<<");
		$("#body_bg03_" + id).css("height", "475px");
		$("#more_info_" + id).css("display", "block");
		$("#key_" + id).val("0");
	} else {
		$("#more_button_" +id).val("更多信息>>");
		$("#body_bg03_" + id).css("height", "250px");
		$("#more_info_" + id).css("display", "none");
		$("#key_" + id).val("1");
	}
}
/** 个人介绍、科室介绍、医院介绍切换* */
function moreInfo(id, name, m, n) {
	var base = $("input[name=base]").val();
	var _obj = $("#" + name + m + "_" + id);
	for(var i = 1; i <= n; i++) {
		i == m ? _obj.addClass("on") : $("#" + name + i + "_" + id).removeClass("on");
	}
	var tag = _obj.data("tag");
	if ("p" == tag) {
		_obj.children("img").attr("src", base + "/static/front/Image/headBlue.png");
		_obj.siblings().children(".d").attr("src", base + "/static/front/Image/keshiGrey.png");
		_obj.siblings().children(".h").attr("src", base + "/static/front/Image/houseGrey.png");
		_obj.parent().parent().siblings(".cont_intro_p").css("display", "block");
		_obj.parent().parent().siblings(".cont_intro_d").css("display", "none");
		_obj.parent().parent().siblings(".cont_intro_h").css("display", "none");
	}
	else if ("d" == tag) {
		_obj.siblings().children(".p").attr("src", base + "/static/front/Image/headGrey.png");
		_obj.children("img").attr("src", base + "/static/front/Image/keshiBlue.png");
		_obj.siblings().children(".h").attr("src", base + "/static/front/Image/houseGrey.png");
		_obj.parent().parent().siblings(".cont_intro_d").css("display", "block");
		_obj.parent().parent().siblings(".cont_intro_p").css("display", "none");
		_obj.parent().parent().siblings(".cont_intro_h").css("display", "none");
	}
	else if ("h" == tag) {
		_obj.siblings().children(".p").attr("src", base + "/static/front/Image/headGrey.png");
		_obj.siblings().children(".d").attr("src", base + "/static/front/Image/keshiGrey.png");
		_obj.children("img").attr("src", base + "/static/front/Image/houseBlue.png");
		_obj.parent().parent().siblings(".cont_intro_h").css("display", "block");
		_obj.parent().parent().siblings(".cont_intro_d").css("display", "none");
		_obj.parent().parent().siblings(".cont_intro_p").css("display", "none");
	}
}
