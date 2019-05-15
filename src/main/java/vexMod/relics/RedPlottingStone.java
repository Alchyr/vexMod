package vexMod.relics;

import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.Finesse;
import com.megacrit.cardcrawl.cards.colorless.FlashOfSteel;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.vfx.CollectorCurseEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class RedPlottingStone extends CustomRelic implements ClickableRelic, CustomSavable<Integer> {


    public static final String ID = VexMod.makeID("RedPlottingStone");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("RedPlottingStone.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("RedPlottingStone.png"));

    private static boolean gainRelic = false;
    private static int chosenFloor;

    public RedPlottingStone() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.CLINK);
    }

    public static void FuckShitPoo() {
        if (gainRelic) {
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), AbstractDungeon.returnRandomRelic(RelicTier.COMMON));
            gainRelic = false;
        }
    }

    @Override
    public void onEquip() {
        chosenFloor = AbstractDungeon.cardRandomRng.random((AbstractDungeon.floorNum + 1), 50);
    }

    @Override
    public void onRightClick() {
        if (!isObtained) {
            return;
        }
        if (AbstractDungeon.floorNum == chosenFloor && !this.usedUp) {
            this.img = TextureLoader.getTexture(makeRelicPath("RedPlottingStoneUsed.png"));
            this.usedUp();
            flash();
            stopPulse();

            if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                AbstractMonster c = AbstractDungeon.getRandomMonster();
                AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_COLLECTOR_DEBUFF"));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new CollectorCurseEffect(c.hb.cX, c.hb.cY), 2.0F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(c, new DamageInfo(AbstractDungeon.player, 100, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
            }

            CardCrawlGame.sound.play("GOLD_GAIN");
            AbstractDungeon.player.gainGold(200);
            AbstractDungeon.player.increaseMaxHp(10, true);
            AbstractDungeon.player.heal(10, true);
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new FlashOfSteel(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Finesse(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
            gainRelic = true;

        } else if (AbstractDungeon.floorNum != chosenFloor && !this.usedUp) {
            this.img = TextureLoader.getTexture(makeRelicPath("RedPlottingStoneUsed.png"));
            this.usedUp();
            AbstractDungeon.player.isDead = true;
            AbstractDungeon.deathScreen = new DeathScreen(AbstractDungeon.getMonsters());
        }

    }

    @Override
    public Integer onSave() {
        return chosenFloor;

    }

    @Override
    public void onLoad(Integer SavedFloor) {


        chosenFloor = SavedFloor;

    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        if (AbstractDungeon.floorNum == chosenFloor) {
            this.flash();
            this.beginLongPulse();
        } else {
            this.stopPulse();
        }
        if (AbstractDungeon.floorNum > chosenFloor) {
            this.img = TextureLoader.getTexture(makeRelicPath("RedPlottingStoneUsed.png"));
            this.usedUp();
        }
    }

    @Override
    public boolean canSpawn() {
        return (!Settings.isEndless || AbstractDungeon.floorNum <= 48);
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}