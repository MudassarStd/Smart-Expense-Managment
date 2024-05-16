import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.backend.Transaction

class TransactionRVAdapter(private val transactionList : List<Transaction>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private lateinit var i_listener: onItemClickListener

    interface onItemClickListener {

        fun onItemClick(itemPosition: Int)
        fun onItemLongClick(itemPosition: Int)

    }
    // this method will trigger our interface
    fun setOnItemClickListener(listener: onItemClickListener) {
        i_listener = listener
    }

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.transaction_date_header_layout, parent, false)
            HeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.transaction_sample_layout, parent, false)
            TransactionViewHolder(view,i_listener)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == VIEW_TYPE_ITEM) {
            val transaction = getTransactionForPosition(position)
            (holder as TransactionViewHolder).bind(transaction)
        } else {
            val header = getHeaderForPosition(position)
            (holder as HeaderViewHolder).bind(header)
        }
    }

    override fun getItemCount(): Int {
        return transactionList.size + getUniqueDates().size
    }

    override fun getItemViewType(position: Int): Int {
        return if (isHeaderPosition(position)) VIEW_TYPE_HEADER else VIEW_TYPE_ITEM
    }

    private fun isHeaderPosition(position: Int): Boolean {
        return if (position == 0) {
            true
        } else {
            val previousIndex = position - 1
            val previousDate = getTransactionForPosition(previousIndex).date
            val currentDate = getTransactionForPosition(position).date
            currentDate != previousDate
        }
    }

    private fun getHeaderForPosition(position: Int): String {
        return getTransactionForPosition(position).date
    }

    private fun getUniqueDates(): List<String> {
        return transactionList.map { it.date }.distinct()
    }

    private fun getTransactionForPosition(position: Int): Transaction {
        val index = position - getHeaderCountBeforePosition(position)
        return transactionList[index]
    }

    private fun getHeaderCountBeforePosition(position: Int): Int {
        var count = 0
        for (i in 0 until position) {
            if (isHeaderPosition(i)) {
                count++
            }
        }
        return count
    }
}

class TransactionViewHolder(view: View, listener: TransactionRVAdapter.onItemClickListener) : RecyclerView.ViewHolder(view) {

    private val rvCategory = view.findViewById<TextView>(R.id.rvCategory)
    private val time = view.findViewById<TextView>(R.id.rvTime)
    private val description = view.findViewById<TextView>(R.id.rvDescription)
    private val amount = view.findViewById<TextView>(R.id.rvAmount)
    private val icon = view.findViewById<ImageView>(R.id.rvIcon)

    fun bind(transaction: Transaction) {
        rvCategory.text = transaction.category
        description.text = transaction.description
        time.text = transaction.time
        amount.text = transaction.amount

        if (transaction.transactionType == "expense") {
            amount.setTextColor(ContextCompat.getColor(itemView.context, R.color.primaryRed))
        } else {
            amount.setTextColor(ContextCompat.getColor(itemView.context, R.color.green))
        }


    }

    init {
        itemView.setOnClickListener {
            listener.onItemClick(adapterPosition)
        }


        itemView.setOnLongClickListener {
            listener.onItemLongClick(adapterPosition)
            true
        }
    }
}

class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val headerTextView = view.findViewById<TextView>(R.id.tvDateHeader)

    fun bind(header: String) {
        headerTextView.text = header
    }
}
