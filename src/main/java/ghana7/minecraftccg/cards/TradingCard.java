package ghana7.minecraftccg.cards;

import ghana7.minecraftccg.MinecraftCCG;
import ghana7.minecraftccg.ModItemGroup;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.command.arguments.NBTCompoundTagArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public abstract class TradingCard extends Item {
    public TradingCard() {
        super(new Item.Properties().group(ModItemGroup.TRADING_CARDS));
    }

    public void onGameActivate(ItemStack stackIn, PlayerEntity playerIn) {
        MinecraftCCG.LOGGER.debug("card activated");
    }
    @Override
    public boolean hasEffect(ItemStack stack) {
        CompoundNBT nbt = stack.getOrCreateTag();
        if(nbt.contains("foil")) {
            return nbt.getBoolean("foil");
        }
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        CompoundNBT nbt = stack.getOrCreateTag();
        if(nbt.contains("foil") && nbt.getBoolean("foil")) {
            tooltip.add(new StringTextComponent("Foil").setStyle(Style.EMPTY.setColor(Color.fromHex("#00FFFF"))));
        }
    }

}
