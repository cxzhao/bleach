/*---register---*/
$('#registerForm').bootstrapValidator({
	message : '没有验证',
	feedbackIcons : {
		valid : 'glyphicon glyphicon-ok',
		invalid : 'glyphicon glyphicon-remove',
		validating : 'glyphicon glyphicon-refresh'
	},
	fields : {			
		email : {
			validators : {
				notEmpty : {
					message : '请输入你的邮箱'
				},
				regexp : {
					regexp :  /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/,
					message : '请输入正确的邮箱格式'
				}
				
			}
		},
		userName : {
			validators : {
				notEmpty : {
					message : '请输入您的用户名'
				}
			}
		},
		password : {
			validators : {
				notEmpty : {
					message : '请输入您的密码'
				},
				regexp : {
					regexp :  /^[a-zA-Z]\w{5,17}$/,
					message : '以字母开头，长度在6~18之间，只能包含字母、数字和下划线'
				}
				
			}
		},
		repassword:{
			validators: {
				notEmpty: {
					message: '确认密码不能为空'
				},
				identical: {
					field: 'password',
					message: '两次输入密码不一致'
				}
			}
		},
		isAgree:{
			validators: {
				notEmpty: {
					message: '您还没有同意用户协议'
				}
			}
		}
	}
});

etApp.controller('registerCtrl', function($scope, $http) {
		$scope.register = function() {
//			$('#registerForm').data('bootstrapValidator').validate();
//			if (!$('#registerForm').data('bootstrapValidator').isValid()) {
//				return;
//			}
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