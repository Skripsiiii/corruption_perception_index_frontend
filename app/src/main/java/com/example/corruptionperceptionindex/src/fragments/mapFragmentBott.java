package com.example.corruptionperceptionindex.src.fragments;

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
import com.google.maps.android.data.geojson.GeoJsonLayer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class mapFragmentBott extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map_bott, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Define bounds for Indonesia
        LatLngBounds indonesiaBounds = new LatLngBounds(
                new LatLng(-11.0, 95.0), // Southwest coordinate
                new LatLng(6.0, 141.0)   // Northeast coordinate
        );

        // Move camera to Indonesia bounds
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(indonesiaBounds, 0));

        // Polygon for West Java (Jawa Barat) with refined coordinates
        PolygonOptions jawaBaratPolygon = new PolygonOptions()
                .add(
                        new LatLng(-7.7956, 106.2649),
                        new LatLng(-6.9175, 107.6191),
                        new LatLng(-6.3455, 108.5149),
                        new LatLng(-6.9148, 108.7046),
                        new LatLng(-7.2553, 108.1904),
                        new LatLng(-7.7549, 106.7689),
                        new LatLng(-7.7956, 106.2649)
                )
                .strokeColor(0xFF00FF00) // Border color (green)
                .fillColor(0x7F00FF00); // Fill color (semi-transparent green)

        mMap.addPolygon(jawaBaratPolygon);

        // Polygon for Central Java (Jawa Tengah) with refined coordinates
        PolygonOptions jawaTengahPolygon = new PolygonOptions()
                .add(
                        new LatLng(-7.5651, 109.2516),
                        new LatLng(-6.9248, 110.1506),
                        new LatLng(-6.9209, 111.1496),
                        new LatLng(-7.3220, 111.4380),
                        new LatLng(-7.7447, 110.5790),
                        new LatLng(-7.5651, 109.2516)
                )
                .strokeColor(0xFF0000FF) // Border color (blue)
                .fillColor(0x7F0000FF); // Fill color (semi-transparent blue)

        mMap.addPolygon(jawaTengahPolygon);

        // Polygon for East Java (Jawa Timur) with refined coordinates
        PolygonOptions jawaTimurPolygon = new PolygonOptions()
                .add(
                        new LatLng(-8.0056, 112.6089),
                        new LatLng(-7.6710, 112.0911),
                        new LatLng(-7.5404, 112.8450),
                        new LatLng(-8.1150, 113.2418),
                        new LatLng(-8.3121, 112.6360),
                        new LatLng(-8.0056, 112.6089)
                )
                .strokeColor(0xFFFF0000) // Border color (red)
                .fillColor(0x7FFF0000); // Fill color (semi-transparent red)

        mMap.addPolygon(jawaTimurPolygon);


//        loadGeoJsonLayer(R.raw.aceh);

