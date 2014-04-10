package auguste.client.command.server;

import java.util.List;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import auguste.client.entity.Game;
import auguste.client.entity.User;

public class GameUsers extends CommandServer
{
	public static final String USERS =		"users";
	public static final String ROOM_ID =	"room_id";
	public static final String USER_NAME = 	"user_name";
	public static final String IS_OWNER =	"is_owner";
	public static final String USER_ID =	"user_id";
	
	public GameUsers()
	{
		super();
	}

	@Override
	public void execute() throws JSONException
	{
		Game game = this.getClient().getGame(this.getJSON().getInt(ROOM_ID));
		JSONArray users = this.getJSON().getJSONArray(USERS);
		
		List<User> userList = new ArrayList<>();
		
		for(int i = 0; i < users.length(); i++)
		{
			JSONObject json = users.getJSONObject(i);
			String userName = json.getString(USER_NAME);
			boolean isOwner = json.getBoolean(IS_OWNER);
			int userId = json.getInt(USER_ID);
			
			User user = new User();
			user.setId(userId);
			user.setName(userName);
			
			userList.add(user);
		}
		
		game.setUsers(userList);
	}
}