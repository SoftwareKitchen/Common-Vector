package tech.softwarekitchen.common.vector

data class Vector2(val x: Double, val y: Double){
    fun plus(other: Vector2): Vector2 {
        return Vector2(x+other.x, y+other.y)
    }
}
