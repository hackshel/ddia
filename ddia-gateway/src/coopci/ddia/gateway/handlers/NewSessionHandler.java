package coopci.ddia.gateway.handlers;

import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.http.util.HttpStatus;

import coopci.ddia.Result;
import coopci.ddia.gateway.DemoEngine;
import coopci.ddia.results.DictResult;
import coopci.ddia.GrizzlyUtils;

public class NewSessionHandler extends HttpHandler {
	public DemoEngine getEngine() {
		return engine;
	}
	public void setEngine(DemoEngine engine) {
		this.engine = engine;
	}
	DemoEngine engine;
	public void service(Request request, Response response) throws Exception {
		Method method = request.getMethod();
		if (!method.getMethodString().equals("POST")) {
			response.setStatus(HttpStatus.METHOD_NOT_ALLOWED_405);
			response.getWriter().write(HttpStatus.METHOD_NOT_ALLOWED_405.getReasonPhrase());
			return;
		}
		DictResult res = this.engine.startNewSession();
        GrizzlyUtils.writeJson(response, res);
		return;
    }
}

