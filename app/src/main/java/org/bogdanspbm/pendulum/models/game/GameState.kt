package org.bogdanspbm.pendulum.models.game

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.ui.geometry.Offset
import org.bogdanspbm.pendulum.R
import org.bogdanspbm.pendulum.effects.ShakeEffect
import org.bogdanspbm.pendulum.models.hook.Hook
import org.bogdanspbm.pendulum.models.pendulum.Pendulum
import org.bogdanspbm.pendulum.utils.getGameRecord
import org.bogdanspbm.pendulum.utils.saveGameRecord
import java.util.Date
import kotlin.math.abs
import kotlin.random.Random

val TAG = "GameState"

data class GameState(
    val time: Long = Date().time,
    val speed: Float = 1.5f,
    var attachedHook: Hook? = null,
    val pendulum: Pendulum = Pendulum(),
    val hooks: MutableList<Hook> = arrayListOf(),
    val shakeEffect: ShakeEffect = ShakeEffect(),
    var fieldWidth: Float = 500f,
    var score: Int = 0,
    var tick: Long = 0L,
    val context: Context? = null
) {

    var recordUpdated = false
    val recordItem: GameRecord = GameRecord(this, getGameRecord(context!!))


    fun isPendulumOutOfField(): Boolean {
        if (abs(pendulum.x) < fieldWidth / 2 - 60 - pendulum.radius || tick < 100) {
            return false
        }

        return true
    }

    fun tickEvent(delta: Int) {
        if (!gameStarted) {
            return
        }

        tick += (delta * speed).toInt()

        if (score > getGameRecord(context!!) && !recordUpdated) {
            if( getGameRecord(context) > 0){
                val destroySound = MediaPlayer.create(context, R.raw.record)
                destroySound.start()
                destroySound.setOnCompletionListener { it -> it.release() }
            }

            recordUpdated = true
        }

        if (!isCollided && isPendulumOutOfField()) {
            isCollided = true
            if (score > getGameRecord(context!!)) {
                saveGameRecord(context, score)
            }
            val destroySound = MediaPlayer.create(context, R.raw.destroy)
            destroySound.start()
            destroySound.setOnCompletionListener { it -> it.release() }
            shakeEffect.enable(tick)
        }

        shakeEffect.tickEvent(tick)

        if (isCollided) {
            return
        }

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
        pendulum.refresh()
        this.score = 0
        this.tick = 0L
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
        var isCollided = false
    }
}