package simplepets.brainsynder.pet.types;

import simple.brainsynder.sound.SoundMaker;
import simplepets.brainsynder.PetCore;
import simplepets.brainsynder.api.entity.IEntityPet;
import simplepets.brainsynder.api.entity.passive.IEntityZombieHorsePet;
import simplepets.brainsynder.pet.PetData;
import simplepets.brainsynder.pet.PetDefault;
import simplepets.brainsynder.utils.ItemBuilder;
import simplepets.brainsynder.utils.Utilities;
import simplepets.brainsynder.wrapper.EntityWrapper;

public class ZombieHorseDefault extends PetDefault {
    public ZombieHorseDefault(PetCore plugin) {
        super(plugin, "zombie_horse", SoundMaker.ENTITY_ZOMBIE_HORSE_AMBIENT, EntityWrapper.ZOMBIE_HORSE);
    }

    @Override
    public ItemBuilder getDefaultItem() {
        return Utilities.getSkullMaterial(Utilities.SkullType.PLAYER).toBuilder(1)
                .setTexture("http://textures.minecraft.net/texture/d22950f2d3efddb18de86f8f55ac518dce73f12a6e0f8636d551d8eb480ceec")
                .withName("&f&lZombie Horse Pet");
    }

    @Override
    public Class<? extends IEntityPet> getEntityClass() {
        return IEntityZombieHorsePet.class;
    }

    @Override
    public PetData getPetData() {
        return PetData.HORSE_OTHER;
    }
}
