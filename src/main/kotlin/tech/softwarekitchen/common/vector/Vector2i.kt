package tech.softwarekitchen.common.vector

import kotlin.math.roundToInt

class Vector2i(val x: Int, val y: Int){
    operator fun plus(other: Vector2i): Vector2i{
        return Vector2i(x+other.x, y+other.y)
    }
    fun invert(): Vector2i{
        return Vector2i(-x, -y)
    }
    operator fun times(fac: Double): Vector2i{
        return Vector2i((x * fac).roundToInt(), (y * fac).roundToInt())
    }

    override fun equals(other: Any?): Boolean {
        other ?: return false
        if(other !is Vector2i){
            return false
        }

        return x == other.x && y == other.y
    }
}
