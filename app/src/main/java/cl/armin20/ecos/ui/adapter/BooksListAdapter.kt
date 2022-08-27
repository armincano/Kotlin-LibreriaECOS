package cl.armin20.ecos.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import cl.armin20.ecos.data.local.entities.BooksListLocal
import cl.armin20.ecos.databinding.BooksListItemBinding
import cl.armin20.ecos.utils.fromUrl
import com.squareup.picasso.Picasso

class BooksListAdapter: RecyclerView.Adapter<BooksListAdapter.BookViewHolder>() {

    private var listBooks = listOf<BooksListLocal>()
    private val selectItem = MutableLiveData<BooksListLocal>()

    fun updateBoardGames(list: List<BooksListLocal>) {
        Log.d("faith", " ADAPTER $list")
        listBooks = list
        notifyDataSetChanged()
    }

    fun selectItem(): LiveData<BooksListLocal> {
        return selectItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(BooksListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(listBooks[position])
    }

    override fun getItemCount(): Int = listBooks.size

    inner class BookViewHolder(
        private val binding: BooksListItemBinding
    ): RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bind(item: BooksListLocal) {
            binding.tvAuthor.text = item.author
            binding.tvCountry.text = item.country
            binding.tvTitle.text = item.title
            binding.ivBookImage.fromUrl(item.imageLink)
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            selectItem.value = listBooks[adapterPosition]
        }
    }
}