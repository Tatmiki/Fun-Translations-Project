package com.example.fun_translations;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TranslationAdapter extends RecyclerView.Adapter<TranslationAdapter.TranslationViewHolder> {

    private final List<TranslationResponse> translationResponses;
    private final OnDeleteClickListener onDeleteClickListener;

    // Interface para o listener do botão de apagar
    public interface OnDeleteClickListener {
        void onDelete(int position);
    }

    public TranslationAdapter(List<TranslationResponse> translationResponses, OnDeleteClickListener onDeleteClickListener) {
        this.translationResponses = translationResponses;
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
        TranslationResponse translationResponse = translationResponses.get(position);
        holder.messageTextView.setText(translationResponse.contents.text);
        holder.authorTextView.setText("Tipo: " + translationResponse.contents.translation);
        holder.translationTextView.setText(translationResponse.contents.translated);

        // Configura o listener do botão de apagar
        holder.deleteButton.setOnClickListener(v -> {
            if (onDeleteClickListener != null) {
                onDeleteClickListener.onDelete(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return translationResponses.size();
    }

    static class TranslationViewHolder extends RecyclerView.ViewHolder {
        TextView messageTextView;
        TextView authorTextView;
        TextView translationTextView;
        ImageButton deleteButton;

        public TranslationViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.txtViewText);
            authorTextView = itemView.findViewById(R.id.edTxtViewAutor);
            translationTextView = itemView.findViewById(R.id.txtViewTranslated);
            deleteButton = itemView.findViewById(R.id.btnApagar);
        }
    }
}
