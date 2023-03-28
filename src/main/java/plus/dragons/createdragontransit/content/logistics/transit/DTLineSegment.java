package plus.dragons.createdragontransit.content.logistics.transit;

import com.simibubi.create.foundation.utility.Couple;
import com.simibubi.create.foundation.utility.NBTHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DTLineSegment {
    public final UUID id;
    public final Couple<String> names;
    public final List<Couple<UUID>> stations;
    public String comment;
    public boolean maintaining;
    public boolean emergency;

    public DTLineSegment(String name) {
        this.id = UUID.randomUUID();
        this.names = Couple.create(name, "");
        this.stations = new ArrayList<>();
        this.comment = "";
        this.maintaining = true;
        this.emergency = false;
    }

    public DTLineSegment(CompoundTag tag) {
        this.id = tag.getUUID("ID");
        this.names = Couple.create(tag.getString("Name"),tag.getString("TName"));
        this.stations = NBTHelper.readCompoundList((ListTag) tag.get("Stations"),compoundTag ->
                Couple.create(tag.getUUID("StationID"),tag.contains("PlatformID")?tag.getUUID("PlatformID"):null));
        this.maintaining = tag.getBoolean("Maintain");
        this.emergency = tag.getBoolean("Emergency");
    }

    public CompoundTag write() {
        CompoundTag ret = new CompoundTag();
        ret.putUUID("ID", id);
        ret.putString("Name", names.getFirst());
        ret.putString("TName", names.getSecond());
        ret.putBoolean("Maintain", maintaining);
        ret.putBoolean("Emergency", emergency);
        ret.put("Stations", NBTHelper.writeCompoundList(stations, pair -> {
            var tag = new CompoundTag();
            tag.putUUID("StationID", pair.getFirst());
            if(pair.getSecond()!=null)
                tag.putUUID("PlatformID", pair.getSecond());
            return tag;
        }));
        return ret;
    }
}
