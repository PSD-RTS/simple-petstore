package org.testinfected.molecule.middlewares;

import org.testinfected.molecule.Application;
import org.testinfected.molecule.Request;
import org.testinfected.molecule.Response;
import org.testinfected.molecule.routing.Route;
import org.testinfected.molecule.routing.RouteBuilder;
import org.testinfected.molecule.routing.RouteSet;

import java.util.ArrayList;
import java.util.List;

public class Router implements Application, RouteSet {

    private Application defaultApp;

    public static Router draw(RouteBuilder routeBuilder) {
        Router router = new Router();
        routeBuilder.build(router);
        return router;
    }

    private final List<Route> routingTable = new ArrayList<Route>();

    public Router() {
        this(new NotFound());
    }

    public Router(final Application fallback) {
        defaultsTo(fallback);
    }

    public Router defaultsTo(Application app) {
        this.defaultApp = app;
        return this;
    }

    public void add(Route route) {
        routingTable.add(route);
    }

    private Route routeFor(Request request) {
        for (Route route : routingTable) {
            if (route.matches(request)) return route;
        }
        return null;
    }

    public void handle(Request request, Response response) throws Exception {
        Route route = routeFor(request);
        if (route != null)
            route.handle(request, response);
        else
            defaultApp.handle(request, response);
    }
}