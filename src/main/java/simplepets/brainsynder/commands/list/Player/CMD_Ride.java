package simplepets.brainsynder.commands.list.Player;

import org.bukkit.entity.Player;
import simplepets.brainsynder.api.pet.IPet;
import simplepets.brainsynder.commands.PetCommand;
import simplepets.brainsynder.commands.annotations.CommandDescription;
import simplepets.brainsynder.commands.annotations.CommandName;
import simplepets.brainsynder.player.PetOwner;

@CommandName(name = "ride")
@CommandDescription(description = "Ride your pet.")
public class CMD_Ride extends PetCommand<Player> {
    @Override
    public void onCommand(Player p, String[] args) {
        PetOwner petOwner = PetOwner.getPetOwner(p);
        if (petOwner == null) return;
        if (petOwner.hasPet()) {
            IPet pet = petOwner.getPet();
            if (pet.getPetType().canMount(p)) pet.toggleRiding(false);
        }
    }
}
