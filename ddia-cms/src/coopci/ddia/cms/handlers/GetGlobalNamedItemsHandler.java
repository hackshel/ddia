package coopci.ddia.cms.handlers;

import java.util.HashSet;

import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.http.util.HttpStatus;

import coopci.ddia.Result;
import coopci.ddia.cms.Engine;
import coopci.ddia.util.Funcs;
import coopci.ddia.GrizzlyUtils;

/**
 *  获取container_id表示的容器的成员。
 * */
public class GetGlobalNamedItemsHandler extends HttpHandler {
	public Engine getEngine() {
		return engine;
	}
	public void setEngine(Engine engine) {
		this.engine = engine;
	}
	Engine engine;
	public void service(Request request, Response response) throws Exception {
		Method method = request.getMethod();
		if (!method.getMethodString().equals("GET")) {
			response.setStatus(HttpStatus.METHOD_NOT_ALLOWED_405);
			response.getWriter().write(HttpStatus.METHOD_NOT_ALLOWED_405.getReasonPhrase());
			return;
		}
        long uid = Long.parseLong(request.getParameter("uid"));
        
        HashSet<String> fields = Funcs.csvToHashSet(request.getParameter("fields"));
        
        int start = Funcs.parseInt(request.getParameter("start"), 0);
        if (start < 0)
        	start = 0;
        int limit = Funcs.parseInt(request.getParameter("limit"), 10);
        
        
        Result res = this.engine.getGlobalNamedItems(uid, fields, start, limit);
        GrizzlyUtils.writeJson(response, res);
		return;
    }
}

