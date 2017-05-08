etApp.controller('commentCtrl', function($scope,$rootScope,$location,$http) {	
	
	query(1,20,'','');
	queryTag(1,20,0);
	loadHotArticle(1,10);
	
	function queryTag(page,size,tagType){		 
		 $.ajax({
				url : "../tag/hotTag",
				type : "get",
				data : {
					pageNumber:page,
					pageSize:size,
					type:tagType,
				},
				dataType : "json",
				success : function(data) {
					if (data.code == "000000") {
						$scope.tagList = data.data.list;
						$scope.tagPageNumber = data.data.pageNumber;
						$scope.$apply();
					} else {
						alert(data.message);
					}
				},
				error : function(data) {
				}
		 });
	 }
	
	 $scope.prepTag = function(){
			if($scope.tagPageNumber==1){
				return;
			}
			queryTag($scope.tagPageNumber-1,20,0);
	}
		
	$scope.lastTag = function(){
		queryTag($scope.tagPageNumber+1,20,0);
	}
		
	function query(page,size,tag,key){
		$scope.tag = tag;
		$scope.key = key;
		$.ajax({
			url : "../comment/query",
			type:"get",
			data : {
				pageNumber:page,
				pageSize:size,
				keyword:key,
				tagId:tag,
				userId:'',
				status:1
			},
			dataType : "json",
			success : function(data) {
				if (data.code == "000000") {	
					$scope.commentList = data.data.list;
					$scope.pageNumber = data.data.pageNumber;
					$scope.pageSize = data.data.pageSize;
					$scope.isLast = data.data.last;
					$scope.pageList = new Array();
					var j=0;
					for(var i=$scope.pageNumber;i<$scope.pageNumber+4;i++){
						$scope.pageList[j]=i;
						j++;
					}
					$scope.isLast = data.data.isLast;		
					$rootScope.$apply();
					
				}
			},
			error:function(data){
			}
		});
	}
	
	$scope.prep = function(){
		var page = $scope.pageNumber;
		if(page==1){
			alert("已经是第一页了！");
		}else{
			page--;
			query(page,$scope.pageSize,$scope.tag,$scope.key);
		}
	}
	
	$scope.currentPage = function(page){
		query(page,$scope.pageSize,$scope.tag,$scope.key);
	}
	
	$scope.last = function(){
		if($scope.isLast==true){
			alert("已经是最后一页了！");
			return;
		}
		var page = $scope.pageNumber;
		page++;
		query(page,$scope.pageSize,$scope.tag,$scope.key);
	}
	
	function loadHotArticle(page,size){
		$.ajax({
			url : "../comment/hot",
			type : "get",
			data:{
				pageNumber:page,
				pageSize:size,
			},
			dataType : "json",
			success : function(data) {
				if(data.code=='000000'){
					$scope.articleList = data.data.list;
					$scope.pageNumberHot = data.data.pageNumber;
					$scope.isLastHot = data.data.last;
				}
				$scope.$apply();
			},
			error : function(data) {
			}
		});
	}
	
	$scope.nextHotArticle=function(){
		if($scope.isLastHot==true){
			return;
		}
		loadHotArticle($scope.pageNumberHot,10);
	}
	
	
	$scope.login = function(){		
		var mail = $("input[name=mail]").val();
		var password = $.md5($("input[name=password]").val());
		
		$.ajax({
			url : "../user/login",
			type:"post",
			data : {
				email:mail,
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
					$("#myModal").modal('hide');
					$rootScope.$apply();
				} else {
					alert(data.message);
				}
			},
			error:function(data){
			}
		});
	}		
	
});


etApp.controller('editCommentCtrl', function($scope,$rootScope,$location,$http,$modal,$log){
	
	$scope.items = [ 'angularjs', 'backbone', 'canjs', 'Ember', 'react' ];
	// open click
	$scope.open = function(size) {
		open (size);
	}
	
	function open (size){
		var modalInstance = $modal.open({
			templateUrl : 'myModelContent.html',
			controller : 'ModalInstanceCtrl', // specify controller for modal
			size : size,
			keyboard:false,
			backdrop:'static',
			resolve : {
				items : function() {
					return $scope.items;
				}
			}
		});
		// modal return result
		modalInstance.result.then(function(selectedItem) {
			$scope.selected = selectedItem;
		}, function() {
			$log.info('Modal dismissed at: ' + new Date())
		});
	}
	
	if($rootScope.isLogin!=true){
		open('sm');
	}
	
	$scope.save=function(stat){	
	
		var ctitle = $("input[name=title]").val();
		var cTag = $("input[name=tag]").val();
		var cContext = $scope.editor.$txt.html();
		var intro = $scope.editor.$txt.text();
		$.ajax({
			url : "../comment/add",
			type:"post",
			data : {
				title : ctitle,
				tag:cTag,
				userId:$rootScope.userId,
				userName:$rootScope.userName,
				status:stat,
				introduction:intro.substr(0,150),
				context:cContext
			},
			dataType : "json",
			success : function(data) {
				if (data.code == "000000") {	
					alert("保存成功");
				} else {
					alert(data.message);
				}
			},
			error:function(data){
			}
		});
	}
	
});
