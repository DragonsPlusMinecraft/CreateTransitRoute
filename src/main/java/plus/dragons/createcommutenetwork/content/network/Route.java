package plus.dragons.createcommutenetwork.content.network;

import com.simibubi.create.foundation.utility.Couple;
import com.simibubi.create.foundation.utility.NBTHelper;
import it.unimi.dsi.fastutil.longs.Long2ObjectArrayMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.common.IExtensibleEnum;
import plus.dragons.createcommutenetwork.foundation.datastructure.ISerializableData;

import java.util.ArrayList;
import java.util.List;

public final class Route implements ISerializableData<Route> {
    public long id = 0;
    public Type type = Type.TRAIN;
    public Couple<String> name = Couple.create("","");
    public List<Long> stationIds = new ArrayList<>();
    public Long2ObjectArrayMap<String> stationToPlatform = new Long2ObjectArrayMap<>();
    public int color = 0;
    public boolean hidden = false;
    public boolean reverse = false;

    @Override
    public Route readFromPacket(FriendlyByteBuf packet) {
        return null;
    }

    @Override
    public void writePacket(FriendlyByteBuf packet) {

    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putLong("id",id);
        tag.putString("type",type.name());
        tag.putString("name",name.getFirst());
        tag.putString("altName",name.getSecond());
        tag.put("stationIds", NBTHelper.writeCompoundList(stationIds,id->{
            CompoundTag compoundTag = new CompoundTag();
            compoundTag.putLong("V", id);
            return compoundTag;
        }));
        tag.put("stationToPlatform", NBTHelper.writeCompoundList(stationToPlatform.keySet(), key->{
            CompoundTag tag1 = new CompoundTag();
            tag1.putLong("station",key);
            tag1.putString("platform",stationToPlatform.getOrDefault((long)key,"Invalid"));
            return tag1;
        }));
        tag.putInt("color",color);
        tag.putBoolean("hidden",hidden);
        tag.putBoolean("reverse",reverse);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        id = tag.getLong("id");
        type = Type.valueOf(tag.getString("type"));
        name = Couple.create(tag.getString("name"),tag.getString("altName"));
        stationIds.clear();
        NBTHelper.iterateCompoundList(tag.getList("stationIds", 10), (c) -> {
            stationIds.add(c.getLong("V"));
        });
        stationToPlatform.clear();
        NBTHelper.iterateCompoundList(tag.getList("stationToPlatform", 10), (c) -> {
            stationToPlatform.put(c.getLong("station"),c.getString("platform"));
        });
        color = tag.getInt("color");
        hidden = tag.getBoolean("hidden");
        reverse = tag.getBoolean("reverse");
    }

    public enum Type implements IExtensibleEnum {
        TRAIN
    }
}
