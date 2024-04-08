package plus.dragons.createcommutenetwork.content.network;

import com.simibubi.create.foundation.utility.Couple;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.common.IExtensibleEnum;
import plus.dragons.createcommutenetwork.foundation.datastructure.ISerializableData;

public final class Station implements ISerializableData<Station> {
    public long id;
    public Type type;
    public Couple<String> name;

    @Override
    public Station readFromPacket(FriendlyByteBuf packet) {
        return null;
    }

    @Override
    public void writePacket(FriendlyByteBuf packet) {

    }

    @Override
    public CompoundTag serializeNBT() {
        return null;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

    }

    public enum Type implements IExtensibleEnum {
        TRAIN
    }
}
