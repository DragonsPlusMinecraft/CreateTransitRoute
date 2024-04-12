package plus.dragons.createcommutenetwork.content.commuteNetwork;

import com.simibubi.create.foundation.utility.Couple;
import com.simibubi.create.foundation.utility.NBTHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Station implements INBTSerializable<CompoundTag> {
    public UUID id = UUID.randomUUID();
    public Couple<String> name = Couple.create("", "");
    public List<UUID> platformIds = new ArrayList<>();

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putUUID("Id", id);
        tag.putString("Name", name.getFirst());
        tag.putString("AltName", name.getSecond());
        tag.put("PlatformIds", NBTHelper.writeCompoundList(platformIds, id -> {
            CompoundTag compoundTag = new CompoundTag();
            compoundTag.putUUID("V", id);
            return compoundTag;
        }));
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        id = tag.getUUID("Id");
        name = Couple.create(tag.getString("Name"), tag.getString("AltName"));
        NBTHelper.iterateCompoundList(tag.getList("PlatformIds", 10), (c) -> {
            platformIds.add(c.getUUID("V"));
        });
    }
}
