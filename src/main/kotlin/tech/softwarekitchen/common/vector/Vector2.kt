package tech.softwarekitchen.common.vector

import kotlin.math.hypot

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

    fun ortho(): Vector2{
        return Vector2(-y, x)
    }

    fun invert(): Vector2{
        return Vector2(-x, -y)
    }

    fun norm(): Vector2{
        val l = length()
        return Vector2(x / l, y / l)
    }

    fun dist(other: Vector2): Double{
        return minus(other).length();
    }

    fun toVector2i(): Vector2i{
        return Vector2i(x.toInt(), y.toInt())
    }

    fun invertY(): Vector2{
        return Vector2(x,-y)
    }

    fun scalar(other: Vector2): Double{
        return x * other.x + y * other.y
    }
}
