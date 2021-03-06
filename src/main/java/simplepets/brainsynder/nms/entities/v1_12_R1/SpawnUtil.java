package simplepets.brainsynder.nms.entities.v1_12_R1;

import net.minecraft.server.v1_12_R1.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Shulker;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.util.EulerAngle;
import simplepets.brainsynder.PetCore;
import simplepets.brainsynder.api.entity.IEntityControllerPet;
import simplepets.brainsynder.api.entity.IEntityPet;
import simplepets.brainsynder.api.pet.IPet;
import simplepets.brainsynder.errors.SimplePetsException;
import simplepets.brainsynder.nms.entities.v1_12_R1.impossamobs.EntityArmorStandPet;
import simplepets.brainsynder.nms.entities.v1_12_R1.impossamobs.EntityGhostStandPet;
import simplepets.brainsynder.nms.entities.v1_12_R1.impossamobs.EntityShulkerPet;
import simplepets.brainsynder.nms.entities.v1_12_R1.list.EntityControllerPet;
import simplepets.brainsynder.pet.types.ArmorStandDefault;
import simplepets.brainsynder.pet.types.ShulkerDefault;
import simplepets.brainsynder.reflection.ReflectionUtil;
import simplepets.brainsynder.utils.ISpawner;

import java.util.HashMap;
import java.util.Map;

public class SpawnUtil implements ISpawner {
    private Map<String, Class<?>> petMap;

    @Override
    public void init() {
        petMap = new HashMap<>();

        PetCore.get().getTypeManager().getTypes().forEach(type -> {
            String name = type.getEntityClass().getSimpleName().replaceFirst("I", "");
            Class<?> clazz = ReflectionUtil.getPetNMSClass(name);
            petMap.put(name, clazz);
        });
    }

    public IEntityPet spawnEntityPet(IPet pet, String className) {
        Location l = pet.getOwner().getLocation();
        return spawn(l, pet, className);
    }

    public IEntityPet spawn(Location l, IPet pet, String className) {
        try {
            World mcWorld = ((CraftWorld) l.getWorld()).getHandle();
            EntityPet customEntity = (EntityPet) petMap.get(className).getDeclaredConstructor(World.class, IPet.class).newInstance(mcWorld, pet);
            customEntity.setInvisible(false);
            customEntity.setLocation(l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());

            mcWorld.addEntity(customEntity, CreatureSpawnEvent.SpawnReason.CUSTOM);
            if (customEntity instanceof IEntityControllerPet) {
                if (pet.getPetType() instanceof ArmorStandDefault) {
                    ArmorStand stand = EntityArmorStandPet.spawn(l, ((EntityControllerPet) customEntity));
                    stand.setGravity(false);
                    stand.setArms(true);
                    stand.setCollidable(false);
                    stand.setLeftLegPose(new EulerAngle(0.0D, 0.0D, 0.0D));
                    stand.setRightLegPose(new EulerAngle(0.0D, 0.0D, 0.0D));
                    stand.setLeftArmPose(new EulerAngle(0.0D, 0.0D, 0.0D));
                    stand.setRightArmPose(new EulerAngle(0.0D, 0.0D, 0.0D));
                    ((IEntityControllerPet) customEntity).setDisplayEntity(stand);
                } else if (pet.getPetType() instanceof ShulkerDefault) {
                    ArmorStand stand = EntityGhostStandPet.spawn(l, pet);
                    stand.setGravity(false);
                    stand.setCollidable(false);
                    stand.setSmall(true);
                    Shulker shulker = EntityShulkerPet.spawn(l, (EntityControllerPet) customEntity);
                    shulker.setAI(false);
                    shulker.setCollidable(false);
                    ((CraftEntity) stand).getHandle().passengers.add(0, ((CraftEntity) shulker).getHandle());
                    ((IEntityControllerPet) customEntity).setDisplayEntity(stand);
                }
            }

            return customEntity;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SimplePetsException("Could not summon the " + pet.getPetType().getConfigName() + " Pet", e);
        }
    }

    @Override
    public IEntityPet spawnEntityPet(Location l, IPet pet, String className) {
        return spawn(l, pet, className);
    }
}
