package cl.armin20.ecos.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import cl.armin20.ecos.AppECOS
import cl.armin20.ecos.R
import cl.armin20.ecos.databinding.FragmentSecondBinding
import cl.armin20.ecos.ui.viewmodel.BooksBookViewModel
import cl.armin20.ecos.ui.viewmodel.BooksBookViewModelFactory

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private val booksBookViewModel: BooksBookViewModel by activityViewModels {
        BooksBookViewModelFactory((activity?.application as AppECOS).repository)
    }

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        booksBookViewModel.getBookByIdFromRemoteToLocal()

        booksBookViewModel.currentBook2.observe(viewLifecycleOwner) {
//            booksBookViewModel.getBookByIdFromRemoteToLocal()
        }



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}