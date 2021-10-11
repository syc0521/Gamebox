package com.example.gamebox.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.gamebox.R;
import com.example.gamebox.activity.LoginActivity;
import com.example.gamebox.bean.Score;
import com.example.gamebox.bean.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserFragment extends Fragment
{
    private Button edit, feedback, exit;
    private SharedPreferences scorePref, userPref;
    private List<Score> scoreList;
    private Gson gson = new Gson();
    private TextView userName;
    private User user;
    private List<User> userList;
    private int genderId, userId;
    private TextView userNickName, userEditSign, birth, userSign;
    private Spinner gender;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        getScorePrefs();
        getUserPrefs();
        getBundle();
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        initialize(view);
        if (user.getUserName() != null)
            userName.setText(user.getUserName());
        else
            userName.setText(user.getUserId());
        if (user.getSign() != null)
            userSign.setText(user.getSign());
        else
            userSign.setText("请编辑个性签名");
        edit.setOnClickListener(a ->
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
            builder.setTitle("编辑信息");
            View userDialog = LayoutInflater.from(this.getActivity()).inflate(R.layout.dialog_user_info_edit, null, false);
            userNickName = userDialog.findViewById(R.id.user_edit_nickname);
            userEditSign = userDialog.findViewById(R.id.user_edit_sign);
            birth = userDialog.findViewById(R.id.user_edit_birthdate);
            gender = userDialog.findViewById(R.id.user_edit_gender);
            userNickName.setText(user.getUserName());
            userEditSign.setText(user.getSign());
            birth.setOnClickListener(v -> showDatePickDlg());
            birth.setOnFocusChangeListener((v, hasFocus) -> { if (hasFocus) showDatePickDlg(); });
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
            try
            {
                birth.setText(dateFormat.format(user.getBirth()));
            }catch (NullPointerException e)
            {
                birth.setText("");
            }
            gender.setAdapter(new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, new String[]{"男", "女"}));
            gender.setSelection(user.isGender() ? 0 : 1);
            MyItemSelectedListener itemSelectedListener = new MyItemSelectedListener();
            gender.setOnItemSelectedListener(itemSelectedListener);
            builder.setView(userDialog);
            builder.setPositiveButton("保存", (dialog, which) -> saveUser());
            builder.setNegativeButton("取消", (dialog, which) -> {});
            builder.show();
        });
        feedback.setOnClickListener(v ->
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
            String[] scores = {"5", "4", "3", "2", "1"};
            final int[] index = {0};
            builder.setSingleChoiceItems(scores, index[0], (dialog, which) -> index[0] = which);
            builder.setTitle("为此应用打分");
            builder.setPositiveButton("保存", (dialog, which) ->
            {
                Score score = new Score();
                score.setScore(5 - index[0]);
                score.setDate(new Date());
                scoreList.add(score);
                String scoreRaw = gson.toJson(scoreList);
                SharedPreferences.Editor editor = scorePref.edit();
                editor.putString("scores", scoreRaw);
                editor.apply();
            });
            builder.setNegativeButton("取消", (dialog, which) -> {});
            builder.show();
        });
        exit.setOnClickListener(v ->
        {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setClass(getContext(), LoginActivity.class);
            startActivity(intent);
        });
        return view;
    }

    private void saveUser()
    {
        SharedPreferences.Editor editor = userPref.edit();
        user.setUserName(userNickName.getText().toString());
        user.setGender(genderId == 0);
        user.setSign(userEditSign.getText().toString());
        Date birthDate;
        SimpleDateFormat birthFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
        try
        {
            birthDate = birthFormat.parse(birth.getText().toString());
        } catch (ParseException e)
        {
            birthDate = new Date();
        }
        user.setBirth(birthDate);
        userList.set(userId, user);
        String userRaw = gson.toJson(userList);
        editor.putString("userRaw", userRaw);
        editor.apply();
        userName.setText(user.getUserName());
        userSign.setText(user.getSign());
    }

    private void initialize(@NonNull View view)
    {
        edit = view.findViewById(R.id.edit_information);
        feedback = view.findViewById(R.id.feedback);
        exit = view.findViewById(R.id.exit_login);
        userName = view.findViewById(R.id.user_name);
        userSign = view.findViewById(R.id.user_sign);
    }

    private void getBundle()
    {
        Bundle bundle = getArguments();
        if (bundle != null)
        {
            String userRaw = bundle.getString("user");
            user = gson.fromJson(userRaw, new TypeToken<User>(){}.getType());
            for (int i = 0; i < userList.size(); i++)
            {
                if (userList.get(i).getUserId().equals(user.getUserId()))
                {
                    userId = i;
                    break;
                }
            }
        }
    }

    private void getScorePrefs()
    {
        scorePref = getActivity().getSharedPreferences("score", Context.MODE_PRIVATE);
        String scoreRaw = scorePref.getString("scores", "");
        if (!scoreRaw.equals(""))
        {
            scoreList = gson.fromJson(scoreRaw, new TypeToken<List<Score>>(){}.getType());
        }
        else
        {
            scoreList = new ArrayList<>();
        }
    }

    private void getUserPrefs()
    {
        userPref = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        String userRaw = userPref.getString("userRaw", "");
        userList = gson.fromJson(userRaw, new TypeToken<List<User>>(){}.getType());
    }

    private void showDatePickDlg()
    {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                (view, year, monthOfYear, dayOfMonth) -> birth.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth),
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private class MyItemSelectedListener implements AdapterView.OnItemSelectedListener
    {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            switch (parent.getId())
            {
                case R.id.user_edit_gender:
                    genderId = position;
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {

        }
    }
}
