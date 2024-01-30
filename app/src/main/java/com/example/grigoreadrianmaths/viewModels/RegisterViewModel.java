package com.example.grigoreadrianmaths.viewModels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grigoreadrianmaths.R;
import com.example.grigoreadrianmaths.dao.UserDAO;
import com.example.grigoreadrianmaths.database.ConexionDB;

import java.sql.Connection;

public class RegisterViewModel extends AppCompatActivity {

    private EditText name,surname,username,password,confirmPassword;
    private UserDAO userDAO;
    private Connection connection;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        connection = ConexionDB.initDBConnection();

        userDAO = new UserDAO(connection);
    /*
        name = findViewById(R.id.etNombre);
        surname = findViewById(R.id.etApellidos);
        username = findViewById(R.id.etUsuario);
        password = findViewById(R.id.etPassword);
        confirmPassword = findViewById(R.id.etPassword2);

     */
    }

    public void register(View view) {
        String nombre = name.getText().toString();
        String apellidos = surname.getText().toString();
        String usuario = username.getText().toString();
        String passwd = password.getText().toString();
        String confirmPasswd = confirmPassword.getText().toString();

        // La cadena "nombre" está vacía o nula = isBlank
        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(apellidos) ||
                TextUtils.isEmpty(usuario) || TextUtils.isEmpty(passwd) || TextUtils.isEmpty(confirmPasswd)) {
            Toast.makeText(this, "Te faltan campos por rellenar!", Toast.LENGTH_LONG).show();
        }

        if(!password.getText().toString().equals(confirmPasswd)){
            Toast.makeText(this, "Las contraseñas no coinciden!", Toast.LENGTH_LONG).show();
        }
        try {
            userDAO.createUser(nombre,apellidos,usuario,passwd);
            Toast.makeText(this, "Usuario creado ", Toast.LENGTH_LONG).show();
            intent = new Intent(RegisterViewModel.this,LoginViewModel.class);
            startActivity(intent);
        }catch (Exception e) {
            if(e.getMessage().contains("already exists"))
                Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_LONG).show();

            else
                Toast.makeText(this, "Error al crear Usuario", Toast.LENGTH_LONG).show();
        }

    }
    public void go(View view){
        intent = new Intent(RegisterViewModel.this,LoginViewModel.class);
        startActivity(intent);
    }
}