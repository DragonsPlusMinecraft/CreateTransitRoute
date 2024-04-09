package plus.dragons.createcommutenetwork.content.commuteNetwork;

import com.simibubi.create.foundation.utility.Couple;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.UUID;

public final class Station implements INBTSerializable<CompoundTag> {
    public UUID id = UUID.randomUUID();
    public Couple<String> name = Couple.create("", "");

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putUUID("Id", id);
        tag.putString("Name", name.getFirst());
        tag.putString("AltName", name.getSecond());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        id = tag.getUUID("Id");
        name = Couple.create(tag.getString("Name"), tag.getString("AltName"));
    }
}
