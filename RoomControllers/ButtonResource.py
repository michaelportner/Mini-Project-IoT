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


    File:          ButtonResource.py
    

    Purpose:       Derived class from the
                   sensor class that
                   implements the concrete
                   behaviour of a GrovePi
                   button.

                   Class is based on
                   the abstract sensor
                   class.
                   
    
    Remarks:       - The GrovePi module has
                     to be installed to 
                     interact with the GrovePi
                     hardware.

                   - This class holds the value
                     of a button (true/false)
                     and publishes it to a
                     MQTT topic if it changes
                     its state.


    Author:        P. Leibundgut <leiu@zhaw.ch>
    
    
    Date:          10/2016

'''

import log
import mqttconfig

import random

import grovepi

from Sensor import Sensor

# logging setup
logger = log.setup_custom_logger( "mqtt_thing_button_resource" )

class ButtonResource( Sensor ):

  potentiometer_pin = int(0)
  states = ["Red", "Orange", "Green"]
  
  def __init__( self, connector, lock, \
                mqtt_client, running, \
                pub_topic, \
                polling_interval, \
                sampling_resolution, \
                potentiometer_pin):
    
    super( ButtonResource, self ).__init__( connector, lock, \
                                            mqtt_client, running, \
                                            pub_topic, \
                                            polling_interval, \
                                            sampling_resolution )
    potentiometer_pin = potentiometer_pin
    self.value = False

  
  def read_sensor( self ):
    new_value = bool( False )

    try:
      self.lock.acquire()
      new_value = bool( grovepi.digitalRead( self.connector ) )
    except IOError:
      logger.debug( "Error in reading sensor at pin " + str( self.connector ) )
    finally:
      self.lock.release()

    if new_value:
      self.value = new_value
      beds = int( grovepi.analogRead( self.connector ) ) // 128 + 1
      bed = random.randint(1, beds)
      state = random.choice(self.states)
      self.lock.acquire()
      self.mqtt_client.publish( self.pub_topic + str(bed) + "/State", state, \
                                mqttconfig.QUALITY_OF_SERVICE, False )
      self.lock.release()
      logger.debug( "---button value just toggled, bed" + str(bed) + " " + state )


  def is_equal( self, a, b ):
    return a == b

