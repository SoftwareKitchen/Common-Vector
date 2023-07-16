package tech.softwarekitchen.common.vector

class Rectangle2i(val x0: Int, val y0: Int, val width: Int, val height: Int){
    val base: Vector2i
        get(){return Vector2i(x0,y0)}
    val size: Vector2i
        get(){return Vector2i(width, height)}

    fun sub(other: Rectangle2i): List<Rectangle2i>{
        val right0 = x0 + width - 1
        val bottom0 = y0 + height - 1
        val right1 = other.x0 + other.width - 1
        val bottom1 = other.y0 + other.height - 1

        val topShrinkAmt =  other.y0 - y0
        val topShrinked = topShrinkAmt > 0

        val rightShrinkAmt = right0 - right1
        val rightShrinked = rightShrinkAmt > 0

        val bottomShrinkAmt = bottom0 - bottom1
        val bottomShrinked = bottomShrinkAmt > 0

        val leftShrinkAmt = other.x0 - x0
        val leftShrinked = leftShrinkAmt > 0

        val topShrink = when{
            !topShrinked -> null
            rightShrinked && leftShrinked -> Rectangle2i(x0,y0,width,topShrinkAmt)
            rightShrinked -> Rectangle2i(other.x0, y0, other.width + rightShrinkAmt, topShrinkAmt)
            leftShrinked -> Rectangle2i(x0,y0,other.width + leftShrinkAmt, topShrinkAmt)
            else -> Rectangle2i(other.x0,y0,other.width,topShrinkAmt)
        }

        val bottomShrink = when{
            !bottomShrinked -> null
            rightShrinked && leftShrinked -> Rectangle2i(x0,bottom1+1,width,bottomShrinkAmt)
            rightShrinked -> Rectangle2i(other.x0,bottom1+1,other.width+rightShrinkAmt, bottomShrinkAmt)
            leftShrinked -> Rectangle2i(x0, bottom1+1,other.width+leftShrinkAmt, bottomShrinkAmt)
            else -> Rectangle2i(other.x0, bottom1+1,other.width, bottomShrinkAmt)
        }

        val leftShrink = when{
            leftShrinked -> Rectangle2i(x0,other.y0,leftShrinkAmt,other.height)
            else -> null
        }

        val rightShrink = when{
            rightShrinked -> Rectangle2i(right1+1, other.y0, rightShrinkAmt, other.height)
            else -> null
        }

        return listOf(topShrink, bottomShrink, leftShrink, rightShrink).filterNotNull()
    }
}
