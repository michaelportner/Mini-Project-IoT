#!/usr/bin/env python3

'''
Author: Lucas Lavine
Project: IoT - Final Project
'''

import mqttconfig

class HospitalListener():

  def __init__( self, \
                mqtt_client, \
                sub_topic ):

    self.connector = connector
    self.mqtt_client = mqtt_client
    self.sub_topic = sub_topic
    
    self.mqtt_client.subscribe( self.sub_topic, mqttconfig.QUALITY_OF_SERVICE )
    self.mqtt_client.message_callback_add( self.sub_topic, self.on_mqtt_message )


  # Function has to be overridden in derived class.
  def on_mqtt_message( self, client, userdata, message ):
    payload = str( "" )
    logger.debug( "Got message on topic: " + str( message.topic ) )
    payload = self.decode_payload_ascii_str( message.payload )

    if self.input_valid( payload ):
      pass

  # Function has to be overridden in derived class.
  def input_valid( self, input ):
    return True
        
  def is_equal( self, a, b ):
    return a == b

