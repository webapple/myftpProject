package cn.huahai.ftp.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Component;

@Component
public class FtpUtil {

	private static String hostname = "119.23.240.79";
	private static int port = 21;
	private static String username = "adzj";
	private static String password = "Adzj123@789";

	
	/**
	 * FileInputStream in = new FileInputStream(new
	 * File("F://upload//Win8_Metro.rar")); FtpUtil.uploadFile("119.23.240.79", 21,
	 * "adzj", "Adzj123@789","test/", "Win8_Metro.rar", in);
	 * 
	 * @param path
	 *            ftp文件路径
	 * @param fileName
	 *            文件名称
	 * @param inputStream
	 *            文件输入流
	 * @return
	 */
	public static boolean uploadFile(String path, String filename, InputStream input) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(hostname, port);// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();

			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			ftp.changeWorkingDirectory(path);
			ftp.setControlEncoding("utf-8");
//			设置连接为被动模式
			ftp.enterLocalPassiveMode();
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
//			添加文件缓冲流
			BufferedInputStream Binput = new BufferedInputStream(input);
			ftp.storeFile(filename,Binput);
			input.close();
			ftp.logout();
			success = true;

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}

		return success;
	}
	/**
	 * FtpUtil.downloadFile("119.23.240.79", 21, "adzj", "Adzj123@789","test//",
	 * "lizhuodong.txt", "F://upload"); 下载文件
	 * 
	 * @param hostname
	 * @param port
	 * @param username
	 * @param password
	 * @param remotePath
	 * @param fileName
	 * @param localPath
	 * @return
	 */

	public static boolean downFile(String remotePath,String fileName,String localPath) {  
	    boolean success = false;  
	    FTPClient ftp = new FTPClient();
	    try {  
	        int reply;  
	        ftp.connect(hostname, port);  
	        //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器  
	        ftp.login(username, password);//登录  
	        reply = ftp.getReplyCode();  
	        if (!FTPReply.isPositiveCompletion(reply)) {  
	            ftp.disconnect();  
	            return success;  
	        }  
	        ftp.changeWorkingDirectory(remotePath);//转移到FTP服务器目录  
	        ftp.enterLocalPassiveMode();
	        FTPFile[] fs = ftp.listFiles();  
	        for(FTPFile ff:fs){  
	            if(ff.getName().equals(fileName)){  
	                File localFile = new File(localPath+"/"+ff.getName());  
	                  
	                OutputStream is = new FileOutputStream(localFile);   
	                ftp.setBufferSize(8*1024);
	                ftp.enterLocalPassiveMode();
	                ftp.retrieveFile(ff.getName(), is);  
	                is.close();  
	            }  
	        }  
	          
	        ftp.logout();  
	        success = true;  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } finally {  
	        if (ftp.isConnected()) {  
	            try {  
	                ftp.disconnect();  
	            } catch (IOException ioe) {  
	            }  
	        }  
	    }  
	    return success;  
	} 
	/**
	 * 
	 * @param remotePath FTP服务器目录  
	 * @param fileName 文件名
	 * @param response
	 * @return
	 */
	public static boolean webDownFile(String remotePath,String fileName, HttpServletResponse response) {  
	    boolean success = false;  
	    FTPClient ftp = new FTPClient();
	    try {  
	        int reply;  
	        ftp.connect(hostname, port);  
	        //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器  
	        ftp.login(username, password);//登录  
	        reply = ftp.getReplyCode();  
	        if (!FTPReply.isPositiveCompletion(reply)) {  
	            ftp.disconnect();  
	            return success;  
	        }  
	        ftp.changeWorkingDirectory(remotePath);//转移到FTP服务器目录  
	        ftp.enterLocalPassiveMode();
	        FTPFile[] fs = ftp.listFiles();  
	        for(FTPFile ff:fs){  
	            if(ff.getName().equals(fileName)){  
	            	 response.reset();
                     response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
                     response.addHeader("Content-Length", "" + ff.getSize());
                     OutputStream os = new BufferedOutputStream(response.getOutputStream());
                     response.setContentType("application/octet-stream");
                     ftp.retrieveFile(ff.getName(),os);
                     os.flush();
                     os.close();
	            }  
	        }  
	          
	        ftp.logout();  
	        success = true;  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } finally {  
	        if (ftp.isConnected()) {  
	            try {  
	                ftp.disconnect();  
	            } catch (IOException ioe) {  
	            }  
	        }  
	    }  
	    return success;  
	} 
	/*
	 * FtpUtil.listDirectory("119.23.240.79", 21, "adzj", "Adzj123@789", "test//");
	 * 列出文件的列表 x下面的参数列表分别是 FTP地址，端口号码，用户名，密码，需要列出的文件目录路径
	 */
	public static ArrayList<String> listDirectory(String pathName) {
		ArrayList<String> arFiles = new ArrayList<String>();
		String path = pathName;
		boolean flag = false;
		FTPClient ftpClient = new FTPClient();
		ftpClient.setControlEncoding("utf-8");
		try {
			// 连接FTP服务器
			ftpClient.connect(hostname, port);
			ftpClient.enterLocalPassiveMode();
			// 登录FTP服务器
			ftpClient.login(username, password);
			System.out.println("login===" + ftpClient.login(username, password));
			// 是否成功登录FTP服务器
			int replyCode = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(replyCode)) {// 不行就退出
				ftpClient.disconnect();
				System.out.println("FTP server refused connection.");
//				return flag;
			}

			if (ftpClient.changeWorkingDirectory(path) != true) {
				System.out.println("该目录不存在，请重新输入!!!");
//				return flag;
			} else {
				ftpClient.changeWorkingDirectory(path);
				System.out.println("切换目录成功！！！" + path);
				FTPFile[] files = ftpClient.listFiles();

				System.out.println(files.length);
				// 遍历所有的文件,并取出今天的文件
				for (FTPFile file : files) {
					if (file.isFile()) {
						// 按照指定的格式获取当前日期
						System.out.println(file);

						DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
						String dateString = dateFormat.format(new Date());
//						if (file.getName().contains(dateString)) {
//							arFiles.add(file.getName());
//						}
						arFiles.add(file.getName());
					} else if (file.isDirectory()) {
						// 如果还有目录的话，递归遍历下去
						listDirectory(pathName + file.getName() + "/");
					}
				}
				System.out.println(arFiles);
				System.out.println(arFiles.size());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 输入遍历目录的名称
			Iterator<String> iter = arFiles.iterator();
			int i = 0;
			while (iter.hasNext()) {
				String a = iter.next();
				System.out.println(a);
				i++;
			}
			System.out.println("文件的个数是： " + i);
			try {
				ftpClient.logout();
				ftpClient.disconnect();
				System.out.println("断开连接！！");
				flag = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return arFiles;
	}
}
