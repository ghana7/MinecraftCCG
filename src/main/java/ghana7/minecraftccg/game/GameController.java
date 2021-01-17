package ghana7.minecraftccg.game;


import ghana7.minecraftccg.MinecraftCCG;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

public class GameController extends Block {
    public GameController() {
        super(Properties.create(Material.WOOD)
                .harvestTool(ToolType.AXE)
                .notSolid()
                .setOpaque((BlockState p_test_1_, IBlockReader p_test_2_, BlockPos p_test_3_) -> (false)));
    }

    @Override
    public boolean hasTileEntity(final BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(final BlockState state, final IBlockReader world) {
        return MinecraftCCG.GAME_CONTROLLER_TE.get().create();
    }
}
