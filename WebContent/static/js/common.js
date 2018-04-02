/**
 * 
 */
/*前缀初始化*/
var CommURLPrefix = "/czsp/common";
var WfURLPrefix = "/czsp/wf";
var UserURLPrefix = "/czsp/user";
var AuthURLPrefix = "/czsp/auth";
var PlanURLPrefix = "/czsp/plan";

function initPage(){
	initRequired();
	initResetBtn();
}

/*表单必填初始化*/
function initRequired(){
	$(".required").each(function(){
		var label = $("label[for='"+$(this).attr("name")+"']");
		label.append("<span>(*)<span>");
		label.children("span").css("color","red");
	})
}

/*重置按钮初始化*/
function initResetBtn(){
	$("button[name='reset']").bind("click",function(){
		var form = $(this).parents("#searchFrom");
		form.find("input[type!='submit']").val("");
		form.find("select").children().removeAttr("selected");
		
		form.submit();
	})
}

/*ajax返回信息提示*/
function resultPrompt(re,withAlert=true,reloadPage=true,content="success!") {
	console.log(re.result);
	if (re.result == 'success'){
		if(withAlert)
			alert(content);
		if(reloadPage)
			location.reload();
	}
	else
		alert("message : " + re.message);
}

/*表单验证*/
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
			var content = $("label[for='"+$(this).attr("id")+"']").text();
			alert("请填写" + content.replace("(*)",""));
			flag = false;
		}
	});
	return flag;
}

/*表单转json*/
$.fn.serializeObject = function()  
{  
   var o = {};  
   var a = this.serializeArray();  
   $.each(a, function() {  
       if (o[this.name]) {  
           if (!o[this.name].push) {  
               o[this.name] = [o[this.name]];  
           }  
           o[this.name].push(this.value || '');  
       } else {  
           o[this.name] = this.value || '';  
       }  
   });  
   return o;  
}
