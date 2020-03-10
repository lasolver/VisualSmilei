package com.github.tubus.visual.smilei.ui;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
public class Main extends VerticalLayout {

    public Main() {

        Label greeting = new Label("Hui");
        add(greeting);
    }

    public String sayHello() {
        return "Hello from bean " + toString();
    }
}