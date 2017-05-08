var etApp = angular.module('etApp', [ 'ngRoute','ui.bootstrap' ]).config(
		[ '$routeProvider', function($routeProvider) {
			$routeProvider.when('/', {
				templateUrl : 'index.html',
				controller : 'indexCtrl'
			}).when('/recommend', {
				templateUrl : 'recommend.html',
				controller : 'indexCtrl'
			}).when('/detail', {
				templateUrl : 'detail.html',
				controller : 'detailCtrl'
			}).when('/comment', {
				templateUrl : 'comment.html',
				controller : 'commentCtrl'
			}).when('/editComment', {
				templateUrl : 'editComment.html',
				controller : 'editCommentCtrl'
			}).when('/news', {
				templateUrl : 'news.html',
				controller : 'newsCtrl'
			}).when('/newsdetail', {
				templateUrl : 'newsDetail.html',
				controller : 'newsDetailCtrl'
			}).when('/commentdetail', {
				templateUrl : 'commentDetail.html',
				controller : 'commentDetailCtrl'
			}).when('/mine', {
				templateUrl : 'mine.html',
				controller : 'mineCtrl'
			}).when('/myrecommend', {
				templateUrl : 'myRecommend.html',
				controller : 'myRecommendCtrl'
			}).when('/mycomment', {
				templateUrl : 'myComment.html',
				controller : 'myCommentCtrl'
			}).when('/editMycomment', {
				templateUrl : 'editMyComment.html',
				controller : 'editMyCommentCtrl'
			}).when('/setinfo', {
				templateUrl : 'setinfo.html',
				controller : 'setInfoCtrl'
			}).otherwise({
				redirectTo : '/'
			});
		} ]);

/*禁止模板缓存
 etApp.run(function($rootScope, $templateCache) {  
    $rootScope.$on('$routeChangeStart', function(event, next, current) {  
        if (typeof(current) !== 'undefined'){  
            $templateCache.remove(current.templateUrl);  
        }  
    });  
}); 
 * */

etApp.run(function($rootScope, $templateCache) {  
    $rootScope.$on('$routeChangeStart', function(event, next, current) {  
        if (typeof(current) != 'undefined'){  
            $templateCache.remove(current.templateUrl);  
        }  
    });  
}); 


/*用于输出html的过滤器*/
etApp.filter('to_trusted', ['$sce', function ($sce) {
	　　return function (text) {
	    　　return $sce.trustAsHtml(text);
	　　};
	}]);

etApp.controller('welcomeCtrl', function($scope,$rootScope, $location,$http) {
	
});

etApp.controller('mainCtrl', function($scope,$rootScope, $location,$http) {
	
	//1.判断用户是否登陆，如果登陆了则将登陆数据写到页面
	$rootScope.isLogin = false;
	$rootScope.role = 1;
	$rootScope.type = 0;
	$rootScope.userId = '';
	$.ajax({
		url : "../user/me",
		type : "get",
		dataType : "json",
		success : function(data) {
			$rootScope.userName = data.data.name;
			$rootScope.role = data.data.role;
			$rootScope.type = data.data.type;
			$rootScope.userId = data.data.userId;
			$rootScope.email = data.data.email;
			$rootScope.headImage = data.data.headImage;
			if (data.code == "000000") {
				$rootScope.isLogin = true;				
			}else{								
				$rootScope.isLogin = false;
			}
			$rootScope.$apply();
		},
		error : function(data) {
			alert("系统异常");
		}
	});
	
	$scope.gologinhtml = function(){
		if($rootScope.isLogin==true){
			return;
		}else{
			window.location.href="login.html";
		}
	}
	
	$scope.contact = function(){
		window.location.href="contact.html";
	}
	
	$scope.about = function(){
		window.location.href="about.html";
	}
	
	$scope.work = function(){
		window.location.href="work.html";
	}
	
	$scope.gotoregister = function(){
		if($rootScope.isLogin==true){
			return;
		}else{
			window.location.href="register.html";
		}
	}
	
	$scope.loginOut = function(){
		$.ajax({
			url : "../user/loginout",
			type : "get",
			dataType : "json",
			success : function(data) {
				if (data.code == "000000") {
					$rootScope.userName = data.data.name;
					$rootScope.isLogin = false;
					$rootScope.role = data.data.role;
					$rootScope.type = data.data.type;
					$rootScope.userId = data.data.userId;
					$rootScope.email = data.data.email;
					$rootScope.$apply();				
				}
			},
			error : function(data) {
				alert("系统异常");
			}
		});
	}
});

