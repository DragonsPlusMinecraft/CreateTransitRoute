package plus.dragons.createdragontransit.content.logistics.transit;

import com.simibubi.create.foundation.utility.Couple;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class DTStationPlatform {

    public final UUID id;

    public String code;
    @Nullable
    public Couple<UUID> line;
    @Nullable
    private UUID station;
    @Nullable
    private UUID edgePoint;

    public DTStationPlatform(String code) {
        this.id = UUID.randomUUID();
        this.code = code;
        this.edgePoint = null;
    }

    public DTStationPlatform(CompoundTag tag){
        this.id = tag.getUUID("ID");
        this.code = tag.getString("Code");
        this.line = tag.contains("Line")?Couple.create(tag.getUUID("Station"),tag.getUUID("Segment")):null;
        this.station = tag.contains("Station")?tag.getUUID("Station"):null;
        this.edgePoint = tag.contains("Edge")?tag.getUUID("Edge"):null;
    }

    public CompoundTag write(){
        var tag = new CompoundTag();
        tag.putUUID("ID",id);
        tag.putString("Code",code);
        if(line!=null){
            tag.putUUID("Line",line.getFirst());
            tag.putUUID("Segment",line.getSecond());
        }
        if(station!=null){
            tag.putUUID("Station",station);
        }
        if(edgePoint!=null){
            tag.putUUID("Edge",edgePoint);
        }
        return tag;
    }
}
