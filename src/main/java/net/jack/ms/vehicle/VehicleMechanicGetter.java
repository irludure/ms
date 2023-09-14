package net.jack.ms.vehicle;

import net.minecraft.world.entity.Entity;

public class VehicleMechanicGetter {
    public static boolean isVehicleIndustrial(Entity vehicle) {
        if (vehicle != null) {
            if (vehicle.getType().getRegistryName() != null) {
                return vehicle.getType().getRegistryName().toString().equals("ms:motorboat");
            }
        }
        return false;
    }
}
