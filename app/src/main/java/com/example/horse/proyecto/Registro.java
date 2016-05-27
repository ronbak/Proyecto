package com.example.horse.proyecto;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registro extends AppCompatActivity {

    String email, contraseña, contraseña2, nombre, apellido1, apellido2, telefono, fecha, m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setCurrentDateOnView();

        addListenerOnButton();

        Button cancelar = (Button) findViewById(R.id.btnCancelar);

        cancelar.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View arg0) {
                Intent intento = new Intent(getApplicationContext(), InicioSession.class);
                finish();
                startActivity(intento);
                Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
            }

        });

        Button registro = (Button) findViewById(R.id.btnRegistrarse);

        registro.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View arg0) {

                if(required()) {
                    //if(guardarUsuario(email,nombre,fecha,contraseña,telefono)){
                        Intent intento = new Intent(getApplicationContext(), InicioSession.class);
                        finish();
                        startActivity(intento);
                        Toast.makeText(getApplicationContext(), "Registrado", Toast.LENGTH_SHORT).show();
                    /*}
                    else {
                        Toast.makeText(getApplicationContext(), m, Toast.LENGTH_SHORT).show();
                    }*/

                }
                else {
                    Toast.makeText(getApplicationContext(), m, Toast.LENGTH_SHORT).show();
                }
            }

        });


    }


    public boolean guardarUsuario(String eemail, String nnombre, String nnacimiento, String clave, String ttelefono){

            boolean exito = false;
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            nnacimiento = arreglaFecha(nnacimiento);

            String URL = "http://empere12-001-site1.btempurl.com/WebServiceApiRouter.svc/api/registrar?email="+ eemail +"&cedula=0&nombre="+ nnombre +"&ap1=0&ap2=0&nacimiento="+ nnacimiento +"&clave="+ clave +"&foto=0&telefono=" + ttelefono;
            //URL = URL.replace(" ", "");
            try{
                String result = "";
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(new HttpGet(URL));
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));
                result=reader.readLine();



                JSONObject obj = new JSONObject(result);
                //JSONArray proveedores = obj.getJSONArray("success");

                //if(proveedores.length() > 0){
                //    exito = true;
                //}

            }catch(JSONException e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();

            }catch(ClientProtocolException e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }catch (IOException e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }
            return exito;

    }

    public String arreglaFecha(String fecha){

        String[] s = fecha.split("-");

        if(s[1].length() == 1){
            s[1] = "0" + s[1];
        }
        if(s[2].length() == 1){
            s[2] = "0" + s[2];
        }

        fecha = s[0] + "-" + s[1] + "-" + s[2];

        return fecha;
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean required(){
        boolean exito = true;
        m = "";

        EditText  ed =(EditText) findViewById(R.id.editEmail);
        email = ed.getText().toString();
        if(!isEmailValid(email)){
            m = "Mal formato del Email";
            exito = false;
        }
        ed =(EditText) findViewById(R.id.editContraseña);
        contraseña = ed.getText().toString();
        ed =(EditText) findViewById(R.id.editContraseña2);
        contraseña2 = ed.getText().toString();
        if(!contraseña.equals(contraseña2)){
            if(m.isEmpty()){
                m = "Contraseñas diferentes";
            }
            else {
                m += " - Contraseñas diferentes";
            }
        exito = false;
        }
        ed =(EditText) findViewById(R.id.editNombre);
        nombre = ed.getText().toString();
        ed =(EditText) findViewById(R.id.editApellido1);
        apellido1 = ed.getText().toString();
        ed =(EditText) findViewById(R.id.editApellido2);
        apellido2 = ed.getText().toString();
        ed =(EditText) findViewById(R.id.editTelefono);
        telefono = ed.getText().toString();

        fecha = "" + (new StringBuilder().append(year).append("-").append(month + 1).append("-").append(day)
               .append(""));

        if(email.isEmpty() || contraseña.isEmpty() || contraseña2.isEmpty() || nombre.isEmpty() ||
                apellido1.isEmpty() || apellido2.isEmpty() || telefono.isEmpty() || fecha.isEmpty()){
            if(m.isEmpty())
                m = "Debe llenar todas las opciones";
            else
                m += " - Debe llenar todas las opciones";
            exito = false;
        }

        return exito;
    }


            // colocar al abrir la clase de la activity
            @Override
            public void onBackPressed() {
                //Toast.makeText(getApplicationContext(), "Te atrape", Toast.LENGTH_LONG).show();
                //super.onBackPressed(); //habilite esto si desea que se devuelva con el boton back
                //Button MiBoton = (Button) findViewById(R.id.btnCancelar);
                //MiBoton.performClick();

            }


    ///////////////////////////////////////////////////////////////////
    private Pattern pattern = Pattern.compile("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)");
    private Matcher matcher;
    public boolean validate(final String date){

        matcher = pattern.matcher(date);

        if(matcher.matches()){

            matcher.reset();

            if(matcher.find()){

                String day = matcher.group(1);
                String month = matcher.group(2);
                int year = Integer.parseInt(matcher.group(3));

                if (day.equals("31") &&
                        (month.equals("4") || month .equals("6") || month.equals("9") ||
                                month.equals("11") || month.equals("04") || month .equals("06") ||
                                month.equals("09"))) {
                    return false; // only 1,3,5,7,8,10,12 has 31 days
                } else if (month.equals("2") || month.equals("02")) {
                    //leap year
                    if(year % 4==0){
                        if(day.equals("30") || day.equals("31")){
                            return false;
                        }else{
                            return true;
                        }
                    }else{
                        if(day.equals("29")||day.equals("30")||day.equals("31")){
                            return false;
                        }else{
                            return true;
                        }
                    }
                }else{
                    return true;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }


    /////////////
    private TextView tvDisplayDate;
    //private DatePicker dpResult;
    private int year;
    private int month;
    private int day;
    private Button btnChangeDate;
    static final int DATE_DIALOG_ID = 999;


    // display current date
    public void setCurrentDateOnView() {

        tvDisplayDate = (TextView) findViewById(R.id.editNacimiento);
        //dpResult = (DatePicker) findViewById(R.id.nacimiento);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        tvDisplayDate.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(day).append("-").append(month + 1).append("-")
                .append(year).append(" "));

    }

    public void addListenerOnButton() {

        btnChangeDate = (Button) findViewById(R.id.btnNacimiento);

        btnChangeDate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                showDialog(DATE_DIALOG_ID);

            }

        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
                        year, month,day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            tvDisplayDate.setText(new StringBuilder()
                    // Month is 0 based, just add 1
                    .append(day).append("-").append(month + 1).append("-")
                    .append(year).append(" "));

        }
    };


}
