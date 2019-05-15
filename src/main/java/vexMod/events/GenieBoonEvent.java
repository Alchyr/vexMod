package vexMod.events;


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
import vexMod.modifiers.NoRelicMode;
import vexMod.relics.*;

import java.util.ArrayList;

import static vexMod.VexMod.makeEventPath;

public class GenieBoonEvent extends AbstractImageEvent {


    public static final String ID = VexMod.makeID("GenieBoonEvent");
    public static final String IMG = makeEventPath("GenieBoonEvent.png");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private int screenNum = 0;


    public GenieBoonEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);
        this.noCardsInRewards = true;


        imageEventText.setDialogOption(OPTIONS[0]);
        imageEventText.setDialogOption(OPTIONS[1]);
        imageEventText.setDialogOption(OPTIONS[2]);
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum) {
            case 0:
                switch (i) {
                    case 0:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;


                        AbstractDungeon.effectList.add(new RainingGoldEffect(100));
                        AbstractDungeon.player.gainGold(100);

                        break;
                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;

                        ArrayList<AbstractRelic> themRelics = new ArrayList<>();
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
                        themRelics.add(RelicLibrary.getRelic(NotEnergy.ID));
                        themRelics.add(RelicLibrary.getRelic(NewsTicker.ID));
                        themRelics.add(RelicLibrary.getRelic(RockLover.ID));
                        themRelics.add(RelicLibrary.getRelic(GildedClover.ID));
                        themRelics.add(RelicLibrary.getRelic(StoryBook.ID));
                        themRelics.add(RelicLibrary.getRelic(Pepega.ID));
                        if (AbstractDungeon.cardRandomRng.random(2) == 0) {
                            themRelics.add(RelicLibrary.getRelic(PuzzleBox.ID));
                        }

                        AbstractRelic relicToGive;
                        if (!Settings.isDailyRun && Settings.isTrial) {
                            if (CardCrawlGame.trial.dailyModIDs().contains(NoRelicMode.ID)) {
                                relicToGive = RelicLibrary.getRelic("nothingatall");
                            } else {
                                relicToGive = themRelics.get(AbstractDungeon.miscRng.random(themRelics.size() - 1));
                            }
                        } else {
                            relicToGive = themRelics.get(AbstractDungeon.miscRng.random(themRelics.size() - 1));
                        }
                        AbstractDungeon.getCurrRoom().rewards.clear();
                        AbstractDungeon.getCurrRoom().addRelicToRewards(relicToGive);
                        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                        AbstractDungeon.commonRelicPool.remove(relicToGive.relicId);
                        AbstractDungeon.uncommonRelicPool.remove(relicToGive.relicId);
                        AbstractDungeon.rareRelicPool.remove(relicToGive.relicId);
                        AbstractDungeon.shopRelicPool.remove(relicToGive.relicId);

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
            case 1:
                if (i == 0) {
                    openMap();
                }
                break;
        }
    }

}
