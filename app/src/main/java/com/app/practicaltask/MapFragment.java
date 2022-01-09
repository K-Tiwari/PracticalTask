package com.app.practicaltask;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.practicaltask.databinding.FragmentMapBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Locale;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    SupportMapFragment supportMapFragment;
    FragmentMapBinding binding;
    private int markerclicked;
    LatLng latLng;
    double lat, lng;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false);

        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (supportMapFragment == null) {
            supportMapFragment = SupportMapFragment.newInstance();
            getChildFragmentManager().beginTransaction().replace(R.id.map, supportMapFragment).commit();
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        fetchLocation();

        binding.setMMap(this);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fragmentManager = getChildFragmentManager();
        supportMapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.map);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I am here");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
        googleMap.addMarker(markerOptions);

        try {

            Geocoder geo = new Geocoder(this.getContext(), Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(lat, lng,1);
            if (addresses.isEmpty()) {
            } else {
                googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Nullable
                    @Override
                    public View getInfoContents(@NonNull Marker marker) {
                        View v = getLayoutInflater().inflate(R.layout.infowindowlayout, null);

                        TextView tv_name = v.findViewById(R.id.tv_name);
                        TextView tv_email = v.findViewById(R.id.tv_email);
                        TextView tv_currentlocation = v.findViewById(R.id.tv_currentlocation);
                        TextView tv_telephone = v.findViewById(R.id.tv_telephone);
                        TextView tv_pincode = v.findViewById(R.id.tv_pincode);
                        TextView tv_country = v.findViewById(R.id.tv_country);

                        tv_name.setText("iFlair");
                        if (SharedPref.getStringPref(getContext(), "KEY_USERNAME") != null){
                            tv_email.setText(SharedPref.getStringPref(getContext(), "KEY_USERNAME"));
                        } else {
                            tv_email.setText("iflair@gmail.com");

                        }
                        tv_currentlocation.setText("Current Location: : "+addresses.get(0).getFeatureName()+", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea() );
                        tv_telephone.setPaintFlags(tv_telephone.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                        tv_telephone.setText("Telephone Number: : "+" 9876543210");
                        tv_pincode.setText("PinCode: : "+addresses.get(0).getPostalCode());
                        tv_country.setText("Country: :"+addresses.get(0).getCountryName());

                        if(onMarkerClick(marker)==true && markerclicked==1){
                            Log.e("rrrrrrrrr", "reached");
                        }

                        return v;
                    }

                    @Nullable
                    @Override
                    public View getInfoWindow(@NonNull Marker marker) {
                        return null;
                    }
                });
            }
        }
        catch (Exception e){
            Log.e("aaaaaaa",""+e.getMessage());
        }

    }

    private void fetchLocation(){
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED  && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
                return;
        }

        Task<Location> task= fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if (location != null){
                currentLocation = location;
               // Toast.makeText(getContext(), currentLocation.getLatitude()+" "+ currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                supportMapFragment.getMapAsync(MapFragment.this::onMapReady);
                onLocationChanged(currentLocation);
            }
        });
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    fetchLocation();
                    Log.e("2","2");
                }
                break;
        }
    }*/

    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub
        if (location != null) {
            System.out.println("in onlocationchanged");
            String locationString = location.convert(location.getLatitude(), 1);
            //Toast.makeText(getContext(), "locationString==" + locationString, Toast.LENGTH_LONG).show();
            lat = location.getLatitude();
            lng = location.getLongitude();
            String currentLocation = "The location is changed to Lat: " + lat + " Lng: " + lng;
           // Toast.makeText(getContext(), currentLocation, Toast.LENGTH_LONG).show();
        }
    }

    public boolean onMarkerClick(final Marker marker) {

        if (marker.equals(marker))
        {
            markerclicked=1;
            return true;
        }
        return false;
    }

}