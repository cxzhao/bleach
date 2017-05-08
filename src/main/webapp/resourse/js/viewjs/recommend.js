etApp.controller('recommendCtrl', function($scope,$rootScope, $location,$http) {	
	
	loadRecommend(1,20,0);
	
	$scope.agree = function(rec){
		if(this.isclicked!=null&&this.isclicked==true){
			return;
		}
		var agree = rec.agree+1;
		this.rec.agree = agree;
		this.isclicked = true;
		
		$.ajax({
			url : "../recommend/agree",
			type : "POST",
			data:{
				userId:$rootScope.userId,
				id:rec.id
			},
			dataType : "json",
			success : function(data) {
				if(data.code=='000000'){
					$scope.$apply();
				}				
			},
			error : function(data) {
				alert("系统异常");
			}
		});
	}
	
	$scope.add=function(){
		$.ajax({
			url : "../recommend/userRecommend",
			type : "post",
			data:{
				userId:$rootScope.userId,
				cartoonName:$("input[name=cartoonName]").val(),
				reason:$("#reason").val()				
			},
			dataType : "json",
			success : function(data) {
				if(data.code=='000000'){
					alert("推荐成功");
				}
			},
			error : function(data) {
				alert("系统异常");
			}
		});
	}
	
	function loadRecommend(page,size,order){
		$.ajax({
			url : "../recommend/query",
			type : "get",
			data:{
				pageNumber:page,
				pageSize:size,
				orderRule:order		
			},
			dataType : "json",
			success : function(data) {
				if(data.code=='000000'){
					$scope.list = data.data.list;
					$scope.pageNumber = data.data.pageNumber;
					$scope.pageSize = data.data.pageSize;
					$scope.total = data.data.total;
					$scope.isLast = data.data.last;
					var max = $scope.pageNumber+5;
					if(max>=$scope.total){
						max = $scope.total;
					}
					var pageList = new Array();
					var j=0;
					for(var i=data.data.pageNumber;i<=max;i++){
						pageList[j] = i;
						j++;
					}
					$scope.pageList = pageList;
				}
				$scope.$apply();
			},
			error : function(data) {
				alert("系统异常");
			}
		});
	}
	
	$scope.prep = function(){
		if($scope.pageNumber==1){
			alert("已经是第一页了");
			return;
		}
		loadRecommend($scope.pageNumber-1,20,0);
	}
	
	$scope.currentPage = function(page){
		loadRecommend(page,20,0);
	}
	
	$scope.last = function(){
		if($scope.total==$scope.pageNumber){
			alert("已经是最后一页了");
			return;
		}
		loadRecommend($scope.pageNumber+1,20,0);
	}
	
});
