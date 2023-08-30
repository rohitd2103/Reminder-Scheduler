import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.schedularreminder.R
import com.example.schedularreminder.Reminder

class ReminderAdapter(
    private val reminders: MutableList<Reminder>,
    private val onDelete: (position: Int) -> Unit
) : RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reminder, parent, false)
        return ReminderViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val reminder = reminders[position]
        holder.bind(reminder)
        holder.itemView.setOnClickListener { onDelete(position) }
    }

    override fun getItemCount(): Int = reminders.size

    class ReminderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val reminderTextView: TextView = itemView.findViewById(R.id.reminderTextView)

        fun bind(reminder: Reminder) {
            val reminderText = "${reminder.text}\nDate: ${reminder.date}, Time: ${reminder.time}"
            reminderTextView.text = reminderText
        }
    }
}
