var signApp = angular.module('signApp', []);

signApp.controller('signCtrl', function($scope,$rootScope, $location,$http) {

$scope.login = function(){

	var email = $("input[name=email]").val();
	var password = $.md5($("input[name=password]").val());
	
	$.ajax({
		url : "../user/login",
		type:"post",
		data : {
			email : email,
			password:password
		},
		dataType : "json",
		success : function(data) {
			if (data.code == "000000") {	
				window.location.href="main.html#/"
				
			} else {
				alert(data.message);
			}
		},
		error:function(data){
			alert("系统异常");
		}
	});
}		
});

var registerApp = angular.module('registerApp', []);
registerApp.controller('registerCtrl', function($scope, $http) {
	$scope.register = function() {
//		$('#registerForm').data('bootstrapValidator').validate();
//		if (!$('#registerForm').data('bootstrapValidator').isValid()) {
//			return;
//		}
		var myEmail = $("input[name=email]").val();
		var userName = $("input[name=userName]").val();
		var mypassword = $.md5($("input[name=password]").val());
		var agree = $("input[name=isAgree]").val();

		$.ajax({
			url : "../user/register",
			type : "post",
			data : {
				email : myEmail,
				name : userName,
				password : mypassword,
				isAgree : agree
			},
			dataType : "json",
			success : function(data) {
				if (data.code == "000000") {						
					//跳转到登陆界面
					 window.location.href="login.html"; 
				} else {
					$scope.result=data.message;
					$scope.$apply();
					$(".tips").css("display","block");
				}
			},
			error : function(data) {
				alert("系统异常");
			}
		});
	}

});

var resetPassApp = angular.module('resetPassApp', []);
resetPassApp.controller('resetPassCtrl', function($scope, $http) {
	
	$scope.isSend = false;
	$scope.emailNull=false;
	$scope.sendPassCode = function(){
		var mail = $("input[name=email]").val();
		if(typeof(mail) == 'undefined' || mail == null || mail==''){
			$scope.emailNull=true;
			$scope.$apply();
			return;
		}
			
		$.ajax({
			url : "../user/sendPassCode",
			type : "post",
			data : {
				email : $("input[name=email]").val(),
			},
			dataType : "json",
			success : function(data) {
				if (data.code == "000000") {						
					$scope.isSend = true;
					$scope.$apply();
				} 
				
			},
			error : function(data) {
			}
		});
	}
	
	$scope.resetPass = function() {

		var myEmail = $("input[name=email]").val();
		
		if(typeof(myEmail) == 'undefined' || myEmail == null || myEmail==''){
			$scope.emailNull=true;
			$scope.$apply();
			return;
		}else{
			$scope.emailNull=false;
		}
		
		var vcode = $("input[name=code]").val();
		//var mypassword = $.md5($("input[name=password]").val());
		
		if(typeof(vcode) == 'undefined' || vcode == null || vcode==''){
			$scope.vcodeNull=true;
			$scope.$apply();
			return;
		}else{
			$scope.vcodeNull=false;
		}
		
		var mypassword = $("input[name=password]").val();
		var repassword = $("input[name=repassword]").val();
		
		if(typeof(mypassword) == 'undefined' || mypassword == null || mypassword==''){
			$scope.passNull=true;
			$scope.$apply();
			return;
		}else{
			$scope.passNull=false;
			if(mypassword!=repassword){
				$scope.passNotEq=true;
				$scope.$apply();
				return;
			}else{
				$scope.passNotEq=false;
			}
		}

		$.ajax({
			url : "../user/resetPass",
			type : "post",
			data : {
				email : myEmail,
				code : vcode,
				pass : $.md5(mypassword)
			},
			dataType : "json",
			success : function(data) {
				if (data.code == "000000") {						
					//跳转到登陆界面
					 window.location.href="login.html"; 
				} else {
					$scope.result=data.message;
					$scope.$apply();
					$(".tips").css("display","block");
				}
			},
			error : function(data) {
			}
		});
	}

});

var contactApp = angular.module('contactApp', []);
contactApp.controller('contactCtrl', function($scope, $http) {
	
	$scope.commitMsg = function(){
		$.ajax({
			url : "../message/send",
			type : "post",
			data : {
				message:$('#message').val()
			},
			dataType : "json",
			success : function(data) {
				if (data.code == "000000") {						
					alert("阿里嘎多，您的留言已收到，我们会尽快处理或者联系您！");
				}
			},
			error : function(data) {
			}
		});
	}

});


