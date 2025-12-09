package com.example.appnote2.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class GyroscopeManager(
    context: Context,
    private val onRotateLeft: () -> Unit,
    private val onRotateRight: () -> Unit
) : SensorEventListener {

    private val sensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val gyroscope: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    private var lastTriggerTime = 0L
    private val cooldown = 700 // ms para evitar triggers múltiples

    fun start() {
        gyroscope?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        val yRotation = event.values[1] // rotación izquierda/derecha

        val now = System.currentTimeMillis()
        if (now - lastTriggerTime < cooldown) return

        if (yRotation > 2.0f) {
            lastTriggerTime = now
            onRotateRight()    // → siguiente nota
        } else if (yRotation < -2.0f) {
            lastTriggerTime = now
            onRotateLeft()     // → nota anterior
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}