//        addProvincePolygon("Aceh", new LatLng[]{
//                new LatLng(5.86199, 95.3163),
//                new LatLng(4.85882, 97.48506),
//                new LatLng(3.139, 97.6982),
//                new LatLng(2.3365, 96.8865),
//                new LatLng(3.2482, 95.8954),
//                new LatLng(4.6782, 95.1977)
//        });
//
//        addProvincePolygon("Sumatera Utara", new LatLng[]{
//                new LatLng(1.48422, 98.5503),
//                new LatLng(2.0964, 99.9195),
//                new LatLng(3.3474, 99.7594),
//                new LatLng(4.0483, 98.6493),
//                new LatLng(3.3512, 97.3583),
//                new LatLng(2.3567, 97.3226)
//        });
//
//        addProvincePolygon("Sumatera Selatan", new LatLng[]{
//                new LatLng(-2.99094, 104.75656),
//                new LatLng(-3.28559, 104.83904),
//                new LatLng(-3.57511, 105.0374),
//                new LatLng(-3.74227, 105.03541),
//                new LatLng(-3.8475, 104.76327),
//                new LatLng(-3.73654, 104.4788),
//                new LatLng(-3.52786, 104.40391),
//                new LatLng(-3.40171, 104.15077),
//                new LatLng(-3.27699, 104.04114),
//                new LatLng(-2.98594, 104.06447)
//        });


        // Polygon for Aceh with example coordinates
        PolygonOptions acehPolygon = new PolygonOptions()
                .add(
                        new LatLng(5.86199, 95.3163),
                        new LatLng(4.85882, 97.48506),
                        new LatLng(3.139, 97.6982),
                        new LatLng(2.3365, 96.8865),
                        new LatLng(3.2482, 95.8954),
                        new LatLng(4.6782, 95.1977)
                )
                .strokeColor(0xFFFFA500) // Border color (orange)
                .fillColor(0x7FFF4500); // Fill color (semi-transparent orange)

        mMap.addPolygon(acehPolygon);

        // Polygon for North Sumatra (Sumatra Utara) with example coordinates
        PolygonOptions sumatraUtaraPolygon = new PolygonOptions()
                .add(
                        new LatLng(1.48422, 98.5503),
                        new LatLng(2.0964, 99.9195),
                        new LatLng(3.3474, 99.7594),
                        new LatLng(4.0483, 98.6493),
                        new LatLng(3.3512, 97.3583),
                        new LatLng(2.3567, 97.3226)
                )
                .strokeColor(0xFF8A2BE2) // Border color (blue violet)
                .fillColor(0x7F8A2BE2); // Fill color (semi-transparent blue violet)

        mMap.addPolygon(sumatraUtaraPolygon);

        // Polygon for West Sumatra (Sumatra Barat) with example coordinates
        PolygonOptions sumatraBaratPolygon = new PolygonOptions()
                .add(
                        new LatLng(-0.097, 99.512),
                        new LatLng(-0.905, 100.200),
                        new LatLng(-1.550, 100.352),
                        new LatLng(-2.041, 100.074),
                        new LatLng(-1.849, 99.270),
                        new LatLng(-1.389, 99.009),
                        new LatLng(-0.919, 99.178),
                        new LatLng(-0.097, 99.512)
                )
                .strokeColor(0xFF00CED1) // Border color (dark turquoise)
                .fillColor(0x7F00CED1); // Fill color (semi-transparent dark turquoise)

        mMap.addPolygon(sumatraBaratPolygon);

        // Polygon for Riau with example coordinates
        PolygonOptions riauPolygon = new PolygonOptions()
                .add(
                        new LatLng(1.748, 100.049),
                        new LatLng(1.053, 101.092),
                        new LatLng(0.203, 101.886),
                        new LatLng(-0.634, 101.801),
                        new LatLng(-1.304, 101.270),
                        new LatLng(-1.141, 100.221),
                        new LatLng(-0.322, 100.024),
                        new LatLng(1.053, 99.616),
                        new LatLng(1.748, 100.049)
                )
                .strokeColor(0xFFADFF2F) // Border color (green yellow)
                .fillColor(0x7FADFF2F); // Fill color (semi-transparent green yellow)

        mMap.addPolygon(riauPolygon);


        // Polygon for Kepulauan Riau with example coordinates
        PolygonOptions kepulauanRiauPolygon = new PolygonOptions()
                .add(
                        new LatLng(0.698, 104.425),
                        new LatLng(0.138, 104.929),
                        new LatLng(1.269, 106.208),
                        new LatLng(0.688, 106.637),
                        new LatLng(0.698, 104.425)
                )
                .strokeColor(0xFF00FF00) // Border color (green)
                .fillColor(0xFF00FF00); // Fill color (solid green)

        mMap.addPolygon(kepulauanRiauPolygon);

// Polygon for Jambi with example coordinates
        PolygonOptions jambiPolygon = new PolygonOptions()
                .add(
                        new LatLng(-1.159, 102.141),
                        new LatLng(-2.343, 102.153),
                        new LatLng(-3.472, 102.844),
                        new LatLng(-3.303, 104.518),
                        new LatLng(-1.901, 104.078),
                        new LatLng(-1.159, 102.141)
                )
                .strokeColor(0xFF00FFFF) // Border color (cyan)
                .fillColor(0x7F00FFFF); // Fill color (semi-transparent cyan)

        mMap.addPolygon(jambiPolygon);

