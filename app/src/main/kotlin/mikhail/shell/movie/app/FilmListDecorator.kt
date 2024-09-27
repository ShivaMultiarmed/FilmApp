package mikhail.shell.movie.app

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


class FilmListDecorator(
    private val spanCount: Int = 1,
    topSpacing: Int = 0,
    leftSpacing: Int = 0,
    rightSpacing: Int = 0,
    bottomSpacing: Int = 0,
    private val coeff: Double = 1.0)
                                        : ItemDecoration() {

    private val topSpacing = (coeff * topSpacing).toInt()
    private val leftSpacing = (coeff * leftSpacing).toInt()
    private val rightSpacing = (coeff * rightSpacing).toInt()
    private val bottomSpacing = (coeff * bottomSpacing).toInt()

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount

        outRect.left =
            leftSpacing - column * leftSpacing / spanCount
        outRect.right =
            (column + 1) * rightSpacing / spanCount

        if (position < spanCount) {
            outRect.top = topSpacing
        }
        outRect.bottom = bottomSpacing
    }
}