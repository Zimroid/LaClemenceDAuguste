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

package auguste.server;

import auguste.server.command.server.GameConfirm;
import java.util.ArrayList;
import org.json.JSONException;

/**
 * Classe représentant une instance de salle de jeu.
 * @author Lzard
 */
public class Room
{
    private final int gameId; // ID de la partie
    
    private Client owner; // Propriétaire de la salle
    
    // Configuration de la game
    private String gameName;
    private int    playerNumber = 6;
    private int    boardSize    = 8;
    
    // Liste des clients affectés à la salle
    private final ArrayList<Client> clients = new ArrayList<>();
    
    /**
     * Création d'une salle avec le nom donné.
     * @param gameId   ID de la salle
     * @param gameName Nom de la salle
     */
    public Room(int gameId, String gameName)
    {
        this.gameId   = gameId;
        this.gameName = gameName;
    }
    
    /**
     * Envoi d'un message à tous les clients de la salle.
     * @param message Message à envoyer
     */
    public void broadcast(String message)
    {
        for (Client client : this.clients)
        {
            client.send(message);
        }
    }
    
    /**
     * Envoi d'une commande game_confirm à tous les clients.
     * @throws org.json.JSONException Erreur JSON
     */
    public void confirm() throws JSONException
    {
        this.broadcast((new GameConfirm(this)).toString());
    }
    
    /**
     * Indique si le client donné est propriétaire de la salle.
     * @param client Client à vérifier
     * @return Client donné est le propriétaire
     */
    public boolean isOwner(Client client)
    {
        return client.getId() == this.owner.getId();
    }
    
    /**
     * Ajoute un client à la salle.
     * @param client
     */
    public void addClient(Client client)
    {
        this.clients.add(client);
    }
    
    /**
     * Retire un client de la salle.
     * @param client
     */
    public void removeClient(Client client)
    {
        this.clients.remove(client);
    }
    
    /**
     * Retourne l'ID de la partie.
     * @return ID de la partie
     */
    public int getGameId()
    {
        return this.gameId;
    }
    
    /**
     * Retourne le nom de la partie.
     * @return Nom de la partie
     */
    public String getGameName()
    {
        return this.gameName;
    }
    
    /**
     * Retourne le nombre de joueurs maximum.
     * @return Nombre de joueurs maximum
     */
    public int getPlayerNumber()
    {
        return this.playerNumber;
    }
    
    /**
     * Retourne la taille du plateau.
     * @return Taille du plateau
     */
    public int getBoardSize()
    {
        return this.boardSize;
    }
    
    /**
     * Définie le propriétaire de la salle.
     * @param client Nouveau propriétaire
     */
    public void setOwner(Client client)
    {
        this.owner = client;
    }
    
    /**
     * Modifie le nom de la partie.
     * @param gameName Nouveau nom
     */
    public void setGameName(String gameName)
    {
        this.gameName = gameName;
    }
    
    /**
     * Modifie le nombre de joueurs maximum.
     * @param playerNumber Nouveau nombre
     */
    public void setPlayerNumber(int playerNumber)
    {
        this.playerNumber = playerNumber;
    }
    
    /**
     * Modifie la taille du plateau de jeu.
     * @param boardSize Nouvelle taille
     */
    public void setBoardSize(int boardSize)
    {
        this.boardSize = boardSize;
    }
    
}