// Polygon for Sumatra Selatan with example coordinates
        PolygonOptions sumatraSelatanPolygon = new PolygonOptions()
                .add(
                        new LatLng(-2.791, 103.145),
                        new LatLng(-3.121, 103.488),
                        new LatLng(-3.858, 104.601),
                        new LatLng(-4.208, 105.342),
                        new LatLng(-2.632, 105.080),
                        new LatLng(-2.791, 103.145)
                )
                .strokeColor(0xFF008000) // Border color (green)
                .fillColor(0x7F008000); // Fill color (semi-transparent green)

        mMap.addPolygon(sumatraSelatanPolygon);

// Polygon for Kepulauan Bangka Belitung with example coordinates
        PolygonOptions kepulauanBangkaBelitungPolygon = new PolygonOptions()
                .add(
                        new LatLng(-2.134, 105.200),
                        new LatLng(-3.055, 105.315),
                        new LatLng(-3.651, 106.009),
                        new LatLng(-2.773, 106.489),
                        new LatLng(-2.134, 105.200)
                )
                .strokeColor(0xFF800080) // Border color (purple)
                .fillColor(0x7F800080); // Fill color (semi-transparent purple)

        mMap.addPolygon(kepulauanBangkaBelitungPolygon);

// Polygon for Bengkulu with example coordinates
        PolygonOptions bengkuluPolygon = new PolygonOptions()
                .add(
                        new LatLng(-2.389, 101.541),
                        new LatLng(-3.212, 101.894),
                        new LatLng(-3.822, 102.625),
                        new LatLng(-3.112, 103.565),
                        new LatLng(-2.389, 101.541)
                )
                .strokeColor(0xFF800000) // Border color (maroon)
                .fillColor(0x7F800000); // Fill color (semi-transparent maroon)

        mMap.addPolygon(bengkuluPolygon);

// Polygon for Lampung with example coordinates
        PolygonOptions lampungPolygon = new PolygonOptions()
                .add(
                        new LatLng(-4.758, 105.185),
                        new LatLng(-5.469, 105.351),
                        new LatLng(-5.853, 105.873),
                        new LatLng(-6.364, 105.515),
                        new LatLng(-5.712, 104.276),
                        new LatLng(-4.758, 105.185)
                )
                .strokeColor(0xFF000080) // Border color (navy)
                .fillColor(0x7F000080); // Fill color (semi-transparent navy)

        mMap.addPolygon(lampungPolygon);

        // Polygon for DKI Jakarta with example coordinates
        PolygonOptions dkiJakartaPolygon = new PolygonOptions()
                .add(
                        new LatLng(-6.360, 106.709),
                        new LatLng(-6.123, 106.890),
                        new LatLng(-6.128, 107.296),
                        new LatLng(-6.368, 107.269),
                        new LatLng(-6.360, 106.709)
                )
                .strokeColor(0xFF000080) // Border color (navy)
                .fillColor(0x7F000080); // Fill color (semi-transparent navy)

        mMap.addPolygon(dkiJakartaPolygon);

// Polygon for Banten with example coordinates
        PolygonOptions bantenPolygon = new PolygonOptions()
                .add(
                        new LatLng(-6.274, 105.868),
                        new LatLng(-6.003, 106.113),
                        new LatLng(-6.076, 106.534),
                        new LatLng(-6.443, 106.378),
                        new LatLng(-6.274, 105.868)
                )
                .strokeColor(0xFF008000) // Border color (green)
                .fillColor(0x7F008000); // Fill color (semi-transparent green)

        mMap.addPolygon(bantenPolygon);

