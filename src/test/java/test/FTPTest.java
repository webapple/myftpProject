package test;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Test;

import cn.huahai.ftp.util.FtpUtil;


public class FTPTest {
	/*public static void main(String[] args) throws FileNotFoundException {
		FtpUtil.listDirectory("119.23.240.79", 21, "adzj", "Adzj123@789", "test//");
		FtpUtil.downloadFile("119.23.240.79", 21, "adzj", "Adzj123@789","test//", "backgroup.jpg", "F://upload");
		
		FileInputStream in = new FileInputStream(new File("F://upload//gallery.jpg"));
		FtpUtil.uploadFile("119.23.240.79", 21, "adzj", "Adzj123@789","test/", "gallery.jpg", in);
	}*/
	@Test
	public void uploadFile() throws FileNotFoundException {
		FileInputStream in = new FileInputStream(new File("F://upload//40959.gif"));
		FtpUtil.uploadFile("test//", "40959.gif", in);
	}
	@Test
	public void downloadFile() {
		FtpUtil.downFile("test//", "cell.xlsx", "F://upload//");
//		byte[] bytes =FtpUtil.downFtpFiletobyte("test", "cell.xlsx");
//		System.out.println(bytes.length);
	}
	@Test
	public void fileList() {
		FtpUtil.listDirectory("test//");
	}
}
