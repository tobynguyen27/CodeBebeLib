package dev.tobynguyen27.codebebelib.fluid;

import io.github.fabricators_of_create.porting_lib.util.FluidAttributes;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class FluidUtils {

    public static long B = FluidAttributes.BUCKET_VOLUME;
    public static FluidStack water = new FluidStack(Fluids.WATER, 1000);
    public static FluidStack lava = new FluidStack(Fluids.LAVA, 1000);

    public static int getLuminosity(FluidStack stack, double density) {
        if (stack.isEmpty()) {
            return 0;
        }
        Fluid fluid = stack.getFluid();
        FluidAttributes attributes = fluid.getAttributes();
        int light = attributes.getLuminosity(stack);
        if (attributes.isGaseous()) {
            light = (int) (light * density);
        }
        return light;
    }
}
