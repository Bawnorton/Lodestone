package team.lodestar.lodestone.client.helpers;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import team.lodestar.lodestone.client.access.Accessor;
import team.lodestar.lodestone.client.systems.rendering.LodestoneRenderLayer;
import team.lodestar.lodestone.helpers.DataHelper;
import team.lodestar.lodestone.helpers.VecHelper;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class RenderHelper {
    public static final int FULL_BRIGHT = 0xF000F0;

    public static ShaderProgram getShader(RenderLayer renderLayer) {
        if(renderLayer instanceof LodestoneRenderLayer lodestoneRenderLayer) {
            Optional<Supplier<ShaderProgram>> shader = Accessor.of(Accessor.of(lodestoneRenderLayer.multiPhaseParameters).getProgram()).getSupplier();
            if(shader.isPresent()) {
                return shader.get().get();
            }
        }
        return null;
    }

    public static RenderPhase.Transparency getTransparency(RenderLayer renderLayer) {
        if(renderLayer instanceof LodestoneRenderLayer lodestoneRenderLayer) {
            return Accessor.of(lodestoneRenderLayer.multiPhaseParameters).getTransparency();
        }
        return null;
    }

    public static void vertexPos(VertexConsumer vertexConsumer, Matrix4f last, float x, float y, float z) {
        vertexConsumer.vertex(last, x, y, z).next();
    }

    public static void vertexPosUV(VertexConsumer vertexConsumer, Matrix4f last, float x, float y, float z, float u, float v) {
        vertexConsumer.vertex(last, x, y, z).texture(u, v).next();
    }

    public static void vertexPosUVLight(VertexConsumer vertexConsumer, Matrix4f last, float x, float y, float z, float u, float v, int light) {
        vertexConsumer.vertex(last, x, y, z).texture(u, v).light(light).next();
    }

    public static void vertexPosColor(VertexConsumer vertexConsumer, Matrix4f last, float x, float y, float z, float r, float g, float b, float a) {
        vertexConsumer.vertex(last, x, y, z).color(r, g, b, a).next();
    }

    public static void vertexPosColorUV(VertexConsumer vertexConsumer, Matrix4f last, float x, float y, float z, float r, float g, float b, float a, float u, float v) {
        vertexConsumer.vertex(last, x, y, z).color(r, g, b, a).texture(u, v).next();
    }

    public static void vertexPosColorUVLight(VertexConsumer vertexConsumer, Matrix4f last, float x, float y, float z, float r, float g, float b, float a, float u, float v, int light) {
        vertexConsumer.vertex(last, x, y, z).color(r, g, b, a).texture(u, v).light(light).next();
    }

    public static Vector3f parametricSphere(float u, float v, float r) {
        return new Vector3f(MathHelper.cos(u) * MathHelper.sin(v) * r, MathHelper.cos(v) * r, MathHelper.sin(u) * MathHelper.sin(v) * r);
    }

    public static Vec2f screenSpaceQuadOffsets(Vector4f start, Vector4f end, float width) {
        float x = -start.x();
        float y = -start.y();
        if (Math.abs(start.z()) > 0) {
            float ratio = end.z() / start.z();
            x = end.x() + x * ratio;
            y = end.y() + y * ratio;
        } else if (Math.abs(end.z()) <= 0) {
            x += end.x();
            y += end.y();
        }
        if (start.z() > 0) {
            x = -x;
            y = -y;
        }
        if (x * x + y * y > 0F) {
            float normalize = width * 0.5F / DataHelper.distance(x, y);
            x *= normalize;
            y *= normalize;
        }
        return new Vec2f(-y, x);
    }

    public static Vector4f midpoint(Vector4f a, Vector4f b) {
        return new Vector4f((a.x() + b.x()) * 0.5F, (a.y() + b.y()) * 0.5F, (a.z() + b.z()) * 0.5F, (a.w() + b.w()) * 0.5F);
    }

    public static Vec2f worldPosToTexCoord(Vector3f worldPos, MatrixStack viewModelStack) {
        Matrix4f posMat = viewModelStack.peek().getPositionMatrix();
        Matrix4f projMat = RenderSystem.getProjectionMatrix();

        Vector3f localPos = new Vector3f(worldPos);
        localPos.sub(MinecraftClient.getInstance().gameRenderer.getCamera().getPos().toVector3f());

        Vector4f pos = new Vector4f(localPos, 0);
        pos.mul(posMat);
        pos.mul(projMat);
        VecHelper.Vector4fHelper.perspectiveDivide(pos);

        return new Vec2f((pos.x() + 1F) / 2F, (pos.y() + 1F) / 2F);
    }

    public static void drawSteppedLineBetween(VertexConsumerProvider provider, MatrixStack matrixStack, List<Vec3d> points, float lineWidth, int r, int g, int b, int a) {
        Vec3d origin = points.get(0);
        for (int i = 1; i < points.size(); i++) {
            Vec3d target = points.get(i);
            drawLineBetween(provider, matrixStack, origin, target, lineWidth, r, g, b, a);
            origin = target;
        }
    }

    public static void drawSteppedLineBetween(VertexConsumerProvider provider, MatrixStack matrixStack, Vec3d start, Vec3d end, int steps, float lineWidth, int r, int g, int b, int a, Consumer<Vec3d> pointConsumer) {
        Vec3d origin = start;
        for (int i = 1; i <= steps; i++) {
            Vec3d target = start.add(end.subtract(start).multiply(i / (float) steps));
            pointConsumer.accept(target);
            drawLineBetween(provider, matrixStack, origin, target, lineWidth, r, g, b, a);
            origin = target;
        }
    }

    public static void drawLineBetween(VertexConsumerProvider provider, MatrixStack matrixStack, Vec3d local, Vec3d target, float lineWidth, int r, int g, int b, int a) {
        VertexConsumer builder = provider.getBuffer(RenderLayer.getLeash());

        float rotY = (float) MathHelper.atan2(target.x - local.x, target.z - local.z);

        double distX = target.x - local.x;
        double distZ = target.z - local.z;
        float rotX = (float) MathHelper.atan2(target.y - local.y, MathHelper.sqrt((float) (distX * distX + distZ * distZ)));

        matrixStack.push();

        matrixStack.translate(local.x, local.y, local.z);
        matrixStack.multiply(VecHelper.Vector3fHelper.rotation(rotY, VecHelper.Vector3fHelper.YP));
        matrixStack.multiply(VecHelper.Vector3fHelper.rotation(rotX, VecHelper.Vector3fHelper.XN));

        float distance = (float) local.distanceTo(target);

        Matrix4f positionMatrix = matrixStack.peek().getPositionMatrix();
        float halfWidth = lineWidth / 2F;

        builder.vertex(positionMatrix, -halfWidth, 0, 0).color(r, g, b, a).light(0xF000F0).next();
        builder.vertex(positionMatrix, halfWidth, 0, 0).color(r, g, b, a).light(0xF000F0).next();
        builder.vertex(positionMatrix, halfWidth, 0, distance).color(r, g, b, a).light(0xF000F0).next();
        builder.vertex(positionMatrix, -halfWidth, 0, distance).color(r, g, b, a).light(0xF000F0).next();

        builder.vertex(positionMatrix, 0, -halfWidth, 0).color(r, g, b, a).light(0xF000F0).next();
        builder.vertex(positionMatrix, 0, halfWidth, 0).color(r, g, b, a).light(0xF000F0).next();
        builder.vertex(positionMatrix, 0, halfWidth, distance).color(r, g, b, a).light(0xF000F0).next();
        builder.vertex(positionMatrix, 0, -halfWidth, distance).color(r, g, b, a).light(0xF000F0).next();

        matrixStack.pop();
    }
}
