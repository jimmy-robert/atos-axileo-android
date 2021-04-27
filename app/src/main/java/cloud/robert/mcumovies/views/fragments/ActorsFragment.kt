package cloud.robert.mcumovies.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cloud.robert.mcumovies.R
import cloud.robert.mcumovies.business.models.entities.Actor
import cloud.robert.mcumovies.viewmodels.MainViewModel
import cloud.robert.mcumovies.views.viewholders.ActorViewHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActorsFragment : Fragment() {

    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_actors, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = Adapter(::navigateToActor)

        val actorsList = view.findViewById<RecyclerView>(R.id.actorsList)
        actorsList.layoutManager = LinearLayoutManager(view.context)
        actorsList.addItemDecoration(
            DividerItemDecoration(
                actorsList.context,
                DividerItemDecoration.VERTICAL
            )
        )
        actorsList.adapter = adapter

        mainViewModel.actors.observe(viewLifecycleOwner) {
            adapter.actors = it
            adapter.notifyDataSetChanged()
        }
    }

    private fun navigateToActor(actor: Actor) {
        findNavController().navigate(R.id.actorFragment, bundleOf("actorId" to actor.id))
    }

    private class Adapter(
        private val onItemClicked: (Actor) -> Unit
    ) : RecyclerView.Adapter<ActorViewHolder>() {
        var actors = emptyList<Actor>()

        override fun getItemCount() = actors.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ActorViewHolder(parent).apply {
                itemView.setOnClickListener { onItemClicked(actors[adapterPosition]) }
            }

        override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
            holder.bindActor(actors[position])
        }
    }
}