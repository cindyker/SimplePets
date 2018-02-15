package simplepets.brainsynder.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import simplepets.brainsynder.PetCore;
import simplepets.brainsynder.api.entity.IEntityPet;
import simplepets.brainsynder.api.entity.IImpossaPet;
import simplepets.brainsynder.api.event.pet.PetMoveEvent;
import simplepets.brainsynder.links.IPlotSquaredLink;
import simplepets.brainsynder.links.IWorldGuardLink;
import simplepets.brainsynder.player.PetOwner;
import simplepets.brainsynder.reflection.ReflectionUtil;
import simplepets.brainsynder.utils.LinkRetriever;

public class OnPetSpawn extends ReflectionUtil implements Listener {

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        Entity e = event.getEntity();
        if (ReflectionUtil.getEntityHandle(e) instanceof IImpossaPet) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCreatureSpawnUnBlock(CreatureSpawnEvent event) {
        Entity e = event.getEntity();
        if (ReflectionUtil.getEntityHandle(e) instanceof IImpossaPet && event.isCancelled()) {
            if (PetCore.get().getConfiguration().getBoolean("Complete-Mobspawning-Deny-Bypass")) {
                event.setCancelled(false);
                return;
            }

            if (LinkRetriever.getProtectionLink(IWorldGuardLink.class).allowPetSpawn(event.getLocation())) {
                event.setCancelled(false);
                return;
            }

            if (LinkRetriever.getProtectionLink(IPlotSquaredLink.class).allowPetSpawn(event.getLocation())) {
                event.setCancelled(false);
            }
        }
    }


    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onCreatureSpawn(EntitySpawnEvent event) {
        Entity e = event.getEntity();
        if (ReflectionUtil.getEntityHandle(e) instanceof IImpossaPet) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCreatureSpawnUnBlock(EntitySpawnEvent event) {
        Entity e = event.getEntity();
        if (ReflectionUtil.getEntityHandle(e) instanceof IImpossaPet && event.isCancelled()) {
            if (PetCore.get().getConfiguration().getBoolean("Complete-Mobspawning-Deny-Bypass")) {
                event.setCancelled(false);
                return;
            }

            if (LinkRetriever.getProtectionLink(IWorldGuardLink.class).allowPetSpawn(event.getLocation())) {
                event.setCancelled(false);
                return;
            }

            if (LinkRetriever.getProtectionLink(IPlotSquaredLink.class).allowPetSpawn(event.getLocation())) {
                event.setCancelled(false);
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        try {
            Player player = e.getPlayer();
            PetOwner petOwner = PetOwner.getPetOwner(player);
            if (petOwner.hasPet()) {
                if (!LinkRetriever.getProtectionLink(IWorldGuardLink.class).allowPetEntry(player.getLocation())) {
                    removePet(petOwner, player);
                    return;
                }
                if (!LinkRetriever.getProtectionLink(IPlotSquaredLink.class).allowPetEntry(player.getLocation())) {
                    removePet(petOwner, player);
                }
            }
        } catch (Exception ignored) {
        }
    }

    @EventHandler
    public void onMove(PetMoveEvent e) {
        try {
            if (e.getEntity() != null) {
                if (e.getEntity().getPet() != null && e.getEntity().getOwner() != null) {
                    IEntityPet entity = e.getEntity();
                    PetOwner petOwner = PetOwner.getPetOwner(entity.getOwner());
                    IWorldGuardLink worldGuard = LinkRetriever.getProtectionLink(IWorldGuardLink.class);
                    IPlotSquaredLink plot2 = LinkRetriever.getProtectionLink(IPlotSquaredLink.class);
                    if (!worldGuard.allowPetEntry(e.getTargetLocation())) {
                        removePet(petOwner, e.getEntity().getOwner());
                        return;
                    }
                    if (!plot2.allowPetEntry(e.getTargetLocation())) {
                        removePet(petOwner, e.getEntity().getOwner());
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }

    private void removePet(PetOwner petOwner, Player player) {
        petOwner.removePet();
        player.sendMessage(PetCore.get().getMessages().getString("Pet-No-Enter", true));
    }
}
