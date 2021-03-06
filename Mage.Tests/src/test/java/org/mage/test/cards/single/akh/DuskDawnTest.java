package org.mage.test.cards.single.akh;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Quercitron
 */
public class DuskDawnTest extends CardTestPlayerBase {

    @Test
    public void testCastDusk() {
        //Cast dusk from hand
        addCard(Zone.BATTLEFIELD, playerB, "Watchwolf");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerA, "Dusk // Dawn");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dusk");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTappedCount("Plains", true, 4); // check that we paid the right side's mana
        assertPermanentCount(playerB, "Watchwolf", 0);
        assertGraveyardCount(playerB, "Watchwolf", 1);
        assertGraveyardCount(playerA, "Dusk // Dawn", 1);
    }

    @Test
    public void testCastDuskFromGraveyardFail() {
        //Fail to cast dusk from graveyard
        addCard(Zone.BATTLEFIELD, playerB, "Watchwolf");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.GRAVEYARD, playerA, "Dusk // Dawn");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dusk");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Watchwolf", 1);
        assertGraveyardCount(playerB, "Watchwolf", 0);
        assertGraveyardCount(playerA, "Dusk // Dawn", 1);
    }

    @Test
    public void testCastDawnFromGraveyard() {
        addCard(Zone.GRAVEYARD, playerA, "Dusk // Dawn");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.GRAVEYARD, playerA, "Devoted Hero");
        addCard(Zone.GRAVEYARD, playerA, "Watchwolf");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dawn");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // Dusk dawn should have been cast and exiled
        // devoted hero should be in the hand
        // watchwolf should still be in the yard
        assertHandCount(playerA, "Devoted Hero", 1);
        assertGraveyardCount(playerA, "Devoted Hero", 0);
        assertGraveyardCount(playerA, "Watchwolf", 1);
        assertExileCount(playerA, "Dusk // Dawn", 1);
        assertGraveyardCount(playerA, "Dusk // Dawn", 0);
    }

    @Test
    public void testCastDawnFail() {
        // Fail to cast dawn from hand
        addCard(Zone.HAND, playerA, "Dusk // Dawn");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.GRAVEYARD, playerA, "Devoted Hero");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dawn");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // Dusk dawn shouldn't have been cast and devoted hero should still be in the yard
        assertHandCount(playerA, "Dusk // Dawn", 1);
        assertGraveyardCount(playerA, "Devoted Hero", 1);
    }


}
