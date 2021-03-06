package coopci.ddia.chat.handlers;

import java.util.HashMap;

import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.http.util.HttpStatus;

import coopci.ddia.Result;
import coopci.ddia.chat.Engine;
import coopci.ddia.GrizzlyUtils;

/**
 * 用户之间发送即时聊天消息。
 * */
public class SendMessageHandler extends HttpHandler {
	public Engine getEngine() {
		return engine;
	}
	public void setEngine(Engine engine) {
		this.engine = engine;
	}
	Engine engine;
	public void service(Request request, Response response) throws Exception {
		Method method = request.getMethod();
		if (!method.getMethodString().equals("POST")) {
			response.setStatus(HttpStatus.METHOD_NOT_ALLOWED_405);
			response.getWriter().write(HttpStatus.METHOD_NOT_ALLOWED_405.getReasonPhrase());
			return;
		}
        long touid = Long.parseLong(request.getParameter("to_uid"));
        
        HashMap<String, String> args = new HashMap<String, String>(); 
        for ( String fn : request.getParameterNames()) {
        	if (fn.equals("to_uid"))
        		continue;
        	args.put(fn, request.getParameter(fn));
        }
        Result res = this.engine.sendMessage(touid, args);
        GrizzlyUtils.writeJson(response, res);
		return;
    }
}

