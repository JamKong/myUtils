package cn.tool;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import sun.misc.BASE64Encoder;

public class DownUtils {
	/**
	 * 改方法的作用：用来处理文件下载的文件名乱码的问题
	 * 
	 * @param filename
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static String filenameEncoding(String filename, HttpServletRequest request) throws IOException {
		String agent = request.getHeader("User-Agent"); //获取浏览器
		if (agent.contains("Firefox")) {
			BASE64Encoder base64Encoder = new BASE64Encoder();
			filename = "=?utf-8?B?"
					+ base64Encoder.encode(filename.getBytes("utf-8"))
					+ "?=";
		} else if(agent.contains("MSIE")) {
			filename = URLEncoder.encode(filename, "utf-8");
		} else {
			filename = URLEncoder.encode(filename, "utf-8");
		}
		return filename;
	}
}
