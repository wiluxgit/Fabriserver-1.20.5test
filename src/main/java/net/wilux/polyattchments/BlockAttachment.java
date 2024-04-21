package net.wilux.polyattchments;

import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.ChunkAttachment;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import eu.pb4.polymer.virtualentity.api.elements.VirtualElement;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static net.wilux.util.ServerCast.asServer;

public abstract class BlockAttachment {
    public static final class NamedVirtualElement<T extends VirtualElement> {
        private static int typeId = 0;
        private final int id;

        public static <T extends VirtualElement> NamedVirtualElement<T> register() {
            return new NamedVirtualElement<T>();
        }

        private NamedVirtualElement() {
            this.id = typeId;
            NamedVirtualElement.typeId++;
        }
    }

    // BlockAttachment
    private final @NotNull ElementHolder holder;
    private final @NotNull Map<Integer, VirtualElement> extraElements = new HashMap<>();
    protected final @NotNull HolderAttachment chunkAttachment;

    protected BlockAttachment(@NotNull World world, @NotNull BlockPos pos) {
        this.holder =  new ElementHolder();
        this.chunkAttachment = ChunkAttachment.ofTicking(this.holder, asServer(world), pos.toCenterPos());
    }

    public void addUnnamedElement(VirtualElement element) {
        this.holder.addElement(element);
    }

    public void destroy() {
        this.holder.destroy();
        this.chunkAttachment.destroy();
    }

    // Extra Attachments
    /**
     * Inserts/Overrides attachments
     * @param namedVirtualElement namedAttachment
     * @param value Element to add
     * @return the old VirtualElement, if one existed
     */
    public <T extends VirtualElement> VirtualElement putNamedElement(NamedVirtualElement<T> namedVirtualElement, T value) {
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
