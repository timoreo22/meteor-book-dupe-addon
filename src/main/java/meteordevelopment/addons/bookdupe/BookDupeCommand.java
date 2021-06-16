package meteordevelopment.addons.bookdupe;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import meteordevelopment.meteorclient.systems.commands.Command;
import meteordevelopment.meteorclient.utils.player.FindItemResult;
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
    private static final ItemStack DUPE_BOOK;

    static {
        NbtList pages = new NbtList();
        pages.addElement(0, NbtString.of("ࠀ".repeat(21845)));
        for (int i = 1; i < 40; i++) pages.addElement(i, NbtString.of("a".repeat(256)));

        DUPE_BOOK = new ItemStack(Items.WRITABLE_BOOK, 1);

        DUPE_BOOK.putSubTag("title", NbtString.of("Dupe Book"));
        DUPE_BOOK.putSubTag("author", NbtString.of("Meteor Client"));
        DUPE_BOOK.putSubTag("pages", pages);
    }

    public BookDupeCommand() {
        super("dupe", "Dupes using a held, writable book.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            FindItemResult book = InvUtils.findInHotbar(Items.WRITABLE_BOOK);
            if (book.getHand() == null) {
                error("No book found, you must be holding a writable book!");
            } else {
                int i = book.isMainHand() ? mc.player.getInventory().selectedSlot : 40;
                mc.player.networkHandler.sendPacket(new BookUpdateC2SPacket(DUPE_BOOK, true, i));
            }

            return SINGLE_SUCCESS;
        });
    }
}
