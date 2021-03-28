package com.example.oracolopartite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {
    Button capito;
    TextView inizio;
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        capito=findViewById(R.id.capito);
        inizio=findViewById(R.id.contesto);

        String testo=
                "Questa applicazione è una AI che permette di calcolare, sulla base "+
                "di partite di calcio nelle maggiori competizioni da 10 anni a oggi, la probabilità di uno o più esiti finali.\n"+
                "La pagina iniziale permette di caricare i prossimi eventi di serie A.\n"+
                "-quota verde: la quota dei bookmakers è più alta della quota calcolata dall' intelligenza, è quindi più probabile che accada.\n"+
                "-quota rossa, i bookmakers hanno messo una quota più bassa per l'evento, non conviene giocarla.\n"+
                "La seconda schermata permette di caricare le prossime partite di serie A per giornata e un' altra rete neurale dirà la probabilità "+
                "che ci sia almeno un gol nel primo tempo, maggiore è la percentuale, maggiore la possibilità che avvenga.\n"+
                "App ancora in beta testing, ha avuto una correttezza nei risultati del 73%, provandola per un paio di giornata sembra prenderci abbastanza, chiaramente deve essere solo uno strumento di sostegno e non una imposizione. Attendere qualche secondo il caricamento, se entro 5 secondi non carica i risultati riprovare più tardi.\n Non si assume responsabilità alcuna di eventuali errori di previsione.\nGioca responsabilmente ma gioca :)";
        inizio.setText(testo);
        capito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, MainActivity2.class));
            }
        });



    }
}
