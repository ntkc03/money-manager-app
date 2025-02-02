package com.example.money_meow.manageEngine.filter;

import android.app.DatePickerDialog;
import android.util.Pair;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.money_meow.account.LoginAccount;
import com.example.money_meow.category.Category;
import com.example.money_meow.transaction.Transaction;
import com.example.money_meow.transaction.TransactionList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class Filter {
    private EditText dayText;

    private boolean checktoFilter = false;

    public boolean isChecktoFilter() {
        return checktoFilter;
    }

    public void setChecktoFilter(boolean checktoFilter) {
        this.checktoFilter = checktoFilter;
    }

    public Filter() {

    }

    public Filter(EditText dayText) {
        this.dayText = dayText;
    }

    public DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                    dayText.setText(selectedDate);
                }
            };

    public List<Transaction> getListByFilter(Filter a, List<Transaction> crList, EditText startDayText, EditText endDayText) {
        List<Transaction> resFilter = new ArrayList<>();
        if(a.isChecktoFilter()) {
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
            a.setChecktoFilter(false);
        } else {
            resFilter = crList;
        }
        return resFilter;
    }

    public List<Transaction> getListBySearch(String keyword, List<Transaction> crList) {
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

    public static List<Transaction> getListByCurrentMonth() {
        List<Transaction> resFilter = new ArrayList<>();
        Date endDate = Calendar.getInstance().getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -30);
        Date startDate = calendar.getTime();
        for (Transaction transaction : TransactionList.mainList) {
            if(transaction.getTransactionTime().after(startDate) && transaction.getTransactionTime().before(endDate)) {
                resFilter.add(transaction);
            }
        }
        return resFilter;
    }

    public static  List<Transaction> getListByMonth(int month, int year) {
        List<Transaction> resFilter = new ArrayList<>();
        Date endDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.AM_PM, Calendar.PM);
        if (month == 1 || month == 3 || month == 5 || month == 7
                || month == 8 || month == 10 || month == 12) {
            calendar.set(Calendar.DAY_OF_MONTH, 30);
        } else if (month == 2) {
            if (year % 4 == 0 && year % 100 != 0) {
                calendar.set(Calendar.DAY_OF_MONTH, 28);
            } else {
                calendar.set(Calendar.DAY_OF_MONTH, 27);
            }
        } else {
            calendar.set(Calendar.DAY_OF_MONTH, 29);
        }
        endDate = calendar.getTime();
        Date startDate = new Date();
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        startDate = calendar.getTime();
        System.out.println(startDate + " " + endDate);
        for (Transaction transaction : TransactionList.mainList) {
            if(transaction.getTransactionTime().after(startDate) && transaction.getTransactionTime().before(endDate)) {
                resFilter.add(transaction);
            }
        }
        return resFilter;
    }

    public static List<Transaction> getExpenseList(List<Transaction> sourceList) {
        List<Transaction> resFilter = new ArrayList<>();
        for (Transaction transaction : sourceList) {
            if(transaction.getTransactionType().equals("extense")) {
                resFilter.add(transaction);
            }
        }
        return resFilter;
    }

    public static List<Transaction> getIncomeList(List<Transaction> sourceList) {
        List<Transaction> resFilter = new ArrayList<>();
        for (Transaction transaction : sourceList) {
            if(transaction.getTransactionType().equals("income")) {
                resFilter.add(transaction);
            }
        }
        return resFilter;
    }

    public static List<Transaction> getListByCategory(List<Transaction> sourceList, String sampleCategory) {
        List<Transaction> resFilter = new ArrayList<>();
        for (Transaction transaction : sourceList) {
            if(transaction.getTransactionCategory().getCategoryName().equals(sampleCategory)) {
                resFilter.add(transaction);
            }
        }
        return resFilter;
    }

    public static List<String> getRangeTime(List<Transaction> transactionList) {
        HashSet<Pair<Integer, Integer>> timeRange = new HashSet<>();
        for (int i = 0; i < transactionList.size(); i++) {
            timeRange.add(new Pair<>(transactionList.get(i).getTime().getYear() + 1900, transactionList.get(i).getTime().getMonth() + 1));
        }
        List<String> result = new ArrayList<>();
        for(Pair<Integer, Integer> i: timeRange) {
            String time = Integer.toString(i.first) + '/' + Integer.toString(i.second);
            result.add(time);
        }
        Collections.sort(result, Collections.reverseOrder());
        return result;
    }




}
