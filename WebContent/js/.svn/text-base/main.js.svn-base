/**
 * 
 */
$(function() {

	// model2

	var pattern1

	
	//初始化页面
	function initpage() {
		flagArray = new Array();
		$('.selectNode').each(function() {
			$(this).removeClass('selectNode');
		});
		$('#top').empty();
	}

	//清除按钮事件
	$('#clear_btn').on('click', function() {
		// initpage();
		$('.selectNode').each(function() {
			$(this).removeClass('selectNode');
		});
		$('.mouseenter').each(function() {
			$(this).removeClass('mouseenter');
		});
		clearTable();
		$('#showjson').empty();

	});

	
	//点击getPage按钮触发事件
	$('#AddBtn_url').on('click', function() {
		$('#loading').css('display', 'block');
		clearTable();
		initpage();
		console.log('click');
		var urlvalue = $('#urlPath').val();
		$.ajax({
			url : 'getPage?url=' + urlvalue,
			type : 'GET',
			success : function(msg) {
				$('#top').get(0).innerHTML = msg;
				$('#loading').css('display', 'none');
			},
			error : function() {

			}
		});
		
		//旧代码
		if ($('#pattern_select').val() == "nopattern") {

		} else {
			
			$.ajax({
				url : 'crawl',
				type : 'POST',
				data : {
					rule : $('#showjson').html(),
					url : urlvalue
				},
				success : function(msg) {
					
					var obj = eval("(" + msg + ")");
					createTable(obj);
				},
				error : function() {
					alert('error new');
				}
			});
		}
	});
	
	//生成name,path,value表格
	function createTable(obj) {
		var len = obj.length;
		var html = '';
		for (var i = 0; i < len; i++) {
			var current = obj[i];
			var allcontent = current[1];
			if (current[1].length > 10)
				current[1] = current[1].substr(0, 11) + '......';
			html += '<tr><td>' + current[0] + '</td><td data-content="'
					+ allcontent + '">' + current[1] + '</td><td>' + current[2]
					+ '</td></tr>';
		}
		$('#showNodeTable').append(html);
	}
	//清空name,path,value表格
	function clearTable() {
		$('#showNodeTable').empty();
		var html = '<tr><td>Name</td><td>Value</td><td>Path</td></tr>';
		$('#showNodeTable').append(html);
	}

	//清除目标页面的超链接事件
	$('#top').on('click', 'a', function(e) {
		e.preventDefault();
	});

	//当value内容过多的时候，显示全部文本的浮动窗口
	$('#showNodeTable').on('mouseenter', 'td', function(e) {
		var tar = $(e.target).get(0).getBoundingClientRect();
		// console.log(tar);
		var cleft = tar.left;
		var ctop = tar.top;
		var cur = $(this);
		if (cur.data('content')) {
			$('#showallcontent').css('display', 'block');
			$('#showallcontent').html(cur.data('content'));
			var ctop = ctop - $('#showallcontent').height();
			$('#showallcontent').css({
				left : cleft,
				top : ctop
			});
		} else {
			// console.log('no content');
		}
	});
	$('#showNodeTable').on('mouseleave', function(e) {
		$('#showallcontent').css('display', 'none');
	});

	
	var multiArray = new Array();
//	function getmodel2(arr) {
//		var count = 0;
//		var len = arr[0].length;
//		var resultArrar = new Array();
//		for (var i = 0; i < len; i++) {
//			if (arr[0][i] == arr[1][i]) {
//				resultArrar[i] = arr[0][i];
//			} else {
//				resultArrar[i] = -1;
//				count++;
//			}
//		}
//		if ((arr[0].length != arr[1].length) || (count > 1)) {
//			return "error";
//		} else {
//			return resultArrar;
//		}
//	}
	var currentNodePosi = new Array();
	var currentNode;
	
	//页面点击选择元素
	$(document).click(function(e) { // 在页面任意位置点击而触发此事件
		//本页面原有的组件，点击不出现坐标窗口
		if ($(e.target).hasClass('noclick_ibm')) {
			return true;
		}
		if ($(e.target).parents().hasClass('noclick_ibm')) {
			return true;
		}

		//清除页面点击并未添加到表格的元素所有添加样式的元素样式
		$('.mouseenter').each(function() {
			$(this).removeClass('mouseenter');
		});
		var tar = $(e.target); //当前元素
		currentNode = tar;   //currentNode赋值为当前元素
		var tarposi = $(e.target).get(0).getBoundingClientRect(); //当前元素的位置
		var offsetleft = tarposi.left + 20;
		if(offsetleft > $('html').get(0).clientWidth - 200){
			
			offsetleft = $('html').get(0).clientWidth - 240;
		}
		var offsettop = tarposi.top + tar.get(0).offsetHeight + 20;

		
		$('#fixedDiv').css({
			'display' : 'block',
			'top' : offsettop,
			'left' : offsetleft
		});			//设置当前元素的坐标位置浮动窗口
			
		tar.addClass('mouseenter'); //为点击元素添加样式
//		console.log(tar.get(0).innerHTML);
		//设置当前选择元素的值
		if(platform.isFirefox){
			$('#currentNode_ibm').val(tar.get(0).innerHTML); //$('#currentNode_ibm')存储当前元素的value
		}else{
			$('#currentNode_ibm').val(tar.get(0).innerText);
		}

		
		
		var parents = tar.parents(); //当前元素的父元素
//		console.log("parents...");
//		console.log(parents);   
		var listArray = new Array();    //listArray存储当前元素的父路径
		listArray[0] = tar.parent().children().index(tar); //存储当前元素是父元素的第几个孩子
		var len = parents.length; //父元素的个数
		for (var i = 0; i < len - 1; i++) {   //遍历父元素获取当前元素的父路径，直到#top元素(目标页面的body元素)
			var current = parents.eq(i);
			var j = current.parent().children().index(current);
			listArray[i + 1] = j;
			if (current.parent().get(0).id == "top") //当前元素的父元素为#top时停止遍历
				break;
		}   //一直选择到id=“top”的div时，当前元素的路径
		listArray.reverse(); //倒置listArray,是元素父路径按照从#top(目标页面的body元素)依次往下排列
		currentNodePosi = listArray;  //当前元素的路径 currentNodePosi
//		console.log("newarray:" + currentNodePosi);
		var posi = '';
		for (var i = 0; i < currentNodePosi.length; i++) { //将数组路径，转换为带,的字符串路径
			posi += currentNodePosi[i] + ',';
		}
		posi = posi.substr(0, posi.length - 1);

		$('#nodepath').val(posi);  //当前元素显示的值到浮动窗口的输入框中

		pathRule = $('#nodepath').val(); //路径规则赋值到pathRule
		var currentArray2 = $('#nodepath').val().split(","); //获取元素路径
		var result = findFirstId(currentArray2); //寻找路径中的第一个ID,调用函数findFirstId
		//返回的result是个2维数组，数组result[0]存储ID，result[1]存储路径
		if (result[0] != "") {  //存在ID的情况
			pathRule = '"#' + result[0] + '",';
			for (var q = 0; q < result[1].length; q++) {
				pathRule += '"' + result[1][q] + '",';
			}
			pathRule = pathRule.substr(0, pathRule.length - 1);
		} else {   //不存在ID的情况
			pathRule = "";
			for (var h = 0; h < result[1].length; h++) {
				pathRule += '"' + result[1][h] + '",';
			}
			pathRule = pathRule.substr(0, pathRule.length - 1);
		}
	});

	var pathRule = $('#nodepath').val(); //初始化pathRule
	//修改路径文本框执行js，实时修改路径 
	$('#nodepath').on('keyup', function() {
		// console.log(Number("*"));
		var hasStar = false; //判断路径中是否有*，默认false
		if($(this).val().indexOf("*")) 
			hasStar = true;
			
		var currentArray = $(this).val().split(",");
//		console.log(currentArray);
		var result = new Array(); //存储路径*前的路径值
		var result2 = new Array();//存储路径*后的路径值
		var judge = true;
		for (var i = 0; i < currentArray.length; i++) {
			var now = currentArray[i];
			if (now != "" && judge) { //根据路径是否包含*对路径处理
				if (isNaN(Number(now))) {
					judge = false;
					continue;
				}
				result.push(Number(now)); //
			} else if (now != "") {
				result2.push(Number(now))
			}
		}
//		console.log(result);
//		console.log(result2);
//		//寻找符合条件的结点,并实时在页面中标注出来
		findAllNodeTem(result, result2,hasStar);

	});
	
	//寻找符合当前路径文本框里面路径的元素 
	//arr 存储路径*前的路径值(若果用*，没有则存储全部路径)
	//arr2  存储路径*后的路径值
	//flag 是否有*的标志位
	function findAllNodeTem(arr, arr2,flag) {
		$('.mouseenter').each(function() {
			$(this).removeClass('mouseenter');
		});  //清除页面的临时标记元素样式
		var tar = $('#top'); //目标根元素#TOP(相当于目标也面对的body)
		for (var j = 0; j < arr.length; j++) {
			tar = tar.children().eq(arr[j]);
		}    //获取arr路径末尾的元素，有*则是*前元素，无*则是目标元素
		var len = tar.children().length;  // 获取arr路径末尾的元素的子元素
		var len2 = arr2.length;           //路径*后的路径值个数
//		console.log("len2:" + len2);   
		if (len2 == 0) {    //数组arr2为空，表示*是最后一个路径，或者没有*
			pathRule = $('#nodepath').val();  //获取当前路径框的值
			if (len > 0) {         //对符合条件的元素添加临时样式
				tar.addClass('mouseenter');
				tar.children().addClass('mouseenter');
			} else {
				tar.addClass('mouseenter');
			}
			var result = findFirstId(arr);   //寻找arr路径中的第一个ID
			//返回的result是个2维数组，数组result[0]存储ID，result[1]存储路径
			if (result[0] != "") {    //路径中有ID情况
				pathRule = '"#' + result[0] + '",';
				for (var q = 0; q < result[1].length; q++) {
					pathRule += '"' + result[1][q] + '",';
				}
				pathRule = pathRule.substr(0, pathRule.length - 1);
			} else {                  //路径没有ID的情况
				pathRule = "";
				for (var h = 0; h < result[1].length; h++) {
					pathRule += '"' + result[1][h] + '",';
				}
				pathRule = pathRule.substr(0, pathRule.length - 1);
			}
			if(flag) pathRule += ',"*"';
		} else {              //arr2不为空，表示有*后的路径
//			console.log('r2:');
//			console.log(len2);
//			console.log(tar);
			var result = findFirstId(arr);  //寻找arr路径中的第一个ID
//			console.log('result return!');
//			console.log(result);
			if (result[0] != "") {  //路径中有ID情况
				console.log("not null");
				pathRule = '"#' + result[0] + '",';
				for (var q = 0; q < result[1].length; q++) {
					pathRule += '"' + result[1][q] + '",';
				}
				if (arr2.length != 0)
					pathRule += '"*",'
				for (var w = 0; w < arr2.length; w++) {
					pathRule += '"' + arr2[w] + '",';
				}
				pathRule = pathRule.substr(0, pathRule.length - 1);
			} else {          //路径没有ID的情况
				pathRule = "";
				for (var h = 0; h < result[1].length; h++) {
					pathRule += '"' + result[1][h] + '",';
				}
				if (arr2.length != 0)
					pathRule += '"*",'
				for (var w = 0; w < arr2.length; w++) {
					pathRule += '"' + arr2[w] + '",';
				}
				pathRule = pathRule.substr(0, pathRule.length - 1);
			}
			for (var k = 0; k < len; k++) {       //对*后所有符合条件的元素添加临时样式   
				var tar2 = tar.children().eq(k);
				for (var l = 0; l < len2; l++) {
					tar2 = tar2.children().eq(arr2[l]);
				}
//				console.log(k);
//				console.log(tar2);
				tar2.addClass('mouseenter');
			}
		}
		console.log(pathRule);

	}
	
	//寻找路径中的最接近元素的Id
	//arr为元素路径
	function findFirstId(arr) {
		var result = new Array();
		result[0] = ''; //result[0]存储ID name
		result[1] = new Array(); //存储路径
		var tar = $('#top');
		for (var i = 0; i < arr.length; i++) {
			tar = tar.children().eq(arr[i]);
		}
		arr.reverse();
		console.log(arr);
		console.log('first id');
		console.log(tar);
		for (var j = 0; j < arr.length; j++) {
			if (j != 0)
				tar = tar.parent();

			if (tar.get(0) && tar.get(0).id && tar.get(0).id != ''
					&& tar.get(0).id != null) {
				result[0] = tar.get(0).id;
				break;
			} else {
				result[1][j] = arr[j];
			}
		}
		result[1].reverse();
		return result;
	}
	
	


	var flagArray = new Array();
	//元素添加按钮点击事件
	$('#addfield').on(
			'click',
			function() {
				var name = $('#nodename').val();
				if(name==""){
					alert("Please input name!");
					return;
				}


				var value = $('#currentNode_ibm').val();  //value值
				if (value.length > 10)
					value = value.substr(0, 11) + '....';
				//新添加的表格列 html
				var html = '<tr><td>' + name + '</td><td>' + value
						+ '</td><td>' + pathRule + '</td></tr>';
				$('#fixedDiv').animate({
					left : '50%',
					top : '100%',
					opacity : '0'
				}, 'slow', function() {
					$('#fixedDiv').css('opacity', 1);
					$('#showNodeTable').append(html);
				});  //元素选择浮动框的动画效果

				$('.mouseenter').each(function() {
					$(this).addClass('selectNode');
				});  //为临时元素样式，添加选择样式
				currentNode.addClass('selectNode');
				setTimeout(function() {   //实时改变json版的内容
					var tablejson = getjsontable();
					console.log(tablejson);
					$('#showjson').html(tablejson);
				}, 1000);
			});

	//clear按钮事件
	$('#resetBtn').on('click', function() {
		$('#fixedDiv').css('display', 'none');
		$('#model_select').val('single');
		$('.mouseenter').each(function() {
			$(this).removeClass('mouseenter');
		});
	});

	//获取name,value,path表格的内容，并转化为json string
	function getjsontable() {
		var table = $('#showNodeTable');
		var trs = table.find('tr');
		console.log(trs);
		var trlen = trs.length;
		console.log(trlen);
		var json = "{"
		for (var i = 1; i < trlen; i++) {
			var tds = trs.eq(i).find('td');
			json += '"' + tds.eq(0).html() + '":[' + tds.eq(2).html() + '],';
		}
		json = json.substr(0, json.length - 1);
		json += "}";

		return json;
	}

	$('#savePattern').on('click', function() {


		getjsontable();
	});

	
	//读文件获取下拉框事件
	function attachFileNameToListAb(folderName, listId) {
		$
				.ajax({
					url : 'getFileListAb?path=' + folderName,
					timeout : 10000,
					success : function(msg) {
						var names = eval(msg);
						$(listId).empty();
						$(listId)
								.append(
										'<option value="nopattern">Generate New Rule</option>');
						$.each(names, function(index, name) {
							if (name == "train.initial.csv") {
								$(listId).append(
										'<option value = ' + name
												+ ' selected>' + name
												+ '</option>');
							} else if (name == "treeModel") {
								$(listId).append(
										'<option value = ' + name
												+ ' selected>' + name
												+ '</option>');
							} else {
								$(listId).append(
										'<option value = ' + name + '>' + name
												+ '</option>');
							}
						})
					},
					error : function() {
					}
				});
	}

	//添加enter事件
	$('#urlPath').keypress(function(e) {
		if (e.which == 13)
			enterDown($('#urlPath'),e, $('#AddBtn_url'));
	})	

	// clickEnter($(document),$('#addfield'));
	 clickEnter($('#nodename'),$('#addfield'));

	function clickEnter(input, tar) {
		console.log('in.........');
		input.on('keyup', function(e) {
			enterDown(input,e, tar);
		});
	}
	function enterDown(input,e, target) {
		var ev = window.event || e;

		// 13是键盘上面固定的回车键
		if (ev.keyCode == 13) {

			// 你要执行的方法
			target.trigger('click');
			input.blur();
			console.log('trigger');
		}
	}

	$("#javabtn").zclip({
		path : "js/jquery.zclip/ZeroClipboard.swf",
		copy : function() {
			return ($('#showjson').text().replace(/"/g, "'"));
		},
		afterCopy : function() {/* 复制成功后的操作 */
			inform('Already saved!');
		}
	});

});

function inform(str) {
	var $copysuc = $("<div id='spec1a1forInform' style='position:fixed;z-index:999;bottom:50%;left:50%;"
			+ "margin:0 0 -20px -80px;background-color:rgba(0, 0, 0, 0.2);"
			+ "filter:progid:DXImageTransform.Microsoft.Gradient(startColorstr=#30000000, endColorstr=#30000000);"
			+ "padding:6px;'><div style='padding:10px 20px;text-align:center;border:1px solid #F4D9A6;"
			+ "background-color:#FFFDEE;font-size:14px;'>"
			+ str
			+ "</div></div>");
	$("body").find("#spec1a1forInform").remove().end().append($copysuc);
	$("#spec1a1forInform").fadeOut(3000);
}