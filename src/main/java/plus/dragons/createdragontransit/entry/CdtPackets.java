package plus.dragons.createdragontransit.entry;

import com.simibubi.create.foundation.networking.SimplePacketBase;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import plus.dragons.createdragontransit.DragonTransit;
import plus.dragons.createdragontransit.content.logistics.transit.DTNetworkSyncPacket;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.minecraftforge.network.NetworkDirection.PLAY_TO_CLIENT;

public enum CdtPackets {
    DT_NETWORK_INI(DTNetworkSyncPacket.Initialize.class, DTNetworkSyncPacket.Initialize::new, PLAY_TO_CLIENT, LoadedPacket.ConsumeThread.MAIN),
    DT_NETWORK_UPDATE(DTNetworkSyncPacket.Update.class, DTNetworkSyncPacket.Update::new, PLAY_TO_CLIENT, LoadedPacket.ConsumeThread.NETWORK);
    // PLATFORM_UNBIND(PlatformEditPacket.PlatformUnbind.class, PlatformEditPacket.PlatformUnbind::new, PLAY_TO_SERVER, LoadedPacket.ConsumeThread.NETWORK);
    public static final ResourceLocation CHANNEL_NAME = DragonTransit.genRL("main");
    public static final int NETWORK_VERSION = 1;
    public static final String NETWORK_VERSION_STR = String.valueOf(NETWORK_VERSION);
    public static SimpleChannel channel;
    private CdtPackets.LoadedPacket<?> packet;

    <T extends SimplePacketBase> CdtPackets(Class<T> type, Function<FriendlyByteBuf, T> factory,
                                            NetworkDirection direction, LoadedPacket.ConsumeThread consumeThread) {
        packet = new CdtPackets.LoadedPacket<>(type, factory, direction, consumeThread);
    }

    public static void registerPackets() {
        channel = NetworkRegistry.ChannelBuilder.named(CHANNEL_NAME)
                .serverAcceptedVersions(NETWORK_VERSION_STR::equals)
                .clientAcceptedVersions(NETWORK_VERSION_STR::equals)
                .networkProtocolVersion(() -> NETWORK_VERSION_STR)
                .simpleChannel();
        for (CdtPackets packet : values())
            packet.packet.register();
    }

    public static void sendToNear(Level world, BlockPos pos, int range, Object message) {
        channel.send(
                PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(pos.getX(), pos.getY(), pos.getZ(), range, world.dimension())),
                message);
    }

    private static class LoadedPacket<T extends SimplePacketBase> {
        private static int index = 0;

        private final BiConsumer<T, FriendlyByteBuf> encoder;
        private final Function<FriendlyByteBuf, T> decoder;
        private final BiConsumer<T, Supplier<NetworkEvent.Context>> handler;
        private final Class<T> type;
        private final NetworkDirection direction;
        private final ConsumeThread consumeThread;

        private LoadedPacket(Class<T> type, Function<FriendlyByteBuf, T> factory, NetworkDirection direction, ConsumeThread consumeThread) {
            encoder = T::write;
            decoder = factory;
            handler = T::handle;
            this.type = type;
            this.direction = direction;
            this.consumeThread = consumeThread;
        }

        private void register() {
            if(consumeThread==ConsumeThread.MAIN)
                channel.messageBuilder(type, index++, direction)
                        .encoder(encoder)
                        .decoder(decoder)
                        .consumerMainThread(handler)
                        .add();
            else channel.messageBuilder(type, index++, direction)
                    .encoder(encoder)
                    .decoder(decoder)
                    .consumerNetworkThread(handler)
                    .add();

        }

        private enum ConsumeThread{
            MAIN,
            NETWORK
        }
    }
}
