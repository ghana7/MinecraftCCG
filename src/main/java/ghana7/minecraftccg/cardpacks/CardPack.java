package ghana7.minecraftccg.cardpacks;

import ghana7.minecraftccg.ModItemGroup;
import ghana7.minecraftccg.cards.CardBuilder;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.FastRandom;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class CardPack extends Item {
    protected List<RegistryObject<Item>> commonDrops;
    protected List<RegistryObject<Item>> uncommonDrops;
    protected List<RegistryObject<Item>> rareDrops;
    protected List<RegistryObject<Item>> legendaryDrops;
    protected int size;
    protected float foilChance;

    private static Random rand = new Random();
    public CardPack() {
        super(new Item.Properties().group(ModItemGroup.TRADING_CARDS));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ArrayList<ItemStack> cardDrops = new ArrayList<ItemStack>();
        for(int i = 0; i < size; i++) {
            float value = rand.nextFloat();
            if (value < 0.7f) {
                int index = rand.nextInt(commonDrops.size());
                boolean foil = rand.nextFloat() < foilChance;
                cardDrops.add(CardBuilder.makeCard(commonDrops.get(index), 1, foil));
            } else if (value < 0.85f) {
                int index = rand.nextInt(uncommonDrops.size());
                boolean foil = rand.nextFloat() < foilChance;
                cardDrops.add(CardBuilder.makeCard(uncommonDrops.get(index), 1, foil));
            } else if (value < 0.95f) {
                int index = rand.nextInt(rareDrops.size());
                boolean foil = rand.nextFloat() < foilChance;
                cardDrops.add(CardBuilder.makeCard(rareDrops.get(index), 1, foil));
            } else {
                int index = rand.nextInt(legendaryDrops.size());
                boolean foil = rand.nextFloat() < foilChance;
                cardDrops.add(CardBuilder.makeCard(legendaryDrops.get(index), 1, foil));
            }
        }
        for ( ItemStack cardStack : cardDrops) {
            ItemEntity newCard = new ItemEntity(playerIn.world, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), cardStack);
            newCard.setNoPickupDelay();
            playerIn.world.addEntity(newCard);
        }

        ItemStack itemStack = playerIn.getHeldItem(handIn);
        return ActionResult.resultConsume(itemStack);
    }
}
