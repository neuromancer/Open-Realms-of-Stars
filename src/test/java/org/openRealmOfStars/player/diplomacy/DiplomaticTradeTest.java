package org.openRealmOfStars.player.diplomacy;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.player.tech.TechList;
import org.openRealmOfStars.player.tech.TechType;
import org.openRealmOfStars.starMap.StarMap;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2017 Tuomo Untinen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see http://www.gnu.org/licenses/
 *
 * Diplomatic Trade tests
 *
 */
public class DiplomaticTradeTest {

  private StarMap generateMap(final int maxPlayer) {
    PlayerList players = Mockito.mock(PlayerList.class);
    Mockito.when(players.getCurrentMaxPlayers()).thenReturn(maxPlayer);

    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.getPlayerList()).thenReturn(players);
    return map;
  }
  
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    StarMap map = generateMap(4);
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    assertEquals(0,trade.getFirstIndex());
    assertEquals(1,trade.getSecondIndex());
  }

  @Test(expected=IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFirstOutOfBound() {
    StarMap map = generateMap(4);
    DiplomaticTrade trade = new DiplomaticTrade(map, 5, 1);
    assertEquals(0,trade.getFirstIndex());
  }

  @Test(expected=IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSecondOutOfBound() {
    StarMap map = generateMap(4);
    DiplomaticTrade trade = new DiplomaticTrade(map, 3, -1);
    assertEquals(0,trade.getFirstIndex());
  }
  
  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testTechListGeneration() {
    PlayerList players = Mockito.mock(PlayerList.class);
    Mockito.when(players.getCurrentMaxPlayers()).thenReturn(2);
    PlayerInfo player1 = Mockito.mock(PlayerInfo.class);
    TechList tech1 = new TechList();
    tech1.addTech(new Tech("MilTech1", TechType.Combat, 1));
    tech1.addTech(new Tech("MilTech2", TechType.Combat, 1));
    tech1.addTech(new Tech("MilTech3", TechType.Combat, 2));
    tech1.addTech(new Tech("DefTech1", TechType.Defense, 1));
    tech1.addTech(new Tech("ProTech2", TechType.Propulsion, 1));
    tech1.addTech(new Tech("ImpTech3", TechType.Improvements, 1));
    Mockito.when(player1.getTechList()).thenReturn(tech1);
    PlayerInfo player2 = Mockito.mock(PlayerInfo.class);
    TechList tech2 = new TechList();
    tech2.addTech(new Tech("MilTech1", TechType.Combat, 1));
    tech2.addTech(new Tech("MilTech2", TechType.Combat, 1));
    tech2.addTech(new Tech("EleTech1", TechType.Electrics, 1));
    tech2.addTech(new Tech("DefTech1", TechType.Defense, 1));
    tech2.addTech(new Tech("DefTech2", TechType.Defense, 1));
    tech2.addTech(new Tech("ProTech2", TechType.Propulsion, 1));
    tech2.addTech(new Tech("ImpTech3", TechType.Improvements, 1));
    Mockito.when(player2.getTechList()).thenReturn(tech2);
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(players.getPlayerInfoByIndex(0)).thenReturn(player1);
    Mockito.when(players.getPlayerInfoByIndex(1)).thenReturn(player2);
    Mockito.when(map.getPlayerList()).thenReturn(players);
    Mockito.when(map.getPlayerByIndex(0)).thenReturn(player1);
    Mockito.when(map.getPlayerByIndex(1)).thenReturn(player2);
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    Tech[] techs = trade.getTradeableTechListForFirst();
    assertEquals(2, techs.length);
    assertEquals("DefTech2", techs[0].getName());
    assertEquals("EleTech1", techs[1].getName());
    techs = trade.getTradeableTechListForSecond();
    assertEquals(1, techs.length);
    assertEquals("MilTech3", techs[0].getName());
  }

}
