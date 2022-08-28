package cl.armin20.ecos.ui

import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import cl.armin20.ecos.AppECOS
import cl.armin20.ecos.databinding.FragmentSecondBinding
import cl.armin20.ecos.ui.viewmodel.BooksBookViewModel
import cl.armin20.ecos.ui.viewmodel.BooksBookViewModelFactory
import cl.armin20.ecos.utils.fromUrl

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
           binding.tvTitle.text = it.title
            binding.tvAuthor.text = it.author
            binding.tvCountry.text = it.country
            binding.tvPrice.text = it.price.toString()
            binding.ivBookImageDetail.fromUrl(it.imageLink)
            binding.tvDelivery.text = setDeliveryText(it.delivery)
            binding.tvPages.text = it.pages.toString()
            binding.btnBuy.setOnClickListener {view ->
                intentSendEmail(it.title,it.id)
            }
            binding.btnWikipedia.setOnClickListener {view ->
                intentSearchWikipedia(it.link)
            }
        }
    }

    private fun setDeliveryText(delivery: Boolean): String {
        return if (delivery){
            "delivery available"
        } else "delivery no available"
    }

    private fun intentSearchWikipedia(link: String) {
        val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
            putExtra(SearchManager.QUERY, link)
        }
        startActivity(intent)
    }

    private fun intentSendEmail(title: String, id:Int) {
        val destination = arrayOf("ventas@ecosbooks.cl")
        val subject = "Consulta por libro $title id $id"
        val body = "Hola\n" +
                "Vi el libro $title de código $id y me gustaría que me contactaran a este correo o al siguiente número _________\n" +
                "Quedo atento.”"
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, destination)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}