package com.example.amst_2ep_grupo8_soto_vargas;

import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.TextView;

import java.util.ArrayList;


public class GraficaActivity extends AppCompatActivity {

    String name;
    String fullName;
    ArrayList<String> nameStats;
    ArrayList<Integer> valueStats;
    BarChart bara_graf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafica);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        name = getIntent().getExtras().getString("name");
        fullName = getIntent().getExtras().getString("fullName");
        nameStats = getIntent().getExtras().getStringArrayList("nameStats");
        valueStats = getIntent().getExtras().getIntegerArrayList("valueStats");


        bara_graf = (BarChart)findViewById(R.id.barchart);
        iniciarGrafica();
        crearEntradas();
        ((TextView)findViewById(R.id.name_txt)).setText(name);
        ((TextView)findViewById(R.id.entire_name)).setText(fullName);

    }


    private void iniciarGrafica(){
        bara_graf.fitScreen();
        XAxis xAxis = bara_graf.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelRotationAngle(90);
        bara_graf.getAxisLeft().setDrawGridLines(false);
        bara_graf.animateY(1000);
        bara_graf.getLegend().setEnabled(false);

        if(nameStats.size() != 0) {
            String labels[] = new String[nameStats.size()];

            for (int i = 0; i < nameStats.size(); i++) {
                labels[i] = nameStats.get(i);
            }

            IndexAxisValueFormatter i = new IndexAxisValueFormatter();
            i.setValues(labels);
            xAxis.setValueFormatter(i);
        }
    }

    private void crearEntradas(){
        System.out.println(valueStats);
        System.out.println(nameStats);
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for(int i = 0; i<valueStats.size(); i++){
            String name = nameStats.get(i);
            Integer value = valueStats.get(i);
            BarEntry barEntry = new BarEntry(i, value);
            barEntries.add(barEntry);
        }
        if(barEntries.size() != 0){
            crearDataset(barEntries);
        }

    }
    private void crearDataset(ArrayList<BarEntry> barEntries){
        BarDataSet Data_Graf = new BarDataSet(barEntries, "Stats Dataset");
        Data_Graf.setColors(ColorTemplate.JOYFUL_COLORS);
        Data_Graf.setDrawValues(true);
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(Data_Graf);
        BarData data = new BarData(dataSets);
        bara_graf.setData(data);
        bara_graf.setFitBars(true);
    }








}
