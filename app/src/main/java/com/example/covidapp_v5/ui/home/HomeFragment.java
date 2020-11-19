package com.example.covidapp_v5.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.covidapp_v5.R;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        final Button btnSalir = root.findViewById(R.id.btnSalir);
        final Button btnHospitales = root.findViewById(R.id.btnHospitales);
        final Button btnLlamadaCovid = root.findViewById(R.id.btnLlamadaCovid);
        final Button btnPCRNegativa = root.findViewById(R.id.btnPCRNegativa);
        final Button btnPCRPositiva = root.findViewById(R.id.btnPCRPositiva);
        //btnLugares = root.findViewById(R.id.btnLugares);

        btnPCRPositiva.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent mailintent = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
                mailintent.putExtra(Intent.EXTRA_EMAIL, new String[] { "pcr@covid.es" });
                mailintent.putExtra(Intent.EXTRA_CC, new String[] { "gdafonso@alu.ucam.edu" });
                mailintent.putExtra(Intent.EXTRA_BCC, new String[] { "gdafonso@alu.ucam.edu" });
                mailintent.putExtra(Intent.EXTRA_SUBJECT, "PCR POSITIVA");
                mailintent.putExtra(Intent.EXTRA_TEXT, "Buenos días, este mail es para informar del positivo del usuario");
                mailintent.setType("text/plain");
                startActivity(mailintent);
            }
        });

        btnPCRNegativa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent mailintent = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
                mailintent.putExtra(Intent.EXTRA_EMAIL, new String[] { "pcr@covid.es" });
                mailintent.putExtra(Intent.EXTRA_CC, new String[] { "gdafonso@alu.ucam.edu" });
                mailintent.putExtra(Intent.EXTRA_BCC, new String[] { "gdafonso@alu.ucam.edu" });
                mailintent.putExtra(Intent.EXTRA_SUBJECT, "PCR NEGATIVA");
                mailintent.putExtra(Intent.EXTRA_TEXT, "Buenos días, este mail es para informar del negativo del usuario");
                mailintent.setType("text/plain");
                startActivity(mailintent);
            }
        });

        btnLlamadaCovid.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String encodedPhoneNumber = String.format("tel:%s", Uri.encode("900112112"));
                Uri number = Uri.parse(encodedPhoneNumber);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
            }
        });

        btnHospitales.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:37.9913674,-1.185472?q=hospitals");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //finish();
            }
        });

        /*btnLugares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccesoActivity.this, LugaresActivity.class);
                startActivity(intent);
            }
        });*/
        return root;
    }
}