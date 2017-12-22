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


    File:          Actuator.py
    

    Purpose:       Base class for all the
                   actuators that are going
                   to be attached to the
                   device ("thing").
                   
    
    Remarks:       - Some functions are just
                     abstract in this base
                     class and have to be
                     overridden in the derived
                     class.

                   - Actuators subscribe to a
                     MQTT topic and react on
                     the messages payload.

                   - If the main program terminates
                     (exception / key stroke / ...)
                     an actuator has to be brought
                     into a save off state
                     e.g. all outputs to low, ...

                     Therefore the tear_down
                     function MUST be implemented
                     in each concrete actuator
                     implementation.
    

    Author:        P. Leibundgut <leiu@zhaw.ch>
    
    
    Date:          10/2016

'''

import mqttconfig

import grovepi


class Actuator():

  def __init__( self, connector, \
                lock, \
                mqtt_client, \
                sub_topic, \
                nuances_resolution = int( 2 ) ):

    self.connector = connector
    self.lock = lock
    self.mqtt_client = mqtt_client
    self.sub_topic = sub_topic
    self.nuances_resolution = nuances_resolution
    
    self.lock.acquire()
    grovepi.pinMode( connector, "OUTPUT" )
    self.lock.release()

    self.mqtt_client.subscribe( self.sub_topic, mqttconfig.QUALITY_OF_SERVICE )
    self.mqtt_client.message_callback_add( self.sub_topic, self.on_mqtt_message )


  # Function has to be overridden in derived class.
  def on_mqtt_message( self, client, userdata, message ):
    pass  


  # Function has to be overridden in derived class.
  def set_actuator( self, value ):
    pass

  
  # Function has to be overridden in derived class.
  def input_valid( self, input ):
    pass
        
  
  # Function has to be overridden in derived class.
  def is_equal( self, a, b ):
    pass


  # Function has to be overridden in derived class.
  def tear_down( self ):
    pass

