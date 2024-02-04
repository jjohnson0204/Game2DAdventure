package entity;

import main.GamePanel;
import object.*;
import object.abilities.hunter.OBJ_CommonArrows;
import object.abilities.mage.*;
import object.consumables.OBJ_ManaCrystal;
import object.consumables.OBJ_Potion_Red;
import object.util.OBJ_Door;
import object.util.OBJ_Door_Iron;
import object.util.OBJ_Equipped_Menu;
import object.weapons.OBJ_Axe;
import object.weapons.OBJ_Pickaxe;
import object.weapons.assassin.*;
import object.weapons.fighter.*;
import object.weapons.hunter.*;
import object.weapons.mage.*;
import object.weapons.warrior.OBJ_Legendary_Sword;
import object.weapons.warrior.OBJ_Sword_Normal;
import object.weapons.warrior.OBJ_Shield_Blue;
import object.weapons.warrior.OBJ_Shield_Wood;

public class EntityGenerator {
    GamePanel gp;
    public EntityGenerator(GamePanel gp) {
        this.gp = gp;

    }
    public Entity getObject(String itemName) {
        Entity obj = null;

        switch (itemName) {
            case OBJ_Aura.objName: obj = new OBJ_Aura(gp); break;
            case OBJ_AuraBall.objName: obj = new OBJ_AuraBall(gp); break;
            case OBJ_AuraNado.objName: obj = new OBJ_AuraNado(gp); break;
            case OBJ_Axe.objName: obj = new OBJ_Axe(gp); break;
            case OBJ_BlueHeart.objName: obj = new OBJ_BlueHeart(gp);
            case OBJ_Boots.objName: obj = new OBJ_Boots(gp); break;
            case OBJ_Bow_Air.objName: obj = new OBJ_Bow_Air(gp, "air"); break;
            case OBJ_Bow_Electric.objName: obj = new OBJ_Bow_Electric(gp, "electric"); break;
            case OBJ_Bow_Fire.objName: obj = new OBJ_Bow_Fire(gp, "fire"); break;
            case OBJ_Bow_Water.objName: obj = new OBJ_Bow_Water(gp, "water"); break;
            case OBJ_Chest.objName: obj = new OBJ_Chest(gp); break;
            case OBJ_Coin_Bronze.objName: obj = new OBJ_Coin_Bronze(gp); break;
            case OBJ_CommonArrows.objName: obj = new OBJ_CommonArrows(gp); break;
            case OBJ_Dagger_Air.objName: obj = new OBJ_Dagger_Air(gp, "air"); break;
            case OBJ_Dagger_Electric.objName: obj = new OBJ_Dagger_Electric(gp, "electric"); break;
            case OBJ_Dagger_Fire.objName: obj = new OBJ_Dagger_Fire(gp, "fire"); break;
            case OBJ_Dagger_Water.objName: obj = new OBJ_Dagger_Water(gp, "water"); break;
            case OBJ_Glove_Air.objName: obj = new OBJ_Glove_Air(gp, "air"); break;
            case OBJ_Glove_Electric.objName: obj = new OBJ_Glove_Electric(gp, "electric"); break;
            case OBJ_Glove_Fire.objName: obj = new OBJ_Glove_Fire(gp, "fire"); break;
            case OBJ_Glove_Water.objName: obj = new OBJ_Glove_Water(gp, "water"); break;
            case OBJ_Legendary_Bow.objName: obj = new OBJ_Legendary_Bow(gp, ""); break;
            case OBJ_Legendary_Dagger.objName: obj = new OBJ_Legendary_Dagger(gp, ""); break;
            case OBJ_Legendary_Glove.objName: obj = new OBJ_Legendary_Glove(gp, ""); break;
            case OBJ_Legendary_Staff.objName: obj = new OBJ_Legendary_Staff(gp, ""); break;
            case OBJ_Legendary_Sword.objName: obj = new OBJ_Legendary_Sword(gp); break;
            case OBJ_Equipped_Menu.objName: obj = new OBJ_Equipped_Menu(gp); break;
            case OBJ_Fireball.objName: obj = new OBJ_Fireball(gp); break;
            case OBJ_FireBlast.objName: obj = new OBJ_FireBlast(gp); break;
            case OBJ_FireAOE.objName: obj = new OBJ_FireAOE(gp); break;
            case OBJ_Door.objName: obj = new OBJ_Door(gp); break;
            case OBJ_Door_Iron.objName: obj = new OBJ_Door_Iron(gp); break;
            case OBJ_Heart.objName: obj = new OBJ_Heart(gp); break;
            case OBJ_Key.objName: obj = new OBJ_Key(gp); break;
            case OBJ_Lantern.objName: obj = new OBJ_Lantern(gp); break;
            case OBJ_LegendaryChest.objName: obj = new OBJ_LegendaryChest(gp); break;
            case OBJ_ManaCrystal.objName: obj = new OBJ_ManaCrystal(gp); break;
            case OBJ_Pickaxe.objName: obj = new OBJ_Pickaxe(gp); break;
            case OBJ_Potion_Red.objName: obj = new OBJ_Potion_Red(gp); break;
            case OBJ_Rock.objName: obj = new OBJ_Rock(gp); break;
            case OBJ_Shield_Blue.objName: obj = new OBJ_Shield_Blue(gp); break;
            case OBJ_Shield_Wood.objName: obj = new OBJ_Shield_Wood(gp); break;
            case OBJ_Staff_Air.objName: obj = new OBJ_Staff_Air(gp, "air"); break;
            case OBJ_Staff_Electric.objName: obj = new OBJ_Staff_Electric(gp, "electric"); break;
            case OBJ_Staff_Fire.objName: obj = new OBJ_Staff_Fire(gp, "fire"); break;
            case OBJ_Staff_Water.objName: obj = new OBJ_Staff_Water(gp, "water"); break;
            case OBJ_Sword_Normal.objName: obj = new OBJ_Sword_Normal(gp); break;
            case OBJ_Tent.objName: obj = new OBJ_Tent(gp); break;
            case OBJ_ThunderBall.objName: obj = new OBJ_ThunderBall(gp); break;
            case OBJ_ThunderBolt.objName: obj = new OBJ_ThunderBolt(gp); break;
            case OBJ_ThunderShield.objName: obj = new OBJ_ThunderShield(gp, gp.players[gp.selectedPlayerIndex]); break;
            case OBJ_ThunderSlash.objName: obj = new OBJ_ThunderSlash(gp); break;
            default:
                throw new IllegalArgumentException("Unrecognized item name: " + itemName);
        }
        return obj;
    }
}
