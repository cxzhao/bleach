adminApp.controller('recommendCtrl', function($scope,$rootScope, $location,$http) {	
	
	loadRecommend(1,20,1);
	
	
	function loadRecommend(page,size,order){
		$.ajax({
			url : "../recommend/adminQuery",
			type : "get",
			data:{
				pageNumber:page,
				pageSize:size
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
	
	$scope.deleteRow = function(row){
		if(row.status==0){
			alert("不能删除正常数据");
			return;
		}
		if(confirm("删除操作会彻底删除数据，确定要删除吗？")){
			this.item=null;
			$.ajax({
				url : "../recommend/delete",
				type : "post",
				data:{
					id:row.id,
					status:row.status
					},
				dataType : "json",
				success : function(data) {
					if(data.code!='000000'){
						alert("操作失败");
					}
					$scope.$apply();
				},
				error : function(data) {
					alert("系统异常");
				}
			});
		}
		
	}
	$scope.check = function(row,stat){
		this.item.status=stat;
		$.ajax({
			url : "../recommend/check",
			type : "post",
			data:{
				id:row.id,
				status:stat
			},
			dataType : "json",
			success : function(data) {
				if(data.code=='000000'){
					alert("已审核");
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
