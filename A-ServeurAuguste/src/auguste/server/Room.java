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

import java.util.ArrayList;

/**
 * Classe représentant une instance de salle de jeu.
 * @author Lzard
 */
public class Room
{
    // Propriétaire de la salle
    private Client owner;
    
    // Liste des clients affectés à la salle
    private final ArrayList<Client> clients = new ArrayList<>();
    
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
     * Retourne le client propriétaire de la salle.
     * @return Propriétaire de la salle
     */
    public Client getOwner()
    {
        return this.owner;
    }
    
    /**
     * Définie le propriétaire de la salle.
     * @param client Nouveau propriétaire
     */
    public void setOwner(Client client)
    {
        this.owner = client;
    }
    
}
