/*
 * Copyright 2014 Conseil7.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package octavio.server.commands.server;

import java.util.Date;
import octavio.engine.entity.Cell;
import octavio.engine.entity.Legion;
import octavio.engine.entity.pawn.Soldier;
import octavio.engine.turnData.Battle;
import octavio.engine.turnData.Move;
import octavio.engine.turnData.Tenaille;
import octavio.server.commands.ServerCommand;
import octavio.server.db.entities.Room;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Commande de transfert des données d'un tour.
 *
 * @author Lzard
 */
public class GameTurn extends ServerCommand
{
    /**
     * Remplit le JSON avec les données du tour.
     *
     * @param room Salon de la partie
     */
    public GameTurn(Room room) throws JSONException
    {
        // Constructeur de la classe mère
        super("game_turn", room);

        JSONObject json = new JSONObject();

        // Ajout des informations
        JSONObject informations = new JSONObject();
        informations.put("board_size", room.getGame().getBoard().getSize());
        JSONArray legions = new JSONArray();
        JSONObject legionData;
        Legion legion;
        for (int i = 0; i < 6; i++)
        {
            legion = room.getGame().getLegion(i);
            if (legion != null)
            {
                legionData = new JSONObject();
                legionData.put("legion_id", legion.getPosition());
                legionData.put("legion_shape", legion.getShape());
                legionData.put("legion_color", legion.getColor());
                if (room.getPlayers().get(legion.getPlayer()) == null)
                {
                    legionData.put("legion_owner", "Bot");
                }
                else
                {
                    legionData.put("legion_owner", room.getPlayers().get(legion.getPlayer()).getName());
                }
                legions.put(legionData);
            }
        }
        informations.put("legions", legions);
        json.put("informations", informations);

        // Ajout du plateau
        JSONArray boardData = new JSONArray();
        JSONObject cellData;
        for (Cell cell : room.getGame().getBoard().getCells().values())
        {
            if (cell.getPawn() != null)
            {
                cellData = new JSONObject();
                cellData.put("u", cell.getP().getX());
                cellData.put("w", cell.getP().getY());
                cellData.put("type", cell.getPawn().getClass().getSimpleName().toLowerCase());

                if (cell.getTent() != null)
                {
                    cellData.put("tent_color", cell.getTent().getColor());
                    cellData.put("tent_shape", cell.getTent().getShape());
                    cellData.put("tent_legion", cell.getTent().getPosition());
                }

                if (cell.getPawn() instanceof Soldier)
                {
                    cellData.put("legion_armor", ((Soldier)cell.getPawn()).isArmored());
                    cellData.put("legion_id", ((Soldier)cell.getPawn()).getLegion().getPosition());
                    cellData.put("legion_color", cell.getPawn().getLegion().getColor());
                    cellData.put("legion_shape", cell.getPawn().getLegion().getShape());
                }
                boardData.put(cellData);
            }
            else if (cell.getTent() != null)
            {
                cellData = new JSONObject();
                cellData.put("u", cell.getP().getX());
                cellData.put("w", cell.getP().getY());
                cellData.put("tent_color", cell.getTent().getColor());
                cellData.put("tent_shape", cell.getTent().getShape());
                cellData.put("tent_legion", cell.getTent().getPosition());
                boardData.put(cellData);
            }
        }
        json.put("board", boardData);

        // Application des actions et ajout du prochain timeout
        json.put("turn_timeout", (new Date((new Date()).getTime() + room.getGame().getTurnDuration())).getTime());

        boolean ends = room.getGame().applyActions();

        // Ajout des déplacements
        JSONArray movesData = new JSONArray();
        JSONObject moveData;
        for (Move move : room.getGame().getMoves())
        {
            moveData = new JSONObject();
            moveData.put("start_u", move.getP1().getX());
            moveData.put("start_w", move.getP1().getY());
            moveData.put("end_u", move.getP2().getX());
            moveData.put("end_w", move.getP2().getY());
            moveData.put("destroyed", move.isDies());
            movesData.put(moveData);
        }
        json.put("moves", movesData);

        // Ajout des tenailles
        JSONArray tenaillesData = new JSONArray();
        JSONObject tenailleData;
        for (Tenaille tenaille : room.getGame().getTenailles())
        {
            tenailleData = new JSONObject();
            tenailleData.put("front1_u", tenaille.getP1().getX());
            tenailleData.put("front1_w", tenaille.getP1().getY());
            tenailleData.put("front2_u", tenaille.getP2().getX());
            tenailleData.put("front2_w", tenaille.getP2().getY());
            tenaillesData.put(tenailleData);
        }
        json.put("tenailles", tenaillesData);

        // Ajout des combats
        JSONArray battlesData = new JSONArray();
        JSONObject battleData;
        for (Battle battle : room.getGame().getBattles())
        {
            battleData = new JSONObject();
            battleData.put("pawn1_u", battle.getP1().getX());
            battleData.put("pawn1_w", battle.getP1().getY());
            battleData.put("pawn2_u", battle.getP2().getX());
            battleData.put("pawn2_w", battle.getP2().getY());
            if (battle.getDies() != null)
            {
                battleData.put("loser_u", battle.getDies().getX());
                battleData.put("loser_w", battle.getDies().getY());
            }
            battlesData.put(battleData);
        }
        json.put("battles", battlesData);

        if (ends)
        {
            if (room.getGame().getTwinner() != null)
            {
                json.put("winner_team", room.getGame().getTwinner().getNum());
            }
            else
            {
                json.put("winner_team", -1);
            }
            if (room.getGame().getWinner() != null)
            {
                json.put("winner_legion", room.getGame().getWinner().getPosition());
            }
            else
            {
                json.put("winner_legion", -1);
            }
            room.getGame().getTimer().cancel();
            room.getGame().getTimer().purge();
        }
        else
        {
            // Début du prochain tour
            room.getGame().nextTurn();
        }
    }

}
