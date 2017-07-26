package coopci.ddia.gateway.login.handlers;

import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.http.util.HttpStatus;

import coopci.ddia.Result;
import coopci.ddia.gateway.DemoEngine;
import coopci.ddia.gateway.ICMSAspect;
import coopci.ddia.gateway.ILoginAspect;
import coopci.ddia.util.Funcs;
import coopci.ddia.GrizzlyUtils;

public class RegisterHandler extends HttpHandler {
	public ILoginAspect getEngine() {
		return engine;
	}
	public void setEngine(ILoginAspect engine) {
		this.engine = engine;
	}
	ILoginAspect engine;
	
	public void service(Request request, Response response) throws Exception {
		Method method = request.getMethod();
		if (!method.getMethodString().equals("POST")) {
			response.setStatus(HttpStatus.METHOD_NOT_ALLOWED_405);
			response.getWriter().write(HttpStatus.METHOD_NOT_ALLOWED_405.getReasonPhrase());
			return;
		}
		request.setCharacterEncoding("utf-8");
        String sessid = request.getParameter("sessid");    
        String username = request.getParameter("username");
        String password = request.getParameter("password");        
        Result res = this.engine.register(sessid, username, password);
        GrizzlyUtils.writeJson(response, res);
		return;
    }
}

