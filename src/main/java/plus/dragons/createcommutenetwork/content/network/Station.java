package plus.dragons.createcommutenetwork.content.network;

import com.simibubi.create.foundation.utility.Couple;
import com.simibubi.create.foundation.utility.NBTHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.common.IExtensibleEnum;
import plus.dragons.createcommutenetwork.foundation.datastructure.ISerializableData;

public final class Station implements ISerializableData<Station> {
    public long id = 0;
    public Couple<String> name = Couple.create("","");

    @Override
    public Station readFromPacket(FriendlyByteBuf packet) {
        return null;
    }

    @Override
    public void writePacket(FriendlyByteBuf packet) {

    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putLong("id",id);
        tag.putString("name",name.getFirst());
        tag.putString("altName",name.getSecond());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        id = tag.getLong("id");
        name = Couple.create(tag.getString("name"),tag.getString("altName"));
    }
}
