package edu.grcc.luisjuarez.spanishdictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> spanishWords;
    List<String> englishWords;
    List<String> spanishSorted;
    ListView listViewSpanish;
    ArrayAdapter arrayAdapter;
    String databaseName = "Spanish.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get access to database data
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this, databaseName);
        databaseAccess.open();

        spanishWords = databaseAccess.getString("words", "spanish");
        spanishSorted = databaseAccess.getString("words", "spanish");
        englishWords = databaseAccess.getString("words", "english");
        Collections.sort(spanishSorted);

        listViewSpanish = findViewById(R.id.listViewSpanish);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, spanishSorted);
        listViewSpanish.setAdapter(arrayAdapter);

        listViewSpanish.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String spanishWord = listViewSpanish.getItemAtPosition(position).toString();
                String englishWord = "";
                
                for (int i = 0; i< spanishWords.size(); i++) {
                    if (spanishWords.get(i).equals(spanishWord)) {
                        englishWord = englishWords.get(i);
                    }
                }
                Toast.makeText(getApplicationContext(), "The word " + spanishWord + " means " + englishWord, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.menu_item_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
