package plus.dragons.createdragontransit.content.logistics.transit;

import com.simibubi.create.foundation.utility.Couple;
import com.simibubi.create.foundation.utility.NBTHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

import java.util.*;


public class DTStation {
    public final UUID id;
    public final Couple<String> names;
    public final List<UUID> platforms;
    private final UUID owner;
    private boolean isPrivate;

    public DTStation(String name, String translatedName, UUID owner) {
        this.id = UUID.randomUUID();
        this.names = Couple.create(name, translatedName);
        this.platforms = new ArrayList<>();
        this.owner = owner;
        this.isPrivate = true;
    }

    @SuppressWarnings("ConstantConditions")
    public DTStation(CompoundTag tag) {
        this.id = tag.getUUID("ID");
        this.names = Couple.create(tag.getString("Name"), tag.getString("TName"));
        this.platforms = NBTHelper.readCompoundList((ListTag) tag.get("Platforms"), compoundTag -> {
            return compoundTag.getUUID("ID");
        });
        this.owner = tag.getUUID("Owner");
        this.isPrivate = tag.getBoolean("Private");
    }

    public CompoundTag write() {
        CompoundTag ret = new CompoundTag();
        ret.putUUID("ID", id);
        ret.putString("Names", names.getFirst());
        ret.putString("TName", names.getSecond());
        ret.put("Platforms", NBTHelper.writeCompoundList(platforms, p -> {
            CompoundTag tag = new CompoundTag();
            tag.putUUID("ID", p);
            return tag;
        }));
        ret.putUUID("Owner", owner);
        ret.putBoolean("Private", isPrivate);
        return ret;
    }
}
