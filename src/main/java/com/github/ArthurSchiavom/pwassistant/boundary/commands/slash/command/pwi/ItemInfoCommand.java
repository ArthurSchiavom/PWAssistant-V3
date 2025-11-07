package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.command.pwi;

import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommand;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandCategory;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandInfo;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandNames;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandPath;
import com.github.ArthurSchiavom.pwassistant.boundary.commands.slash.SlashCommandSubgroups;
import com.github.ArthurSchiavom.pwassistant.control.pwi.PwiItemDto;
import com.github.ArthurSchiavom.pwassistant.control.pwi.PwiItemService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionContextType;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

@ApplicationScoped
public class ItemInfoCommand implements SlashCommand {
    @Inject
    PwiItemService pwiItemService;

    private static final String SUBCOMMAND_NAME = "info";
    private static final String DESCRIPTION = "Get information about an item by name";
    private static final String OPTION_NAME_ITEM_NAME = "item-name";
    private static final String OPTION_DESCRIPTION_ITEM_NAME = "Name of the item you want information about. Example: Mirage celestone";

    @Override
    public SlashCommandInfo getSlashCommandInfo() {
        return new SlashCommandInfo(new SlashCommandPath(SlashCommandNames.PWI, SlashCommandSubgroups.ITEM, SUBCOMMAND_NAME),
                DESCRIPTION,
                List.of(InteractionContextType.GUILD),
                List.of(new OptionData(OptionType.STRING, OPTION_NAME_ITEM_NAME, OPTION_DESCRIPTION_ITEM_NAME, true, false)),
                SlashCommandCategory.PWI);
    }

    @Override
    public void execute(final SlashCommandInteractionEvent event) {
        if (event.getOptions().isEmpty()) {
            event.reply("Please specify the name of the item when using the command.").queue();
            return;
        }

        final String itemName = event.getOptions().get(0).getAsString();
		List<PwiItemDto> pwiItemDtos = pwiItemService.getMatchingItems(itemName);
		if (pwiItemDtos.size() > 10) {
			event.reply("More than 10 results! Try again but be more specific.").queue();
		}
		else if(pwiItemDtos.isEmpty()) {
            event.reply("No items called **" + itemName + "** were found.").queue();
		}
		else {
			StringBuilder resultSB = new StringBuilder();
			for (final PwiItemDto item : pwiItemDtos) {
				resultSB.append("\n• [").append(item.getName()).append("](")
						.append(item.getInfoLink()).append(")");
			}
			event.reply(resultSB.toString()).queue();
		}
    }
}
