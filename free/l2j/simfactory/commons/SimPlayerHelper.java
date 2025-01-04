package free.l2j.simfactory.commons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.l2j.commons.random.Rnd;

import net.sf.l2j.gameserver.data.xml.PlayerLevelData;
import net.sf.l2j.gameserver.enums.actors.ClassRace;
import net.sf.l2j.gameserver.enums.actors.Sex;
import net.sf.l2j.gameserver.model.actor.container.player.Appearance;
import net.sf.l2j.gameserver.model.item.instance.ItemInstance;

import free.l2j.simfactory.model.actor.ai.attribute.SimPlayerAIClass;
import free.l2j.simfactory.data.xml.SimPlayerAIData;
import free.l2j.simfactory.model.actor.SimPlayer;

public class SimPlayerHelper{
	public static int[][] getCommonFighterBuffs() {
		return new int[][] { { 1204, 2 }, // wind walk	                
            { 1040, 3 }, // shield
			{ 1035, 4 }, // mental shield
			{ 1045, 6 }, // bless the body				
			{ 1068, 3 }, // might
			{ 1062, 2 }, // beserkers
			{ 1086, 2 }, // haste
			{ 1077, 3 }, // focus
			{ 1388, 3 }, // greater Might
			{ 1036, 2 }, // magic barrier
			{ 274, 1 }, // dance of fire
			{ 273, 1 }, // dance of fury
			{ 268, 1 }, // dance of wind
			{ 271, 1 }, // dance of warrior
			{ 267, 1 }, // song of warding
			{ 349, 1 }, // song of renewal
			{ 264, 1 }, // song of earth
			{ 269, 1 }, // song of hunter
			{ 364, 1 }, // song of champion
			{ 1363, 1 }, // chant of victory
			{ 4699, 5 } // blessing of queen
		};
	}

	public static int[][] getCommonMageBuffs() {
		return new int[][] { { 1204, 2 }, // wind walk	
            { 1040, 3 }, // shield
			{ 1035, 4 }, // mental shield
			{ 4351, 6 }, // concentration
			{ 1036, 2 }, // magic barrier
			{ 1045, 6 }, // bless the body
			{ 1303, 2 }, // wild magic
			{ 1085, 3 }, // acumen
			{ 1062, 2 }, // beserkers
			{ 1059, 3 }, // empower
			{ 1389, 3 }, // greater shield
			{ 273, 1 }, // dance of the mystic
			{ 276, 1 }, // dance of concentration
			{ 365, 1 }, // dance of siren
			{ 264, 1 }, // song of earth
			{ 268, 1 }, // song of wind
			{ 267, 1 }, // song of warding
			{ 349, 1 }, // song of renewal
			{ 1413, 1 }, // magnus chant
			{ 4703, 4 } // gift of seraphim
		};
	}

	public static void giveArmorsByClass(SimPlayer player) {
		List<Integer> itemIds = new ArrayList<>();
		switch (player.getClassId()) {
		case ARCHMAGE:
		case SOULTAKER:
		case HIEROPHANT:
		case ARCANA_LORD:
		case CARDINAL:
		case MYSTIC_MUSE:
		case ELEMENTAL_MASTER:
		case EVAS_SAINT:
		case STORM_SCREAMER:
		case SPECTRAL_MASTER:
		case SHILLIEN_SAINT:
		case DOMINATOR:
		case DOOMCRYER:
			itemIds = Arrays.asList(2407, 512, 5767, 5779, 858, 858, 889, 889, 920);
			break;
		case DUELIST:
		case DREADNOUGHT:
		case PHOENIX_KNIGHT:
		case SWORD_MUSE:
		case HELL_KNIGHT:
		case SPECTRAL_DANCER:
		case EVAS_TEMPLAR:
		case SHILLIEN_TEMPLAR:
		case TITAN:
		case MAESTRO:
			itemIds = Arrays.asList(6373, 6374, 6375, 6376, 6378, 858, 858, 889, 889, 920);
			break;
		case SAGGITARIUS:
		case ADVENTURER:
		case WIND_RIDER:
		case MOONLIGHT_SENTINEL:
		case GHOST_HUNTER:
		case GHOST_SENTINEL:
		case FORTUNE_SEEKER:
		case GRAND_KHAVATARI:
			itemIds = Arrays.asList(6379, 6380, 6381, 6382, 858, 858, 889, 889, 920);
			break;
		default:
			if (player.isMageClass())
				itemIds = Arrays.asList(1101, 1104, 44, 51, 39, 845, 845, 878, 878, 909, 676, 54); // devotion set cat eye earring roral ring diamond neck underwear
			else
				itemIds = Arrays.asList(2386, 23, 43, 51, 39, 845, 845, 878, 878, 909, 676, 54); // wooden set cat eye earring roral ring diamond neck underwear
			break;
		}
		//TODO
		for (int id : itemIds) {
			player.getInventory().addItem(id, 1);
			ItemInstance item = player.getInventory().getItemByItemId(id);
			player.getInventory().equipItemAndRecord(item);
			player.getInventory().reloadEquippedItems();
			player.broadcastCharInfo();
		}
	}

