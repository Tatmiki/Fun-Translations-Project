package com.example.fun_translations;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TranslationAdapter extends RecyclerView.Adapter<TranslationAdapter.TranslationViewHolder> {

    private final List<Translation> translations;
    private final OnDeleteClickListener onDeleteClickListener;

    // Interface para o listener do botão de apagar
    public interface OnDeleteClickListener {
        void onDelete(int position);
    }

    public TranslationAdapter(List<Translation> translations, OnDeleteClickListener onDeleteClickListener) {
        this.translations = translations;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @NonNull
    @Override
    public TranslationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_translation_item, parent, false);
        return new TranslationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TranslationViewHolder holder, int position) {
        Translation translation = translations.get(position);
        holder.messageTextView.setText(translation.getMessage());
        holder.authorTextView.setText(translation.getAuthor());

        // Configura o listener do botão de apagar
        holder.deleteButton.setOnClickListener(v -> {
            if (onDeleteClickListener != null) {
                onDeleteClickListener.onDelete(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return translations.size();
    }

    static class TranslationViewHolder extends RecyclerView.ViewHolder {
        TextView messageTextView;
        TextView authorTextView;
        ImageButton deleteButton;

        public TranslationViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.txxtViewMsg);
            authorTextView = itemView.findViewById(R.id.edTxtViewAutor);
            deleteButton = itemView.findViewById(R.id.btnApagar);
        }
    }
}