// Polygon for DI Yogyakarta with example coordinates
        PolygonOptions diyPolygon = new PolygonOptions()
                .add(
                        new LatLng(-7.918, 110.043),
                        new LatLng(-7.785, 110.729),
                        new LatLng(-7.955, 110.912),
                        new LatLng(-8.091, 110.586),
                        new LatLng(-7.918, 110.043)
                )
                .strokeColor(0xFF0000FF) // Border color (blue)
                .fillColor(0x7F0000FF); // Fill color (semi-transparent blue)

        mMap.addPolygon(diyPolygon);


// Polygon for Bali with example coordinates
        PolygonOptions baliPolygon = new PolygonOptions()
                .add(
                        new LatLng(-8.836, 114.477),
                        new LatLng(-8.505, 114.666),
                        new LatLng(-8.348, 115.431),
                        new LatLng(-8.671, 115.496),
                        new LatLng(-8.836, 114.477)
                )
                .strokeColor(0xFF800080) // Border color (purple)
                .fillColor(0x7F800080); // Fill color (semi-transparent purple)

        mMap.addPolygon(baliPolygon);


        // Polygon for Nusa Tenggara Barat with example coordinates
        PolygonOptions nusaTenggaraBaratPolygon = new PolygonOptions()
                .add(
                        new LatLng(-8.743, 115.872),
                        new LatLng(-8.807, 116.501),
                        new LatLng(-9.329, 116.584),
                        new LatLng(-9.684, 116.199),
                        new LatLng(-8.743, 115.872)
                )
                .strokeColor(0xFF000080) // Border color (navy)
                .fillColor(0x7F000080); // Fill color (semi-transparent navy)

        mMap.addPolygon(nusaTenggaraBaratPolygon);

// Polygon for Nusa Tenggara Timur with example coordinates
        PolygonOptions nusaTenggaraTimurPolygon = new PolygonOptions()
                .add(
                        new LatLng(-8.552, 118.115),
                        new LatLng(-8.641, 119.537),
                        new LatLng(-9.393, 119.753),
                        new LatLng(-10.164, 118.967),
                        new LatLng(-10.191, 118.125),
                        new LatLng(-8.552, 118.115)
                )
                .strokeColor(0xFF008000) // Border color (green)
                .fillColor(0x7F008000); // Fill color (semi-transparent green)

        mMap.addPolygon(nusaTenggaraTimurPolygon);

// Polygon for Kalimantan Barat with example coordinates
        PolygonOptions kalimantanBaratPolygon = new PolygonOptions()
                .add(
                        new LatLng(-0.062, 110.804),
                        new LatLng(-1.853, 110.632),
                        new LatLng(-2.874, 109.784),
                        new LatLng(-2.292, 109.312),
                        new LatLng(-0.062, 110.804)
                )
                .strokeColor(0xFFFFA500) // Border color (orange)
                .fillColor(0x7FFF4500); // Fill color (semi-transparent orange)

        mMap.addPolygon(kalimantanBaratPolygon);

// Polygon for Kalimantan Tengah with example coordinates
        PolygonOptions kalimantanTengahPolygon = new PolygonOptions()
                .add(
                        new LatLng(-1.042, 112.776),
                        new LatLng(-3.304, 113.682),
                        new LatLng(-3.663, 112.637),
                        new LatLng(-2.252, 111.592),
                        new LatLng(-1.042, 112.776)
                )
                .strokeColor(0xFF0000FF) // Border color (blue)
                .fillColor(0x7F0000FF); // Fill color (semi-transparent blue)

        mMap.addPolygon(kalimantanTengahPolygon);

// Polygon for Kalimantan Selatan with example coordinates
        PolygonOptions kalimantanSelatanPolygon = new PolygonOptions()
                .add(
                        new LatLng(-2.049, 115.318),
                        new LatLng(-3.413, 115.236),
                        new LatLng(-3.944, 114.557),
                        new LatLng(-2.700, 113.949),
                        new LatLng(-2.049, 115.318)
                )
                .strokeColor(0xFF800080) // Border color (purple)
                .fillColor(0x7F800080); // Fill color (semi-transparent purple)

        mMap.addPolygon(kalimantanSelatanPolygon);

