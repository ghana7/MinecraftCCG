package ghana7.minecraftccg.game;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import ghana7.minecraftccg.MinecraftCCG;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class CardTableTileEntityRenderer extends TileEntityRenderer<CardTableTileEntity> {
    public CardTableTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(CardTableTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if(tileEntityIn.hasHeldCard()) {
            ItemStack itemstack = tileEntityIn.getHeldCard();
            matrixStackIn.push();
            matrixStackIn.translate(0.5, 1.01, 0.5);
            matrixStackIn.scale(0.5f,0.5f,0.5f);
            matrixStackIn.rotate(new Quaternion(new Vector3f(1.0f,0.0f,0.0f), 90, true));
            RenderHelper.disableStandardItemLighting();
            Minecraft.getInstance().getItemRenderer().renderItem(itemstack, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
            RenderHelper.enableStandardItemLighting();
            matrixStackIn.pop();
        }
    }
}
