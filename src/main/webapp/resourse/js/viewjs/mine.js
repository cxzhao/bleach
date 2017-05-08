etApp.controller('mineCtrl', function($scope,$rootScope, $location,$http) {	
	
});

etApp.controller('myCommentCtrl', function($scope,$rootScope, $location,$http) {
	
	query(1,20,'','',1);
	
	$scope.search = function(stat){
		query(1,20,'',$('input[name=keyword]').val(),stat);
	}
	
	$scope.deleteCom = function(com){
		if(confirm("确定要删除本篇评论吗")){
			$.ajax({
				url : "../comment/delete",
				type:"POST",
				data : {
					id:com.id
				},
				dataType : "json",
				success : function(data) {
					if (data.code == "000000") {	
						query($scope.pageNumber,$scope.pageSize,$scope.tag,$scope.key,$scope.status);
					}
				},
				error:function(data){
				}
			});
		}
		
	}
	
	function query(page,size,tag,key,stat){
		$scope.tag = tag;
		$scope.key = key;
		$scope.status = stat;
		$scope.keyword = key;
		$.ajax({
			url : "../comment/myComment",
			type:"get",
			data : {
				pageNumber:page,
				pageSize:size,
				keyword:key,
				tagId:tag,
				userId:$rootScope.userId,
				status:stat
			},
			dataType : "json",
			success : function(data) {
				if(data.code == "000004"){
					window.location.href="#/login";
					return;
				}
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
			query(page,$scope.pageSize,$scope.tag,$scope.key,$scope.status);
		}
	}
	
	$scope.currentPage = function(page){
		query(page,$scope.pageSize,$scope.tag,$scope.key,$scope.status);
	}
	
	$scope.last = function(){
		if($scope.isLast==true){
			alert("已经是最后一页了！");
			return;
		}
		var page = $scope.pageNumber;
		page++;
		query(page,$scope.pageSize,$scope.tag,$scope.key,$scope.status);
	}
});

etApp.controller('editMyCommentCtrl', function($scope,$rootScope, $location,$http) {	
	$scope.id = $location.search()["id"];
	queryCommentDetail($scope.id);
	function queryCommentDetail(id){
		if(id==null||typeof(id)=="undefined"){
			return;
		}
		
		$.ajax({
			url : "../comment/detail",
			type : "get",
			data:{
				id:$scope.id
			},
			dataType : "json",
			success : function(data) {
				$scope.comment=data.data;
				var tagList = data.data.tagList;
				var tag = '';
				for(var i=0;i<tagList.length;i++){
					if(i==0){
						tag+=tagList[i].tagName;
					}else{
						tag+=' '+tagList[i].tagName;
					}
				}
				$scope.commentTag = tag;
				$scope.$apply();
			},
			error : function(data) {
			}
		});
	}
	
	$scope.update = function(stat){
		update(stat);
	}
	
	function update(stat){
		if($rootScope.isLogin==false){
			return;
		}
		var intro = $scope.editor.$txt.text();
		$.ajax({
			url : "../comment/update",
			type : "post",
			data:{
				id:$scope.id,
				title:$('input[name=title]').val(),
				tag:$('input[name=tag]').val(),
				context:$scope.editor.$txt.html(),
				introduction:intro.substr(0,150),
				userId:$rootScope.userId,
				status:stat
			},
			dataType : "json",
			success : function(data) {
				if(data.code=='000000'){
					alert("保存成功");
				}
				$scope.$apply();
			},
			error : function(data) {
			}
		});
	}
	
});

etApp.controller('setInfoCtrl', function($scope,$rootScope, $location,$http) {	
	
	$scope.setName = function(){
		$.ajax({
			url : "../user/setName",
			type : "post",
			data:{
				id:$rootScope.userId,
				name:$('input[name=name]').val(),
			},
			dataType : "json",
			success : function(data) {
				if(data.code=='000000'){
					alert("修改成功");
					$rootScope.userName = $('input[name=name]').val();
				}else{
					alert("修改失败");
				}
				$scope.$apply();
			},
			error : function(data) {
			}
		});
	}
	
	$scope.setPass = function(){
		var pass = $('input[name=password]').val();
		var repass = $('input[name=repassword]').val();

		if (typeof(pass) == "undefined" || pass == null || pass == ''){
			$scope.passwordNull=true;
			return;
		}else{
			$scope.passwordNull=false;
		}
		
		if(pass!=repass){
			$scope.isRep = false;
			$scope.$apply();
			return;
		}else{
			$scope.isRep = true;
		}
		var mypass=$.md5(pass);
		$.ajax({
			url : "../user/setPass",
			type : "post",
			data:{
				id:$rootScope.userId,
				pass:mypass,
			},
			dataType : "json",
			success : function(data) {
				if(data.code=='000000'){
					alert("修改成功");
				}else{
					alert("修改失败");
				}
				$scope.$apply();
			},
			error : function(data) {
			}
		});
	}
	
	$scope.setHead=function() {
		var formData = new FormData();
		formData.append('uploadfile', $('#uploadfile')[0].files[0]);
		formData.append('id', $rootScope.userId);
		$.ajax({
		    url: '../file/uploadHeadImage',
		    type: 'POST',
		    cache: false,
		    data: formData,
		    processData: false,
		    contentType: false
		}).done(function(data) {
			if (data.code == "000000") {
				alert("头像上传成功");
				$scope.headImage = data.data;
				$scope.$apply();
			}else{
				alert("头像上传失败");
			}
			
		}).fail(function(data) {
		});
		
	}
});
