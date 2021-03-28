package com.example.oracolopartite;



   /*final Thread t = new Thread(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void run() {
                            try {

                                    String link2 = "https://sport.sky.it/calcio/serie-a/classifica";


                                    Connection conn2 = Jsoup.connect(link2);
                                    conn2.timeout(5 * 1000);

                                    conn2.followRedirects(true);
                                    Document doc2 = conn2.get();
                                    calendario.setText("ok");




                                    Element table = doc2.select("table").first();

                                    ArrayList<String> s = new ArrayList<>();
                                    for (Element tr : table.select("tr")) {
                                        for (Element td : tr.select("td")) {

                                            s.add(td.text().trim());

                                        }

                                    }

                                    int z = 2;
                                    int n = 8;
                                    int t = 9;
                                    sgf.clear();
                                    sgs.clear();
                                    st.clear();

                                    for (int i = 0; i < 20; i++) {

                                        st.add(s.get(z));
                                        sgf.add(s.get(n));
                                        sgs.add(s.get(t));
                                        z += 12;
                                        n += 12;
                                        t += 12;
                                    }

                                    calendario.setText("Fatto, cliccare su vai per vedere le probabilità.");

                                }
                                return;
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        }
                    });

                    t.run();
                    */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.math.RoundingMode;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.tensorflow.lite.Interpreter;


public class Over05 extends AppCompatActivity {

    Spinner giornata;
    Button vai,indietro;
    TextView calendario;
    Interpreter interpreter;
    String input[][];
    String m[][];
    Button precarica;

    ArrayList<String> st;
    ArrayList<String> sgf;
    ArrayList<String> sgs;
    ArrayList<String> sgfC, sgfF, sgsC, sgsF, giocate;
    String[] squadre = {
            "Ascoli",
            "Albinoleffe",
            "Atalanta",
            "Avellino",
            "Bari",
            "Benevento",
            "Bologna",
            "Brescia",
            "Cagliari",
            "Carpi",
            "Catania",
            "Cesena",
            "Chievo",
            "Cittadella",
            "Como",
            "Cosenza",
            "Cremonese",
            "Crotone",
            "Empoli",
            "Fiorentina",
            "Foggia",
            "Frosinone",
            "Genoa",
            "Grosseto",
            "Gubbio",
            "Inter",
            "Juventus",
            "JuveStabia",
            "Latina",
            "Lazio",
            "Lecce",
            "Livorno",
            "Milan",
            "Modena",
            "Monza",
            "Napoli",
            "Nocerina",
            "Novara",
            "Padova",
            "Palermo",
            "Parma",
            "Perugia",
            "Pescara",
            "Piacenza",
            "Pisa",
            "Pordenone",
            "Portogruaro",
            "ProVercelli",
            "Reggiana",
            "Reggina",
            "Roma",
            "Salernitana",
            "Sampdoria",
            "Sassuolo",
            "Siena",
            "Spal",
            "Spezia",
            "Ternana",
            "Torino",
            "Trapani",
            "Triestina",
            "Udinese",
            "Varese",
            "Venezia",
            "Verona",
            "Vicenza",
            "VirtusEntella",
            "VirtusLanciano"

    };

