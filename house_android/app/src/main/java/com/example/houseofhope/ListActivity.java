package com.example.houseofhope;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.houseofhope.api.Model.GuestData;
import com.example.houseofhope.api.Model.GuestModel;
import com.example.houseofhope.api.Model.ResultCode;
import com.example.houseofhope.api.Model.UpdateGuest;
import com.example.houseofhope.api.Model.Upload;
import com.example.houseofhope.api.RetrofitSender;
import com.example.houseofhope.src.Auth_guest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_visitor_lookup);

        SharedPreferences pref = getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
        String user_dong = pref.getString("user_dong", "");
        String user_ho = pref.getString("user_ho", "");

        Auth_guest visitor = new Auth_guest(user_dong, user_ho);
        try{
            getVisitlist(visitor);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = (ListAdapter) listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    void show(final String guestid, final int position)
    {
        SharedPreferences pref2 = getSharedPreferences("VisitorInfo", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref2.edit();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("방문자 조회");
        builder.setMessage("방문을 수락하시겠습니까?");
        builder.setPositiveButton("예",

                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        update_visit_guest(guestid, 1);
                        for (int i = 0; i < mListView.getChildCount(); i++) {
                            if(position == i ){
                                mListView.getChildAt(i).setBackgroundColor(Color.BLUE);
                            }
                        }

                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        update_visit_guest(guestid, 2);
                        for (int i = 0; i < mListView.getChildCount(); i++) {
                            if(position == i ){
                                mListView.getChildAt(i).setBackgroundColor(Color.RED);
                            }
                        }

                    }
                });
        builder.show();
    }
    public void update_visit_guest(String guest_id, int status){
        System.out.println("게스트정보 : "+guest_id + "   " + status);
        UpdateGuest guest = new UpdateGuest(guest_id, status);
        RetrofitSender.getServer().updateguest(guest).enqueue(new Callback<Upload>(){
            @Override
            public void onResponse(Call<Upload> call, Response<Upload> response) {
                if (response.isSuccessful()){
                    Upload result = response.body();
                    if (result.getResponseCode() == 200){
                        System.out.println(" 업데이트 성공공");
                    }else{
                        System.out.println("실패패");
                    }
                }

            }

            @Override
            public void onFailure(Call<Upload> call, Throwable t) {
                System.out.println("서버 연결 실패");
            }
        });
    }
        
    private void getVisitlist(final Auth_guest visitor) {

        RetrofitSender.getServer().getuserguest(visitor).enqueue(new Callback<GuestData>(){
            @Override
            public void onResponse(Call<GuestData> call, Response<GuestData> response) {
                if (response.isSuccessful()){
                    GuestData result = response.body();
                    if (result.getResponseCode() == 200){
                        List<GuestModel> guests = result.getData();
                        ArrayList<Integer> GuestID = new ArrayList<>();
                        ArrayList<String> GuestName = new ArrayList<String>();
                        ArrayList<String> GuestCar = new ArrayList<String>();
                        ArrayList<String> VisitDay = new ArrayList<String>();
                        ArrayList<String> VisitDong = new ArrayList<String>();
                        ArrayList<String> VisitHo = new ArrayList<String>();
                        ArrayList<String> VisitWhy = new ArrayList<String>();


                        for (GuestModel guest : guests){
                            //System.out.println(guest.getGuestName());
                            GuestID.add(guest.getId());
                            GuestName.add(guest.getGuestName());
                            GuestCar.add(guest.getGuestCar());
                            VisitDay.add(guest.getVisitDay());
                            VisitDong.add(guest.getVisitDong());
                            VisitHo.add(guest.getVisitHo());
                            VisitWhy.add(guest.getVisitWhy());
                        }
                        final Integer[] guestid = GuestID.toArray(new Integer[GuestID.size()]);
                        String[] guestNames = GuestName.toArray(new String[GuestName.size()]);
                        String[] guestCars = GuestCar.toArray(new String[GuestCar.size()]);
                        String[] visitdays = VisitDay.toArray(new String[VisitDay.size()]);
                        String[] visitdongs = VisitDong.toArray(new String[VisitDong.size()]);
                        String[] visithos = VisitHo.toArray(new String[VisitHo.size()]);
                        String[] visitwhys = VisitWhy.toArray(new String[VisitWhy.size()]);

                        final ArrayList<ItemData> oData = new ArrayList<ItemData>();

                        for(int i=0; i<guestNames.length; i++) {
                            ItemData oItem = new ItemData();
                            oItem.guest_name = guestNames[i];
                            oItem.guest_car = guestCars[i];
                            oItem.date = visitdays[i];
                            oItem.visit_dong = visitdongs[i];
                            oItem.visit_ho = visithos[i];
                            oItem.visit_why = visitwhys[i];
                            oData.add(oItem);
                        }


                        mListView = (ListView) findViewById(R.id.list_mountain);
                        ListAdapter mMountainAdapter = new ListAdapter(oData);
                        mListView.setAdapter(mMountainAdapter);
                        setListViewHeightBasedOnChildren(mListView);

                        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                int click_id = guestid[(int) id];
                                String click_id_str = Integer.toString(click_id);
                                //Toast.makeText(ListActivity.this ,oData.get(position).guest_name,Toast.LENGTH_LONG).show();
                                show(click_id_str, position);
                            }
                        });


                    }
                }
            }
            @Override
            public void onFailure(Call<GuestData> call, Throwable t) {

            }
        });


    }
}