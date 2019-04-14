package vexMod.events;


import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.WhiteNoise;
import com.megacrit.cardcrawl.cards.curses.Doubt;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import vexMod.VexMod;
import vexMod.cards.EyeBeam;
import vexMod.cards.GorgonsGaze;
import vexMod.cards.GorgonsGlare;
import vexMod.cards.SnakeSkin;
import vexMod.relics.*;

import java.util.ArrayList;

import static vexMod.VexMod.makeEventPath;

public class GorgonEncounterEvent extends AbstractImageEvent {


    public static final String ID = VexMod.makeID("GorgonEncounterEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("GorgonEncounterEvent.png");

    private AbstractCard gorgonCard;

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;

    private boolean pickCard = false;

    public GorgonEncounterEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);
        this.noCardsInRewards = true;

        // The first dialogue options available to us.
        imageEventText.setDialogOption(OPTIONS[0]); // Flirt: Gain 9 max HP. Become cursed - Gorgon's Gaze.
        int goldAmount = this.getGoldAmount();// 53
        if (goldAmount != 0) {// 54
            this.imageEventText.setDialogOption(OPTIONS[1] + goldAmount + OPTIONS[6]);// 55
        } else {
            this.imageEventText.setDialogOption(OPTIONS[5], true);// 57
        }
        imageEventText.setDialogOption(OPTIONS[2]); // Fight the Gorgon.
        imageEventText.setDialogOption(OPTIONS[3]); // Leave
    }


    @Override
    protected void buttonEffect(int i) { // This is the event:
        switch (screenNum) {
            case 0: // While you are on screen number 0 (The starting screen)
                switch (i) {
                    case 0:

                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        AbstractDungeon.player.increaseMaxHp(9, true);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new GorgonsGaze(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));

                        break; // Onto screen 1 we go.
                    case 1: // If you press button the second button (Button at index 1), in this case: Ease


                        AbstractDungeon.player.loseGold(112);

                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;

                        int c = AbstractDungeon.cardRandomRng.random(2);
                        if (c == 0) {
                            gorgonCard = new GorgonsGlare().makeCopy();
                        } else if (c == 1) {
                            gorgonCard = new EyeBeam().makeCopy();
                        } else {
                            gorgonCard = new SnakeSkin().makeCopy();
                        }

                        // Get a random "bonus" relic
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(gorgonCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));

                        break; // Onto screen 1 we go.
                    case 2: // If you press button the third button (Button at index 2), in this case: Acceptance
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        ArrayList<String> list = new ArrayList();
                        list.add("vexMod:GildedGorgon");
                        AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter((String) (list.get(0)));
                        AbstractDungeon.getCurrRoom().rewards.clear();
                        AbstractDungeon.getCurrRoom().addRelicToRewards(new GorgonsHead());
                        this.enterCombatFromImage();
                        break; // Onto screen 1 we go.
                    case 3:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
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

    private int getGoldAmount() {
        if (AbstractDungeon.player.gold < 100) {// 92
            return 0;// 93
        } else {
            return AbstractDungeon.miscRng.random(100, AbstractDungeon.player.gold);// 95 96 98
        }
    }

}
