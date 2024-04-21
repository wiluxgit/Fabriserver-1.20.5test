package net.wilux.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.EntityAttachS2CPacket;

public class UtilEntityAttachS2CPacket {
    public static EntityAttachS2CPacket entityAttachS2CPacketFromIds(int attachedEntityId, int holdingEntityId){
        ByteBufAllocator allocator = ByteBufAllocator.DEFAULT;
        ByteBuf buffer = allocator.buffer();
        buffer.writeInt(attachedEntityId);
        buffer.writeInt(holdingEntityId);

        var packet = new EntityAttachS2CPacket(new PacketByteBuf(buffer));
        assert packet.getAttachedEntityId() == attachedEntityId;
        assert packet.getHoldingEntityId() == holdingEntityId;
        return packet;
    }
}
