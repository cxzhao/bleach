etApp.controller('detailCtrl', function($scope,$rootScope, $location,$http,$modal,$log) {	
	
	$scope.id = $location.search()["id"];
	queryCartoonDetail($scope.id);
	queryCartoonType($scope.id);
	queryUserComment(1,20,$scope.id);
	/*loadRecommend(1,5,0);*/
	loadHotArticle(1,9);
	function queryCartoonDetail(id){
		$.ajax({
			url : "../cartoon/detail",
			type : "get",
			data:{
				cartoonId:$scope.id
			},
			dataType : "json",
			success : function(data) {
				$scope.cartoon = data.data;
				
				if($scope.cartoon.isEnd!=null){					
					if($scope.cartoon.isEnd==0){
						$scope.isEnd="连载中";
					}else{
						$scope.isEnd="完结";
					}
				}else{
					$scope.isEnd="连载中";
				}
				$scope.role='';
				$scope.akira='';
				$scope.director='';
				for(var i=0;i<data.data.roleList.length;i++){
					if(i==0){
						$scope.role+=data.data.roleList[i].roleName;
					}else{
						$scope.role+="，"+data.data.roleList[i].roleName;
					}
				}
				
				for(var i=0;i<data.data.akiraList.length;i++){
					if(i==0){
						$scope.akira+=data.data.akiraList[i].akiraName;
					}else{
						$scope.akira+="，"+data.data.akiraList[i].akiraName;
					}
				}
				
				for(var i=0;i<data.data.directorList.length;i++){
					if(i==0){
						$scope.director+=data.data.directorList[i].directorName;
					}else{
						$scope.director+="，"+data.data.directorList[i].directorName;
					}
				}
				if($scope.director=='' || $scope.director=='null'){
					$scope.isShowDirector=false;
				}else{
					$scope.isShowDirector=true;
				}
				if(data.data.author=='' || data.data.author=='null'){
					$scope.isShowAuthor=true;
				}else{
					$scope.isShowAuthor=true;
				}
				$scope.$apply();
			},
			error : function(data) {
			}
		});
	}
	
	function queryCartoonType(id){
		$.ajax({
			url : "../cartoonType/queryByCartoonId",
			type : "get",
			data:{
				cartoonId:$scope.id
			},
			dataType : "json",
			success : function(data) {
				var result = data.result.data;							
				$scope.type='';
				for(var i=0;i<result.length;i++){
					if(i==0){
						$scope.type+=result[i].typeName;
					}else{
						$scope.type+="，"+result[i].typeName;
					}
				}
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
			queryCartoon($scope.typeId,$scope.key,page,2);
		}
	}
	
	$scope.page = function(page){
		queryCartoon($scope.typeId,$scope.key,page,2);
	}
	
	$scope.last = function(){
		if($scope.isLast==true){
			alert("已经是最后一页了！");
			return;
		}
		var page = $scope.pageNumber;
		page++;
		queryCartoon($scope.typeId,$scope.key,page,2);
	}
	
	/*评论模块-----------------------------------------*/
	var halfstar = ['很差5.5分','还行6.5分','一般7.5分','推荐8.5分','力荐9.5分'];
	
	var star = ['很差6分','还行7分','一般8分','推荐9分','力荐10分'];
	
	var score = [6,7,8,9,10];
	var halfscore = [5.5,6.5,7.5,8.5,9.5];
	$scope.map = new Map();
	
	var color = "#FF6600";
	var halfColor = "FFCC00";
	
	$scope.clickStart = function(index){
		var key = "start-"+index;
		var count = $scope.map.get(key);
		if('undefined' == typeof(count)){
			count=1;
			$scope.map.set(key,count);
		}else{
			count=count+1;
			$scope.map.set(key,count);
		}
		
		for(var i=1;i<index;i++){
			$("#start-"+i).css("color","#FF6600");
		}
		if(index==1){			
			if(count==15){
				count=1;
				$scope.map.set(key,count);
			}
			if(count>2){				
				var lowscore = 6-0.5*(count-2)
				$("#span-score").html("很差"+lowscore+"分");
				$rootScope.score = lowscore;
				//颜色变化
				if((count%2)==0){
					$("#start-"+index).css("color","#FF6600");
				}else{
					$("#start-"+index).css("color","#FFCC00");
				}
			}else{
				if((count%2)==0){
					$("#start-"+index).css("color","#FF6600");
					$("#span-score").html(star[index-1]);			
					$rootScope.score = score[index-1];
				}else{
					$("#start-"+index).css("color","#FFCC00");
					$("#span-score").html(halfstar[index-1]);			
					$rootScope.score = halfscore[index-1];
				}
			}
		}else{
			if((count%2)==0){
				$("#start-"+index).css("color","#FF6600");
				$("#span-score").html(star[index-1]);			
				$rootScope.score = score[index-1];
			}else{
				$("#start-"+index).css("color","#FFCC00");
				$("#span-score").html(halfstar[index-1]);			
				$rootScope.score = halfscore[index-1];
			}
		}

		for(var i=index+1;i<=5;i++){
			$("#start-"+i).css("color","#666666");
		}
		
	}
	
	$scope.overStart = function(index){
		$scope.map = new Map();
		for(var i=1;i<index;i++){
			$("#start-"+i).css("color","#FF6600");
		}
		
		$("#start-"+index).css("color","#FFCC00");
		
		for(var i=index+1;i<=5;i++){
			$("#start-"+i).css("color","#666666");
		}
		$("#span-score").html(halfstar[index-1]);		
		$rootScope.score = halfscore[index-1];
	}
	
	$scope.submitComment = function(){
		
		if($rootScope.isLogin!=true){
			openLogin('sm');
			return;
		}
		
		$.ajax({
			url : "../etcom/save",
			type : "post",
			data:{
				objectId:$scope.id,
				userId:$rootScope.userId,
				score:$rootScope.score,
				context:$("#comment-context").val(),
				name:$rootScope.userName,
				type:0	
			},
			dataType : "json",
			success : function(data) {
				if(data.code=='000000'){
					alert("评论成功！");
					queryUserComment(1,20,$scope.id);
					$scope.$apply();
				}
				
			},
			error : function(data) {
			}
		});
	}
	
	$scope.items = [ 'angularjs', 'backbone', 'canjs', 'Ember', 'react' ];
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
	
	/*----------------------查询评论------------------------------*/
	function queryUserComment(page,size,objId){
		$.ajax({
			url : "../etcom/query",
			type : "get",
			data:{
				objectId:objId,
				pageNumber:page,
				pageSize:size,	
				type:0
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
	
	$scope.prep = function(){
		if($scope.pageNumber==1){
			return;
		}
		queryUserComment($scope.pageNumber-1,$scope.pageSize,$scope.id);
	}
	
	$scope.next = function(){
		if($scope.isLast==true){
			return;
		}
		queryUserComment($scope.pageNumber+1,$scope.pageSize,$scope.id);
	}
	
	/*------------------------------查询推荐------------------------------------*/
	/*function loadRecommend(page,size,order){
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
	}*/
	
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
				}
				$scope.$apply();
			},
			error : function(data) {
			}
		});
	}
});

etApp.controller('commentDetailCtrl', function($scope,$rootScope, $location,$http,$modal,$log) {
	
	$scope.id = $location.search()["id"];
	queryCommentDetail($scope.id);
	
	queryUserComment(1,20,$scope.id);
	
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
	
	function queryUserComment(page,size,objId){
		$.ajax({
			url : "../etcom/query",
			type : "get",
			data:{
				objectId:objId,
				pageNumber:page,
				pageSize:size,
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
		queryUserComment($scope.pageNumber-1,$scope.pageSize,$scope.id);
	}
	
	$scope.next = function(){
		if($scope.isLast==true){
			return;
		}
		queryUserComment($scope.pageNumber+1,$scope.pageSize,$scope.id);
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
				objectId:$scope.id,
				userId:$rootScope.userId,
				context:$("#comment-context").val(),
				name:$rootScope.userName,
				type:1		
			},
			dataType : "json",
			success : function(data) {
				if(data.code=='000000'){
					alert("评论成功！");
					queryUserComment(1,20,$scope.id);
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
				type:1		
			},
			dataType : "json",
			success : function(data) {
				if(data.code=='000000'){
					alert("回复成功！");
					queryUserComment(1,20,$scope.id);
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
					queryUserComment(1,20,$scope.id);
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
