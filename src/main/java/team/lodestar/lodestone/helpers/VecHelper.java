package team.lodestar.lodestone.helpers;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;
import java.util.ArrayList;
import java.util.List;

public class VecHelper {
    public static final Vec3d CENTER_OF_ORIGIN = new Vec3d(.5, .5, .5);

    /**
     * A method that returns a position on the perimeter of a circle around a given Vec3d position
     *
     * @param pos      - Defines the center of the circle
     * @param distance - Defines the radius of your circle
     * @param current  - Defines the current point we are calculating the position for on the circle
     * @param total    - Defines the total amount of points in the circle
     */
    public static Vec3d radialOffset(Vec3d pos, float distance, float current, float total) {
        double angle = current / total * (Math.PI * 2);
        return pos.add(rotate((float) angle, distance, distance));
    }

    /**
     * A method that returns an array list of positions on the perimeter of a circle around a given Vec3d position.
     * These positions constantly rotate around the center of the circle based on gameTime
     *
     * @param pos      - Defines the center of the circle
     * @param distance - Defines the radius of your circle
     * @param total    - Defines the total amount of points in the circle
     * @param gameTime - Defines the current game time value
     * @param time     - Defines the total time for one position to complete a full rotation cycle
     */
    public static List<Vec3d> rotatingRadialOffsets(Vec3d pos, float distance, float total, long gameTime, float time) {
        return rotatingRadialOffsets(pos, distance, distance, total, gameTime, time);
    }

    /**
     * A method that returns an array list of positions on the perimeter of a sphere around a given Vec3d position.
     * These positions constantly rotate around the center of the circle based on gameTime.
     */
    public static List<Vec3d> rotatingRadialOffsets(Vec3d pos, float distanceX, float distanceZ, float total, long gameTime, float time) {
        List<Vec3d> positions = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            positions.add(rotatingRadialOffset(pos, distanceX, distanceZ, i, total, gameTime, time));
        }
        return positions;
    }

    /**
     * A method that returns a single position on the perimeter of a circle around a given Vec3d position.
     * These positions constantly rotate around the center of the circle based on gameTime
     */
    public static Vec3d rotatingRadialOffset(Vec3d pos, float distance, float current, float total, long gameTime, float time) {
        return rotatingRadialOffset(pos, distance, distance, current, total, gameTime, time);
    }

    /**
     * A method that returns a single position on the perimeter of a circle around a given Vec3d position.
     * These positions constantly rotate around the center of the circle based on gameTime
     */
    public static Vec3d rotatingRadialOffset(Vec3d pos, float distanceX, float distanceZ, float current, float total, long gameTime, float time) {
        double angle = current / total * (Math.PI * 2);
        angle += ((gameTime % time) / time) * (Math.PI * 2);
        return pos.add(rotate((float) angle, distanceX, distanceZ));
    }

    public static Vec3d rotate(float angle, float distanceX, float distanceZ) {
        double dx2 = (distanceX * Math.cos(angle));
        double dz2 = (distanceZ * Math.sin(angle));

        Vec3d vector2f = new Vec3d(dx2, 0, dz2);
        double x = vector2f.x * distanceX;
        double z = vector2f.z * distanceZ;
        return new Vec3d(x, 0, z);
    }

    public static List<Vec3d> blockOutlinePositions(World world, BlockPos pos) {
        List<Vec3d> list = new ArrayList<>();
        double d0 = 0.5625D;
        var random = world.random;
        for (Direction direction : Direction.values()) {
            BlockPos blockpos = pos.offset(direction);
            if (!world.getBlockState(blockpos).isOpaqueFullCube(world, blockpos)) {
                Direction.Axis direction$axis = direction.getAxis();
                double d1 = direction$axis == Direction.Axis.X ? 0.5D + d0 * (double) direction.getOffsetX() : (double) random.nextFloat();
                double d2 = direction$axis == Direction.Axis.Y ? 0.5D + d0 * (double) direction.getOffsetY() : (double) random.nextFloat();
                double d3 = direction$axis == Direction.Axis.Z ? 0.5D + d0 * (double) direction.getOffsetZ() : (double) random.nextFloat();
                list.add(new Vec3d((double) pos.getX() + d1, (double) pos.getY() + d2, (double) pos.getZ() + d3));
            }
        }
        return list;
    }

    public static Vec3d getCenterOf(Vec3i pos) {
        if (pos.equals(Vec3i.ZERO))
            return CENTER_OF_ORIGIN;
        return Vec3d.of(pos).add(.5f, .5f, .5f);
    }

    public static Vec3d axisAlignedPlaneOf(Vec3d vec) {
        vec = vec.normalize();
        return new Vec3d(1, 1, 1).subtract(Math.abs(vec.x), Math.abs(vec.y), Math.abs(vec.z));
    }

    public static Vec3d rotate(Vec3d vec, double deg, Direction.Axis axis) {
        if (deg == 0)
            return vec;
        if (vec == Vec3d.ZERO)
            return vec;

        float angle = (float) (deg / 180f * Math.PI);
        double sin = MathHelper.sin(angle);
        double cos = MathHelper.cos(angle);
        double x = vec.x;
        double y = vec.y;
        double z = vec.z;

        if (axis == Direction.Axis.X)
            return new Vec3d(x, y * cos - z * sin, z * cos + y * sin);
        if (axis == Direction.Axis.Y)
            return new Vec3d(x * cos + z * sin, y, z * cos - x * sin);
        if (axis == Direction.Axis.Z)
            return new Vec3d(x * cos - y * sin, y * cos + x * sin, z);
        return vec;
    }

    public static class Vector3fHelper {
        public static Vector3f XP = new Vector3f(1.0F, 0.0F, 0.0F);
        public static Vector3f YP = new Vector3f(0.0F, 1.0F, 0.0F);
        public static Vector3f ZP = new Vector3f(0.0F, 0.0F, 1.0F);
        public static Vector3f XN = new Vector3f(-1.0F, 0.0F, 0.0F);
        public static Vector3f YN = new Vector3f(0.0F, -1.0F, 0.0F);
        public static Vector3f ZN = new Vector3f(0.0F, 0.0F, -1.0F);

        public static Quaternionf rotation(float rotation, Vector3f axis) {
            return new Quaternionf(new AxisAngle4f(rotation, axis));
        }
    }

    public static class Vector4fHelper {
        public static void perspectiveDivide(Vector4f v) {
            v.div(v.x, v.y, v.z, 1.0f);
        }
    }
}
