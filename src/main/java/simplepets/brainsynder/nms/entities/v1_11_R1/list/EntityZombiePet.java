package simplepets.brainsynder.nms.entities.v1_11_R1.list;

import net.minecraft.server.v1_11_R1.DataWatcher;
import net.minecraft.server.v1_11_R1.DataWatcherObject;
import net.minecraft.server.v1_11_R1.DataWatcherRegistry;
import net.minecraft.server.v1_11_R1.World;
import simplepets.brainsynder.api.entity.hostile.IEntityZombiePet;
import simplepets.brainsynder.api.pet.IPet;
import simplepets.brainsynder.nms.entities.v1_11_R1.AgeableEntityPet;

public class EntityZombiePet extends AgeableEntityPet implements IEntityZombiePet {
    private static final DataWatcherObject<Integer> VILLAGER_TYPE;
    private static final DataWatcherObject<Boolean> ARMS_RAISED;

    static {
        VILLAGER_TYPE = DataWatcher.a(EntityZombiePet.class, DataWatcherRegistry.b);
        ARMS_RAISED = DataWatcher.a(EntityZombiePet.class, DataWatcherRegistry.h);
    }


    public EntityZombiePet(World world) {
        super(world);
    }

    public EntityZombiePet(World world, IPet pet) {
        super(world, pet);
    }

    @Override
    protected void registerDatawatchers() {
        super.registerDatawatchers();
        this.getDataWatcher().register(VILLAGER_TYPE, 0);
        this.getDataWatcher().register(ARMS_RAISED, false);
    }

    @Override
    public boolean isArmsRaised() {
        return datawatcher.get(ARMS_RAISED);
    }

    @Override
    public void setArmsRaised(boolean flag) {
        datawatcher.set(ARMS_RAISED, flag);
    }
}
