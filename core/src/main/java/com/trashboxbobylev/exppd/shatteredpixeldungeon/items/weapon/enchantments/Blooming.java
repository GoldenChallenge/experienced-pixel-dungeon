/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2018 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.trashboxbobylev.exppd.shatteredpixeldungeon.items.weapon.enchantments;

import com.trashboxbobylev.exppd.shatteredpixeldungeon.Dungeon;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.actors.Char;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.effects.CellEmitter;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.effects.particles.LeafParticle;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.items.weapon.Weapon;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.levels.Level;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.levels.Terrain;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.scenes.GameScene;
import com.trashboxbobylev.exppd.shatteredpixeldungeon.sprites.ItemSprite;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Blooming extends Weapon.Enchantment {
	
	private static ItemSprite.Glowing DARK_GREEN = new ItemSprite.Glowing( 0x008800 );
	
	@Override
	public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
		
		// lvl 0 - 33%
		// lvl 1 - 50%
		// lvl 2 - 60%
		int level = Math.max( 0, weapon.level() );
		
		if (Random.Int( level + 3 ) >= 2) {
			
			if (!plantGrass(defender.pos)){
				ArrayList<Integer> positions = new ArrayList<>();
				for (int i : PathFinder.NEIGHBOURS8){
					positions.add(i);
				}
				Random.shuffle( positions );
				for (int i : positions){
					if (plantGrass(defender.pos + i)){
						break;
					}
				}
			}
		
		}
		
		return damage;
	}
	
	private boolean plantGrass(int cell){
		int c = Dungeon.level.map[cell];
		if ( c == Terrain.EMPTY || c == Terrain.EMPTY_DECO
				|| c == Terrain.EMBERS || c == Terrain.GRASS){
			Level.set(cell, Terrain.HIGH_GRASS);
			GameScene.updateMap(cell);
			CellEmitter.get( cell ).burst( LeafParticle.LEVEL_SPECIFIC, 4 );
			return true;
		}
		return false;
	}
	
	@Override
	public ItemSprite.Glowing glowing() {
		return DARK_GREEN;
	}
}
