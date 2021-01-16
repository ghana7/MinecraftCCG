package ghana7.minecraftccg.cards;

import ghana7.minecraftccg.MinecraftCCG;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;
import org.lwjgl.system.CallbackI;

public class BlankCard extends TradingCard{
    public BlankCard() {
        super();
    }

    @Override
    public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        if(!playerIn.world.isRemote) {
            String targetName = target.getType().getName().getString();
            switch (targetName) {
                case "Cow":
                    stack.shrink(1);
                    GetCard(playerIn, MinecraftCCG.COW_CARD);
                    return ActionResultType.SUCCESS;
                case "Villager":
                    stack.shrink(1);
                    GetCard(playerIn, MinecraftCCG.VILLAGER_CARD);
                    return ActionResultType.SUCCESS;
                default:
                    return ActionResultType.PASS;
            }
        }

        return ActionResultType.PASS;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if(!context.getWorld().isRemote) {
            String targetName = context.getWorld().getBlockState(context.getPos()).getBlock().getTranslatedName().getString();
            switch (targetName) {
                case "Fire":
                    context.getItem().shrink(1);
                    GetCard(context.getPlayer(), MinecraftCCG.FIRE_CARD);
                    return ActionResultType.SUCCESS;
                default:
                    return ActionResultType.PASS;
            }
        }
        return ActionResultType.PASS;
    }

    private void GetCard(PlayerEntity playerIn, RegistryObject<Item> cardType) {
        ItemEntity newCard = new ItemEntity(playerIn.world, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), CardBuilder.makeCard(cardType, 1, true));
        newCard.setNoPickupDelay();
        playerIn.world.addEntity(newCard);
    }
}