etApp.directive('wangEditor', function() {
    return {
        restrict: 'A' ,
        require: '?ngModel',
        link: function(scope, element, attrs, ngModel) {
            if (!ngModel) {
                return;
            } 
            ngModel.$render = function() {
                element.html(ngModel.$viewValue || '');
            };
            element.on('blur keyup change', function() {
                scope.$apply(readViewText);
            });
            
            function readViewText() {
                var html = element.html();
                if (attrs.stripBr && html === '<br>') {
                    html = '';
                }
                ngModel.$setViewValue(html);
            }
            var editor = new wangEditor(element);
            //editor.config.uploadImgUrl = '/upload';
            editor.config.menus = [ 'bold', 'underline', 'italic', 'strikethrough',
                        			'eraser', 'forecolor', 'bgcolor', '|', 'quote', 'fontfamily',
                        			'fontsize', 'head', 'unorderlist', 'orderlist', 'alignleft',
                        			'aligncenter', 'alignright', '|', 'img', 'video', '|', 'undo',
                        			'redo', 'fullscreen' ];
            editor.config.uploadImgUrl = '../file/uploadEditorImage';
            
            editor.config.uploadImgFns.onload = function (resultText, xhr) {
                // 上传图片时，已经将图片的名字存在 editor.uploadImgOriginalName
                var originalName = editor.uploadImgOriginalName || ''; 
                // 如果 resultText 是图片的url地址，可以这样插入图片：
                editor.command(null, 'insertHtml', '<img src="' + resultText + '" alt="' + originalName + '" style="max-width:100%;"/>');
            };
            editor.create();
            scope.editor = editor;
        }
    };
});


etApp.controller('ModalInstanceCtrl', function($scope,$rootScope,$modalInstance,items) {
	
	$scope.items = items;
	
	$scope.selected = {
		item : $scope.items[0]
	};
	// ok click
	$scope.ok = function() {
		$modalInstance.close($scope.selected.item);
	};
	
	$scope.cancel = function() {
		$modalInstance.dismiss('cancel');
	}
	
	var halfstar = ['很差5.5分','还行6.5分','一般7.5分','推荐8.5分','力荐9.5分'];
	
	var star = ['很差6分','还行7分','一般8分','推荐9分','力荐10分'];
	
	var score = [6,7,8,9,10];
	var halfscore = [5.5,6.5,7.5,8.5,9.5];
	$scope.map = new Map();
	
	var color = "#FF6600";
	var halfColor = "FFCC00";
	
	$scope.clickStart = function(index){
		var key = "start-"+index;
		var count = $scope.map.get(key);
		if('undefined' == typeof(count)){
			count=1;
			$scope.map.set(key,count);
		}else{
			count=count+1;
			$scope.map.set(key,count);
		}
		
		for(var i=1;i<index;i++){
			$("#start-"+i).css("color","#FF6600");
		}
		if(index==1){			
			if(count==15){
				count=1;
				$scope.map.set(key,count);
			}
			if(count>2){				
				var lowscore = 6-0.5*(count-2)
				$("#span-score").html("很差"+lowscore+"分");
				$rootScope.score = lowscore;
				//颜色变化
				if((count%2)==0){
					$("#start-"+index).css("color","#FF6600");
				}else{
					$("#start-"+index).css("color","#FFCC00");
				}
			}else{
				if((count%2)==0){
					$("#start-"+index).css("color","#FF6600");
					$("#span-score").html(star[index-1]);			
					$rootScope.score = score[index-1];
				}else{
					$("#start-"+index).css("color","#FFCC00");
					$("#span-score").html(halfstar[index-1]);			
					$rootScope.score = halfscore[index-1];
				}
			}
		}else{
			if((count%2)==0){
				$("#start-"+index).css("color","#FF6600");
				$("#span-score").html(star[index-1]);			
				$rootScope.score = score[index-1];
			}else{
				$("#start-"+index).css("color","#FFCC00");
				$("#span-score").html(halfstar[index-1]);			
				$rootScope.score = halfscore[index-1];
			}
		}

		for(var i=index+1;i<=5;i++){
			$("#start-"+i).css("color","#666666");
		}
		
	}
	
	$scope.overStart = function(index){
		$scope.map = new Map();
		for(var i=1;i<index;i++){
			$("#start-"+i).css("color","#FF6600");
		}
		
		$("#start-"+index).css("color","#FFCC00");
		
		for(var i=index+1;i<=5;i++){
			$("#start-"+i).css("color","#666666");
		}
		$("#span-score").html(halfstar[index-1]);		
		$rootScope.score = halfscore[index-1];
	}
	
	$scope.submitComment = function(){
		$.ajax({
			url : "../etcom/save",
			type : "post",
			data:{
				objectId:$rootScope.commentObjectId,
				userId:$rootScope.userId,
				score:$rootScope.score,
				context:$("#comment-context").val(),
				name:$rootScope.userName,
				type:0	
			},
			dataType : "json",
			success : function(data) {
				if(data.code=='000000'){
					$modalInstance.close($scope.selected.item);
				}
				$scope.$apply();
			},
			error : function(data) {
			}
		});
	}
	
	$scope.userLogin = function(){
		var mail = $("input[name=mail]").val();
		var password = $.md5($("input[name=password]").val());
		$.ajax({
			url : "../user/login",
			type:"post",
			data : {
				email: mail,
				password:password
			},
			dataType : "json",
			success : function(data) {
				if (data.code == "000000") {	
					$rootScope.userName = data.data.name;
					$rootScope.isLogin = true;
					$rootScope.role = data.data.role;
					$rootScope.type = data.data.type;
					$rootScope.userId = data.data.userId;
					$modalInstance.close($scope.selected.item);
					$rootScope.$apply();
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

etApp.directive('onFinishRenderFilters', function ($timeout) {
    return {
        restrict: 'A',
        link: function (scope, element, attr) {
            if (scope.$last === true) {
                $timeout(function () {
                    scope.$emit('ngRepeatFinished');
                });
            }
        }
    }
});
