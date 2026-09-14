package com.iotech.qualitrack.platform.inventory.interfaces.rest.transform;
import com.iotech.qualitrack.platform.inventory.domain.model.commands.SaveRawMaterialCommand;
import com.iotech.qualitrack.platform.inventory.interfaces.rest.resources.SaveRawMaterialResource;
public final class SaveRawMaterialCommandFromResourceAssembler {
    private SaveRawMaterialCommandFromResourceAssembler() { }
    public static SaveRawMaterialCommand toCommandFromResource(Long lab, Long id, SaveRawMaterialResource resource) {
        return new SaveRawMaterialCommand(lab, id, resource.code(), resource.name(), resource.unit(), resource.minimumStock());
    }
}
