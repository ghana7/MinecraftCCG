package ghana7.minecraftccg.game;

import ghana7.minecraftccg.MinecraftCCG;
import ghana7.minecraftccg.cards.CardBuilder;
import ghana7.minecraftccg.cards.TradingCard;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CardTable extends Block {

    public CardTable() {
        super(Properties.create(Material.WOOD)
                .harvestTool(ToolType.AXE)
                .notSolid().setOpaque((BlockState p_test_1_, IBlockReader p_test_2_, BlockPos p_test_3_) -> (false)));
    }

    @Override
    public boolean hasTileEntity(final BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(final BlockState state, final IBlockReader world) {
        return MinecraftCCG.CARD_TABLE_TE.get().create();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {

        ItemStack stack = player.getHeldItem(handIn);
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        CardTableTileEntity cardTable;
        if(tileEntity instanceof CardTableTileEntity) {
            cardTable = ((CardTableTileEntity) tileEntity);
            if(cardTable.hasHeldCard()) {
                if(stack.isEmpty()) {
                    player.setHeldItem(handIn, cardTable.extractHeldCard());
                    return ActionResultType.SUCCESS;
                }
                if(stack.getItem() instanceof GamingStick) {
                    if(!worldIn.isRemote) {
                        ((TradingCard)cardTable.getHeldCard().getItem()).onGameActivate(stack, player);
                    }
                    return ActionResultType.SUCCESS;
                }
            } else {
                if(stack.getItem() instanceof TradingCard) {
                    cardTable.insertHeldCard(CardBuilder.makeSingleCopy(stack));
                    stack.shrink(1);
                    return ActionResultType.SUCCESS;
                }
            }
        } else {
            throw new IllegalStateException("Our named container provider is missing");
        }

        return ActionResultType.PASS;
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        CardTableTileEntity cardTable;
        if(tileEntity instanceof CardTableTileEntity) {
            cardTable = (CardTableTileEntity) tileEntity;
            InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), cardTable.extractHeldCard());
        } else {
            throw new IllegalStateException("Our named container provider is missing");
        }
    }
}
