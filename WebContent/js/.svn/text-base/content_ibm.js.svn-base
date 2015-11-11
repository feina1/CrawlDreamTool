/**
 * 
 */
	console.log('aaaa');
	$('a').on('click',function(e){
		e.preventDefault();
	});
	var currentValue,currentNode;
	$(document).click(function(e) { // 在页面任意位置点击而触发此事件
		if($(e.target).hasClass('noclick_ibm')){
			return true;
		}
		console.log("position"); 
		console.log($(e.target)); // e.target表示被点击的目标
		var tar = $(e.target);
		console.log(tar.get(0).innerHTML);
		$('#currentNode_ibm').html(tar.get(0).innerHTML);
		
		var parents = tar.parents();
		console.log(parents);
		var listArray = new Array();
		listArray[0] = tar.parent().children().index(tar);
		var len = parents.length;
		for(var i=0;i<len-1;i++){
			var current = parents.eq(i);
			var j = current.parent().children().index(current);
			listArray[i+1] = j;
		}
		console.log(listArray);
		console.log(listArray.reverse());
		currentValue = listArray.reverse();
		
	});
	var namelist = new Array();
	var positionlist = new Array();
	$('#addlist_ibm').on('click',function(){
		var html = "<div class='ritem_ibm noclick_ibm'>"+$('#describeInput_ibm').val()+":"+$('#currentNode_ibm').html()+"</div>";
		 $('#rightitemArea').append(html);
		 namelist.push($('#describeInput_ibm').val());
		 positionlist.push(currentValue);
		 console.log(namelist);
		 console.log(positionlist);
	});
	
	$('#saveRuleBtn_ibm').on('click',function(){
		var len = namelist.length;
		var jsondata = '{';
		for(var i=0;i<len;i++){
			var nowarray = positionlist[i];
			var nowstr = '';
			for(var j=0;j<nowarray.length;j++){
				nowstr += nowarray[j] +',';
			}
			nowstr = nowstr.substr(0,nowstr.length-1);
			jsondata += '"'+namelist[i]+'":['+nowstr+'],';
		}
		jsondata = jsondata.substr(0,jsondata.length-1);
		jsondata +='}';
		
		console.log(jsondata);
		console.log(eval("("+jsondata+")"));
		var name = $('#pagename_ibm').val();
		$.ajax({
			url:'saveCrawlConfig?name='+name+'&configMap='+jsondata,
			type:'GET',
			success:function(){
				alert("sucesss");
			},
			error:function(){
				
			}
		});
		
	});