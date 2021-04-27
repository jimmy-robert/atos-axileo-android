package cloud.robert.mcumovies.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import cloud.robert.mcumovies.R
import cloud.robert.mcumovies.business.models.entities.Actor
import cloud.robert.mcumovies.utils.extensions.load
import cloud.robert.mcumovies.utils.extensions.toTmdbImageUrl
import cloud.robert.mcumovies.viewmodels.ActorViewModel

class ActorFragment : Fragment() {

    private val viewModel by viewModels<ActorViewModel>()

    private lateinit var picture: ImageView
    private lateinit var name: TextView
    private lateinit var character: TextView

    private val args : ActorFragmentArgs by navArgs()

    val actorId: Int get() = args.actorId

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_actor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val header = view.findViewById<View>(R.id.actorHeader)
        picture = header.findViewById(R.id.itemActorPicture)
        name = header.findViewById(R.id.itemActorName)
        character = header.findViewById(R.id.itemActorCharacter)

        viewModel.getActor(actorId).observe(viewLifecycleOwner) {
            bindActor(it.actor)
        }
    }

    private fun bindActor(actor: Actor) {
        picture.load(actor.picturePath.toTmdbImageUrl())
        name.text = actor.name
        character.text = actor.character
    }
}