// Polygon for Kalimantan Timur with example coordinates
        PolygonOptions kalimantanTimurPolygon = new PolygonOptions()
                .add(
                        new LatLng(1.034, 115.323),
                        new LatLng(0.171, 115.940),
                        new LatLng(0.355, 116.931),
                        new LatLng(1.543, 116.712),
                        new LatLng(1.763, 116.039),
                        new LatLng(1.034, 115.323)
                )
                .strokeColor(0xFFFF0000) // Border color (red)
                .fillColor(0x7FFF0000); // Fill color (semi-transparent red)

        mMap.addPolygon(kalimantanTimurPolygon);

        // Polygon for Kalimantan Utara with example coordinates
        PolygonOptions kalimantanUtaraPolygon = new PolygonOptions()
                .add(
                        new LatLng(2.877, 116.145),
                        new LatLng(3.603, 116.984),
                        new LatLng(4.090, 116.560),
                        new LatLng(3.358, 115.727),
                        new LatLng(2.877, 116.145)
                )
                .strokeColor(0xFF000080) // Border color (navy)
                .fillColor(0x7F000080); // Fill color (semi-transparent navy)

        mMap.addPolygon(kalimantanUtaraPolygon);

// Polygon for Sulawesi Utara with example coordinates
        PolygonOptions sulawesiUtaraPolygon = new PolygonOptions()
                .add(
                        new LatLng(2.575, 124.676),
                        new LatLng(2.941, 125.203),
                        new LatLng(3.848, 124.922),
                        new LatLng(3.699, 124.137),
                        new LatLng(2.575, 124.676)
                )
                .strokeColor(0xFF008000) // Border color (green)
                .fillColor(0x7F008000); // Fill color (semi-transparent green)

        mMap.addPolygon(sulawesiUtaraPolygon);

// Polygon for Gorontalo with example coordinates
        PolygonOptions gorontaloPolygon = new PolygonOptions()
                .add(
                        new LatLng(0.874, 121.612),
                        new LatLng(1.016, 123.157),
                        new LatLng(0.127, 123.628),
                        new LatLng(0.283, 121.825),
                        new LatLng(0.874, 121.612)
                )
                .strokeColor(0xFFFFA500) // Border color (orange)
                .fillColor(0x7FFF4500); // Fill color (semi-transparent orange)

        mMap.addPolygon(gorontaloPolygon);

// Polygon for Sulawesi Tengah with example coordinates
        PolygonOptions sulawesiTengahPolygon = new PolygonOptions()
                .add(
                        new LatLng(0.297, 121.421),
                        new LatLng(-0.128, 122.335),
                        new LatLng(-1.042, 122.217),
                        new LatLng(-1.244, 121.342),
                        new LatLng(-0.367, 120.692),
                        new LatLng(0.297, 121.421)
                )
                .strokeColor(0xFF0000FF) // Border color (blue)
                .fillColor(0x7F0000FF); // Fill color (semi-transparent blue)

        mMap.addPolygon(sulawesiTengahPolygon);

// Polygon for Sulawesi Barat with example coordinates
        PolygonOptions sulawesiBaratPolygon = new PolygonOptions()
                .add(
                        new LatLng(-1.118, 119.253),
                        new LatLng(-1.955, 120.380),
                        new LatLng(-2.767, 119.762),
                        new LatLng(-2.032, 118.805),
                        new LatLng(-1.118, 119.253)
                )
                .strokeColor(0xFF800080) // Border color (purple)
                .fillColor(0x7F800080); // Fill color (semi-transparent purple)

        mMap.addPolygon(sulawesiBaratPolygon);

// Polygon for Sulawesi Selatan with example coordinates
        PolygonOptions sulawesiSelatanPolygon = new PolygonOptions()
                .add(
                        new LatLng(-2.019, 120.795),
                        new LatLng(-3.249, 120.636),
                        new LatLng(-4.204, 121.614),
                        new LatLng(-3.091, 122.621),
                        new LatLng(-2.019, 120.795)
                )
                .strokeColor(0xFFFF0000) // Border color (red)
                .fillColor(0x7FFF0000); // Fill color (semi-transparent red)

        mMap.addPolygon(sulawesiSelatanPolygon);

