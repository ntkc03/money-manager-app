package com.example.money_meow.home;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.money_meow.R;
import com.example.money_meow.account.LoginAccount;
import com.example.money_meow.database.TransactionQuery;
import com.example.money_meow.information.Information;
import com.example.money_meow.setting.AccountSettings;
import com.example.money_meow.transaction.TransactionAction;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    private RecyclerView rcvHistory;
    private HistoryList historyList;
    private Button addTransBtn,infoBtn,historyBtn,searchBtn,settingBtn;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        rcvHistory = findViewById(R.id.historylist);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        rcvHistory.setLayoutManager(gridLayoutManager);
        addTransBtn = findViewById(R.id.AddTransBtn);
        infoBtn = findViewById(R.id.InfoBtn);
        historyBtn = findViewById(R.id.HistoryBtn);
        searchBtn = findViewById(R.id.SearchBtn);
        settingBtn = findViewById(R.id.SettingBtn);

        historyList = new HistoryList(TransactionQuery.FindByUserName(LoginAccount.account.getUserName()),this);
        rcvHistory.setAdapter(historyList);
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Information.class);
                startActivity(intent);
            }
        });
        addTransBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, TransactionAction.class);
                startActivity(intent);
            }
        });

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, AccountSettings.class);
                startActivity(intent);
            }
        });
    }

}
