package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.Defect;
import com.megacrit.cardcrawl.characters.Ironclad;
import com.megacrit.cardcrawl.characters.TheSilent;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.Nemesis;
import com.megacrit.cardcrawl.monsters.city.BookOfStabbing;
import com.megacrit.cardcrawl.monsters.exordium.GremlinNob;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import com.megacrit.cardcrawl.vfx.SpeechTextEffect;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import java.util.ArrayList;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class VoiceBox extends CustomRelic {

    // ID, images, text.
    public static final String ID = VexMod.makeID("VoiceBox");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));


    public VoiceBox() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) {
            AbstractDungeon.actionManager.addToBottom(new TalkAction(true, getBossTaunt(), 0.0F, 2.0F));
        } else if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite) {
            AbstractDungeon.actionManager.addToBottom(new TalkAction(true, getEliteTaunt(), 0.0F, 2.0F));
        } else {
            AbstractDungeon.actionManager.addToBottom(new TalkAction(true, getIntroTaunt(), 0.0F, 2.0F));
        }
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        switch (card.cardID) {
            case "Demon Form":
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, getDemonTaunt(), 0.0F, 2.0F));
                break;
            case "Shrug It Off":
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, getShrugTaunt(), 0.0F, 2.0F));
                break;
            case "Corruption":
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, getCorruptionTaunt(), 0.0F, 2.0F));
                break;
            case "Fiend Fire":
            case "Immolate":
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, getFlameTaunt(), 0.0F, 2.0F));
                break;
            case "Feed":
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, getFeedTaunt(), 0.0F, 2.0F));
                break;
            case "Wraith Form v2":
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, getWraithTaunt(), 0.0F, 2.0F));
                break;
            case "Terror":
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, getTerrorTaunt(), 0.0F, 2.0F));
                break;
            case "Caltrops":
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, getCaltropsTaunt(), 0.0F, 2.0F));
                break;
            case "Grand Finale":
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, getGrandFinaleTaunt(), 0.0F, 2.0F));
                break;
            case "vexMod:MidnightStrike":
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, getMidnightTaunt(), 0.0F, 2.0F));
                break;
            case "Distraction":
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, getDistractionTaunt(), 0.0F, 2.0F));
                break;
            case "Backstab":
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, getBackstabTaunt(), 0.0F, 2.0F));
                break;
            case "Hyperbeam":
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, getHyperbeamTaunt(), 0.0F, 2.0F));
                break;
            case "Echo Form":
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, getEchoTaunt(), 0.0F, 2.0F));
                break;
            case "BootSequence":
            case "Reboot":
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, getBootTaunt(), 0.0F, 2.0F));
                break;
            case "White Noise":
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, getNoiseTaunt(), 0.0F, 2.0F));
                break;

        }
        if (card.type == AbstractCard.CardType.ATTACK && !card.cardID.equals("Fiend Fire") && !card.cardID.equals("Immolate") && !card.cardID.equals("Feed") && !card.cardID.equals("Grand Finale") && !card.cardID.equals("vexMod:MidnightStrike") && !card.cardID.equals("Backstab") && !card.cardID.equals("Hyperbeam")) {
            AbstractDungeon.actionManager.addToBottom(new TalkAction(true, getAttackTaunt(action), 0.0F, 1.5F));
        }
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        if (room instanceof ShopRoom) {
            AbstractDungeon.effectList.add(new SpeechBubble(AbstractDungeon.player.dialogX,AbstractDungeon.player.dialogY,  getShopTaunt(), true));
        } else if (room instanceof TreasureRoom || room instanceof TreasureRoomBoss) {
            AbstractDungeon.effectList.add(new SpeechBubble(AbstractDungeon.player.dialogX,AbstractDungeon.player.dialogY,  getChestTaunt(room), true));
        }

    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    private String getNoiseTaunt() {
        ArrayList<String> taunts = new ArrayList<>();
        taunts.add(DESCRIPTIONS[171]);
        taunts.add(DESCRIPTIONS[172]);
        taunts.add(DESCRIPTIONS[173]);
        return taunts.get(MathUtils.random(taunts.size() - 1));
    }

    private String getBootTaunt() {
        ArrayList<String> taunts = new ArrayList<>();
        taunts.add(DESCRIPTIONS[168]);
        taunts.add(DESCRIPTIONS[169]);
        taunts.add(DESCRIPTIONS[170]);
        return taunts.get(MathUtils.random(taunts.size() - 1));
    }

    private String getEchoTaunt() {
        ArrayList<String> taunts = new ArrayList<>();
        taunts.add(DESCRIPTIONS[164]);
        taunts.add(DESCRIPTIONS[165]);
        taunts.add(DESCRIPTIONS[166]);
        taunts.add(DESCRIPTIONS[167]);
        return taunts.get(MathUtils.random(taunts.size() - 1));
    }

    private String getHyperbeamTaunt() {
        ArrayList<String> taunts = new ArrayList<>();
        taunts.add(DESCRIPTIONS[161]);
        taunts.add(DESCRIPTIONS[162]);
        taunts.add(DESCRIPTIONS[163]);
        return taunts.get(MathUtils.random(taunts.size() - 1));
    }

    private String getBackstabTaunt() {
        ArrayList<String> taunts = new ArrayList<>();
        taunts.add(DESCRIPTIONS[158]);
        taunts.add(DESCRIPTIONS[159]);
        taunts.add(DESCRIPTIONS[160]);
        return taunts.get(MathUtils.random(taunts.size() - 1));
    }

    private String getDistractionTaunt() {
        ArrayList<String> taunts = new ArrayList<>();
        taunts.add(DESCRIPTIONS[147]);
        taunts.add(DESCRIPTIONS[148]);
        taunts.add(DESCRIPTIONS[149]);
        taunts.add(DESCRIPTIONS[150]);
        taunts.add(DESCRIPTIONS[151]);
        taunts.add(DESCRIPTIONS[152]);
        taunts.add(DESCRIPTIONS[153]);
        taunts.add(DESCRIPTIONS[154]);
        taunts.add(DESCRIPTIONS[155]);
        taunts.add(DESCRIPTIONS[156]);
        taunts.add(DESCRIPTIONS[157]);
        return taunts.get(MathUtils.random(taunts.size() - 1));
    }

    private String getMidnightTaunt() {
        ArrayList<String> taunts = new ArrayList<>();
        taunts.add(DESCRIPTIONS[145]);
        taunts.add(DESCRIPTIONS[146]);
        return taunts.get(MathUtils.random(taunts.size() - 1));
    }

    private String getGrandFinaleTaunt() {
        ArrayList<String> taunts = new ArrayList<>();
        taunts.add(DESCRIPTIONS[142]);
        taunts.add(DESCRIPTIONS[143]);
        taunts.add(DESCRIPTIONS[144]);
        return taunts.get(MathUtils.random(taunts.size() - 1));
    }

    private String getCaltropsTaunt() {
        ArrayList<String> taunts = new ArrayList<>();
        taunts.add(DESCRIPTIONS[139]);
        taunts.add(DESCRIPTIONS[140]);
        taunts.add(DESCRIPTIONS[141]);
        return taunts.get(MathUtils.random(taunts.size() - 1));
    }

    private String getTerrorTaunt() {
        ArrayList<String> taunts = new ArrayList<>();
        taunts.add(DESCRIPTIONS[136]);
        taunts.add(DESCRIPTIONS[137]);
        taunts.add(DESCRIPTIONS[138]);
        return taunts.get(MathUtils.random(taunts.size() - 1));
    }


    private String getWraithTaunt() {
        ArrayList<String> taunts = new ArrayList<>();
        taunts.add(DESCRIPTIONS[133]);
        taunts.add(DESCRIPTIONS[134]);
        taunts.add(DESCRIPTIONS[135]);
        return taunts.get(MathUtils.random(taunts.size() - 1));
    }

    private String getFeedTaunt() {
        ArrayList<String> taunts = new ArrayList<>();
        taunts.add(DESCRIPTIONS[130]);
        taunts.add(DESCRIPTIONS[131]);
        taunts.add(DESCRIPTIONS[132]);
        return taunts.get(MathUtils.random(taunts.size() - 1));
    }

    private String getFlameTaunt() {
        ArrayList<String> taunts = new ArrayList<>();
        taunts.add(DESCRIPTIONS[127]);
        taunts.add(DESCRIPTIONS[128]);
        taunts.add(DESCRIPTIONS[129]);
        return taunts.get(MathUtils.random(taunts.size() - 1));
    }

    private String getCorruptionTaunt() {
        ArrayList<String> taunts = new ArrayList<>();
        taunts.add(DESCRIPTIONS[124]);
        taunts.add(DESCRIPTIONS[125]);
        taunts.add(DESCRIPTIONS[126]);
        return taunts.get(MathUtils.random(taunts.size() - 1));
    }

    private String getDemonTaunt() {
        ArrayList<String> taunts = new ArrayList<>();
        taunts.add(DESCRIPTIONS[118]);
        taunts.add(DESCRIPTIONS[119]);
        taunts.add(DESCRIPTIONS[120]);
        return taunts.get(MathUtils.random(taunts.size() - 1));
    }

    private String getShrugTaunt() {
        ArrayList<String> taunts = new ArrayList<>();
        taunts.add(DESCRIPTIONS[121]);
        taunts.add(DESCRIPTIONS[122]);
        taunts.add(DESCRIPTIONS[123]);
        return taunts.get(MathUtils.random(taunts.size() - 1));
    }


    private String getChestTaunt(AbstractRoom room) {
        ArrayList<String> taunts = new ArrayList<>();
        if (AbstractDungeon.player instanceof Ironclad) {
            taunts.add(DESCRIPTIONS[103]);
            taunts.add(DESCRIPTIONS[104]);
            taunts.add(DESCRIPTIONS[105]);
            taunts.add(DESCRIPTIONS[106]);
            taunts.add(DESCRIPTIONS[107]);
        }
        if (AbstractDungeon.player instanceof TheSilent) {
            taunts.add(DESCRIPTIONS[108]);
            taunts.add(DESCRIPTIONS[109]);
            taunts.add(DESCRIPTIONS[110]);
            if (room instanceof TreasureRoom) {
                taunts.add(DESCRIPTIONS[111]);
            }
            taunts.add(DESCRIPTIONS[112]);
        }
        if (AbstractDungeon.player instanceof Defect) {
            taunts.add(DESCRIPTIONS[113]);
            taunts.add(DESCRIPTIONS[114]);
            taunts.add(DESCRIPTIONS[115]);
            taunts.add(DESCRIPTIONS[116]);
            taunts.add(DESCRIPTIONS[117]);
        }
        return taunts.get(MathUtils.random(taunts.size() - 1));
    }

    private String getShopTaunt() {
        ArrayList<String> taunts = new ArrayList<>();
        if (AbstractDungeon.player instanceof Ironclad) {
            taunts.add(DESCRIPTIONS[85]);
            taunts.add(DESCRIPTIONS[86]);
            taunts.add(DESCRIPTIONS[87]);
            taunts.add(DESCRIPTIONS[88]);
            taunts.add(DESCRIPTIONS[89]);
        }
        if (AbstractDungeon.player instanceof TheSilent) {
            taunts.add(DESCRIPTIONS[90]);
            taunts.add(DESCRIPTIONS[91]);
            taunts.add(DESCRIPTIONS[92]);
            taunts.add(DESCRIPTIONS[93]);
            taunts.add(DESCRIPTIONS[94]);
            taunts.add(DESCRIPTIONS[95]);
            taunts.add(DESCRIPTIONS[96]);
            taunts.add(DESCRIPTIONS[97]);
        }
        if (AbstractDungeon.player instanceof Defect) {
            taunts.add(DESCRIPTIONS[98]);
            taunts.add(DESCRIPTIONS[99]);
            taunts.add(DESCRIPTIONS[100]);
            taunts.add(DESCRIPTIONS[101]);
            taunts.add(DESCRIPTIONS[102]);
        }
        return taunts.get(MathUtils.random(taunts.size() - 1));
    }

    private String getAttackTaunt(UseCardAction action) {
        ArrayList<String> taunts = new ArrayList<>();
        if (AbstractDungeon.player instanceof Ironclad) {
            taunts.add(DESCRIPTIONS[55]);
            taunts.add(DESCRIPTIONS[56]);
            taunts.add(DESCRIPTIONS[57]);
            taunts.add(DESCRIPTIONS[58]);
            taunts.add(DESCRIPTIONS[59]);
            taunts.add(DESCRIPTIONS[174]);
            taunts.add(DESCRIPTIONS[175]);
            taunts.add(DESCRIPTIONS[176]);
        }
        if (AbstractDungeon.player instanceof TheSilent) {
            taunts.add(DESCRIPTIONS[60]);
            taunts.add(DESCRIPTIONS[61]);
            taunts.add(DESCRIPTIONS[62]);
            taunts.add(DESCRIPTIONS[63]);
            taunts.add(DESCRIPTIONS[64]);
            taunts.add(DESCRIPTIONS[177]);
            taunts.add(DESCRIPTIONS[178]);
            taunts.add(DESCRIPTIONS[179]);
        }
        if (AbstractDungeon.player instanceof Defect) {
            taunts.add(DESCRIPTIONS[65]);
            taunts.add(DESCRIPTIONS[66]);
            taunts.add(DESCRIPTIONS[67]);
            taunts.add(DESCRIPTIONS[69]);
            if (action.target != null) {
                taunts.add(DESCRIPTIONS[68] + action.target.id.toUpperCase());
            }
            taunts.add(DESCRIPTIONS[180]);
            taunts.add(DESCRIPTIONS[181]);
            taunts.add(DESCRIPTIONS[182]);
        }
        return taunts.get(MathUtils.random(taunts.size() - 1));
    }

    public void onMonsterDeath(AbstractMonster m) {
        AbstractDungeon.effectList.add(new SpeechBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, getDeathTaunt(m), true));
    }

    private String getDeathTaunt(AbstractMonster m) {
        ArrayList<String> taunts = new ArrayList<>();
        if (AbstractDungeon.player instanceof Ironclad) {
            taunts.add(DESCRIPTIONS[70]);
            taunts.add(DESCRIPTIONS[71]);
            taunts.add(DESCRIPTIONS[72]);
            taunts.add(DESCRIPTIONS[73]);
            taunts.add(DESCRIPTIONS[74]);
        }
        if (AbstractDungeon.player instanceof TheSilent) {
            taunts.add(DESCRIPTIONS[75]);
            taunts.add(DESCRIPTIONS[76]);
            taunts.add(DESCRIPTIONS[77]);
            taunts.add(DESCRIPTIONS[78]);
            taunts.add(DESCRIPTIONS[79]);
        }
        if (AbstractDungeon.player instanceof Defect) {
            taunts.add(m.id.toUpperCase() + DESCRIPTIONS[80]);
            taunts.add(DESCRIPTIONS[81]);
            taunts.add(DESCRIPTIONS[82]);
            taunts.add(m.id.toUpperCase() + DESCRIPTIONS[83]);
            taunts.add(DESCRIPTIONS[84]);
        }
        return taunts.get(MathUtils.random(taunts.size() - 1));
    }

    private String getIntroTaunt() {
        ArrayList<String> taunts = new ArrayList<>();
        if (AbstractDungeon.player instanceof Ironclad) {
            taunts.add(DESCRIPTIONS[1]);
            taunts.add(DESCRIPTIONS[2]);
            taunts.add(DESCRIPTIONS[3]);
            taunts.add(DESCRIPTIONS[4]);
            taunts.add(DESCRIPTIONS[5]);
        }
        if (AbstractDungeon.player instanceof TheSilent) {
            taunts.add(DESCRIPTIONS[6]);
            taunts.add(DESCRIPTIONS[7]);
            taunts.add(DESCRIPTIONS[8]);
            taunts.add(DESCRIPTIONS[9]);
            taunts.add(DESCRIPTIONS[10]);
        }
        if (AbstractDungeon.player instanceof Defect) {
            taunts.add(DESCRIPTIONS[11]);
            taunts.add(DESCRIPTIONS[12]);
            taunts.add(DESCRIPTIONS[13]);
            taunts.add(DESCRIPTIONS[14] + AbstractDungeon.cardRandomRng.random(0, 100) + "%");
            taunts.add(DESCRIPTIONS[15]);
        }
        return taunts.get(MathUtils.random(taunts.size() - 1));
    }

    private String getEliteTaunt() {
        ArrayList<String> taunts = new ArrayList<>();
        if (AbstractDungeon.player instanceof Ironclad) {
            if (AbstractDungeon.getCurrRoom().monsters.monsters.contains(new GremlinNob(0, 0))) {
                taunts.add(DESCRIPTIONS[31]);
                taunts.add(DESCRIPTIONS[32]);
                taunts.add(DESCRIPTIONS[33]);
            }
            taunts.add(DESCRIPTIONS[16]);
            taunts.add(DESCRIPTIONS[17]);
            taunts.add(DESCRIPTIONS[18]);
            taunts.add(DESCRIPTIONS[19]);
            taunts.add(DESCRIPTIONS[20]);
        }
        if (AbstractDungeon.player instanceof TheSilent) {
            if (AbstractDungeon.getCurrRoom().monsters.monsters.contains(new Nemesis())) {
                taunts.add(DESCRIPTIONS[34]);
                taunts.add(DESCRIPTIONS[35]);
                taunts.add(DESCRIPTIONS[36]);
            }
            taunts.add(DESCRIPTIONS[21]);
            taunts.add(DESCRIPTIONS[22]);
            taunts.add(DESCRIPTIONS[23]);
            taunts.add(DESCRIPTIONS[24]);
            taunts.add(DESCRIPTIONS[25]);
        }
        if (AbstractDungeon.player instanceof Defect) {
            if (AbstractDungeon.getCurrRoom().monsters.monsters.contains(new BookOfStabbing())) {
                taunts.add(DESCRIPTIONS[37]);
                taunts.add(DESCRIPTIONS[38]);
                taunts.add(DESCRIPTIONS[39]);
            }
            taunts.add(DESCRIPTIONS[26]);
            taunts.add(DESCRIPTIONS[27]);
            taunts.add(DESCRIPTIONS[28]);
            taunts.add(DESCRIPTIONS[29] + AbstractDungeon.cardRandomRng.random(0, 50) + "%");
            taunts.add(DESCRIPTIONS[30]);
        }
        return taunts.get(MathUtils.random(taunts.size() - 1));
    }

    private String getBossTaunt() {
        ArrayList<String> taunts = new ArrayList<>();
        if (AbstractDungeon.player instanceof Ironclad) {
            taunts.add(DESCRIPTIONS[40]);
            taunts.add(DESCRIPTIONS[41]);
            taunts.add(DESCRIPTIONS[42]);
            taunts.add(DESCRIPTIONS[43]);
            taunts.add(DESCRIPTIONS[44]);
        }
        if (AbstractDungeon.player instanceof TheSilent) {
            taunts.add(DESCRIPTIONS[45]);
            taunts.add(DESCRIPTIONS[46]);
            taunts.add(DESCRIPTIONS[47]);
            taunts.add(DESCRIPTIONS[48]);
            taunts.add(DESCRIPTIONS[49]);
        }
        if (AbstractDungeon.player instanceof Defect) {
            taunts.add(DESCRIPTIONS[50]);
            taunts.add(DESCRIPTIONS[51] + AbstractDungeon.cardRandomRng.random(0, 25) + "%");
            taunts.add(DESCRIPTIONS[52]);
            taunts.add(DESCRIPTIONS[53]);
            taunts.add(DESCRIPTIONS[54]);
        }
        return taunts.get(MathUtils.random(taunts.size() - 1));
    }

}
