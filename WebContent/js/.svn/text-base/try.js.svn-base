/**
 * 
 */
$(function(){
	
	
	//model2
	
	var pattern1
	
	
	var namelist = new Array();
	var positionlist = new Array();
	
	function initpage(){
		namelist = new Array();
		positionlist = new Array();
		$('#top').empty();
	}
	
	attachFileNameToListAb('c:/data/cdt/','#pattern_select');
	$('#AddBtn_url').on('click',function(){
//		$('#loading').css('display','block');
		clearTable();
		initpage();
		console.log('click');
		var urlvalue =  $('#urlPath').val();
		var html = '<iframe id="newiframe" src='+urlvalue+'></iframe>';
		$('#top').html(html);
//		$.ajax({
//			url:'getPage?url='+urlvalue,
//			type:'GET',
//			success:function(msg){
//				console.log(msg);
//				$('#top').get(0).innerHTML = msg;
//				$('#loading').css('display','none');
//			},
//			error:function(){
//				
//			}
//		});
//		if($('#pattern_select').val() == "nopattern"){
//			
//		}else{
//			$.ajax({
//				url:'crawl?rule='+$('#pattern_select').val()+'&url='+urlvalue,
//				type:'GET',
//				success:function(msg){
//					console.log(msg);
////					$('#top').get(0).innerHTML = msg;
//					var obj = eval("("+msg+")");
//					createTable(obj);
//				},
//				error:function(){
//					alert('error new');
//				}
//			});
//		}
	});
	function createTable(obj){
		var len = obj.length;
		var html = '';
		for(var i=0;i<len;i++){
			var current = obj[i];
			if(current[1].length > 10) current[1] = current[1].substr(0,11) +'......';
			html +='<tr><td>'+current[0]+'</td><td>'+current[1]+'</td><td>'+current[2]+'</td></tr>';
		}
		$('#showNodeTable').append(html);
	}
	function clearTable(){
		$('#showNodeTable').empty();
		var html = '<tr><td>Name</td><td>Value</td><td>Path</td></tr>';
		$('#showNodeTable').append(html);
	}
	
	$('#top').on('click','a',function(e){
		e.preventDefault();
	});
	var currentNodePosi = new Array();
	$(document).click(function(e) { // 在页面任意位置点击而触发此事件
		if($(e.target).hasClass('noclick_ibm')){
			return true;
		}
		if($(e.target).parents().hasClass('noclick_ibm')){
			return true;
		}
		console.log("position"); 
		console.log($(e.target)); // e.target表示被点击的目标
		$('.mouseenter').each(function(){
			$(this).removeClass('mouseenter');
		});
		var tar = $(e.target).get(0).getBoundingClientRect();
		var offsetleft = tar.left+ 20;
		var offsettop = tar.top + 20;
		
		$('#fixedDiv').css({'display':'block','top':offsettop,'left':offsetleft});
		tar.addClass('mouseenter');
		console.log(tar.get(0).innerHTML);
		$('#currentNode_ibm').val(tar.get(0).innerText);
		
		var parents = tar.parents();
		console.log(parents);
		var listArray = new Array();
		listArray[0] = tar.parent().children().index(tar);
		var len = parents.length;
		for(var i=0;i<len-1;i++){
			var current = parents.eq(i);
			var j = current.parent().children().index(current);
			listArray[i+1] = j;
			if(current.parent().get(0).id == "top") break;
		}
		console.log(listArray);
		console.log(listArray.reverse());
		currentNodePosi = listArray;
		console.log("newarray:"+currentNodePosi);
		
	});
	
	
	$('#addfield').on('click',function(){
		namelist.push($('#nodename').val());
		positionlist.push(currentNodePosi);
		var name = $('#nodename').val();
		var value = $('#currentNode_ibm').val();
		if(value.length > 10) value = value.substr(0,11) +'....';
		var posi='';
		for(var i=0;i<currentNodePosi.length;i++){
			posi += currentNodePosi[i] +',';
		}
		posi = posi.substr(0,posi.length-1);
		var html = '<tr><td>'+name+'</td><td>'+value+'</td><td>'+posi+'</td></tr>';
		$('#showNodeTable').append(html);
	});
	
	$('#savePattern').on('click',function(){
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
				attachFileNameToListAb('c:/data/cdt/','#pattern_select');
				alert("sucesss");
			},
			error:function(){
				
			}
		});
	});
	
	$('#pattern_select').on('change',function(){
		if($(this).val() == "nopattern"){
			$('#bbright').css('opacity',1);
			$('#bbleft').css('opacity',1);
		}else{
			$('#bbright').css('opacity',0);
			$('#bbleft').css('opacity',0);
		}
	});
	
	
	function attachFileNameToListAb(folderName,listId){
		$.ajax({
			url : 'getFileListAb?path='+folderName,
			timeout : 10000,
			success : function(msg) {
				var names=eval(msg);
				$(listId).empty();
				$(listId).append( '<option value="nopattern">No Crawl Rule</option>');
				$.each(names,function(index,name){
					if(name == "train.initial.csv"){
						$(listId).append( '<option value = '+name+' selected>'+name+'</option>');
					}else if(name == "treeModel"){
						$(listId).append( '<option value = '+name+' selected>'+name+'</option>');
					}
					else{
						$(listId).append( '<option value = '+name+'>'+name+'</option>');
					}
				})
			},
			error : function() {
			}
		});
	}
	
});