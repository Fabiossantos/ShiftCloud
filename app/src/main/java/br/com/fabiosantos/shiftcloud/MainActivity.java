package br.com.fabiosantos.shiftcloud;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.fabiosantos.shiftcloud.adapter.CarroListaAdapter;
import br.com.fabiosantos.shiftcloud.model.Carro;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvCarros;
    private EditText etModelo;
    private Spinner spFabricante;
    private Button btAdd;

    private List<Carro> carros;
    private CarroListaAdapter carroListaAdapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carros = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("carros");

        rvCarros = (RecyclerView) findViewById(R.id.rvCarros);
        spFabricante = (Spinner) findViewById(R.id.spFabricante);
        etModelo = (EditText) findViewById(R.id.etModelo);
        btAdd = (Button) findViewById(R.id.btAdd);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Carro carro = new Carro();
                carro.setModelo(etModelo.getText().toString());
                carro.setFabricante(spFabricante.getSelectedItem().toString());

                databaseReference.push().setValue(carro);

                etModelo.setText("");
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getAll(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getAll(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                carros.remove(convertDataSnapshot(dataSnapshot));
                setList();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setList(){
        carroListaAdapter = new CarroListaAdapter(carros);
        rvCarros.setLayoutManager(new LinearLayoutManager(this));
        rvCarros.setAdapter(carroListaAdapter);

    }

    private Carro convertDataSnapshot (DataSnapshot dataSnapshot){
        Carro carro = new Carro();
        HashMap hashMap = (HashMap) dataSnapshot.getValue();

        carro.setId(dataSnapshot.getKey());
        carro.setFabricante(hashMap.containsKey("fabricante")
                ? hashMap.get("fabricante").toString():"");

        carro.setModelo(hashMap.containsKey("modelo")
                ? hashMap.get("modelo").toString():"");

        return carro;
    }

    private void getAll (DataSnapshot dataSnapshot) {
        carros.add(convertDataSnapshot(dataSnapshot));
        setList ();
    }
}
