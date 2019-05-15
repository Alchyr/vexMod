package vexMod.events;


import com.megacrit.cardcrawl.cards.curses.Doubt;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import vexMod.VexMod;
import vexMod.relics.FloorLord;

import java.util.ArrayList;

import static vexMod.VexMod.makeEventPath;

public class SerpentsRevenge extends AbstractImageEvent {


    public static final String ID = VexMod.makeID("SerpentsRevenge");
    public static final String IMG = makeEventPath("SerpentsRevenge.png");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private int screenNum = 0;

    public SerpentsRevenge() {
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


                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), new FloorLord());
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Doubt(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Doubt(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Doubt(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                        break;
                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        ArrayList<String> list = new ArrayList<>();
                        list.add("vexMod:EvilSsserpent");
                        AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter((list.get(0)));
                        AbstractDungeon.getCurrRoom().rewards.clear();
                        AbstractDungeon.getCurrRoom().addGoldToRewards(1234);
                        this.enterCombatFromImage();
                        break;
                    case 2:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
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
