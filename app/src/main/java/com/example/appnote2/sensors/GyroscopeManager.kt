package com.example.appnote2.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.abs

class GyroscopeManager(
    context: Context,
    private val onRotateLeft: () -> Unit,
    private val onRotateRight: () -> Unit
) : SensorEventListener {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    private var lastTriggerTime = 0L
    private val cooldown = 500 // ms para evitar multi detección

    fun start() {
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_GAME)
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onSensorChanged(event: SensorEvent?) {
        event ?: return

        val zRotation = event.values[2] // giro izquierda/derecha

        val now = System.currentTimeMillis()
        if (now - lastTriggerTime < cooldown) return

        if (zRotation > 2.0f) {  // giró hacia la izquierda
            lastTriggerTime = now
            onRotateLeft()
        } else if (zRotation < -2.0f) { // giró hacia la derecha
            lastTriggerTime = now
            onRotateRight()
        }
    }
}
