package org.birdnerd.views.birds;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.theme.lumo.LumoUtility.*;
import lombok.extern.slf4j.Slf4j;
import org.birdnerd.data.Species;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.time.format.DateTimeFormatter;

@Slf4j
public class BirdsViewCard extends ListItem {

    private String imageFilePath;

    public BirdsViewCard(Species species, String imagePath) {
        this.imageFilePath = imagePath + species.getImageFileName();

        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM,
                BorderRadius.LARGE);

        Div div = new Div();
        div.addClassNames(Background.CONTRAST, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                Margin.Bottom.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);
        //div.setHeight("160px");

        Image image = new Image(imageResource, "Bird image");
        image.setWidth("100%");
        image.setAlt(species.getDanishName());

        div.add(image);

        Span header = new Span();
        header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        header.setText(species.getDanishName());

        Span subtitle = new Span();
        subtitle.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        String subTitleText = species.getLatinName() + " - " + species.getEnglishName();
        subtitle.setText(subTitleText);



        Paragraph description = new Paragraph(
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut.");
        description.addClassName(Margin.Vertical.MEDIUM);

        Div badges = new Div();

        Span badgeObservation = new Span();
        badgeObservation.getElement().setAttribute("theme", "badge");
        badgeObservation.setText(species.getFirstObservation().format(DateTimeFormatter.ofPattern("dd/MM-yyyy")));

        Span badgeSpeciesCategory = new Span();
        badgeSpeciesCategory.getElement().setAttribute("theme", "badge");
        badgeSpeciesCategory.setText(species.getCategory().toString());

        badges.add(badgeObservation, badgeSpeciesCategory);

        add(div, header, subtitle, description, badges);

    }

    final StreamResource imageResource = new StreamResource("MyResourceName", () -> {
        try
        {
            return new FileInputStream(new File(imageFilePath));
        }
        catch(final FileNotFoundException e)
        {
            log.warn("Image file not found: " + imageFilePath);
            return null;
        }
    });
}
