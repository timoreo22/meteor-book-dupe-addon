package meteordevelopment.addons.bookdupe;

import meteordevelopment.meteorclient.MeteorAddon;
import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.systems.commands.Commands;

public class BookDupeAddon extends MeteorAddon {
	@Override
	public void onInitialize() {
		MeteorClient.LOG.info("Initializing Book Dupe Addon");
		Commands.get().add(new BookDupeCommand());
	}
}