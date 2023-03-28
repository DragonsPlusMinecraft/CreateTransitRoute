package plus.dragons.createdragontransit.content.logistics.transit;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;

public class TransitNetworkSavedData extends SavedData {

    public TransitNetwork network = new TransitNetwork();

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        network.save(compoundTag);
        return compoundTag;
    }

    private static TransitNetworkSavedData load(CompoundTag compoundTag) {
        TransitNetworkSavedData ret = new TransitNetworkSavedData();
        ret.network.load(compoundTag);
        return ret;
    }

    private TransitNetworkSavedData() {}

    public static TransitNetworkSavedData load(MinecraftServer server) {
        return server.overworld()
                .getDataStorage()
                .computeIfAbsent(TransitNetworkSavedData::load, TransitNetworkSavedData::new, "dragon_transit_railway");
    }
}
