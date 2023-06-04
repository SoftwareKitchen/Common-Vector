package tech.softwarekitchen.common.vector

class Rectangle2i(val x0: Int, val y0: Int, val width: Int, val height: Int){
    val base: Vector2i
        get(){return Vector2i(x0,y0)}
    val size: Vector2i
        get(){return Vector2i(width, height)}
}
