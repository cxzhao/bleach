adminApp.controller('newsCtrl', function($scope, $http, $rootScope) {
	 
	reflushTable(1,20,'',0);
	
	$scope.search = function(){
		reflushTable(1,20,$("input[name=keyword]").val(),$('#selectType option:selected').val(),$('#selectStatus option:selected').val());
	}
	
	 function reflushTable(page,size,key,newsType,stat){
		 $scope.key = key;
		 $scope.type=newsType;
		 $.ajax({
				url : "../newsAdmin/query",
				type : "get",
				data : {
					pageNumber:page,
					pageSize:size,
					type:newsType,
					keyword:key,
					status:stat
				},
				dataType : "json",
				success : function(data) {
					if (data.code == "000000") {
						$scope.newsList = data.data.list;
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
						$scope.$apply();
					} else {
						alert(data.message);
					}
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
			reflushTable($scope.pageNumber-1,20,$scope.key,$scope.type);
		}
		
		$scope.currentPage = function(page){
			reflushTable(page,20,$scope.key,$scope.type);
		}
		
		$scope.last = function(){
			if($scope.total==$scope.pageNumber){
				alert("已经是最后一页了");
				return;
			}
			reflushTable($scope.pageNumber+1,20,$scope.key,$scope.type);
		}
		
		$scope.edit = function(row){
			$rootScope.editNewsId = row.id;
			window.location.href="#/editNews";
		}
		
		$scope.deleteNews = function(row){
			if(confirm("删除操作会彻底删除数据，确定要删除吗？")){
				$.ajax({
					url : "../newsAdmin/delete",
					type : "get",
					data : {
						id:row.id
					},
					dataType : "json",
					success : function(data) {
						if (data.code == "000000") {
							alert("删除成功");
							$scope.$apply();
						} else {
							alert(data.message);
						}
					},
					error : function(data) {
						alert("系统异常");
					}
			 });
			}
			
		}

});

adminApp.controller('addNewsCtrl', function($scope, $http, $rootScope) {

	var filePath = '';
	
	$scope.save = function(stat) {
		$.ajax({
			url : "../newsAdmin/add",
			type : "post",
			data : {
				title : $("input[name=title]").val(),
				type:$('#newsType option:selected').val(),
				newsImage:filePath,
				keyword:$("input[name=keyword]").val(),
				source:$("input[name=source]").val(),
				tag:$("input[name=tag]").val(),
				introduction:$("#introduction").val(),
				context:$scope.editor.$txt.html(),
				status:stat
			},
			dataType : "json",
			success : function(data) {
				if (data.code == "000000") {
					alert("保存成功");
					$scope.$apply();
				} else {
					alert(data.message);
				}
			},
			error : function(data) {
				alert("系统异常");
			}
		});
	}
	
	$scope.upload=function() {
		var formData = new FormData();
		formData.append('uploadfile', $('#uploadfile')[0].files[0]);
		formData.append('userId', $rootScope.userId);
		$.ajax({
		    url: '../file/uploadNewsThemeImage',
		    type: 'POST',
		    cache: false,
		    data: formData,
		    processData: false,
		    contentType: false
		}).done(function(data) {
			if (data.code == "000000") {
				filePath = data.data;
				$scope.filePath = filePath;
				alert($scope.filePath);
			}else{
				alert("上传失败！");
			}
			
		}).fail(function(data) {
			alert("上传失败！");
		});
		
	}

});



adminApp.controller('editNewsCtrl', function($scope, $http, $rootScope) {

	var filePath = '';
	query();
	function query(){
		$.ajax({
			url : "../newsAdmin/detail",
			type : "get",
			data : {
				id : $rootScope.editNewsId,
			},
			dataType : "json",
			success : function(data) {
				if (data.code == "000000") {
					$scope.news = data.data;
					var tagList = data.data.tagList;
					var tag = '';
					for(var i=0;i<tagList.length;i++){
						if(i==0){
							tag+=tagList[i].tagName;
						}else{
							tag+=','+tagList[i].tagName;
						}
					}
					$("#newsType option").each(function(){
					    if($(this).val() == $scope.news.type){
					    	$(this).attr("selected",true);
					    }
					 });
					
					$scope.tag = tag;
					$scope.$apply();
				} else {
					alert(data.message);
				}
			},
			error : function(data) {
				alert("系统异常");
			}
		});
	}
	
	$scope.updateReadCount = function(){
		$.ajax({
			url : "../newsAdmin/updateReadCount",
			type : "post",
			data : {
				id:$rootScope.editNewsId,
				readCount: $("input[name=readCount]").val(),
			},
			dataType : "json",
			success : function(data) {
				if (data.code == "000000") {
					alert("更新成功");
					$scope.$apply();
				} else {
					alert(data.message);
				}
			},
			error : function(data) {
				alert("系统异常");
			}
		});
	}
	
	$scope.update = function(stat) {
		$.ajax({
			url : "../newsAdmin/update",
			type : "post",
			data : {
				id:$rootScope.editNewsId,
				title : $("input[name=title]").val(),
				type:$('#newsType option:selected').val(),
				newsImage:filePath,
				keyword:$("input[name=keyword]").val(),
				source:$("input[name=source]").val(),
				tag:$("input[name=tag]").val(),
				introduction:$("#introduction").val(),
				context:$scope.editor.$txt.html(),
				status:stat
			},
			dataType : "json",
			success : function(data) {
				if (data.code == "000000") {
					alert("保存成功");
					$scope.$apply();
				} else {
					alert(data.message);
				}
			},
			error : function(data) {
				alert("系统异常");
			}
		});
	}
	
	$scope.upload=function() {
		var formData = new FormData();
		formData.append('uploadfile', $('#uploadfile')[0].files[0]);
		formData.append('id',$rootScope.editNewsId);
		$.ajax({
		    url: '../file/uploadNewsThemeImage',
		    type: 'POST',
		    cache: false,
		    data: formData,
		    processData: false,
		    contentType: false
		}).done(function(data) {
			if (data.code == "000000") {
				filePath = data.data;
				$scope.filePath = filePath;
				$scope.news.newsImage = filePath;
				$scope.$apply();
				alert($scope.filePath);
			}else{
				alert("上传失败！");
			}
			
		}).fail(function(data) {
			alert("上传失败！");
		});
		
	}

});