	public static void giveWeaponsByClass(SimPlayer player, boolean randomlyEnchant) {
		List<Integer> itemIds = new ArrayList<>();
		switch (player.getClassId()) {
		case FORTUNE_SEEKER:
		case GHOST_HUNTER:
		case WIND_RIDER:
		case ADVENTURER:
			itemIds = Arrays.asList(6590);
			break;
		case SAGGITARIUS:
		case MOONLIGHT_SENTINEL:
		case GHOST_SENTINEL:
			itemIds = Arrays.asList(7577);
			break;
		case PHOENIX_KNIGHT:
		case SWORD_MUSE:
		case HELL_KNIGHT:
		case EVAS_TEMPLAR:
		case SHILLIEN_TEMPLAR:
			itemIds = Arrays.asList(6583, 6377);
			break;
		case MAESTRO:
			itemIds = Arrays.asList(6585, 6377);
			break;
		case TITAN:
			itemIds = Arrays.asList(6607);
			break;
		case DUELIST:
		case SPECTRAL_DANCER:
			itemIds = Arrays.asList(6580);
			break;
		case DREADNOUGHT:
			itemIds = Arrays.asList(6599);
			break;
		case ARCHMAGE:
		case SOULTAKER:
		case HIEROPHANT:
		case ARCANA_LORD:
		case CARDINAL:
		case MYSTIC_MUSE:
		case ELEMENTAL_MASTER:
		case EVAS_SAINT:
		case STORM_SCREAMER:
		case SPECTRAL_MASTER:
		case SHILLIEN_SAINT:
		case DOMINATOR:
		case DOOMCRYER:
			itemIds = Arrays.asList(6608);
			break;
		case GRAND_KHAVATARI:
			itemIds = Arrays.asList(6602);
			break;
		default:
			if (player.isMageClass())
				itemIds = Arrays.asList(7816); // Apprentice Adventurer's Staff
			else
				itemIds = Arrays.asList(7818); // Apprentice Adventurer's Knife
			break;
		}
		// TODO
		for (int id : itemIds) {
			player.getInventory().addItem(id, 1);
			ItemInstance item = player.getInventory().getItemByItemId(id);
			if(randomlyEnchant)
				item.setEnchantLevel(Rnd.get(7, 20),player);
			player.getInventory().equipItemAndRecord(item);
			player.getInventory().reloadEquippedItems();
		}
	}

	public static SimPlayerAIClass getRandomSimPlayerAIClass(int playerLevel) {
		List<SimPlayerAIClass> classids = new ArrayList<>();
		for(SimPlayerAIClass playerClass: SimPlayerAIData.getInstance().getAvailableSimPlayerAIClasses()) {
			if(playerLevel < 20)
				if (playerClass.getClassID().getLevel()==0)
					classids.add(playerClass);
			if(playerLevel >= 20 && playerLevel < 40)
				if (playerClass.getClassID().getLevel()==1)
					classids.add(playerClass);
			if(playerLevel >= 40 && playerLevel < 76)
				if (playerClass.getClassID().getLevel()==2)
					classids.add(playerClass);
			if(playerLevel >= 76)
				if (playerClass.getClassID().getLevel()==3)
					classids.add(playerClass);
		}
			
		return classids.get(Rnd.get(0, classids.size() - 1));
	}


	public static Appearance getRandomAppearance(ClassRace race) {

		Sex randomSex = Rnd.get(1, 2) == 1 ? Sex.MALE : Sex.FEMALE;
		int hairStyle = Rnd.get(0, randomSex == Sex.MALE ? 4 : 6);
		int hairColor = Rnd.get(0, 3);
		int faceId = Rnd.get(0, 2);

		return new Appearance((byte) faceId, (byte) hairColor, (byte) hairStyle, randomSex);
	}

	public static void setLevel(SimPlayer player, int level) {
		if (level >= 1 && level <= 81) {
			long pXp = player.getStatus().getExp();
			long tXp = PlayerLevelData.getInstance().getPlayerLevel(level).requiredExpToLevelUp();

			if (pXp > tXp)
				player.removeExpAndSp(pXp - tXp, 0);
			else if (pXp < tXp)
				player.addExpAndSp(tXp - pXp, 0);
		}
	}
}