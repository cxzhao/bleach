adminApp.controller('cartoonCtrl', function($scope,$rootScope,$http) {

	reflushTable(1,20,null,null);
	
	$scope.queryCartoon = function(){		
		var key = $("input[name=key]").val();
		$scope.key = key;
		reflushTable(1,20,'',key);
	}
	
	$scope.prepPage = function(){		
		$scope.pageNumber--;
		var pageNumber = $scope.pageNumber;		
		if(pageNumber<=0){
			alert("已经是第一页了");
			reflushTable(1,20,'',$scope.key);
			return;
		}
		reflushTable(pageNumber,20,'',$scope.key);
	}
	
	$scope.nextPage = function(){		
		var pageNumber = $scope.pageNumber+1;
		reflushTable(pageNumber,20,'',$scope.key);
	}
	
	$scope.edit = function(item){
		$rootScope.editCartoonId = item.id;
		window.location.href="#/editCartoon";
	}
	
	$scope.deleteCartoon = function(item){
		if(confirm("删除操作会彻底删除数据，确定要删除吗？")){
			$.ajax({
				url : "../cartoonAdmin/delete",
				data:{id:item.id},
				type:"get",
				dataType : "json",
				success : function(data) {
					if (data.code == "000000") {						
						reflushTable($scope.pageNumber,20,null,null);
					} else {
						alert(data.message);
					}
				},
				error:function(data){
					alert("系统异常");
				}
			});
		}
		
	}
	
	function reflushTable(number,size,typeId,key){
		$.ajax({
			url : "../cartoonAdmin/queryCartoonList",
			type:"get",
			data:{
				pageNumber:number,
				pageSize:size,
				keyword:key,
				typeId:typeId
			},
			dataType : "json",
			success : function(data) {
				if (data.code == "000000") {		
					$scope.list = data.data.list;
					$scope.pageNumber = data.data.pageNumber;
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

adminApp.controller('addCartoonCtrl', function($scope, $http) {
	
	queryType();
	$scope.filePath="";
	var typeId = "";  
	
	$scope.save = function(addStatus){
		
		var filePath = $scope.filePath;
		if(filePath==null){filePath=='';}
		$.ajax({
			url : "../cartoonAdmin/add",
			type:"post",
			data:{
				name:$("input[name=name]").val(),
				englishName:$("input[name=englishName]").val(),
				score:$("input[name=score]").val(),
				status:addStatus,
				country:$("input[name=country]").val(),
				language:$("input[name=language]").val(),
				commentCount:$("input[name=commentCount]").val(),
				years:$("input[name=years]").val(),
				director:$("input[name=director]").val(),
				akira:$("input[name=akira]").val(),
				role:$("input[name=role]").val(),
				shortIntro:$("#shortIntro").val(),
				introduction:$("#introduction").val(),
				loveCount:$("#loveCount").val(),
				keyword:$("input[name=key]").val(),
				type:typeId,
				themeImage:filePath,
				isEnd:$('#isEnd option:selected').val(),
				author:$('input[name=author]').val()
			},
			dataType : "json",
			success : function(data) {
				if (data.code == "000000") {		
					alert(data.message);
				} else {
					alert(data.message);
				}
			},
			error:function(data){
				alert("系统异常");
			}
		});
	}
	
	$scope.upload=function() {
		var formData = new FormData();
		formData.append('uploadfile', $('#uploadfile')[0].files[0]);
		$.ajax({
		    url: '../file/uploadThemeImage',
		    type: 'POST',
		    cache: false,
		    data: formData,
		    processData: false,
		    contentType: false
		}).done(function(data) {
			if (data.code == "000000") {
				$scope.filePath = data.data;
				alert($scope.filePath);
			}else{
				alert("上传失败！");
			}
			
		}).fail(function(data) {
			alert("上传失败！");
		});
		
	}
	
	function queryType(){
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

adminApp.controller('editCartoonCtrl', function($scope,$rootScope,$http) {
	$scope.id = $rootScope.editCartoonId;
	loadType();
	load($scope.id);
	var typeId='';
	
	$scope.updateThemeImage=function() {
		var formData = new FormData();
		formData.append('uploadfile', $('#uploadfile')[0].files[0]);
		formData.append('id', $scope.id);
		$.ajax({
		    url: '../file/updateThemeImage',
		    type: 'POST',
		    cache: false,
		    data: formData,
		    processData: false,
		    contentType: false
		}).done(function(data) {
			if (data.code == "000000") {
				$scope.filePath = data.data;
				alert($scope.filePath);
			}else{
				alert("更新失败");
			}
			
		}).fail(function(data) {
			alert("上传失败！");
		});
		
	}
	
	$scope.updateLove = function(){
		$.ajax({
			url : "../cartoonAdmin/updateLove",
			type:"post",
			data:{
				id:$scope.id,
				love:$("input[name=loveCount]").val()
			},
			dataType : "json",
			success : function(data) {
				if (data.code == "000000") {		
					alert(data.message);
				} else {
					alert(data.message);
				}
			},
			error:function(data){
				alert("系统异常");
			}
		});
	}
	
	$scope.updateScore = function(){
		$.ajax({
			url : "../cartoonAdmin/updateScore",
			type:"post",
			data:{
				id:$scope.id,
				score:$("input[name=score]").val()
			},
			dataType : "json",
			success : function(data) {
				if (data.code == "000000") {		
					alert(data.message);
				} else {
					alert(data.message);
				}
			},
			error:function(data){
				alert("系统异常");
			}
		});
	}
	
	$scope.update = function(updateStatus){
		alert($('#isEnd option:selected').val());
		$('input:checkbox[name=type]:checked').each(function(i){  
	        if(0==i){  
	        	typeId = $(this).val();  
	        }else{  
	        	typeId += (","+$(this).val());  
	        }
	    });
		$.ajax({
			url : "../cartoonAdmin/update",
			type:"post",
			data:{
				id:$scope.id,
				name:$("input[name=name]").val(),
				englishName:$("input[name=englishName]").val(),
				status:updateStatus,
				country:$("input[name=country]").val(),
				language:$("input[name=language]").val(),
				themeImage:$scope.filePath,
				years:$("input[name=years]").val(),
				director:$("input[name=director]").val(),
				akira:$("input[name=akira]").val(),
				role:$("input[name=role]").val(),
				shortIntro:$("#shortIntro").val(),
				introduction:$scope.editor.$txt.html(),
				keyword:$("input[name=key]").val(),
				type:typeId,
				isEnd:$('#isEnd option:selected').val(),
				author:$('input[name=author]').val()
			},
			dataType : "json",
			success : function(data) {
				if (data.code == "000000") {		
					alert(data.message);
				} else {
					alert(data.message);
				}
			},
			error:function(data){
				alert("系统异常");
			}
		});
	}
	
	function load(id){
		$.ajax({
			url : "../cartoonAdmin/cartoonDetail",
			type:"get",
			data:{id:$scope.id},
			dataType : "json",
			success : function(data) {
				
				if (data.code == "000000") {						
					$scope.cartoon = data.data;
					$scope.wangEditorText = $scope.cartoon.introduction;
					if(data.data!=null){
						var akiraList = data.data.akiraList;
						var roleList =  data.data.roleList;
						var directorList =  data.data.directorList;
						var akira = ""; 
						
						for(var i=0;i<akiraList.length-1;i++){
							akira = akira+akiraList[i].akiraName+",";
						}
						if(akiraList.length!=0){
							akira = akira+akiraList[akiraList.length-1].akiraName;
						}else{
							akira =akiraList[0].akiraName;
						}				
						
						var role = ""; 
						for(var i=0;i<roleList.length-1;i++){
							role = role+roleList[i].roleName+",";
						}
						if(roleList.length!=0){
							role = role+roleList[roleList.length-1].roleName;
						}else{
							role =roleList[0].roleName;
						}	
						
						var director = ""; 
						for(var i=0;i<directorList.length-1;i++){
							director = director+directorList[i].directorName+",";
						}
						if(directorList.length!=0){
							director = director+directorList[directorList.length-1].directorName;
						}else{
							director = directorList[0].directorName;
						}
						$scope.director = director;
						$scope.role = role;
						$scope.akira = akira;
						$("#isEnd option").each(function(){
						    if($(this).val() == $scope.cartoon.isEnd){
						    $(this).attr("selected",true);
						    }
						  });
					}					
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
	
	function loadType(){
		$.ajax({
			url : "../cartoonType/queryAll",
			type:"get",
			dataType : "json",
			success : function(data) {				
				if (data.result.code == "000000") {						
					if(data.result.data!=null){
						$scope.typeList = data.result.data;	
						setType();
					}
					$scope.$apply();
				} else {
					alert(data.result.message);
				}
			},
			error:function(data){
				alert("系统异常");
			}
		});
	}
	
	function setType(){
		$.ajax({
			url : "../cartoonType/queryByCartoonId",
			type:"get",
			data:{cartoonId:$scope.id},
			dataType : "json",
			success : function(data) {				
				if (data.result.code == "000000") {						
					if(data.result.data!=null){
						var list = data.result.data;
						var typeList = $scope.typeList;
						for(var i=0;i<list.length;i++){
							for(var j=0;j<typeList.length;j++){
								if(list[i].id==typeList[j].id){
									typeList[j].ischecked=true;									
								}
							}
						}
					}					
				} else {
					alert(data.result.message);
				}
				$scope.typeList = typeList;
				
				var i=0;
				$("#type :input").each(function() {
					var id = $(this).attr("value");
					if(id==$scope.typeList[i].id){
						if($scope.typeList[i].ischecked==true){
							$(this).prop("checked", "checked");
						}
					}
					i++;
				});
				$scope.$apply();
			},
			error:function(data){
				alert("系统异常");
			}
		});
	}
	
//	$scope.$on('ngRepeatFinished', function(
//			ngRepeatFinishedEvent) {	
//		var i=0;
//		$("#type :input").each(function() {
//			var id = $(this).attr("value");
//			if(id==$scope.typeList[i].id){
//				if($scope.typeList[i].ischecked==true){
//					$(this).prop("checked", "checked");
//				}
//			}
//			i++;
//		});
//	});
	
	
});
//循环结束指令
adminApp.directive('onFinishRenderFilters', function($timeout) {
	return {
		restrict : 'A',
		link : function(scope, element, attr) {
			if (scope.$last == true) {
				$timeout(function() {
					scope.$emit('ngRepeatFinished');
				});
			}
		}
	};
});


