package com.github.tubus.visual.smilei.ui;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Route
public class Main extends VerticalLayout {

    HorizontalLayout headLayout = new HorizontalLayout();

    public Main() {

        //Label greeting = new Label("Hui");
        //add(greeting);

        Span greeting = new Span();
        TextField textField = new TextField("dick");
        textField.addValueChangeListener(event -> greeting.setText(event.getValue()));

        InputStream imageStream = getClass().getClassLoader().getResourceAsStream("smileiLogo.svg");
        StreamResource resource = new StreamResource("test-logo.svg", () -> imageStream);
        Image testImage = new Image(resource, "logo");
        Label welcomeLable = new Label("Welcome to Visual Smilei");
        welcomeLable.setWidthFull();
        headLayout.add(testImage, welcomeLable);

        add(headLayout, greeting, textField);
    }

    public String sayHello() {
        return "Hello from bean " + toString();
    }
}