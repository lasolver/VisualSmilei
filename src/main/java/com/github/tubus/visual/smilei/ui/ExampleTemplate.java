package com.github.tubus.visual.smilei.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.templatemodel.TemplateModel;

/**
 * Simple template example.
 */
@Tag("example-template")
@JsModule("./src/example-template.js")
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ExampleTemplate extends PolymerTemplate<ExampleTemplate.ExampleModel> {

    /**
     * Template model which defines the single "name" property.
     */
    public static interface ExampleModel extends TemplateModel {

        void setMessage(String message);
    }

    public ExampleTemplate(@Autowired Greeter bean) {
        // Set the initial value to the "message" property.
        getModel().setMessage(bean.sayHello());
    }
}