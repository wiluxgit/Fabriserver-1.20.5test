package net.wilux.polyattchments;

import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import eu.pb4.polymer.virtualentity.api.elements.VirtualElement;
import org.jetbrains.annotations.NotNull;

public class NamedVirtualElement<T extends VirtualElement> {
    private static int typeId = 0;
    protected final int id;

    public static <T extends VirtualElement> NamedVirtualElement<T> register() {
        return new NamedVirtualElement<T>();
    }

    private NamedVirtualElement() {
        this.id = typeId;
        NamedVirtualElement.typeId++;
    }
}
