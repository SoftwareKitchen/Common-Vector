package tech.softwarekitchen.common.vector

import java.math.BigDecimal
import java.math.BigInteger
import kotlin.math.roundToInt

class Vector2i(val x: Int, val y: Int){
    operator fun plus(other: Vector2i): Vector2i{
        return Vector2i(x+other.x, y+other.y)
    }

    operator fun minus(other: Vector2i): Vector2i{
        return Vector2i(x - other.x, y - other.y)
    }
    fun invert(): Vector2i{
        return Vector2i(-x, -y)
    }
    operator fun times(fac: Double): Vector2i{
        return Vector2i((x * fac).roundToInt(), (y * fac).roundToInt())
    }

    operator fun times(fac: Int): Vector2i{
        return Vector2i(x * fac, y * fac)
    }

    operator fun times(fac: BigDecimal): Vector2i{
        val asDouble = fac.toDouble()
        return this * asDouble
    }

    operator fun times(fac: BigInteger): Vector2i{
        val asInt = fac.toInt()
        return this * asInt
    }

    fun scale(fac: Double): Vector2i{
        return this * fac
    }

    override fun equals(other: Any?): Boolean {
        other ?: return false
        if(other !is Vector2i){
            return false
        }

        return x == other.x && y == other.y
    }
}
