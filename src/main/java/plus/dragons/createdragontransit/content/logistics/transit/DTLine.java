package plus.dragons.createdragontransit.content.logistics.transit;

import com.simibubi.create.foundation.utility.Couple;
import com.simibubi.create.foundation.utility.NBTHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;


import java.util.*;

public class DTLine {
    public final UUID id;
    public final Couple<String> names;
    public final List<UUID> segments;
    public final UUID owner;
    public String code;
    public String color;
    public Visibility visibility;
    public String comment;

    public DTLine(UUID owner) {
        this.id = UUID.randomUUID();
        this.names = Couple.create("New Line","");
        this.code = "";
        var random = new Random();
        char[] hex = { '0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        char[] s = new char[7];
        int n = random.nextInt(0x1000000);
        s[0] = '#';
        for (int i=1;i<7;i++) {
            s[i] = hex[n & 0xf];
            n >>= 4;
        }
        this.color = new String(s);
        this.segments = new ArrayList<>();
        this.owner = owner;
        this.visibility = Visibility.PRIVATE;
        this.comment = "";
    }

    public DTLine(CompoundTag tag) {
        this.id = tag.getUUID("ID");
        this.names = Couple.create(tag.getString("Name"),tag.getString("TName"));
        this.code = tag.getString("Code");
        this.color = tag.getString("Color");
        this.segments = NBTHelper.readCompoundList(tag.getList("Segs", Tag.TAG_COMPOUND), compoundTag -> compoundTag.getUUID("ID"));
        this.owner = tag.getUUID("Owner");
        this.visibility = NBTHelper.readEnum(tag,"Ownership", Visibility.class);
    }

    public CompoundTag write(){
        CompoundTag ret = new CompoundTag();
        ret.putUUID("ID",id);
        ret.putString("Name", names.getFirst());
        ret.putString("TName", names.getSecond());
        ret.putString("Code",code);
        ret.putString("Color",color);
        ret.putUUID("Owner",owner);
        NBTHelper.writeEnum(ret,"Ownership", visibility);
        ret.put("Segs", NBTHelper.writeCompoundList(segments, uuid -> {
            var tag = new CompoundTag();
            tag.putUUID("ID",uuid);
            return tag;
        }));
        return ret;
    }

    public enum Visibility {
        PUBLIC,
        PRIVATE,
        SECRET
    }

}
