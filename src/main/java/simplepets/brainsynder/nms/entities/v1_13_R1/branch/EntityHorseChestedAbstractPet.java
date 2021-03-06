package simplepets.brainsynder.nms.entities.v1_13_R1.branch;

import net.minecraft.server.v1_13_R1.*;
import simple.brainsynder.nbt.StorageTagCompound;
import simplepets.brainsynder.api.entity.IChestedAbstractPet;
import simplepets.brainsynder.api.pet.IPet;

public abstract class EntityHorseChestedAbstractPet extends EntityHorseAbstractPet implements IChestedAbstractPet {
    private static final DataWatcherObject<Boolean> CHEST;

    static {
        CHEST = DataWatcher.a(EntityHorseChestedAbstractPet.class, DataWatcherRegistry.i);
    }


    public EntityHorseChestedAbstractPet(EntityTypes<?> type, World world) {
        super(type, world);
    }

    public EntityHorseChestedAbstractPet(EntityTypes<?> type, World world, IPet pet) {
        super(type,world, pet);
    }

    @Override
    protected void registerDatawatchers() {
        super.registerDatawatchers();
        this.datawatcher.register(CHEST, Boolean.FALSE);
    }

    @Override
    public StorageTagCompound asCompound() {
        StorageTagCompound object = super.asCompound();
        object.setBoolean("chested", isChested());
        return object;
    }

    @Override
    public void applyCompound(StorageTagCompound object) {
        super.applyCompound(object);
        if (object.hasKey("chested")) {
            setChested(object.getBoolean("chested"));
        }
    }

    @Override
    public boolean isChested() {
        return datawatcher.get(CHEST);
    }

    public void setChested(boolean flag) {
        this.datawatcher.set(CHEST, flag);
    }
}