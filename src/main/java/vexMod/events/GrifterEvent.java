package vexMod.events;


import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.blue.WhiteNoise;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.TheSilent;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.exordium.GoldenWing;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.stats.RunData;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import vexMod.VexMod;
import vexMod.relics.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static vexMod.VexMod.makeEventPath;

public class GrifterEvent extends AbstractImageEvent {


    public static final String ID = VexMod.makeID("GrifterEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("GrifterEvent.png");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;

    private int goldAmount;
    private AbstractRelic crelic;

    public GrifterEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);
        this.noCardsInRewards = true;
        ArrayList<AbstractRelic> relics = new ArrayList();
        relics.addAll(AbstractDungeon.player.relics);
        Collections.shuffle(relics, new Random(AbstractDungeon.miscRng.randomLong()));
        this.crelic = (AbstractRelic) relics.get(0);
        // The first dialogue options available to us.
        imageEventText.setDialogOption(OPTIONS[0]); // Big Spender: Lose ALL your gold for a Common relic.
        imageEventText.setDialogOption(OPTIONS[1] + crelic.name + OPTIONS[2]); // Purchase: Lose 1 Gold for a silly relic.
        imageEventText.setDialogOption(OPTIONS[3]);
        imageEventText.setDialogOption(OPTIONS[4]); // Fight the Grifter.
    }

    @Override
    protected void buttonEffect(int i) { // This is the event:
        switch (screenNum) {
            case 0: //
                switch (i) {
                    case 0:
                        AbstractDungeon.player.loseGold(AbstractDungeon.player.gold);
                        AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.COMMON);
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), r);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[5]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                    case 1: //
                        AbstractDungeon.effectList.add(new RainingGoldEffect(50));
                        AbstractDungeon.player.loseRelic(crelic.relicId);
                        AbstractDungeon.player.gainGold(50);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[5]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                    case 2:
                        AbstractDungeon.player.loseGold(1);
                        ArrayList<AbstractRelic> themRelics = new ArrayList();
                        themRelics.add(RelicLibrary.getRelic(VoiceBox.ID));
                        themRelics.add(RelicLibrary.getRelic(TheWave.ID));
                        themRelics.add(RelicLibrary.getRelic(NotEnergy.ID));
                        themRelics.add(RelicLibrary.getRelic(NewsTicker.ID));
                        themRelics.add(RelicLibrary.getRelic(TimeMachine.ID));
                        themRelics.add(RelicLibrary.getRelic(StoryBook.ID));

                        AbstractRelic relicToGive = (AbstractRelic) themRelics.get(AbstractDungeon.miscRng.random(themRelics.size() - 1));
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), relicToGive);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[5]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                    case 3:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        ArrayList<String> list = new ArrayList();
                        list.add("vexMod:Grifter");
                        AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter((String) (list.get(0)));
                        AbstractDungeon.getCurrRoom().rewards.clear();
                        AbstractDungeon.getCurrRoom().addRelicToRewards(new GrifterSatchel());
                        this.enterCombatFromImage();
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
