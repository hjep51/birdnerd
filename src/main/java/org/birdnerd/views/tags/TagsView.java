package org.birdnerd.views.tags;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.birdnerd.data.models.HashTag;
import org.birdnerd.data.models.HashTagGroup;
import org.birdnerd.services.HashTagGroupService;
import org.birdnerd.services.HashTagService;
import org.birdnerd.views.MainLayout;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@Slf4j
@PageTitle("Tags")
@Route(value = "tags/:tagId?/:action?(edit)", layout = MainLayout.class)
@Uses(Icon.class)
public class TagsView extends Div implements BeforeEnterObserver {

  private final String TAG_ID = "tagId";
  private final String TAG_EDIT_ROUTE_TEMPLATE = "tags/%s/edit";

  private final Grid<HashTag> grid = new Grid<>(HashTag.class, false);

  private TextField name;
  private IntegerField weight;
  private ComboBox<HashTagGroup> hashTagGroup;

  private final Button cancel = new Button("Cancel");
  private final Button save = new Button("Save");

  private final BeanValidationBinder<HashTag> binder;

  private HashTag hashTag;

  private final HashTagService hashTagService;
  private final HashTagGroupService hashTagGroupService;

  public TagsView(HashTagService hashTagService, HashTagGroupService hashTagGroupService) {
    this.hashTagService = hashTagService;
    this.hashTagGroupService = hashTagGroupService;
    addClassNames("tags-view");

    // Create UI
    SplitLayout splitLayout = new SplitLayout();

    createGridLayout(splitLayout);
    createEditorLayout(splitLayout);

    add(splitLayout);

    // Configure Grid
    grid.addColumn("name").setAutoWidth(true);
    grid.addColumn(HashTag::getAsHashTag).setAutoWidth(true).setHeader("HashTag");
    grid.addColumn("weight").setAutoWidth(true);
    grid.addColumn("hashTagGroup.name").setAutoWidth(true).setHeader("HashTag Group");

    grid.setItems(
        query ->
            hashTagService
                .list(
                    PageRequest.of(
                        query.getPage(),
                        query.getPageSize(),
                        VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream()
                .map(
                    hashTag -> {
                      if (hashTag.getHashTagGroup() == null) {
                        hashTag.setHashTagGroup(new HashTagGroup());
                        hashTag.getHashTagGroup().setName("");
                      }
                      return hashTag;
                    }));
    grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

    // when a row is selected or deselected, populate form
    grid.asSingleSelect()
        .addValueChangeListener(
            event -> {
              if (event.getValue() != null) {
                UI.getCurrent()
                    .navigate(String.format(TAG_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
              } else {
                clearForm();
                UI.getCurrent().navigate(TagsView.class);
              }
            });

    // Configure Form
    binder = new BeanValidationBinder<>(HashTag.class);

    // Bind fields. This is where you'd define e.g. validation rules

    binder.bindInstanceFields(this);

    cancel.addClickListener(
        e -> {
          clearForm();
          refreshGrid();
        });

    save.addClickListener(
        e -> {
          try {
            if (this.hashTag == null) {
              this.hashTag = new HashTag();
            }
            binder.writeBean(this.hashTag);
            hashTagService.update(this.hashTag);
            clearForm();
            refreshGrid();
            Notification.show("Data updated");
            UI.getCurrent().navigate(TagsView.class);
          } catch (ObjectOptimisticLockingFailureException exception) {
            Notification n =
                Notification.show(
                    "Error updating the data. Somebody else has updated the record while you were making changes.");
            n.setPosition(Position.MIDDLE);
            n.addThemeVariants(NotificationVariant.LUMO_ERROR);
          } catch (ValidationException validationException) {
            Notification.show("Failed to update the data. Check again that all values are valid");
          }
        });
  }

  @Override
  public void beforeEnter(BeforeEnterEvent event) {
    Optional<Long> hashTagId = event.getRouteParameters().get(TAG_ID).map(Long::parseLong);
    if (hashTagId.isPresent()) {
      Optional<HashTag> hashTagFromBackend = hashTagService.get(hashTagId.get());
      if (hashTagFromBackend.isPresent()) {
        populateForm(hashTagFromBackend.get());
      } else {
        Notification.show(
            String.format("The requested samplePerson was not found, ID = %s", hashTagId.get()),
            3000,
            Position.BOTTOM_START);
        // when a row is selected but the data is no longer available,
        // refresh grid
        refreshGrid();
        event.forwardTo(TagsView.class);
      }
    }
  }

  private void createEditorLayout(SplitLayout splitLayout) {
    Div editorLayoutDiv = new Div();
    editorLayoutDiv.setClassName("editor-layout");

    Div editorDiv = new Div();
    editorDiv.setClassName("editor");
    editorLayoutDiv.add(editorDiv);

    FormLayout formLayout = new FormLayout();
    name = new TextField("Tag");
    name.setPlaceholder("eg. birdwatching");
    name.setRequired(true);
    weight = new IntegerField("Weight");
    weight.setValue(50);
    weight.setRequired(true);
    weight.setStepButtonsVisible(true);
    weight.setMin(0);
    weight.setMax(100);
    hashTagGroup = new ComboBox<>("HashTag Group");
    hashTagGroup.setItems(
        (hashTagGroupService.list(PageRequest.of(0, hashTagGroupService.count())).getContent()));
    hashTagGroup.setItemLabelGenerator(HashTagGroup::getName);
    hashTagGroup.setPlaceholder("Select a group for the tag");

    formLayout.add(name, weight, hashTagGroup);

    editorDiv.add(formLayout);
    createButtonLayout(editorLayoutDiv);

    splitLayout.addToSecondary(editorLayoutDiv);
  }

  private void createButtonLayout(Div editorLayoutDiv) {
    HorizontalLayout buttonLayout = new HorizontalLayout();
    buttonLayout.setClassName("button-layout");
    cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    buttonLayout.add(save, cancel);
    editorLayoutDiv.add(buttonLayout);
  }

  private void createGridLayout(SplitLayout splitLayout) {
    Div wrapper = new Div();
    wrapper.setClassName("grid-wrapper");
    splitLayout.addToPrimary(wrapper);
    wrapper.add(grid);
  }

  private void refreshGrid() {
    grid.select(null);
    grid.getDataProvider().refreshAll();
  }

  private void clearForm() {
    populateForm(null);
  }

  private void populateForm(HashTag value) {
    this.hashTag = value;
    binder.readBean(this.hashTag);
    if (this.hashTag != null && this.hashTag.getHashTagGroup() != null) {
      hashTagGroup.setValue(this.hashTag.getHashTagGroup());
    } else {
      hashTagGroup.clear();
    }
  }
}
