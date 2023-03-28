package plus.dragons.createdragontransit.content.logistics.transit;

import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class DTStationPlatform {

    public final UUID id;

    public String code;

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
        this.edgePoint = tag.contains("EdgeID")?tag.getUUID("EdgeID"):null;
    }

    public CompoundTag write(){
        var tag = new CompoundTag();
        tag.putUUID("ID",id);
        tag.putString("Code",code);
        if(edgePoint!=null){
            tag.putUUID("EdgeID",edgePoint);
        }
        return tag;
    }
}
