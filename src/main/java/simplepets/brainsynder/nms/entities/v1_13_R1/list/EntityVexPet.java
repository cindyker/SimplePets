package simplepets.brainsynder.nms.entities.v1_13_R1.list;

import net.minecraft.server.v1_13_R1.DataWatcher;
import net.minecraft.server.v1_13_R1.DataWatcherObject;
import net.minecraft.server.v1_13_R1.DataWatcherRegistry;
import net.minecraft.server.v1_13_R1.World;
import simple.brainsynder.nbt.StorageTagCompound;
import simplepets.brainsynder.api.Size;
import simplepets.brainsynder.api.entity.hostile.IEntityVexPet;
import simplepets.brainsynder.api.pet.IPet;
import simplepets.brainsynder.nms.entities.v1_13_R1.EntityNoClipPet;
import simplepets.brainsynder.nms.registry.v1_13_R1.Types;

@Size(width = 0.4F, length = 0.8F)
public class EntityVexPet extends EntityNoClipPet implements IEntityVexPet {
    protected static final DataWatcherObject<Byte> VEX_FLAGS;

    static {
        VEX_FLAGS = DataWatcher.a(EntityVexPet.class, DataWatcherRegistry.a);
    }

    public EntityVexPet(World world) {
        super(Types.VEX, world);
    }

    public EntityVexPet(World world, IPet pet) {
        super(Types.VEX, world, pet);
    }

    protected void registerDatawatchers() {
        super.registerDatawatchers();
        this.getDataWatcher().register(VEX_FLAGS, (byte) 0);
    }

    @Override
    public StorageTagCompound asCompound() {
        StorageTagCompound object = super.asCompound();
        object.setBoolean("powered", isPowered());
        return object;
    }

    @Override
    public void applyCompound(StorageTagCompound object) {
        if (object.hasKey("powered")) setPowered(object.getBoolean("powered"));
        super.applyCompound(object);
    }

    public boolean isPowered() {
        return (datawatcher.get(VEX_FLAGS) & 1) != 0;
    }

    public void setPowered(boolean flag) {
        byte b0 = this.datawatcher.get(VEX_FLAGS);
        int j;
        if (flag) {
            j = b0 | 1;
        } else {
            j = b0 & ~1;
        }

        this.datawatcher.set(VEX_FLAGS, (byte) (j & 255));
    }
}