// Polygon for Sulawesi Tenggara with example coordinates
        PolygonOptions sulawesiTenggaraPolygon = new PolygonOptions()
                .add(
                        new LatLng(-2.618, 122.252),
                        new LatLng(-4.789, 122.585),
                        new LatLng(-5.578, 123.887),
                        new LatLng(-4.422, 124.985),
                        new LatLng(-2.618, 122.252)
                )
                .strokeColor(0xFF800000) // Border color (maroon)
                .fillColor(0x7F800000); // Fill color (semi-transparent maroon)

        mMap.addPolygon(sulawesiTenggaraPolygon);

        // Polygon for Maluku with example coordinates
        PolygonOptions malukuPolygon = new PolygonOptions()
                .add(
                        new LatLng(-3.258, 128.273),
                        new LatLng(-3.342, 129.065),
                        new LatLng(-3.936, 129.759),
                        new LatLng(-4.727, 129.487),
                        new LatLng(-4.415, 128.539),
                        new LatLng(-3.258, 128.273)
                )
                .strokeColor(0xFF000080) // Border color (navy)
                .fillColor(0x7F000080); // Fill color (semi-transparent navy)

        mMap.addPolygon(malukuPolygon);

// Polygon for Maluku Utara with example coordinates
        PolygonOptions malukuUtaraPolygon = new PolygonOptions()
                .add(
                        new LatLng(1.635, 127.632),
                        new LatLng(1.498, 128.526),
                        new LatLng(0.611, 128.253),
                        new LatLng(0.879, 127.315),
                        new LatLng(1.635, 127.632)
                )
                .strokeColor(0xFF008000) // Border color (green)
                .fillColor(0x7F008000); // Fill color (semi-transparent green)

        mMap.addPolygon(malukuUtaraPolygon);

// Polygon for Papua with example coordinates
        PolygonOptions papuaPolygon = new PolygonOptions()
                .add(
                        new LatLng(-2.011, 137.022),
                        new LatLng(-2.928, 139.731),
                        new LatLng(-4.682, 140.854),
                        new LatLng(-5.583, 140.121),
                        new LatLng(-5.131, 137.223),
                        new LatLng(-3.863, 135.692),
                        new LatLng(-2.011, 137.022)
                )
                .strokeColor(0xFFFFA500) // Border color (orange)
                .fillColor(0x7FFF4500); // Fill color (semi-transparent orange)

        mMap.addPolygon(papuaPolygon);

// Polygon for Papua Barat with example coordinates
        PolygonOptions papuaBaratPolygon = new PolygonOptions()
                .add(
                        new LatLng(-0.076, 132.011),
                        new LatLng(-2.617, 134.298),
                        new LatLng(-3.746, 133.448),
                        new LatLng(-2.486, 130.743),
                        new LatLng(-0.076, 132.011)
                )
                .strokeColor(0xFF0000FF) // Border color (blue)
                .fillColor(0x7F0000FF); // Fill color (semi-transparent blue)

        mMap.addPolygon(papuaBaratPolygon);


        // Polygon for Papua Selatan with example coordinates
//        PolygonOptions papuaSelatanPolygon = new PolygonOptions()
//                .add(
//                        new LatLng(-5.050, 138.914),
//                        new LatLng(-5.686, 139.901),
//                        new LatLng(-7.607, 139.112),
//                        new LatLng(-6.666, 137.334),
//                        new LatLng(-5.050, 138.914)
//                )
//                .strokeColor(0xFF800080) // Border color (purple)
//                .fillColor(0x7F800080); // Fill color (semi-transparent purple)
//
//        mMap.addPolygon(papuaSelatanPolygon);

// Polygon for Papua Tengah with example coordinates
//        PolygonOptions papuaTengahPolygon = new PolygonOptions()
//                .add(
//                        new LatLng(-4.335, 138.571),
//                        new LatLng(-4.837, 139.285),
//                        new LatLng(-5.683, 139.195),
//                        new LatLng(-5.530, 138.363),
//                        new LatLng(-4.335, 138.571)
//                )
//                .strokeColor(0xFFFF0000) // Border color (red)
//                .fillColor(0x7FFF0000); // Fill color (semi-transparent red)
//
//        mMap.addPolygon(papuaTengahPolygon);

