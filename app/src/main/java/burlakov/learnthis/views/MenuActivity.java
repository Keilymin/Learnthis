package burlakov.learnthis.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

import burlakov.learnthis.R;
import burlakov.learnthis.models.Role;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Стартовый класс view содержит в себе связь с фрагментами и работу с боковым меню.
 * Если пользователь не вошел в систему сразу же перебрасывает его к меню для входа и регистрации
 */
public class MenuActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseAuth mAuth;
    private CircleImageView image;
    private TextView name;
    private TextView email;
    String avatar;
    FirebaseUser currentUser;
    FirebaseDatabase database;
    String role;
    String nameInfo;

    /**
     * При создании создает тулбар, налаживает связь с Firebase и настраивает боковое меню.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        String[] strings = Objects.requireNonNull(currentUser.getDisplayName()).split(" ");
        role = strings[2] + "s";
        if (role.equals(Role.TEACHER.toString() + "s")) {
            setContentView(R.layout.activity_teacher_menu);
        } else if (role.equals(Role.LEARNER.toString() + "s")) {
            setContentView(R.layout.activity_learner_menu);
        } else if (role.equals(Role.PARENT.toString() + "s")) {
            setContentView(R.layout.activity_parent_menu);
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_courses, R.id.nav_marks, R.id.nav_options)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        View hView = navigationView.getHeaderView(0);
        image = hView.findViewById(R.id.image);
        name = hView.findViewById(R.id.name);
        email = hView.findViewById(R.id.email);

    }

    /**
     * При запуске проверяет пользователя на вход, если пользователь не вошел выбрасывает его на главную страницу.
     */
    @Override
    public void onStart() {
        super.onStart();
        if (currentUser != null) {
            updateUI();
        } else {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Обновляет интерфейс
     */
    public void updateUI() {
        String[] strings = Objects.requireNonNull(currentUser.getDisplayName()).split(" ");
        nameInfo = strings[0] + " " + strings[1];
        email.setText(currentUser.getEmail());
        name.setText(nameInfo);
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users").child(role).child(currentUser.getUid()).child("image");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                avatar = dataSnapshot.getValue(String.class);
                assert avatar != null;
                if (avatar.equals("def")) {
                    image.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Glide.with(getApplicationContext()).load(avatar).into(image);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                image.setImageResource(R.mipmap.ic_launcher);
            }
        });
    }

    /**
     * Настройка навигации.
     */
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}