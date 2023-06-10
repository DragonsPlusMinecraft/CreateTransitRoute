package plus.dragons.createcommutenetwork.content.network;

import com.simibubi.create.foundation.utility.Couple;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import plus.dragons.createcommutenetwork.foundation.datastructure.ISerializableData;

import java.util.UUID;

public class RouteSegment implements ISerializableData<RouteSegment> {
    public UUID id;

    public Couple<String> name;

    public boolean hidden;

    @Override
    public RouteSegment readFromPacket(FriendlyByteBuf packet) {
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
}
