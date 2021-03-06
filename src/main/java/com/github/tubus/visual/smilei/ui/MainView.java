package com.github.tubus.visual.smilei.ui;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

//@Route()
public class MainView extends VerticalLayout implements LocaleChangeObserver {

    private RouterLink link;

    public MainView(@Autowired Greeter greeter,
                    @Autowired ExampleTemplate template) {
        H1 heading = new H1("Vaadin + Spring examples");

        Label greeting = new Label(greeter.sayHello());
        Style grretingStyle = greeting.getElement().getStyle();
        grretingStyle.set("display", "block");
        grretingStyle.set("margin-bottom", "10px");

        Button button = new Button("Switch language to Chinese",
                event -> getUI().get().setLocale(Locale.CHINESE));

        link = new RouterLink(
                getTranslation("root.navigate_to_component"),
                ViewComponent.class);

        Style linkStyle = link.getElement().getStyle();
        linkStyle.set("display", "block");
        linkStyle.set("margin-bottom", "10px");

        add(heading, greeting, button, link, template);
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        link.setText(
                getTranslation("root.navigate_to_component"));
    }

}