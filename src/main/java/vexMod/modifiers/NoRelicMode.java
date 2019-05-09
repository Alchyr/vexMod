package vexMod.modifiers;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.daily.mods.AbstractDailyMod;
import com.megacrit.cardcrawl.localization.RunModStrings;
import vexMod.VexMod;

public class NoRelicMode
        extends AbstractDailyMod
{
    public static final String ID = VexMod.makeID("noRelicMode");
    private static final RunModStrings modStrings = CardCrawlGame.languagePack.getRunModString(ID);
    public static final String NAME = modStrings.NAME;
    public static final String DESCRIPTION = modStrings.DESCRIPTION;

    public NoRelicMode() {
        super(ID, NAME, DESCRIPTION, null, true);
    }
}