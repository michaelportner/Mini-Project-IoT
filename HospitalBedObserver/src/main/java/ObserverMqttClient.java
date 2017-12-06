import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
        
public class ObserverMqttClient{

    private String publishTopic = "HospitalBedObserver/Request";
    private String subscribeTopic = "HospitalID/#";
    private String content      = "";
    private int qos             = 2;
    private String broker       = "tcp://test.mosquitto.org:1883";
    private String clientId     = "HospitalBedObserver";
    private MemoryPersistence persistence = new MemoryPersistence();
    private MqttClient myClient;
    private static final ObserverMqttClient myObserverMqttClient = new ObserverMqttClient(); 
    private MqttConnectOptions connOpts = new MqttConnectOptions();
    private MqttMessageHandler myMessageHandler = new MqttMessageHandler();
   
    private ObserverMqttClient(){
    	try {    		
            myClient = new MqttClient(broker, clientId, persistence);
            myClient.setCallback(new MqttCallback(){
            	
                /**
                 * @see MqttCallback#connectionLost(Throwable)
                 */
            	public void connectionLost(Throwable cause) {
            		// Called when the connection to the server has been lost.
            		// An application may choose to implement reconnection
            		// logic at this point. This sample simply exits.
                	try {
                        System.out.println("Connecting lost to broker: "+broker);
                        connOpts.setCleanSession(true);
                        System.out.println("Try reconnecting to broker: "+broker);
            	        myClient.connect(connOpts);
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

                /**
                 * @see MqttCallback#deliveryComplete(IMqttDeliveryToken)
                 */
            	public void deliveryComplete(IMqttDeliveryToken token) {
            		// Called when a message has been delivered to the
            		// server. The token passed in here is the same one
            		// that was passed to or returned from the original call to publish.
            		// This allows applications to perform asynchronous
            		// delivery without blocking until delivery completes.
            		//
            		// This sample demonstrates asynchronous deliver and
            		// uses the token.waitForCompletion() call in the main thread which
            		// blocks until the delivery has completed.
            		// Additionally the deliveryComplete method will be called if
            		// the callback is set on the client
            		//
            		// If the connection to the server breaks before delivery has completed
            		// delivery of a message will complete after the client has re-connected.
            		// The getPendingTokens method will provide tokens for any messages
            		// that are still to be delivered.
            	}

            	/**
            	 * messageArrived
            	 * @param topic name of the topic on the message was published to
            	 * @param message the actual message.
            	 * @throws Exception if a terminal error has occurred, and the client should be
            	 * shut down.
            	 */
            	public void messageArrived(String topic, MqttMessage message) throws Exception{
            		System.out.println("-------------------------------------------------");
            		System.out.println("| Topic:" + topic);
            		System.out.println("| Message: " + new String(message.getPayload()));
            		System.out.println("-------------------------------------------------");
            		myMessageHandler.messageReceived(topic, message.toString());
            	}	
            });
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: "+broker);
	        myClient.connect(connOpts);
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
	
    /**
     * Publishes the reinitialize Request
     */
    protected void publishReinitializeRequest() {

		publishRequest(ObserverRequest.REINITIALIZE);
    	
    }
    
    /**
     * Publishes the GetStates Request
     */
    protected void publishGetStatesRequest() {
    	
		publishRequest(ObserverRequest.REINITIALIZE);
    	
    }
    
    /**
     * @param Request
     */
    protected void publishRequest(ObserverRequest Request) {
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
    
    /**
     * Method which publishes the Message to the Broker
     */
    private void publishMessage() {
	    try {
	        System.out.println("Publishing message: "+content);
	        MqttMessage message = new MqttMessage(content.getBytes());
	        message.setQos(qos);
	        myClient.publish(publishTopic, message);
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
    

    /**
     * Subscribes a topic
     */
    public void subscribe() {
	    try {
	    	myClient.subscribe(subscribeTopic, qos);
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
	        myClient.disconnect();
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
