$("#demo-upload").dropzone({
    url: uploadUrl+"/main/upload.do", //上传地址         
    method: "post", //方式        
    addRemoveLinks: true,
    maxFiles: 10,
    maxFilesize: 512,
    uploadMultiple: false,
    parallelUploads: 100,
    previewsContainer: false,
    acceptedFiles: ".pdf, .doc,.txt,.docx,.xlsx,.xls",
    success: function(file, response, e) {
        console.log(response);
        if(response.state){
        	alert("上传成功")
        }
    }
});