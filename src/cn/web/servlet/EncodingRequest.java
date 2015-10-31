package cn.web.servlet;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
/**
 * 装饰者模式对HttpServletRequest进行增强。
 * 该类的作用：处理get请求的编码问题。
 * 两种方法：
 * 		1. implements HttpServletRequest(所有的方法都要自己来重写，所以，建议使用第二种方法，也就是通过继承HttpServletRequestWrapper，再对自己的需要进行重写相应的方法)
 * 		2. extends HttpServletRequestWrapper(已经用装饰者模式帮我们重写所有的方法，我们只需要重写自己需要重写的方法就可以了，而免去了对其它方法的进行重写的麻烦)
 * @author Administrator
 *
 */
public class EncodingRequest extends HttpServletRequestWrapper {
	private HttpServletRequest req ;
	public EncodingRequest(HttpServletRequest request) {
		super(request);
		this.req = request;
	}
	/**
	 * 增强getParameter()方法，使其能够自行处理get的编码问题
	 */
	@Override
	public String getParameter(String name) {
		String value = req.getParameter(name);
		try {
			value = new String(value.getBytes("ISO-8859-1"),"UTF-8");//处理get方式
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return value;
	}
}
