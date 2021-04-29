package cloud.robert.mcumovies.views.viewholders

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cloud.robert.mcumovies.R
import cloud.robert.mcumovies.business.models.entities.Actor
import cloud.robert.mcumovies.utils.defaults.DefaultViewHolder
import cloud.robert.mcumovies.utils.extensions.load
import cloud.robert.mcumovies.utils.extensions.toTmdbImageUrl

class ActorViewHolder(parent: ViewGroup) : DefaultViewHolder(parent, R.layout.item_actor_list) {

    private val picture: ImageView by viewById(R.id.itemActorPicture)
    private val name: TextView by viewById(R.id.itemActorName)
    private val character: TextView by viewById(R.id.itemActorCharacter)

    fun bindActor(actor: Actor) {
        itemView.contentDescription = "${actor.name}, plays ${actor.character}"

        picture.load(actor.picturePath.toTmdbImageUrl())
        name.text = actor.name
        character.text = actor.character
    }
}