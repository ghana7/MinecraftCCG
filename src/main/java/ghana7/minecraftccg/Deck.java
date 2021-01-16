package ghana7.minecraftccg;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class Deck extends Item {
    public Deck() {
        super(new Item.Properties().group(ModItemGroup.TRADING_CARDS));
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        
        return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
    }
}
