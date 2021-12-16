package ch.ffhs.drugstore.presentation.todo.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import javax.inject.Inject;

import ch.ffhs.drugstore.data.entity.Todo;
import ch.ffhs.drugstore.databinding.TodoItemBinding;

public class TodoListAdapter extends ListAdapter<Todo, TodoListAdapter.TodoHolder> {

  private static final DiffUtil.ItemCallback<Todo> DIFF_CALLBACK =
      new DiffUtil.ItemCallback<Todo>() {
        @Override
        public boolean areItemsTheSame(@NonNull Todo oldItem, @NonNull Todo newItem) {
          // Todo: fix this
          return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Todo oldItem, @NonNull Todo newItem) {
          return oldItem.getText().equals(newItem.getText())
              && oldItem.isChecked() == newItem.isChecked();
        }
      };
  private OnTodoClickListener clickListener;

  @Inject
  public TodoListAdapter() {
    super(DIFF_CALLBACK);
  }

  // Create new views (invoked by the layout manager)
  @NonNull
  @Override
  public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    TodoItemBinding binding =
        TodoItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
    return new TodoHolder(binding);
  }

  // Replace the contents of a view (invoked by the layout manager)
  @Override
  public void onBindViewHolder(TodoHolder holder, int position) {
    holder.bind(position);
  }

  // allows clicks events to be caught
  public void setClickListener(OnTodoClickListener itemClickListener) {
    this.clickListener = itemClickListener;
  }

  // parent activity will implement this method to respond to click events
  public interface OnTodoClickListener {
    void onItemClick(Todo todo);

    void onItemLongClick(Todo todo);
  }

  /** Provide a reference to the type of views that you are using (custom ViewHolder). */
  public class TodoHolder extends RecyclerView.ViewHolder
      implements View.OnClickListener, View.OnLongClickListener {
    private final CheckedTextView checkedTextView;

    TodoHolder(TodoItemBinding binding) {
      super(binding.getRoot());
      checkedTextView = binding.todo;
      itemView.setOnClickListener(this);
      itemView.setOnLongClickListener(this);
    }

    void bind(int position) {
      checkedTextView.setText(getItem(position).getText());
      checkedTextView.setChecked(getItem(position).isChecked());
    }

    @Override
    public void onClick(View view) {
      if (clickListener != null) clickListener.onItemClick(getItem(getAdapterPosition()));
    }

    @Override
    public boolean onLongClick(View view) {
      if (clickListener != null) clickListener.onItemLongClick(getItem(getAdapterPosition()));
      return true;
    }
  }
}