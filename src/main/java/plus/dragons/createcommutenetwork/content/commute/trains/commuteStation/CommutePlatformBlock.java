package plus.dragons.createcommutenetwork.content.commute.trains.commuteStation;

import com.simibubi.create.AllItems;
import com.simibubi.create.AllShapes;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.block.ProperWaterloggedBlock;
import com.simibubi.create.foundation.gui.ScreenOpener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import plus.dragons.createcommutenetwork.entry.CcnBlockEntities;

public class CommutePlatformBlock extends Block implements IBE<CommutePlatformBlockEntity>, IWrenchable, ProperWaterloggedBlock {

    public CommutePlatformBlock(Properties pProperties) {
        super(pProperties);
        registerDefaultState(defaultBlockState().setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder.add(WATERLOGGED));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return withWater(super.getStateForPlacement(pContext), pContext);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState,
                                  LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        updateWater(pLevel, pState, pCurrentPos);
        return pState;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        AdvancementBehaviour.setPlacedBy(pLevel, pPos, pPlacer);
    }

    @Override
    public FluidState getFluidState(BlockState pState) {
        return fluidState(pState);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        // TODO
        return 0;/*getTileEntityOptional(pLevel, pPos).map(ste -> ste.trainPresent ? 15 : 0)
                .orElse(0);*/
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        IBE.onRemove(state, worldIn, pos, newState);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand,
                                 BlockHitResult pHit) {

        if (pPlayer == null || pPlayer.isSteppingCarefully())
            return InteractionResult.PASS;
        ItemStack itemInHand = pPlayer.getItemInHand(pHand);
        if (AllItems.WRENCH.isIn(itemInHand))
            return InteractionResult.PASS;

        // TODO
        /*
        if (itemInHand.getItem() == Items.FILLED_MAP) {
            return onTileEntityUse(pLevel, pPos, station -> {
                if (pLevel.isClientSide)
                    return InteractionResult.SUCCESS;

                if (station.getStation() == null || station.getStation().getId() == null)
                    return InteractionResult.FAIL;

                MapItemSavedData savedData = MapItem.getSavedData(itemInHand, pLevel);
                if (!(savedData instanceof StationMapData stationMapData))
                    return InteractionResult.FAIL;

                if (!stationMapData.toggleStation(pLevel, pPos, station))
                    return InteractionResult.FAIL;

                return InteractionResult.SUCCESS;
            });
        }*/

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
                () -> () -> withBlockEntityDo(pLevel, pPos, te -> this.displayScreen(te, pPlayer)));

        return InteractionResult.SUCCESS;
    }

    @OnlyIn(value = Dist.CLIENT)
    protected void displayScreen(CommutePlatformBlockEntity te, Player player) {
        if (!(player instanceof LocalPlayer))
            return;
        CommutePlatformEdgePoint platform = te.getPlatform();
        if (platform == null)
            return;
        ScreenOpener.open(new CommutePlatformScreen(te, platform));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        // TODO
        return AllShapes.STATION;
    }

    @Override
    public Class<CommutePlatformBlockEntity> getBlockEntityClass() {
        return CommutePlatformBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends CommutePlatformBlockEntity> getBlockEntityType() {
        return CcnBlockEntities.COMMUTE_PLATFORM.get();
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter reader, BlockPos pos, PathComputationType type) {
        return false;
    }
}
