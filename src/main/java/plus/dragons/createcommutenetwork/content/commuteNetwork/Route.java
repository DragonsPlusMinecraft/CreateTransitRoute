package plus.dragons.createcommutenetwork.content.commuteNetwork;

import com.simibubi.create.foundation.utility.Couple;
import com.simibubi.create.foundation.utility.NBTHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.IExtensibleEnum;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.*;

public final class Route implements INBTSerializable<CompoundTag> {
    public UUID id = UUID.randomUUID();
    public Type type = Type.TRAIN;
    public Couple<String> name = Couple.create("","");
    public List<UUID> stationIds = new ArrayList<>();
    public Map<UUID,String> stationToPlatform = new HashMap<>();
    public int color = 0;
    public boolean hidden = false;
    public boolean reverse = false;

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putUUID("id",id);
        tag.putString("type",type.name());
        tag.putString("name",name.getFirst());
        tag.putString("altName",name.getSecond());
        tag.put("stationIds", NBTHelper.writeCompoundList(stationIds,id->{
            CompoundTag compoundTag = new CompoundTag();
            compoundTag.putUUID("V", id);
            return compoundTag;
        }));
        tag.put("stationToPlatform", NBTHelper.writeCompoundList(stationToPlatform.keySet(), key->{
            CompoundTag tag1 = new CompoundTag();
            tag1.putUUID("station",key);
            tag1.putString("platform",stationToPlatform.get(key));
            return tag1;
        }));
        tag.putInt("color",color);
        tag.putBoolean("hidden",hidden);
        tag.putBoolean("reverse",reverse);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        id = tag.getUUID("id");
        type = Type.valueOf(tag.getString("type"));
        name = Couple.create(tag.getString("name"),tag.getString("altName"));
        stationIds.clear();
        NBTHelper.iterateCompoundList(tag.getList("stationIds", 10), (c) -> {
            stationIds.add(c.getUUID("V"));
        });
        stationToPlatform.clear();
        NBTHelper.iterateCompoundList(tag.getList("stationToPlatform", 10), (c) -> {
            stationToPlatform.put(c.getUUID("station"),c.getString("platform"));
        });
        color = tag.getInt("color");
        hidden = tag.getBoolean("hidden");
        reverse = tag.getBoolean("reverse");
    }

    public enum Type implements IExtensibleEnum {
        TRAIN
    }
}
