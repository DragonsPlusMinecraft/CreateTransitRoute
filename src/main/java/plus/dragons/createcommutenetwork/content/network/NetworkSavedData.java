package plus.dragons.createcommutenetwork.content.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;

public class NetworkSavedData extends SavedData {
    Network network = new Network();

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        // TODO
        return compoundTag;
    }

    private static NetworkSavedData load(CompoundTag compoundTag) {
        NetworkSavedData ret = new NetworkSavedData();
        // TODO
        return ret;
    }

    private NetworkSavedData() {
    }

    public static NetworkSavedData load(MinecraftServer server) {
        return server.overworld()
                .getDataStorage()
                .computeIfAbsent(NetworkSavedData::load, NetworkSavedData::new, "train_network");
    }
}
