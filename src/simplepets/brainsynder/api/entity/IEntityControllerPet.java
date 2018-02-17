package simplepets.brainsynder.api.entity;

import org.bukkit.entity.Entity;

public interface IEntityControllerPet extends IEntityPet {
    Entity getDisplayEntity();

    void setDisplayEntity(Entity entity);

    void remove();

    void reloadLocation();

    boolean isMoving();

    default void updateName() {}

    IEntityPet getVisibleEntity();
}
