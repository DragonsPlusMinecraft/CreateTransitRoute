package plus.dragons.createcommutenetwork.foundation.datastructure;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.common.util.INBTSerializable;

public interface ISerializableData<T extends ISerializableData<T>> extends INBTSerializable<CompoundTag> {

    T readFromPacket(FriendlyByteBuf packet);

    void writePacket(FriendlyByteBuf packet);
}
