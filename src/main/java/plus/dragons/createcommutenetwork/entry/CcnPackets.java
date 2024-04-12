package plus.dragons.createcommutenetwork.entry;

import com.simibubi.create.foundation.networking.SimplePacketBase;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import plus.dragons.createcommutenetwork.CommuteNetwork;
import plus.dragons.createcommutenetwork.foundation.network.toClient.MultipleStationSyncPacket;
import plus.dragons.createcommutenetwork.foundation.network.toClient.RouteSyncPacket;
import plus.dragons.createcommutenetwork.foundation.network.toServer.PlatformCodePacket;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public enum CcnPackets {
    MULTI_STATION_SYNC(MultipleStationSyncPacket.class, MultipleStationSyncPacket::new, NetworkDirection.PLAY_TO_CLIENT),
    ROUTE_SYNC(RouteSyncPacket.class, RouteSyncPacket::new, NetworkDirection.PLAY_TO_CLIENT),
    PLATFORM_EDIT(PlatformCodePacket.class, PlatformCodePacket::new, NetworkDirection.PLAY_TO_SERVER);
    public static final ResourceLocation CHANNEL_NAME = CommuteNetwork.genRL("main");
    public static final int NETWORK_VERSION = 1;
    public static final String NETWORK_VERSION_STR = String.valueOf(NETWORK_VERSION);
    public static SimpleChannel channel;
    private CcnPackets.LoadedPacket<?> packet;

    <T extends SimplePacketBase> CcnPackets(Class<T> type, Function<FriendlyByteBuf, T> factory,
                                            NetworkDirection direction) {
        packet = new CcnPackets.LoadedPacket<>(type, factory, direction);
    }

    public static void registerPackets() {
        channel = NetworkRegistry.ChannelBuilder.named(CHANNEL_NAME)
                .serverAcceptedVersions(NETWORK_VERSION_STR::equals)
                .clientAcceptedVersions(NETWORK_VERSION_STR::equals)
                .networkProtocolVersion(() -> NETWORK_VERSION_STR)
                .simpleChannel();
        for (CcnPackets packet : values())
            packet.packet.register();
    }

    public static SimpleChannel getChannel() {
        return channel;
    }

    private static class LoadedPacket<T extends SimplePacketBase> {
        private static int index = 0;
        private final BiConsumer<T, FriendlyByteBuf> encoder;
        private final Function<FriendlyByteBuf, T> decoder;
        private final BiConsumer<T, Supplier<NetworkEvent.Context>> handler;
        private final Class<T> type;
        private final NetworkDirection direction;

        private LoadedPacket(Class<T> type, Function<FriendlyByteBuf, T> factory, NetworkDirection direction) {
            encoder = T::write;
            decoder = factory;
            handler = (packet, contextSupplier) -> {
                NetworkEvent.Context context = contextSupplier.get();
                if (packet.handle(context)) {
                    context.setPacketHandled(true);
                }
            };
            this.type = type;
            this.direction = direction;
        }

        private void register() {
            channel.messageBuilder(type, index++, direction)
                    .encoder(encoder)
                    .decoder(decoder)
                    .consumerNetworkThread(handler)
                    .add();

        }
    }
}
