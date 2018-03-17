/**
 * 
 */
/*前缀初始化*/
var WfURLPrefix = "/czsp/wf";
var UserURLPrefix = "/czsp/user";
var AuthURLPrefix = "/czsp/auth";
var PlanURLPrefix = "/czsp/plan";

function resultPrompt(re,withAlert=true,reloadPage=true) {
	console.log(re.result);
	if (re.result == 'success'){
		if(withAlert)
			alert("success!");
		if(reloadPage)
			location.reload();
	}
	else
		alert("message : " + re.message);
}

function validate() {
	var elements = $("select.required");
	var flag = true;
	elements.each(function(){
		if ($(this).val() == ""){
			alert("请选择" + $("label[for='"+$(this).attr("id")+"']").text());
			flag = false;
		}
	});
	if(!flag)
		return flag;
	elements = $("input.required");
	elements.each(function(){
		if ($(this).val() == ""){
			alert("请填写" + $("label[for='"+$(this).attr("id")+"']").text());
			flag = false;
		}
	});
	return flag;
}

