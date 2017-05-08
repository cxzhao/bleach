var adminApp = angular.module('adminApp', [ 'ngRoute' ]).config(
		[ '$routeProvider', function($routeProvider) {
			$routeProvider.when('/', {
				template : '主人，系统准备就绪！请下达命令！',
			}).when('/type', {
				templateUrl : 'type.html',
				controller : 'typeAdminCtrl'
			}).when('/cartoon', {
				templateUrl : 'cartoon.html',
				controller : 'cartoonCtrl'
			}).when('/addCartoon', {
				templateUrl : 'addCartoon.html',
				controller : 'addCartoonCtrl'
			}).when('/editCartoon', {
				templateUrl : 'editCartoon.html',
				controller : 'editCartoonCtrl'
			}).when('/recommend', {
				templateUrl : 'recommend.html',
				controller : 'recommendCtrl'
			}).when('/news', {
				templateUrl : 'news.html',
				controller : 'newsCtrl'
			}).when('/addNews', {
				templateUrl : 'addNews.html',
				controller : 'addNewsCtrl'
			}).when('/editNews', {
				templateUrl : 'editNews.html',
				controller : 'editNewsCtrl'
			}).otherwise({
				redirectTo : '/'
			});
		} ]);

adminApp.controller('adminCtrl', function($scope,$rootScope, $location,$http) {	
	$rootScope.isLogin = false;
	$rootScope.role = 1;
	$rootScope.type = 0;
	$rootScope.userId = '';
	$.ajax({
		url : "../user/me",
		type : "get",
		dataType : "json",
		success : function(data) {
			if(data.code=="000000"&&data.data.role==0){
				$rootScope.isAdminLogin = true;
			}else{
				$rootScope.isAdminLogin = false;
			}
			$rootScope.userName = data.data.name;				
			$rootScope.role = data.data.role;				
			$rootScope.type = data.data.type;
			$rootScope.userId = data.data.userId;
			$rootScope.$apply();
		},
		error : function(data) {
			alert("系统异常");
		}
	});
	
});

adminApp.directive('contenteditable', function() {
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
            editor.config.menus = [ 'bold', 'underline', 'italic', 'strikethrough',
                        			'eraser', 'forecolor', 'bgcolor', '|', 'quote', 'fontfamily',
                        			'fontsize', 'head', 'unorderlist', 'orderlist', 'alignleft',
                        			'aligncenter', 'alignright', '|', 'img', 'video', '|', 'undo',
                        			'redo', 'fullscreen' ];
            editor.config.uploadImgUrl = '/upload';
            editor.create();
            scope.editor = editor;
        }
    };
});

adminApp.directive('contenteditable2', function() {
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