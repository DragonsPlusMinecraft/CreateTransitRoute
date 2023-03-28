package plus.dragons.createdragontransit.content.logistics.transit;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;

public class DTNetworkSavedData extends SavedData {

    public DTNetwork network = new DTNetwork();

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        network.save(compoundTag);
        return compoundTag;
    }

    private static DTNetworkSavedData load(CompoundTag compoundTag) {
        DTNetworkSavedData ret = new DTNetworkSavedData();
        ret.network.load(compoundTag);
        return ret;
    }

    private DTNetworkSavedData() {}

    public static DTNetworkSavedData load(MinecraftServer server) {
        return server.overworld()
                .getDataStorage()
                .computeIfAbsent(DTNetworkSavedData::load, DTNetworkSavedData::new, "dragon_transit_railway");
    }
}
