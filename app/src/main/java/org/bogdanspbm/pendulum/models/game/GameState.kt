package org.bogdanspbm.pendulum.models.game

import org.bogdanspbm.pendulum.models.hook.Hook
import org.bogdanspbm.pendulum.models.pendulum.Pendulum
import java.util.Date
import kotlin.math.sin
import kotlin.random.Random

data class GameState(
    val time: Long = Date().time,
    var tick: Long = 0L,
    val pendulum: Pendulum = Pendulum(),
    val hooks: MutableList<Hook> = arrayListOf(),
    var attachedHook: Hook? = null
) {

    fun tickEvent(delta: Int) {
        tick += delta


        if (!isPointerDown) {
            pendulum.move(delta)
            attachedHook = null
            return
        }

        if (attachedHook == null) {
            attachedHook = getNearestHook()
            pendulum.angle = attachedHook!!.getAngleToPendulum(pendulum)
        }

        attachedHook!!.rotatePendulum(delta, pendulum)
    }

    fun prepareGame(): GameState {
        this.generateHooks()
        return this
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
        var isPointerDown = false
    }
}