package com.example.money_meow.home;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.money_meow.BaseActivity;
import com.example.money_meow.R;
import com.example.money_meow.account.LoginAccount;
import com.example.money_meow.information.Information;
import com.example.money_meow.manageEngine.calculation.Calculation;
import com.example.money_meow.manageEngine.statistic.StatisticsAction;
import com.example.money_meow.setting.Settings;
import com.example.money_meow.transaction.ShowingTransactionAction;
import com.example.money_meow.transaction.Transaction;
import com.example.money_meow.transaction.TransactionAction;
import com.example.money_meow.transaction.TransactionList;

import java.util.List;

public class Home extends BaseActivity {
    private RecyclerView rcvHistory;
    private HistoryListForHome historyListForHome;
    private Button addTransBtn,homeBtn,historyBtn,transactionBtn,settingBtn;

    private TextView balanceView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        rcvHistory = findViewById(R.id.historylist);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        rcvHistory.setLayoutManager(gridLayoutManager);

        addTransBtn = findViewById(R.id.AddTransBtn);
        homeBtn = findViewById(R.id.HomeBtn);
        historyBtn = findViewById(R.id.HistoryBtn);
        transactionBtn = findViewById(R.id.transactionBtn);
        settingBtn = findViewById(R.id.SettingBtn);


        balanceView = findViewById(R.id.balance);
        balanceView.setText(
                Double.toString(
                LoginAccount.account.getBalance()
                )
                );

        historyListForHome = new HistoryListForHome(getList(),this);
        rcvHistory.setAdapter(historyListForHome);


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
                Intent intent = new Intent(Home.this, Settings.class);
                startActivity(intent);
            }
        });
        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, StatisticsAction.class);
                startActivity(intent);
            }
        });
        transactionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, ShowingTransactionAction.class);
                startActivity(intent);
            }
        });
    }

    private List<Transaction> getList() {
        List<Transaction> res = TransactionList.mainList.subList(Math.max(TransactionList.mainList.size() - 10, 0), TransactionList.mainList.size());
        return res;
    }

}
