package vexMod.events;


import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import vexMod.VexMod;
import vexMod.relics.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static vexMod.VexMod.makeEventPath;

public class GrifterEvent extends AbstractImageEvent {


    public static final String ID = VexMod.makeID("GrifterEvent");
    public static final String IMG = makeEventPath("GrifterEvent.png");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private int screenNum = 0;

    private AbstractRelic crelic;

    public GrifterEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);
        this.noCardsInRewards = true;
        ArrayList<AbstractRelic> relics = new ArrayList<>(AbstractDungeon.player.relics);
        Collections.shuffle(relics, new Random(AbstractDungeon.miscRng.randomLong()));
        this.crelic = relics.get(0);

        if (AbstractDungeon.player.gold > 1) {
            imageEventText.setDialogOption(OPTIONS[0]);
        }
        imageEventText.setDialogOption(OPTIONS[1] + crelic.name + OPTIONS[2]);
        if (AbstractDungeon.player.gold > 1) {
            imageEventText.setDialogOption(OPTIONS[3]);
        }
        imageEventText.setDialogOption(OPTIONS[4]);
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum) {
            case 0:
                switch (i) {
                    case 0:
                        if (AbstractDungeon.player.gold == 0) {
                            AbstractDungeon.effectList.add(new RainingGoldEffect(50));
                            AbstractDungeon.player.loseRelic(crelic.relicId);
                            AbstractDungeon.player.gainGold(50);
                            this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                            this.imageEventText.updateDialogOption(0, OPTIONS[5]);
                            this.imageEventText.clearRemainingOptions();
                            screenNum = 1;
                            break;
                        } else {
                            AbstractDungeon.player.loseGold(AbstractDungeon.player.gold);
                            AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.COMMON);
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), r);
                            this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                            this.imageEventText.updateDialogOption(0, OPTIONS[5]);
                            this.imageEventText.clearRemainingOptions();
                            screenNum = 1;
                            break;
                        }
                    case 1:
                        if (AbstractDungeon.player.gold == 0) {
                            this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                            ArrayList<String> list = new ArrayList<>();
                            list.add("vexMod:Grifter");
                            AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter((list.get(0)));
                            AbstractDungeon.getCurrRoom().rewards.clear();
                            AbstractDungeon.getCurrRoom().addRelicToRewards(new GrifterSatchel());
                            AbstractDungeon.lastCombatMetricKey = "Grifter";
                            this.enterCombatFromImage();
                            break;
                        } else {
                            AbstractDungeon.effectList.add(new RainingGoldEffect(50));
                            AbstractDungeon.player.loseRelic(crelic.relicId);
                            AbstractDungeon.player.gainGold(50);
                            this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                            this.imageEventText.updateDialogOption(0, OPTIONS[5]);
                            this.imageEventText.clearRemainingOptions();
                            screenNum = 1;
                            break;
                        }

                    case 2:
                        AbstractDungeon.player.loseGold(1);
                        ArrayList<AbstractRelic> themRelics = new ArrayList<>();
                        themRelics.add(RelicLibrary.getRelic(VoiceBox.ID));
                        themRelics.add(RelicLibrary.getRelic(TheWave.ID));
                        themRelics.add(RelicLibrary.getRelic(NotEnergy.ID));
                        themRelics.add(RelicLibrary.getRelic(NewsTicker.ID));
                        themRelics.add(RelicLibrary.getRelic(StoryBook.ID));
                        themRelics.add(RelicLibrary.getRelic(Pepega.ID));
                        themRelics.add(RelicLibrary.getRelic(SpireShuffle.ID));
                        themRelics.add(RelicLibrary.getRelic(HealthChanger.ID));
                        themRelics.add(RelicLibrary.getRelic(Bottle.ID));
                        themRelics.add(RelicLibrary.getRelic(Incredibleness.ID));
                        themRelics.add(RelicLibrary.getRelic(MiniSolarSystem.ID));
                        themRelics.add(RelicLibrary.getRelic(RealismEngine.ID));
                        themRelics.add(RelicLibrary.getRelic(PopTire.ID));
                        themRelics.add(RelicLibrary.getRelic(JugglerBalls.ID));

                        for (AbstractRelic m : AbstractDungeon.player.relics) {
                            themRelics.remove(m);
                        }

                        AbstractRelic relicToGive = themRelics.get(AbstractDungeon.miscRng.random(themRelics.size() - 1));
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), relicToGive);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[5]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                    case 3:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        ArrayList<String> list = new ArrayList<>();
                        list.add("vexMod:Grifter");
                        AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter((list.get(0)));
                        AbstractDungeon.getCurrRoom().rewards.clear();
                        AbstractDungeon.getCurrRoom().addRelicToRewards(new GrifterSatchel());
                        AbstractDungeon.lastCombatMetricKey = "Grifter";
                        this.enterCombatFromImage();
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
