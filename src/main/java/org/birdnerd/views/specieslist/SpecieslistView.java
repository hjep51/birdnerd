package org.birdnerd.views.specieslist;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.server.StreamResource;
import lombok.extern.slf4j.Slf4j;
import org.birdnerd.data.Species;
import org.birdnerd.data.enums.SpeciesCategory;
import org.birdnerd.data.enums.SpeciesStatus;
import org.birdnerd.data.enums.SpeciesType;
import org.birdnerd.services.SpeciesService;
import org.birdnerd.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.io.*;
import java.util.Optional;

@Slf4j
@PageTitle("Species list")
@Route(value = "species/:speciesID?/:action?(edit)", layout = MainLayout.class)
@Uses(Icon.class)
public class SpecieslistView extends Div implements BeforeEnterObserver {

    private final String SPECIES_ID = "speciesID";
    private final String SPECIES_EDIT_ROUTE_TEMPLATE = "species/%s/edit";

    @Value("${birdnerd.imagepath}")
    private String IMAGE_PATH;

    private final Grid<Species> grid = new Grid<>(Species.class, false);

    private TextField danishName;
    private TextField latinName;
    private TextField englishName;
    private TextField euringCode;
    private DatePicker firstObservation;
    private ComboBox<SpeciesCategory> category;
    private ComboBox<SpeciesType> type;
    private ComboBox<SpeciesStatus> status;
    private Upload imageUpload;
    private TextField imageFileName;
    private Div speciesImage;

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    private final BeanValidationBinder<Species> binder;

    private Species species;

    private final SpeciesService speciesService;

    private File file;
    private String originalFileName;
    private String mimeType;

    public SpecieslistView(SpeciesService speciesService) {
        this.speciesService = speciesService;
        addClassNames("specieslist-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("danishName").setAutoWidth(true);
        grid.addColumn("latinName").setAutoWidth(true);
        grid.addColumn("englishName").setAutoWidth(true);
        grid.addColumn("euringCode").setAutoWidth(true);
        grid.addColumn("category").setAutoWidth(true);
        grid.addColumn("type").setAutoWidth(true);
        grid.addColumn("status").setAutoWidth(true);
        grid.addColumn("firstObservation").setAutoWidth(true);

        LitRenderer<Species> imageFileNameRender = LitRenderer.<Species>of(
                        "<vaadin-icon icon='vaadin:${item.icon}' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: ${item.color};'></vaadin-icon>")
                .withProperty("icon", species -> (species.getImageFileName() != null && !species.getImageFileName().isEmpty()) ? "check" : "minus").withProperty("color",
                        species -> (species.getImageFileName() != null && !species.getImageFileName().isEmpty())
                                ? "var(--lumo-primary-text-color)"
                                : "var(--lumo-disabled-text-color)");

        grid.addColumn(imageFileNameRender).setHeader("Has image").setAutoWidth(true);

        grid.setItems(query -> speciesService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(SPECIES_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(SpecieslistView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Species.class);

        // Bind fields. This is where you'd define e.g. validation rules
        binder.forField(type).bind(Species::getType, Species::setType);

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.species == null) {
                    this.species = new Species();
                }
                binder.writeBean(this.species);
                saveImage();
                speciesService.update(this.species);
                clearForm();
                refreshGrid();
                Notification.show("Data updated");
                UI.getCurrent().navigate(SpecieslistView.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
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
        Optional<Long> speciesId = event.getRouteParameters().get(SPECIES_ID).map(Long::parseLong);
        if (speciesId.isPresent()) {
            Optional<Species> speciesFromBackend = speciesService.get(speciesId.get());
            if (speciesFromBackend.isPresent()) {
                populateForm(speciesFromBackend.get());
            } else {
                Notification.show(
                        String.format("The requested species was not found, ID = %s", speciesId.get()), 3000,
                        Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(SpecieslistView.class);
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
        danishName = new TextField("Danish Name");
        latinName = new TextField("Latin Name");
        englishName = new TextField("English Name");
        euringCode = new TextField("Euring Code");
        firstObservation = new DatePicker("First Observation");
        category = new ComboBox<>("Category");
        category.setItems(SpeciesCategory.values());
        category.setItemLabelGenerator(SpeciesCategory::name);
        type = new ComboBox<>("Type");
        type.setItems(SpeciesType.values());
        type.setItemLabelGenerator(SpeciesType::name);
        status = new ComboBox<>("Status");
        status.setItems(SpeciesStatus.values());
        status.setItemLabelGenerator(SpeciesStatus::name);
        imageFileName = new TextField("Image file");
        imageFileName.setReadOnly(true);
        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
        imageUpload = new Upload(this::receiveUpload);
        imageUpload.setAcceptedFileTypes("image/jpeg", "image/png");
        imageUpload.setMaxFiles(1);
        speciesImage = new Div(new Text("(no image file uploaded yet)"));
        formLayout.add(danishName, latinName, englishName, euringCode, firstObservation, category, type, status, imageFileName, imageUpload, speciesImage);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);

        imageUpload.addSucceededListener(event -> {
            speciesImage.removeAll();
            imageFileName.setValue(originalFileName);
            Image img = new Image(new StreamResource(this.originalFileName,this::loadFile),"Uploaded image");
            img.setWidth("100%");
            speciesImage.add(img);
            speciesImage.add(new Text("Uploaded: "+originalFileName+" to "+ file.getAbsolutePath()+ "Type: "+mimeType));
        });

        imageUpload.addFileRejectedListener(event -> {
            String errorMessage = event.getErrorMessage();

            Notification notification = Notification.show(errorMessage, 5000,
                    Notification.Position.MIDDLE);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        });
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
        speciesImage.removeAll();
        imageUpload.clearFileList();
        file = null;
        originalFileName = null;
        mimeType = null;
    }

    private void populateForm(Species value) {
        this.species = value;
        binder.readBean(this.species);

    }

    public InputStream loadFile() {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            log.warn("Failed to create InputStream for: '{}'", this.file.getAbsolutePath(), e);
        }
        return null;
    }

    public OutputStream receiveUpload(String originalFileName, String MIMEType) {
        this.originalFileName = originalFileName;
        this.mimeType = MIMEType;
        try {
            // Create a temporary file for example, you can provide your file here.
            this.file = File.createTempFile("prefix-", "-suffix");
            file.deleteOnExit();
            return new FileOutputStream(file);
        } catch (IOException e) {
            log.warn("Failed to create InputStream for: '{}'", this.file.getAbsolutePath(), e);
        }

        return null;
    }

    public boolean saveImage() {
        if (this.file != null) {
            try {
                File targetFile = new File(IMAGE_PATH + this.originalFileName);
                if (targetFile.exists()) {
                    targetFile.delete();
                }
                this.file.renameTo(targetFile);
                return true;
            } catch (Exception e) {
                log.warn("Failed to save image file: '{}'", this.file.getAbsolutePath(), e);
            }
        }
        return false;
    }
}