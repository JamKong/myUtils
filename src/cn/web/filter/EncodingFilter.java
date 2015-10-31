package cn.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import cn.web.servlet.EncodingRequest;


/**
 * 该过滤器的作用：设置get和post请求的编码
 * 需要在web.xml配置！！
 * 依赖：cn.web.servlet.EncodingRequest.java
 * @author Administrator
 *
 */
public class EncodingFilter implements Filter {

	@Override
	public void destroy() {
		
	}
	@Override
	public void doFilter(ServletRequest servletrequest,
			ServletResponse servletresponse, FilterChain filterchain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) servletrequest;
		
		if(request.getMethod().equals("POST")){
			request.setCharacterEncoding("UTF-8");
			filterchain.doFilter(servletrequest, servletresponse);
		}else if(request.getMethod().equals("GET")){
			EncodingRequest req = new EncodingRequest(request);
			filterchain.doFilter(req, servletresponse);//调包request
		}
	}

	@Override
	public void init(FilterConfig filterconfig) throws ServletException {
		
	}


}
