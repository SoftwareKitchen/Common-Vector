package tech.softwarekitchen.common.vector

class Vector2i(val x: Int, val y: Int){
    fun plus(other: Vector2i): Vector2i{
        return Vector2i(x+other.x, y+other.y)
    }
}
