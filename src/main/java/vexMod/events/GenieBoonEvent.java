package vexMod.events;


import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.BlightHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import vexMod.VexMod;
import vexMod.relics.*;

import java.util.ArrayList;

import static vexMod.VexMod.makeEventPath;

public class GenieBoonEvent extends AbstractImageEvent {


    public static final String ID = VexMod.makeID("GenieBoonEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("GenieBoonEvent.png");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;


    public GenieBoonEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);
        this.noCardsInRewards = true;

        // The first dialogue options available to us.
        imageEventText.setDialogOption(OPTIONS[0]); // Ease - Gain a very weak relic.
        imageEventText.setDialogOption(OPTIONS[1]); // Speed - Gain a relic which Shortens the Spire.
        imageEventText.setDialogOption(OPTIONS[2]); // Impossible Challenge - Gain a game-ending Relic.
    }

    @Override
    protected void buttonEffect(int i) { // This is the event:
        switch (screenNum) {
            case 0: // While you are on screen number 0 (The starting screen)
                switch (i) {
                    case 0: // If you press button the first button (Button at index 0), in this case: Inspiration.
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others
                        screenNum = 1; // Screen set the screen number to 1. Once we exit the switch (i) statement,
                        // we'll still continue the switch (screenNum) statement. It'll find screen 1 and do it's actions
                        // (in our case, that's the final screen, but you can chain as many as you want like that)
                        AbstractDungeon.effectList.add(new RainingGoldEffect(50));
                        AbstractDungeon.player.gainGold(50);

                        break; // Onto screen 1 we go.
                    case 1: // If you press button the second button (Button at index 1), in this case: Ease
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;

                        AbstractDungeon.player.damage(new DamageInfo((AbstractCreature) null, 8));

                        ArrayList<AbstractRelic> themRelics = new ArrayList();
                        themRelics.add(RelicLibrary.getRelic(BerrySword.ID));
                        themRelics.add(RelicLibrary.getRelic(BrokenBowl.ID));
                        themRelics.add(RelicLibrary.getRelic(GoldFlippers.ID));
                        themRelics.add(RelicLibrary.getRelic(LichBottle.ID));
                        themRelics.add(RelicLibrary.getRelic(MidasArmor.ID));
                        themRelics.add(RelicLibrary.getRelic(RegenHeart.ID));
                        themRelics.add(RelicLibrary.getRelic(RandomRelic.ID));
                        themRelics.add(RelicLibrary.getRelic(RedPlottingStone.ID));
                        themRelics.add(RelicLibrary.getRelic(TimeEaterHat.ID));
                        themRelics.add(RelicLibrary.getRelic(VoiceBox.ID));
                        themRelics.add(RelicLibrary.getRelic(GhostlyGear.ID));
                        themRelics.add(RelicLibrary.getRelic(ExplorationPack.ID));
                        themRelics.add(RelicLibrary.getRelic(ShortStop.ID));
                        themRelics.add(RelicLibrary.getRelic(TreasureMap.ID));
                        themRelics.add(RelicLibrary.getRelic(TheWave.ID));
                        themRelics.add(RelicLibrary.getRelic(LastWill.ID));
                        themRelics.add(RelicLibrary.getRelic(PuzzleBox1.ID));

                        AbstractRelic relicToGive = (AbstractRelic) themRelics.get(AbstractDungeon.miscRng.random(themRelics.size() - 1));
                        AbstractDungeon.getCurrRoom().rewards.clear();
                        AbstractDungeon.getCurrRoom().addRelicToRewards(relicToGive);
                        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                        AbstractDungeon.combatRewardScreen.open();
                        break;
                    case 2:
                        AbstractDungeon.getCurrRoom().spawnBlightAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), BlightHelper.getRandomBlight());

                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                }
                break;
            case 1: // Welcome to screenNum = 1;
                switch (i) {
                    case 0: // If you press the first (and this should be the only) button,
                        openMap(); // You'll open the map and end the event.
                        break;
                }
                break;
        }
    }

}
