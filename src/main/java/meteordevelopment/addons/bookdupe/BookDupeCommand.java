package meteordevelopment.addons.bookdupe;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import meteordevelopment.meteorclient.systems.commands.Command;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import net.minecraft.command.CommandSource;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.network.packet.c2s.play.BookUpdateC2SPacket;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

//Credit to the original author (https://github.com/Gaider10/BookDupe) (i think) for some of this code.
public class BookDupeCommand extends Command {

    private final ItemStack DUPE_BOOK = new ItemStack(Items.WRITABLE_BOOK, 1);

    public BookDupeCommand() {
        super("dupe", "Dupes using a held, writable book.");

        NbtList listTag = new NbtList();

        String str1 = String.valueOf((char) 2048).repeat(21845);
        listTag.add(0, NbtString.of(str1));

        String str2 = "a".repeat(256);
        for (int i = 1; i < 40; i++) listTag.add(i, NbtString.of(str2));

        DUPE_BOOK.putSubTag("title", NbtString.of("Dupe Book"));
        DUPE_BOOK.putSubTag("pages", listTag);
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            if (!InvUtils.findInHotbar(Items.WRITABLE_BOOK).isMainHand()) {
                error("No book found, you must be holding a writable book!");
            }
            else {
                mc.player.networkHandler.sendPacket(new BookUpdateC2SPacket(
                    DUPE_BOOK,
                    true,
                    mc.player.getInventory().selectedSlot
                    )
                );
            }

            return SINGLE_SUCCESS;
        });
    }

}
