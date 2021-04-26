package cloud.robert.mcumovies.utils.defaults

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

open class DefaultViewHolder(parent: ViewGroup, @LayoutRes layoutId: Int) :
    RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false)) {
    fun <T: View> viewById(@IdRes id: Int) = lazy { itemView.findViewById<T>(id) }
}