    protected void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.calendario);



            // Here we create a document object and use JSoup to fetch the website
            vai= findViewById(R.id.vai);

            calendario=findViewById(R.id.calendario);
            try {
                interpreter = new Interpreter(loadModelFile(this), null);
            } catch (
                    IOException e) {
                e.printStackTrace();
            }
            indietro=findViewById(R.id.indietro);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.giornata, android.R.layout.simple_spinner_item);
            giornata = findViewById(R.id.giornata);

            giornata.setAdapter(adapter);

            StringBuffer tmp=  new StringBuffer();
          /*  tmp.append("Una versione della classifica aggiornata alla 25 giornata è stata caricata a default, " +
                    "se si vuole scaricare la versione aggiornata cliccare su 'Precarica il dataset'.\n");
            tmp.append("Se richiede molto tempo cliccare su vai per vedere le probabilità non aggiornate");*/
            tmp.append("Scegliere la giornata e cliccare su 'Vai' per visualizzare le probabilità: la seconda  tiene conto anche dei gol subiti e fatti dalle squadre");
            st = new ArrayList<>();//squadra
            sgf = new ArrayList<>();//g fatti squadra
            sgs = new ArrayList<>();//g subiti squadra

            sgfC= new ArrayList<>();
            sgsC=new ArrayList<>();
            sgfF=new ArrayList<>();
            sgsF=new ArrayList<>();
            giocate=new ArrayList<>();
            calendario.setText(tmp);




                           /* Milan								23	9
                            Roma								29	10
                            Atalanta							25	15
                            Inter								20	11
                            Juventus							18	10
                            Napoli								18	8
                            Sampdoria							18	13
                            Bologna							    16	19
                            Spezia								14	14
                            Lazio								15	19
                            Verona								11	11
                            Torino								14	15
                            Genoa								11	12
                            Parma								11	20
                            Fiorentina							11	17
                            Sassuolo							10	16
                            Cagliari							10	19
                            Udinese							    9	16
                            Benevento							10	21
                            Crotone							    14	32
                        calendario.setText("Caricamento completato, cliccare su vai per vedere le probabilità.");


                    }
                }

            });*/

            indietro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Over05.this,MainActivity2.class));
                }
            });


            vai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    final Thread thread = new Thread(new Runnable() {

                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void run() {
                            try {
                                //Your code goes here


                                StringBuffer tmp = new StringBuffer();
                                tmp.append("Attendere il caricamento...");
                                calendario.setText(tmp);
                                String link = "https://sport.sky.it/calcio/serie-a/calendario";
                                Connection conn = Jsoup.connect(link);
                                conn.timeout(10 * 1000);
                                Document doc = conn.get();


                                String g = "giornata-" + giornata.getSelectedItem().toString();

                                Element div = doc.getElementById(g);
                                Elements partite = div.getElementsByClass("ftbl__results-table ftbl__results-table--margin");

                                StringBuffer str = new StringBuffer();
                                String c[] = null, f[] = null;
                                for (Element partita : partite) {
                                    c = partita.getElementsByClass("ftbl__match-row__home").text().split(" ");
                                    f = partita.getElementsByClass("ftbl__match-row__away").text().split(" ");
                                }

                                Set<String> casa = new LinkedHashSet<>();
                                Set<String> fuori = new LinkedHashSet<>();

                                for (String x : c)
                                    casa.add(x.trim());
                                for (String y : f)
                                    fuori.add(y.trim());

                                ArrayList<Float> home = new ArrayList<>();
                                HashMap<Float, String> sc = new HashMap<>();
                                for (String x : casa) {
                                    for (int i = 0; i < squadre.length; i++) {

                                        if (x.toUpperCase().equals(squadre[i].toUpperCase())) {
                                            home.add(i + 1f);
                                            sc.put(i + 1f, x);

                                            break;
                                        }
                                    }

                                }
                                ArrayList<Float> away = new ArrayList<>();
                                HashMap<Float, String> sf = new HashMap<>();

                                for (String y : fuori) {
                                    for (int j = 0; j < squadre.length; j++) {
                                        if (y.toUpperCase().equals(squadre[j].toUpperCase())) {
                                            away.add(j + 1f);
                                            sf.put(j + 1f, y);
                                            break;
                                        }
                                    }
                                }


                                DecimalFormat df = new DecimalFormat("##.##");
                                df.setRoundingMode(RoundingMode.DOWN);
                                float[][] r;
                                float v[][] = new float[1][2];
                              /*  if(sgf.isEmpty() || sgs.isEmpty()){
                                    calendario.setText("Errore nel caricamento dei dati, ripovare più tardi\n");
                                    for (int i = 0; i < 10; i++) {
                                        v[0][0] = home.get(i);
                                        v[0][1] = away.get(i);
                                        r = doInference(v);



                                        str.append(sc.get(v[0][0]) + "-");
                                        str.append(sf.get(v[0][1]) + " ");
                                        str.append("Prob Over 0.5 primo tempo: " +   df.format(r[0][0] * 100) + "%\n");

                                        }
                                    calendario.setText(str);
                                    return;
                                }*/

                                String[] classifica = {

                                        "Milan",
                                        "Roma",
                                        "Atalanta",
                                        "Napoli",
                                        "Inter",
                                        "Juventus",
                                        "Sampdoria",
                                        "Lazio",
                                        "Verona",
                                        "Spezia",
                                        "Bologna",
                                        "Torino",
                                        "Fiorentina",
                                        "Cagliari",
                                        "Genoa",
                                        "Sassuolo",
                                        "Parma",
                                        "Udinese",
                                        "Benevento",
                                        "Crotone"


                                };
                                String gi[]={
                                        "26",
                                        "25",
                                        "27",
                                        "25",
                                        "26",
                                        "25",
                                        "26",
                                        "26",
                                        "25",
                                        "27",
                                        "26",
                                        "24",
                                        "26",
                                        "26",
                                        "26",
                                        "25",
                                        "26",
                                        "26",
                                        "26",
                                        "27"


                                };
                                String y[] = {//gfatti

                                        "24",
                                        "30",
                                        "26",
                                        "20",
                                        "20",
                                        "19",
                                        "18",
                                        "18",
                                        "13",
                                        "14",
                                        "16",
                                        "15",
                                        "13",
                                        "12",
                                        "11",
                                        "12",
                                        "12",
                                        "10",
                                        "11",
                                        "17"



                                };
                                String u[] = {

                                        "9",
                                        "10",
                                        "16",
                                        "10",
                                        "11",
                                        "11",
                                        "14",
                                        "21",
                                        "12",
                                        "15",
                                        "21",
                                        "16",
                                        "18",
                                        "19",
                                        "13",
                                        "18",
                                        "22",
                                        "16",
                                        "23",
                                        "36"

                                };
                                String gfc[]={
                                        "11",
                                        "19",
                                        "12",
                                        "14",
                                        "11",
                                        "10",
                                        "11",
                                        "8",
                                        "6",
                                        "6",
                                        "12",
                                        "6",
                                        "8",
                                        "7",
                                        "7",
                                        "5",
                                        "4",
                                        "5",
                                        "5",
                                        "9"

                                };
                                String gsc[]={
                                        "8",
                                        "4",
                                        "7",
                                        "2",
                                        "6",
                                        "3",
                                        "10",
                                        "10",
                                        "5",
                                        "11",
                                        "12",
                                        "7",
                                        "6",
                                        "8",
                                        "7",
                                        "9",
                                        "9",
                                        "6",
                                        "11",
                                        "14"

                                };
                                String gff[]={
                                        "13",
                                        "11",
                                        "14",
                                        "6",
                                        "9",
                                        "9",
                                        "7",
                                        "10",
                                        "7",
                                        "8",
                                        "4",
                                        "9",
                                        "5",
                                        "5",
                                        "4",
                                        "7",
                                        "8",
                                        "5",
                                        "6",
                                        "8"

                                };
                                String gsf[]={
                                        "1",
                                        "6",
                                        "9",
                                        "8",
                                        "5",
                                        "8",
                                        "4",
                                        "11",
                                        "7",
                                        "4",
                                        "9",
                                        "9",
                                        "12",
                                        "11",
                                        "6",
                                        "9",
                                        "13",
                                        "10",
                                        "12",
                                        "22"
                                };
                                for (String x : classifica) {
                                    st.add(x);

                                }
                                for (String x : y) {
                                    sgf.add(x);
                                }
                                for (String x : u) {
                                    sgs.add(x);
                                }
                                for (String x: gi){
                                    giocate.add(x);
                                }
                                for(String x: gfc){
                                    sgfC.add(x);
                                }
                                for(String x: gsc){
                                    sgsC.add(x);
                                }
                                for(String x: gff){
                                    sgfF.add(x);
                                }
                                for(String x: gsf){
                                    sgsF.add(x);
                                }

                          /* final Thread t = new Thread(new Runnable() {


                                    @Override
                                    public void run() {
                                        try {

                                            String link2 = "http://1x2stats.com/it/ITA/Serie-A/classifica/primo-tempo/";


                                            Connection conn2 = Jsoup.connect(link2);
                                            conn2.timeout(5 * 1000);

                                            conn2.followRedirects(true);
                                            Document doc2 = conn2.get();
                                            calendario.setText("ok");


                                            Element table = doc2.select("table").first();

                                            ArrayList<String> s = new ArrayList<>();
                                            for (Element tr : table.select("tr")) {
                                                for (Element td : tr.select("td")) {

                                                    s.add(td.text().trim());

                                                }

                                            }

                                            int z = 2;
                                            int n = 8;
                                            int t = 9;
                                            sgf.clear();
                                            sgs.clear();
                                            st.clear();

                                            for (int i = 0; i < 20; i++) {

                                                st.add(s.get(z));
                                                sgf.add(s.get(n));
                                                sgs.add(s.get(t));
                                                z += 12;
                                                n += 12;
                                                t += 12;
                                            }


                                        } catch (IOException exception) {
                                            exception.printStackTrace();
                                        }
                                    }



                                    });

                                    t.run();*/

                                ArrayList<String> par= new ArrayList<>();
                                ArrayList<Float>fff= new ArrayList<>();
                                HashMap<String, ArrayList<Float>>mappa=new HashMap<>();
                                for (int i = 0; i<home.size(); i++) {
                                    v[0][0] = home.get(i);
                                    v[0][1] = away.get(i);
                                    r = doInference(v);

                                    float x1=0,x2=0, ris1=0, ris2=0, ris3=0;
                                    String gf1 = "",gs1="", gf2="", gs2="";


                                    String golFattiCasa="", golFattiFuori="", golSubitiCasa="", golSubitiFuori="", giocateCasa="", giocateFuori="";
                                    String teamHome= sc.get(v[0][0]);
                                    String teamAway = sf.get(v[0][1]);


                                    for(int h=0;h<st.size();h++) {
                                        if (st.get(h).toUpperCase().equals(teamHome.toUpperCase())) {
                                            gf1 = sgf.get(h);//gf tot
                                            gs1 = sgs.get(h);//gs tot
                                            golFattiCasa=sgfC.get(h);
                                            golSubitiCasa=sgsC.get(h);
                                            giocateCasa=giocate.get(h);
                                        } else if (st.get(h).toUpperCase().equals(teamAway.toUpperCase())) {
                                            gf2 = sgf.get(h);
                                            gs2 = sgs.get(h);
                                            golFattiFuori=sgfF.get(h);
                                            golSubitiFuori=sgsF.get(h);
                                            giocateFuori=giocate.get(h);
                                        }
                                    }
                                    x1 = (Float.parseFloat(gf1) + Float.parseFloat(gs2));
                                    x2 = (Float.parseFloat(gf2) + Float.parseFloat(gs1));

                                    ris1=(Float.parseFloat(golFattiCasa)/Float.parseFloat(giocateCasa))+(Float.parseFloat(golFattiFuori)/Float.parseFloat(giocateFuori));
                                    ris2=(Float.parseFloat(golSubitiCasa)/Float.parseFloat(giocateCasa))+(Float.parseFloat(golSubitiFuori)/Float.parseFloat(giocateFuori));


                                   // ris3=(Float.parseFloat(golFattiCasa)+Float.parseFloat(golSubitiFuori))/(Float.parseFloat(giocateCasa)+Float.parseFloat(giocateFuori));

                                    fff.add(ris2);
                                    float t2=0;
                                    if(x1>x2){
                                        t2=x1;
                                    }else t2=x2;

                                    ArrayList<Float> lista = new ArrayList<>();
                                    lista.add(r[0][0] * 100+t2/10);
                                    mappa.put(teamHome + "-" + teamAway, lista);


                                    if((x1+x2)+r[0][0]*100> 130 && ris2 > 0.6 && ris1> 0.6)
                                            par.add(teamHome+"-"+teamAway);

                                    }


                                float max1=0;
                                String mx1=null;
                                for(String x : mappa.keySet()) {
                                    if (mappa.get(x).get(0) > max1){
                                        max1 = mappa.get(x).get(0);
                                        mx1=x;
                                    }

                                }



                                for(String x : mappa.keySet()) {
                                    str.append(x);
                                    str.append(" " + df.format(mappa.get(x).get(0)) + "%\n");

                                }

                                str.append("\nPartita con probablità più alta : "+mx1+" "+df.format(max1));
                                str.append("\nSi consiglia di giocare: ");

                                for(String x : par){
                                    str.append(x+" ");
                                }


                                calendario.setText( str);

                            ////metodo 2




                            } catch (IOException ex) {
                                ex.printStackTrace();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    thread.start();

                }
            });
        


    }




    private MappedByteBuffer loadModelFile(Activity activity) throws IOException {
        AssetFileDescriptor fileDescriptor = activity.getAssets().openFd("model4.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }
    public float[][] doInference(float[][] c){
        float[][] output = new float[1][1];
        Map<Integer, Object> outputs = new HashMap<>();

        outputs.put(0, output);
        interpreter.runForMultipleInputsOutputs(c,outputs);
        return output;

    }


}
