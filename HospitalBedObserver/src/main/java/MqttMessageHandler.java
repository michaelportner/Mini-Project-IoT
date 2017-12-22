import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Michael Portner
 *
 */
public class MqttMessageHandler {
	private String stateTopic = "HospitalID/+/+/State";
	private String initializeTopic = "HospitalID/+/Beds";
	private static final Pattern ROOM_INDEX_INITIALIZE_PATTERN = Pattern.compile("(?<=hospitalid/room).*(?=/beds)");
	private static final Pattern ROOM_INDEX_STATE_PATTERN = Pattern.compile("(?<=hospitalid/room).*(?=/bed([1-9]|[1-9][0-9])/state)");
	private static final Pattern BED_INDEX_STATE_PATTERN = Pattern.compile("(?<=hospitalid/room([1-9]|[1-9][0-9])/bed).*(?=/state)");
	private static final String ROOM_INITIALIZE_DELIMETER = "-";

	
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
			roomIndex = getRoomIndexFromTopic(receivedTopic.toLowerCase());
			bedIndex = getBedIndexFromTopic(receivedTopic.toLowerCase());
			if (roomIndex > 0 && bedIndex > 0) {
				setBedState(roomIndex, bedIndex, message.toLowerCase());
			}
		}else if (compareTopic(initializeTopic, receivedTopic)) {
			roomIndex = getRoomIndexFromTopic(receivedTopic.toLowerCase());
			if (roomIndex > 0) {
				initializeRoom(roomIndex, message.toLowerCase());
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
			if (!matcher.group(0).isEmpty()){
			return Integer.parseInt(matcher.group(0));
			}
		}
		matcher = ROOM_INDEX_INITIALIZE_PATTERN.matcher(topic);
		if (matcher.find()) {
			if (!matcher.group(0).isEmpty()){
			return Integer.parseInt(matcher.group(0));
			}
		}
		return 0;
	}
	
	/**
	 * @param MQTT topic
	 * @return Bed index
	 */
	private int getBedIndexFromTopic(String topic) {
		Matcher matcher = BED_INDEX_STATE_PATTERN.matcher(topic);
		if (matcher.find()) {
			if (!matcher.group(0).isEmpty()){
			return Integer.parseInt(matcher.group(0));
			}
		}
		return 0;
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
		if(stateMessage.equalsIgnoreCase("green")) {
			myState = State.GREEN;
		}else if(stateMessage.equalsIgnoreCase("orange")) {
			myState = State.ORANGE;
		}else if(stateMessage.equalsIgnoreCase("red")) {
			myState = State.RED;
		}else {
			myState = State.UNKNOWN;
		}
		synchronized (this) {
			if (MainWindow.myHospital.getRoom(roomIndex) != null && MainWindow.myHospital.getRoom(roomIndex).getBed(bedIndex) != null) {
				MainWindow.myHospital.getRoom(roomIndex).getBed(bedIndex).setMyState(myState);
			}
		}
	}
	
	/**
	 * @param roomIndex
	 * @param initializing string i.e. "1-2-3-4" or "2-3-5-6"
	 */
	private void initializeRoom(int roomIndex, String initializeString) {
		String[] myBedIndices = initializeString.split(ROOM_INITIALIZE_DELIMETER);
		Room myRoom = new Room();
		ArrayList<Integer> indicesList = new ArrayList<Integer>();
		for(String BedIndex : myBedIndices) {
			if (BedIndex != "") {
				indicesList.add(Integer.parseInt(BedIndex));
			}
		}
		Collections.sort(indicesList);
		for(Integer BedIndex : indicesList) {
			if(myRoom.getBed(BedIndex) == null) {
				myRoom.addBed(BedIndex, new Bed());
			}
		}
		if (MainWindow.myHospital.getRoom(roomIndex) == null) {
			MainWindow.myHospital.addRoom(roomIndex, myRoom);
		}else{
			MainWindow.myHospital.removeRoom(roomIndex);
			MainWindow.myHospital.addRoom(roomIndex, myRoom);
		}
}

}
