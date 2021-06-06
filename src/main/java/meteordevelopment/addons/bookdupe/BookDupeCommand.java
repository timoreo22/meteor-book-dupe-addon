package meteordevelopment.addons.bookdupe;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import minegame159.meteorclient.systems.commands.Command;
import minegame159.meteorclient.utils.player.InvUtils;
import net.minecraft.command.CommandSource;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.packet.c2s.play.BookUpdateC2SPacket;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

//Credit to the original author (https://github.com/Gaider10/BookDupe) (i think) for some of this code.
public class BookDupeCommand extends Command {

    private final ItemStack DUPE_BOOK = new ItemStack(Items.WRITABLE_BOOK, 1);

    public BookDupeCommand() {
        super("dupe", "Dupes using a held, writable book.");

        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < 21845; i++){
            stringBuilder.append((char) 2048);
        }

        String str1 = stringBuilder.toString();

        ListTag listTag = new ListTag();
        listTag.addTag(0, StringTag.of(str1));

        for(int i = 1; i < 40; i++){
            listTag.addTag(i, StringTag.of("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
        }

        DUPE_BOOK.putSubTag("title", StringTag.of("Dupe Book"));
        DUPE_BOOK.putSubTag("pages", listTag);
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            if (!InvUtils.findInHotbar(Items.WRITABLE_BOOK).isMainHand()) error("No book found, you must be holding a writable book!");
            else mc.player.networkHandler.sendPacket(new BookUpdateC2SPacket(DUPE_BOOK, true, mc.player.inventory.selectedSlot));

            return SINGLE_SUCCESS;
        });
    }

}
