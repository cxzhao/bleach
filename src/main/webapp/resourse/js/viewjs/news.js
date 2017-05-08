etApp.controller('newsCtrl', function($scope, $http,$location,$rootScope) {
	var type = $location.search()["type"];
	if(typeof(type) == "undefined"){
		$scope.newsType = 1;
	}else{
		$scope.newsType = type;
	}
	
	
	query(1,20,'',$scope.newsType,'');
	queryTag(1,20,1);
	loadRecommend(1,5,0);
	
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
					$scope.recommendList = data.data.list;							
				}
				$scope.$apply();
			},
			error : function(data) {
			}
		});
	}
	
//	$scope.search = function(){
//		reflushTable(1,20,$("input[name=keyword]").val(),$('#selectType option:selected').val(),1);
//	}
	
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
					alert("系统异常");
				}
		 });
	 }
	
	
	 function query(page,size,key,newsType,tag){
		 $scope.key = key;
		 $scope.type=newsType;
		 
		 $.ajax({
				url : "../news/query",
				type : "get",
				data : {
					pageNumber:page,
					pageSize:size,
					type:newsType,
					keyword:key,
					status:1,
					tagId:tag
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
				}
		 });
	 }
	 
	 $scope.queryByTag=function(tag){
		 query(1,20,'',$scope.newsType,tag.id)
	 }
	 

	 $scope.prepTag = function(){
			if($scope.tagPageNumber==1){
				return;
			}
			queryTag($scope.tagPageNumber-1,20,1);
		}
		
		$scope.lastTag = function(){
			queryTag($scope.tagPageNumber+1,20,1);
		}
		
	 $scope.prep = function(){
			if($scope.pageNumber==1){
				alert("已经是第一页了");
				return;
			}
			query($scope.pageNumber-1,20,$scope.key,$scope.type,'');
		}
		
		$scope.currentPage = function(page){
			query(page,20,$scope.key,$scope.type,'');
		}
		
		$scope.last = function(){
			if($scope.total==$scope.pageNumber){
				alert("已经是最后一页了");
				return;
			}
			query($scope.pageNumber+1,20,$scope.key,$scope.type,'');
		}
		
		$scope.edit = function(row){
			$rootScope.editNewsId = row.id;
			window.location.href="#/editNews";
		}
	
});

etApp.controller('newsDetailCtrl', function($scope, $http,$location,$rootScope,$modal,$log) {
	var id = $location.search()["id"];
	if(typeof(id) == "undefined"){
		$scope.newsId = '';
	}else{
		$scope.newsId = id;
	}
	query(id);	
//	$scope.search = function(){
//		reflushTable(1,20,$("input[name=keyword]").val(),$('#selectType option:selected').val(),1);
//	}
	
	 function query(newsId){
		 
		 $.ajax({
				url : "../news/detail",
				type : "get",
				data : {
					id:newsId
				},
				dataType : "json",
				success : function(data) {
					if (data.code == "000000") {
						$scope.news = data.data;						
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
			if($scope.pageNumber==1){
				return;
			}
			reflushTable($scope.pageNumber-1,20,$scope.key,$scope.type);
	}
	 
	 $scope.last = function(){
			if($scope.total==$scope.pageNumber){
				return;
			}
			reflushTable($scope.pageNumber+1,20,$scope.key,$scope.type);
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
		
		/*评论*/
		queryUserComment(1,20,$scope.newsId);		
		function queryUserComment(page,size,objId){
			$.ajax({
				url : "../etcom/query",
				type : "get",
				data:{
					objectId:objId,
					pageNumber:page,
					pageSize:size	
				},
				dataType : "json",
				success : function(data) {
					if(data.code=='000000'){
						$scope.pageNumber = data.data.pageNumber;
						$scope.pageSize = data.data.pageSize;
						$scope.isLast = data.data.last;
						$scope.list = data.data.list;
						$scope.$apply();
					}				
				},
				error : function(data) {
				}
			});
		}
		
		$scope.prep = function(){
			if($scope.pageNumber==1){
				return;
			}
			queryUserComment($scope.pageNumber-1,$scope.pageSize,$scope.newsId);
		}
		
		$scope.next = function(){
			if($scope.isLast==true){
				return;
			}
			queryUserComment($scope.pageNumber+1,$scope.pageSize,$scope.newsId);
		}
		
		$scope.saveComment = function(){
			
			if($rootScope.isLogin!=true){
				open('sm');
				return;
			}
			
			if($("#comment-context").val()==''){
				alert("评论内容不能为空");
				return;
			}
			
			$.ajax({
				url : "../etcom/save",
				type : "post",
				data:{
					objectId:$scope.newsId,
					userId:$rootScope.userId,
					context:$("#comment-context").val(),
					name:$rootScope.userName,
					type:2		
				},
				dataType : "json",
				success : function(data) {
					if(data.code=='000000'){
						alert("评论成功！");
						queryUserComment(1,20,$scope.newsId);
					}else if(data.code=='000018'){
						alert("评论内容不能为空！");
					}
					$scope.$apply();
				},
				error : function(data) {
				}
			});
		}
		
	$scope.reply = function(){
			if($rootScope.isLogin!=true){
				open('sm');
				return;
			}

			$.ajax({
				url : "../etcom/reply",
				type : "post",
				data:{
					objId:$scope.objectId,
					userId:$rootScope.userId,
					userName:$rootScope.userName,
					objUserName:$scope.objUserName,
					objUserId:$scope.objUserId,
					context:$("#"+$scope.objectId).find("textarea").val(),
					type:2		
				},
				dataType : "json",
				success : function(data) {
					if(data.code=='000000'){
						alert("回复成功！");
						queryUserComment(1,20,$scope.newsId);
					}
					$scope.$apply();
				},
				error : function(data) {
				}
			});
		}

		
		
		$scope.showReply=function(comment) {
			$scope.objectId = comment.id;
			$scope.objUserId = comment.userId;
			$scope.objUserName = comment.name;
			if(this.displayed!=null&&this.displayed==false){
				$("#" + comment.id).css("display", "none");
				this.displayed = true;
			}else{
				$("#" + comment.id).css("display", "block");
				this.displayed = false;
			}		
		}
		
		$scope.childReplyShow=function(comment,reply){
			
			$scope.objectId = comment.id;
			$scope.objUserId = reply.userId;
			$scope.objUserName = reply.userName;
			
			if(this.displayed!=null&&this.displayed==false){
				$("#" + comment.id).css("display", "none");
				this.displayed = true;
			}else{
				$("#" + comment.id).css("display", "block");
				this.displayed = false;
			}
		}
		
		$scope.love = function(comment){
			if(this.isClicked!=null&&this.isClicked==true){			
				return;
			}
			this.isClicked = true;
			this.comment.agree = comment.agree+1;
			
			$.ajax({
				url : "../etcom/love",
				type : "post",
				data:{
					id:comment.id,
					userId:$rootScope.userId,
				},
				dataType : "json",
				success : function(data) {
					if(data.code=='000000'){
						
					}
					$scope.$apply();
				},
				error : function(data) {
				}
			});
		}
		
		$scope.deleteComment=function(deleteId,objId,ctype){
			
			$.ajax({
				url : "../etcom/delete",
				type : "post",
				data:{
					id:deleteId,
					userId:$rootScope.userId,
					objectId:objId,
					type:ctype
				},
				dataType : "json",
				success : function(data) {
					if(data.code=='000000'){
						queryUserComment(1,20,$scope.newsId);
					}else if(data.code=='000017'){
						alert("已经有人回复了该评论，不能删除！");
					}
					$scope.$apply();
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
		

});