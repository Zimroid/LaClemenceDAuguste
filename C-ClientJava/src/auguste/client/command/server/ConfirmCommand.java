/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.client.command.server;

import org.json.JSONException;

/**
 * Classe qui gère les retour positifs du serveurs. Elle sert à confirmer au client
 * que la dernière action s'est bien déroulée.
 * @author Evinrude
 */
public class ConfirmCommand extends CommandServer
{

    /**
     *  Constructeur de la classe
     */
    public ConfirmCommand()
    {
        super();
    }

    /**
     * Récupère le type de confirmation du message et averti le client.
     * @throws JSONException
     */
    @Override
    public void execute() throws JSONException
    {
        String confirm_msg = this.getJSON().getString("type");
        this.getClient().getCSL().confirmCommand(confirm_msg);
    }
}