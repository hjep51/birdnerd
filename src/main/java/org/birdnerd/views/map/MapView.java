package org.birdnerd.views.map;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.birdnerd.views.MainLayout;
import software.xdev.vaadin.maps.leaflet.MapContainer;
import software.xdev.vaadin.maps.leaflet.basictypes.LLatLng;
import software.xdev.vaadin.maps.leaflet.layer.raster.LTileLayer;
import software.xdev.vaadin.maps.leaflet.layer.ui.LMarker;
import software.xdev.vaadin.maps.leaflet.map.LMap;
import software.xdev.vaadin.maps.leaflet.registry.LComponentManagementRegistry;
import software.xdev.vaadin.maps.leaflet.registry.LDefaultComponentManagementRegistry;

@Slf4j
@PageTitle("Map")
@Route(value = "map", layout = MainLayout.class)
public class MapView extends Main implements HasComponents, HasStyle {

    private final LDefaultComponentManagementRegistry reg;
    private final LMap map;

    public MapView() {
        addClassNames("map-view");
        // Let the view use 100% of the site
        this.setSizeFull();

        // Create the registry which is needed so that components can be reused and their methods invoked
        // Note: You normally don't need to invoke any methods of the registry and just hand it over to the components
        reg = new LDefaultComponentManagementRegistry(this);

        // Create and add the MapContainer (which contains the map) to the UI
        final MapContainer mapContainer = new MapContainer(reg);
        mapContainer.setSizeFull();
        this.add(mapContainer);

        map = mapContainer.getlMap();

        // Add a (default) TileLayer so that we can see something on the map
        map.addLayer(LTileLayer.createDefaultForOpenStreetMapTileServer(reg));

        // Set what part of the world should be shown
        map.setView(new LLatLng(reg, 55.868469, 8.2271481), 12);

        // Create a new marker
        new LMarker(reg, new LLatLng(reg, 49.6756, 12.1610))
                // Bind a popup which is displayed when clicking the marker
                .bindPopup("XDEV Software")
                // Add it to the map
                .addTo(map);
    }
}
