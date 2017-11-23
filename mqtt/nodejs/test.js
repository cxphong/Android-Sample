
// run: node test.js
// https://github.com/mqttjs/MQTT.js
// install:
// - npm init
// - npm install mqtt --save

var mqtt = require('mqtt')
var client  = mqtt.connect('mqtt://test.mosquitto.org')

client.on('connect', function () {
  client.subscribe('abc')
  client.publish('abc', 'Hello mqtt')
})

client.on('message', function (topic, message, packet) {
  // message is Buffer
  console.log("topic: " + topic + ", message = " + message.toString() + ", packet = " + JSON.stringify(packet))
  client.end()
})
