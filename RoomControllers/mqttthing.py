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


    File:          mqttthing.py
    

    Purpose:       Exemplarily implementation
                   of a "thing" that uses
                   the MQTT protocol to
                   interact with other devices.
                   
                   This is the main file and
                   program entry point.

                   The pins of connected 
                   hardware to the GrovePi
                   board should be declared
                   here as well as the
                   topics (sensors and
                   actuators) this device/
                   "thing" should publish
                   or subscribe to.
                   
    
    Remarks:       - This application
                     uses the paho-mqtt-module.
                     Be sure it is installed
                     on the device you want
                     to run this application.

                   - A running MQTT broker
                     is required to provide
                     a working communitcation
                     of the MQTT protocol.

                   - Run this program as root/
                     sudoer if a permission 
                     issue occurs. The program 
                     wants to get access to 
                     system information like 
                     IP addresses.
    

    Author:        P. Leibundgut <leiu@zhaw.ch>
    
    
    Date:          10/2016

'''

import sys
import signal
import threading

import log
from tools import get_ip_address_by_if_name
import mqttconfig

from Sensor import Sensor
from Actuator import Actuator

from ButtonResource import ButtonResource
from RotaryAngleResource import RotaryAngleResource


# connected hardware
NETWORK_INTERFACE = "eth0"

BUTTON0_PIN        = int( 3 )
POTENTIOMETER0_PIN = int( 0 )

LOCAL_IP = get_ip_address_by_if_name( NETWORK_INTERFACE )

# globals

# logging setup
logger = log.setup_custom_logger( "mqtt_thing_main" )

lock = threading.Lock()
resources = { }
mqtt_client = mqttconfig.setup_mqtt_client( LOCAL_IP )


# signal handler to perform a proper shutdown of the application.
def signal_handler( *args ):
  logger.debug( "\n\n\n\n\n" + \
                "+--------------------------------------------------------------------+\n" + \
                "| Thing was interrupted by key stroke. Thats all, folks! Exiting ... |\n" + \
                "+--------------------------------------------------------------------+" )
  
  # clean up sensors and actuators. 
  # set their output to low
  for key, value in resources.items():
    if isinstance( value, Sensor ):
      lock.acquire()
      value.running = False    
      lock.release()
    
    if isinstance( value, Actuator ):
      value.tear_down()
      mqtt_client.unsubscribe( value.sub_topic )

  mqtt_client.loop_stop()
  mqtt_client.disconnect()


def main():

  signal.signal( signal.SIGINT, signal_handler )

  # add all resources to the application

  resources[ 'button0' ] = ButtonResource( connector = BUTTON0_PIN, \
                                           lock = lock, \
                                           mqtt_client = mqtt_client, \
                                           running = True, \
                                           pub_topic = "HospitalID/Room1/Bed", \
                                           polling_interval = float( 0.15 ), \
                                           sampling_resolution = int( 2 ), \
                                           potentiometer_pin = POTENTIOMETER0_PIN )


  resources[ 'potentiometer0' ] = RotaryAngleResource( connector = POTENTIOMETER0_PIN, \
                                                       lock = lock, \
                                                       mqtt_client = mqtt_client, \
                                                       running = True, \
                                                       pub_topic = "HospitalID/Room1/Beds", \
                                                       polling_interval = float( 0.4 ), \
                                                       sampling_resolution = int( 1024 ),
                                                       sub_topic = "HospitalBedObserver/Request" )


  # start the sensor threads ...
  for key, value in resources.items():
    if isinstance( value, Sensor ):
      value.start()


  '''
  if not called here running threads
  are not affected by Ctrl + C
  because the main thread finishes
  here and its child threads
  become orphans ...
  '''
  #signal.pause() 


if __name__ == "__main__": 
  main()

