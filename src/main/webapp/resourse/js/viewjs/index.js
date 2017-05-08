etApp.controller('indexCtrl', function($scope,$rootScope, $location,$http,$modal,$log) {	
	
	
	loadType();
	queryCartoon('','',-1,'japan',1,20);
	loadNews();
	loadinfo();
	function loadType(){
		$.ajax({
			url : "../cartoonType/queryAll",
			type : "get",
			dataType : "json",
			success : function(data) {
				$scope.typeList = data.result.data;
				$scope.$apply();
			},
			error : function(data) {
			}
		});
	}
	
	function loadNews(){
		$.ajax({
			url : "../news/query",
			type : "get",
			data : {
				pageNumber:1,
				pageSize:5,
				type:0,
				status:1
			},
			dataType : "json",
			success : function(data) {
				if (data.code == "000000") {
					$scope.newsList = data.data.list;
					$scope.$apply();
				}
			},
			error : function(data) {
			}
	 });
	}
	
	function loadinfo(){
		$.ajax({
			url : "../news/query",
			type : "get",
			data : {
				pageNumber:1,
				pageSize:5,
				type:1,
				status:1
			},
			dataType : "json",
			success : function(data) {
				if (data.code == "000000") {
					$scope.infoList = data.data.list;	
					$scope.$apply();
				}
			},
			error : function(data) {
			}
	 });
	}
	
	$scope.queryByType=function(type){
		var area = '';
		var isEnd = '';
		
		$('input:checkbox[name=area]:checked').each(function(i){  
	        if(0==i){  
	        	area = $(this).val();
	        }else{  
	        	area += (","+$(this).val());  
	        }  
	    });
		
		$('input:checkbox[name=isEnd]:checked').each(function(i){  
	        if(0==i){  
	        	isEnd = $(this).val();
	        }else{  
	        	isEnd += (","+$(this).val());  
	        }  
	    });
		
		$scope.area = area;
		$scope.isEnd = isEnd;
		
		if(type==0){
			queryCartoon2('','',isEnd,area,1,20);
		}else{
			queryCartoon2(type.id,'',isEnd,area,1,20);
		}
		
	}
	
	$scope.$on('ngRepeatFinished', function (ngRepeatFinishedEvent) {
		var $container = $('.result');
		$container.imagesLoaded(function() {
			$container.masonry('destroy');
			$container.masonry();
		});
    });
	
	function queryCartoon(typeId,key,end,carea,page,size){
		$scope.typeId=typeId;
		$scope.key=key;
		$.ajax({
			url : "../cartoon/query",
			type : "get",
			data:{
				type:typeId,
				keyword:key,
				pageNumber:page,
				pageSize:size,
				isEnd:end,
				area:carea
			},
			dataType : "json",
			success : function(data) {
				$scope.cartoonList = data.data.list;
				$scope.pageNumber = data.data.pageNumber;
				$scope.pageList = new Array();
				var j=0;
				for(var i=$scope.pageNumber;i<$scope.pageNumber+4;i++){
					$scope.pageList[j]=i;
					j++;
				}
				$scope.isLast = data.data.isLast;
				var $container = $('.result');
				$container.imagesLoaded(function() {
					 $container.masonry({
							columnWidth : '.result-item',
							itemSelector : '.result-item',
							isAnimated: true
					});
				});
				$scope.$apply();
			},
			error : function(data) {
			}
		});
	}
	/*瀑布流容易已经初始化完成，之后只能使用reload，不然布局会乱*/
	function queryCartoon2(typeId,key,end,carea,page,size){
		$scope.typeId=typeId;
		$scope.key=key;
		$.ajax({
			url : "../cartoon/query",
			type : "get",
			data:{
				type:typeId,
				keyword:key,
				pageNumber:page,
				pageSize:size,
				isEnd:end,
				area:carea
			},
			dataType : "json",
			success : function(data) {
				$scope.cartoonList = data.data.list;
				$scope.pageNumber = data.data.pageNumber;
				$scope.pageList = new Array();
				var j=0;
				for(var i=$scope.pageNumber;i<$scope.pageNumber+4;i++){
					$scope.pageList[j]=i;
					j++;
				}
				$scope.isLast = data.data.isLast;
				/*瀑布流布局代码
				var $container = $('.result');
				$container.imagesLoaded(function() {
					$container.masonry('destroy');
					$container.masonry({});
				});		*/	
				var $container = $('.result');
				$container.imagesLoaded(function() {
					$container.masonry('destroy');
					$container.masonry();	
				});
				$scope.$apply();
			},
			error : function(data) {
			}
		});
	}
	
	
	$scope.prep = function(){
		var page = $scope.pageNumber;
		if(page==1){
			alert("已经是第一页了！");
		}else{
			page--;
			queryCartoon2($scope.typeId,$scope.key,$scope.isEnd,$scope.area,page,20);
		}
	}
	
	$scope.page = function(page){
		queryCartoon2($scope.typeId,$scope.key,$scope.isEnd,$scope.area,page,20);
	}
	
	$scope.last = function(){
		if($scope.isLast==true){
			alert("已经是最后一页了！");
			return;
		}
		var page = $scope.pageNumber;
		page++;
		queryCartoon2($scope.typeId,$scope.key,$scope.isEnd,$scope.area,page,20);
	}
	
	$scope.love = function(cartoon){
		if(this.isclicked!=null&&this.isclicked==true){
			return;
		}
		this.cartoon.loveCount = cartoon.loveCount+1;
		this.isclicked=true;
		
		$.ajax({
			url : "../cartoon/love",
			type : "POST",
			data : {
				cartoonId:cartoon.id,
				userId:$rootScope.userId
			},
			dataType : "json",
			success : function(data) {
				if (data.code == "000000") {
					$scope.$apply();
				}
			},
			error : function(data) {
			}
	 });
	}
	
	
	
	$scope.items = [ 'angularjs', 'backbone', 'canjs', 'Ember', 'react' ];
	
	function open (size){
		var modalInstance = $modal.open({
			templateUrl : 'myModelContent.html',
			controller : 'ModalInstanceCtrl',
			size : size,
			keyboard:false,
			resolve : {
				items : function() {
					return $scope.items;
				}
			}
		
		});
		modalInstance.result.then(function(selectedItem) {
			$scope.selected = selectedItem;
		}, function() {
		});
	}
	
	function openLogin (size){
		var modalInstance = $modal.open({
			templateUrl : 'myModelLogin.html',
			controller : 'ModalInstanceCtrl',
			size : size,
			keyboard:false,
			resolve : {
				items : function() {
					return $scope.items;
				}
			}
		
		});
		modalInstance.result.then(function(selectedItem) {
			$scope.selected = selectedItem;
		}, function() {
		});
	}
	
	
	$scope.showComment=function(cartoon){
		
		if($rootScope.isLogin!=true){
			openLogin('sm');
			return;
		}

		$rootScope.commentObjectId = cartoon.id;
		open('md');
	}

});