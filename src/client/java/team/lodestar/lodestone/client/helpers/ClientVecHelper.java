package team.lodestar.lodestone.client.helpers;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import team.lodestar.lodestone.client.mixin.accessor.GameRendererAccessor;
import team.lodestar.lodestone.helpers.VecHelper;

public class ClientVecHelper {
    // https://forums.minecraftforge.net/topic/88562-116solved-3d-to-2d-conversion/?do=findComment&comment=413573 slightly modified
    public static Vec3d projectToPlayerView(Vec3d target, float deltaTicks) {
        MinecraftClient client = MinecraftClient.getInstance();
        GameRenderer gameRenderer = client.gameRenderer;
        /*
         * The (centered) location on the screen create the given 3d point in the world.
         * Result is (dist right create center screen, dist up from center screen, if < 0, then in front create view plane)
         */
        Camera ari = gameRenderer.getCamera();
        Vec3d camera_pos = ari.getPos();
        Quaternionf camera_rotation_conj = new Quaternionf(ari.getRotation());
        camera_rotation_conj.conjugate();

        Vector3f result3f = new Vector3f((float) (camera_pos.x - target.x), (float) (camera_pos.y - target.y),
                (float) (camera_pos.z - target.z));
        result3f.rotate(camera_rotation_conj);

        // ----- compensate for view bobbing (if active) -----
        // the following code adapted from GameRenderer::applyBobbing (to invert it)
        if (client.options.getBobView().getValue()) {
            Entity renderViewEntity = client.getCameraEntity();
            if (renderViewEntity instanceof PlayerEntity player) {
                float horizontalSpeed = player.horizontalSpeed;

                float f = horizontalSpeed - player.prevHorizontalSpeed;
                float f1 = -(horizontalSpeed + f * deltaTicks);
                float f2 = MathHelper.lerp(deltaTicks, player.prevStrideDistance, player.strideDistance);
                Quaternionf q2 = new Quaternionf(new AxisAngle4f(Math.abs(MathHelper.cos(f1 * (float) Math.PI - 0.2F) * f2) * 5.0F, VecHelper.Vector3fHelper.XP));
                q2.conjugate();
                result3f.rotate(q2);

                Quaternionf q1 = new Quaternionf(new AxisAngle4f(Math.abs(MathHelper.sin(f1 * (float) Math.PI) * f2) * 3.0F, VecHelper.Vector3fHelper.ZP));
                q1.conjugate();
                result3f.rotate(q1);

                Vector3f bob_translation = new Vector3f((MathHelper.sin(f1 * (float) Math.PI) * f2 * 0.5F), (-Math.abs(MathHelper.cos(f1 * (float) Math.PI) * f2)), 0.0f);
                result3f.add(new Vector3f(bob_translation.x(), -bob_translation.y(), bob_translation.z()));
            }
        }

        // ----- adjust for fov -----
        float fov = (float) ((GameRendererAccessor) gameRenderer).invokeGetFov(ari, deltaTicks, true);

        float halfHeight = (float) client.getWindow().getScaledHeight() / 2;
        float scaleFactor = halfHeight / (result3f.z() * (float) Math.tan(Math.toRadians(fov / 2)));
        return new Vec3d(-result3f.x() * scaleFactor, result3f.y() * scaleFactor, result3f.z());
    }
}
