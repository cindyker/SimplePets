package simplepets.brainsynder.commands.list.Console;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import simple.brainsynder.nbt.*;
import simplepets.brainsynder.PetCore;
import simplepets.brainsynder.api.entity.IEntityPet;
import simplepets.brainsynder.commands.PetCommand;
import simplepets.brainsynder.commands.annotations.CommandDescription;
import simplepets.brainsynder.commands.annotations.CommandName;
import simplepets.brainsynder.commands.annotations.CommandUsage;
import simplepets.brainsynder.commands.annotations.Console;
import simplepets.brainsynder.player.PetOwner;
import simplepets.brainsynder.storage.files.Commands;

import java.util.Arrays;

@Console
@CommandName(name = "info")
@CommandUsage(usage = "<player>")
@CommandDescription(description = "Collects Info the Selected Players' Pet.")
public class Console_Info extends PetCommand {
    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sendUsage(sender);
        } else {
            Player target = Bukkit.getPlayerExact(args[0]);
            if (target == null) {
                sender.sendMessage(PetCore.get().getMessages().getString("Player-Not-Found", true).replace("%player%", args[0]));
                return;
            }
            PetOwner owner = PetOwner.getPetOwner(target);
            Commands commands = PetCore.get().getCommands();
            if (owner == null) return;
            if (!owner.hasPet()) {
                sender.sendMessage(PetCore.get().getMessages().getString("Player-No-Pet", true).replace("%player%", args[0]));
                return;
            }

            sender.sendMessage(commands.getString("Info.Pet-Data-Header")
                    .replace("%player%", args[0]));
            IEntityPet entity = owner.getPet().getVisableEntity();
            StorageTagCompound compound = entity.asCompound();
            compound.getKeySet().forEach(key -> {
                if (!commands.getStringList("Info.Excluded-Data-Values").contains(key)) {
                    sender.sendMessage(commands.getString("Info.Pet-Data-Values")
                            .replace("%key%", WordUtils.capitalize(key.toLowerCase()))
                            .replace("%value%", WordUtils.capitalize(fetchValue(compound.getTag(key)).toLowerCase())));
                }
            });
        }
    }

    private String fetchValue(StorageBase base) {
        if (base instanceof StorageTagByte) {
            byte tagByte = ((StorageTagByte) base).getByte();
            if ((tagByte == 0) || (tagByte == 1))
                return String.valueOf(tagByte == 1);
            return String.valueOf(tagByte);
        }
        if (base instanceof StorageTagByteArray)
            return Arrays.toString(((StorageTagByteArray) base).getByteArray());
        if (base instanceof StorageTagDouble)
            return String.valueOf(((StorageTagDouble) base).getDouble());
        if (base instanceof StorageTagFloat)
            return String.valueOf(((StorageTagFloat) base).getFloat());
        if (base instanceof StorageTagInt)
            return String.valueOf(((StorageTagInt) base).getInt());
        if (base instanceof StorageTagIntArray)
            return Arrays.toString(((StorageTagIntArray) base).getIntArray());
        if (base instanceof StorageTagLong)
            return String.valueOf(((StorageTagLong) base).getLong());
        if (base instanceof StorageTagShort)
            return String.valueOf(((StorageTagShort) base).getShort());
        if (base instanceof StorageTagString)
            return String.valueOf(((StorageTagString) base).getString());
        if (base instanceof StorageTagList) {
            StorageTagList list = (StorageTagList) base;
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < list.tagCount(); i++) {
                builder.append(fetchValue(list.get(i)));
            }
            return builder.toString();
        }

        return "";
    }
}
