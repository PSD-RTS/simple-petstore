package org.testinfected.petstore.controllers;

import org.testinfected.molecule.Application;
import org.testinfected.molecule.Request;
import org.testinfected.molecule.Response;
import org.testinfected.petstore.Page;
import org.testinfected.petstore.order.Order;
import org.testinfected.petstore.order.OrderBook;
import org.testinfected.petstore.order.OrderNumber;

public class ShowOrder implements Application {
    private final OrderBook orderBook;
    private final Page orderPage;

    public ShowOrder(OrderBook orderBook, Page orderPage) {
        this.orderBook = orderBook;
        this.orderPage = orderPage;
    }

    public void handle(Request request, Response response) throws Exception {
        String number = request.parameter("number");
        Order order = orderBook.find(new OrderNumber(number));
        orderPage.render(response, order);
    }
}
