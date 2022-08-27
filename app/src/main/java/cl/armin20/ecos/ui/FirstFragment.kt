package cl.armin20.ecos.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import cl.armin20.ecos.AppECOS
import cl.armin20.ecos.R
import cl.armin20.ecos.databinding.FragmentFirstBinding
import cl.armin20.ecos.ui.adapter.BooksListAdapter
import cl.armin20.ecos.ui.viewmodel.BooksBookViewModel
import cl.armin20.ecos.ui.viewmodel.BooksBookViewModelFactory

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private val booksBookViewModel: BooksBookViewModel by activityViewModels {
        BooksBookViewModelFactory((activity?.application as AppECOS).repository)
    }

    private lateinit var adapter: BooksListAdapter

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = BooksListAdapter()
        setRecyclerView()
        observeData()
    }

    private fun setRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        )
    }

    private fun observeData() {
        booksBookViewModel.booksFromRepository.observe(viewLifecycleOwner){
            Log.d("faith", "observer data booksFromRepository, list size ${it.size}")
            adapter.updateBoardGames(it)
        }

        adapter.selectItem().observe(viewLifecycleOwner) {
            booksBookViewModel.getSelectItem(it)
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, )
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}