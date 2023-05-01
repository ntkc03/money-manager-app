package com.example.money_meow.manageEngine.searchEngine;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.money_meow.BaseActivity;
import com.example.money_meow.R;
import com.example.money_meow.home.Home;
import com.example.money_meow.manageEngine.filter.Filter;
import com.example.money_meow.manageEngine.statistic.StatisticsAction;
import com.example.money_meow.setting.Settings;
import com.example.money_meow.transaction.Transaction;
import com.example.money_meow.transaction.TransactionAction;
import com.example.money_meow.transaction.TransactionList;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SearchEngine extends BaseActivity {
    private RecyclerView rcvTransList;
    private TransactionAdapter transactionAdapter;

    private  String searchValue;
    private EditText searchText, startDayText, endDayText;

    private Filter filter = new Filter();
    private Button homeBtn, historyBtn, settingBtn,addTransBtn, filterBtn;
    public static ImageView searchImg;

    private List<Transaction> crList = new ArrayList<>(TransactionList.mainList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching);

        rcvTransList = findViewById(R.id.TransactionList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        rcvTransList.setLayoutManager(gridLayoutManager);

        transactionAdapter = new TransactionAdapter(crList,this);
        rcvTransList.setAdapter(transactionAdapter);

        homeBtn = findViewById(R.id.HomeBtn);
        historyBtn = findViewById(R.id.HistoryBtn);
        addTransBtn = findViewById(R.id.AddTransBtn);
        settingBtn = findViewById(R.id.SettingBtn);
        filterBtn = findViewById(R.id.FilterBtn);
        filterBtn.setTransformationMethod(null);

        searchText = findViewById(R.id.edit_text_search);
        searchText.addTextChangedListener(searchValueWatcher);
        searchImg = findViewById(R.id.imageSearch);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(SearchEngine.this, R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView(R.layout.filter_date);
        startDayText = bottomSheetDialog.findViewById(R.id.editStartDay);
        endDayText = bottomSheetDialog.findViewById(R.id.editEndDay);
        Button submit = bottomSheetDialog.findViewById(R.id.acceptBtn);
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.show();
            }
        });

        assert submit != null;
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(endDayText != null && startDayText != null && !endDayText.getText().toString().isEmpty() && !startDayText.getText().toString().isEmpty()) {
                    filter.setChecktoFilter(true);
                    getListByFilter(filter);
                    startDayText.setText(null);
                    endDayText.setText(null);
                    bottomSheetDialog.dismiss();
                }
            }
        });

        endDayText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                Filter filterDay = new Filter(endDayText);
                DatePickerDialog dialog = new DatePickerDialog(
                        SearchEngine.this,
                        filterDay.mDateSetListener,
                        year,
                        month,
                        day);
                dialog.show();
            }
        });

        startDayText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                Filter filterDay = new Filter(startDayText);
                DatePickerDialog dialog = new DatePickerDialog(
                        SearchEngine.this,
                        filterDay.mDateSetListener,
                        year,
                        month,
                        day);
                dialog.show();
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchEngine.this, Home.class);
                startActivity(intent);
            }
        });

        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchEngine.this, StatisticsAction.class);
                startActivity(intent);
            }
        });

        addTransBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchEngine.this, TransactionAction.class);
                startActivity(intent);
            }
        });

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchEngine.this, Settings.class);
                startActivity(intent);
            }
        });
    }
    private List<Transaction> getListBySearch(String keyword) {
        List<Transaction> resSearch = new ArrayList<>();
        if (keyword != null && !keyword.isEmpty()) {
            for (Transaction transaction : crList) {
                if (transaction.getTransactionCategory() != null &&
                        transaction.getTransactionCategory().getCategoryName() != null &&
                        transaction.getTransactionCategory().getCategoryName().contains(keyword)) {
                    resSearch.add(transaction);
                }
            }
        } else {
            resSearch = crList;
        }
        return resSearch;
    }

    private List<Transaction> getListByFilter(Filter filter) {
        List<Transaction> resFilter = new ArrayList<>();
        if(filter.isChecktoFilter()) {
            for (Transaction transaction : crList) {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    Date startDate = format.parse(startDayText.getText().toString());
                    Date endDate = format.parse(endDayText.getText().toString());
                    if(transaction.getTransactionTime().after(startDate) && transaction.getTransactionTime().before(endDate)) {
                        resFilter.add(transaction);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            filter.setChecktoFilter(false);
        } else {
            resFilter = crList;
        }
        setCrList(resFilter);
        transactionAdapter.updateList(crList);
        return resFilter;
    }
    private TextWatcher searchValueWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            searchValue = s.toString();
            setCrList(getListBySearch(searchValue));
            transactionAdapter.updateList(crList);
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    public List<Transaction> getCrList() {
        return crList;
    }

    public void setCrList(List<Transaction> crList) {
        this.crList = crList;
    }
}
