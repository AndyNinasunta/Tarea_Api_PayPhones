package com.example.tarea_api_payphone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tarea_api_payphone.Modelo.Carros;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private RequestQueue requestQue;
    private String url = "";
    Carros[] carros =new Carros[2];

    TextView nom_carro1,nom_carro2,precio_carro1,precio_carro2;

    //Variables para el Ingreso de los datos para el pago
    String numero_Tlf_Cliente="0988152063";//Se simula que en la app ya tiene el telefono del cliente
    String cod_Pais="593";//Por defecto Ecuador
    String cedula="1250599436";//Se simula que ya se tiene la cedula en la app
    double monto; // se lo calcula mas adelante
    double monto_con_Iva; //Se lo calcula mas adelante
    double monto_sin_Iva=0; //Se lo calcula despues
    int iva; //Aqui se almacena el IVA de los carros
    int id_Cliente_Transaccion; //Generaremos un ramdon enorme, que sirva como identificador unico


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nom_carro1=(TextView) findViewById(R.id.txtCarro1);
        nom_carro2=(TextView) findViewById(R.id.txtCarro2);
        precio_carro1=(TextView) findViewById(R.id.txtMonto);
        precio_carro2=(TextView) findViewById(R.id.txtMonto2);

        carros[0]=new Carros("Chevrolet Camaro SS 2016",400,12);
        nom_carro1.setText(carros[0].getDescripcion());
        precio_carro1.setText(String.valueOf(carros[0].getCosto()));
        carros[1]=new Carros("Chevrolet Camaro ZL1 2017",700,12);
        nom_carro2.setText(carros[1].getDescripcion());
        precio_carro2.setText(String.valueOf(carros[1].getCosto()));
        //obtenerEstadoDeLaTransaccion(5343287);

    }

    public void clicBoton1(View view)
    {
        iva=(int) (carros[0].getIva()*carros[0].getCosto());
        monto=carros[0].getCosto()*(carros[0].getIva()+100);
        monto_con_Iva=(carros[0].getCosto()*100);
        Random rn=new Random();
        id_Cliente_Transaccion=rn.nextInt(10000);
        enviarUnNuevoCobro();

        Intent intent = new Intent("payPhone_Android.PayPhone_Android.Purchase");
        startActivity(intent);
    }

    public void clicBoton2(View view)
    {
        iva=(int) (carros[1].getIva()*carros[1].getCosto());
        monto=carros[1].getCosto()*(carros[1].getIva()+100);
        monto_con_Iva=(carros[1].getCosto()*100);
        Random rn=new Random();
        id_Cliente_Transaccion=rn.nextInt(10000);
        enviarUnNuevoCobro();
        Intent intent = new Intent("payPhone_Android.PayPhone_Android.Purchase");
        startActivity(intent);
    }

    public void enviarUnNuevoCobro() {
        String url = "https://pay.payphonetodoesposible.com/api/Sale";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            obtenerIdTransaccion(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR:" + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("phoneNumber", numero_Tlf_Cliente);
                params.put("countryCode", cod_Pais);
                params.put("clientUserId", cedula);
                params.put("reference", "none");
                params.put("responseUrl", "http://paystoreCZ.com/confirm.php");
                params.put("amount",""+(int) monto+"");
                params.put("amountWithTax",""+(int) monto_con_Iva+"");
                params.put("amountWithoutTax", ""+(int)monto_sin_Iva+"");
                params.put("tax",""+iva+"");
                params.put("clientTransactionId", ""+id_Cliente_Transaccion+"");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                String auth = "Bearer wv9hYlGGqDOSPg4Xk89Dx7B5neTmZA9KXSQwP-" +
                        "LhWmm7k2cc7W7GyL7MeuKZElw9nJUtDnWdqhKoV_Q0K_QWrNCWi16E-F2wSzV3k5_2W5tlhH-KKLi_5hMbXPKSsHgja_nRCQYR0TPF_bRGn8K7KsUpIIgQW7ipml26s7abDpN5PK5Y8NKvbwkURtazAmX1qMPqvGLfU5o0SD2G3gnHCKG3y_ZYGKjyKJOqLm2VmlJjC-Oa6_RIw4g0kolE-yB7BvXTW2VpZx_uqfaqszxc31XRxw7LWM6lNr4z3WA2V0gxgJu3fkfsQRtkWfeW0eV683dqtUwx2olneof5-gEhisyBDeA";
                headers.put("Authorization", auth);

                return headers;
            }
        };
        requestQue = Volley.newRequestQueue(this);
        requestQue.add(stringRequest);
    }

    public void obtenerIdTransaccion(String stringResponse) throws JSONException {
        //Pasamos al objeto json los valores de el primer usuario
        JSONObject obJson=new JSONObject(stringResponse);
        System.out.println(obJson.get("transactionId"));
        //System.out.println(stringResponse);

    }


    //Procedimiento para obtener el estado de una transaccion
    public void obtenerEstadoDeLaTransaccion(int idTransaccion) {
        url = "https://pay.payphonetodoesposible.com/api/Sale/" + idTransaccion;

        JsonObjectRequest requestJson = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            cargarEstado(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error al conectarse:" + error.getMessage(), Toast.LENGTH_LONG).show();
                        System.out.println(error.getMessage());
                    }
                }

        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                String auth = "Bearer wv9hYlGGqDOSPg4Xk89Dx7B5neTmZA9KXSQwP-" +
                        "LhWmm7k2cc7W7GyL7MeuKZElw9nJUtDnWdqhKoV_Q0K_QWrNCWi16E-F2wSzV3k5_2W5tlhH-KKLi_5hMbXPKSsHgja_nRCQYR0TPF_bRGn8K7KsUpIIgQW7ipml26s7abDpN5PK5Y8NKvbwkURtazAmX1qMPqvGLfU5o0SD2G3gnHCKG3y_ZYGKjyKJOqLm2VmlJjC-Oa6_RIw4g0kolE-yB7BvXTW2VpZx_uqfaqszxc31XRxw7LWM6lNr4z3WA2V0gxgJu3fkfsQRtkWfeW0eV683dqtUwx2olneof5-gEhisyBDeA";
                headers.put("Authorization", auth);
                return headers;
            }
        };
        requestQue = Volley.newRequestQueue(this);
        requestQue.add(requestJson);
    }

    public void cargarEstado(JSONObject objt) throws JSONException {
        try {
            System.out.println(objt.get("statusCode"));
        } catch (JSONException e) {
            Toast.makeText(this, "Error al cargar los datos al objeto: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
}