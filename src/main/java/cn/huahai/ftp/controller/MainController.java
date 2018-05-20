package cn.huahai.ftp.controller;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.huahai.ftp.bean.ResponseResult;
import cn.huahai.ftp.util.FtpUtil;



@Controller
@RequestMapping("/main")
public class MainController {
	
	@RequestMapping("/showIndex.do")
	public String showIndex() {
		return "index";
	}
	/**
	 * 
	 * @param file
	 * @return
	 */
	@RequestMapping("/upload.do")
	@ResponseBody
	public ResponseResult<String> upload(HttpSession session, @RequestParam("file") MultipartFile file){
		ResponseResult<String> rr = new ResponseResult<String>();
		try {
			//获取服务器的真实路径
//			String path =  session.getServletContext().getRealPath("/");
//			File dirFile = new File(path,"/upload/"+file.getOriginalFilename());
			System.out.println(file.getOriginalFilename()+"文件流已经上传到服务器");
			//获取文件输入流
			InputStream input = file.getInputStream();
			FtpUtil.uploadFile("test//", file.getOriginalFilename(), input);
			System.out.println("更新生效");
			System.out.println(file.getOriginalFilename()+"文件已经上传到ftp");
//			返回文件名
			rr.setData(file.getOriginalFilename());
			rr.setMessage("上传成功");
			rr.setState(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rr.setData(file.getOriginalFilename());
			rr.setMessage("上传失败，请重新上传（可能是网络原因），或者联系后台管理员");
			rr.setState(0);
		}
		
		return rr;
	}
	
	@RequestMapping("/downLoad.do")
	@ResponseBody
	public void downLoad(HttpServletResponse response,String filename) {
//		try {
//			String file = URLEncoder.encode(filename,"utf-8");
//			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//			response.setHeader("Content-Disposition","attachment;filename=\""+file+"\"");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		FtpUtil.webDownFile("test//", filename, response);
//		System.out.println("开始下载");
////		FtpUtil.downFile("test//", "cell.xlsx", "F://upload//");
//		System.out.println("下载结束");
	}
	
	@RequestMapping("/getFilelist.do")
	@ResponseBody
	public ResponseResult<ArrayList<String>> getFilelist() {
		ResponseResult<ArrayList<String>> rr = new ResponseResult<ArrayList<String>>();
		rr.setData(FtpUtil.listDirectory("test//"));
		rr.setMessage("查询成功");
		rr.setState(1);
		return rr;
	}
}
