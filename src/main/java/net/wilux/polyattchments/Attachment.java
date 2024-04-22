package net.wilux.polyattchments;

import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import eu.pb4.polymer.virtualentity.api.elements.VirtualElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

import static net.wilux.util.ServerCast.asServer;

public class Attachment {
    private final @NotNull ElementHolder holder;
    private final @NotNull Map<Integer, VirtualElement> extraElements = new HashMap<>();
    protected final @NotNull HolderAttachment holderAttachment;

    protected record Args(@NotNull ElementHolder elementHolder, @NotNull HolderAttachment holderAttachment){}
    protected Attachment(Args args) {
        this.holder = args.elementHolder;
        this.holderAttachment = args.holderAttachment;
    }

    public void addUnnamedElement(VirtualElement element) {
        this.holder.addElement(element);
    }

    public void destroy() {
        this.holder.destroy();
        this.holderAttachment.destroy();
    }

    // Extra Attachments
    /**
     * Inserts/Overrides attachments
     * @param namedVirtualElement namedAttachment
     * @param value Element to add
     * @return the old VirtualElement, if one existed
     */
    public <T extends VirtualElement> @Nullable VirtualElement putNamedElement(NamedVirtualElement<T> namedVirtualElement, T value) {
        @Nullable VirtualElement old = this.extraElements.put(namedVirtualElement.id, value);
        if (old != null) {
            this.holder.removeElement(old);
        }
        this.holder.addElement(value);
        return old;
    }

    public <T extends VirtualElement> @Nullable T getNamedElement(NamedVirtualElement<T> property){
        VirtualElement virtualElement = this.extraElements.get(property.id);
        if (virtualElement != null) {
            return (T)virtualElement;
        }
        return null;
    }
}
