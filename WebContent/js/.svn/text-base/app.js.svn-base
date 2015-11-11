/**
 * 
 */

$(function(){
	$('#AddBtn_url').on('click',function(){
		var urlvalue =  $('#urlPath').val();
		$.ajax({
			url:'getPage?url='+urlvalue,
			type:'GET',
			success:function(){
				window.location.href="target.html";
			},
			error:function(){
				
			}
		});
	});
});