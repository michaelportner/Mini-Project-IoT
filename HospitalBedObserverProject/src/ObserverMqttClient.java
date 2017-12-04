 import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
        
public class ObserverMqttClient {

    private String topic        = "HospitalBedObserver/Request";
    private String content      = "";
    private int qos             = 2;
    private String broker       = "tcp://test.mosquitto.org:1883";
    private String clientId     = "HospitalBedObserver";
    private MemoryPersistence persistence = new MemoryPersistence();
    private MqttClient applicationClient;
    private static final ObserverMqttClient myObserverMqttClient = new ObserverMqttClient(); 
   
    private ObserverMqttClient() {
    	try {
            applicationClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: "+broker);
	        applicationClient.connect(connOpts);
	        System.out.println("Connected");
        } catch(MqttException me) {
	        System.out.println("reason "+me.getReasonCode());
	        System.out.println("msg "+me.getMessage());
	        System.out.println("loc "+me.getLocalizedMessage());
	        System.out.println("cause "+me.getCause());
	        System.out.println("excep "+me);
	        me.printStackTrace();
        }
	}
    
     //Singleton   
    public static ObserverMqttClient getInstance() {
      return myObserverMqttClient;
    } 
	
    
    protected void publishInitializeRequest(ObserverRequest Request) {
    	switch (Request) {
    	case REINITIALIZE:
    		content = "Reinitialize";
    		break;
    	case GETSTATES:
    		content = "GetStates";
    		break;
    	default:
    		break;    		
    	}
    	publishMessage();
    }
    
    private void publishMessage() {
	    try {
	        System.out.println("Publishing message: "+content);
	        MqttMessage message = new MqttMessage(content.getBytes());
	        message.setQos(qos);
	        applicationClient.publish(topic, message);
	        System.out.println("Message published");
	    } catch(MqttException me) {
	        System.out.println("reason "+me.getReasonCode());
	        System.out.println("msg "+me.getMessage());
	        System.out.println("loc "+me.getLocalizedMessage());
	        System.out.println("cause "+me.getCause());
	        System.out.println("excep "+me);
	        me.printStackTrace();
	    }
    }
    
    public void disconnectBroker() {
	    try {
	        applicationClient.disconnect();
	        System.out.println("Disconnected");
	    } catch(MqttException me) {
	        System.out.println("reason "+me.getReasonCode());
	        System.out.println("msg "+me.getMessage());
	        System.out.println("loc "+me.getLocalizedMessage());
	        System.out.println("cause "+me.getCause());
	        System.out.println("excep "+me);
	        me.printStackTrace();
	    }
    }
}
