package plus.dragons.createcommutenetwork.content.network;

import com.simibubi.create.foundation.utility.Couple;
import it.unimi.dsi.fastutil.longs.Long2ObjectArrayMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.common.IExtensibleEnum;
import plus.dragons.createcommutenetwork.foundation.datastructure.ISerializableData;

import java.util.List;

public final class Route implements ISerializableData<Route> {
    public long id;
    public Type type;
    public Couple<String> name;
    public List<Long> stationIds;
    public Long2ObjectArrayMap<String> stationToPlatform;
    public int color;
    public boolean hidden;
    public boolean reverse;

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
