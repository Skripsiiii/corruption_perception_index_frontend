package com.example.corruptionperceptionindex.src.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.corruptionperceptionindex.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class mapFragmentBott extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Map<String, Integer> corruptionData;
    private Map<GeoJsonFeature, String> featureProvinceMap = new HashMap<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Example corruption data
        corruptionData = new HashMap<>();
        corruptionData.put("Special Region of Aceh", 18);
        corruptionData.put("North Sumatera", 14);
        corruptionData.put("Bangka Belitung Islands", 65);
        corruptionData.put("Sumatera Selatan", 14);
        corruptionData.put("West Sumatera", 14);
        corruptionData.put("Bengkulu", 19);
        corruptionData.put("Riau", 35);
        corruptionData.put("Riau Islands", 45);
        corruptionData.put("Jambi", 35);
        corruptionData.put("Lampung", 35);
        corruptionData.put("Banten", 54);
        corruptionData.put("Jakarta Special Capital Region", 54);
        corruptionData.put("West Java", 54);
        corruptionData.put("Central Java", 55);
        corruptionData.put("Special Region of Yogyakarta", 55);
        corruptionData.put("East Java", 75);
        corruptionData.put("Bali", 75);
        corruptionData.put("West Nusa Tenggara", 75);
        corruptionData.put("East Nusa Tenggara", 74);
        corruptionData.put("West Kalimantan", 85);
        corruptionData.put("Central Kalimantan", 75);
        corruptionData.put("South Kalimantan", 65);
        corruptionData.put("East Kalimantan", 55);
        corruptionData.put("Kalimantan Utara", 45);
        corruptionData.put("North Sulawesi", 88);
        corruptionData.put("Central Sulawesi", 75);
        corruptionData.put("South Sulawesi", 95);
        corruptionData.put("Southeast Sulawesi", 5);
        corruptionData.put("Gorontalo", 75);
        corruptionData.put("West Sulawesi", 74);
        corruptionData.put("Maluku", 94);
        corruptionData.put("North Maluku", 20);
        corruptionData.put("Special Region of West Papua", 94);
        corruptionData.put("Papua", 95);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_bott, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Add legend to the view
        LinearLayout legendLayout = view.findViewById(R.id.legendLayout);
        LayoutInflater.from(getContext()).inflate(R.layout.legend_layout, legendLayout, true);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try {
            // Load GeoJSON file from assets
            GeoJsonLayer layer = new GeoJsonLayer(mMap, R.raw.indonesiaedit, getContext());

            for (GeoJsonFeature feature : layer.getFeatures()) {
                String provinceName = feature.getProperty("name");
                if (corruptionData.containsKey(provinceName)) {
                    int corruptionLevel = corruptionData.get(provinceName);
                    int color = getColorForCorruptionLevel(corruptionLevel);
                    GeoJsonPolygonStyle polygonStyle = new GeoJsonPolygonStyle();
                    polygonStyle.setFillColor(color);
                    polygonStyle.setStrokeWidth(2);
                    feature.setPolygonStyle(polygonStyle);

                    // Store the feature and province name mapping
                    featureProvinceMap.put(feature, provinceName);
                }
            }

            layer.addLayerToMap();

            // Add click listener to the layer
            layer.setOnFeatureClickListener(feature -> {
                String provinceName = feature.getProperty("name");
                if (provinceName != null && corruptionData.containsKey(provinceName)) {
                    int corruptionLevel = corruptionData.get(provinceName);
                    String statusText;
                    if (corruptionLevel <= 20) {
                        statusText = "Tingkat Korupsi Sangat Tinggi";
                    } else if (corruptionLevel <= 40) {
                        statusText = "Tingkat Korupsi Tinggi";
                    } else if (corruptionLevel <= 60) {
                        statusText = "Tingkat Korupsi Sedang";
                    } else if (corruptionLevel <= 80) {
                        statusText = "Tingkat Korupsi Rendah";
                    } else {
                        statusText = "Tingkat Korupsi Sangat Rendah";
                    }
                    showProvinceInfo(provinceName, corruptionLevel, statusText);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Adjust camera position
        LatLngBounds indonesiaBounds = new LatLngBounds(
                new LatLng(-11.0, 95.0), new LatLng(6.0, 141.0));
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(indonesiaBounds, 0));

        // Add this part to zoom in on Sumatra and Java
        LatLngBounds sumatraJavaBounds = new LatLngBounds(
                new LatLng(-6.5, 95.0), new LatLng(1.5, 115.0));
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(sumatraJavaBounds, 0));
    }

    private int getColorForCorruptionLevel(int level) {
        if (level >= 80) {
            return ContextCompat.getColor(getContext(), R.color.Safe);
        } else if (level >= 60) {
            return ContextCompat.getColor(getContext(), R.color.SlightSafe);
        } else if (level >= 40) {
            return ContextCompat.getColor(getContext(), R.color.midDanger);
        } else if (level >= 20) {
            return ContextCompat.getColor(getContext(), R.color.SlightDdanger);
        } else {
            return ContextCompat.getColor(getContext(), R.color.VeryDanger);
        }
    }

    private void showProvinceInfo(String provinceName, int corruptionLevel, String statusText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_province_info, null);
        builder.setView(dialogView);

        TextView provinceNameTextView = dialogView.findViewById(R.id.province_name);
        TextView cpiValueTextView = dialogView.findViewById(R.id.cpi_value);
        TextView statusValueTextView = dialogView.findViewById(R.id.status_value);

        provinceNameTextView.setText(provinceName);
        cpiValueTextView.setText("CPI: " + corruptionLevel);
        statusValueTextView.setText(statusText);

        // Set the background color of the status value text based on corruption level
        int backgroundColor = getColorForCorruptionLevel(corruptionLevel);
        statusValueTextView.setBackgroundColor(backgroundColor);

        builder.setPositiveButton("OK", null);
        builder.show();
    }
}
