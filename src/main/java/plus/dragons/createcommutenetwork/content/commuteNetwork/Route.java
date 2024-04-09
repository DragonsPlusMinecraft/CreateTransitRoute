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
        tag.putUUID("Id", id);
        tag.putString("Type", type.name());
        tag.putString("Name", name.getFirst());
        tag.putString("AltName", name.getSecond());
        tag.put("StationIds", NBTHelper.writeCompoundList(stationIds, id -> {
            CompoundTag compoundTag = new CompoundTag();
            compoundTag.putUUID("V", id);
            return compoundTag;
        }));
        tag.put("StationToPlatform", NBTHelper.writeCompoundList(stationToPlatform.keySet(), key -> {
            CompoundTag tag1 = new CompoundTag();
            tag1.putUUID("Station", key);
            tag1.putString("Platform", stationToPlatform.get(key));
            return tag1;
        }));
        tag.putInt("Color", color);
        tag.putBoolean("Hidden", hidden);
        tag.putBoolean("Reverse", reverse);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        id = tag.getUUID("Id");
        type = Type.valueOf(tag.getString("Type"));
        name = Couple.create(tag.getString("Name"), tag.getString("AltName"));
        stationIds.clear();
        NBTHelper.iterateCompoundList(tag.getList("StationIds", 10), (c) -> {
            stationIds.add(c.getUUID("V"));
        });
        stationToPlatform.clear();
        NBTHelper.iterateCompoundList(tag.getList("StationToPlatform", 10), (c) -> {
            stationToPlatform.put(c.getUUID("Station"), c.getString("Platform"));
        });
        color = tag.getInt("Color");
        hidden = tag.getBoolean("Hidden");
        reverse = tag.getBoolean("Reverse");
    }

    public enum Type implements IExtensibleEnum {
        TRAIN
    }
}
