package org.bogdanspbm.pendulum.models.game

import androidx.compose.ui.geometry.Offset
import org.bogdanspbm.pendulum.effects.ShakeEffect
import org.bogdanspbm.pendulum.models.hook.Hook
import org.bogdanspbm.pendulum.models.pendulum.Pendulum
import java.util.Date
import kotlin.math.abs
import kotlin.math.sin
import kotlin.random.Random

data class GameState(
    val time: Long = Date().time,
    val speed: Float = 1.5f,
    var attachedHook: Hook? = null,
    val pendulum: Pendulum = Pendulum(),
    val hooks: MutableList<Hook> = arrayListOf(),
    val shakeEffect: ShakeEffect = ShakeEffect(),
    var fieldWidth: Float = 500f,
    var score: Int = 0,
    var tick: Long = 0L
) {
    fun isPendulumOutOfField(): Boolean {
        if (abs(pendulum.x) < fieldWidth / 2 - 30) {
            return false
        }

        return true
    }

    fun tickEvent(delta: Int) {
        if (!gameStarted) {
            return
        }

        tick += (delta * speed).toInt()

        if(isPendulumOutOfField()){
            shakeEffect.enable(tick)
        }

        shakeEffect.tickEvent(tick)

        if (pendulum.y / 50 > score) {
            score = (pendulum.y / 50).toInt()
        }

        if (!isPointerDown) {
            pendulum.move((delta * speed).toInt())
            attachedHook = null
            return
        }

        if (attachedHook == null) {
            attachedHook = getNearestHook()
            pendulum.angle = attachedHook!!.getAngleToPendulum(pendulum)
            pendulum.rotationDirection = attachedHook!!.getRotateDirection(pendulum)
        }

        attachedHook!!.rotatePendulum((delta * speed).toInt(), pendulum)
    }

    fun prepareGame(): GameState {
        this.generateHooks()
        this.score = 0
        return this
    }

    fun getOffset(): Offset {
        return shakeEffect.offset
    }

    private fun generateHooks() {
        for (i in 0..100) {
            val x = (Random((Math.random() * 1000000).toInt()).nextFloat() - 0.5) * 750;
            val y = i * 750 + (Random((Math.random() * 1000000).toInt()).nextFloat() - 0.5) * 175;
            val hook = Hook(x.toFloat(), y.toFloat())
            hooks.add(hook)
        }
    }

    private fun getNearestHook(): Hook {
        var distance = 999999.toDouble()
        var hook = hooks[0]
        for (i in 0..hooks.size - 1) {
            val tmpHook = hooks[i]
            val tmpDistance = tmpHook.distanceToPendulum(pendulum)
            if (tmpDistance < distance) {
                distance = tmpDistance
                hook = tmpHook
            }
        }

        return hook
    }

    companion object {
        var gameStarted = false
        var isPointerDown = false
    }
}