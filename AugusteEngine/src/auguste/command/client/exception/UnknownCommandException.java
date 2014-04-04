/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.command.client.exception;

/**
 *
 * @author Evinrude
 */
public class UnknownCommandException extends Exception 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnknownCommandException(String not_supported_yet) 
    {
        super(not_supported_yet);
    }
}