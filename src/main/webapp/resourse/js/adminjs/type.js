//var app = angular.module('typeAdmin', []);
adminApp.controller('typeAdminCtrl', function($scope, $http,$rootScope) {
	reflushTable();
	$scope.addType = function() {
		
		var type = $("input[name=typeName]").val();
		if(type==null||type==''){
			return;
		}
		$.ajax({
			url : "../cartoonType/add",
			type:"post",
			data : {
				typeName : type,
			},
			dataType : "json",
			success : function(data) {
				if (data.result.code == "000000") {					
					alert("数据保存成功！");
					reflushTable()
				} else {
					alert(data.result.message);
				}
			},
			error:function(data){
				alert("系统异常");
			}
		});
	}
	
	$scope.deleteType = function(item){
		if(confirm("删除操作会彻底删除数据，确定要删除吗？")){
			var id = item.id;
			$.ajax({
				url : "../cartoonType/delete",
				type:"post",
				data:{typeId:id},
				dataType : "json",
				success : function(data) {
					if (data.result.code == "000000") {	
						alert("成功删除数据，并且删除了"+data.result.data.deleteRelationCount+"条关联数据");
						reflushTable();
					} else {
						alert(data.result.message);
					}
				},
				error:function(data){
					alert("系统异常");
				}
			});
		}	
	}
	
	function reflushTable(){
		$.ajax({
			url : "../cartoonType/queryAll",
			type:"get",
			dataType : "json",
			success : function(data) {
				if (data.result.code == "000000") {		
					$scope.typelist = data.result.data;
					$scope.$apply();
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