// Polygon for Papua Pegunungan with example coordinates
        PolygonOptions papuaPegununganPolygon = new PolygonOptions()
                .add(
                        new LatLng(-4.416, 138.771),
                        new LatLng(-4.955, 139.287),
                        new LatLng(-5.789, 138.792),
                        new LatLng(-5.216, 137.753),
                        new LatLng(-4.416, 138.771)
                )
                .strokeColor(0xFFFFFF00) // Border color (yellow)
                .fillColor(0x7FFFFF00); // Fill color (semi-transparent yellow)

        mMap.addPolygon(papuaPegununganPolygon);

// Polygon for Papua Barat Daya with example coordinates
        PolygonOptions papuaBaratDayaPolygon = new PolygonOptions()
                .add(
                        new LatLng(-2.312, 132.399),
                        new LatLng(-2.580, 134.212),
                        new LatLng(-3.301, 133.832),
                        new LatLng(-3.127, 132.475),
                        new LatLng(-2.312, 132.399)
                )
                .strokeColor(0xFF00FF00) // Border color (lime)
                .fillColor(0x7F00FF00); // Fill color (semi-transparent lime)

        mMap.addPolygon(papuaBaratDayaPolygon);

        PolygonOptions papuaSelatanPolygon = new PolygonOptions()
                .add(
                        new LatLng(-4.8768, 138.7461),
                        new LatLng(-5.0985, 137.3400),
                        new LatLng(-5.3503, 136.4083),
                        new LatLng(-5.2996, 136.1071),
                        new LatLng(-4.8091, 135.8389),
                        new LatLng(-4.3347, 136.2391),
                        new LatLng(-3.6553, 137.7534),
                        new LatLng(-4.8768, 138.7461)
                )
                .strokeColor(0xFF800080) // Border color (purple)
                .fillColor(0x7F800080); // Fill color (semi-transparent purple)

        mMap.addPolygon(papuaSelatanPolygon);

        // Polygon for Papua Tengah
        PolygonOptions papuaTengahPolygon = new PolygonOptions()
                .add(
                        new LatLng(-3.2817, 137.1474),
                        new LatLng(-3.3431, 137.5251),
                        new LatLng(-3.7735, 137.7417),
                        new LatLng(-4.1621, 137.6892),
                        new LatLng(-4.1621, 137.4143),
                        new LatLng(-4.0009, 136.8542),
                        new LatLng(-3.4721, 136.8890),
                        new LatLng(-3.2817, 137.1474)
                )
                .strokeColor(0xFFFF0000) // Border color (red)
                .fillColor(0x7FFF0000); // Fill color (semi-transparent red)

        mMap.addPolygon(papuaTengahPolygon);



        // Add listener for marker clicks
        mMap.setOnMarkerClickListener(marker -> {
            // Handle marker click events
            // e.g., show additional information or trigger actions
            return false;
        });
    }

//    private void loadGeoJsonLayer(int resourceId) {
//        try {
//            InputStream inputStream = getResources().openRawResource(resourceId);
//            byte[] buffer = new byte[inputStream.available()];
//            inputStream.read(buffer);
//            inputStream.close();
//            String geoJsonString = new String(buffer, "UTF-8");
//            JSONObject geoJsonData = new JSONObject(geoJsonString);
//            GeoJsonLayer layer = new GeoJsonLayer(mMap, geoJsonData);
//            layer.addLayerToMap();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private void loadGeoJsonLayer(int resourceId) {
        try {
            InputStream inputStream = getResources().openRawResource(resourceId);
            JSONObject geoJsonData = new JSONObject(convertStreamToString(inputStream));
            GeoJsonLayer layer = new GeoJsonLayer(mMap, geoJsonData);
            layer.addLayerToMap();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String convertStreamToString(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }


}
