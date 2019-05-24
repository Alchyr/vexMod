package vexMod.crossovers;

import basemod.BaseMod;
import com.evacipated.cardcrawl.mod.bard.characters.Bard;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import vexMod.relics.*;

public class BardCrossover {
    public static void Relics() {
        BaseMod.addRelicToCustomPool(new MusicMaker(), Bard.Enums.COLOR);
        BaseMod.addRelicToCustomPool(new BottledTune(), Bard.Enums.COLOR);
        BaseMod.addRelicToCustomPool(new SingingFlower(), Bard.Enums.COLOR);
        BaseMod.addRelicToCustomPool(new AutoTuner(), Bard.Enums.COLOR);
        BaseMod.addRelicToCustomPool(new AudienceDoll(), Bard.Enums.COLOR);
        BaseMod.addRelicToCustomPool(new DeadRequiem(), Bard.Enums.COLOR);
        BaseMod.addRelicToCustomPool(new Warpstrument(), Bard.Enums.COLOR);
        BaseMod.addRelicToCustomPool(new CleansingRadio(), Bard.Enums.COLOR);
        BaseMod.addRelicToCustomPool(new Demotivationator(), Bard.Enums.COLOR);
        BaseMod.addRelicToCustomPool(new PreRecordedMusic(), Bard.Enums.COLOR);
        BaseMod.addRelicToCustomPool(new RestingRock(), Bard.Enums.COLOR);
        BaseMod.addRelicToCustomPool(new MetalNotes(), Bard.Enums.COLOR);
        BaseMod.addRelicToCustomPool(new Dootinator(), Bard.Enums.COLOR);
        UnlockTracker.markRelicAsSeen(MusicMaker.ID);
        UnlockTracker.markRelicAsSeen(BottledTune.ID);
        UnlockTracker.markRelicAsSeen(AutoTuner.ID);
        UnlockTracker.markRelicAsSeen(SingingFlower.ID);
        UnlockTracker.markRelicAsSeen(Warpstrument.ID);
        UnlockTracker.markRelicAsSeen(AudienceDoll.ID);
        UnlockTracker.markRelicAsSeen(DeadRequiem.ID);
        UnlockTracker.markRelicAsSeen(CleansingRadio.ID);
        UnlockTracker.markRelicAsSeen(Demotivationator.ID);
        UnlockTracker.markRelicAsSeen(PreRecordedMusic.ID);
        UnlockTracker.markRelicAsSeen(RestingRock.ID);
        UnlockTracker.markRelicAsSeen(MetalNotes.ID);
        UnlockTracker.markRelicAsSeen(Dootinator.ID);
    }
}