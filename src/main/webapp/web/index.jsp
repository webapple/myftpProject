<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>

<head>
<meta charset="utf-8">
<title>文件上传</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/web/jquery-3.1.1.min.js"></script>
<link type="text/css" href="${pageContext.request.contextPath}/web/css/upload.css">
	<style type="text/css">
	body {
		font-size: 14px;
	}
	
	input {
		vertical-align: middle;
		margin: 0;
		padding: 0
	}
	
	.file-box {
		position: relative;
		width: 312px;
		min-height: 300px;
		margin: 0 auto;
		margin-top: 100px;
		background: #11515c;
		padding: 30px;
	}
	
	.txt {
		height: 22px;
		border: 1px solid #cdcdcd;
		width: 180px;
	}
	
	.btn {
		background-color: #FFF;
		border: 1px solid #CDCDCD;
		height: 24px;
		width: 112px;
		color: #0d3a0c;
	}
	
	.file {
		position: absolute;
		top: 30px;
		right: 60px;
		height: 24px;
		filter: alpha(opacity : 0);
		opacity: 0;
		width: 280px
	}
	
	.result {
		height: 200px;
		padding-top: 30px;
		text-align: center;
		font-size: 30px;
		color: white;
		word-wrap: break-word;
		word-break: break-all;
		overflow: hidden;
	}
	
	.spinner {
	  margin: 100px auto;
	  width: 50px;
	  height: 60px;
	  text-align: center;
	  font-size: 10px;
	  display: none;
	}
	 .FileList >a{
	 color: white;
	 display: block;
	 font-size: 20px;
	 }
	.spinner > div {
	  background-color: #67CF22;
	  height: 100%;
	  width: 6px;
	  display: inline-block;
	   
	  -webkit-animation: stretchdelay 1.2s infinite ease-in-out;
	  animation: stretchdelay 1.2s infinite ease-in-out;
	}
	 
	.spinner .rect2 {
	  -webkit-animation-delay: -1.1s;
	  animation-delay: -1.1s;
	}
	 
	.spinner .rect3 {
	  -webkit-animation-delay: -1.0s;
	  animation-delay: -1.0s;
	}
	 
	.spinner .rect4 {
	  -webkit-animation-delay: -0.9s;
	  animation-delay: -0.9s;
	}
	 
	.spinner .rect5 {
	  -webkit-animation-delay: -0.8s;
	  animation-delay: -0.8s;
	}
	 
	@-webkit-keyframes stretchdelay {
	  0%, 40%, 100% { -webkit-transform: scaleY(0.4) } 
	  20% { -webkit-transform: scaleY(1.0) }
	}
	 
	@keyframes stretchdelay {
	  0%, 40%, 100% {
	    transform: scaleY(0.4);
	    -webkit-transform: scaleY(0.4);
	  }  20% {
	    transform: scaleY(1.0);
	    -webkit-transform: scaleY(1.0);
	  }
	}
	</style>
	
</head>

<body>
	<div class="file-box">
		<form action="" method="post" enctype="multipart/form-data">
			<input type='text' name='textfield' id='textfield' class='txt' />
			<input type='button' class='btn' value='点击选择文件' /> 
			<input type="file"  accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel"
				name="file" class="file" id="iconPic" onchange="getImage()" />
		</form>
		<div class="result">
			<div class="FileList"></div>
			<div class="resultText"></div>
			<div class="spinner">
				<div class="rect1"></div>
				  <div class="rect2"></div>
				  <div class="rect3"></div>
				  <div class="rect4"></div>
				  <div class="rect5"></div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	//上传文件
	function getImage() {
		$(".resultText").html("");
		$(".spinner").show();
		//创建表单数据对象
		var formData = new FormData();
		$("#iconPic")
		//获取file节点
		var fileNode = document.getElementById("iconPic");
		formData.append("file", fileNode.files[0]);
		$.ajax({
			url : "${pageContext.request.contextPath}/main/upload.do",
			data : formData,
			type : "post",
			contentType : false,
			processData : false,
			success : function(data) {
				console.log(data);
				$(".spinner").hide();
				if (data.state == 1) {
					$(".resultText").html(data.data + "上传成功");
					getFilelist();
				} else {
					$(".resultText").html(data.message);
				}
			},
			error:function(e){
				console.log(e);
				$(".spinner").hide();
				$(".resultText").html("上传失败未知原因");
			}
		});
	}
	//获取文件本地路径
	function getObjectURL(file) {
		var url = null;
		if (window.createObjectURL != undefined) {
			url = window.createObjectURL(file);
		} else if (window.URL != undefined) {
			url = window.URL.createObjectURL(file);
		} else if (window.webkitURL != unddfined) {
			url = window.webkitURL.createObjectURL(file);
		}
		return url;
	}
	function getFilelist(){
		$.ajax({
			url : "${pageContext.request.contextPath}/main/getFilelist.do",
			type : "post",
			success : function(data) {
				console.log(data);
				if(data.state){
					$(".FileList").html("");
					var dom = "";
					for(var i = 0;i<data.data.length;i++){
						dom += `<a href="${pageContext.request.contextPath}/main/downLoad.do?filename=\${data.data[i]}">\${data.data[i]}</a>`
					}
					$(".FileList").html(dom);
				}
			},
			error:function(e){
				console.log(e);
			}
		})
	}
	getFilelist();
</script>
</html>
