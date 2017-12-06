import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Portner
 *
 */
public class MqttMessageHandler {
	private String stateTopic = "HospitalID/+/+/State";
	private String initializeTopic = "HospitalID/+/Reinitialize";
	private static final Pattern ROOM_INDEX_INITIALIZE_PATTERN = Pattern.compile("(?<=HospitalID/Room).*(?=/Reinitialize)");
	private static final Pattern ROOM_INDEX_STATE_PATTERN = Pattern.compile("(?<=HospitalID/Room).*(?=/Bed([1-9]|[1-9][0-9])/State)");
	private static final Pattern BED_INDEX_STATE_PATTERN = Pattern.compile("(?<=HospitalID/Room([1-9]|[1-9][0-9])/Bed).*(?=/State)");

	
	/**
	 * Handles the incoming messages
	 * 
	 * @param MQTT topic from which the message received
	 * @param MQTT message
	 */
	public void messageReceived(String receivedTopic, String message) {
		int roomIndex = 0;
		int bedIndex = 0;
		if (compareTopic(stateTopic, receivedTopic)) {
			roomIndex = getRoomIndexFromTopic(receivedTopic);
			bedIndex = getBedIndexFromTopic(receivedTopic);
			if (roomIndex > 0 && bedIndex > 0) {
				setBedState(roomIndex, bedIndex, message);
			}
		}else if (compareTopic(initializeTopic, receivedTopic)) {
			roomIndex = getRoomIndexFromTopic(receivedTopic);
			if (roomIndex > 0) {
				initializeRoom(roomIndex, message);
			}
		}
		
		
	}
	
	/**
	 * @param MQTT topic
	 * @return Room index
	 */
	private int getRoomIndexFromTopic(String topic) {
		Matcher matcher = ROOM_INDEX_STATE_PATTERN.matcher(topic);
		if (matcher.find()) {
			return Integer.parseInt(matcher.group(1));
		}
		matcher = ROOM_INDEX_INITIALIZE_PATTERN.matcher(topic);
		if (matcher.find()) {
			return Integer.parseInt(matcher.group(1));
		}else {
			return 0;
		}	
	}
	
	/**
	 * @param MQTT topic
	 * @return Bed index
	 */
	private int getBedIndexFromTopic(String topic) {
		Matcher matcher = BED_INDEX_STATE_PATTERN.matcher(topic);
		if (matcher.find()) {
			return Integer.parseInt(matcher.group(1));
		}else {
			return 0;
		}	
	}
	
	/**
	 * @param MQTT topic with wildcards
	 * @param MQTT topic without wildcards
	 * @return Boolean if MQTT-topics match
	 */
	private static boolean compareTopic(String myTopic, String receivedTopic){
		   return receivedTopic.matches(myTopic.replaceAll("\\+", "[^/]+").replaceAll("#", ".+"));
	}
	
	/**
	 * @param roomIndex
	 * @param bedIndex
	 * @param State
	 */
	private void setBedState(int roomIndex, int bedIndex, String stateMessage) {
		State myState = State.UNKNOWN;
		if(stateMessage.equals("Green")) {
			myState = State.GREEN;
		}else if(stateMessage.equals("Orange")) {
			myState = State.ORANGE;
		}else if(stateMessage.equals("Red")) {
			myState = State.RED;
		}else {
			myState = State.UNKNOWN;
		}
		synchronized (this) {
			MainWindow.myHospital.getRoom(roomIndex).getBed(bedIndex).setMyState(myState);
		}
	}
	
	/**
	 * @param roomIndex
	 * @param initializing string i.e. "1-2-3-4" or "2-3-5-6"
	 */
	private void initializeRoom(int roomIndex, String initializeString) {
		
	}

}
