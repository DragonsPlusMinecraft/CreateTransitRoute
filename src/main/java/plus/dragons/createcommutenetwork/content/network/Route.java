package plus.dragons.createcommutenetwork.content.network;

import com.simibubi.create.foundation.utility.Couple;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.common.IExtensibleEnum;
import plus.dragons.createcommutenetwork.foundation.datastructure.ISerializableData;

import java.util.UUID;

public final class Route implements ISerializableData<Route> {
    public UUID id;

    public Type type;
    public Couple<String> name;
    public String color;

    @Override
    public Route readFromPacket(FriendlyByteBuf packet) {
        return null;
    }

    @Override
    public int messagePackLength() {
        return 0;
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
