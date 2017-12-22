#!/usr/bin/env python3

'''
                    ___           ___           ___     
        ___        /\__\         /\  \         /\  \    
       /\  \      /::|  |       /::\  \       /::\  \   
       \:\  \    /:|:|  |      /:/\:\  \     /:/\ \  \  
       /::\__\  /:/|:|  |__   /::\~\:\  \   _\:\~\ \  \ 
    __/:/\/__/ /:/ |:| /\__\ /:/\:\ \:\__\ /\ \:\ \ \__\
   /\/:/  /    \/__|:|/:/  / \:\~\:\ \/__/ \:\ \:\ \/__/
   \::/__/         |:/:/  /   \:\ \:\__\    \:\ \:\__\  
    \:\__\         |::/  /     \:\ \/__/     \:\/:/  /  
     \/__/         /:/  /       \:\__\        \::/  /   
                   \/__/         \/__/         \/__/    


    File:          RotaryAngleResource.py
    

    Purpose:       Derived class from the
                   sensor class that
                   implements the concrete
                   behaviour of a GrovePi
                   rotary angle sensor.

                   Class is based on
                   the abstract sensor
                   class.
                   
    
    Remarks:       - The GrovePi module has
                     to be installed to 
                     interact with the GrovePi
                     hardware.

                   - This class holds the value
                     of a rotary angle sensor 
                     ([0,1023]) and publishes 
                     it to a MQTT topic if 
                     it changes its state.


    Author:        P. Leibundgut <leiu@zhaw.ch>
    
    
    Date:          10/2016

'''

import log
import mqttconfig

import grovepi

from Sensor import Sensor

# logging setup
logger = log.setup_custom_logger( "mqtt_thing_rotary_angle_resource" )

class RotaryAngleResource( Sensor ):
  
  sub_topic = ""
  
  def __init__( self, connector, lock, \
                mqtt_client, running, \
                pub_topic, \
                polling_interval, \
                sampling_resolution, \
                sub_topic):
    
    super( RotaryAngleResource, self ).__init__( connector, lock, \
                                                 mqtt_client, running, \
                                                 pub_topic, \
                                                 polling_interval, \
                                                 sampling_resolution )
    
    self.value = int( 0 )
    self.sub_topic = sub_topic
    
    self.mqtt_client.subscribe( self.sub_topic, mqttconfig.QUALITY_OF_SERVICE )
    self.mqtt_client.message_callback_add( self.sub_topic, self.on_mqtt_message )

  
  def read_sensor( self ):
    new_value = int( 0 )

    try:
      self.lock.acquire()
      new_value = int( grovepi.analogRead( self.connector ) )
      new_value = new_value // 128 + 1
    except IOError:
      logger.debug( "Error in reading sensor at pin " + str( self.connector ) )
    finally:
      self.lock.release()

    if not self.is_equal( self.value, new_value ):
      self.value = new_value
      self.lock.acquire()
      self.mqtt_client.publish( self.pub_topic, str( self.value ), \
                                mqttconfig.QUALITY_OF_SERVICE, False )
      self.lock.release()
      logger.debug( "---rotary angle sensor value just published its new value: " \
                    + str( self.value ) )

  def on_mqtt_message( self, client, userdata, message ):
    payload = str( "" )
    logger.debug( "Got message on topic: " + str( message.topic ) )
    payload = self.decode_payload_ascii_str( message.payload )

    if self.input_valid( payload ):
      self.lock.acquire()
      self.mqtt_client.publish( self.pub_topic, str( self.value ), \
                                mqttconfig.QUALITY_OF_SERVICE, False )
      self.lock.release()
      logger.debug( "---rotary angle sensor value just published its new value: " \
                    + str( self.value ) )

  def input_valid( self, input ):
    return input in ["Reinitialize"]
      
  def is_equal( self, a, b ):
    return a == b


  def decode_payload_ascii_str( self, payload ):
    ascii_str = str( "" )

    if( isinstance( payload, bytes ) ):
      try:
        ascii_str = str( payload.decode( 'ascii' ) )
      except ValueError:
        logger.debug( "received payload does not contain anything convertible to bool" )
      except Exception:
        logger.debug( "Exception occurred while converting payload to bool" )
    else:
      logger.debug( "To decode message payload you have to use an array of bytes as input." )
      
    return ascii_str
