package com.elevenzon.WearIt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class PhotoTagActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerColor;
    private Spinner spinnerType;
    private Spinner spinnerPattern;
    private Spinner spinnerSeason;
    StringBuilder t_color = new StringBuilder("-");
    StringBuilder t_type = new StringBuilder("-");
    StringBuilder t_pattern = new StringBuilder("-");
    StringBuilder t_season = new StringBuilder("-");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_tag);

        spinnerColor = (Spinner) findViewById(R.id.tagColor);
        spinnerType = (Spinner) findViewById(R.id.tagType);
        spinnerPattern = (Spinner) findViewById(R.id.tagPattern);
        spinnerSeason = (Spinner) findViewById(R.id.tagSeason);

        //create arrayadapter using the string array and a default spinner
        ArrayAdapter<CharSequence> colorAdapter = ArrayAdapter.createFromResource(this, R.array.color_tags, android.R.layout.simple_spinner_item);
        //specify the layout to use when the list of choices appears
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //apply the adapter to the spinner
        spinnerColor.setAdapter(colorAdapter);
        spinnerColor.setOnItemSelectedListener(this);
        //same for the other 3 spinners
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,R.array.type_tags, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(typeAdapter);
        spinnerType.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> patternAdapter = ArrayAdapter.createFromResource(this,R.array.pattern_tags, android.R.layout.simple_spinner_item);
        patternAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPattern.setAdapter(patternAdapter);
        spinnerPattern.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> seasonAdapter = ArrayAdapter.createFromResource(this,R.array.season_tags, android.R.layout.simple_spinner_item);
        seasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSeason.setAdapter(seasonAdapter);
        spinnerSeason.setOnItemSelectedListener(this);

        Button exitButton = (Button) findViewById(R.id.buttonTagPage);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int GET_TEXT_REQUEST_CODE = 1;

                Intent tagResult = new Intent(PhotoTagActivity.this, MainActivity.class);
                tagResult.putExtra("color", t_color.toString());
                tagResult.putExtra("type", t_type.toString());
                tagResult.putExtra("pattern", t_pattern.toString());
                tagResult.putExtra("season", t_season.toString());

                setResult(RESULT_OK, tagResult);

                finish();
            }
        });

    }

    /**we need onItemSelectedListeners for every spinner to constantly save the options for our final tags
     * in this case though, we only save the selected option, irregardless of the specific spinner
     * this means we can use the same listener for every spinner, hence this chosen class structure
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int optionID = parent.getSelectedItemPosition();
        String value = parent.getItemAtPosition(optionID).toString();

        switch (parent.getId()){
            case R.id.tagColor:
                t_color.replace(0, t_color.length(), value);
                break;
            case R.id.tagType:
                t_type.replace(0, t_type.length(), value);
                break;
            case R.id.tagPattern:
                t_pattern.replace(0, t_pattern.length(), value);
                break;
            case R.id.tagSeason:
                t_season.replace(0, t_season.length(), value);
                break;
            default:break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}