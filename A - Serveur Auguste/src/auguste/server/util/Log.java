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

package auguste.server.util;

import java.io.PrintStream;

/**
 * Classe utilitaire pour logger les messages du serveur.
 * @author Lzard
 */
public class Log
{
	// Configuration des sorties
	private static final PrintStream OUT   = System.out;
	private static final PrintStream DEBUG = System.out;
	private static final PrintStream ERROR = System.err;
	
	/**
	 * Ecriture d'un message à logger.
	 * @param message Message à logger
	 */
	public static void out(String message)
	{
		Log.OUT.println(message);
	}
	
	/**
	 * Ecriture d'un message de débuggage.
	 * @param message Message à logger
	 */
	public static void debug(String message)
	{
		Log.DEBUG.println(message);
	}
	
	/**
	 * Ecriture d'un message d'erreur.
	 * @param message Message à logger
	 */
	public static void error(String message)
	{
		Log.ERROR.println(message);
	}
}
