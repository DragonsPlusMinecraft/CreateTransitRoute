package plus.dragons.createcommutenetwork.content.network;

import net.minecraftforge.common.IExtensibleEnum;
import plus.dragons.createcommutenetwork.foundation.datastructure.ISerializableData;

public interface Destination extends ISerializableData<Destination> {
    Type type();

    String identifier();

    enum Type implements IExtensibleEnum {
        STANDARD_STATION,
        COMMUTE_STATION
    }
}
