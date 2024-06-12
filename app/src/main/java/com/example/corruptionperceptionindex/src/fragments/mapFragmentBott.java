package com.example.corruptionperceptionindex.src.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.corruptionperceptionindex.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.HashMap;
import java.util.Map;

public class mapFragmentBott extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Map<String, Integer> corruptionData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Example corruption data
        corruptionData = new HashMap<>();
        corruptionData.put("Aceh", 10);
        corruptionData.put("Sumatera Utara", 65);
        corruptionData.put("Sumatera Selatan", 25);
        corruptionData.put("Sumatera Barat", 55);
        corruptionData.put("Bengkulu", 15);
        corruptionData.put("Riau", 45);
        corruptionData.put("Jambi", 35);
        corruptionData.put("Lampung", 5);
        corruptionData.put("Banten", 80);
        corruptionData.put("Jakarta", 60);
        corruptionData.put("Jawa Barat", 40);
        corruptionData.put("Jawa Tengah", 20);
        corruptionData.put("Yogyakarta", 10);
        corruptionData.put("Jawa Timur", 50);
        corruptionData.put("Bali", 30);
        corruptionData.put("Nusa Tenggara Barat", 70);
        corruptionData.put("Nusa Tenggara Timur", 90);
        corruptionData.put("Kalimantan Barat", 85);
        corruptionData.put("Kalimantan Tengah", 75);
        corruptionData.put("Kalimantan Selatan", 65);
        corruptionData.put("Kalimantan Timur", 55);
        corruptionData.put("Kalimantan Utara", 45);
        corruptionData.put("Sulawesi Utara", 35);
        corruptionData.put("Sulawesi Tengah", 25);
        corruptionData.put("Sulawesi Selatan", 15);
        corruptionData.put("Sulawesi Tenggara", 5);
        corruptionData.put("Gorontalo", 80);
        corruptionData.put("Sulawesi Barat", 60);
        corruptionData.put("Maluku", 40);
        corruptionData.put("Maluku Utara", 20);
        corruptionData.put("Papua Barat", 10);
        corruptionData.put("Papua", 50);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_bott, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Hardcoded coordinates for provinces
        addProvincePolygons();
        // Adjust camera position
        LatLngBounds indonesiaBounds = new LatLngBounds(
                new LatLng(-11.0, 95.0), new LatLng(6.0, 141.0));
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(indonesiaBounds, 0));
    }

    private void addProvincePolygons() {
        addProvincePolygon("Aceh", new LatLng[]{
                new LatLng(5.86199, 95.3163),
                new LatLng(4.85882, 97.48506),
                new LatLng(3.139, 97.6982),
                new LatLng(2.3365, 96.8865),
                new LatLng(3.2482, 95.8954),
                new LatLng(4.6782, 95.1977)
        });

        addProvincePolygon("Sumatera Utara", new LatLng[]{
                new LatLng(1.48422, 98.5503),
                new LatLng(2.0964, 99.9195),
                new LatLng(3.3474, 99.7594),
                new LatLng(4.0483, 98.6493),
                new LatLng(3.3512, 97.3583),
                new LatLng(2.3567, 97.3226)
        });

        addProvincePolygon("Sumatera Selatan", new LatLng[]{
                new LatLng(-2.99094, 104.75656),
                new LatLng(-3.28559, 104.83904),
                new LatLng(-3.57511, 105.0374),
                new LatLng(-3.74227, 105.03541),
                new LatLng(-3.8475, 104.76327),
                new LatLng(-3.73654, 104.4788),
                new LatLng(-3.52786, 104.40391),
                new LatLng(-3.40171, 104.15077),
                new LatLng(-3.27699, 104.04114),
                new LatLng(-2.98594, 104.06447)
        });

        addProvincePolygon("Sumatera Barat", new LatLng[]{
                new LatLng(-0.94708, 100.41718),
                new LatLng(-1.12331, 100.44087),
                new LatLng(-1.41128, 100.56833),
                new LatLng(-1.65757, 100.64702),
                new LatLng(-1.82513, 100.55084),
                new LatLng(-1.86517, 100.26747),
                new LatLng(-1.73121, 100.12319),
                new LatLng(-1.41521, 100.03212),
                new LatLng(-1.16241, 100.06294),
                new LatLng(-0.94767, 100.18542)
        });

        addProvincePolygon("Bengkulu", new LatLng[]{
                new LatLng(-3.8313, 102.3388),
                new LatLng(-3.8759, 102.4316),
                new LatLng(-3.9461, 102.5282),
                new LatLng(-4.0365, 102.6188),
                new LatLng(-4.1564, 102.7134),
                new LatLng(-4.2256, 102.7647),
                new LatLng(-4.2962, 102.8433),
                new LatLng(-4.3759, 102.9326),
                new LatLng(-4.4676, 103.0111),
                new LatLng(-4.5899, 103.0837),
                new LatLng(-4.7194, 103.1452),
                new LatLng(-4.8476, 103.2029),
                new LatLng(-4.9472, 103.2762),
                new LatLng(-5.0232, 103.3497),
                new LatLng(-5.0764, 103.4354),
                new LatLng(-5.1136, 103.5307),
                new LatLng(-5.1384, 103.6212),
                new LatLng(-5.1542, 103.6992),
                new LatLng(-5.1714, 103.7693),
                new LatLng(-5.1836, 103.8395),
                new LatLng(-5.1908, 103.9007),
                new LatLng(-5.1934, 103.9645),
                new LatLng(-5.1912, 104.0323),
                new LatLng(-5.1842, 104.1013),
                new LatLng(-5.1729, 104.1707),
                new LatLng(-5.1586, 104.2406),
                new LatLng(-5.1407, 104.3111),
                new LatLng(-5.1193, 104.3825),
                new LatLng(-5.0946, 104.4549),
                new LatLng(-5.0669, 104.5286),
                new LatLng(-5.0362, 104.6039)
        });

        addProvincePolygon("Bali", new LatLng[]{
                new LatLng(-8.409518, 115.188919),
                new LatLng(-8.230002, 115.167579),
                new LatLng(-8.090249, 115.149823),
                new LatLng(-8.092053, 115.091086),
                new LatLng(-8.133521, 114.897852),
                new LatLng(-8.389648, 114.469736),
                new LatLng(-8.65629, 114.451119),
                new LatLng(-8.860647, 114.509858),
                new LatLng(-8.908611, 114.62905),
                new LatLng(-8.768314, 114.923607),
                new LatLng(-8.409518, 115.188919)
        });

        addProvincePolygon("Banten", new LatLng[]{
                new LatLng(-6.405817, 106.064017),
                new LatLng(-6.319303, 106.163918),
                new LatLng(-6.225071, 106.293963),
                new LatLng(-6.137352, 106.318415),
                new LatLng(-5.925747, 106.345965),
                new LatLng(-5.884017, 106.457197),
                new LatLng(-5.995817, 106.587102),
                new LatLng(-6.050088, 106.726983),
                new LatLng(-6.105238, 106.797165),
                new LatLng(-6.200241, 106.91458),
                new LatLng(-6.405817, 106.064017)
        });

        addProvincePolygon("Yogyakarta", new LatLng[]{
                new LatLng(-7.946527, 110.369487),
                new LatLng(-7.784099, 110.409394),
                new LatLng(-7.703883, 110.405389),
                new LatLng(-7.608437, 110.393685),
                new LatLng(-7.516315, 110.360317),
                new LatLng(-7.476168, 110.318594),
                new LatLng(-7.511834, 110.252235),
                new LatLng(-7.584526, 110.243286),
                new LatLng(-7.646801, 110.258293),
                new LatLng(-7.723514, 110.289642),
                new LatLng(-7.813988, 110.314918),
                new LatLng(-7.909395, 110.342304),
                new LatLng(-7.946527, 110.369487)
        });



    }

    private void addProvincePolygon(String provinceName, LatLng[] coordinates) {
        if (corruptionData.containsKey(provinceName)) {
            int corruptionLevel = corruptionData.get(provinceName);
            int color = getColorForCorruptionLevel(corruptionLevel);

            PolygonOptions polygonOptions = new PolygonOptions()
                    .add(coordinates)
                    .fillColor(color)
                    .strokeColor(Color.BLACK)
                    .strokeWidth(2)
                    .zIndex(1);  // Ensure the polygon is visible
            mMap.addPolygon(polygonOptions);
        }
    }

    private int getColorForCorruptionLevel(int level) {
        if (level >= 80) {
            return Color.parseColor("#69b3a2");  // Dark Green
        } else if (level >= 60) {
            return Color.parseColor("#90c8ac");  // Light Green
        } else if (level >= 40) {
            return Color.parseColor("#ffd966");  // Yellow
        } else if (level >= 20) {
            return Color.parseColor("#f4a261");  // Orange
        } else {
            return Color.parseColor("#e76f51");  // Red
        }
    }
}
