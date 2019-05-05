package vexMod.modifiers;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.daily.mods.AbstractDailyMod;
import com.megacrit.cardcrawl.localization.RunModStrings;
import vexMod.VexMod;

public class ShiftingDeckMod
        extends AbstractDailyMod
{
    public static final String ID = VexMod.makeID("shiftingDeck");
    private static final RunModStrings modStrings = CardCrawlGame.languagePack.getRunModString(ID);
    public static final String NAME = modStrings.NAME;
    public static final String DESCRIPTION = modStrings.DESCRIPTION;

    public ShiftingDeckMod() {
        super(ID, NAME, DESCRIPTION, null, true);
    }
}