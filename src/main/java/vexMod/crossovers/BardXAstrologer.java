package vexMod.crossovers;

import Astrologer.Enums.CardColorEnum;
import basemod.BaseMod;
import com.evacipated.cardcrawl.mod.bard.characters.Bard;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import vexMod.relics.MidnightOcarina;
import vexMod.relics.StarlightTuner;

public class BardXAstrologer {
    public static void Relics() {
        BaseMod.addRelicToCustomPool(new MidnightOcarina(), CardColorEnum.ASTROLOGER);
        BaseMod.addRelicToCustomPool(new StarlightTuner(), Bard.Enums.COLOR);
        UnlockTracker.markRelicAsSeen(StarlightTuner.ID);
        UnlockTracker.markRelicAsSeen(MidnightOcarina.ID);
    }
}