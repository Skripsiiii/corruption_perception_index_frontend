package com.example.corruptionperceptionindex.src.adapter;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.corruptionperceptionindex.R;
import com.example.corruptionperceptionindex.src.model.DimensionData;

import java.util.List;

public class dataProvinsiThirdAdapter extends RecyclerView.Adapter<dataProvinsiThirdAdapter.ViewHolder> {

    private List<DimensionData> dimensionDataList;
    private OnNextButtonClickListener nextButtonClickListener;
    private DimensionData selectedDimensionData;

    public dataProvinsiThirdAdapter(List<DimensionData> dimensionDataList) {
        this.dimensionDataList = dimensionDataList;
    }

    public interface OnNextButtonClickListener {
        void onNextButtonClick(DimensionData dimensionData);
    }

    public void setOnNextButtonClickListener(OnNextButtonClickListener listener) {
        this.nextButtonClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_data_kabupaten_kota_second_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DimensionData dimensionData = dimensionDataList.get(position);
        holder.dimensiTxt.setText(dimensionData.getName());
        holder.statusPointText.setText(dimensionData.getIndexResult() + "/100");
        holder.progressBar.setProgress((int) (dimensionData.getIndexResult()));

        int color;
        String statusText;
        if (dimensionData.getIndexResult() <= 20) {
            color = 0xFFe76f51; // Red
            statusText = "Sangat Korup";
        } else if (dimensionData.getIndexResult() <= 40) {
            color = 0xFFf4a261; // Orange
            statusText = "Korup";
        } else if (dimensionData.getIndexResult() <= 60) {
            color = 0xFFffd966; // Yellow
            statusText = "Netral";
        } else if (dimensionData.getIndexResult() <= 80) {
            color = 0xFF90c8ac; // Light Green
            statusText = "Aman";
        } else {
            color = 0xFF69b3a2; // Green
            statusText = "Sangat Aman";
        }

        // Customize ProgressBar color
        LayerDrawable progressDrawable = (LayerDrawable) holder.progressBar.getProgressDrawable();
        Drawable progressLayer = progressDrawable.findDrawableByLayerId(android.R.id.progress);
        progressLayer.setColorFilter(color, PorterDuff.Mode.SRC_IN);

        holder.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDimensionData = dimensionData;
                if (nextButtonClickListener != null) {
                    nextButtonClickListener.onNextButtonClick(dimensionData);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dimensionDataList.size();
    }

    public DimensionData getSelectedDimensionData() {
        return selectedDimensionData;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView dimensiTxt;
        public ProgressBar progressBar;
        public TextView statusPointText;
        public TextView statusWarnaText;
        public View statusBar;
        public ImageView nextButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dimensiTxt = itemView.findViewById(R.id.dimensiTxt);
            progressBar = itemView.findViewById(R.id.progressBar);
            statusPointText = itemView.findViewById(R.id.statusPointText);
            statusWarnaText = itemView.findViewById(R.id.statusWarnaText);
            statusBar = itemView.findViewById(R.id.statusBar);
            nextButton = itemView.findViewById(R.id.nextButton);
        }
    }
}
