package plus.dragons.createcommutenetwork.foundation.datastructure;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.common.util.INBTSerializable;

public interface SerializableData<T extends SerializableData<T>> extends INBTSerializable<CompoundTag> {

    T readFromPacket(FriendlyByteBuf packet);

    int messagePackLength();

    void writePacket(FriendlyByteBuf packet);
}
