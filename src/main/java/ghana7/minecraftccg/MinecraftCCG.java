package ghana7.minecraftccg;

import ghana7.minecraftccg.cardpacks.DebugCardPack;
import ghana7.minecraftccg.cards.BlankCard;
import ghana7.minecraftccg.cards.CowCard;
import ghana7.minecraftccg.cards.FireCard;
import ghana7.minecraftccg.cards.VillagerCard;
import ghana7.minecraftccg.game.CardTable;
import ghana7.minecraftccg.game.CardTableTileEntity;
import ghana7.minecraftccg.game.CardTableTileEntityRenderer;
import ghana7.minecraftccg.game.GamingStick;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MinecraftCCG.MODID)
public class MinecraftCCG
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "minecraftccg";

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final RegistryObject<Block> CARD_TABLE_BLOCK = BLOCKS.register("card_table", () ->
            new CardTable()
    );

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    //BlockItems
    public static final RegistryObject<Item> CARD_TABLE_BLOCK_ITEM = ITEMS.register("card_table", () ->
            new BlockItem(
                    CARD_TABLE_BLOCK.get(),
                    new Item.Properties().group(ModItemGroup.TRADING_CARDS)
            )
    );

    //Misc. Items
    public static final RegistryObject<Item> GAMING_STICK = ITEMS.register("gaming_stick", () ->
            new GamingStick()
    );

    //Cards
    public static final RegistryObject<Item> BLANK_CARD = MinecraftCCG.ITEMS.register("blank_card", () ->
            new BlankCard()
    );

    public static final RegistryObject<Item> COW_CARD = MinecraftCCG.ITEMS.register("cow_card", () ->
            new CowCard()
    );

    public static final RegistryObject<Item> VILLAGER_CARD = MinecraftCCG.ITEMS.register("villager_card", () ->
            new VillagerCard()
    );

    public static final RegistryObject<Item> FIRE_CARD = MinecraftCCG.ITEMS.register("fire_card", () ->
            new FireCard()
    );

    public static final RegistryObject<Item> DEBUG_CARD_PACK = MinecraftCCG.ITEMS.register("card_pack", () ->
            new DebugCardPack()
    );


    private static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MODID);
    public static final RegistryObject<TileEntityType<CardTableTileEntity>> CARD_TABLE_TE = TILE_ENTITY_TYPES.register(
            "card_table", () -> TileEntityType.Builder.create(CardTableTileEntity::new, CARD_TABLE_BLOCK.get()).build(null)
    );

    public MinecraftCCG() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILE_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());

        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
        ClientRegistry.bindTileEntityRenderer(CARD_TABLE_TE.get(), CardTableTileEntityRenderer::new);

        RenderTypeLookup.setRenderLayer(CARD_TABLE_BLOCK.get(), RenderType.getTranslucent());
    }
}
