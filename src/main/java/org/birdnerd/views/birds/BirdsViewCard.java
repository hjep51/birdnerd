package org.birdnerd.views.birds;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.theme.lumo.LumoUtility.*;
import lombok.extern.slf4j.Slf4j;
import org.birdnerd.data.models.Species;
import org.birdnerd.data.models.HashTagGroup;
import org.birdnerd.data.models.HashTag;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BirdsViewCard extends ListItem {

    private String imageFilePath;

    public BirdsViewCard(Species species, String imagePath, String dofbasenSpeciesUrl) {
        this.imageFilePath = imagePath + species.getImageFileName();
        String speciesUrl = dofbasenSpeciesUrl + species.getEuringCode();

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
        if (species.getEuringCode() != null) {
            try {
                Integer.parseInt(species.getEuringCode());
                Html linkHtml = new Html("<a href=\"" + speciesUrl + "\" target=\"_blank\">" + species.getDanishName() + "</a>");
                header.add(linkHtml);
            } catch (NumberFormatException e) {
                header.setText(species.getDanishName());
            }
        } else {
            header.setText(species.getDanishName());
        }
        header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);

        Span subtitle = new Span();
        subtitle.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        String subTitleText = species.getLatinName() + " - " + species.getEnglishName();
        subtitle.setText(subTitleText);

        Div tagBadges = new Div();

        List<String> hashTagList = new ArrayList<>();

        species.getHashTagGroups().forEach(hashTagGroup -> {
            hashTagGroup.getHashTags().forEach(hashTag -> {
                hashTagList.add(hashTag.getAsHashTag());
            });
        });

        species.getHashTags().forEach(hashTag -> {
            hashTagList.add(hashTag.getAsHashTag());
        });

        hashTagList.forEach(hashTag -> {
            Span badge = new Span();
            badge.getElement().setAttribute("theme", "badge").setAttribute("badgetype", "tag");
            badge.setText(hashTag);
            tagBadges.add(badge);
        });

        Div badges = new Div();

        Span badgeObservation = new Span();
        badgeObservation.getElement().setAttribute("theme", "badge");
        badgeObservation.setText(species.getFirstObservation().format(DateTimeFormatter.ofPattern("dd/MM-yyyy")));

        Span badgeSpeciesCategory = new Span();
        badgeSpeciesCategory.getElement().setAttribute("theme", "badge");
        badgeSpeciesCategory.setText(species.getCategory().toString());

        Span badgeNumberOfTags = new Span();
        badgeNumberOfTags.getElement().setAttribute("theme", "badge");
        badgeNumberOfTags.setText("Tags: " + hashTagList.size() + " / 30");

        badges.add(badgeObservation, badgeSpeciesCategory, badgeNumberOfTags);

        add(div, header, subtitle, tagBadges, badges);

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
