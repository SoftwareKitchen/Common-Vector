package tech.softwarekitchen.common.vector

import kotlin.math.hypot
import kotlin.math.sqrt

data class Vector2(val x: Double, val y: Double){
    fun plus(other: Vector2): Vector2 {
        return Vector2(x+other.x, y+other.y)
    }

    fun minus(other: Vector2): Vector2{
        return Vector2(x - other.x, y - other.y)
    }

    fun scale(fac: Double): Vector2{
        return Vector2(x * fac, y * fac)
    }

    fun length(): Double{
        return hypot(x,y)
    }

    fun uni(): Vector2{
        return scale(1/length())
    }
}
