package org.birdnerd.views.bnworkflow;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import org.birdnerd.views.MainLayout;

@PageTitle("BN Workflow")
@Route(value = "workflow", layout = MainLayout.class)
public class BNWorkflowView extends Composite<VerticalLayout> {

  public BNWorkflowView() {
    HorizontalLayout layoutRow = new HorizontalLayout();
    Checkbox checkbox = new Checkbox();
    Button buttonPrimary = new Button();
    VerticalLayout layoutColumn2 = new VerticalLayout();
    TextArea textArea = new TextArea();
    getContent().setWidth("100%");
    getContent().getStyle().set("flex-grow", "1");
    layoutRow.addClassName(Gap.MEDIUM);
    layoutRow.setWidth("100%");
    layoutRow.setHeight("min-content");
    checkbox.setLabel("All species");
    checkbox.setWidth("150px");
    buttonPrimary.setText("Generate");
    buttonPrimary.setWidth("min-content");
    buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    layoutColumn2.setWidth("100%");
    layoutColumn2.getStyle().set("flex-grow", "1");
    textArea.setLabel("Alfred Workflow");
    textArea.setWidth("100%");
    textArea.getStyle().set("flex-grow", "1");
    getContent().add(layoutRow);
    layoutRow.add(checkbox);
    layoutRow.add(buttonPrimary);
    getContent().add(layoutColumn2);
    layoutColumn2.add(textArea);
  }
}
