import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class Test {

    static MinecraftClient mc = MinecraftClient.getInstance();

    public static void crystal() {
        if (mc.player == null || mc.world == null) return;

        if (mc.player.getMainHandStack().getItem() != Items.END_CRYSTAL) return;

        if (!mc.options.useKey.isPressed()) return;


        HitResult hitResult = mc.crosshairTarget;
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = ((BlockHitResult) hitResult).getBlockPos();
            Direction direction = ((BlockHitResult) hitResult).getSide();
            mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, new BlockHitResult(Vec3d.of(blockPos), direction, blockPos, true));
        }

        if (mc.crosshairTarget instanceof EntityHitResult hit) {
            mc.interactionManager.attackEntity(mc.player, hit.getEntity());
            mc.player.swingHand(Hand.MAIN_HAND);
            hit.getEntity().kill();
            hit.getEntity().remove(Entity.RemovalReason.KILLED);
        }
    }
}
