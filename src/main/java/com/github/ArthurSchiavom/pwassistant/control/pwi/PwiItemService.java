package com.github.ArthurSchiavom.pwassistant.control.pwi;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Slf4j
public class PwiItemService {
    private final List<PwiItemDto> pwiItemDtos = new ArrayList<>();
    @ConfigProperty(name = "pwi.itemsfilename")
    String pwiItemsFileName;

    public void init() throws IOException {
        pwiItemDtos.clear();
        final List<String> fileLines = Resources.readLines(Resources.getResource(pwiItemsFileName), Charsets.UTF_8);
        for (final String fileLine : fileLines) {
            final String[] itemInfo = fileLine.split(";");
            pwiItemDtos.add(new PwiItemDto(itemInfo[0], itemInfo[1]));
        }
    }

    public List<PwiItemDto> getMatchingItems(String itemName) {
        itemName = itemName.toLowerCase();
        List<PwiItemDto> matchingItems = new ArrayList<>();
        for (final PwiItemDto item : pwiItemDtos) {
            if (item.mightReferToThisItem(itemName))
                matchingItems.add(item);
        }
        return matchingItems;
    }
}
